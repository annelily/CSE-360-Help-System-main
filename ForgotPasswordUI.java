package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

/**
 * <p> Title: Forgot Password UI </p>
 * 
 * <p> Description: A user interface that allows users to reset their password by providing 
 * their username, a temporary password, and a new password. If the provided details are correct, 
 * the user's password is successfully reset </p>
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
 */

public class ForgotPasswordUI {
    private UserManagementApp mainApp;
    
    /**
     * Constructor for the ForgotPasswordUI class.
     * This method sets up the UI for the Forgot Password screen, where users can reset their 
     * password by entering their username, a temporary password, and a new password
     * 
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     */

    public ForgotPasswordUI(Pane root, UserManagementApp app){
        this.mainApp = app;

        Label titleLabel = new Label("Forgot Password"); // forget password label
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(50);

        Label usernameLabel = new Label("Username:"); // username label
        usernameLabel.setLayoutX(100);
        usernameLabel.setLayoutY(100);

        TextField usernameField = new TextField(); // enter username
        usernameField.setLayoutX(200);
        usernameField.setLayoutY(95);

        Label tempPasswordLabel = new Label("Temporary Password:"); // temporary password label
        tempPasswordLabel.setLayoutX(100);
        tempPasswordLabel.setLayoutY(150);

        TextField tempPasswordField = new TextField(); // enter temporary password
        tempPasswordField.setLayoutX(250);
        tempPasswordField.setLayoutY(145);

        Label newPasswordLabel = new Label("New Password:"); // new password label
        newPasswordLabel.setLayoutX(100);
        newPasswordLabel.setLayoutY(200);

        PasswordField newPasswordField = new PasswordField(); // enter password
        newPasswordField.setLayoutX(250);
        newPasswordField.setLayoutY(195);

        Label confirmPasswordLabel = new Label("Confirm Password:"); // confirm password label
        confirmPasswordLabel.setLayoutX(100);
        confirmPasswordLabel.setLayoutY(250);

        PasswordField confirmPasswordField = new PasswordField(); // enter password again to confirm
        confirmPasswordField.setLayoutX(250);
        confirmPasswordField.setLayoutY(245);

        Button submitButton = new Button("Submit"); // submit button
        submitButton.setLayoutX(250);
        submitButton.setLayoutY(300);
        submitButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String tempPassword = tempPasswordField.getText().trim();
            String newPassword = newPasswordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            // All Messages presented below after Clicking Submit
            if (username.isEmpty() || tempPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
                showErrorMessage("All fields are required."); // If missing a field
                return;
            }

            if (!newPassword.equals(confirmPassword)){
                showErrorMessage("Passwords do not match."); // passwords don't match
                return;
            }

            if (PasswordResetManager.getInstance().validateTemporaryPassword(username, tempPassword)){
                User user = UserManagement.getInstance().getUser(username);
                
                if (user != null){
                    user.setPassword(newPassword);
                    UserManagement.getInstance().updateUser(user);
                    PasswordResetManager.getInstance().removeTemporaryPassword(username);
                    showInfoMessage("Password has been successfully reset."); // reset password prompt
                    mainApp.showLoginUI(); 
                } 
                
                else{
                	
                    showErrorMessage("User not found."); // user not found
                }
            } 
            
            else{
            	
                showErrorMessage("Invalid or expired temporary password."); // invalid or expired temporary password
            }
        });

        Button backButton = new Button("Back"); //Implementation of a Back Button
        backButton.setLayoutX(150);
        backButton.setLayoutY(300);
        backButton.setOnAction(e -> mainApp.showLoginUI()); 
        root.getChildren().addAll(titleLabel, usernameLabel, usernameField, tempPasswordLabel, tempPasswordField, newPasswordLabel, newPasswordField, confirmPasswordLabel, confirmPasswordField, submitButton, backButton);
    }

    /**
     * This method displays an error message in an alert dialog
     * 
     * @param message 	the error message to be displayed
     */
    
    private void showErrorMessage(String message){ // error messages
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }

    /**
     * This method displays an informational message in an alert dialog
     * 
     * @param message 	the informational message to be displayed
     */
    
    private void showInfoMessage(String message){ //info messages
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}