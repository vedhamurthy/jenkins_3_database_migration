package com.ej;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/new")
    public @ResponseBody User addNewUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody User getUserById(@PathVariable("id") Long id) {
        return this.userRepository.findOne(id);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
