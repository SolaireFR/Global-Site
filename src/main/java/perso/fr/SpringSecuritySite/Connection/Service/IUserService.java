package perso.fr.SpringSecuritySite.Connection.Service;

import java.util.List;

import perso.fr.SpringSecuritySite.Connection.Dto.UserRegisterDto;
import perso.fr.SpringSecuritySite.Connection.Entity.User;

public interface IUserService {
    void saveUser(UserRegisterDto userDto);

    User findUserByEmail(String email);

    List<UserRegisterDto> findAllUsers();
}
