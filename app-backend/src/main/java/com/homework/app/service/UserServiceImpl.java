package com.homework.app.service;

import com.homework.app.model.User;
import com.homework.app.payload.UserPayload;
import com.homework.app.respository.MongoTemplateOperations;
import com.homework.app.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private MongoTemplateOperations mongoTemplateOperations;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, MongoTemplateOperations mongoTemplateOperations, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.mongoTemplateOperations = mongoTemplateOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(UserPayload userPayload, String role){
        User user = new User();
        user.setId(userPayload.getpId());
        user.setName(userPayload.getpName());
        user.setUsername(userPayload.getpUsername());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userPayload.getpPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsersByRole(String role){
        return mongoTemplateOperations.getAllUsersByRole(role);
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }

    public User updateUser(String id, UserPayload userPayload){

        User user = new User();
        user.setName(userPayload.getpName());
        user.setUsername(userPayload.getpUsername());
        user.setRole(userPayload.getpRole());
        user.setPassword(userPayload.getpPassword());

        Optional<User> currentUser = userRepository.findById(id);
        if (currentUser.isPresent()){
            User updatingUser = currentUser.get();

            if(user.getUsername() != null){
                updatingUser.setUsername(user.getUsername());
            }
            if(user.getName() != null){
                updatingUser.setName(user.getName());
            }
            if(user.getRole() != null){
                updatingUser.setRole(user.getRole());
            }
            if(user.getPassword() != null){
                updatingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return userRepository.save(updatingUser);
        } else {
            return null;
        }
    }

    public User deleteUser(String id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User deletedUser = optionalUser.get();
            userRepository.delete(deletedUser);
            return deletedUser;
        } else {
            return null;
        }
    }

    public User getUserByUsername(String username){
        return mongoTemplateOperations.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mongoTemplateOperations.getUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException(username + " user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
