package com.habibu.abdullahi.shoppinglistsystem.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.access.prepost.PreAuthorize;

import com.habibu.abdullahi.shoppinglistsystem.exception.CustomErrorType;
import com.habibu.abdullahi.shoppinglistsystem.dto.UserDTO;
import com.habibu.abdullahi.shoppinglistsystem.repository.UserJpaRepository;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
    
    public static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);
    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> listAllUsers(){
        logger.info("Fetching all users");
        List<UserDTO> users = userJpaRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final Long id ) {
        logger.info("Feteching user with id {} ", id);
        UserDTO user = userJpaRepository.findById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<UserDTO>(new CustomErrorType("User with id "+ id + "not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user) {
        logger.info("Creating user: {}", user);
        if (userJpaRepository.findByName(user.getName()) != null) {
            logger.error("Unable to create. A user with name {} already exist", user.getName());
            return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to create. User with name " + user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        userJpaRepository.save(user);
        return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UserDTO user) {
        logger.info("Updating user with id {}", id);
        UserDTO currentUser = userJpaRepository.findById(id);
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found", id);
            return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to update. User with id " + id + "not found"),HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setAddress(user.getAddress());
        userJpaRepository.saveAndFlush(currentUser);

        return new ResponseEntity<UserDTO>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id) {
        logger.info("Deleting User with id {}", id);
        UserDTO user = userJpaRepository.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to delete. User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        userJpaRepository.delete(id);
        return new ResponseEntity<UserDTO>(new CustomErrorType("Deleted user with id " + id + "successfully"), HttpStatus.NO_CONTENT);
    }
    
}
