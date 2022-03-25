package com.example.software1project;

import com.example.software1project.Inventory;
import com.example.software1project.Part;
import com.example.software1project.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProdController implements Initializable {

    private Stage stage;
    private Object scene;


    @FXML private TableView<Part> PartsTableView;
    @FXML private TableColumn<Part, Integer> PartsIDColumn;
    @FXML private TableColumn<Part, String> PartsNameColumn;
    @FXML private TableColumn<Part, Integer> PartsInventoryColumn;
    @FXML private TableColumn<Part, Double> PartsCostColumn;

    @FXML private TableView<Part> AssociatedPartsTableView;
    @FXML private TableColumn<Product, Integer> AssociatedPartsIDColumn;
    @FXML private TableColumn<Product, String> AssociatedPartsNameColumn;
    @FXML private TableColumn<Product, Integer> AssociatedPartsInventoryColumn;
    @FXML private TableColumn<Product, Double> AssociatedPartsCostColumn;

    //Add Fields
    @FXML private TextField APRName;
    @FXML private TextField APRInventory;
    @FXML private TextField APRPrice;
    @FXML private TextField APRMaximum;
    @FXML private TextField APRMinimum;

    //Other Buttons/Fields
    @FXML private TextField SearchField;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();

    @FXML public void searchPartButton(ActionEvent event) {
        String term = SearchField.getText();
        ObservableList foundParts = Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            MainController.confirmDialog("No Match", "Unable to locate a Part name with: " + term);
        } else {
            PartsTableView.setItems(foundParts);
        }
    }

    @FXML void addPartToProduct(ActionEvent event) {
        Part selectedPart = PartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }

        else {
            MainController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }

    @FXML
    void deletePartFromProduct(ActionEvent event) {
        Part selectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            MainController.confirmDialog("Deleting Parts","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedParts.remove(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }

        else {
            MainController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    @FXML public void cancelAddProduct(ActionEvent event) throws IOException {
        if (MainController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML void saveProduct(ActionEvent event) throws IOException {

        //Test if valid product
        if (associatedParts.isEmpty()){
            MainController.infoDialog("Input Error", "Please add one ore more parts", "A product must have one or more parts associated with it.");
            return;}

        if (APRName.getText().isEmpty() || APRMinimum.getText().isEmpty() || APRMaximum.getText().isEmpty() || APRMaximum.getText().isEmpty() || APRPrice.getText().isEmpty()) {
            MainController.infoDialog("Input Error", "Cannot have blank fields", "Check all the fields.");
            return;
        }

        Integer inv = Integer.parseInt(this.APRInventory.getText());
        Integer min = Integer.parseInt(this.APRMinimum.getText());
        Integer max = Integer.parseInt(this.APRMaximum.getText());

        if (max < min) {
            MainController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value.");
            return;
        }

        if (inv < min || inv > max) {
            MainController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum");
            return;
        }

        //Add Product
        if (MainController.confirmDialog("Save?", "Would you like to save this part?")) {
            Product product = new Product();
            product.setId(getNewID());
            product.setName(this.APRName.getText());
            product.setStock(Integer.parseInt(this.APRInventory.getText()));
            product.setMinimum(Integer.parseInt(this.APRMinimum.getText()));
            product.setMaximum(Integer.parseInt(this.APRMaximum.getText()));
            product.setPrice(Double.parseDouble(this.APRPrice.getText()));
            product.addAssociatedPart(associatedParts);
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    }


    private int getNewID(){
        int newID = 1;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getId() == newID) {
                newID++;
            }
        }
        return newID;
    }

    public void updatePartTable() {
        PartsTableView.setItems(Inventory.getAllParts());
    }

    private void updateAssociatedPartTable() {
        AssociatedPartsTableView.setItems(associatedParts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        originalParts = Inventory.getAllParts();

        //Columns and Table for un-associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        PartsTableView.setItems(originalParts);

        //Columns and Table for associated parts
        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        AssociatedPartsTableView.setItems(associatedParts);

        updatePartTable();
        updateAssociatedPartTable();
    }
}
