import java.io.*;
import java.util.*;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class ContactManager {
    private List<Contact> contacts;
    private static final String FILE_NAME = "contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    public void addContact(Contact contact) {
        if (isValidName(contact.getName()) && isValidPhoneNumber(contact.getPhoneNumber()) && isValidEmail(contact.getEmail())) {
            contacts.add(contact);
            saveContacts();
        }
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void updateContact(int index, Contact contact) {
        if (index >= 0 && index < contacts.size()) {
            contacts.set(index, contact);
            saveContacts();
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts();
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private void loadContacts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            // File not found, which is okay as it will be created when we save contacts
        }
    }

    private void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();

        while (true) {
            System.out.println("\nContact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    contactManager.addContact(new Contact(name, phoneNumber, email));
                    break;

                case 2:
                    List<Contact> contacts = contactManager.getContacts();
                    for (int i = 0; i < contacts.size(); i++) {
                        System.out.println((i + 1) + ". " + contacts.get(i));
                    }
                    break;

                case 3:
                    System.out.print("Enter contact number to edit: ");
                    int editIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume newline
                    if (editIndex >= 0 && editIndex < contactManager.getContacts().size()) {
                        System.out.print("Enter new name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new phone number: ");
                        phoneNumber = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        email = scanner.nextLine();
                        contactManager.updateContact(editIndex, new Contact(name, phoneNumber, email));
                    } else {
                        System.out.println("Invalid contact number.");
                    }
                    break;

                case 4:
                    System.out.print("Choose Number to delete: ");
                    int deleteIndex = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume newline
                    if (deleteIndex >= 0 && deleteIndex < contactManager.getContacts().size()) {
                        contactManager.deleteContact(deleteIndex);
                    } else {
                        System.out.println("Invalid contact number.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
