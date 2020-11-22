package com.freeinc.Library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.freeinc.Library.Business.IUserService;
import com.freeinc.Library.Entites.User;
import com.freeinc.Library.payloads.SignUpRequest;

@Controller
public class RegistrationController {

	@Autowired
	private IUserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/signup")
	public String showRegister(Model model) {

		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/auth/signup")
	public String registeration(@ModelAttribute("user") User user, Model model) {

		User existing = userService.findByUserName(user.getUserName());
		if (existing != null) {
			model.addAttribute("signupRequest", new SignUpRequest());
			model.addAttribute("message", "User already exist with this name");
		}
		userService.registerUser(user);

		return "redirect:../login";
	}

}
