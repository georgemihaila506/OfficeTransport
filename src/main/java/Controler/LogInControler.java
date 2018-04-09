package Controler;

import Domain.Users;
import Service.ServiceException;
import Service.ServiceUser;
import Utils.ExtensionMethods;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LogInControler implements Initializable {
    ServiceUser serviceUser;
    public LogInControler()
    {

    }
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    public void logInHandle()
    {
        serviceUser= ExtensionMethods.getServiceUser();
        Users user=new Users(username.getText(),password.getText());
        try {
            serviceUser.checkUser(user);


            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("MainWindow.fxml"));
            BorderPane borderPane= null;
            try {
                borderPane = loader.load();
                Scene scene=new Scene(borderPane);
                Stage stage=new Stage();
                stage.setScene(scene);
                MainWindowControler mainWindowControler=loader.getController();
                mainWindowControler.setService();
                stage.show();
                username.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ServiceException e) {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Wrong username or password!");
            alert.setContentText("Please try again!");
            alert.show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
