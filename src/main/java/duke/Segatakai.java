package duke;

import java.nio.file.Path;

public class Segatakai {
    private final Storage storage;
    private final Ui ui;
    private final Parser parser;
    private TaskList tasks;

    public Segatakai(String filePath) {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage(Path.of(filePath));
        this.tasks = loadTaskList();
    }

    private TaskList loadTaskList() {
        try {
            return new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showError("Unable to load saved tasks: " + e.getMessage());
            return new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String userInput = ui.readCommand();
                Command command = parser.parse(userInput);
                isExit = command.getType() == CommandType.BYE;
                if (!isExit) {
                    execute(command);
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.showGoodbye(tasks.size());
        ui.close();
    }

    private void execute(Command command) throws DukeException {
        switch (command.getType()) {
        case LIST:
            ui.showList(tasks);
            break;
        case MARK:
            handleMark(command.getTaskNumber());
            break;
        case UNMARK:
            handleUnmark(command.getTaskNumber());
            break;
        case DELETE:
            handleDelete(command.getTaskNumber());
            break;
        case TODO:
            addTask(new Todo(command.getDescription()));
            break;
        case DEADLINE:
            addTask(new Deadline(command.getDescription(), command.getBy()));
            break;
        case EVENT:
            addTask(new Event(command.getDescription(), command.getFrom(), command.getTo()));
            break;
        default:
            throw new DukeException("Unsupported command.");
        }
    }

    private void addTask(Task task) throws DukeException {
        tasks.addTask(task);
        storage.save(tasks);
        ui.showAddConfirmation(task, tasks.size());
    }

    private void handleMark(int taskNumber) throws DukeException {
        Task task = tasks.getTask(toIndex(taskNumber));
        if (task.isDone()) {
            ui.showAlreadyMarked(task);
            return;
        }
        task.markAsDone();
        storage.save(tasks);
        ui.showMarkConfirmation(task);
    }

    private void handleUnmark(int taskNumber) throws DukeException {
        Task task = tasks.getTask(toIndex(taskNumber));
        if (!task.isDone()) {
            ui.showAlreadyUnmarked(task);
            return;
        }
        task.markAsNotDone();
        storage.save(tasks);
        ui.showUnmarkConfirmation(task);
    }

    private void handleDelete(int taskNumber) throws DukeException {
        Task deletedTask = tasks.deleteTask(toIndex(taskNumber));
        storage.save(tasks);
        ui.showDeleteConfirmation(deletedTask, tasks.size());
    }

    private int toIndex(int taskNumber) throws DukeException {
        if (tasks.size() == 0) {
            throw new DukeException("There are no tasks in the list yet.");
        }
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new DukeException("Task number must be between 1 and " + tasks.size() + ".");
        }
        return taskNumber - 1;
    }

    public static void main(String[] args) {
        new Segatakai("data/duke.txt").run();
    }
}
