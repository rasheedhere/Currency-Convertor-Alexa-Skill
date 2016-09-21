package com.rasheedhere;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KakaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KakaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		CurrencySpeechletRequestStreamHandler currencySpeechletRequestStreamHandler = new CurrencySpeechletRequestStreamHandler();
	}
}
