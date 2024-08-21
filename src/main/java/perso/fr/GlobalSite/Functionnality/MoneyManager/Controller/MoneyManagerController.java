package perso.fr.GlobalSite.Functionnality.MoneyManager.Controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.MoneyManagerUser;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewAccumulatorDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewBankAccountDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewLabelDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Dto.NewTransactionDto;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Repository.MoneyManagerUserRepository;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service.AccumulatorService;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service.BankAccountService;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service.LabelService;
import perso.fr.GlobalSite.Functionnality.MoneyManager.Entity.Service.TransactionService;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Service.UserService;

/** Controlleur principal de MoneyManager. */
@Controller
@RequestMapping("MoneyManager")
public class MoneyManagerController {
    @Autowired
    private MoneyManagerUserRepository moneyManagerUserRepository;

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private AccumulatorService accumulatorService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    /** Affiche la page d'accueil.
     *
     * @param model Le model.
     * @return La page.
     */
    @GetMapping
    String mainPage(Model model) {
        model.addAttribute("newBankAccountDto", new NewBankAccountDto());
        model.addAttribute("newAccumulatorDto", new NewAccumulatorDto());
        model.addAttribute("newLabelDto", new NewLabelDto());
        model.addAttribute("newTransactionDto", new NewTransactionDto());
        
        model.addAttribute("user", this.getMoneyManagerUser());
        return "Functionnality/MoneyManager/main";
    }
    
    /** Création de compte bancaire.
     *
     * @param bankAccount Le compte bancaire.
     * @return La page.
     */
    @PostMapping("/bankAccount/create")
    public String createBankAccount(@ModelAttribute NewBankAccountDto bankAccount) {
        bankAccountService.saveAccount(bankAccount, getMoneyManagerUser());
        return "redirect:/MoneyManager"; // Redirige vers la page de détails utilisateur
    }

    /** Création d'un accumulateur.
     *
     * @param accumulator L'accumulateur'.
     * @return La page.
     */
    @PostMapping("/accumulator/create")
    public String createAccumulator(@ModelAttribute NewAccumulatorDto accumulator) {
        accumulatorService.saveAccumulator(accumulator, getMoneyManagerUser());
        return "redirect:/MoneyManager";
    }

    /** Création d'un label.
     *
     * @param label Le label.
     * @return La page.
     */
    @PostMapping("/label/create")
    public String createLabel(@ModelAttribute NewLabelDto label) {
        labelService.saveLabel(label, getMoneyManagerUser());
        return "redirect:/MoneyManager";
    }

    /** Création d'une transaction.
     *
     * @param transaction La transaction.
     * @return La page.
     */
    @PostMapping("/transaction/create")
    public String createTransaction(@ModelAttribute NewTransactionDto transaction) {
        // Assurez-vous que transactionService existe et est correctement injecté
        transactionService.saveTransaction(transaction); 
        return "redirect:/MoneyManager";
    }

    /** Affiche la page de lecture de relevé bancaire.
     *
     * @return La page.
     */
    @GetMapping("/statementreader")
    public String showStatementReader() {
        System.out.println("showStatementReader");
        return "Functionnality/MoneyManager/statementreader";
    }

    /** Gère le téléchargement de fichiers.
     *
     * @param file Le fichier.
     * @return Le résultat.
     */
    @PostMapping("/statementreader")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file selected", HttpStatus.BAD_REQUEST);
        }

        // Vérifier que le fichier est un PDF
        if (!file.getContentType().equals("application/pdf")) {
            return new ResponseEntity<>("Invalid file type. Only PDF files are accepted.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Vous pouvez maintenant enregistrer le fichier ou le traiter selon vos besoins
            InputStream inputStream = file.getInputStream();
            System.out.println(inputStream);
            // Process the input stream as needed
            // For example, save the file to a directory or a database

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Renvoie le MoneyManagerUser de l'utilisateur.
     *
     * @return Le compte utilisateur.
     */
    private MoneyManagerUser getMoneyManagerUser() {
        String email = "not_found";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }

        UserDto mainUser = userService.findUserDtoByEmail(email);

        return moneyManagerUserRepository.findOneByUserData(mainUser.getUserData());
    }
}
