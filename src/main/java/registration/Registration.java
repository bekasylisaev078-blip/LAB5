package registration;

public class Registration {
    private final int id;
    private final String fullName;
    private final String email;
    private final String studentGroup;
    private final String eventTitle;

    public Registration(int id, String fullName, String email,
                        String studentGroup, String eventTitle) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.studentGroup = studentGroup;
        this.eventTitle = eventTitle;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    @Override
    public String toString() {
        return id + " | " + fullName + " | " + email + " | "
                + studentGroup + " | " + eventTitle;
    }
}
