import Controler.HomeControler;
import Domain.Cursa;
import Domain.Rezervare;
import Repository.RepoUtils;
import Repository.RepositoryCursa;
import Repository.RepositoryRezervare;
import Service.ServiceOffice;
import Utils.ExtensionMethods;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args)
    {
       // Date date=Date.valueOf(LocalDate.of(2018,3,15));
        //Time time=Time.valueOf(LocalTime.of(14,15));
        //Cursa cursa=new Cursa("Bucuresti",date,time,18);
       // ServiceOffice serviceOffice=ExtensionMethods.getServiceOffice();
        //ServiceOffice serviceOffice1=ExtensionMethods.getServiceOffice();
       // System.out.println(serviceOffice.cautaRezervari(cursa));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("Controler/LogIn.fxml"));
        VBox vBox=loader.load();
        Scene scene=new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    static ServiceOffice  getInstaceProperties(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileReader("bd.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return null;
        }
        // SortingTaskJdbcRepository repo=new SortingTaskJdbcRepository(serverProps);//Repository(new SortingTaskValidator());
        // ObservableTaskRunner runner=new ObservableTaskRunner(new TaskStack());
        RepositoryCursa repositoryCursa=new RepositoryCursa(serverProps);
        RepositoryRezervare repositoryRezervare=new RepositoryRezervare(serverProps);
        ServiceOffice serviceOffice=new ServiceOffice(repositoryCursa,repositoryRezervare);
        return serviceOffice;
    }
}
