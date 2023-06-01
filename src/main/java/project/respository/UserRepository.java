package project.respository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import project.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, String> {
}
