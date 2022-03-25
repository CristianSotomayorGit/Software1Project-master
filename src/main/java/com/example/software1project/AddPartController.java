package com.example.software1project;

import com.example.software1project.InHousePart;
import com.example.software1project.Inventory;
import com.example.software1project.OutSourcedPart;
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
import com.example.software1project.Inventory.*;

import static com.example.software1project.Inventory.getAllParts;

public class AddPartController implements Initializable {
    @FXML private RadioButton outsourced;
    @FXML private Label inHousePartOrOutsourcedPart;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maximumTextField;
    @FXML private TextField minimumTextField;
    @FXML private TextField companyNameOrMachineIDTextField;

    private Stage stage;
    private Object scene;


    public void radioButton()
    {
        if (outsourced.isSelected())
            this.inHousePartOrOutsourcedPart.setText("Company Name");
        else
            this.inHousePartOrOutsourcedPart.setText("Machine ID");
    }

    @FXML public void onCancelButtonPushed(ActionEvent event) throws IOException {
        if(MainController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("mainform.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        }
    }
    //save part
    @FXML public void onSaveButtonPushed(ActionEvent event) {
        try {
            int partInventory = Integer.parseInt(inventoryTextField.getText());
            int partMin = Integer.parseInt(minimumTextField.getText());
            int partMax = Integer.parseInt(maximumTextField.getText());
            if(MainController.confirmDialog("Save?", "Would you like to save this part?"))
                if(partMax < partMin) {
                    MainController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
                }
                else if(partInventory < partMin || partInventory> partMax) {
                    MainController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
                }
                else{
                    int newID = getNewID();
                    String name = nameTextField.getText();
                    int stock = partInventory;
                    double price = Double.parseDouble(priceTextField.getText());
                    int min = partMin;
                    int max = partMax;
                    if (outsourced.isSelected()) {
                        String companyName = companyNameOrMachineIDTextField.getText();
                        OutSourcedPart temp = new OutSourcedPart(newID, name, price, stock, min, max, companyName);
                        com.example.software1project.Inventory.addPart(temp);
                    } else {
                        int machineID = Integer.parseInt(companyNameOrMachineIDTextField.getText());
                        InHousePart temp = new InHousePart(newID, name, price, stock, min, max, machineID);
                        com.example.software1project.Inventory.addPart(temp);
                    }
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("mainform.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
        }
        catch (Exception e){
            MainController.infoDialog("Input Error", "Error in adding part", "Check fields for correct input" );
        }
    }

    public static int getNewID(){
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
