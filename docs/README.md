# SEGATAKAI User Guide

SEGATAKAI is a command-line chatbot for managing tasks quickly from your terminal.

## Quick Start

1. Ensure you have Java 17 installed.
2. Build and run from project root:
   - Compile:
     `javac -cp src/main/java -d bin $(find src/main/java -name "*.java")`
   - Run:
     `java -cp bin duke.Segatakai`
3. Type commands and press Enter.

## Command Summary

- `todo <description>`
- `deadline <description> /by <yyyy-MM-dd>`
- `event <description> /from <start> /to <end>`
- `list`
- `mark <task number>`
- `unmark <task number>`
- `delete <task number>`
- `find <keyword>`
- `bye`

## Features

### Add a Todo

Adds a basic task.

Example:
`todo read book`

### Add a Deadline

Adds a task with a date.

Input date format must be `yyyy-MM-dd`.
Displayed format is `MMM dd yyyy`.

Example:
`deadline return book /by 2026-12-02`

Shown as:
`[D][ ] return book (by: Dec 02 2026)`

### Add an Event

Adds a task with start and end details.

Example:
`event project meeting /from Mon 2pm /to Mon 4pm`

### List Tasks

Shows all tasks with numbering.

Example:
`list`

### Mark / Unmark Tasks

Marks or unmarks a task by number.

Examples:
- `mark 2`
- `unmark 2`

### Delete Tasks

Removes a task by number.

Example:
`delete 3`

### Find Tasks

Searches task descriptions by keyword.

Example:
`find book`

### Exit

Ends the app.

Example:
`bye`

## Data Storage

- Tasks are saved automatically after every change.
- Data file path: `data/duke.txt`
- If the file or folder does not exist, SEGATAKAI creates it automatically.

## Error Handling

SEGATAKAI reports invalid commands and invalid formats with an error message, for example:

- Missing description (`todo`)
- Invalid task number (`mark x`)
- Wrong deadline date format (`deadline x /by 12/02/2026`)
