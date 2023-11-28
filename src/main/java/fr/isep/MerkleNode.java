package fr.isep;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MerkleNode {
    private byte[] hash;
    private MerkleNode left;
    private MerkleNode right;

    // Constructor for leaf node
    public MerkleNode(String data) {
        this.hash = computeHash(prependByte(data.getBytes(), (byte) 0x00));
        this.left = null;
        this.right = null;
    }

    // Constructor for internal node
    public MerkleNode(MerkleNode left, MerkleNode right) {
        this.left = left;
        this.right = right;
        this.hash = computeHash(prependByte(concatenate(left.getHash(), right.getHash()), (byte) 0x01));
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public MerkleNode getLeft() {
        return left;
    }

    public void setLeft(MerkleNode left) {
        this.left = left;
    }

    public MerkleNode getRight() {
        return right;
    }

    public void setRight(MerkleNode right) {
        this.right = right;
    }

    // Utility method to compute SHA-256 hash
    private byte[] computeHash(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to concatenate byte arrays
    private byte[] concatenate(byte[] a, byte[] b) {
        byte[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    // Utility method to prepend a byte to a byte array
    private byte[] prependByte(byte[] array, byte b) {
        byte[] result = new byte[array.length + 1];
        result[0] = b;
        System.arraycopy(array, 0, result, 1, array.length);
        return result;
    }
}

