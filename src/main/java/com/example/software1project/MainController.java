package com.example.software1project;

import com.example.software1project.Inventory;
import com.example.software1project.Part;
import com.example.software1project.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    //Buttons


    //Table views
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML
    private TableColumn<Part, Double> partCostColumn;
    @FXML
    private TextField partSearchArea;

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInvLevelColumn;
    @FXML
    private TableColumn<Product, Double> productCostColumn;
    @FXML
    private TextField productSearchArea;

    private Parent scene;

    public void addPartButtonPushed(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addpart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void modifyPartButtonPushed(ActionEvent event) {
        try {
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifypart.fxml"));
            scene = loader.load();
            ModifyPartController controller = loader.getController();
            controller.setPart(selectedPart);
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void modifyProductButtonPushed(ActionEvent event) {
        try {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyproduct.fxml"));
            scene = loader.load();
            ModProdController controller = loader.getController();
            controller.setProduct(selectedProduct);
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void searchPartButton(ActionEvent event) {
        String term = partSearchArea.getText();
        ObservableList foundParts = Inventory.lookupPart(term);
        if (foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a part name with: " + term);
            alert.showAndWait();
        } else {
            partsTableView.setItems(foundParts);
        }
    }

    public void searchProductButton(ActionEvent event) {
        String term = productSearchArea.getText();
        ObservableList foundProducts = Inventory.lookupProduct(term);
        if (foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a product name with: " + term);
            alert.showAndWait();
        } else {
            productsTableView.setItems(foundProducts);
        }
    }

    public void deletePartButton(ActionEvent event) {
        if (partsTableView.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Part Selected", "Please choose part from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?")) {
            int selectedPart = partsTableView.getSelectionModel().getSelectedIndex();
            partsTableView.getItems().remove(selectedPart);
        }
    }

    public void deleteProductButton(ActionEvent event) {
        if (productsTableView.getSelectionModel().isEmpty()) {
            infoDialog("Warning!", "No Product Selected", "Please choose product from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this product?")) {
            int selectedPart = productsTableView.getSelectionModel().getSelectedIndex();
            productsTableView.getItems().remove(selectedPart);
        }
    }

    static boolean confirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    static void infoDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void addProductButtonPushed(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    public void exitButton(ActionEvent event) throws IOException {
        confirmDialog("Exit", "Are you sure you would like to exit the program?");
        {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));


    }

}