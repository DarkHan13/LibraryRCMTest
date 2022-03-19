package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setFirstname(userIn.getFirstname());
        user.setEmail(userIn.getEmail());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);
        }
        catch (Exception e){
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }


    public void deleteUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        userRepository.delete(user);
    }

    public void getAdminRole(Principal principal) {
        User user = getUserByPrincipal(principal);
        user.getRoles().add(ERole.ROLE_ADMIN);
        userRepository.save(user);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }







}
