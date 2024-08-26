package perso.fr.GlobalSite.Functionnality.MoneyManager.Service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Transaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Le service pour gèrer les PDFs. */
@Service
public class PdfService {

    /** Extrait le texte d'un document PDF.
     *
     * @param inputStream Le document PDF.
     * @return Le texte extrait.
     * @throws IOException Si une erreur survient lors de la lecture du document.
     */
    public Transaction[] extractTextFromPdf(InputStream inputStream) throws IOException {
        // Charger le document PDF à partir de l'InputStream
        
        byte[] pdfData = inputStreamToByteArray(inputStream);

        try (PDDocument DOCUMENT = Loader.loadPDF(pdfData)) {
            // Utiliser PDFTextStripper pour extraire le texte
            PDFTextStripper pdfStripper = new PDFTextStripper();

            String text = pdfStripper.getText(DOCUMENT);

            // Couper le texte entre "VIREMENTS RECUS" et "SOLDE CREDITEUR"
            String relevantText = extractRelevantText(text);

            // Formater le texte avec des retours à la ligne
            String formattedText = formatTransactions(relevantText);

            // Diviser le texte en transactions individuelles
            Transaction[] transactionsTextTable = splitTransactions(formattedText);

            return transactionsTextTable;
        }
    }

    /** Extrait les données d'un formulaire PDF.
     *
     * @param inputStream Le document PDF.
     * @return Les données extraites.
     * @throws IOException Si une erreur survient lors de la lecture du document.
     */
    private byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream BUFFER = new ByteArrayOutputStream()) {
            int nRead;
            final int BUFFER_SIZE = 1024;
            byte[] data = new byte[BUFFER_SIZE];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                BUFFER.write(data, 0, nRead);
            }
            return BUFFER.toByteArray();
        }
    }

    private String extractRelevantText(String text) {
        int startIndex = text.indexOf("VIREMENTS RECUS");
        int endIndex = text.lastIndexOf("SOLDE CREDITEUR");
    
        // Vérifiez si les indices sont valides et que startIndex est avant endIndex
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return text.substring(startIndex + "VIREMENTS RECUS".length(), endIndex).trim();
        } else {
            // Gérer le cas où les mots-clés ne sont pas trouvés ou sont dans le mauvais ordre
            throw new IllegalArgumentException(
                "Impossible de trouver les sections 'VIREMENTS RECUS' et 'SOLDE CREDITEUR' correctement dans le texte."
                );
        }
    }

    private String formatTransactions(String text) {
        // Supposons que chaque transaction soit sur une seule ligne avec des espaces comme séparateurs
        String[] transactions = text.split("\n");

        StringBuilder formattedText = new StringBuilder();
        for (String transaction : transactions) {
            // Remplacer les espaces multiples par un seul espace
            String formattedTransaction = transaction.replaceAll("\\s+", " ");
            formattedText.append(formattedTransaction).append("\n");
        }
        return formattedText.toString();
    }

    /** Divise le texte des transactions en transactions individuelles.
     *
     * @param transactionsText Le texte des transactions.
     * @return Un tableau de transactions individuelles.
     */
    public Transaction[] splitTransactions(String transactionsText) {
        String searchRegex = "(\\d{2}/\\d{2}/\\d{4})\\s+(.+?)\\s+([+-])\\s+([\\d,]+)";
        Pattern pattern = Pattern.compile(searchRegex);
        String[] badStrings = {"POUR UN TOTAL DE"};
        String removeRegex = "[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9] ";

        Matcher matcher = pattern.matcher(transactionsText);

        List<Transaction> transactionsTextList = new ArrayList<>();
        while (matcher.find()) {
            String date = matcher.group(1);
            String text = matcher.group(2);
            String sign = matcher.group(3);
            String number = matcher.group(4).replace(',', '.');

            boolean containNoBadString = Arrays.stream(badStrings).noneMatch(text::contains);
            if (containNoBadString) {
                Transaction newTransaction = new Transaction();
                newTransaction.setAmount(Float.valueOf("" + (sign.equals("-") ? '-' : "") + number));
                newTransaction.setSecondParticipant(text.replaceAll(removeRegex, ""));
                newTransaction.setTransactionDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                transactionsTextList.add(newTransaction);
            }
        }

        return transactionsTextList.toArray(new Transaction[0]);
    }
}
