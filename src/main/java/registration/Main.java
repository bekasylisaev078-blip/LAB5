package registration;

import java.util.Scanner;

public class Main {
    private static final int INVALID = Integer.MIN_VALUE;
    private static final int FIELD_NAME = 1;
    private static final int FIELD_EMAIL = 2;
    private static final int FIELD_GROUP = 3;

    private static final Scanner scanner = new Scanner(System.in);
    private static final RegistrationManager manager = new RegistrationManager();

    private static class InputClosedException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    public static void main(String[] args) {
        System.out.println("=== Event Registration ===");
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = readInt("Choose an option: ");
                if (choice == INVALID) {
                    continue;
                }
                switch (choice) {
                    case 1:
                        addRegistration();
                        break;
                    case 2:
                        manager.displayAll();
                        break;
                    case 3:
                        findRegistration();
                        break;
                    case 4:
                        deleteRegistration();
                        break;
                    case 5:
                        showByEvent();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Unknown option: " + choice + ". Choose 0-5.");
                        break;
                }
            }
        } catch (InputClosedException e) {
            System.out.println();
            System.out.println("Input closed. Goodbye!");
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add registration");
        System.out.println("2. Show all registrations");
        System.out.println("3. Find registration by ID");
        System.out.println("4. Delete registration by ID");
        System.out.println("5. Show registrations for an event");
        System.out.println("0. Exit");
    }

    private static void addRegistration() {
        System.out.println("(type 'cancel' at any prompt to return to the menu)");

        String fullName = readValidated("Full name: ", FIELD_NAME);
        if (fullName == null) {
            return;
        }

        String email = readValidated("Email: ", FIELD_EMAIL);
        if (email == null) {
            return;
        }

        String group = readValidated("Student group: ", FIELD_GROUP);
        if (group == null) {
            return;
        }

        int eventChoice = readEventChoice();
        if (eventChoice == INVALID) {
            return;
        }

        try {
            Registration created = manager.addRegistration(fullName, email, group, eventChoice);
            System.out.println("Registration created. ID: " + created.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Registration was not created: " + e.getMessage());
        }
    }

    private static void findRegistration() {
        int id = readInt("Enter ID: ");
        if (id == INVALID) {
            return;
        }
        Registration found = manager.findById(id);
        if (found == null) {
            System.out.println("Registration not found.");
        } else {
            System.out.println(found);
        }
    }

    private static void deleteRegistration() {
        int id = readInt("Enter ID: ");
        if (id == INVALID) {
            return;
        }
        if (manager.deleteById(id)) {
            System.out.println("Registration " + id + " deleted.");
        } else {
            System.out.println("Registration not found. Nothing was deleted.");
        }
    }

    private static void showByEvent() {
        printEvents();
        int choice = readInt("Select event: ");
        if (choice == INVALID) {
            return;
        }
        String error = manager.validateEventChoice(choice);
        if (error != null) {
            System.out.println(error);
            return;
        }
        manager.displayByEvent(choice);
    }

    private static void printEvents() {
        System.out.println("Available events:");
        for (int i = 0; i < RegistrationManager.EVENTS.length; i++) {
            System.out.println((i + 1) + ". " + RegistrationManager.EVENTS[i]);
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new InputClosedException();
        }
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        String line = readLine(prompt);
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a whole number.");
            return INVALID;
        }
    }

    private static String validate(int field, String value) {
        switch (field) {
            case FIELD_NAME:
                return manager.validateFullName(value);
            case FIELD_EMAIL:
                return manager.validateEmail(value);
            case FIELD_GROUP:
                return manager.validateStudentGroup(value);
            default:
                return null;
        }
    }

    private static String readValidated(String prompt, int field) {
        while (true) {
            String value = readLine(prompt);
            if (value.trim().equalsIgnoreCase("cancel")) {
                System.out.println("Cancelled. Nothing was saved.");
                return null;
            }
            String error = validate(field, value);
            if (error == null) {
                return value.trim();
            }
            System.out.println(error + " Try again.");
        }
    }

    private static int readEventChoice() {
        printEvents();
        while (true) {
            String line = readLine("Select event: ");
            if (line.trim().equalsIgnoreCase("cancel")) {
                System.out.println("Cancelled. Nothing was saved.");
                return INVALID;
            }
            try {
                int choice = Integer.parseInt(line.trim());
                String error = manager.validateEventChoice(choice);
                if (error == null) {
                    return choice;
                }
                System.out.println(error + " Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number 1-"
                        + RegistrationManager.EVENTS.length + ". Try again.");
            }
        }
    }
}
