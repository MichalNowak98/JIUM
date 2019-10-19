/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Stores value to be converted. Enables to get and set value with methods and
 * does passed value verification.
 * @author Michal
 * @version %I%, %G% 
 * @since
 */
public class BinDecModel {
    /**
     * String representation of value in decimal or binary numeral system.
     */
    private String value;
    /**
     * Returns String representation of value
     * @return String representation of value
     */
    public String getValue() {
        return value;
    }
    /**
     * Verifies correctness of passed value before assigning it. If value is correct decimal or binary number, it is assigned to this.value variable. In other case, it throws an IncorrectNumberException exception.
     * @param value String representation of value to be converted.
     * @throws IncorrectNumberException Exception informing, that passed value is incorrect - neither decimal nor binary number
     */
    public void setValue(String value) throws IncorrectNumberException {
        boolean verifiedNumber = verifyNumber(value);
        if(!verifiedNumber) {
            throw new IncorrectNumberException();            
        }
        this.value = value;
    }
    /**
     * Verifies correctness of passed value. Returns true if number is verified - correct binary or decimal number. In other case, it returns false.
     * @param value String representation of value to be verified.
     * @return true - verified, false - unverified
     */
    private boolean verifyNumber(String value) {
        boolean verifiedNumber = true;
        //check if value is long enough to store binary number (3 charaters, etc. 0b1)
        if(value.length() >= 3) {
            // check if binary
            if( value.charAt(0) == '0' && (value.charAt(1) == 'b' || value.charAt(1) == 'B') ) {
                // check if number contains only '1' and '0' (first two elements are '0' and 'b'
                for(int i = 2; i < value.length(); i++) {
                    if(value.charAt(i) != '0' && value.charAt(i) != '1') {
                        verifiedNumber = false;
                    }
                }
            }
            else {
                // check if number contains only digits (is correct decimal number)
                for(int i = 0; i < value.length(); i++) {
                    if(!Character.isDigit(value.charAt(i))) {
                        verifiedNumber = false;
                    }
                }
            }
        }
        else {
            // check if number contains only digits (is correct decimal number)
            for(int i = 0; i < value.length(); i++) {
                if(!Character.isDigit(value.charAt(i))) {
                    verifiedNumber = false;
                }
            }
        }
        return verifiedNumber;
    }
       
}
