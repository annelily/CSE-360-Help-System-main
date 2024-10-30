package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

/**
 * <p> Title: Login UI </p>
 * 
 * <p> Description: A user interface that allows users to log in by entering their username and 
 * password. It provides options to register a new account or reset the password if forgotten </p>
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

public class LoginUI{
    private TextField usernameText = new TextField();
    private PasswordField passwordText = new PasswordField();
    private UserManagementApp mainApp;
    private Label statusLabel;

    /**
     * Constructor for the LoginUI class
     * This method sets up the user interface for the login screen, including the login form, 
     * registration option, and forgot password feature.
     * 
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     */

    public LoginUI(Pane root, UserManagementApp app){
        this.mainApp = app;

        Label titleLabel = new Label("User Login"); // user login label
        titleLabel.setLayoutX(250);
        titleLabel.setLayoutY(50);

        Label usernameLabel = new Label("Username:"); // username label
        usernameLabel.setLayoutX(150);
        usernameLabel.setLayoutY(120);

        usernameText.setLayoutX(250);
        usernameText.setLayoutY(115);

        Label passwordLabel = new Label("Password:"); // password label
        passwordLabel.setLayoutX(150);
        passwordLabel.setLayoutY(180);

        passwordText.setLayoutX(250);
        passwordText.setLayoutY(175);

        statusLabel = new Label();
        statusLabel.setLayoutX(250);
        statusLabel.setLayoutY(250);

        Button loginButton = new Button("Login"); // login button
        loginButton.setLayoutX(250);
        loginButton.setLayoutY(220);
        loginButton.setOnAction(e -> handleLoginButton());

        Button registerButton = new Button("Register"); // register button
        registerButton.setLayoutX(350);
        registerButton.setLayoutY(220);
        registerButton.setOnAction(e -> handleRegisterButton());

        Button forgotButton = new Button("Forgot Password?"); // forgot password button
        forgotButton.setLayoutX(250);
        forgotButton.setLayoutY(260);
        forgotButton.setOnAction(e -> mainApp.showForgotPasswordUI());

        root.getChildren().addAll(titleLabel, usernameLabel, usernameText, passwordLabel, passwordText, loginButton, registerButton, forgotButton, statusLabel);
    }

    /**
     * This method executes when the login button is clicked
     * It validates the username and password fields, checks the credentials, and updates the status label
     */
    
    private void handleLoginButton(){ // parameters for when the login button is clicked
        String username = usernameText.getText().trim();
        String password = passwordText.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            showError("All fields are required!"); // if username or password is empty, display all fields are required!
            return;
        }

        if (UserManagement.getInstance().validateUser(username, password)){
            showSuccess("Login successful!"); // if validated, show login successful!
            User user = UserManagement.getInstance().getUser(username);
            mainApp.handleLogin(user);  
        } 
        
        else{
        	
            showError("Invalid username or password!"); // else invalid username or password
        }
    }

    /**
     * This method executes when the register button is clicked
     * It determines if an admin account needs to be created and directs to the registration screen
     */
    
    private void handleRegisterButton(){
        if (UserManagement.getInstance().getUsers().isEmpty()){
            mainApp.showRegistrationUI("Admin", true);
        } 
        
        else{
        	
            mainApp.showRegistrationUI("", false); 
        }
    }

    private void showError(String message){
    	statusLabel.setText(message);
    	statusLabel.setStyle("-fx-text-fill: red;");
    }

    /**
     * This method displays a success message in the status label
     * it changes the label color to green and updates the message
     * 
     * @param message The success message to be displayed.
     */
    
    private void showSuccess(String message){
    	statusLabel.setText(message);
    	statusLabel.setStyle("-fx-text-fill: green;");
    }
}