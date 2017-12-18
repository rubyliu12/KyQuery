package app.user;

import app.query.generated.tables.KyUserAccess;
import app.util.database.DbContext;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jooq.DSLContext;
import org.jooq.Record;

public class UserDao {

  private static DbContext dbContext = new DbContext();
  private static DSLContext create = dbContext.getDSLContext();
  private final List<User> users = ImmutableList.of(
      //        Username    Salt for hash                    Hashed password (the password is "password" for all users)
      new User("perwendel", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
          "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO"),
      new User("davidase", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe",
          "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy"),
      new User("federico", "$2a$10$E3DgchtVry3qlYlzJCsyxe",
          "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2")
  );

  public User getUserByUsername(String username) {
    return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
  }

  public Iterable<String> getAllUserNames() {
    return users.stream().map(User::getUsername).collect(Collectors.toList());
  }

  public boolean getUser(String userName, String hashedPassword) {
    Record result = create.select(KyUserAccess.KY_USER_ACCESS.ID).from(KyUserAccess.KY_USER_ACCESS)
        .where(KyUserAccess.KY_USER_ACCESS.USERNAME.eq(userName))
        .and(KyUserAccess.KY_USER_ACCESS.HASHEDPASSWORD.eq(hashedPassword)).and(KyUserAccess.KY_USER_ACCESS.ISACTIVE.eq("1")).fetchOne();
    return Objects.isNull(result) ? false : true;
  }
}
