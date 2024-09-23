package perso.fr.GlobalSite.Main.Service;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import perso.fr.GlobalSite.Main.Entity.Dto.UserDto;
import perso.fr.GlobalSite.Main.Entity.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Main.Entity.Repository.UserRepository;
import perso.fr.GlobalSite.Main.Entity.Repository.UserDataRepository;
import perso.fr.GlobalSite.Main.Entity.Role;
import perso.fr.GlobalSite.Main.Entity.User;
import perso.fr.GlobalSite.Main.Entity.UserData;

/** Classe Service de User. */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDataRepository userDataRepository;

    /** Enregistre un utilisateur.
     *
     * @param userDto Les données de l'utilisateur.
     */
    public void registerUser(UserRegisterDto userDto) {
        User user = new User();

        user.setUsername(userDto.getUsername());

        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(Arrays.asList(new Role("ROLE_USER")));

        User savedUser = userRepository.save(user);

        UserData userData = new UserData(savedUser);

        userDataRepository.save(userData);
    }

    /** Trouve un utilisateur par son nom d'utilisateur.
     *
     * @param username Le nom d'utilisateur.
     * @return L'utilisateur.
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /** Trouve un utilisateur par son nom d'utilisateur et le converti en UserDto.
     *
     * @param username Le nom d'utilisateur.
     * @return L'utilisateur.
     */
    public UserDto findUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return mapToUserDto(user);
    }

    /** Converti un User en UserDto.
     *
     * @param user L'utilisateur.
     * @return L'utilisateur.
     */
    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setCreation(user.getCreation());
        userDto.setUserData(this.getUserData(user));
        return userDto;
    }

    private UserData getUserData(User user) {
        return userDataRepository.findByUsername(user.getUsername());
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

    /** Supprime un utilisateur par son username.
     *
     * @param username L'username.
     * @return Si la suppression a réussi.
     */
    @Transactional
    public Boolean deleteUserByUsername(String username) {
        try {
            // Supprimer l'utilisateur de la base de données en utilisant le UserRepository
            Integer removed = userRepository.deleteByUsername(username);
            return removed > 0; // La suppression a réussi
        } catch (Exception e) {
            System.out.println("Suppression utilisateur echoue !");
            e.printStackTrace();
            return false; // La suppression a échoué
        }
    }
}
