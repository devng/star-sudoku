package com.devng.starsudoku;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Global Variables
 */
public class GV {

    public static final String NAME = "StarSUDOKU";

    // Keep in sync with the build.gradle
    public static final String VERSION = "1.0.1";

    public static final String WEB_PAGE = "https://github.com/devng/star-sudoku";

    public static final Color BORDER_COLOR = new Color(184, 207, 229);

    public static final Color GRID_COLOR_1 = new Color(230, 255, 255);

    public static final Color GRID_COLOR_2 = BORDER_COLOR;

    public static final Color GRID_COLOR_2_SELC = new Color(160, 220, 110);

    public static final Color GRID_COLOR_1_SELC = new Color(160, 220, 110);

    public static final Dimension DIM_RA = new Dimension(3, 1);

    public static final String IMG_FOLDER = "img/";

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

    public enum NumberEntry {sctn, sntc}

    public final static int DIFF_EASY = 34;
    public final static int DIFF_NORMAL = 30;
    public static final int DIFF_HARD = 26;
    public static final int DIFF_DESIGN = -1;

    public enum NumDistributuon {evenlyDistributedNumbers, evenlyFilled3x3Square3, random}

    public final static String ERROR_LOAD = "Sudoku could not be loaded!";

    public final static String ERROR_SAVE = "Sudoku has not been saved!";

    public final static String ERROR_EXPORT = "Sudoku has not been exported";

    public final static String ERROR_PLAY = "The program was unable to determinate \nthat this was a valid Sudoku!";

    public final static String ERROR_NUMBER = "Invalid Number!\nEnter a number between 1 and 81";

    public final static String INFO_NUMBER = "Enter a number between 1 and 81";

    public final static String INFO_SOLVED = "Sudoku is already solved.";

    public final static String INFO_SUCCESS = "Sudoku solved :)";

    //LOOK AND FEEL
    public enum LAF {
        NIMBUS("javax.swing.plaf.nimbus.NimbusLookAndFeel"),

        METAL("javax.swing.plaf.metal.MetalLookAndFeel"),

        SYSTEM(UIManager.getSystemLookAndFeelClassName());

        private final String lookAndFeelClassName;

        LAF(String lookAndFeelClassName) {
            this.lookAndFeelClassName = lookAndFeelClassName;
        }

        public String getLookAndFeelClassName() {
            return lookAndFeelClassName;
        }
    }

    public static boolean useSystemLAF = false;
}
