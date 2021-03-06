package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Schedule;

//@@author CT15
/**
 * The Welcome Screen is opened when the application is run. Main Window will be
 * loaded when the welcome screen is closed.
 */
public class WelcomeScreen extends UiPart<Region> {
    private static final String ICON = "/images/contag_logo.png";
    private static final String LOGO = "/images/contag_logo_high_res.png";
    private static final String FXML = "WelcomeScreen.fxml";
    private static final int FIXED_HEIGHT = 450;
    private static final int FIXED_WIDTH = 450;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Stage primaryStage;
    private Config config;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;

    private MainWindow mainWindow;

    private ImageView logo;
    private Button continueButton;

    @FXML
    private VBox welcomeWindow;

    @FXML
    private StackPane logoPlaceHolder;

    @FXML
    private StackPane buttonPlaceHolder;

    public WelcomeScreen(Stage primaryStage, Config config, UserPrefs prefs, Logic logic, Model model) {
        super(FXML);

        // main window dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.logic = logic;
        this.model = model;

        setWindowFixedSize();
        setIcon(ICON);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
    }

    private void setWindowFixedSize() {
        primaryStage.setHeight(FIXED_HEIGHT);
        primaryStage.setWidth(FIXED_WIDTH);
        primaryStage.setResizable(false);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logo = new ImageView(new Image(LOGO));
        logo.setFitHeight(250);
        logo.setFitWidth(250);
        logoPlaceHolder.getChildren().add(logo);

        continueButton = new Button("Continue");
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                welcomeWindow.getScene().getWindow().hide();
                loadMainWindow();
                openReminderWindowIfRequired();
            }
        });
        buttonPlaceHolder.getChildren().add(continueButton);
    }

    /**
     * Opens main window.
     */
    public void loadMainWindow() {
        mainWindow = new MainWindow(primaryStage, config, prefs, logic, model);
        mainWindow.show(); //This should be called before creating other UI parts
        mainWindow.fillInnerParts();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    /**
     * Stops the application.
     */
    public void stop() {
        if (mainWindow != null) {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.hide();
            mainWindow.releaseResources();
        }
    }

    //@@author 17navasaw
    /**
     * Shows reminder pop-up if there exists upcoming activities the next day.
     */
    private void openReminderWindowIfRequired() {
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        ObservableList<Schedule> schedulesToRemindList = addressBook.getScheduleToRemindList();
        for (Schedule schedule : schedulesToRemindList) {
            logger.info("Schedules for reminder: " + schedule);
        }
        if (!schedulesToRemindList.isEmpty()) {
            ReminderWindow reminderWindow = new ReminderWindow(schedulesToRemindList);
            reminderWindow.show();
        }
    }
}
