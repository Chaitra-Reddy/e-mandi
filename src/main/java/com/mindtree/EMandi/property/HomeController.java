package com.mindtree.EMandi.property;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping({"/home","/"})
	public String get() {
		return "hello";
	}
}
