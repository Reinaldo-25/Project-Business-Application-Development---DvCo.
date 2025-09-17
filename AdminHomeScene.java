package interfaceApp;

import databaseObject.AdminHomeConnection;
import databaseConnection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import modelData.Donut;

public class AdminHomeScene {
	
	Scene scAdminHomeScene;
    Stage stAdminDvCO;
    ConnectionDB adminCon;
    BorderPane bpAdminHomeDvCO;
    GridPane gpAdminHomeDvCO;
    VBox vbAdminHomeDvCO;
    HBox hbAdminHomeDvCO;

    Label welcomeLb, activeDonutLb, nameLb, descLb, priceLb;
    TextField nameField, priceField;
    TextArea descArea;
    Button addBtn, updateBtn, deleteBtn;
    MenuBar menubarAdmin;
    Menu logout;
    MenuItem logoutItem;
    TableView<Donut> donutTable;
    ObservableList<Donut> donutList;
    Donut selects;

	public void getAdminHomeScene(Stage stAdminDvCO) {
		this.stAdminDvCO = stAdminDvCO;
		adminCon = ConnectionDB.getInstance();
		donutList = FXCollections.observableArrayList();
		interfaceAdminHome();
		positionLayoutAdminHome();
		stylingAdminHome();
		componentActionAdminHome();
        stAdminDvCO.setTitle("Dv.CO | Home (Admin)");
    	stAdminDvCO.setScene(scAdminHomeScene);
    	stAdminDvCO.show();
	}
	
	public void interfaceAdminHome() {
		bpAdminHomeDvCO = new BorderPane();
        gpAdminHomeDvCO = new GridPane();
        vbAdminHomeDvCO = new VBox(10);
        hbAdminHomeDvCO = new HBox(10);
		
		menubarAdmin = new MenuBar();
		logout = new Menu("Logout");
		logoutItem = new MenuItem("Logout");
		logout.getItems().add(logoutItem);
		menubarAdmin.getMenus().add(logout);

		welcomeLb = new Label("Hello, " + Main.userLogin.getUsername());
		activeDonutLb = new Label("Active Donut: ");
		donutTable = new TableView<>();
		setDonutTableView();
		showDonutsList();

		nameLb = new Label("Donut Name");
        descLb = new Label("Donut Description");
        priceLb = new Label("Donut Price");

        nameField = new TextField();
        priceField = new TextField();
        descArea = new TextArea();
        descArea.setPrefHeight(60);

		addBtn = new Button("Add Donut");
		updateBtn = new Button("Update Donut");
		deleteBtn = new Button("Delete Donut");
	}
	
	public void positionLayoutAdminHome() {
		vbAdminHomeDvCO.getChildren().add(donutTable);
        vbAdminHomeDvCO.getChildren().add(gpAdminHomeDvCO);
   
        gpAdminHomeDvCO.setPadding(new Insets(10));
        gpAdminHomeDvCO.setHgap(10);
        gpAdminHomeDvCO.setVgap(5);
        gpAdminHomeDvCO.add(nameLb, 0, 0);
        gpAdminHomeDvCO.add(nameField, 0, 1);
        gpAdminHomeDvCO.add(descLb, 0, 2);
        gpAdminHomeDvCO.add(descArea, 0, 3);
        gpAdminHomeDvCO.add(priceLb, 0, 4);
        gpAdminHomeDvCO.add(priceField, 0, 5);

        HBox buttonLayout = new HBox(10, addBtn, updateBtn, deleteBtn);
        buttonLayout.setPadding(new Insets(15, 0, 0, 0));
        gpAdminHomeDvCO.add(buttonLayout, 0, 6);

        VBox headerLayout = new VBox(5, welcomeLb, activeDonutLb);
        headerLayout.setPadding(new Insets(10));
        
        VBox centerLayout = new VBox(5, headerLayout, donutTable);
        centerLayout.setPadding(new Insets(10));

        donutTable.setPrefWidth(800);
        donutTable.setPrefHeight(250);
        donutTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        VBox tableLayout = new VBox(donutTable);
        tableLayout.setPadding(new Insets(0, 0, 0, 10));
        tableLayout.setAlignment(Pos.TOP_LEFT);

        VBox mainLayout = new VBox(10, headerLayout, tableLayout, gpAdminHomeDvCO);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        bpAdminHomeDvCO.setTop(menubarAdmin);
        bpAdminHomeDvCO.setCenter(mainLayout);
        scAdminHomeScene = new Scene(bpAdminHomeDvCO, 800, 600);
	}
	
	public void stylingAdminHome() {
		nameLb.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        descLb.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        priceLb.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        welcomeLb.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        activeDonutLb.setStyle("-fx-font-size: 14px; -fx-font-weight: normal;");
	}
	
