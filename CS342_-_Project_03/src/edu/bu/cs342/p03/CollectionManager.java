package edu.bu.cs342.p03;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import edu.bu.cs342.utilities.UserInput;

public class CollectionManager {

    private CollectionHash<String> collectionHash;

    static enum CollectionTypes {
        OPEN_ADDRESS, CHAINED
    }

    enum CommandList {
        PRINT("p", "Print Hash Table"), ADD("a", "Add an item."), SEARCH("s", "Search for an item."), TRACE_SEARCH(
                "t", "Trace the search for an item."), REMOVE("r",
                "Remove an item from the collection"), EXIT("x", "Exit the collection.");

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

    public CollectionManager(CollectionTypes type) {
        if (type == CollectionTypes.OPEN_ADDRESS) {
            this.collectionHash = new OpenAddressHash<String>();
        } else {
            this.collectionHash = new ChainedHash<String>();
        }
    }

    public static void main(String[] args) {
        UserInput ui = new UserInput(System.in);
        CollectionManager self;
        CollectionTypes collectionType;
        if (ui.readBoolean("Create a chained hash table")) {
            collectionType = CollectionTypes.CHAINED;
            System.out.println("Using a chained hash table");
        } else {
            collectionType = CollectionTypes.OPEN_ADDRESS;
            System.out.println("Using an open-address hash table");
        }
        self = new CollectionManager(collectionType);
        CommandList command = null;
        while (command != CommandList.EXIT) {
            int bucket = -1;
            System.out.println();
            command = CommandList.getCommand(System.in, System.out, System.err);
            switch (command) {
            case PRINT:
                self.collectionHash.showHash(System.out);
                break;
            case ADD:
                if (self.collectionHash.add(ui.readLine("Enter the name of the item"))) {
                    System.out.println("Item added.");
                } else {
                    System.err.println("Unable to add item.");
                }
                break;
            case SEARCH:
                bucket = self.collectionHash.search(ui.readLine("Enter the name of the item"));
                if (bucket == -1) {
                    System.err.println("Item not found.");
                } else {
                    System.out.printf("Found item at bucket %d%n", bucket);
                }
                break;
            case TRACE_SEARCH:
                bucket = self.collectionHash.traceSearch(ui.readLine("Enter the name of the item"),
                        System.out);
                if (bucket == -1) {
                    System.err.println("Item not found.");
                } else {
                    System.out.printf("Found item at bucket %d%n", bucket);
                }
                break;
            case REMOVE:
                if (null == self.collectionHash.delete(ui.readLine("Enter the name of the item"))) {
                    System.err.println("Item not found");
                } else {
                    System.out.println("Item removed.");
                }
            default:
                break;
            }
            System.out.println();
        }
    }
}
