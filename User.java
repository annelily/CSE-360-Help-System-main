package cse360Phase1Project;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Title: User </p>
 * 
 * <p> Description: This class represents a user in the system. It stores the user's details 
 * such as username, password, name, email, and roles. The class provides methods to set and get 
 * these attributes, verify the user, and manage their roles. It also supports generating the 
 * full name of the user. </p>
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

public class User {
    private String username; 
    private String password; 
    private String firstName; 
    private String middleName; 
    private String lastName; 
    private String preferredName; 
    private String email; 
    private boolean isVerified; 
    private String registrationCode; 
    private List<String> roles; 

    /**
     * Constructor for creating a new user with a username and password
     * Initializes the user with no roles and an unverified status
     * 
     * @param username 	the username of the user
     * @param password 	the password of the user
     */

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.isVerified = false;  
    }

    public String getUsername(){
        return username;
    }
   
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getMiddleName(){
        return middleName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getPreferredName(){
        return preferredName;
    }

    public void setPreferredName(String preferredName){
        this.preferredName = preferredName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public boolean isVerified(){
        return isVerified;
    }

    public void setVerified(boolean verified){
        isVerified = verified;
    }

    public String getRegistrationCode(){
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode){
        this.registrationCode = registrationCode;
    }

    public List<String> getRoles(){
        return roles;
    }

    public void setRoles(List<String> roles){
        this.roles = roles;
    }

    /**
     * This method adds a new role to the user if it doesn't already exist
     * 
     * @param role The role to be added (Admin, Student, Instructor)
     */

    public void addRole(String role){
        if (!roles.contains(role)){
            roles.add(role);
        }
    }

    /**
     * This method returns the full name of the user
     * It combines the first name, middle name (if provided), and last name
     * 
     * @return the full name of the user.
     */
    
    public String getFullName(){
        String fullName = (firstName != null ? firstName : "") + (middleName != null ? " " + middleName : "") + (lastName != null ? " " + lastName : "");
        return fullName.trim();
    }

    
    /**
     * This method returns a string representation of the user, including the username,
     * roles, verification status, and registration code
     * 
     * @return The string representation of the user
     */
    
    public String toString(){
        return "User{" + "username='" + username + '\'' + ", roles=" + roles + ", isVerified=" + isVerified + ", registrationCode='" + registrationCode + '\'' +'}';
    }
}