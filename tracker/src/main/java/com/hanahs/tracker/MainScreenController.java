package com.hanahs.tracker;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

public class MainScreenController {
	@FXML private GridPane calendarGrid;
	@FXML private ListView<AssignmentProvider> accountList;
	@FXML private Label scheduleDescriptionLabel;
	@FXML private ListView<Assignment> scheduleList;
	@FXML Spinner<Integer> scheduleDays;
	private int currentSelectionY = -1;
	private int currentSelectionX = -1;
	private AssignmentManager manager;
	private List<List<Assignment>> schedule;
	
	
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
		scheduleDescriptionLabel.setText("");
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
				label.getProperties().put("date", current);
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
						
						
						if (schedule == null || schedule.size() == 0) return;
						LocalDate selectedDate = (LocalDate) sender.getProperties().get("date");
						scheduleDescriptionLabel.setText(String.format("%s의 과제 스케줄", selectedDate.toString()));
						LocalDate today = LocalDate.now();
						LocalDate endOfSchedule = today.plusDays(schedule.size() - 1);
						List<Assignment> assignments = new ArrayList<>();
						if (today.isAfter(selectedDate)) {
							scheduleList.setItems(FXCollections.observableArrayList(assignments));
							return;
						}
						if (endOfSchedule.isBefore(selectedDate)) {
							scheduleList.setItems(FXCollections.observableArrayList(assignments));
							return;
						}
						
						long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(today,selectedDate);
						List<Assignment> scheduleFactors = schedule.get((int) daysBetween);
						scheduleList.setItems(FXCollections.observableArrayList(scheduleFactors));
					}
				};
				label.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
				calendarGrid.add(label, x, y + 1);
				current = current.plusDays(1);
			}
		}

		accountList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		scheduleList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		IntegerSpinnerValueFactory factory = new IntegerSpinnerValueFactory(1, 30, 7);
		scheduleDays.setValueFactory(factory);

		manager = new AssignmentManager();
	}
	
	private void showErrorAlert(String titleText, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(titleText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	private void updateAccountList() {
		accountList.setItems(FXCollections.observableArrayList(manager.getProviders()));
	}

	@FXML public void addAccountButtonAction() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("AccountSelector.fxml"));
		Parent root = (Parent) loader.load();
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("계정 유형 선택");
		stage.setScene(new Scene(root));
		stage.showAndWait();
		
		String accountType = AccountSelection.getInstance().getAccountType();
		if (accountType.equals("google_classroom")) {
			try {
				manager.getProviders().add(new GoogleClassroomProvider());
				updateAccountList();
			} catch (GeneralSecurityException | IOException e) {
				e.printStackTrace();
				String titleText = "오류 발생";
				String contentText = "계정 추가 중 오류가 발생했습니다.";
				showErrorAlert(titleText, contentText);
			}
		}
	}

	@FXML public void deleteAccountButtonAction() {
		int selected = accountList.getSelectionModel().getSelectedIndex();
		if (selected == -1) return;
		manager.getProviders().remove(selected);
		updateAccountList();
	}

	@FXML public void scheduleAssignmentAction() {
		try {
			int days = Optional.ofNullable((Integer)scheduleDays.getValue()).orElse(0);
			schedule = manager.scheduleAssignments(days, 5);
		} catch(IOException e) {
			e.printStackTrace();
			String titleText = "오류 발생";
			String contentText = "스케줄을 생성하는 과정에서 오류가 발생했습니다.";
			showErrorAlert(titleText, contentText);
		}
	}
}
