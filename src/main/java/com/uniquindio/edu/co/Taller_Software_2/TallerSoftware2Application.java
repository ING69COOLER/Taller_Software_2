package com.uniquindio.edu.co.Taller_Software_2;

import com.uniquindio.edu.co.Taller_Software_2.controller.ConsoleController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TallerSoftware2Application implements CommandLineRunner {

	private final ConsoleController consoleController;

	public TallerSoftware2Application(ConsoleController consoleController) {
		this.consoleController = consoleController;
	}

	public static void main(String[] args) {
		SpringApplication.run(TallerSoftware2Application.class, args);
	}

	@Override
	public void run(String... args) {
		consoleController.iniciar();
	}
}
