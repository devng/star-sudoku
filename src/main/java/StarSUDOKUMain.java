/* Main created on 10.07.2006 */

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class StarSUDOKUMain {

    public static void main(String[] args) {

        if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
            try {
                UIManager
                        .setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                com.devng.starsudoku.GV.isWin = true;
            } catch (Exception exc) {
                com.devng.starsudoku.GV.isWin = false;
                exc.printStackTrace();
            }
        } 
        
        String jVer = System.getProperty("java.version");
        if(jVer.compareToIgnoreCase("1.7.0") < 0) {
            String errorMsg = "<HTML><FONT COLOR=\"RED\">Your Java version is too old!</FONT><BR><BR>Star SUDOKU requires JAVA Runtime Environment JRE 1.7.0 / JRE 7 or above.<BR> Please download the latest JRE at <FONT COLOR=\"#0000ff\">www.java.com</FONT></HTML>";
            JOptionPane.showMessageDialog(null,
                  errorMsg, "Fatal error",
                  JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        com.devng.starsudoku.gui.SudokuMainFrame f = 
            new com.devng.starsudoku.gui.SudokuMainFrame();
        f.setVisible(true);
    }
}
