package registration;

import java.util.ArrayList;

public class RegistrationManager {
    public static final String[] EVENTS = {
            "Web Design Workshop",
            "JavaScript Quiz",
            "IT Career Talk"
    };

    private final ArrayList<Registration> registrations = new ArrayList<Registration>();
    private int nextId = 1;

    public String validateFullName(String fullName) {
        if (fullName == null || fullName.trim().length() < 3) {
            return "Full name must contain at least 3 characters.";
        }
        return null;
    }

    public String validateEmail(String email) {
        if (email == null) {
            return "Email must not be empty.";
        }
        String value = email.trim();
        int at = value.indexOf('@');
        if (at <= 0 || at == value.length() - 1) {
            return "Email must contain @ with some text before and after it (e.g. ali@example.com).";
        }
        return null;
    }

    public String validateStudentGroup(String group) {
        if (group == null || group.trim().isEmpty()) {
            return "Student group must not be blank.";
        }
        return null;
    }

    public String validateEventChoice(int choice) {
        if (choice < 1 || choice > EVENTS.length) {
            return "Event number must be between 1 and " + EVENTS.length + ".";
        }
        return null;
    }

    public Registration addRegistration(String fullName, String email,
                                        String studentGroup, int eventChoice) {
        String error = validateFullName(fullName);
        if (error == null) {
            error = validateEmail(email);
        }
        if (error == null) {
            error = validateStudentGroup(studentGroup);
        }
        if (error == null) {
            error = validateEventChoice(eventChoice);
        }
        if (error != null) {
            throw new IllegalArgumentException(error);
        }

        Registration registration = new Registration(
                nextId,
                fullName.trim(),
                email.trim(),
                studentGroup.trim(),
                EVENTS[eventChoice - 1]);
        registrations.add(registration);
        nextId++;
        return registration;
    }

    public void displayAll() {
        if (registrations.isEmpty()) {
            System.out.println("No registrations yet.");
            return;
        }
        for (int i = 0; i < registrations.size(); i++) {
            System.out.println(registrations.get(i));
        }
    }

    public Registration findById(int id) {
        for (int i = 0; i < registrations.size(); i++) {
            if (registrations.get(i).getId() == id) {
                return registrations.get(i);
            }
        }
        return null;
    }

    public boolean deleteById(int id) {
        for (int i = 0; i < registrations.size(); i++) {
            if (registrations.get(i).getId() == id) {
                registrations.remove(i);
                return true;
            }
        }
        return false;
    }

    public int displayByEvent(int eventChoice) {
        String error = validateEventChoice(eventChoice);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        String title = EVENTS[eventChoice - 1];
        int count = 0;
        for (int i = 0; i < registrations.size(); i++) {
            Registration r = registrations.get(i);
            if (r.getEventTitle().equals(title)) {
                System.out.println(r);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No registrations for \"" + title + "\".");
        }
        return count;
    }
}
