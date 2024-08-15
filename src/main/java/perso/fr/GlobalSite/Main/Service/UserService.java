package perso.fr.GlobalSite.Main.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.fr.GlobalSite.Main.Entity.Role;
import perso.fr.GlobalSite.Main.Entity.User;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDataDto;
import perso.fr.GlobalSite.Main.Entity.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Main.Entity.Repository.UserRepository;
import perso.fr.GlobalSite.Main.Config.GlobalVars;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

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

        String verificationToken = createVerificationToken(createdUser);

        // sendVerificationMail(createdUser, verificationToken); // Mise en commentaire
        // pour ne pas envoyer de mail
        this.enableUser(createdUser); // Pas de commentaire pour verifié
    }

    private void sendVerificationMail(User user, String token) {
        String destinationEMail = user.getEmail();
        String subject = "GlobalSite - Registration Confirmation";

        String confirmationUrl = GlobalVars.mainUrl + "userVerification?token=" + token;
        String body = "Cliquer sur le lien suivant pour verifier votre compte :\r\n" + confirmationUrl;
        mailService.sendEmail(destinationEMail, subject, body);
    }

    public String createVerificationToken(User user) {
        String token = User.generateNewToken();
        return token;
    }

    public User findUserWithToken(String verificationToken) {
        return userRepository.findByToken(verificationToken);
    }

    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDataDto findUserDataByEmail(String Email) {
        User user = userRepository.findByEmail(Email);
        return mapToUserDataDto(user);
    }

    public UserDataDto mapToUserDataDto(User user) {
        UserDataDto userDto = new UserDataDto();
        userDto.setDisplayName(user.getDisplayName());
        userDto.setEmail(user.getEmail());
        userDto.setCreation(user.getCreation());
        return userDto;
    }

    public List<UserDataDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDataDto(user))
                .collect(Collectors.toList());
    }

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
