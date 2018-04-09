package Controler;

import Domain.Cursa;
import Service.ServiceException;
import Service.ServiceOffice;
import Utils.ExtensionMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;


public class HomeControler {

    @FXML
    private TableView<Cursa> tableView;
    @FXML
    private TableColumn<Cursa,String> destinationColumn;
    @FXML
    private TableColumn<Cursa,String> dateColumn;
    @FXML
    private TableColumn<Cursa,String> timeColumn;
    @FXML
    private TableColumn<Cursa,Integer> nrColumn;
    private ServiceOffice serviceOffice;
    private ObservableList<Cursa> model=FXCollections.observableArrayList();

    public HomeControler()
    {
    }
    public void setService()
    {
        try {
            serviceOffice= ExtensionMethods.getServiceOffice();
            model.setAll(serviceOffice.getAllTrips());
            tableView.setItems(model);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void initialize()
    {
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Cursa,String>("destinatie"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Cursa,String>("data"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Cursa,String>("ora"));
        nrColumn.setCellValueFactory(new PropertyValueFactory<Cursa,Integer>("nrLocuri"));
    }
}
