package com.example.demo.controller;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.User;
import java.util.ArrayList;
import java.util.List;
@Controller
public class LoginController {
	
	@Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Spring Boot Web App using STS!");
        return "login";  // Loads templates/index.html
    }
    
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("error", "");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model) {
    	String authResult=loginService.validateUser(email, password);
    	if (authResult!=null && !(authResult.equalsIgnoreCase("NO SUCH USER"))) {
            model.addAttribute("username", authResult);
            return "welcome";
        } else {
            model.addAttribute("error", authResult);
            return "login";
        }
    }
    
    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> userList = loginService.getAllUsers();
        model.addAttribute("users", userList);
        return "userlist"; // thymeleaf template name
    }
    
    @GetMapping("/users/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute("user") User user) {
    	loginService.saveUser(user);
        return "redirect:/users"; // redirect after saving
    }
    
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") String userid, Model model) {
        User existingUser = loginService.getUserById(userid);
        model.addAttribute("user", existingUser);
        return "user-edit-form";
    }
    
    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user) {
    	loginService.updateUser(user);
        return "redirect:/users";
    }
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String userid,RedirectAttributes redirectAttributes) {
    	loginService.deleteUserById(userid);
    	redirectAttributes.addFlashAttribute("delResult", "DELETION SUCCESSFULLY");
        return "redirect:/users";
    }
}
