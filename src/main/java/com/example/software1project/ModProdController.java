package com.example.software1project;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModProdController implements Initializable {

    private Stage stage;
    private Object scene;

    //Un Associated Parts Table
    @FXML private TableView<Part> PartsTableView;
    @FXML private TableColumn<Part, Integer> PartsIDColumn;
    @FXML private TableColumn<Part, String> PartsNameColumn;
    @FXML private TableColumn<Part, Integer> PartsInventoryColumn;
    @FXML private TableColumn<Part, Double> PartsCostColumn;

    //Associated Parts Table
    @FXML private TableView<Part> AssociatedPartsTableView;
    @FXML private TableColumn<Product, Integer> AssociatedPartsIDColumn;
    @FXML private TableColumn<Product, String> AssociatedPartsNameColumn;
    @FXML private TableColumn<Product, Integer> AssociatedPartsInventoryColumn;
    @FXML private TableColumn<Product, Double> AssociatedPartsCostColumn;

    //Modify Fields
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField ID;

    //Other Buttons/Fields
    @FXML private TextField SearchField;
    private Product selectedProduct;
    private Product modProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;

    public void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = com.example.software1project.Inventory.getAllProducts().indexOf(selectedProduct);
        ID.setText(Integer.toString(selectedProduct.getId()));
        Name.setText(selectedProduct.getName());
        Inventory.setText(Integer.toString(selectedProduct.getStock()));
        Price.setText(Double.toString(selectedProduct.getPrice()));
        Maximum.setText(Integer.toString(selectedProduct.getMaximum()));
        Minimum.setText(Integer.toString(selectedProduct.getMinimum()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
    }

    @FXML public void searchPartButton(ActionEvent event) {
        String term = SearchField.getText();
        ObservableList foundParts = com.example.software1project.Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Part name with: " + term);
            alert.showAndWait();
        } else {
            PartsTableView.setItems(foundParts);
        }
    }

    @FXML public void modifyProductCancel(ActionEvent event) throws IOException {
        if (MainController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML void modifyProductSave(ActionEvent event) throws IOException {
        int productInventory = Integer.parseInt(Inventory.getText());
        int productMinimum = Integer.parseInt(Minimum.getText());
        int productMaximum = Integer.parseInt(Maximum.getText());
        if(MainController.confirmDialog("Save?", "Would you like to save this part?"))
            if(productMaximum < productMinimum) {
                MainController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(productInventory < productMinimum || productInventory > productMaximum) {
                MainController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else {
                this.modProduct = selectedProduct;
                selectedProduct.setId(Integer.parseInt(ID.getText()));
                selectedProduct.setName(Name.getText());
                selectedProduct.setStock(Integer.parseInt(Inventory.getText()));
                selectedProduct.setPrice(Double.parseDouble(Price.getText()));
                selectedProduct.setMaximum(Integer.parseInt(Maximum.getText()));
                selectedProduct.setMinimum(Integer.parseInt(Minimum.getText()));
                selectedProduct.getAllAssociatedParts().clear();
                selectedProduct.addAssociatedPart(associatedParts);
                com.example.software1project.Inventory.updateProduct(productID, selectedProduct);

                //Back to Main Screen
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
    }

    @FXML void addPartToProduct(ActionEvent event) {
        Part selectedPart = PartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
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
            updateAssociatedPartTable();
        }

        else {
            MainController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    public void updatePartTable() {
        PartsTableView.setItems(com.example.software1project.Inventory.getAllParts());
    }

    private void updateAssociatedPartTable() {
        AssociatedPartsTableView.setItems(associatedParts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Columns and Table for un-associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        PartsTableView.setItems(com.example.software1project.Inventory.getAllParts());

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
