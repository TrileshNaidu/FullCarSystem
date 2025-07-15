package com.example.FullCarSystem.Service;

import com.example.FullCarSystem.Modules.model.User;
import com.example.FullCarSystem.Modules.repository.UserRepository;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Optional;

public class userService {

    private final UserRepository userRepository;

    public userService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }


    public List<User> getAllUsers(){
             return userRepository.findAll();
    }


    public Optional<User> getUserById(Long id){
        return  userRepository.findById(id);
    }


    public User updateUser(Long id, User updatedUser){
        return userRepository.findById(id).map(users -> {
            users.setUsername(updatedUser.getUsername());
            users.setEmail(updatedUser.getEmail());
            users.setPassword(updatedUser.getPassword());
            return userRepository.save(users);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


}