	public void componentActionAdminHome() {
		logoutItem.setOnAction(e -> {
			Main.userLogin = null;
			LoginScene loginScDvCO = new LoginScene();
			loginScDvCO.getLoginScene(stAdminDvCO);
		});
		
		donutTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				selects = newValue;
				outputDonutDetails(selects);
			}
		});
		
		addBtn.setOnAction(e -> handleAddDonut());
		updateBtn.setOnAction(e -> handleUpdateDonut());
		deleteBtn.setOnAction(e -> handleDeleteDonut());
	}

	@SuppressWarnings("unchecked")
	private void setDonutTableView() {
		TableColumn<Donut, String> idColumn = new TableColumn<>("Donut ID");
    	idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Donut, String> nameColumn = new TableColumn<>("Donut Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Donut, String> descColumn = new TableColumn<>("Donut Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<Donut, Integer> priceColumn = new TableColumn<>("Donut Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        donutTable.getColumns().addAll(idColumn, nameColumn, descColumn, priceColumn);
        donutTable.setItems(donutList);
	}

	private void showDonutsList() {
		donutList.clear();
	    ObservableList<Donut> donutsShownList = AdminHomeConnection.getDonutList();
	    donutList.addAll(donutsShownList);
	}

	private void handleAddDonut() {
		String name = nameField.getText();
		String description = descArea.getText();
		String price = priceField.getText();

		if (name.isEmpty()) {
			showAlertError(AlertType.ERROR, "Invalid Request", "Donut name cannot be empty!");
			return;
		}

		if (description.isEmpty()) {
			showAlertError(AlertType.ERROR, "Invalid Request", "Donut description cannot be empty!");
			return;
		}

		if (price.isEmpty()) {
			showAlertError(AlertType.ERROR, "Invalid Request", "Donut price cannot be empty!");
			return;
		}

		int prices;
		try {
			prices = Integer.parseInt(price);
		} catch (NumberFormatException e) {
			showAlertError(AlertType.ERROR, "Invalid Request", "Donut price must be a valid number!");
			return;
		}

		Donut addDonut = new Donut(AdminHomeConnection.generateDonutId(), name, description, prices);
	    boolean donutSuccessAdded = AdminHomeConnection.addDonut(addDonut);

	    if (donutSuccessAdded) {
	        donutList.add(addDonut);
	        showAlertMessage(AlertType.INFORMATION, "Donut Added Successfully", "Donut Added Successfully");
	        clearFields();
	    } else {
	        showAlertError(AlertType.ERROR, "Error Adding Donut", "There was an issue adding the donut.");
	    }
	}
	
	private void handleUpdateDonut() {
		if (selects == null) {
			showAlertError(AlertType.ERROR, "Invalid Request", "No donut selected!");
			return;
		}

		String name = nameField.getText();
		String description = descArea.getText();
		String price = priceField.getText();

		if (name.isEmpty() || description.isEmpty() || price.isEmpty()) {
			showAlertError(AlertType.ERROR, "Invalid Request", "All fields must be filled!");
			return;
		}

		int prices;
		try {
			prices = Integer.parseInt(price);
		} catch (NumberFormatException e) {
			showAlertError(AlertType.ERROR, "Invalid Request", "Donut price must be a valid number!");
			return;
		}

		selects.setName(name);
	    selects.setDescription(description);
	    selects.setPrice(prices);

	    boolean donutSuccessUpdated = AdminHomeConnection.updateDonut(selects);

	    if (donutSuccessUpdated) {
	        donutTable.refresh();
	        showAlertMessage(AlertType.INFORMATION, "Donut Updated Successfully", "Donut Updated Successfully");
	        clearFields();
	    } else {
	        showAlertError(AlertType.ERROR, "Error Updating Donut", "There was an issue updating the donut.");
	    }
	}

	private void handleDeleteDonut() {
	    if (selects == null) {
	    	showAlertError(AlertType.ERROR, "Invalid Request", "No donut selected!");
	        return;
	    }

	    boolean donutSuccessDeleted = AdminHomeConnection.deleteDonut(selects.getId());

	    if (donutSuccessDeleted) {
	        donutList.remove(selects);
	        showAlertMessage(AlertType.INFORMATION, "Donut Deleted Successfully", "Donut Deleted Successfully");
	        clearFields();
	    } else {
	        showAlertError(AlertType.ERROR, "Error Deleting Donut", "There was an issue deleting the donut.");
	    }
	}
	
	public void showAlertError(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.show();
	}
	
	public void showAlertMessage(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("Message");
		alert.setContentText(message);
		alert.show();
	}
	
	private void outputDonutDetails(Donut donut) {
		nameField.setText(donut.getName());
		descArea.setText(donut.getDescription());
		priceField.setText(String.valueOf(donut.getPrice()));
	}
	
	private void clearFields() {
		nameField.clear();
		descArea.clear();
		priceField.clear();
		selects = null;
	}
}
