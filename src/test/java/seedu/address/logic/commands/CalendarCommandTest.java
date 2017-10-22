package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;


import org.junit.Rule;
import org.junit.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LocateCommand}.
 */
public class CalendarCommandTest {


    private static final long MESSAGE_DISPLAY_CALENDAR_SUCCESS = ;
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    private void assertExecutionSuccess() throws CommandException {
        CalendarCommand command = new CalendarCommand();

        try {
            CommandResult commandResult = command.execute();
            assertEquals(MESSAGE_DISPLAY_CALENDAR_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

    }

}


