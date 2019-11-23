/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import model.BinDecModel;
import model.IncorrectNumberException;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Michal
 */
public class ModelJUnitTest {
    private BinDecModel model;
    private final String messageIncorrectNumberException = "Incorrect number, only positive decimal and binary allowed";
    
    /**
     * Empty constructor
     */
    public ModelJUnitTest() {
    }
    
    /**
     * Creates new BinDecModel for tests
     */
    @Before
    public void setUp() {
        model = new BinDecModel();
    }
    
    /**
     * Validates whether <code>addValue(String value)</code> function throws
     * <code>IncorrectNumberException</code> when value is incorrect
     */
    @Test
    public void setValueTest() {
        try  {
            model.addValue("4215w");
            //fails if exception was not thrown
            fail("Proper exception was not thrown as expected");
        } catch (Exception ex) {
            //fails if another exception was thrown
            assertThat(ex.getMessage(), is(messageIncorrectNumberException));
        }
        try  {
            model.addValue("0b4215");
            fail("Proper exception was not thrown as expected");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is(messageIncorrectNumberException));
        }
        try  {
            model.addValue("0x1001101");
            fail("Proper exception was not thrown as expected");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is(messageIncorrectNumberException));
        }
        try  {
            model.addValue("three");
            fail("Proper exception was not thrown as expected");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is(messageIncorrectNumberException));
        }
        try  {
            model.addValue("0b");
            fail("Proper exception was not thrown as expected");
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is(messageIncorrectNumberException));
        }
        model.clearValues();
    }
    /**
     * Validates adding and setting values to list with <code>setValue(String value)</code>
     * and <code>addValue(String value)</code> functions (both can throw <code>IncorrectNumberException</code>
     * if values are incorrect) and converting values with <code>convertDecToBin()</code> and
     * <code>convertBinToDec()</code> functions
     */
    @Test
    public void convertTest() {
        try {
            model.addValue("0");
            assertThat(model.convertDecToBin(model.getValuesSize() - 1), is("0b0"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown:" + ex.getMessage());
        }
        try {
            model.addValue("0b1");
            assertThat(model.convertBinToDec(model.getValuesSize() - 1), is("1"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("21");
            assertThat(model.convertDecToBin(model.getValuesSize() - 1), is("0b10101"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("0b11101");
            assertThat(model.convertBinToDec(model.getValuesSize() - 1), is("29"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("0b111");
            assertThat(model.convertDecToBin(model.getValuesSize() - 1), is("0b111"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("21");
            assertThat(model.convertBinToDec(model.getValuesSize() - 1), is("21"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("0");
            assertThat(model.convertBinToDec(model.getValuesSize() - 1), is("0"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
        try {
            model.addValue("0b1");
            assertThat(model.convertDecToBin(model.getValuesSize() - 1), is("0b1"));
            model.clearValues();
        } catch (IncorrectNumberException ex) {
            fail("Exception thrown" + ex.getMessage());
        }
    }
}
