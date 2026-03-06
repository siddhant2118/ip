package duke;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void addTask_singleTask_sizeIsOne() {
        taskList.addTask(new Todo("read book"));
        assertEquals(1, taskList.size());
    }

    @Test
    void addTask_multipleTasks_sizeMatchesCount() {
        taskList.addTask(new Todo("task 1"));
        taskList.addTask(new Todo("task 2"));
        taskList.addTask(new Todo("task 3"));
        assertEquals(3, taskList.size());
    }

    @Test
    void deleteTask_removesCorrectTask_sizeDecreases() {
        taskList.addTask(new Todo("task A"));
        taskList.addTask(new Todo("task B"));
        Task removed = taskList.deleteTask(0);
        assertEquals("task A", removed.getDescription());
        assertEquals(1, taskList.size());
    }

    @Test
    void getTask_returnsCorrectTask() {
        taskList.addTask(new Todo("hello world"));
        Task t = taskList.getTask(0);
        assertEquals("hello world", t.getDescription());
    }

    @Test
    void listTasks_emptyList_returnsEmptyMessage() {
        String result = taskList.listTasks();
        assertTrue(result.contains("No tasks"), "Expected 'No tasks' message for empty list");
    }

    @Test
    void listTasks_nonEmptyList_containsTaskDescription() {
        taskList.addTask(new Todo("go running"));
        String result = taskList.listTasks();
        assertTrue(result.contains("go running"));
    }

    @Test
    void findTasks_matchingKeyword_returnsMatchingTask() {
        taskList.addTask(new Todo("read book"));
        taskList.addTask(new Todo("buy groceries"));
        String result = taskList.findTasks("book");
        assertTrue(result.contains("read book"));
        assertFalse(result.contains("buy groceries"));
    }

    @Test
    void findTasks_noMatch_returnsNoMatchMessage() {
        taskList.addTask(new Todo("read book"));
        String result = taskList.findTasks("xyz123");
        assertTrue(result.contains("No matching"), "Expected no-match message");
    }

    @Test
    void markAsDone_taskIsDone() {
        taskList.addTask(new Todo("finish assignment"));
        taskList.getTask(0).markAsDone();
        assertTrue(taskList.getTask(0).isDone());
    }

    @Test
    void markAsNotDone_taskIsNotDone() {
        taskList.addTask(new Todo("finish assignment"));
        taskList.getTask(0).markAsDone();
        taskList.getTask(0).markAsNotDone();
        assertFalse(taskList.getTask(0).isDone());
    }
}
