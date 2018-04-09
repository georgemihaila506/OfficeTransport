package Controler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class MainWindowControler {


    public MainWindowControler()
    {
    }
    @FXML
    AnchorPane hBox=null;
    @FXML
    public void initialize()
    {

    }
    public void setService()
    {
        try {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("Home.fxml"));
            hBox.getChildren().setAll((AnchorPane) loader.load());
            HomeControler homeControler=loader.getController();
            homeControler.setService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void searchHandle()
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("Search.fxml"));
        try {
            hBox.getChildren().setAll((AnchorPane) loader.load());
            SearchControler searchCotroler=loader.getController();
            searchCotroler.setService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void reserveHandle()
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("Reserve.fxml"));
        try {
            hBox.getChildren().setAll((AnchorPane) loader.load());
            ReserveControler reserveControler=loader.getController();
            reserveControler.setService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void singoutHandle()
    {
        Stage stage=(Stage)hBox.getScene().getWindow();
        stage.close();
    }
}
