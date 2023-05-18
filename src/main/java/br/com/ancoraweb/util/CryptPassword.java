package br.com.ancoraweb.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptPassword {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String result = encoder.encode("123456");
		System.out.println(result);
	}

}
