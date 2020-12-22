package com.studiomediatech.examples.tarnished.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrobulatorController {

	@GetMapping("/")
	public String index(Model model) {

		model.addAttribute("name", "Eyoor");
		return "index";
	}

}
