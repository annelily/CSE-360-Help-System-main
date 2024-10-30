package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;

/**
 * <p> Title: Code Login UI </p>
 * 
 * <p> Description: A user interface for log in with a verification code and name within
 * the CSE360 Phase 1 Project. It checks if the provided credentials are valid and allows the 
 * user to proceed with the setup process if successful </p>
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

public class CodeLoginUI {
    private UserManagementApp mainApp;
    
    /**
     * Constructor for the CodeLoginUI class
     * This method sets up the login screen UI, where users can enter their verification code and name
     * 
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     */


    public CodeLoginUI(Pane root, UserManagementApp app){
        this.mainApp = app;

        Label titleLabel = new Label("Login with Verification Code"); // login with Verification Code Label
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(50);

        TextField codeText = new TextField(); // insert verification code
        codeText.setLayoutX(250);
        codeText.setLayoutY(115);

        Label nameLabel = new Label("Name:"); // name Label
        nameLabel.setLayoutX(100);
        nameLabel.setLayoutY(180);

        TextField nameText = new TextField(); // insert name
        nameText.setLayoutX(250);
        nameText.setLayoutY(175);

        Button loginButton = new Button("Login"); // login button
        loginButton.setLayoutX(250);
        loginButton.setLayoutY(250);
        loginButton.setOnAction(e -> {
            String enteredCode = codeText.getText().trim();
            String enteredName = nameText.getText().trim();

            User user = UserManagement.getInstance().getUserByCodeAndName(enteredCode, enteredName);

            if (user != null && !user.isVerified()){
                user.setVerified(true);  
                UserManagement.getInstance().updateUser(user);
                mainApp.showFinishSetupUI(user);  
            } 
            
            else{
            	
                showErrorMessage("Invalid code or name. Please try again."); // error if login is not correct
            }
        });

        root.getChildren().addAll(titleLabel, codeText, nameLabel, nameText, loginButton);
    }

    
    /**
     * This method displays an error message in an alert dialog
     * 
     * @param message the error message to be displayed
     */
    
    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}