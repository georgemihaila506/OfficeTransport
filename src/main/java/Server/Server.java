package Server;

import Service.ServiceInterface;
import Utils.ExtensionMethods;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class Server {
    public static void main(String[] args)
    {
        Properties serverProps=new Properties();

        try
        {
            serverProps.load(Server.class.getResourceAsStream("server.properties"));
            //serverProps.load(new FileReader("C:\\Users\\George\\ProiectMPP\\src\\main\\resources\\Server\\server.properties"));
            System.out.println("Server properties set.");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties"+e);
            return;
        }
        if(System.getSecurityManager()==null) {
            System.setSecurityManager(new SecurityManager());
        }
        try
        {
            String name= serverProps.getProperty("office.serverID","Office");
            ServiceInterface serviceUser=ExtensionMethods.getServiceUser();
            ServiceInterface stub= (ServiceInterface) UnicastRemoteObject.exportObject(serviceUser,0);
            //Registry registry= LocateRegistry.getRegistry("localhost");
            Registry registry= LocateRegistry.getRegistry();
            System.out.println("Before binding!");
            registry.rebind(name, stub);
            System.out.println("Server bound!");
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
