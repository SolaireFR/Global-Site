package perso.fr.GlobalSite.Main.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import perso.fr.GlobalSite.Main.Entity.Role;
import perso.fr.GlobalSite.Main.Entity.User;
import perso.fr.GlobalSite.Main.Entity.Repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/** Le service de l'utilisateur personnalisé */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    /** Constructeur
     *
     * @param userRepository UserRepository
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    true, 
                    accountNonExpired,
                    credentialsNonExpired,
                    !user.isLocked(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
