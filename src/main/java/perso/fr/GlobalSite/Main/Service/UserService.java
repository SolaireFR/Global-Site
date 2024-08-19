package perso.fr.GlobalSite.Main.Service;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Entity.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Main.Entity.Repository.UserRepository;
import perso.fr.GlobalSite.Main.Entity.Role;
import perso.fr.GlobalSite.Main.Entity.User;

/** Classe Service de User. */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    /** Constructeur de UserService.
     *
     * @param userRepository Récupération des données utilisateurs.
     * @param passwordEncoder Encodage des mots de passe.
     * @param mailService Service des mails.
     */
    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    /** Enregistre un utilisateur.
     *
     * @param userDto Les données de l'utilisateur.
     */
    public void registerUser(UserRegisterDto userDto) {
        User user = new User();

        if (userDto.getDisplayName() == null || userDto.getDisplayName().isEmpty())
            user.setDisplayName(userDto.getEmail().split("@")[0]);
        else
            user.setDisplayName(userDto.getDisplayName());

        user.setEmail(userDto.getEmail());

        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        User createdUser = userRepository.save(user);

        //String verificationToken = createVerificationToken(createdUser);

        // sendVerificationMail(createdUser, verificationToken); // Mise en commentaire
        // pour ne pas envoyer de mail
        this.enableUser(createdUser); // Pas de commentaire pour verifié
    }

    // private void sendVerificationMail(User user, String token) {
    //     String destinationEMail = user.getEmail();
    //     String subject = "GlobalSite - Registration Confirmation";

    //     String confirmationUrl = GlobalVars.mainUrl + "userVerification?token=" + token;
    //     String body = "Cliquer sur le lien suivant pour verifier votre compte :\r\n" + confirmationUrl;
    //     mailService.sendEmail(destinationEMail, subject, body);
    // }

    /** Crée un token de vérification.
     *
     * @param user L'utilisateur.
     * @return Le token.
     */
    public String createVerificationToken(User user) {
        String token = User.generateNewToken();
        return token;
    }

    /** Trouve un utilisateur avec un token.
     *
     * @param verificationToken Le token.
     * @return L'utilisateur.
     */
    public User findUserWithToken(String verificationToken) {
        return userRepository.findByToken(verificationToken);
    }

    /** Active un utilisateur.
     *
     * @param user L'utilisateur.
     */
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    /** Trouve un utilisateur par son email.
     *
     * @param email L'email.
     * @return L'utilisateur.
     */
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /** Trouve un utilisateur par son email et le converti en UserDto.
     *
     * @param email L'email.
     * @return L'utilisateur.
     */
    public UserDto findUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return mapToUserDto(user);
    }

    /** Converti un User en UserDto.
     *
     * @param user L'utilisateur.
     * @return L'utilisateur.
     */
    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setDisplayName(user.getDisplayName());
        userDto.setEmail(user.getEmail());
        userDto.setCreation(user.getCreation());
        userDto.setUserData(user.getUserData());
        return userDto;
    }

    /** Trouve tous les utilisateurs.
     *
     * @return La liste des utilisateurs.
     */
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().
            map(user -> mapToUserDto(user)).
            collect(Collectors.toList());
    }

    /** Supprime un utilisateur par son email.
     *
     * @param email L'email.
     * @return Si la suppression a réussi.
     */
    @Transactional
    public Boolean deleteUserByEmail(String email) {
        try {
            // Supprimer l'utilisateur de la base de données en utilisant le UserRepository
            Integer removed = userRepository.deleteByEmail(email);
            return removed > 0; // La suppression a réussi
        } catch (Exception e) {
            System.out.println("Suppression utilisateur echoue !");
            e.printStackTrace();
            return false; // La suppression a échoué
        }
    }
}
