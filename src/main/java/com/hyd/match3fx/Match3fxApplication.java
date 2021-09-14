package com.hyd.match3fx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Match3fxApplication extends Application {

	public static void main(String[] args) {
		SpringApplication.run(Match3fxApplication.class, args);
		Application.launch(Match3fxApplication.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.show();
	}
}
