package autogara.persistence;

import autogara.domain.User;

public interface RepositoryUser extends IRepository<String, User> {
    User findByUserAndPass(String username, String password);
}
