package com.pw.quizwhizz.controller;

import com.pw.quizwhizz.model.account.User;
import com.pw.quizwhizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class UserController {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/loginform")
	public String loginForm() {
		return "login_form";
	}

	@GetMapping("/loginerror")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login_form";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register_form";
	}

	@PostMapping("/register")
	public String addUser(@ModelAttribute @Valid User user, BindingResult bindResult, HttpServletRequest request) throws ServletException {
		if(bindResult.hasErrors())
			return "register_form";
		else {
			userService.addWithDefaultRole(user);
			request.login(user.getEmail(),user.getPassword()); // auto logowanie

			return "register_success";
		}
	}


	
}