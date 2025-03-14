package com.listo.tag_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@Controller
public class TagFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TagFinderApplication.class, args);
	}
}
