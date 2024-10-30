package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * <p> Title: Registration UI </p>
 * 
 * <p> Description: This class provides the user interface for the registration process. 
 * Users can register an account by entering a username and password. If it's an invited 
 * user, a verification code is required. It also handles the initial admin registration process  </p>
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

public class RegistrationUI{
    private UserManagementApp mainApp;
    
    /**
     * Constructor for the RegistrationUI class
     * It sets up the registration screen with input fields for username, password, 
     * and (if applicable) a verification code for invited users.
     * 
     * @param root            the Pane to which all UI components are added
     * @param app             the main application instance for interaction
     * @param role            the role of the user being registered (e.g., Admin, Student)
     * @param primaryStage    the stage of the JavaFX application
     * @param isInitialAdmin  Boolean flag to indicate if the registration is for the initial admin
     */

    public RegistrationUI(Pane root, UserManagementApp app, String role, Stage primaryStage, boolean isInitialAdmin){
        this.mainApp = app;

        Label titleLabel = new Label("User Registration"); // user registration label
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setLayoutX(200);
        titleLabel.setLayoutY(50);

        Label usernameLabel = new Label("Username:"); // username label
        usernameLabel.setFont(new Font("Arial", 16));
        usernameLabel.setLayoutX(100);
        usernameLabel.setLayoutY(120);

        TextField usernameText = new TextField(); // enter username
        usernameText.setFont(new Font("Arial", 16));
        usernameText.setLayoutX(200);
        usernameText.setLayoutY(115);

        Label passwordLabel = new Label("Password:"); // password label
        passwordLabel.setFont(new Font("Arial", 16));
        passwordLabel.setLayoutX(100);
        passwordLabel.setLayoutY(180);

        PasswordField passwordText = new PasswordField(); // enter password
        passwordText.setFont(new Font("Arial", 16));
        passwordText.setLayoutX(200);
        passwordText.setLayoutY(175);

        Label confirmLabel = new Label("Confirm Password:"); // confirm password label
        confirmLabel.setFont(new Font("Arial", 16));
        confirmLabel.setLayoutX(100);
        confirmLabel.setLayoutY(240);

        PasswordField confirmText = new PasswordField(); // enter password again to verify
        confirmText.setFont(new Font("Arial", 16));
        confirmText.setLayoutX(250);
        confirmText.setLayoutY(235);

        TextField verificationCodeField = null;
        
        if (!isInitialAdmin){  // if it is not inital admin
            Label verificationCodeLabel = new Label("Verification Code:"); // verification code
            verificationCodeLabel.setFont(new Font("Arial", 16));
            verificationCodeLabel.setLayoutX(100);
            verificationCodeLabel.setLayoutY(300);

            verificationCodeField = new TextField(); // enter verification code
            verificationCodeField.setFont(new Font("Arial", 16));
            verificationCodeField.setLayoutX(250);
            verificationCodeField.setLayoutY(295);

            root.getChildren().addAll(verificationCodeLabel, verificationCodeField);
        }

        Button button_Register = new Button("Register"); // register button
        button_Register.setFont(new Font("Arial", 16));
        button_Register.setLayoutX(200);
        button_Register.setLayoutY(360);
        TextField finalVerificationCodeField = verificationCodeField; 

        button_Register.setOnAction(e -> { // register contains if not initial login
            String username = usernameText.getText().trim();
            String password = passwordText.getText().trim();
            String confirmPassword = confirmText.getText().trim();
            String enteredCode = finalVerificationCodeField != null ? finalVerificationCodeField.getText().trim() : "";

            if (!isInitialAdmin){ // if it is not the initial admin 
                User invitedUser = UserManagement.getInstance().getUser(username); 
                
                if (invitedUser == null || !invitedUser.getRegistrationCode().equals(enteredCode)){
                    showErrorMessage("Invalid verification code. Please enter the correct code provided by the Admin."); // invalid verification code
                    return;
                }
            }

            if (!password.equals(confirmPassword)){ // if the passwords don't match
                showErrorMessage("Passwords do not match."); 
                return;
            }
            
            User newUser = new User(username, password);
            
            if (isInitialAdmin){ // if it is first login, default admin
                newUser.addRole("Admin");
            } 
            
            else {
                User invitedUser = UserManagement.getInstance().getUser(username); 
                newUser.setRoles(invitedUser.getRoles()); 
                newUser.setRegistrationCode(invitedUser.getRegistrationCode()); 
            }
            
            UserManagement.getInstance().registerUser(newUser);
            showInfoMessage("User registered successfully."); // register successful
            mainApp.showLoginUI(); 
        });

        Button button_Back = new Button("Back"); // back button implementation
        button_Back.setFont(new Font("Arial", 16));
        button_Back.setLayoutX(300);
        button_Back.setLayoutY(360);
        button_Back.setOnAction(e -> mainApp.showLoginUI());

        root.getChildren().addAll(titleLabel, usernameLabel, usernameText, passwordLabel, passwordText, confirmLabel, confirmText, button_Register, button_Back);
    }

    /**
     * This method displays an error message in an alert dialog
     * 
     * @param message The error message to be displayed
     */
    
    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * This method displays an informational message in an alert dialog
     * 
     * @param message The informational message to be displayed
     */
    
    private void showInfoMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}