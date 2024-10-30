package cse360Phase1Project;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * <p> Title: Role Action UI </p>
 * <p> Description: This class provides the user interface that displays options based on the 
 * selected role (Admin, Student, or Instructor). Admins and instructors can manage users, 
 * invite new users, and manage or back up help articles, while students receive a welcome message. </p>
 *
 * <p> Group Tu18: 
 * - Aidan Beardslee
 * - Emily Do
 * - Kyle Zang
 * - Anne Shih
 * - Angie Moroyoqui 
 * </p>
 * 
 * @version 3.03    2024-10-28    Added consistent role navigation and persistence
 */
public class RoleActionUI {
    private UserManagementApp mainApp;
    private String role;

    /**
     * Constructor for the RoleActionUI class.
     * Sets up the user interface based on the selected role (Admin, Student, or Instructor).
     *
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     * @param role  the selected role (Admin, Student, or Instructor)
     * @param user  the user who is currently logged in
     */
    public RoleActionUI(Pane root, UserManagementApp app, String role, User user) {
        this.mainApp = app;
        this.role = role;

        Label titleLabel = new Label("Selected Role: " + role);
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(20);

        if (role.equals("Admin") || role.equals("Instructor")) { 
            setupAdminInstructorOptions(root);  // Shared Admin/Instructor options
        } else if (role.equals("Student")) {
            setupStudentView(root);  // Student-specific view
        }

        // Back and Logout Buttons
        Button backButton = new Button("Back");
        backButton.setFont(new Font("Arial", 16));
        backButton.setLayoutX(200);
        backButton.setLayoutY(300);
        backButton.setOnAction(e -> mainApp.showRoleSelectionUI(user));

        Button logoutButton = new Button("Logout");
        logoutButton.setFont(new Font("Arial", 16));
        logoutButton.setLayoutX(200);
        logoutButton.setLayoutY(350);
        logoutButton.setOnAction(e -> mainApp.showLoginUI());

        root.getChildren().addAll(titleLabel, backButton, logoutButton);
    }

    private void setupAdminInstructorOptions(Pane root) {
        Button manageArticlesButton = new Button("Manage Help Articles");
        manageArticlesButton.setFont(new Font("Arial", 16));
        manageArticlesButton.setLayoutX(200);
        manageArticlesButton.setLayoutY(150);
        manageArticlesButton.setOnAction(e -> mainApp.showHelpArticleManagementUI());


        root.getChildren().addAll(manageArticlesButton);

        if (role.equals("Admin")) { 
            Button viewUsersButton = new Button("Manage Users");
            viewUsersButton.setFont(new Font("Arial", 16));
            viewUsersButton.setLayoutX(200);
            viewUsersButton.setLayoutY(200);
            viewUsersButton.setOnAction(e -> mainApp.showAdminHomePageUI());

            Button inviteUsersButton = new Button("Invite New User");
            inviteUsersButton.setFont(new Font("Arial", 16));
            inviteUsersButton.setLayoutX(200);
            inviteUsersButton.setLayoutY(250);
            inviteUsersButton.setOnAction(e -> mainApp.showInviteUserUI());

            root.getChildren().addAll(viewUsersButton, inviteUsersButton);
        }
    }

    private void setupStudentView(Pane root) {
        Label welcomeLabel = new Label("Welcome, Student!");
        welcomeLabel.setFont(new Font("Arial", 18));
        welcomeLabel.setLayoutX(200);
        welcomeLabel.setLayoutY(100);
        root.getChildren().add(welcomeLabel);
    }
}