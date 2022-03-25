package com.example.software1project;

import com.example.software1project.InHousePart;
import com.example.software1project.Inventory;
import com.example.software1project.OutSourcedPart;
import com.example.software1project.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    @FXML private RadioButton outsourcedPartRadioButton;
    @FXML private RadioButton inHousePartRadioButton;
    @FXML private Label inHousePartOrOutsourcedPart;
    @FXML private TextField idTextField;
    @FXML private TextField NameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maximumTextField;
    @FXML private TextField minimumTextField;
    @FXML private TextField companyNameOrMachineIdTextField;

    private Stage stage;
    private Object scene;
    public Part selectedPart;
    private int partId;

    public void changeLabelName()
    {
        if (outsourcedPartRadioButton.isSelected())
            this.inHousePartOrOutsourcedPart.setText("Company Name");
        else
            this.inHousePartOrOutsourcedPart.setText("Machine ID");
    }

    public void setPart(Part selectedPart) {
        this.selectedPart = selectedPart;
        partId = com.example.software1project.Inventory.getAllParts().indexOf(selectedPart);
        idTextField.setText(Integer.toString(selectedPart.getId()));
        NameTextField.setText(selectedPart.getName());
        inventoryTextField.setText(Integer.toString(selectedPart.getStock()));
        priceTextField.setText(Double.toString(selectedPart.getPrice()));
        maximumTextField.setText(Integer.toString(selectedPart.getMaximum()));
        minimumTextField.setText(Integer.toString(selectedPart.getMinimum()));
        if(selectedPart instanceof InHousePart){
            InHousePart ih = (InHousePart) selectedPart;
            inHousePartRadioButton.setSelected(true);
            this.inHousePartOrOutsourcedPart.setText("Machine ID");
            companyNameOrMachineIdTextField.setText(Integer.toString(ih.getId()));
        }
        else{
            OutSourcedPart os = (OutSourcedPart) selectedPart;
            outsourcedPartRadioButton.setSelected(true);
            this.inHousePartOrOutsourcedPart.setText("Company Name");
            companyNameOrMachineIdTextField.setText(os.getCompanyName());
        }
    }

    @FXML void onSaveButtonPushed(ActionEvent event) throws IOException {
        int partInventory = Integer.parseInt(inventoryTextField.getText());
        int partMin = Integer.parseInt(minimumTextField.getText());
        int partMax = Integer.parseInt(maximumTextField.getText());
        if (MainController.confirmDialog("Save?", "Would you like to save this part?"))
            if (partMax < partMin) {
                MainController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value.");
            }
        else if (partInventory < partMin || partInventory > partMax) {
                MainController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum");
            }
        else {
                int id = Integer.parseInt(idTextField.getText());
                String name = NameTextField.getText();
                double price = Double.parseDouble(priceTextField.getText());
                int stock = Integer.parseInt(inventoryTextField.getText());
                int min = Integer.parseInt(minimumTextField.getText());
                int max = Integer.parseInt(maximumTextField.getText());
                if (inHousePartRadioButton.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(companyNameOrMachineIdTextField.getText());
                        InHousePart temp = new InHousePart(id, name, price, stock, min, max, machineID);
                        com.example.software1project.Inventory.updatePart(partId, temp);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    }
                    catch (NumberFormatException e){
                        MainController.infoDialog("Input Error", "Check Machine ID ", "Machine ID can only contain numbers 0-9");
                    }
                }
                else {
                    String companyName = companyNameOrMachineIdTextField.getText();
                    OutSourcedPart temp = new OutSourcedPart(id, name, price, stock, min, max, companyName);
                    com.example.software1project.Inventory.updatePart(partId, temp);
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
    }
    @FXML public void onCancelButtonPushed(ActionEvent event) throws IOException {
        if(MainController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
