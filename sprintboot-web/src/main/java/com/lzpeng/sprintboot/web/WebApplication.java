package com.lzpeng.sprintboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@SpringBootApplication
public class WebApplication {

	@GetMapping
	public String hello(){
		return "Hello World !" + LocalDateTime.now();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
