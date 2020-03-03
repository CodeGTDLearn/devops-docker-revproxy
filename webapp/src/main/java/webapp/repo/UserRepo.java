package webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import webapp.entity.User;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Long> {

    User findByName(String email);

}
