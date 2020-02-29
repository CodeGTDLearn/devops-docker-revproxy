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

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserServiceInt userServ;

    @PostMapping
    public User add(@Valid @RequestBody User user) throws UsernameDuplicated {
        return userServ.add(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userServ.update(user);
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
    public ResponseEntity<Page<User>> findAll(Pageable pageable){
        Page<User> userList = userServ.findAll(pageable);
        return new ResponseEntity<>(userList, new HttpHeaders(), HttpStatus.OK);
    }


}
