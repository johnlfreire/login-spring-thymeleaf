package com.br.sfb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller

public class MainController {


	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		if (user != null) {
			return "redirect:/home";
		}
		return "login";
	}
	
	@RequestMapping("/home")
	public String test() {
		return "index";
	}
	
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}

}