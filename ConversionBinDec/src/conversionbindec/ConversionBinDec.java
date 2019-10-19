/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversionbindec;

import controller.BinDecController;
import model.BinDecModel;
import view.BinDecView;

/**
 * Takes input from user through console input, text file or command line arguments
 * and converts them to decimal or binary numeral system.
 * @author Michal
 * @version %I%, %G% 
 * @since
 */
public class ConversionBinDec {
    /**
     * @param args Contains numbers for conversion, both decimal and binary. Order does not matter.
     */
    public static void main(String[] args) {
        BinDecModel model = new BinDecModel();
        BinDecView view = new BinDecView();
        BinDecController controller = new BinDecController(view, model);
        controller.convert(args.length, args);
        controller.runConsoleInterface();
    }
    
}
