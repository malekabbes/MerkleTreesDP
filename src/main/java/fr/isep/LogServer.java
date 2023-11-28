package fr.isep;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LogServer extends Remote {
    String getCurrentRootHash() throws RemoteException;
    void appendEvent(String event) throws RemoteException;

    // Generates an audit path for a given event
    List<byte[]> genPath(String event) throws RemoteException;

    // Generates a consistency proof for the current tree over a previous tree size
    List<byte[]> genProof(int size) throws RemoteException;
}
