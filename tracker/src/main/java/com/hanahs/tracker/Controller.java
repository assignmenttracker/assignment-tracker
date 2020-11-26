package com.hanahs.tracker;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

public class Controller {
	@FXML GridPane calendarGrid;
	@FXML ListView accountList;
	@FXML Label scheduleDescriptionLabel;
	@FXML ListView scheduleList;

	@FXML public void addAccountButtonAction() {
		System.out.println("Account Add");
	}

	@FXML public void deleteAccountButtonAction() {
		System.out.println("Account Delete");
	}
}
