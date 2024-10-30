package cse360Phase1Project;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * <p> Title: Student/Instructor Home UI </p>
 * 
 * <p> Description: This class provides the home page interface for students and instructors. 
 * It displays a welcome message and provides an option to log out and returning the user to the login screen. </p>
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

public class StudentInstructorUI{
    private UserManagementApp mainApp;

    
    /**
     * Constructor for the StudentInstructorUI class
     * This method sets up the interface for students and instructors with a welcome message and a logout button
     * 
     * @param root  	the Pane to which all UI components are added
     * @param app   	the main application instance for interaction
     */
    
    public StudentInstructorUI(Pane root, UserManagementApp app){
        this.mainApp = app;

        Label titleLabel = new Label("Welcome to the Home Page"); // label for welcome to home page for student or instructor
        titleLabel.setFont(new Font("Arial", 24));
        titleLabel.setLayoutX(150);
        titleLabel.setLayoutY(50);

        Button logoutButton = new Button("Logout"); // logout button
        logoutButton.setFont(new Font("Arial", 16));
        logoutButton.setLayoutX(250);
        logoutButton.setLayoutY(150);
        logoutButton.setOnAction(e -> mainApp.showLoginUI());
        
        root.getChildren().addAll(titleLabel, logoutButton);
    }
}