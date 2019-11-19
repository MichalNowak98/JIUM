/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversionbindec;

import controller.BinDecController;
import controller.Controller;
import model.BinDecModel;
import view.BinDecView;

/**
 * Takes input from user through console input, text file or command line arguments
 * and converts them to decimal or binary numeral system.
 * @author Michal Nowak
 * @version 2.0 
 * @since 20-10-2019
 */
public class ConversionBinDec {
    /**
     * Assigns model and view to controller. Calls methods from controller.
     * @param args Contains numbers for conversion, both decimal and binary. Order does not matter.
     */
    public static void main(String[] args) {
        BinDecModel model = new BinDecModel();
        BinDecView view = new BinDecView();
        Controller controller = new BinDecController(view, model);
        controller.convert(args);
        controller.runConsoleInterface();
    }
}
