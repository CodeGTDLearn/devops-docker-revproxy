package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import webapp.entity.User;
import webapp.exceptions.ResourceNotFound;
import webapp.exceptions.UsernameDuplicated;
import webapp.repo.UserRepo;

import java.util.Optional;

@Service
public class UserService implements UserServiceInt {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User save(User user) throws UsernameDuplicated {

        User checkUsername = userRepo.findByName(user.getName());

        if (checkUsername != null) {
            throw new UsernameDuplicated("Username already exists.");
        }

        return userRepo.save(user);
    }


    @Override
    public User update(User user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(Long id) {
        verifyIdUserLaunchException(id);
        userRepo.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public User findById(Long id) {

        Optional<User> userReturn = userRepo.findById(id);

        return userReturn.orElseThrow(() -> new ResourceNotFound("User ID not found."));
    }

    @Override
    public void verifyIdUserLaunchException(Long id) {
        User userTest = new User();
        try {
            userTest = findById(id);
        } catch (Exception e) {
            throw new ResourceNotFound("User ID not found.");
        }
    }
}
