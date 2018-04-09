package Client;

import Service.ServiceInterface;
import Service.ServiceUser;
import Utils.ExtensionMethods;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class Client extends Application {

        private static String defaultServer="localhost";
        public static void main(String[] args) {

            Properties clientProps=new Properties();
            try {
                clientProps.load(Client.class.getResourceAsStream("client.properties"));
                System.out.println("Client properties set. ");
                clientProps.list(System.out);
            } catch (IOException e) {
                System.err.println("Cannot find client.properties "+e);
                return;
            }

            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            String name=clientProps.getProperty("office.rmi.serverID","Office");
            String serverIP=clientProps.getProperty("office.server.host",defaultServer);
            try {

                Registry registry = LocateRegistry.getRegistry(serverIP);
                ServiceInterface server = (ServiceInterface) registry.lookup(name);
                System.out.println("Obtained a reference to remote office server");
                ServiceUser ctrl= ExtensionMethods.getServiceUser();
                launch(args);

            } catch (Exception e) {
                System.err.println("Office Initialization  exception:"+e);
                e.printStackTrace();
            }

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
}
