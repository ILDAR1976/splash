package edu.splash;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

import com.sun.javafx.application.LauncherImpl;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SuppressWarnings({ "unused", "restriction" })
public abstract class AbstractJavaFxApplicationSupport extends Application {
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    private static final int COUNT_LIMIT = 500000;
    private static int stepCount = 1;
    private static String[] savedArgs;
    private Stage applicationStage;
    protected ConfigurableApplicationContext context;

    public static String STEP() {
        return stepCount++ + ". ";
    }


    @Override
    public void init() throws Exception {
        
    	SpringApplication sa = new SpringApplication();
    	context = sa.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
        

         //Perform some heavy lifting (i.e. database start, check for application kupdates, etc. )
       /* for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new CurrentPreloader.ProgressNotification(progress));
        }
*/
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass,Class<? extends Preloader> appPreloaderClass  , String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        //Application.launch(appClass, args);
        LauncherImpl.launchApplication(appClass, appPreloaderClass, args);
    }
}
