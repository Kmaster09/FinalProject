// TaskManager.java
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> pending;
    private Task[] completed;
    private int completedCount;
    private int nextId;

    private final String pendingFile;
    private final String completedFile;

    public TaskManager(int completedCapacity, String pendingFile, String completedFile) {
        this.pendingFile = pendingFile;
        this.completedFile = completedFile;

        // load from files if present
        this.pending = FileHandler.loadPending(pendingFile);
        this.completed = FileHandler.loadCompleted(completedFile, completedCapacity);

        // determine completedCount by scanning for nulls
        int count = 0;
        for (Task t : this.completed) {
            if (t != null) count++;
            else break;
        }
        this.completedCount = count;

        // compute nextId as max existing id + 1
        int maxId = 0;
        for (Task t : pending) if (t.getId() > maxId) maxId = t.getId();
        for (int i = 0; i < completedCount; i++) if (completed[i].getId() > maxId) maxId = completed[i].getId();
        this.nextId = maxId + 1;
    }

    public void addTask(String description) {
        Task t = new Task(nextId++, description);
        pending.add(t);
        System.out.println("Added: " + t);
    }

    public void listPending() {
        if (pending.isEmpty()) {
            System.out.println("No pending tasks.");
            return;
        }
        System.out.println("Pending tasks:");
        for (Task t : pending) {
            System.out.println("  " + t);
        }
    }

    public void listCompleted() {
        if (completedCount == 0) {
            System.out.println("No completed tasks.");
            return;
        }
        System.out.println("Completed tasks:");
        for (int i = 0; i < completedCount; i++) {
            System.out.println("  " + completed[i]);
        }
    }

    // complete by task id; uses try-catch to handle invalid removal scenarios
    public void completeTaskById(int id) {
        Task toComplete = null;
        for (Task t : pending) {
            if (t.getId() == id) {
                toComplete = t;
                break;
            }
        }
        if (toComplete == null) {
            System.out.println("No pending task with id " + id);
            return;
        }

        try {
            if (completedCount >= completed.length) {
                throw new ArrayIndexOutOfBoundsException("Completed tasks array is full.");
            }
            completed[completedCount++] = toComplete;
            pending.remove(toComplete);
            System.out.println("Completed: " + toComplete);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Cannot complete task: completed archive is full.");
            System.out.println("Consider clearing some completed tasks from the archive.");
        }
    }

    // remove pending task by id
    public void removePendingById(int id) {
        Task found = null;
        for (Task t : pending) {
            if (t.getId() == id) { found = t; break; }
        }
        if (found == null) {
            System.out.println("No pending task with id " + id);
            return;
        }
        pending.remove(found);
        System.out.println("Removed pending task: " + found);
    }

    // clear completed (resets array)
    public void clearCompleted() {
        for (int i = 0; i < completedCount; i++) {
            completed[i] = null;
        }
        completedCount = 0;
        System.out.println("Cleared completed tasks archive.");
    }

    // persistence methods
    public void saveAll() {
        FileHandler.savePending(pending, pendingFile);
        FileHandler.saveCompleted(completed, completedCount, completedFile);
    }

    // getters useful for UI
    public int getCompletedCapacity() {
        return completed.length;
    }
}

