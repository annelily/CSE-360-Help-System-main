package cse360Phase1Project;

import java.util.regex.Pattern;

/**
 * <p> Title: Password </p>
 * 
 * <p> Description: This class represents a password and provides methods to check if it meets 
 * certain security requirements. The password is validated based on criteria such as the presence 
 * of upper and lowercase letters, digits, special characters, and length  </p>
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

public class Password{
    private String value;
    
    public Password(String value){
        this.value = value;
    }
    
    public boolean meetsRequirements(){
        return hasUpperCase() && hasLowerCase() && hasDigit() && hasSpecialCharacter() && isLongEnough();
    }

    public boolean hasUpperCase(){
        return Pattern.compile("[A-Z]").matcher(value).find();
    }

    public boolean hasLowerCase(){
        return Pattern.compile("[a-z]").matcher(value).find();
    }

    public boolean hasDigit(){
        return Pattern.compile("\\d").matcher(value).find();
    }

    public boolean hasSpecialCharacter(){
        return Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(value).find();
    }

    public boolean isLongEnough(){
        return value.length() >= 8;
    }

    public String toString(){
        return value;
    }
}