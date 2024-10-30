package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.HashSet;
import java.util.Set;


/**
 * <p> Title: Admin Login UI </p>
 * 
 * <p> Description: A user interface for the Admin login page, allowing management of users within
 * the CSE360 Phase 1 Project. This includes functionality for deleting users and managing roles. </p>
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

public class AdminLoginUI {
    private UserManagementApp mainApp;

    /**
     * Constructor for the AdminLoginUI class.
     * This method sets up the main UI for the Admin login page, which allows the user
     * to view and manage users, including deleting users and managing their roles
     * 
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     */
    
    public AdminLoginUI(Pane root, UserManagementApp app){
        this.mainApp = app;
        
        ListView<String> userListView = new ListView<>(); // User List View
        userListView.setLayoutX(50);
        userListView.setLayoutY(70);
        userListView.setPrefSize(500, 300);

        for (User user : UserManagement.getInstance().getUsers()){
            userListView.getItems().add(
                user.getUsername() + " - " + user.getFullName() + " - Roles: " + String.join(", ", user.getRoles())
            );
        }

        Button deleteUserButton = new Button("Delete User"); // Delete User Button
        deleteUserButton.setLayoutX(50);
        deleteUserButton.setLayoutY(400);
        deleteUserButton.setOnAction(e -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            
            if (selectedUser != null){
                String username = selectedUser.split(" - ")[0];
                showDeleteUserConfirmation(username);
                userListView.getItems().remove(selectedUser); 
            } 
            
            else{
            	
                showErrorMessage("No user selected.");
            }
         });

        Button manageRolesButton = new Button("Manage Roles"); // Manage Roles Button
        manageRolesButton.setLayoutX(150);
        manageRolesButton.setLayoutY(400);
        manageRolesButton.setOnAction(e -> {
            String selectedUser = userListView.getSelectionModel().getSelectedItem();
            
            if (selectedUser != null){
                String username = selectedUser.split(" - ")[0];
                User user = UserManagement.getInstance().getUser(username);
                if (user != null) {
                    showManageRolesDialog(user, userListView);
                }
            } 
            
            else{
            	
                showErrorMessage("No user selected.");
            }
         });
        
        Button manageArticlesButton = new Button("Manage Help Articles");
        manageArticlesButton.setLayoutX(250);
        manageArticlesButton.setLayoutY(400);
        manageArticlesButton.setOnAction(e -> mainApp.showHelpArticleManagementUI());


        Button backButton = new Button("Back"); // Implementation for Back Button
        backButton.setLayoutX(250);
        backButton.setLayoutY(400);
        backButton.setOnAction(e -> mainApp.showRoleActionUI("Admin", mainApp.getCurrentUser()));

        root.getChildren().addAll(userListView, deleteUserButton, manageRolesButton, manageArticlesButton, backButton);
    }
    
    /**
     * this part displays a dialog that allows the admin to manage the roles of a user
     * 
     * @param user          the user whose roles are being managed.
     * @param userListView  the ListView where user information is displayed
     */

    private void showManageRolesDialog(User user, ListView<String> userListView){
        Dialog<Set<String>> dialog = new Dialog<>();
        dialog.setTitle("Manage Roles for " + user.getUsername());
        // Manage Roles and displaying all the roles
        CheckBox adminRole = new CheckBox("Admin");
        adminRole.setSelected(user.getRoles().contains("Admin"));
        CheckBox studentRole = new CheckBox("Student");
        studentRole.setSelected(user.getRoles().contains("Student"));
        CheckBox instructorRole = new CheckBox("Instructor");
        instructorRole.setSelected(user.getRoles().contains("Instructor"));

        VBox rolesBox = new VBox(10);
        rolesBox.getChildren().addAll(adminRole, studentRole, instructorRole);
        dialog.getDialogPane().setContent(rolesBox);

        ButtonType confirmButtonType = new ButtonType("Update Roles", ButtonBar.ButtonData.OK_DONE); // Update Roles BUtton
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType){
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
            userListView.getItems().clear();
            
            for (User updatedUser : UserManagement.getInstance().getUsers()){
                userListView.getItems().add(updatedUser.getUsername() + " - " + updatedUser.getFullName() + " - Roles: " + String.join(", ", updatedUser.getRoles()));
            }
        });
    }

    /**
     * Displays a confirmation dialog before deleting a selected user
     * 
     * @param username 	the username of the user to be deleted.
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
            if (response == ButtonType.YES){
                UserManagement.getInstance().deleteUser(username);
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.");
                infoAlert.show();
            }
        });
    }

    /**
     * Displays an error message in an alert dialog
     * 
     * @param message 	the error message to be displayed
     */
    
    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}