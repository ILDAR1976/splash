package edu.splash;

import javafx.scene.Scene;
import javafx.stage.Stage;
import com.sun.javafx.application.LauncherImpl;
import javafx.stage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SuppressWarnings({ "unused", "restriction" })
@Lazy
@SpringBootApplication
public class Application extends AbstractJavaFxApplicationSupport {
    private Stage applicationStage;

    public Application() {
       System.out.println(Application.STEP() + "MyApplication constructor called, thread: " + Thread.currentThread().getName());
    }

    @Value("JavaFX and spring application")
    private String windowTitle;

    @Qualifier("mainView")
    @Autowired
    private ConfigurationControllers.View view;
    
    @SuppressWarnings("restriction")
	@Override
    public void start(Stage stage) throws Exception {
    	applicationStage = stage;
    	stage.setTitle(windowTitle);
    	Scene scene = new Scene(view.getView());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launchApp(Application.class, CurrentPreloader.class, args);
    }

}
