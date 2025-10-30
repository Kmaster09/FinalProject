// Main.java
public class Main {
    private static final String PENDING_FILE = "pending.txt";
    private static final String COMPLETED_FILE = "completed.txt";
    private static final int COMPLETED_CAPACITY = 100; // fixed array size requirement

    public static void main(String[] args) {
        TaskManager manager = new TaskManager(COMPLETED_CAPACITY, PENDING_FILE, COMPLETED_FILE);

        System.out.println("Welcome to the Task Manager");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = InputValidator.getIntInRange("Choose an option: ", 1, 8);
            switch (choice) {
                case 1:
                    String desc = InputValidator.getNonEmptyString("Task description: ");
                    manager.addTask(desc);
                    break;
                case 2:
                    manager.listPending();
                    break;
                case 3:
                    manager.listCompleted();
                    break;
                case 4:
                    manager.listPending();
                    int idToComplete = InputValidator.getInt("Enter the task ID to complete: ");
                    manager.completeTaskById(idToComplete);
                    break;
                case 5:
                    manager.listPending();
                    int idToRemove = InputValidator.getInt("Enter the task ID to remove: ");
                    manager.removePendingById(idToRemove);
                    break;
                case 6:
                    manager.clearCompleted();
                    break;
                case 7:
                    manager.saveAll();
                    System.out.println("Data saved.");
                    break;
                case 8:
                    // exit: save and quit
                    manager.saveAll();
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("MENU:");
        System.out.println("  1) Add task");
        System.out.println("  2) List pending tasks");
        System.out.println("  3) List completed tasks");
        System.out.println("  4) Complete a task (move pending -> completed)");
        System.out.println("  5) Remove pending task");
        System.out.println("  6) Clear completed archive");
        System.out.println("  7) Save now");
        System.out.println("  8) Save and Exit");
    }
}

