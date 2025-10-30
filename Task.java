// Task.java
public class Task {
    private int id;
    private String description;

    public Task(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return id + " | " + description;
    }

    // for persistence format: id|description
    public String serialize() {
        return id + "|" + description.replace("\n", " ").replace("|", " ");
    }

    public static Task deserialize(String line) {
        String[] parts = line.split("\\|", 2);
        if (parts.length < 2) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String desc = parts[1];
            return new Task(id, desc);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

