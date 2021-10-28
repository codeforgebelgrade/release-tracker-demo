package com.codeforge.codeforgeDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class CodeforgeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeforgeDemoApplication.class, args);
	}

}
