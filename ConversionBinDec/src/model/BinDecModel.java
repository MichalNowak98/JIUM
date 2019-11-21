/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores values to be converted in list. Enables to add and get value with methods, convert them
 * to decimal and binary numeral system and verifies correctness of assigned value.
 * @author Michal Nowak
 * @version 2.1 
 * @since 20-10-2019
 */
public class BinDecModel {
    /** List of String representations of values in decimal or binary numeral system. */
    private List<String> values;
    /** String representation of last converted value in decimal or binary numeral system. */
    private List<String> convertedValues;
    
    public BinDecModel() {
        values = new ArrayList();
        convertedValues = new ArrayList();
    }
    /**
     * Clears <code>values</code> List
     */
    public void clearValues() {
        if(!values.isEmpty()){
            values.clear();
        }
        if(!convertedValues.isEmpty()){
            convertedValues.clear();
        }
    }
    public int getValuesSize() {
        return values.size();
    }
    public String[] getValues() {
        return values.toArray(new String[0]);
    }
    public String[] getConvertedValues() {
        return convertedValues.toArray(new String[0]);
    }
    public String getValue(int index) {
        return values.get(index);
    }
    public void addConvertedValue(String convertedValue) {
        convertedValues.add(convertedValue);
    }
    public String getConvertedValue(int index) {
        return convertedValues.get(index);
    }
    /**
     * Verifies correctness of passed value before adding it to <code>values</code> List. If <code>value</code> is correct decimal or binary number, it is added to List. In other case, it throws an <code>IncorrectNumberException</code> exception.
     * @param value String representation of value to be converted.
     * @throws IncorrectNumberException Exception informing, that passed <code>value</code> is incorrect - neither decimal nor binary number.
     */
    public void addValue(String value) throws IncorrectNumberException {
        boolean verifiedNumber = verifyNumber(value);
        if(!verifiedNumber) {
            throw new IncorrectNumberException();            
        }
        values.add(value);
    }
    /**
     * Verifies correctness of passed <code>value</code>. Returns <code>true</code> if number is verified - correct binary or decimal number. In other case, it returns <code>false</code>.
     * @param value String representation of value to be verified.
     * @return <code>true</code> if verified, <code>false</code> if unverified
     */
    private boolean verifyNumber(String value) {
        boolean verifiedNumber = true;
        //check if value is long enough to store binary number (3 charaters, e.g. 0b1)
        if(value.length() >= 3) {
            // check if binary
            if( value.charAt(0) == '0' && (value.charAt(1) == 'b' || value.charAt(1) == 'B') ) {
                // check if number contains only '1' and '0' (starting from 2, because first two elements are '0' and 'b')
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
    /**
     * Converts <code>values[index]</code> from decimal numeral system to binary numeral system.
     * @param index Index of <code>values</code> element to be converted.
     * @return String representation of converted value.
     */
    public String convertDecToBin(int index) {
        //value is verified at this point. Check only if value is in decimal numeral system
        if(values.get(index).length() > 2) {
            if(values.get(index).charAt(0) == '0' && (values.get(index).charAt(1) == 'b' || values.get(index).charAt(1) == 'B')) {
                return values.get(index);
            }
        }
        int binValue = Integer.parseInt(values.get(index));
        return "0b" + Integer.toBinaryString(binValue);
    }
    /**
     * Converts <code>values[index]</code> from binary numeral system to decimal numeral system.
     * @param index Index of <code>values</code> element to be converted.
     * @return String representation of converted value.
     */
    public String convertBinToDec(int index) {
        //value is verified at this point. Check only if value is in binary numeral system
        if(values.get(index).length() <= 2) {
            return values.get(index);
        } else if (values.get(index).charAt(1) != 'b' && values.get(index).charAt(1) != 'B') {
            return values.get(index);
        }
        int multiplier = 1;
        int decValue = 0;
        for (int i = values.get(index).length() - 1; i > 1; i--) {
            decValue += Character.getNumericValue(values.get(index).charAt(i)) * multiplier;
            multiplier *= 2;
        }
        return Integer.toString(decValue);
    } 
}
