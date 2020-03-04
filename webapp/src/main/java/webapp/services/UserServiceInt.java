package webapp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import webapp.entity.User;
import webapp.exceptions.UsernameDuplicated;

public interface UserServiceInt {

    User save(User user) throws UsernameDuplicated;

    User update(User user);

    void delete(Long idMethod);

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    public void verifyIdUserLaunchException(Long id);

}
