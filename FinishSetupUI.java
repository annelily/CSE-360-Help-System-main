package cse360Phase1Project;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * <p> Title: Finish Setup UI </p>
 * 
 * <p> Description: A user interface for completing the setup of a user's account within
 * the CSE360 Phase 1 Project. It allows users to enter their personal details and finish
 * the account setup process </p>
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

public class FinishSetupUI{
	 private UserManagementApp mainApp;
	 
	/**
     * Constructor for the FinishSetupUI class
     * This method sets up the UI for the final account setup process, allowing the user to
     * input their first name, middle name, last name, and email, and then finish the setup
     * 
     * @param root  the Pane to which all UI components are added
     * @param app   the main application instance for interaction
     * @param user  the user whose account is being set up.
     */

    public FinishSetupUI(Pane root, UserManagementApp app, User user){
        mainApp = app;
        
        Label titleLabel = new Label("Finish Setting Up Your Account"); // Finish Setting Up Your Account Label
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(50);

        Label firstNameLabel = new Label("First Name:"); // First Name Label
        firstNameLabel.setFont(new Font("Arial", 16));
        firstNameLabel.setLayoutX(100);
        firstNameLabel.setLayoutY(120);

        TextField firstNameText = new TextField(); // Enter First Name
        firstNameText.setFont(new Font("Arial", 16));
        firstNameText.setLayoutX(200);
        firstNameText.setLayoutY(115);

        Label middleNameLabel = new Label("Middle Name:"); // Middle Name Label
        middleNameLabel.setFont(new Font("Arial", 16));
        middleNameLabel.setLayoutX(100);
        middleNameLabel.setLayoutY(180);

        TextField middleNameText = new TextField(); // Enter Middle Name
        middleNameText.setFont(new Font("Arial", 16));
        middleNameText.setLayoutX(200);
        middleNameText.setLayoutY(175);

        Label lastNameLabel = new Label("Last Name:"); // Last Name Label
        lastNameLabel.setFont(new Font("Arial", 16));
        lastNameLabel.setLayoutX(100);
        lastNameLabel.setLayoutY(240);

        TextField lastNameText = new TextField(); // Enter Last Name
        lastNameText.setFont(new Font("Arial", 16));
        lastNameText.setLayoutX(200);
        lastNameText.setLayoutY(235);

        Label emailLabel = new Label("Email:"); // Email Label
        emailLabel.setFont(new Font("Arial", 16));
        emailLabel.setLayoutX(100);
        emailLabel.setLayoutY(300);

        TextField emailText = new TextField(); // Enter Email
        emailText.setFont(new Font("Arial", 16));
        emailText.setLayoutX(200);
        emailText.setLayoutY(295);

        Button finishSetUpButton = new Button("Finish Setup"); // Button for clicking Finish Setup when properly filling in all the information above
        finishSetUpButton.setFont(new Font("Arial", 16));
        finishSetUpButton.setLayoutX(200);
        finishSetUpButton.setLayoutY(360);
        finishSetUpButton.setOnAction(e -> {
            user.setFirstName(firstNameText.getText());
            user.setMiddleName(middleNameText.getText());
            user.setLastName(lastNameText.getText());
            user.setEmail(emailText.getText());

            UserManagement.getInstance().updateUser(user);
            mainApp.showRoleSelectionUI(user);
        });

        root.getChildren().addAll(titleLabel, firstNameLabel, firstNameText, middleNameLabel, middleNameText, lastNameLabel, lastNameText, emailLabel, emailText, finishSetUpButton);
    }
}