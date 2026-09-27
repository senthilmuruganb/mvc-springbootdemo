package com.example.demo.service;

import com.example.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import java.util.List;
import java.util.ArrayList;

@Service
public class LoginService {
	@Autowired
    private LoginRepository loginRepository;

    public String validateUser(String email, String password) {
        String authResult = loginRepository.findUsernameByEmail(email,password);
        return authResult;
    }
    
    public List<User> getAllUsers() {
        return loginRepository.findAllUsers();
    }
    public void saveUser(User user) {
    	loginRepository.insertUser(user);
    }
    public User getUserById(String userid) {
        return loginRepository.findUserById(userid);
    }
    public void updateUser(User user) {
    	loginRepository.updateUser(user);
    }
    public void deleteUserById(String userid) {
    	loginRepository.deleteUserById(userid);
    }
}
