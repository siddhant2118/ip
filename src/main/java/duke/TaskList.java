package duke;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of tasks and provides methods to add, list, mark, and unmark tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

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

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

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
}
