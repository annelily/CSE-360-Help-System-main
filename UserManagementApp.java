package cse360Phase1Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * <p> Title: User Management App - Phase 2 </p>
 * 
 * <p> Description: This class serves as the main entry point for the JavaFX-based 
 * user management system. It provides various UI screens, such as Login, Role Selection, 
 * Help Article Management, and User Registration. The application allows users to 
 * manage accounts, invite new users, and navigate between roles.</p>
 * 
 * <p> Group Tu18:
 * - Aidan Beardslee
 * - Emily Do
 * - Kyle Zang
 * - Anne Shih
 * - Angie Moroyoqui
 * </p>
 * 
 * @version 3.00    2024-10-29    Final version
 */
public class UserManagementApp extends Application {
    private Stage primaryStage;
    private User currentUser;

    /**
     * Main method that launches the JavaFX application.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFX entry point that initializes the primary stage
     * 
     * @param primaryStage The main stage (window) for the application
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginUI();
    }

    /**
     * Displays the login screen.
     */
    public void showLoginUI() {
        Pane root = new Pane();
        new LoginUI(root, this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    /**
     * Displays the Help Article Management UI.
     */
    public void showHelpArticleManagementUI() {
        Pane root = new Pane();
        new HelpArticleUI(root, this, HelpArticleManager.getInstance());
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Help Article Management");
        primaryStage.show();
    }

    /**
     * Displays the role selection screen for the given user
     * 
     * @param user The user for whom the role selection screen is shown
     */
    public void showRoleSelectionUI(User user) {
        Pane root = new Pane();
        new RoleSelectionUI(root, this, user);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Role Selection");
        primaryStage.show();
    }

    /**
     * Displays the role-specific action screen
     * 
     * @param role The role of the user(e.g. Admin, Instructor)
     * @param user The current user
     */
    public void showRoleActionUI(String role, User user) {
        Pane root = new Pane();
        new RoleActionUI(root, this, role, user);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Role Action - " + role);
        primaryStage.show();
    }

    /**
     * Displays the setup completion screen for the user
     * 
     * @param user The user completing the setup
     */
    public void showFinishSetupUI(User user) {
        Pane root = new Pane();
        new FinishSetupUI(root, this, user);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Finish Setup");
        primaryStage.show();
    }

    /**
     * Displays the Admin home page UI
     */
    public void showAdminHomePageUI() {
        Pane root = new Pane();
        new AdminHomeUI(root, this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Home Page");
        primaryStage.show();
    }

    /**
     * Displays the user registration screen.
     * 
     * @param role          	The role for which the user is being registered
     * @param isInitialAdmin 	Indicates if the registration is for the initial admin user
     */
    public void showRegistrationUI(String role, boolean isInitialAdmin) {
        Pane root = new Pane();
        new RegistrationUI(root, this, role, primaryStage, isInitialAdmin);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("User Registration");
        primaryStage.show();
    }

    /**
     * Displays the user invitation screen
     */
    public void showInviteUserUI() {
        Pane root = new Pane();

        Label inviteUserLabel = new Label("Invite New User");
        inviteUserLabel.setLayoutX(200);
        inviteUserLabel.setLayoutY(50);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        usernameField.setLayoutX(200);
        usernameField.setLayoutY(100);

        TextField rolesField = new TextField();
        rolesField.setPromptText("Enter Roles");
        rolesField.setLayoutX(200);
        rolesField.setLayoutY(150);

        Button inviteButton = new Button("Invite");
        inviteButton.setLayoutX(200);
        inviteButton.setLayoutY(200);
        inviteButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String roles = rolesField.getText().trim();

            if (!username.isEmpty() && !roles.isEmpty()) {
                User newUser = new User(username, "");

                for (String role : roles.split(",")) {
                    newUser.addRole(role.trim());
                }

                newUser.setRegistrationCode(generateVerificationCode());
                UserManagement.getInstance().registerUser(newUser);
                showInfoMessage("User invited successfully. Verification Code: " + newUser.getRegistrationCode());
            } else {
                showErrorMessage("Please fill out all fields.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setLayoutX(200);
        backButton.setLayoutY(250);
        backButton.setOnAction(e -> showRoleActionUI(currentUser.getRoles().get(0), getCurrentUser()));

        root.getChildren().addAll(inviteUserLabel, usernameField, rolesField, inviteButton, backButton);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Invite New User");
        primaryStage.show();
    }

    public void showVerifyUserUI(User user) {
        Pane root = new Pane();
        new VerifyUserUI(root, this, user);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Verify User");
        primaryStage.show();
    }

    public void showStudentInstructorHomeUI() {
        Pane root = new Pane();
        new StudentInstructorUI(root, this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }

    
    /**
     * Handles the login process for the given user
     * Directs the user to the appropriate screen based on their role and setup status
     * 
     * @param user The user attempting to log in
     */

    public void handleLogin(User user) {
        this.currentUser = user;
        if (user.getRoles().contains("Admin")) {
            if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null) {
                showFinishSetupUI(user);
            } else {
                showRoleSelectionUI(user);
            }
        } else {
            if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null) {
                showFinishSetupUI(user);
            } else if (user.getRoles().size() == 1) {
                showRoleActionUI(user.getRoles().get(0), user);
            } else {
                showRoleSelectionUI(user);
            }
        }
    }

    /**
     * Generates a random 4-digit verification code.
     * 
     * @return A 4-digit verification code as a string.
     */
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    
    public void showForgotPasswordUI() {
        Pane root = new Pane();
        new ForgotPasswordUI(root, this);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Forgot Password");
        primaryStage.show();
    }

    /**
     * Displays an error message in an alert dialog.
     * 
     * @param message The error message to display.
     */
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational message in an alert dialog.
     * 
     * @param message The informational message to display.
     */
    public void showInfoMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Returns the current logged-in user.
     * 
     * @return The current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }
}