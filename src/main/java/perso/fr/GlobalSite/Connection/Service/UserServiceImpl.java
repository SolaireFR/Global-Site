package perso.fr.GlobalSite.Connection.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import perso.fr.GlobalSite.Connection.Dto.UserDataDto;
import perso.fr.GlobalSite.Connection.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Connection.Entity.Role;
import perso.fr.GlobalSite.Connection.Entity.User;
import perso.fr.GlobalSite.Connection.Entity.Repository.RoleRepository;
import perso.fr.GlobalSite.Connection.Entity.Repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserRegisterDto userDto) {
        User user = new User();

        if (userDto.getDisplayName() == null || userDto.getDisplayName().isEmpty())
            user.setDisplayName(user.getEmail().split("@")[0]);
        else
            user.setDisplayName(userDto.getDisplayName());

        user.setEmail(userDto.getEmail());

        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = addRoleUser();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
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

    @Override
    public List<UserDataDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDataDto(user))
                .collect(Collectors.toList());
    }

    private Role addRoleUser() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
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
