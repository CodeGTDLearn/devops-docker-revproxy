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

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserServiceInt userServ;

    @PostMapping
    public User add(@Valid @RequestBody User user) throws UsernameDuplicated {
        return userServ.add(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable long id) {

        if (userServ.getById(id) == null) return ResponseEntity.notFound().build();
        userServ.delete(id);
        user.setId(id);
        userServ.add(user);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userServ.delete(id);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id, Pageable pageable) throws NoSuchFieldException {

        userServ.verifyIdUserLaunchException(id);

        User user = userServ.getById(id);

        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAll(Pageable pageable) {
        Page<User> userList = userServ.findAll(pageable);
        return new ResponseEntity<>(userList, new HttpHeaders(), HttpStatus.OK);
    }
}
