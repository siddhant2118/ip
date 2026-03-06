package duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    void parse_listCommand_returnsListCommand() throws DukeException {
        Command cmd = parser.parse("list");
        assertEquals(CommandType.LIST, cmd.getType());
    }

    @Test
    void parse_byeCommand_returnsByeCommand() throws DukeException {
        Command cmd = parser.parse("bye");
        assertEquals(CommandType.BYE, cmd.getType());
    }

    @Test
    void parse_todoWithDescription_returnsTodoCommand() throws DukeException {
        Command cmd = parser.parse("todo borrow book");
        assertEquals(CommandType.TODO, cmd.getType());
        assertEquals("borrow book", cmd.getDescription());
    }

    @Test
    void parse_todoEmptyDescription_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("todo"));
        assertThrows(DukeException.class, () -> parser.parse("todo   "));
    }

    @Test
    void parse_deadlineValidInput_returnsDeadlineCommand() throws DukeException {
        Command cmd = parser.parse("deadline return book /by 2026-12-02");
        assertEquals(CommandType.DEADLINE, cmd.getType());
        assertEquals("return book", cmd.getDescription());
    }

    @Test
    void parse_deadlineEmptyDescription_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("deadline"));
        assertThrows(DukeException.class, () -> parser.parse("deadline   "));
    }

    @Test
    void parse_deadlineMissingByKeyword_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("deadline do homework"));
    }

    @Test
    void parse_deadlineInvalidDateFormat_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("deadline do homework /by 12/02/2026"));
    }

    @Test
    void parse_eventValidInput_returnsEventCommand() throws DukeException {
        Command cmd = parser.parse("event meeting /from Mon 2pm /to Mon 4pm");
        assertEquals(CommandType.EVENT, cmd.getType());
        assertEquals("meeting", cmd.getDescription());
    }

    @Test
    void parse_eventMissingFrom_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("event meeting /to Mon 4pm"));
    }

    @Test
    void parse_eventMissingTo_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("event meeting /from Mon 2pm"));
    }

    @Test
    void parse_markWithNumber_returnsMarkCommand() throws DukeException {
        Command cmd = parser.parse("mark 3");
        assertEquals(CommandType.MARK, cmd.getType());
        assertEquals(3, cmd.getTaskNumber());
    }

    @Test
    void parse_markNoNumber_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("mark"));
        assertThrows(DukeException.class, () -> parser.parse("mark abc"));
    }

    @Test
    void parse_deleteWithNumber_returnsDeleteCommand() throws DukeException {
        Command cmd = parser.parse("delete 2");
        assertEquals(CommandType.DELETE, cmd.getType());
        assertEquals(2, cmd.getTaskNumber());
    }

    @Test
    void parse_findWithKeyword_returnsFindCommand() throws DukeException {
        Command cmd = parser.parse("find book");
        assertEquals(CommandType.FIND, cmd.getType());
        assertEquals("book", cmd.getKeyword());
    }

    @Test
    void parse_findNoKeyword_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("find"));
    }

    @Test
    void parse_unknownCommand_throwsDukeException() {
        assertThrows(DukeException.class, () -> parser.parse("blah"));
        assertThrows(DukeException.class, () -> parser.parse("xyz"));
    }
}
