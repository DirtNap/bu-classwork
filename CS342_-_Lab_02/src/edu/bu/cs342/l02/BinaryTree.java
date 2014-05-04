package edu.bu.cs342.l02;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class BinaryTree {

    class Node {
        private final String data;
        private Node right;
        private Node left;

        public Node(String data) {
            if (null == data) {
                throw new IllegalArgumentException();
            }
            this.data = data;
        }

        public String getData() {
            return this.data;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        @Override
        public String toString() {
            return this.getData().toString();
        }

    }

    private Node root;

    public void destroy() {
        this.root = null;
    }

    private void insert(String item, Node node) {
        int comp = node.getData().compareToIgnoreCase(item);
        if (comp > 0) {
            if (null == node.getLeft()) {
                node.setLeft(new Node(item));
            } else {
                this.insert(item, node.getLeft());
            }
        }
        if (comp < 0) {
            if (null == node.getRight()) {
                node.setRight(new Node(item));
            } else {
                this.insert(item, node.getRight());
            }
        }

    }

    public void insert(String item) {
        if (null == item) {
            return;
        }
        if (null == this.root) {
            this.root = new Node(item);
        } else {
            this.insert(item, this.root);
        }
    }

    public boolean find(String item, Node node) {
        int comp = node.getData().compareToIgnoreCase(item);
        if (comp > 0) {
            if (null == node.getLeft()) {
                return false;
            } else {
                return this.find(item, node.getLeft());
            }
        } else if (comp < 0) {
            if (null == node.getRight()) {
                return false;
            } else {
                return this.find(item, node.getRight());
            }
        } else {
            return true;
        }
    }

    public boolean find(String item) {
        if (null == this.root || null == item) {
            return false;
        } else {
            return this.find(item, this.root);
        }
    }

    public boolean delete(String item) {
        if (null == this.root || null == item) {
            return false;
        } else {
            return this.delete(item, this.root, null);
        }
    }

    private boolean delete(String item, Node node, Node parent) {
        int comp = node.getData().compareToIgnoreCase(item);
        if (comp > 0) {
            if (null == node.getLeft()) {
                return false;
            } else {
                return this.delete(item, node.getLeft(), node);
            }
        } else if (comp < 0) {
            if (null == node.getRight()) {
                return false;
            } else {
                return this.delete(item, node.getRight(), node);
            }
        } else {
            comp = 0;
            if (null != parent) {
                comp = parent.getData().compareToIgnoreCase(node.getData());
            }
            Node newNode = null;
            if (node.getLeft() != null) {
                newNode = new Node(node.getLeft().getData());
            } else if (node.getRight() != null) {
                newNode = new Node(node.getRight().getData());
            }
            if (null != newNode) {
                this.rebalance(node.getLeft(), newNode);
                this.rebalance(node.getRight(), newNode);
            }
            if (comp > 0) {
                // Came from the left
                parent.setLeft(newNode);
            } else if (comp < 0) {
                parent.setRight(newNode);
            } else {
                this.root = newNode;
            }
            return true;
        }
    }

    private void rebalance(Node tree, Node root) {
        this.insert(tree.getData(), root);
        if (null != tree.getLeft()) {
            this.rebalance(tree.getLeft(), root);
        }
        if (null != tree.getRight()) {
            this.rebalance(tree.getRight(), root);
        }
    }

    private BinaryTree() {
        // TODO Auto-generated constructor stub
    }

    public static BinaryTree create() {
        return new BinaryTree();
    }

    private void uglyPrint(Node rt) {
        if (rt == null)
            return;

        System.out.println(rt.getData());

        uglyPrint(rt.getLeft());
        uglyPrint(rt.getRight());

    }

    private void prettyPrint(Node rt) {
        if (rt == null)
            return;

        prettyPrint(rt.getLeft());
        System.out.println(rt.getData());

        prettyPrint(rt.getRight());

    }

    public void print() {
        this.uglyPrint(this.root);
    }

    public void printSort() {
        this.prettyPrint(this.root);
    }

    enum CommandList {
        CREATE("c", "Create a tree"), FIND("f", "Find an item."), PRINT("p", "Print the tree."), PRINT_SORT(
                "s", "Print the sorted tree."), DELETE("d", "Delete an item."), INSERT("i",
                "Insert an item."), DESTROY("r", "Reset the existing tree."), EXIT("x",
                "Exit the collection.");

        private final String selector;
        private final String descriptor;

        CommandList(String selector, String descriptor) {
            this.selector = selector.toUpperCase();
            this.descriptor = descriptor;
        }

        public String getSelector() {
            return this.selector;
        }

        public String getDescriptor() {
            return this.descriptor;
        }

        public static void printCommands(PrintStream output) {
            for (CommandList command : CommandList.values()) {
                output.printf("%s\t%s%n", command.getSelector(), command.getDescriptor());
            }
            System.out.println();
        }

        public static CommandList getCommand(InputStream in, PrintStream output, PrintStream error) {
            CommandList result = null;
            CommandList.printCommands(output);
            @SuppressWarnings("resource")
            Scanner input = new Scanner(in);
            while (null == result) {
                output.print("Enter the letter corresponding to your choice:\t");
                String choice = input.next().trim().toUpperCase();
                for (CommandList command : CommandList.values()) {
                    if (command.getSelector().equals(choice)) {
                        result = command;
                    }
                }
                if (null == result) {
                    error.println("Invalid option.");
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        CommandList result = null;
        BinaryTree tree = null;
        while (result != CommandList.EXIT) {
            result = CommandList.getCommand(System.in, System.out, System.err);
            @SuppressWarnings("resource")
            Scanner input = new Scanner(System.in);
            String in = "";
            switch (result) {
            case CREATE:
                tree = BinaryTree.create();
                break;
            case DESTROY:
                tree.destroy();
                break;
            case PRINT:
                tree.print();
                break;
            case PRINT_SORT:
                tree.printSort();
                break;
            case INSERT:
                System.out.print("Enter a new item");
                in = input.nextLine();
                tree.insert(in);
                break;
            case DELETE:
                System.out.print("Enter an item");
                in = input.nextLine();
                tree.delete(in);
                break;
            case FIND:
                System.out.print("Enter an item");
                in = input.nextLine();
                if (tree.find(in)) {
                    System.out.println("Found!");
                } else {
                    System.out.println("Not Found!");
                }
                break;
            default:
                break;
            }
        }
    }
}
