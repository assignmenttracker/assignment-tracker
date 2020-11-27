package com.hanahs.tracker;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class AccountSelectorController {
	@FXML VBox mainVbox;
	
	private void closeWindow() {
		Stage stage = (Stage) mainVbox.getScene().getWindow();
		stage.close();
	}

	@FXML public void loginWithGoogleClassroom() {
		AccountSelection.getInstance().setAccountType("google_classroom");
		closeWindow();
	}

	@FXML public void loginWithSchoology() {
		AccountSelection.getInstance().setAccountType("schoology");
		closeWindow();
	}
}
