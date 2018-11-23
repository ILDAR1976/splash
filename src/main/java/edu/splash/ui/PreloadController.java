package edu.splash.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class PreloadController {
	
    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(PreloadController.class);

    @FXML 
    private Label main;
    
    @FXML 
    private Label value;

    @FXML 
    private ProgressBar progress;
   
    @FXML 
    private ImageView phoneImage;
    
    @FXML
    public void initialize() {
    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
    }

	public Label getMain() {
		return main;
	}

	public Label getValue() {
		return value;
	}

	
	public ProgressBar getProgress() {
		return progress;
	}

	public ImageView getPhoneImage() {
		return phoneImage;
	}

	public void setPhoneImage(ImageView phoneImage) {
		this.phoneImage = phoneImage;
	}

	
    
}
