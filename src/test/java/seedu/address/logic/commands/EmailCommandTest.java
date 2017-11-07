package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jin-ting
/**
 * Contains integration tests (interaction with the Model) for {@code EmailCommand}.
 */
public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEmail = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EmailCommand emailCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(EmailCommand.MESSAGE_DISPLAY_EMAIL_SUCCESS, personToEmail.getEmails().toString());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
      //  expectedModel.deletePerson(personToEmail);

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        Set<Index> indices = new HashSet<>();
        indices.add(index);

        EmailCommand emailCommand = new EmailCommand(indices);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}


