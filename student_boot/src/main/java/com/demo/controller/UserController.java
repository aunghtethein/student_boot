package com.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.demo.ds.SignupForm;
import com.demo.ds.User;
import com.demo.repo.UserRepo;
import com.demo.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping({"/","/home"})
	public String index() {
		return "home";
	}
	
	

	@GetMapping("/account")
	public String account(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		model.addAttribute("localDateTime", LocalDateTime.now());
		
		return "account";
	}
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user",new User());
		return "login";
	}
	@PostMapping("/login-process")
	public String loginProcess(@ModelAttribute("user") @Valid User user,BindingResult result, Model model,HttpServletRequest request) {
		if(result.hasErrors()) {
			return "login";
		}
		User name = userService.findByUsername(user.getUsername());	
		User pass = userService.findByPassword(user.getPassword());	
		
		
		if(name != null && pass != null) {
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", user.getUsername());
			return "home";
		}
	
		return "login";	
		
	}
	@GetMapping("/logout")
	public String logoutPorcess(HttpSession session) {
		if(session.getAttribute("currentUser") != null) {
			session.invalidate();
		}
		return "redirect:/login";
	}

	@GetMapping("/register")
    public String addStudent(Model model){
    	model.addAttribute("signupForm", new SignupForm());
        return "register";
    }
	
	@PostMapping("/saveUser")
    public String save(@ModelAttribute("signupForm") @Valid SignupForm signupForm, BindingResult result,Model model) {

		if(result.hasErrors()) {
			return "register";
		}
		if(!signupForm.getPassword().equals(signupForm.getRepeatPassword())) {
			return "register";
		}else {
			User user = new User();
			user.setUsername(signupForm.getUsername());
			user.setPassword(signupForm.getPassword());
			userService.save(user);
	    }
		return "redirect:/home";    	
    }    
	
	@GetMapping("/user-list")
	public String showUserList(Model model, @Param("keyword") String keyword,HttpServletRequest request){
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		model.addAttribute("users",userService.listAll(keyword));
		model.addAttribute("keyword",keyword);
		return "userList";
    }
	
	 @GetMapping("/edit/{id}")
	    public String showUserUpdateForm(@PathVariable Integer id, Model model,HttpServletRequest request){
		 if(request.getSession().getAttribute("currentUser") == null) {
				request.getSession(false).invalidate();
				model.addAttribute("error", "Please Login First");
				return "redirect:/login";
			}  
		 User user = userService.findById(id);
	        model.addAttribute("user",user);
	        return "userUpdate";
	    }
	    @PostMapping("/update/{id}")
	    public String processUserUpdate(@PathVariable Integer id,@ModelAttribute("user") @Valid User user,BindingResult result,Model model){
	        if (result.hasErrors()){
	            user.setId(id);
	            return "userUpdate";
	        }

	        userService.save(user);
	        return "redirect:/user-list";
	    }
	    
	    @GetMapping("/delete/{id}")
	    public String deleteUser(@PathVariable Integer id,Model model,HttpServletRequest request){
	    	if(request.getSession().getAttribute("currentUser") == null) {
				request.getSession(false).invalidate();
				model.addAttribute("error", "Please Login First");
				return "redirect:/login";
			}
	        User user = userService.findById(id);
	        userRepo.delete(user);
	        return "redirect:/user-list";
	    }

	
}
