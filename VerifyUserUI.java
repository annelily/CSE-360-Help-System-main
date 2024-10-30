package cse360Phase1Project;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * <p> Title: Verify User UI </p>
 * 
 * <p> Description: This class provides a user interface for verifying a user by entering a 
 * verification code. It interacts with the `UserManagementApp` and updates the user's verification 
 * status based on the inputted code. </p>
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

public class VerifyUserUI{
    private UserManagementApp mainApp;
    
    /**
     * Constructor for the VerifyUserUI.
     * This method sets up the UI for verifying a user's account with a verification code
     * 
     * @param root 	the Pane to which all UI components are added
     * @param app 	the main application instance for interaction
     * @param user 	the user who is being verified
     */

    public VerifyUserUI(Pane root, UserManagementApp app, User user){
        this.mainApp = app;
        
        Label TitleLabel = new Label("Enter Verification Code"); // enter verification code label
        TitleLabel.setLayoutX(200);
        TitleLabel.setLayoutY(50);

        Label CodeLabel = new Label("Verification Code:"); // verification code label
        CodeLabel.setLayoutX(150);
        CodeLabel.setLayoutY(150);

        TextField text_Code = new TextField(); // enter verification role
        text_Code.setLayoutX(300);
        text_Code.setLayoutY(145);

        Button VerifyButton = new Button("Verify"); // verify button
        VerifyButton.setLayoutX(250);
        VerifyButton.setLayoutY(200);
        VerifyButton.setOnAction(e -> { 
        	String enteredCode = text_Code.getText().trim();
        	
            if (enteredCode.equals(user.getRegistrationCode())){
                user.setVerified(true); 
                UserManagement.getInstance().updateUser(user);  
                mainApp.showRegistrationUI("", false); 
            } 
            
            else{
            	
                showErrorMessage("Invalid verification code. Please try again."); // invalid verification code prompt
            }
        });

        root.getChildren().addAll(TitleLabel, CodeLabel, text_Code, VerifyButton);
    }


    /**
     * Constructor for the VerifyUserUI
     * This method sets up the UI for verifying a user's account with a verification code
     * 
     * @param root 	the Pane to which all UI components are added
     * @param app 	the main application instance for interaction
     * @param user 	the user who is being verified
     */
    
    private void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}