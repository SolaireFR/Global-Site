package perso.fr.GlobalSite.Connection.Service;

import java.util.List;

import perso.fr.GlobalSite.Connection.Dto.UserDataDto;
import perso.fr.GlobalSite.Connection.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Connection.Entity.User;

public interface IUserService {
    void saveUser(UserRegisterDto userDto);

    User findUserByEmail(String email);

    UserDataDto findUserDataByEmail(String email);

    List<UserRegisterDto> findAllUsers();

    Boolean deleteUserByEmail(String email);
}
