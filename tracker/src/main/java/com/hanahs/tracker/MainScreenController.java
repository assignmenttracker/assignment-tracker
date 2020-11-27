package com.hanahs.tracker;

import java.time.LocalDate;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class Controller {
	@FXML private GridPane calendarGrid;
	@FXML private ListView accountList;
	@FXML private Label scheduleDescriptionLabel;
	@FXML private ListView scheduleList;
	private int currentSelectionY = -1;
	private int currentSelectionX = -1;
	
	private Node getNodeFromGridPane(int col, int row) {
		for (Node node: calendarGrid.getChildren()) {
			int childCol = Optional.ofNullable(GridPane.getColumnIndex(node)).orElse(-1);
			int childRow = Optional.ofNullable(GridPane.getRowIndex(node)).orElse(-1);
			if (childCol == col && childRow == row) return node;
		}
		System.out.println("Not found");
		return null;
	}
	
	@FXML public void initialize() {
		LocalDate today = LocalDate.now();
		LocalDate current = LocalDate.now();
		for (int y = 0; y < 6; ++y) {
			for (int x = 0; x < 7; ++x) {
				if (current.equals(today) && current.getDayOfWeek().getValue() != x) continue;
				Label label = new Label(String.valueOf(current.getDayOfMonth()));
				label.maxHeight(Double.MAX_VALUE);
				label.setPrefHeight(Double.MAX_VALUE);
				label.maxWidth(Double.MAX_VALUE);
				label.setPrefWidth(Double.MAX_VALUE);
				label.getStyleClass().add("calendar-date");
				label.setId(String.format("label_%d_%d", x, y + 1));
				EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (currentSelectionX != -1) {
							Node lastSelectedNode = getNodeFromGridPane(currentSelectionX, currentSelectionY);
							if (lastSelectedNode instanceof Label) {
								Label lastSelected = (Label)lastSelectedNode;
								lastSelected.getStyleClass().remove("calendar-date-selected");
							}
						}
						Label sender = (Label)event.getSource();
						sender.getStyleClass().add("calendar-date-selected");
						currentSelectionX = GridPane.getColumnIndex(sender);
						currentSelectionY = GridPane.getRowIndex(sender);
					}
				};
				label.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				calendarGrid.add(label, x, y + 1);
				current = current.plusDays(1);
			}
		}
	}

	@FXML public void addAccountButtonAction() {
		System.out.println("Account Add");
	}

	@FXML public void deleteAccountButtonAction() {
		System.out.println("Account Delete");
	}
}
