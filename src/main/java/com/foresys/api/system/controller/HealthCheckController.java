package com.foresys.api.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
	@GetMapping("/healthCheck")
	public String halthCheck() {
		return "OK";
	}
}
