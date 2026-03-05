package duke;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks and provides methods to add, list, mark, and unmark tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Creates an empty task list.
     */
    public TaskList() {
    }

    /**
     * Creates a task list from existing tasks.
     *
     * @param tasks Existing tasks to copy into this list.
     */
    public TaskList(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    /**
     * Adds a task to the list.
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given (0-based) index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes and returns the task at the given index.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns a defensive copy of all tasks.
     */
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    /**
     * Returns a string listing all tasks, or a message if empty.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return " No tasks yet! Time to add some adventures.";
        }
        StringBuilder sb = new StringBuilder(" Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(" ").append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Finds tasks whose descriptions contain the given keyword.
     */
    public String findTasks(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        StringBuilder sb = new StringBuilder(" Here are the matching tasks in your list:\n");
        int displayedIndex = 0;
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(lowerKeyword)) {
                displayedIndex++;
                sb.append(" ").append(displayedIndex).append(".").append(task).append("\n");
            }
        }
        if (displayedIndex == 0) {
            return " No matching tasks found.";
        }
        return sb.toString().trim();
    }
}
