package fr.isep;

import fr.isep.MerkleNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MerkleTree {
    private MerkleNode root;

    public MerkleTree(List<String> data) {
        if (data.isEmpty()) {
            // Handle the empty case, perhaps set the root to null or a default value
            this.root = null;
        } else {
            List<MerkleNode> nodes = data.stream().map(MerkleNode::new).collect(Collectors.toList());
            while (nodes.size() > 1) {
                List<MerkleNode> internalNodes = new ArrayList<>();
                for (int i = 0; i < nodes.size(); i += 2) {
                    MerkleNode left = nodes.get(i);
                    MerkleNode right = (i + 1 < nodes.size()) ? nodes.get(i + 1) : null;
                    internalNodes.add(new MerkleNode(left, right));
                }
                nodes = internalNodes;
            }
            this.root = nodes.get(0);
        }
    }

    // Getter for root
    public MerkleNode getRoot() {
        return root;
    }

    // Add methods for genPath, genProof, etc.
    public List<byte[]> genPath(String data) {
        MerkleNode targetLeaf = new MerkleNode(data);
        List<byte[]> path = new ArrayList<>();
        genPathHelper(this.root, targetLeaf, path);
        return path;
    }

    private boolean genPathHelper(MerkleNode currentNode, MerkleNode targetLeaf, List<byte[]> path) {
        if (currentNode == null) {
            return false;
        }
        if (currentNode.equals(targetLeaf)) {
            return true;
        }
        if (genPathHelper(currentNode.getLeft(), targetLeaf, path) || genPathHelper(currentNode.getRight(), targetLeaf, path)) {
            path.add(currentNode.getHash());
            return true;
        }
        return false;
    }
    public List<byte[]> genProof(int size) {
        List<byte[]> proof = new ArrayList<>();
        genProofHelper(this.root, size, getTreeSize(this.root), proof);
        return proof;
    }

    private void genProofHelper(MerkleNode node, int size, int totalSize, List<byte[]> proof) {
        if (node == null || size == totalSize || size == 0) {
            return;
        }

        int leftTreeSize = getTreeSize(node.getLeft());
        if (size <= leftTreeSize) {
            if (node.getRight() != null) {
                proof.add(node.getRight().getHash());
            }
            genProofHelper(node.getLeft(), size, leftTreeSize, proof);
        } else {
            proof.add(node.getLeft().getHash());
            genProofHelper(node.getRight(), size - leftTreeSize, totalSize - leftTreeSize, proof);
        }
    }

    private int getTreeSize(MerkleNode node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1; // This is a leaf node
        }
        return getTreeSize(node.getLeft()) + getTreeSize(node.getRight());
    }
}
