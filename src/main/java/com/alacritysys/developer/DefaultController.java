package com.alacritysys.developer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

	@GetMapping("/")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok("I Really Love Java as it has a coffee icon");
	}

}
