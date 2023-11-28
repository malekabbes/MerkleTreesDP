package fr.isep;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Auditor {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            LogServer logServer = (LogServer) registry.lookup("LogServer");

            // Example event verification
            String event = "ExampleEvent";
            boolean isEventValid = verifyEvent(logServer, event);
            System.out.println("Event '" + event + "' is " + (isEventValid ? "valid" : "invalid") + " in the log.");
        } catch (Exception e) {
            System.err.println("Auditor exception: " + e.toString());
            e.printStackTrace();
        }
    }

    // Method to verify if an event is part of the Merkle Tree
    private static boolean verifyEvent(LogServer logServer, String event) throws Exception {
        List<byte[]> auditPath = logServer.genPath(event);
        return !auditPath.isEmpty();
    }
}
