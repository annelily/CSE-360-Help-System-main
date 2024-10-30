package cse360Phase1Project;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> Title: Password Reset Manager </p>
 * 
 * <p> Description: This class manages temporary passwords for users who are resetting their passwords.
 * It generates temporary passwords, validates them, and removes them after use or expiration.
 * Temporary passwords expire after one hour </p>
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

public class PasswordResetManager{
    private static PasswordResetManager instance;
    private Map<String, TemporaryPassword> temporaryPasswords;
    
    /**
     * Private constructor for the PasswordResetManager class
     * Initializes the map to store temporary passwords
     */

    private PasswordResetManager(){
        temporaryPasswords = new HashMap<>();
    }

    /**
     * This method returns the singleton instance of PasswordResetManager
     * If it doesn't exist, a new instance is created
     * 
     * @return the single instance of PasswordResetManager
     */
    
    public static PasswordResetManager getInstance(){
        if (instance == null) {
            instance = new PasswordResetManager();
        }
        return instance;
    }

    /**
     * This method generates a temporary password for a given username.
     * The temporary password is a random 4-digit number and expires in one hour
     * 
     * @param   username The username for which the temporary password is generated
     * @return  the generated temporary password
     */
    
    public String generateTemporaryPassword(String username){
        String tempPassword = String.valueOf((int) (Math.random() * 9000) + 1000);
        TemporaryPassword tempPasswordObj = new TemporaryPassword(tempPassword, LocalDateTime.now().plusHours(1));
        temporaryPasswords.put(username, tempPasswordObj); 
        return tempPassword;
    }

    /**
     * This method validates a temporary password for a given username
     * It checks if the temporary password is correct and hasn't expired
     * 
     * @param username     	the username whose temporary password is being validated
     * @param tempPassword 	the temporary password entered by the user
     * @return true if the 	temporary password is valid, false otherwise
     */
    
    public boolean validateTemporaryPassword(String username, String tempPassword){
        TemporaryPassword storedTempPassword = temporaryPasswords.get(username);
        
        if (storedTempPassword != null){
        	
            if (storedTempPassword.getPassword().equals(tempPassword) && LocalDateTime.now().isBefore(storedTempPassword.getExpirationTime())) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * This method removes the temporary password for a given username
     * It is called after the password reset is successful or the temporary password expires
     * 
     * @param username 	the username whose temporary password is being removed
     */
    
    public void removeTemporaryPassword(String username){
        temporaryPasswords.remove(username);
    }

    /**
     * Inner class representing a temporary password with its expiration time
     */
    
    private class TemporaryPassword{
        private String password;
        private LocalDateTime expirationTime;

        public TemporaryPassword(String password, LocalDateTime expirationTime){
            this.password = password;
            this.expirationTime = expirationTime;
        }

        public String getPassword(){
            return password;
        }

        public LocalDateTime getExpirationTime(){
            return expirationTime;
        }
    }
}