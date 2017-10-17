package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;

/**
 * Schedules an Activity with a person.
 */
public class ScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "schedule";

    public static final String COMMAND_ALIAS = "sc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules an Activity with a person. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_ACTIVITY + "ACTIVITY";

    public static final String MESSAGE_ADD_SCHEDULE_SUCCESS = "Added schedule with Person: %1$s";

    private final Index index;
    private final Schedule schedule;

    /**
     * Creates a ScheduleCommand to add the specified {@code Schedule}
     */
    public ScheduleCommand(Index index, Schedule schedule) {
        requireNonNull(index);
        requireNonNull(schedule);
        this.index = index;
        this.schedule = schedule;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), schedule, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_SCHEDULE_SUCCESS, personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && schedule.equals(((ScheduleCommand) other).schedule));
    }
}

