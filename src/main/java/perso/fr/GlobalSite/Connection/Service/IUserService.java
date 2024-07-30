package perso.fr.GlobalSite.Connection.Service;

import java.util.List;

import perso.fr.GlobalSite.Connection.Dto.UserDataDto;
import perso.fr.GlobalSite.Connection.Dto.UserRegisterDto;
import perso.fr.GlobalSite.Connection.Entity.User;

public interface IUserService {
    void registerUser(UserRegisterDto userDto);

    void enableUser(User user);

    String createVerificationToken(User user);

    User findUserWithToken(String VerificationToken);

    User findUserByEmail(String email);

    UserDataDto findUserDataByEmail(String email);

    List<UserDataDto> findAllUsers();

    Boolean deleteUserByEmail(String email);
}
