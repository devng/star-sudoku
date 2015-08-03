package net.sourceforge.starsudoku;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * Global Variabls
 * 
 * @author Nikolay G. GEorgiev
 */
public class GV {
    
    public static final String NAME = "Star SUDOKU ";
    
    public static final String VERSION = "0.8.1 Beta";

    public static final Color BORDER_COLOR = new Color(184, 207, 229);
    
    public static final Color GRID_COLOR_1 = new Color(230,255,255);
    
    public static final Color GRID_COLOR_2 = BORDER_COLOR;
    
    public static final Color GRID_COLOR_2_SELC = new Color(160,220,110);
    
    public static final Color GRID_COLOR_1_SELC = new Color(160,220,110);

    public static final Dimension DIM_RA = new Dimension(3, 1);

    public static final String IMG_FOLDER = "/res/img/";
    
    //SudokuGui Vars
    public final static Font FONT_BIG = new Font("Tahoma", Font.BOLD, 32);
    
    public final static int FONT_BIG_H = 15;
    
    public final static int FONT_BIG_W = 37;
    
    public final static Font FONT_S = new Font("Tahoma", Font.BOLD, 11);
    
    public final static int H1 = 12;
    
    public final static int W1 = 3;
    
    public final static int H2 = 29;
    
    public final static int W2 = 22;
    
    public final static int H3 = 48;
    
    public final static int W3 = 42;
    
    public final static int W4 = 35;
    
    public final static Border BORDER_RAISED = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    public final static Border BORDER_LOWERED = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
    
    public static enum NumberEntry {sctn, sntc};
    
    public final static int DIFF_EASY = 34;
    public final static int DIFF_NORMAL = 30;
    public static final int DIFF_HARD = 26;
    public static final int DIFF_DESIGN = -1;
    
    public enum NumDistributuon {evenlyDistributedNumbers, evenlyFilled3x3Square3, random};
    
    public final static String ERROR_LOAD = "Sudoku could not be loaded!";
    
    public final static String ERROR_SAVE = "Sudoku has not been saved!";
    
    public final static String ERROR_EXPORT = "Sudoku has not been exported";
    
    public final static String ERROR_PLAY = "The program was unable to determinate \nthat this was a valid Sudoku!";
    
    public final static String ERROR_NUMBER = "Invalid Number!\nEnter a number between 1 and 81";
    
    public final static String INFO_NUMBER = "Enter a number between 1 and 81";
    
    public final static String INFO_SOLVED = "Sudoku is already solved.";
    
    public final static String INFO_SUCCESS = "Sudoku solved :)";
    
    //LOOK AND FEEL
    public enum LAF{METAL, SYSTEM};

    public static boolean isWin = false;
    
    public final static String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";

    public final static String SYSTEM = UIManager.getSystemLookAndFeelClassName();
    
    //public final static String MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    //public final static String WIN = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    //public final static String GTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; 
    
}
