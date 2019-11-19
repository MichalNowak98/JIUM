/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Occurs when incorrect number is passed to model.
 * @author Michal Nowak
 * @version 2.0
 * @since 20-10-2019
 */
public class IncorrectNumberException extends Exception {
    /**
     * Calls base class constructor, passing <code>"Incorrect number, only decimal and binary allowed"</code> as argument.
     */
    public IncorrectNumberException() {
        super("Incorrect number, only positive decimal and binary allowed");
    }
}
