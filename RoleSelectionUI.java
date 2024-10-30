package cse360Phase1Project;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * <p> Title: Role Selection UI </p>
 * 
 * <p> Description: This class provides the user interface where a user can select a role 
 * for the current session. Based on the roles assigned to the user (Admin, Student, Instructor), 
 * the appropriate buttons are displayed. The user can also log out from this screen. </p>
 * 
 * <p> Group Tu18: 
 * - Aidan Beardslee
 * - Emily Do
 * - Kyle Zang
 * - Anne Shih
 * - Angie Moroyoqui 
 * </p>
 * 
 * @version 3.00    2024-10-09    Final version
 * 
 */

public class RoleSelectionUI{
    private UserManagementApp mainApp;
    private User user;

    /**
     * Constructor for the RoleSelectionUI class
     * This method sets up the interface where the user selects a role for the current session
     * Based on the roles assigned to the user, the appropriate buttons are displayed
     * 
     * @param root  	the Pane to which all UI components are added
     * @param app   	the main application instance for interaction
     * @param user  	the user who is currently logged in
     */
    
    public RoleSelectionUI(Pane root, UserManagementApp app, User user){
        mainApp = app;
        this.user = user;

        System.out.println("Roles for user " + user.getUsername() + ": " + user.getRoles());

        Label titleLabel = new Label("Select Your Role for This Session"); // select your role for this session label
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(50);

        String[] availableRoles = {"Admin", "Student", "Instructor"}; // display all current labels for session

        int yOffset = 150; 
        for (String role : availableRoles){
            if (user.getRoles().contains(role)){
                Button roleButton = new Button(role);
                roleButton.setLayoutX(250);
                roleButton.setLayoutY(yOffset);
                roleButton.setOnAction(e -> handleRoleSelection(role));
                root.getChildren().add(roleButton);
                yOffset += 50; 
            }
        }

        Button logoutButton = new Button("Logout"); // logout button
        logoutButton.setLayoutX(250);  
        logoutButton.setLayoutY(yOffset + 20); 
        logoutButton.setOnAction(e -> handleLogout()); 
        root.getChildren().add(logoutButton); 
        root.getChildren().add(titleLabel);
    }

    
    /**
     * This method handles the role selection action
     * when a user clicks on a role button, the corresponding role's action UI is displayed
     * 
     * @param role 	the selected role (Admin, Student, or Instructor)
     */
    
    private void handleRoleSelection(String role){
        mainApp.showRoleActionUI(role, user); 
    }

    /**
     * This method handles the logout action
     * 
     */
    
    private void handleLogout(){
        mainApp.showLoginUI(); 
    }
}