package perso.fr.SpringSecuritySite.Connection.Service;

import java.util.List;

import perso.fr.SpringSecuritySite.Connection.Dto.UserDataDto;
import perso.fr.SpringSecuritySite.Connection.Dto.UserRegisterDto;
import perso.fr.SpringSecuritySite.Connection.Entity.User;

public interface IUserService {
    void saveUser(UserRegisterDto userDto);

    User findUserByEmail(String email);

    UserDataDto findUserDataByEmail(String email);

    List<UserRegisterDto> findAllUsers();

    Boolean deleteUserByEmail(String email);
}
