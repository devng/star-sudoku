/* Main created on 10.07.2006 */

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class StarSUDOKUMain {

    public static void main(String[] args) {

        if(System.getProperty("os.name").toLowerCase().startsWith("windows xp")) {
            try {
                UIManager
                        .setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                net.sourceforge.starsudoku.GV.isWin = true;
            } catch (Exception exc) {
                net.sourceforge.starsudoku.GV.isWin = false;
                exc.printStackTrace();
            }
        } 
        
        String jVer = System.getProperty("java.version");
        if(jVer.compareToIgnoreCase("1.5.0") < 0) {
            String errorMsg = "<HTML><FONT COLOR=\"RED\">Your Java version is too old!</FONT><BR><BR>Star SUDOKU requires JAVA Runtime Enviroment JRE 1.5.0 / JRE 5 or above.<BR> Please donwload the latest JRE at <FONT COLOR=\"#0000ff\">www.java.com</FONT></HTML>";
            JOptionPane.showMessageDialog(null,
                  errorMsg, "Fatal error",
                  JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        net.sourceforge.starsudoku.gui.SudokuMainFrame f = 
            new net.sourceforge.starsudoku.gui.SudokuMainFrame();
        f.setVisible(true);
    }
}
