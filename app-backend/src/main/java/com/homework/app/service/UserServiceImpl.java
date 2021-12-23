package com.homework.app.service;

import com.homework.app.model.User;
import com.homework.app.respository.MongoTemplateOperations;
import com.homework.app.respository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
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

    public User addUser(User user, String role){
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsersByRole(String role){
        return mongoTemplateOperations.getAllUsersByRole(role);
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }

    public User updateUser(String id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User updatedUser = optionalUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            updatedUser.setName(user.getName());
            return userRepository.save(updatedUser);
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
