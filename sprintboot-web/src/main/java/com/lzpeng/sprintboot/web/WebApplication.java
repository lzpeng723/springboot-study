package com.lzpeng.sprintboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@SpringBootApplication
public class WebApplication {

	@GetMapping
	public String hello(){
		return String.format("%s : %s", new Date(), getClass().getName());
	}

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
}
