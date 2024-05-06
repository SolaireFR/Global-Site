package perso.fr.SpringSecuritySite.Connection.Service;

import java.util.List;

import perso.fr.SpringSecuritySite.Connection.Dto.UserDto;
import perso.fr.SpringSecuritySite.Connection.Entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
