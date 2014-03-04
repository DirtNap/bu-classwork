package edu.bu.cs342.p01;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Contacts {

    private ContactList contactList;

    enum CommandList {
        PRINT("p", "Print List"), ADD("a", "Add a Contact"), NAME_SEARCH("s", "Search By Name"), EMAIL_SEARCH(
                "e", "Search By Email"), DELETE("d", "Delete an Entry"), WRITE("w",
                "Write the List to a File"), RESTORE("r", "Restore the List from a File"), EXIT(
                "x", "Exit");

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

    public Contacts() {
        this.contactList = new ContactList();
    }

    public static void main(String[] args) {
        Contacts self = new Contacts();
        self.banner();
        System.exit(self.run());
    }

    public int run() {
        Iterable<ContactEntry> dataset = null;
        boolean exit = false;
        do {
            this.displayData(dataset);
            CommandList command = CommandList.getCommand(System.in, System.out, System.err);
            switch (command) {
            case PRINT:
                dataset = this.contactList;
                break;
            case ADD:
                this.addContact(System.in, System.out, System.err);
                dataset = this.contactList;
                break;
            case NAME_SEARCH:
                dataset = this.nameSearch(System.in, System.out, System.err);
                break;
            case EMAIL_SEARCH:
                dataset = this.emailSearch(System.in, System.out, System.err);
                break;
            case DELETE:
                this.deleteContact(dataset, System.in, System.out, System.err);
                dataset = this.contactList;
                break;
            case WRITE:
                this.saveContacts(System.in, System.out, System.err);
                dataset = this.contactList;
                break;
            case RESTORE:
                this.loadContacts(System.in, System.out, System.err);
                dataset = this.contactList;
                break;
            case EXIT:
                exit = true;
            }
        } while (!exit);
        return 0;
    }

    private String getScannerLine(String prompt, InputStream in, PrintStream out) {
        String result = "";
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(in);
        while ("" == result) {
            out.printf("%s:\t", prompt);
            result = scanner.nextLine();
        }
        return result;
    }

    private String getScannerInput(String prompt, InputStream in, PrintStream out) {
        String result = "";
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(in);
        while ("" == result) {
            out.printf("%s:\t", prompt);
            result = scanner.next();
        }
        return result;
    }

    private void saveContacts(InputStream in, PrintStream out, PrintStream err) {
        String fileName = this.getScannerInput("Enter the file name", in, out);
        try {
            this.contactList.write(fileName);
        } catch (IOException ex) {
            err.printf("Failed to save contacts:\t%s%n", ex.getMessage());
        }
    }

    private void loadContacts(InputStream in, PrintStream out, PrintStream err) {
        String fileName = this.getScannerInput("Enter the file name", in, out);
        try {
            this.contactList.reload(fileName);
        } catch (IOException ex) {
            err.printf("Failed to reload contacts:\t%s%n", ex.getMessage());
        }
    }

    private void deleteContact(Iterable<ContactEntry> dataset, InputStream in, PrintStream out,
            PrintStream err) {
        String input = this.getScannerInput("Enter the number corresponding to your choice.", in,
                out);
        Iterator<ContactEntry> iterator = dataset.iterator();
        try {
            int choice = Integer.valueOf(input);
            int ct = 0;
            while (iterator.hasNext()) {
                ContactEntry e = iterator.next();
                if (++ct == choice) {
                    this.contactList.removeContact(e);
                    break;
                }
            }
            if (ct != choice) {
                err.println("Invalid selection.");
            }
        } catch (NumberFormatException ex) {
            err.println("Invalid number.");
        }

    }

    private List<ContactEntry> emailSearch(InputStream in, PrintStream out, PrintStream err) {
        String searchTerm = this.getScannerInput("Enter your search term", in, out);
        return this.contactList.searchByContactEmail(searchTerm);
    }

    private List<ContactEntry> nameSearch(InputStream in, PrintStream out, PrintStream err) {
        String searchTerm = this.getScannerInput("Enter your search term", in, out);
        return this.contactList.searchByContactName(searchTerm);
    }

    private void addContact(InputStream in, PrintStream out, PrintStream err) {
        String name = this.getScannerLine("Enter the contact name", in, out);
        String email = this.getScannerInput("Enter the contact email address", in, out);
        String phone = this.getScannerInput("Enter the contact phone", in, out);
        try {
            this.contactList.addContact(name, email, phone);
        } catch (ContactValidationException ex) {
            err.printf("Invalid contact information:\t%s%n", ex.getMessage());
        }
    }

    private void displayData(Iterable<ContactEntry> dataset) {
        if (null != dataset) {
            System.out.println("Contacts\n");
            int counter = 0;
            for (ContactEntry ce : dataset) {
                System.out.printf("%d\t%s%n", ++counter, ce);
            }
            System.out.println();
        }
    }

    private void banner() {
        System.out.println("Welcome to the Contacts application\n");
        System.out
                .println("This application stores contact names, email addresses and phone numbers.");
        System.out.println("Contacts are displayed in alphabetical order.");
        System.out.println("Only simple email addresses (per RFC 2821) are stored, and only");
        System.out.println("US telephone numbers are permitted.\n");
    }
}
