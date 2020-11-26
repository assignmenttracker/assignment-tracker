package com.hanahs.tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {
	
	@FXML
	private Text result;
	
	@FXML
	private void opopop(ActionEvent event) {
		result.setText(result.getText() + ((Button) event.getSource()).getText());
	}

}
