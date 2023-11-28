package fr.isep;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LogServerImpl extends UnicastRemoteObject implements LogServer {
    private MerkleTree merkleTree;
    private List<String> logEntries;

    public LogServerImpl() throws RemoteException {
        logEntries = new ArrayList<>();
        merkleTree = new MerkleTree(logEntries);
    }

    @Override
    public String getCurrentRootHash() throws RemoteException {
        return bytesToHex(merkleTree.getRoot().getHash());
    }

    @Override
    public void appendEvent(String event) throws RemoteException {
        logEntries.add(event);
        merkleTree = new MerkleTree(logEntries);
    }

    @Override
    public List<byte[]> genPath(String event) throws RemoteException {
        // Assuming MerkleTree class has a method to generate audit path
        return merkleTree.genPath(event);
    }

    @Override
    public List<byte[]> genProof(int size) throws RemoteException {
        // Assuming MerkleTree class has a method to generate consistency proof
        return merkleTree.genProof(size);
    }

    // Utility method to convert byte array to hex string
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
