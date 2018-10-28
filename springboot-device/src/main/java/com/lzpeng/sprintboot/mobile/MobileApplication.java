package com.lzpeng.sprintboot.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@SpringBootApplication
public class MobileApplication {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public String hello(Device device, @RequestHeader("user-agent") String userAgent){
		return String.format("<title>当前设备信息</title><body>%s<br>%s<br>%s<br>%s<br>%s</body>",
				new Date(),
				device,
				userAgent,
				"SpringBoot " + SpringBootVersion.getVersion(),
				MobileApplication.class.getName());
	}

	public static void main(String[] args) {
		SpringApplication.run(MobileApplication.class, args);
	}
}
