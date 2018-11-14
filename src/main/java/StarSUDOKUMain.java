/* Main created on 10.07.2006 */

import com.devng.starsudoku.GV;
import com.devng.starsudoku.gui.SudokuMainFrame;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class StarSUDOKUMain {

    public static void main(String[] args) throws Exception {
        final String os = System.getProperty("os.name").toLowerCase();

        if(os.startsWith("windows")) {
            // The system Look and Feel only looks good on Windows
            UIManager.setLookAndFeel(GV.LAF.SYSTEM.getLookAndFeelClassName());
            GV.useSystemLAF = true;
        } else {
            UIManager.setLookAndFeel(GV.LAF.METAL.getLookAndFeelClassName());
        }
        
        final String jVer = System.getProperty("java.version");
        if(jVer.compareToIgnoreCase("1.8.0") < 0) {
            String errorMsg = "<HTML><FONT COLOR=\"RED\">Your Java version is too old!</FONT><BR><BR> " +
                    "Star SUDOKU requires JAVA Runtime Environment JRE 1.8.0 / JRE 8 or above.<BR> " +
                    "Please download the latest JRE at <FONT COLOR=\"#0000ff\">www.java.com</FONT></HTML>";
            JOptionPane.showMessageDialog(null,
                  errorMsg, "Fatal error",
                  JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        final SudokuMainFrame f = new SudokuMainFrame();
        f.generateNewSudoku();
        f.setVisible(true);
    }
}
