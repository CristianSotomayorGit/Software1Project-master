package com.example.software1project;

import com.example.software1project.InHousePart;
import com.example.software1project.Inventory;
import com.example.software1project.OutSourcedPart;
import com.example.software1project.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {

        //Add Parts InHouse
        Inventory.addPart(new InHousePart(1, "Animal Crossing - New Horizons", 59.99, 20, 1, 50, 19));

        //Add Parts OutSourced
        Inventory.addPart(new OutSourcedPart(5, "Octopath Traveler", 39.99, 10, 1, 50, "Square Enix"));

        //Add Products
        Inventory.addProduct(new Product(1, "Nintendo Bundle", 249.99, 10, 1, 50));


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainform.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Hello!");
        mainStage.setScene(scene);
        mainStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
