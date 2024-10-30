package cse360Phase1Project;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * <p> Title: Admin Home UI </p>
 * 
 * <p> Description: A user interface for the Admin home page, allowing management of users within
 * the CSE360 Phase 1 Project. This includes functionality for deleting users, resetting accounts, 
 * and editing user roles. This is part of the JavaFX application for user management. </p>
 * 
 * <p> Group Tu18: 
 * - Aidan Beardslee
 * - Emily Do
 * - Kyle Zang
 * - Anne Shih
 * - Angie Moroyoqui 
 * </p>
 * 
 * 
 * @version 3.00    2024-10-09    Final version
 * 
 */

public class AdminHomeUI {
    private UserManagementApp mainApp;
    private ListView<String> userListView;
    
    /**
     * Constructor for AdminHomeUI.
     * This method sets up the Admin home page with the user list and buttons for managing users
     * 
     * @param root  The Pane to which all UI components are added
     * @param app   The main application instance for interaction
     */

    public AdminHomeUI(Pane root, UserManagementApp app){
        this.mainApp = app;

        Label titleLabel = new Label("Admin Manage Users"); // Admin Manage Users Label
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(30);

        userListView = new ListView<>();
        userListView.setLayoutX(50);
        userListView.setLayoutY(70);
        userListView.setPrefSize(500, 300);

        populateUserList();

        Button deleteUserButton = new Button("Delete User"); // Button for enabling the use of deleting Users
        deleteUserButton.setLayoutX(50);
        deleteUserButton.setLayoutY(400);
        deleteUserButton.setOnAction(e -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            
            if (selectedUser != null){ // is 
                String username = selectedUser.split(" \\| ")[0].split(": ")[1]; 
                showDeleteUserConfirmation(username);
                populateUserList(); 
            } 
            else{
                showErrorMessage("No user selected.");
            }
        });

        Button editRolesButton = new Button("Edit Roles"); // Button for enabling the use of editing roles
        editRolesButton.setLayoutX(150);
        editRolesButton.setLayoutY(400);
        editRolesButton.setOnAction(e -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null){
                String username = selectedUser.split(" \\| ")[0].split(": ")[1]; 
                User user = UserManagement.getInstance().getUser(username);
                
                if (user != null){
                    showEditRolesDialog(user);
                    populateUserList(); 
                }
            } 
            else{
                showErrorMessage("No user selected.");
            }
        });

        Button resetUserButton = new Button("Reset User Account"); // Button for the use of enabling resetting user accounts
        resetUserButton.setLayoutX(250);
        resetUserButton.setLayoutY(400);
        resetUserButton.setOnAction(e -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            if (selectedUser != null){
                String username = selectedUser.split(" \\| ")[0].split(": ")[1]; 
                showResetUserAccountDialog(username);
                populateUserList(); 
            } 
            else{
                showErrorMessage("No user selected.");
            }
        });

        Button backButton = new Button("Back"); // Implementation of a Back Button
        backButton.setLayoutX(400);
        backButton.setLayoutY(400);
        backButton.setOnAction(e -> mainApp.showRoleActionUI("Admin", mainApp.getCurrentUser()));

        root.getChildren().addAll(titleLabel, userListView, deleteUserButton, editRolesButton, resetUserButton, backButton);
    }

    /**********
     * This method fetches the list of users from the system and displays their details
     * (like username, full name, email, and roles) in the user list view
     * It prepares the information in a readable format for the admin to view
     */
    
    private void populateUserList(){
        userListView.getItems().clear(); 
        
        for (User user : UserManagement.getInstance().getUsers()) {
            String userDetails = "Username: " + user.getUsername() + " | Name: " + user.getFullName() + " | Email: " + user.getEmail() + " | Roles: " + String.join(", ", user.getRoles()) +" | Invite Code: " + (user.getRegistrationCode() != null ? user.getRegistrationCode() : "N/A"); 
            userListView.getItems().add(userDetails);
        }
    }

    /**********
     * This method displays a dialog for editing the roles of a selected user. The dialog
     * allows the admin to select or deselect the roles (Admin, Student, Instructor) for the user
     * Once confirmed, the roles are updated, and the user list is refreshed
     * 
     * @param user the user whose roles are being edited
     */
    
    private void showEditRolesDialog(User user){
        Dialog<Set<String>> dialog = new Dialog<>();
        dialog.setTitle("Edit Roles for " + user.getUsername());
        //Edit Roles Functions/All Options
        CheckBox adminRole = new CheckBox("Admin");
        adminRole.setSelected(user.getRoles().contains("Admin"));
        CheckBox studentRole = new CheckBox("Student");
        studentRole.setSelected(user.getRoles().contains("Student"));
        CheckBox instructorRole = new CheckBox("Instructor");
        instructorRole.setSelected(user.getRoles().contains("Instructor"));
        
        VBox rolesBox = new VBox(10);
        rolesBox.getChildren().addAll(adminRole, studentRole, instructorRole);
        
        dialog.getDialogPane().setContent(rolesBox);

        ButtonType confirmButtonType = new ButtonType("Update Roles", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
       
        dialog.setResultConverter(dialogButton -> { // Confirmation Dialogue
            if (dialogButton == confirmButtonType) {
                Set<String> newRoles = new HashSet<>();
                if (adminRole.isSelected()) newRoles.add("Admin");
                if (studentRole.isSelected()) newRoles.add("Student");
                if (instructorRole.isSelected()) newRoles.add("Instructor");
                return newRoles;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newRoles -> {
            user.getRoles().clear();
            user.getRoles().addAll(newRoles);
            UserManagement.getInstance().updateUser(user); 
            showInfoMessage("Roles updated successfully for " + user.getUsername());
        });
    }

    /**********
     * This method displays a confirmation dialog before deleting a selected user. If the admin 
     * confirms the deletion, the user is removed from the UserManagement system, and a message 
     * is displayed indicating the successful deletion.
     * 
     * @param username the username of the user to be deleted
     */
    
    private void showDeleteUserConfirmation(String username){ 
        if (UserManagement.getInstance().getUser(username) == null){
            showErrorMessage("User not found.");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete user: " + username + "?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete User Confirmation");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                UserManagement.getInstance().deleteUser(username);
                showInfoMessage("User deleted successfully.");
            }
        });
    }

    /**********
     * This method show confirmation dialog for resetting a user's account
     * If confirmed, the user's password is reset to an empty value
     * and the system is updated with the changes.
     * 
     * @param username the username of the user whose account is to be reset
     */
    
    private void showResetUserAccountDialog(String username){
        User user = UserManagement.getInstance().getUser(username);
        
        if (user == null) {
            showErrorMessage("User not found.");
            return;
        }

        String tempPassword = PasswordResetManager.getInstance().generateTemporaryPassword(username);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Temporary password for user " + username + ": " + tempPassword + "\nThis password will expire in 1 hour.", ButtonType.OK);
        alert.setTitle("Temporary Password Generated");
        alert.setHeaderText("Password Reset Successful");
        alert.show();
    }

    /**********
     * This method is the function which if an alert box is needed 
     * it will print out the error message set to the alert. 
     * 
     * @param message which is the alert that appears with the error message.
     */
    
    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }

    /**********
     * This method is the function which if an alert box is needed
     * it will print out the message taken, such as "Temporary Password Generated" and such.
     * 
     * @param message which is the alert that appears with the accurate message 
     */
    
    private void showInfoMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}