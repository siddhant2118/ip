package duke;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public List<Task> load() throws DukeException {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("Could not read data file: " + filePath);
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }
            tasks.add(parseTask(line, i + 1));
        }
        return tasks;
    }

    public void save(TaskList taskList) throws DukeException {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            List<String> lines = new ArrayList<>();
            for (Task task : taskList.getTasks()) {
                lines.add(task.toStorageString());
            }
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("Could not save tasks to data file: " + filePath);
        }
    }

    private Task parseTask(String line, int lineNumber) throws DukeException {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
        }

        String type = parts[0];
        String doneText = parts[1];
        String description = parts[2];

        Task task;
        switch (type) {
        case "T":
            if (parts.length != 3) {
                throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
            }
            task = new Todo(description);
            break;
        case "D":
            if (parts.length != 4) {
                throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
            }
            try {
                task = new Deadline(description, LocalDate.parse(parts[3]));
            } catch (DateTimeParseException e) {
                throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
            }
            break;
        case "E":
            if (parts.length != 5) {
                throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
            }
            task = new Event(description, parts[3], parts[4]);
            break;
        default:
            throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
        }

        if (doneText.equals("1")) {
            task.setDone(true);
        } else if (doneText.equals("0")) {
            task.setDone(false);
        } else {
            throw new DukeException("Corrupted data at line " + lineNumber + ": " + line);
        }
        return task;
    }
}
