package com.gestion.empleados;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "A0004";
		String encodedPassword = encoder.encode(rawPassword);
		
		System.out.println(encodedPassword);
		
	}
	
}
