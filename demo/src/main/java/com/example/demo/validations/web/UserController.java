package com.example.demo.validations.web;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.facade.UserFacade;
import com.example.demo.repository.responce.MessageResponce;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;

    @PostMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteUser(Principal principal) {
        userService.deleteUser(principal);
        return new ResponseEntity<>(new MessageResponce("User was deleted"), HttpStatus.OK);
    }

    @PostMapping("/getAdmin")
    public ResponseEntity<Object> getRoleAdmin(Principal principal) {
        userService.getAdminRole(principal);
        return new ResponseEntity<>(new MessageResponce("get admin successfully"), HttpStatus.OK);
    }


}
