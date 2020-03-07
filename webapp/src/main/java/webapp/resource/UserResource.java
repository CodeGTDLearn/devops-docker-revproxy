package webapp.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp.entity.User;
import webapp.exceptions.UsernameDuplicated;
import webapp.services.UserServiceInt;

import webapp.utils.UtilsInt;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserServiceInt userServ;

    @Autowired
    private UtilsInt utils;

    @PostMapping
    public User save(@Valid @RequestBody User user) throws UsernameDuplicated {
        return userServ.save(user);
    }


    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable long id) {

        User userFound = userServ.findById(id);

        Object userFinal = null;
        try {
            userFinal = utils.UpdatePatchFields(userFound, user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return userServ.save((User) userFinal);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userServ.delete(id);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id, Pageable pageable) throws NoSuchFieldException {

        userServ.verifyIdUserLaunchException(id);

        User user = userServ.findById(id);

        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAll(Pageable pageable) {
        Page<User> userList = userServ.findAll(pageable);
        return new ResponseEntity<>(userList, new HttpHeaders(), HttpStatus.OK);
    }
}
