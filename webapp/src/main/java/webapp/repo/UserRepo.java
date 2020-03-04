package webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import webapp.entity.User;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepo extends JpaRepository<User, Long> {

    User findByName(String name);

    Optional<User> findById(Long id);

}
