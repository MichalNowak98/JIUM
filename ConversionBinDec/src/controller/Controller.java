/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * Contains methods allowing for conversion of passed parameters and 
 * passing input from user to program.
 * @author Michal Nowak
 * @version 2.0
 * @since 2-11-2019
 */
public interface Controller {
    /**
     * Converts passed String representations of decimal or binary values
     * @param value undefined number of String representations of numbers to be converted
     */
    public void convert(String ... value);
    /**
     * Runs user interface in console, letting user to communicate with the program.
     */
    public void runConsoleInterface ();
}
