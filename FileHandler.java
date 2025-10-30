// FileHandler.java
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static void savePending(ArrayList<Task> pending, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task t : pending) {
                writer.write(t.serialize());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving pending tasks: " + e.getMessage());
        }
    }

    public static ArrayList<Task> loadPending(String filename) {
        ArrayList<Task> pending = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) return pending;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task t = Task.deserialize(line);
                if (t != null) pending.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error loading pending tasks: " + e.getMessage());
        }
        return pending;
    }

    public static void saveCompleted(Task[] completed, int count, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < count; i++) {
                Task t = completed[i];
                if (t != null) {
                    writer.write(t.serialize());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving completed tasks: " + e.getMessage());
        }
    }

    public static Task[] loadCompleted(String filename, int capacity) {
        Task[] completed = new Task[capacity];
        File f = new File(filename);
        if (!f.exists()) return completed;
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null && index < capacity) {
                Task t = Task.deserialize(line);
                if (t != null) {
                    completed[index++] = t;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading completed tasks: " + e.getMessage());
        }
        return completed;
    }
}

