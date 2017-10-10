package app.user;

import static app.Application.userDao;

import org.mindrot.jbcrypt.BCrypt;

public class UserController {

  // Authenticate the user by hashing the inputted password using the stored salt,
  // then comparing the generated hashed password to the stored hashed password
  public static boolean authenticate(String username, String password) {
    if (username.isEmpty() || password.isEmpty()) {
      return false;
    }
    //        User user = userDao.getUserByUsername(username);
    //        if (user == null) {
    //            return false;
    //        }
    //Keyimc$2a
    String hashedPassword = BCrypt.hashpw(password, "$2a$10$h.dl5J86rGH7I8bD9bZeZe");
    return userDao.getUser(username, hashedPassword);
  }

  // This method doesn't do anything, it's just included as an example
  public static void setPassword(String username, String oldPassword, String newPassword) {
    if (authenticate(username, oldPassword)) {
      String newSalt = BCrypt.gensalt();
      String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
      // Update the user salt and password
    }
  }
}
