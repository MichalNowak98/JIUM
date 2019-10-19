/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Occurs when incorrect number is passed to model.
 * @author Michal
 * @version %I%, %G% 
 * @since
 */
public class IncorrectNumberException extends Exception {
    /**
     * Returns message containing cause of throwing exception.
     * @return Exception message
     */
    @Override
    public String getMessage() {
        return "Incorrect number. Only decimal and binary allowed.";
    }
}
