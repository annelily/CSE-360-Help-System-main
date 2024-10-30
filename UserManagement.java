package cse360Phase1Project;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: User Management </p>
 * 
 * <p> Description: This class manages the list of users in the system. It provides methods 
 * to register users, retrieve user details, validate login credentials, update users, 
 * and delete users. It follows the singleton pattern, ensuring only one instance of 
 * UserManagement exists throughout the system. </p>
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

public class UserManagement{
    private static UserManagement instance; 
    private List<User> users; 

    
    /**
     * This method returns the singleton instance of UserManagement
     * If the instance is null, it creates a new instance
     * 
     * @return the singleton instance of UserManagement
     */
    
    private UserManagement(){
        users = new ArrayList<>();
    }

    public static UserManagement getInstance(){
        if (instance == null) {
            instance = new UserManagement();
        }
        return instance;
    }

    /**
     * This method registers a new user in the system
     * If a user with the same username already exists, it replaces the old user with the new one
     * 
     * @param user 	the user to be registered
     */
    
    public void registerUser(User user){
        users.removeIf(existingUser -> existingUser.getUsername().equalsIgnoreCase(user.getUsername()));
        users.add(user); 
    }

    
    /**
     * This method retrieves a user by their username
     * 
     * @param username 	the username of the user to retrieve
     * @return the User object if found, or null if no user matches the username
     */
    
    public User getUser(String username){
        for (User user : users){
        	
            if (user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        
        return null; 
    }

    /**
     * This method validates the username and password combination for login
     * 
     * @param username 	the username to validate
     * @param password 	the password to validate
     * @return True if the username and password match, otherwise, false 
     */
    
    public boolean validateUser(String username, String password){
        User user = getUser(username);
        return user != null && user.getPassword().equals(password);
    }
    
    /**
     * This method retrieves a user by their registration code and first name
     * 
     * @param code 	the registration code of the user
     * @param name 	the first name of the user
     * @return The User object if found, or null if no match is found
     */
    
    public User getUserByCodeAndName(String code, String name){
        for (User user : users){
               if (user.getRegistrationCode().equals(code) && (user.getFirstName() != null && user.getFirstName().equalsIgnoreCase(name))) {
                return user;
            }
        }
        
        return null; 
    }

    /**
     * This method returns the list of all users in the system
     * 
     * @return The list of User objects
     */
    
    public List<User> getUsers(){
        return users;
    }


    /**
     * This method deletes a user by their username
     * 
     * @param username 	the username of the user to delete
     * @return True if the user was successfully deleted, false if the user was not found
     */
    
    public boolean deleteUser(String username){
        return users.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
    }

    /**
     * This method updates an existing user's information in the system
     * It searches for the user by their username and replaces their details with the updated information
     * 
     * @param updatedUser the user object with updated information
     */
    
    public void updateUser(User updatedUser){
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equalsIgnoreCase(updatedUser.getUsername())){
                users.set(i, updatedUser); 
                return;
            }
        }
    }
    
    /**
     * This method checks if a user with a specific username exists in the system
     * 
     * @param username the username to check
     * @return True if the user exists, otherwise, false 
     */
    
    public boolean userExists(String username){
        return getUser(username) != null;
    }
}