package fr.isep;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            LogServer logServer = new LogServerImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("LogServer", logServer);
            System.out.println("Log Server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
