package interfaceApp;

import databaseObject.CartConnection;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import modelData.CartItems;

public class CartScene {
	
	Scene scCartScene;
	Stage stCartDvCO;
	BorderPane bpCartScene;
	VBox vboxCartScene;
	ConnectionDB cartCon;
	
	Label yourcartLabel, totalPriceLabel;
	TableView<CartItems> cartTable;
    Button checkoutBtn;
    MenuBar menubarCart;
    Menu dashboard, logout;
    MenuItem home, cart, loggingout;
    ObservableList<CartItems> cartItem;

	public void getCartScene(Stage stCartDvCO) {
		this.stCartDvCO = stCartDvCO;
		cartCon = ConnectionDB.getInstance();
		cartItem = FXCollections.observableArrayList();
		interfaceCart();
		positionLayoutCart();
		stylingCart();
		componentActionCart();
        stCartDvCO.setTitle("Dv.CO | Cart");
        stCartDvCO.setScene(scCartScene);
        stCartDvCO.show();
	}
	
	public void interfaceCart() {
		bpCartScene = new BorderPane();
		
		menubarCart = new MenuBar();
		dashboard = new Menu("Dashboard");
		logout = new Menu("Logout");

		home = new MenuItem("Home");
		cart = new MenuItem("Cart");
		loggingout = new MenuItem("Logout");

		cart.setDisable(true);
		dashboard.getItems().addAll(home, cart);
		logout.getItems().add(loggingout);
		menubarCart.getMenus().addAll(dashboard, logout);

		yourcartLabel = new Label("Your Cart");
		totalPriceLabel = new Label("Total Price: Rp 0");

		cartTable = new TableView<>();
		setCartTableView();
		getCartItemList();

		checkoutBtn = new Button("Checkout");
	}
	
	public void positionLayoutCart() {
		vboxCartScene = new VBox(10);
        vboxCartScene.getChildren().addAll(yourcartLabel, cartTable, totalPriceLabel, checkoutBtn);
		
        vboxCartScene.setAlignment(Pos.CENTER);
        vboxCartScene.setPadding(new Insets(20));
        vboxCartScene.setMaxWidth(500);
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        bpCartScene.setTop(menubarCart);
        bpCartScene.setCenter(vboxCartScene);
        BorderPane.setAlignment(cartTable, Pos.CENTER);
        
        scCartScene = new Scene(bpCartScene, 800, 600); 
	}
	
	public void stylingCart() {
		yourcartLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px 0;");
    	totalPriceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        checkoutBtn.setStyle("-fx-font-size: 14px;");
	}
	
	public void componentActionCart() {
		home.setOnAction(e -> {
			CustomerHomeScene custScDvCO = new CustomerHomeScene();
			custScDvCO.getCustHomeScene(stCartDvCO);
		});

		logout.setOnAction(e -> {
			Main.userLogin = null;
			LoginScene loginScDvCO = new LoginScene();
			loginScDvCO.getLoginScene(stCartDvCO);
		});
		
		checkoutBtn.setOnAction(e -> actionHandleCheckout());
	}

	@SuppressWarnings("unchecked")
	private void setCartTableView() {
		TableColumn<CartItems, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<CartItems, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<CartItems, Integer> qtyColumn = new TableColumn<>("Quantity");
		qtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		TableColumn<CartItems, Integer> totalColumn = new TableColumn<>("Total");
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		
        cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		cartTable.getColumns().addAll(nameColumn, priceColumn, qtyColumn, totalColumn);
		cartTable.setItems(cartItem);
	}

	private void getCartItemList() {
		cartItem.clear();
	    cartItem.addAll(CartConnection.getCartItems(Main.userLogin.getId()));

	    int totalPrice = 0;
	    for (CartItems item : cartItem) {
	        totalPrice += item.getTotal(); 
	    }

	    totalPriceLabel.setText("Total Price: Rp. " + totalPrice);
	}

	private void actionHandleCheckout() {
		CartItems selectedCartItem = cartTable.getSelectionModel().getSelectedItem();
		if (selectedCartItem == null) {
	        showAlertError(AlertType.ERROR, "Checkout Failed", "No item selected for checkout.");
	        return;
	    }
	    
	    boolean checkoutSuccess = CartConnection.checkoutCart(selectedCartItem, Main.userLogin.getId());
	    if (checkoutSuccess) {
	        showAlertMessage(AlertType.INFORMATION, "Checkout Success", "Checkout Successful!");
	        cartItem.remove(selectedCartItem);
	        getCartItemList(); 
	    } else {
	        showAlertError(AlertType.ERROR, "Checkout Failed", "There was an error during checkout.");
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
}
