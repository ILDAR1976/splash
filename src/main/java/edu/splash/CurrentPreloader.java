package edu.splash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import edu.splash.ConfigurationControllers.View;
import edu.splash.ui.PreloadController;
import javafx.scene.Group;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.PreloaderNotification;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.effect.DropShadow;

@SuppressWarnings("restriction")
public class CurrentPreloader extends Preloader {

	private static final double WIDTH = 600;
	private static final double HEIGHT = 400;

	private Stage preloaderStage;
	private Scene scene;
	private ProgressBar progressBar;
	private Label progress;
	private Label progressInfo;
	private PreloadController controller;

	public CurrentPreloader() {
		System.out.println(
				Application.STEP() + "MyPreloader constructor called, thread: " + Thread.currentThread().getName());	
	}

	@Override
	public void init() throws Exception {
		System.out
				.println(Application.STEP() + "MyPreloader#init (could be used to initialize preloader view), thread: "
						+ Thread.currentThread().getName());

		// If preloader has complex UI it's initialization can be done in
		// MyPreloader#init
		Platform.runLater(() -> {

			Group root = new Group();

			try {
				root.getChildren().addAll(loadForm("fxml/preload.fxml"));

			} catch (IOException e) {
				e.printStackTrace();
			}

			progressBar = controller.getProgress();
			progressInfo = controller.getInform();
		
			progress = controller.getValue();
			progress.setStyle("-fx-background-color: transparent;");

			InputStream imageStream = null;
			imageStream = getClass().getClassLoader().getResourceAsStream("splash.jpg");
			Image image = new Image(imageStream);
			controller.getPhoneImage().setImage(image);

			scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets()
			.add(getClass()
			.getClassLoader()
			.getResource("css/preload.css")
			.toExternalForm());
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Application.STEP() + "MyPreloader#start (showing preloader stage), thread: "
				+ Thread.currentThread().getName());

		this.preloaderStage = primaryStage;

		// Set preloader scene and show stage.
		preloaderStage.setResizable(false);
		preloaderStage.setMaximized(false);
		preloaderStage.initStyle(StageStyle.UNDECORATED);
		preloaderStage.setScene(scene);
		preloaderStage.show();
	}

	@Override
	public void handleApplicationNotification(PreloaderNotification info) {
		// Handle application notification in this point (see MyApplication#init).
		if (info instanceof  PreloaderProgressNotification) {
			
			progress.setText( ((ProgressNotification) info).getProgress() + "%");
			progressInfo.setText((( PreloaderProgressNotification) info).getDetails() );
			progressBar.setProgress((( PreloaderProgressNotification) info).getProgress() / 100);
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		// Handle state change notifications.
		StateChangeNotification.Type type = info.getType();
		switch (type) {
		case BEFORE_LOAD:
			// Called after MyPreloader#start is called.
			System.out.println(Application.STEP() + "BEFORE_LOAD");
			break;
		case BEFORE_INIT:
			// Called before MyApplication#init is called.
			System.out.println(Application.STEP() + "BEFORE_INIT");
			break;
		case BEFORE_START:
			// Called after MyApplication#init and before MyApplication#start is called.
			System.out.println(Application.STEP() + "BEFORE_START");

			preloaderStage.hide();
			break;
		}
	}

	private AnchorPane loadForm(String url) throws IOException {
		InputStream fxmlStream = null;
		try {
			fxmlStream = getClass().getClassLoader().getResourceAsStream(url);
			FXMLLoader loader = new FXMLLoader();
			AnchorPane anchor = loader.<AnchorPane>load(fxmlStream);
			controller = loader.getController();
			return anchor;
		} finally {
			if (fxmlStream != null) {
				fxmlStream.close();
			}
		}
	}

	public static class PreloaderProgressNotification extends ProgressNotification implements PreloaderNotification {
		private  double progress;
        private  String details;
        
       
        
		public PreloaderProgressNotification(double progress, String details) {
			super(progress);
			
			this.progress = progress;
            this.details = details;
        }

		 public double getProgress() {
	            return progress;
	        }

	     public  String getDetails() {
	            return details;
	        }
	}

}
