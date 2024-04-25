package perso.fr.globalsite.Connexion.Service;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import perso.fr.globalsite.Connexion.Entity.User;
import perso.fr.globalsite.Connexion.Entity.Repository.UserRepository;

@Component
public class TokenManager {
    private static final String SEPARATOR = ":";
    private static final String ALGORITHM = "AES";
    private static final int EXPIRATION_TIME_MS = 3600 * 1000;
    private static final String SECRET_FOR_EMAIL = "mot_de_passe_de_24_gsite";

    /*public static void main(String[] args) {
        String mail = "test@test.com";
        String sID = "abs123";
        String pwd = "votre_mot_de_passe_de_24";
        String token = generateToken(mail, sID, pwd);
        System.out.println("TOKEN = "+token);
        System.out.println("is valid ? "+verifyToken(token));
    }*/

    public static String generateToken(String email, String sessionId, String password) throws Exception {
        password = adaptPassword(password);

        // Obtenez l'horodatage actuel en millisecondes
        long currentTime = System.currentTimeMillis();

        // Concaténer les informations avec la clé secrète et l'horodatage
        String data = encrypt(email, SECRET_FOR_EMAIL) + SEPARATOR + encrypt(sessionId, password) + SEPARATOR + encrypt(currentTime, password);

        return data;
    }

    public static boolean isTokenValid(String token, UserRepository userRepository) throws Exception {
        String[] parts = token.split(SEPARATOR);

        if (parts.length != 3) {
            System.out.println("Token not valid !");
            return false;
        }

        String tokenEmail = getTokenEmail(token);
        String password = getUserPassword(tokenEmail, userRepository);

        password = adaptPassword(password);

        if (password == null) {
            System.out.println("Token not valide !");
            return false;
        }

        String tokenSessionId = decrypt(parts[1], password);
        long tokenTime = Long.parseLong(decrypt(parts[2], password));

        // Vérifiez si l'horodatage est expiré
        if (System.currentTimeMillis() - tokenTime > EXPIRATION_TIME_MS) {
            System.out.println("Token expired !");
            return false; // Token expiré
        }

        // Vérifier si les tokens générés correspondent
        System.out.println(tokenEmail);
        System.out.println(tokenSessionId);
        return true;
    }

    public static String getTokenEmail(String token) throws Exception {
        String[] parts = token.split(SEPARATOR);

        if (parts.length != 3) {
            System.out.println("Token not valid !");
            return null;
        }
        return decrypt(parts[0], SECRET_FOR_EMAIL);
    }

    private static String encrypt(Object objData, String secret24) {
        String data = objData.toString();
        try {
            Key key = new SecretKeySpec(secret24.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Gestion d'erreur
        }
    }

    private static String decrypt(String encryptedData, String secret24) throws Exception {
        try {
            Key key = new SecretKeySpec(secret24.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new Exception("TokenManager : can't use token !");
        }
    }

    private static String getUserPassword(String email, UserRepository userRepository) {
        User bddUser = userRepository.getUserByEmail(email);
        if (bddUser != null)
            return bddUser.getEncodedPassword();
        else return null;
    }

    private static String adaptPassword(String password) throws Exception {
        if(password.length() < 24)
            throw new Exception("Generate token : Incorrect value !");
        else
            return password.substring(0, 24);
    }
}