package com.bwteconologia.sulmetais.controllers;


import com.bwteconologia.sulmetais.exceptions.UserAlreadyExistsException;
import com.bwteconologia.sulmetais.exceptions.UserNotFoundException;
import com.bwteconologia.sulmetais.models.LoginResponse;
import com.bwteconologia.sulmetais.models.UserModel;
import com.bwteconologia.sulmetais.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping(value = "/users")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable("id") int id) {
        UserModel user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " is Not Found!"));
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserModel> addUser(@RequestBody UserModel user) {
        Optional<UserModel> existsUser = userService.findByLogin(user.getLogin());
        if (existsUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        UserModel savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable("id") int id, @RequestBody UserModel userUpdated) {
        UserModel user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " is Not Found!"));

        user.setName(userUpdated.getName());
        user.setEmail(userUpdated.getEmail());
        user.setLogin(userUpdated.getLogin());
        user.setPassword(userUpdated.getPassword());
        user.setRole(userUpdated.getRole());
        user.setPhone(userUpdated.getPhone());
        user.setUpdatedAt(new Date());

        UserModel updatedUser = userService.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        UserModel user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with " + id + " is Not Found!"));
        userService.deleteById(user.getId());

        String message = "User with ID " + id + " is deleted";
        return ResponseEntity.ok(message);
    }
    @PostMapping("/users/role")
    public ResponseEntity<Optional<UserModel>> getUsersByRole(@RequestBody Map<String, String> requestBody) {
        String role = requestBody.get("role");
        int id = Integer.parseInt(requestBody.get("id"));
        Optional<UserModel> users = userService.findByRoleAndId(role, id);
        return ResponseEntity.ok(users);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserModel userCheck) {
        Optional<UserModel> user = userService.findByLogin(userCheck.getLogin());
        int userId = user.get().getId();
        if (user.isPresent() && user.get().getPassword().equals(userCheck.getPassword())) {
            String message = "Logged in successfully";
            LoginResponse loginResponse = new LoginResponse(message, userId);
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}


