package interfaceApp;

import java.util.HashMap;
import java.util.Map;
import databaseObject.CustomerHomeConnection;
import databaseConnection.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import modelData.Donut;

public class CustomerHomeScene {
	
	Stage stCustDvCO;
    Scene scCustHomeScene;
    BorderPane bpCustHomeDvCO;
    ConnectionDB custConnect;
    VBox chooseDonut, detailDonut, controlsDonut, inputQty;
    HBox split;
    Label greetingLb, activeDonutLb, donutNameLb, donutDescLb, donutPriceLb;
    Spinner<Integer> qtySpinner;
    Button addCartBtn;
    ListView<Donut> donutListView;
    Map<String, Integer> cartList;
    MenuBar menubarCust;
    Menu dashboard, logout;
    MenuItem home, cart, signout;
    Donut selects;

	public void getCustHomeScene(Stage stCustDvCO) {
		this.stCustDvCO = stCustDvCO;
		custConnect = ConnectionDB.getInstance();
		interfaceCustHome();
		positionLayoutCustHome();
		stylingLayoutCustHome();
		componentActionCustHome();
		stCustDvCO.setTitle("Dv.CO | Home(User)");
        stCustDvCO.setScene(scCustHomeScene);
        stCustDvCO.show();
	}
	
	public void interfaceCustHome() {
		bpCustHomeDvCO = new BorderPane();
		
		menubarCust = new MenuBar();
		dashboard = new Menu("Dashboard");
		home = new MenuItem("Home");
		home.setDisable(true);
		cart = new MenuItem("Cart");
		dashboard.getItems().addAll(home, cart);
		
		logout = new Menu("Logout");
		signout = new MenuItem("Logout");
		logout.getItems().add(signout);
		menubarCust.getMenus().addAll(dashboard, logout);

		greetingLb = new Label("Hello, " + Main.userLogin.getUsername());
		activeDonutLb = new Label("Active Donut:");
		
		cartList = new HashMap<>();
		ObservableList<Donut> donutList = FXCollections.observableArrayList();
		donutListView = new ListView<>(donutList);
		getDonutList(donutList);

		donutNameLb = new Label();
        donutDescLb = new Label();
        donutPriceLb = new Label();
        qtySpinner = new Spinner<>(1, 999, 1);
        addCartBtn = new Button("Add to Cart");
        hideDetails();
	}
	
	public void positionLayoutCustHome() {
		donutListView.setPrefWidth(250); 
        donutListView.setMaxWidth(250);
        
        greetingLb.setAlignment(Pos.CENTER_LEFT);
        chooseDonut = new VBox(10, greetingLb, activeDonutLb, donutListView);
        chooseDonut.setAlignment(Pos.TOP_LEFT); 
        
		detailDonut = new VBox(5, donutNameLb, donutDescLb, donutPriceLb);
		detailDonut.setAlignment(Pos.TOP_LEFT);
		
        controlsDonut = new VBox(10, qtySpinner, addCartBtn);
        controlsDonut.setAlignment(Pos.TOP_LEFT);
        
        inputQty = new VBox(15, detailDonut, controlsDonut);
        inputQty.setAlignment(Pos.TOP_LEFT);
        
        split = new HBox(30, chooseDonut, inputQty); 
        split.setAlignment(Pos.TOP_LEFT); 
        
        bpCustHomeDvCO.setTop(menubarCust);
        bpCustHomeDvCO.setCenter(split);
        scCustHomeScene = new Scene(bpCustHomeDvCO, 700, 400);
	}
	
	public void stylingLayoutCustHome() {
		greetingLb.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");
        activeDonutLb.setStyle("-fx-font-size: 14px;");
		donutNameLb.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); 
        donutDescLb.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;"); 
        donutPriceLb.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        chooseDonut.setStyle("-fx-padding: 0 20 20 20; -fx-pref-width: 250;");
        inputQty.setStyle("-fx-padding: 20;");
        split.setStyle("-fx-padding: 10 20 20 20;");
	}
	
	public void componentActionCustHome() {
		cart.setOnAction(e -> {
			CartScene cartScDvCO = new CartScene();
			cartScDvCO.getCartScene(stCustDvCO);
		});

		logout.setOnAction(e -> {
			Main.userLogin = null;
			LoginScene loginScDvCO = new LoginScene();
			loginScDvCO.getLoginScene(stCustDvCO);
		});
		
		donutListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				selects = newValue;
				showDetails(selects);
			} else {
				hideDetails();
			}
		});

		addCartBtn.setOnAction(e -> validationAddToCart());
	}

	private void getDonutList(ObservableList<Donut> donutList) {
		donutList.addAll(CustomerHomeConnection.getDonutList());
	}

	private void validationAddToCart() {
		if (selects == null) {
			showAlertError(Alert.AlertType.ERROR, "Add to Cart Failed", "Please select a donut.");
			return;
		}

		int quantity = qtySpinner.getValue();
		try {
			boolean addedToCart = CustomerHomeConnection.actionAddToCart(selects, Main.userLogin, quantity);

			if (addedToCart) {
			    showAlertSuccess(Alert.AlertType.INFORMATION, "Donut Added to Cart",
			            selects.getName() + " has been added to the cart.");
			} else {
			    showAlertError(Alert.AlertType.ERROR, "Add to Cart Failed", "There was an issue adding the donut to the cart.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void showAlertError(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.show();
	}
	
	public void showAlertSuccess(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("Success");
		alert.setContentText(message);
		alert.show();
	}
	
	private void showDetails(Donut donut) {
		donutNameLb.setText(donut.getName());
		donutDescLb.setText(donut.getDescription());
		donutPriceLb.setText("Rp. " + donut.getPrice());
		donutNameLb.setVisible(true);
		donutDescLb.setVisible(true);
		donutPriceLb.setVisible(true);
		qtySpinner.setVisible(true);
		addCartBtn.setVisible(true);
	}

	private void hideDetails() {
		donutNameLb.setVisible(false);
		donutDescLb.setVisible(false);
		donutPriceLb.setVisible(false);
		qtySpinner.setVisible(false);
		addCartBtn.setVisible(false);
	}
}
