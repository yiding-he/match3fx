package com.hyd.match3fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Match3fxApplication extends Application {

    private static ApplicationContext applicationContext;

    private static FXMLLoader fxmlLoader(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Match3fxApplication.class.getResource(fxmlPath));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        return fxmlLoader;
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Match3fxApplication.class, args);
        Application.launch(Match3fxApplication.class, args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(fxmlLoader("/main.fxml").load(), 600, 500));
        primaryStage.show();
    }
}
