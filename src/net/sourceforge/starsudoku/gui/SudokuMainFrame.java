/* SubBoard created on 08.07.2006 */
package net.sourceforge.starsudoku.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;

import net.sourceforge.starsudoku.*;
import net.sourceforge.starsudoku.GV.NumDistributuon;
import net.sourceforge.starsudoku.GV.NumberEntry;
import net.sourceforge.starsudoku.io.*;

public class SudokuMainFrame extends JFrame implements SudokuObserver {

    //GUI BEGIN:
    private JPanel all;
    
    //HANDLERS
    private ButtonHandlerUp bHUp;
    private ButtonHandlerDown bHDown;
    private WindowHandler wH;
    private MenuHandler mH;
    private CheckboxHandler cH;
    
    //Action
    private ActionButDown actButDown;
    private ActionButUp actButUp;
    
    //MENU
    private JMenuItem sudNew;
    private JMenuItem sudDesign;
    private JMenuItem sudOpen;
    private JMenuItem sudSave;
    private JMenuItem sudSaveAs;
    private JMenuItem sudHint;
    private JMenuItem sudClear;
    private JMenuItem sudSolve;
    
    private JMenuItem sudExport;
    private JMenuItem sudExit;
    
    private JRadioButtonMenuItem opDiffHard;
    private JRadioButtonMenuItem opDiffNormal;
    private JRadioButtonMenuItem opDiffEasy;
    private JRadioButtonMenuItem opDiffCustom;
    private JRadioButtonMenuItem opND1;
    private JRadioButtonMenuItem opND2;
    private JRadioButtonMenuItem opND3;
    private JRadioButtonMenuItem opNE1;
    private JRadioButtonMenuItem opNE2;
    private JCheckBoxMenuItem opHLines;
    private JMenuItem opPref;
    
    private JRadioButtonMenuItem lafMetal;
    private JRadioButtonMenuItem lafSys;
    
    private JMenuItem helpAbout;
    
    //GRID
    private SudokuGuiGrid guiGrid;
    private SudokuGrid sudGrid;
    
    //BUTTONS
    private JPanel butUp;
    private JButton[] bUp;
    private JPanel butDown;
    private SudFrameButDown[] bDown;
    private SudFrameButDown delBut;

    private JTextField jTextDiff;
    
    private JTextField jTextTime;
    
    private JFileChooser fc;
    private JFileChooser fcExport;
    private FileFilter ffXML;
    private FileFilter ffHTML;
    private FileFilter ffRTF;
    private FileFilter ffCSV;
    private FileFilter ffPDF;
    
    
    private JPanel about;
    //END GUI
    
    private Time time;
    private boolean wasPaused;
    private int diff;
    private NumDistributuon nD;
    private SudokuGenerator sudGenerator;
    private boolean hasTheGridBeenChanged;
    private boolean autoCheck;
    private boolean isDesignMode;
    
    private UndoRedoStack uRS;
    
    private static SudFrameButDown lastBut;
    private static NumberEntry ne;
    
    private class Time extends Thread {
        
        private int hours;
        private int min;
        private int sec;
        private boolean pause;
        
        private int count;
        
        public Time(int h, int m, int s) {
            super();
            hours = h;
            min = m;
            sec = s;
            count = 0;
            pause = false;
        }
        public Time(JTextField jTextTime) {
            this(0,0,0);
        }
        
        public int getHours() {return hours;}
        public int getMin() {return min;}
        public int getSec() {return sec;}
        
        public void setHours(int h) {hours = h;}
        public void setMin(int m) {min = m;}
        public void setSec(int s) {sec = s;}
        
        public void reset() {
            hours = min = sec = count = 0;
        }

        private void incSec() {
            sec++;
            if(sec == 60) {
                sec = 0;
                min++;
            }
            if(min == 60) {
                min = 0;
                hours++;
            }
        }
        
        public void pause(boolean b) {
            pause = b;
        }
        
        public boolean isPaused() {
            return pause;
        }
        
        public void run() {
            while(true) {
                try {
                    Thread.sleep(50);
                    if(pause) {
//                        if(guiGrid.isPaintPause)
//                            guiGrid.paintPause();
                    } else if(count >= 1000) {
                        count = 0;
                        incSec();
                        jTextTime.setText(this.toString());
                    } else {
                        count+=50;
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            if(hours < 10) {
                sb.append('0');
            }
            sb.append(hours);
            
            sb.append(':');
            
            if(min < 10) {
                sb.append('0');
            }
            sb.append(min);
            
            sb.append(':');
            
            if(sec < 10) {
                sb.append('0');
            }
            sb.append(sec);
            
            return sb.toString();
        }
    }
    
    
    public SudokuMainFrame() {
        super(GV.NAME+GV.VERSION);
        
        bHUp = new ButtonHandlerUp();
        bHDown = new ButtonHandlerDown();
        mH = new MenuHandler();
        wH = new WindowHandler();
        cH = new CheckboxHandler();

        autoCheck = true;
        
        actButDown = new ActionButDown();
        actButUp = new ActionButUp();
        
        sudGenerator = new SudokuGenerator();
        sudGrid = sudGenerator.getGrid();
        diff = GV.DIFF_NORMAL;
        nD = NumDistributuon.evenlyFilled3x3Square3;
        
        uRS = new UndoRedoStack(100);

        ne = GV.NumberEntry.sntc;
        
        initMenu();
        initGUI();
        uRS.setUndoButton(bUp[5]);
        uRS.setRedoButton(bUp[6]);
        setNotEnabledItems();
        
        isDesignMode = true;
        wasPaused = true;
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setIconImage(loadImage("star16x16.png"));

        Container cp = getContentPane();
        cp.add(all);
        pack();
        
        setResizable(false);
        setLocation(50, 50);
        addWindowListener(wH);
        sudGrid.addObserver(this);
    }
    
    private Image loadImage(String imageName) {
        try {
            Image im = ImageIO.read(new BufferedInputStream(
                    getClass().getResourceAsStream(GV.IMG_FOLDER + 
                            imageName)));
            return im;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    private ImageIcon loadIcon(String imageName) {
        
        try {
            return new ImageIcon(loadImage(imageName));
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    private void initMenu() {
        
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);
        {
            //SUDOKU
            JMenu mSudoku = new JMenu();
            menu.add(mSudoku);
            mSudoku.setText("Sudoku");
            mSudoku.setMnemonic('S');
            {
                sudNew = new JMenuItem("New", loadIcon("star1.png"));
                mSudoku.add(sudNew);
                setCtrlAcceleratorMenu(sudNew,'N');
                sudNew.addActionListener(mH);
                sudNew.setMnemonic('N');
                
                sudDesign = new JMenuItem("New design", loadIcon("board1.png"));
                mSudoku.add(sudDesign);
                sudDesign.addActionListener(mH);
                sudDesign.setMnemonic('R');
                
                sudOpen = new JMenuItem("Open...", loadIcon("open1.png"));
                mSudoku.add(sudOpen);
                setCtrlAcceleratorMenu(sudOpen,'O');
                sudOpen.addActionListener(mH);
                sudOpen.setMnemonic('O');

                sudSave = new JMenuItem("Save", loadIcon("disk1.png"));
                mSudoku.add(sudSave);
                setCtrlAcceleratorMenu(sudSave,'S');
                sudSave.addActionListener(mH);
                sudSave.setMnemonic('S');

                sudSaveAs = new JMenuItem("Save as...", loadIcon("diskAs1.png"));
                mSudoku.add(sudSaveAs);
                sudSaveAs.addActionListener(mH);
                setKeyAcceleratorMenu(sudSaveAs, 'S', Event.SHIFT_MASK+Event.CTRL_MASK);
                sudSaveAs.setMnemonic('A');
                
                mSudoku.addSeparator();
                
                sudClear = new JMenuItem("Clear", loadIcon("bin1.png"));
                mSudoku.add(sudClear);
                sudClear.addActionListener(mH);
                sudClear.setMnemonic('C');
                
                sudHint = new JMenuItem("Hint", loadIcon("help1.png"));
                mSudoku.add(sudHint);
                sudHint.addActionListener(mH);
                sudHint.setMnemonic('H');
                setCtrlAcceleratorMenu(sudHint,'H');
                
                sudSolve = new JMenuItem("Solve", loadIcon("tick1.png"));
                mSudoku.add(sudSolve);
                sudSolve.addActionListener(mH);
                sudSolve.setMnemonic('L');
                
                mSudoku.addSeparator();
                
                sudExport = new JMenuItem("Export...",loadIcon("export1.png"));
                mSudoku.add(sudExport);
                sudExport.addActionListener(mH);
                setCtrlAcceleratorMenu(sudExport,'T');
                sudExport.setMnemonic('T');

                mSudoku.addSeparator();
                
                sudExit = new JMenuItem("Exit", loadIcon("cross1.png"));
                mSudoku.add(sudExit);
                setKeyAcceleratorMenu(sudExit, Event.ESCAPE, 0);
                sudExit.addActionListener(mH);
                sudExit.setMnemonic('X');
                
            }
            
            //Options
            JMenu mOptions = new JMenu("Options");
            mOptions.setMnemonic('O');
            menu.add(mOptions);
            {
                JLabel jl1 = new JLabel("<HTML>  &#032&#032<U>Difficulty :</U><HTML>");
                jl1.setHorizontalAlignment(JLabel.LEFT);
                jl1.setHorizontalTextPosition(JLabel.LEFT);
                mOptions.add(jl1);
                
                ButtonGroup bGr1 = new ButtonGroup();
                
                opDiffEasy = new JRadioButtonMenuItem("Easy");
                opDiffEasy.addActionListener(mH);
                opDiffEasy.setMnemonic('E');
                mOptions.add(opDiffEasy);
                bGr1.add(opDiffEasy);

                opDiffNormal = new JRadioButtonMenuItem("Normal", true);
                opDiffNormal.addActionListener(mH);
                opDiffNormal.setMnemonic('N');
                mOptions.add(opDiffNormal);
                bGr1.add(opDiffNormal);

                opDiffHard = new JRadioButtonMenuItem("Hard");
                opDiffHard.addActionListener(mH);
                opDiffHard.setMnemonic('H');
                mOptions.add(opDiffHard);
                bGr1.add(opDiffHard);
                
                opDiffCustom = new JRadioButtonMenuItem("Custom...");
                mOptions.add(opDiffCustom);
                opDiffCustom.addActionListener(mH);
                opDiffCustom.setMnemonic('C');
                bGr1.add(opDiffCustom);
                
                mOptions.addSeparator();
                
                JLabel jl2 = new JLabel("<HTML>  &#032&#032<U>Number Distribution :</U><HTML>");
                jl2.setHorizontalAlignment(JLabel.LEFT);
                jl2.setHorizontalTextPosition(JLabel.LEFT);
                mOptions.add(jl2);
                
                ButtonGroup bGr2 = new ButtonGroup();
                
                opND1 = new JRadioButtonMenuItem("Evenly filled 3x3 Squares", true);
                opND1.addActionListener(mH);
                opND1.setMnemonic('F');
                mOptions.add(opND1);
                bGr2.add(opND1);
                
                opND2 = new JRadioButtonMenuItem("Evenly distributed Numbers");
                opND2.addActionListener(mH);
                opND2.setMnemonic('D');
                mOptions.add(opND2);
                bGr2.add(opND2);
                
                opND3 = new JRadioButtonMenuItem("Random");
                opND3.addActionListener(mH);
                opND3.setMnemonic('R');
                mOptions.add(opND3);
                bGr2.add(opND3);
                
                mOptions.addSeparator();
                
                ButtonGroup bGr3 = new ButtonGroup();
                
                {
                    JLabel jl3 = new JLabel("<HTML>  &#032&#032<U>Number entry :</U><HTML>");
                    jl3.setHorizontalAlignment(JLabel.LEFT);
                    jl3.setHorizontalTextPosition(JLabel.LEFT);
                    mOptions.add(jl3);
                    
                    opNE1 =  new JRadioButtonMenuItem("Select number then cell", true);
                    opNE1.addActionListener(mH);
                    opNE1.setMnemonic('N');
                    mOptions.add(opNE1);
                    bGr3.add(opNE1);
                    
                    opNE2 =  new JRadioButtonMenuItem("Select cell then number");
                    opNE2.addActionListener(mH);
                    opNE2.setMnemonic('E');
                    mOptions.add(opNE2);
                    bGr3.add(opNE2);
                    
                }
                
                mOptions.addSeparator();
                
                opHLines = new JCheckBoxMenuItem("Draw helping lines", true);
                opHLines.addItemListener(cH);
                opHLines.setMnemonic('D');
                mOptions.add(opHLines);
                
                mOptions.addSeparator();
                
                opPref = new JMenuItem("Preferences...", loadIcon("wrench1.png"));
                mOptions.add(opPref);
                opPref.addActionListener(mH);
                setCtrlAcceleratorMenu(opPref,'P');
                opPref.setMnemonic('P');
            } 
            
            //LOOK AND FELL
            JMenu mLAF = new JMenu("Look And Feel"); 
            mLAF.setMnemonic('L');
            menu.add(mLAF);
            {
                ButtonGroup bGr4 = new ButtonGroup();
                lafMetal =  new JRadioButtonMenuItem("Java Look And Feel", !GV.isWin);
                lafMetal.addActionListener(mH);
                lafMetal.setMnemonic('M');
                mLAF.add(lafMetal);
                bGr4.add(lafMetal);

                lafSys =  new JRadioButtonMenuItem("System Look And Feel", GV.isWin);
                lafSys.addActionListener(mH);
                lafSys.setMnemonic('S');
                mLAF.add(lafSys);
                bGr4.add(lafSys);
            }
            
            //Help
            JMenu mHelp = new JMenu("Help");
            mHelp.setMnemonic('H');
            menu.add(mHelp);
            {
                helpAbout = new JMenuItem("About", loadIcon("cup1.png"));
                mHelp.add(helpAbout);
                helpAbout.addActionListener(mH);
                helpAbout.setMnemonic('A');
            }
        }
    }
    
    private void initGUI() {

        all = new JPanel();
        all.setLayout(new BorderLayout());
        all.setBackground(GV.BORDER_COLOR);
        all.setBorder(BorderFactory.createTitledBorder(""));
        
        //BUTTONS UP
        butUp = new JPanel();
        BoxLayout bUpLayout = new BoxLayout(butUp,BoxLayout.X_AXIS);
        butUp.setLayout(bUpLayout);
        butUp.setBorder(new EtchedBorder());
        bUp = new JButton[12];
        {
            Dimension butUpDim = new Dimension(24, 24);
             
            bUp[0] = createButUp("star1.png", "star2.png", "Generate new Sudoku", butUpDim);
            butUp.add(bUp[0]);
            bUp[1] = createButUp("board1.png", "board2.png", "Design new Sudoku", butUpDim);
            butUp.add(bUp[1]);
            bUp[11] = createButUp("play1.png", "play2.png", "Play the designed Sudoku", butUpDim);
            butUp.add(bUp[11]);
            bUp[11].setDisabledIcon(loadIcon("play3.png"));
            bUp[2] = createButUp("open1.png", "open2.png", "Open Sudoku", butUpDim);
            butUp.add(bUp[2]);
            bUp[3] = createButUp("disk1.png", "disk2.png", "Save Sudoku As", butUpDim);
            butUp.add(bUp[3]);
            
            //SEPARATOR
            createButUpSeparator();
            bUp[4] = createButUp("pause1.png", "pause2.png", "Pause", butUpDim);
            butUp.add(bUp[4]);
            //SEPARATOR
            createButUpSeparator();
            
            bUp[5] = createButUp("undo1.png", "undo2.png", "Undo", butUpDim);
            bUp[5].setDisabledIcon(loadIcon("undo3.png"));
            butUp.add(bUp[5]);
            addKeyAcceleratorButton(bUp[5],actButUp,"undo",'Z',Event.CTRL_MASK);
            
            bUp[6] = createButUp("redo1.png", "redo2.png", "Redo", butUpDim);
            bUp[6].setDisabledIcon(loadIcon("redo3.png"));
            butUp.add(bUp[6]);
            addKeyAcceleratorButton(bUp[6],actButUp,"redo",'Y',Event.CTRL_MASK);

            //SEPARATOR
            createButUpSeparator();
            
            bUp[7] = createButUp("bin1.png", "bin2.png", "Clear Sudoku", butUpDim);
            butUp.add(bUp[7]);
            
            bUp[8] = createButUp("help1.png", "help2.png", "Hint", butUpDim);
            butUp.add(bUp[8]);
            
            bUp[9] = createButUp("tick1.png", "tick2.png", "Solve", butUpDim);
            butUp.add(bUp[9]);

            butUp.add(Box.createRigidArea(new Dimension(160, 1)));
            bUp[10] = createButUp("cross1.png", "cross2.png", "<HTML>Close</HTML>", butUpDim);
            butUp.add(bUp[10]);
            addKeyAcceleratorButton(bUp[9],actButUp,"close",Event.ESCAPE,0);
        }
        
        all.add(butUp, BorderLayout.NORTH);
        
        //GRID
        guiGrid = new SudokuGuiGrid(sudGrid, uRS);
        guiGrid.setBorder(new LineBorder(new Color(238,238,238), 4, false));
        all.add(guiGrid, BorderLayout.CENTER);
        
        //System Field
        JPanel sysField = new JPanel();
        BoxLayout sfLayout = new BoxLayout(sysField,BoxLayout.X_AXIS);
        sysField.setLayout(sfLayout);
        sysField.setBorder(new EtchedBorder());
        {
            //Difficulty
            sysField.add(Box.createRigidArea(GV.DIM_RA));
            JLabel diffLabel = new JLabel(" Difficulty :  ");
            sysField.add(diffLabel);
            
            jTextDiff = new JTextField();
            jTextDiff.setEditable(false);
            jTextDiff.setText("");
            jTextDiff.setMaximumSize(new Dimension(80, 18));
            sysField.add(jTextDiff);
            
            //TIME
            sysField.add(Box.createRigidArea(GV.DIM_RA));
            sysField.add(Box.createRigidArea(GV.DIM_RA));
            sysField.add(Box.createRigidArea(GV.DIM_RA));
            
            JSeparator jSep = new JSeparator();
            jSep.setOrientation(SwingConstants.VERTICAL);
            jSep.setMaximumSize(new Dimension(2,18));
            sysField.add(jSep);
            
            sysField.add(Box.createRigidArea(GV.DIM_RA));

            JLabel timeLabel = new JLabel(" Time :  ");
            sysField.add(timeLabel);
            
            jTextTime = new JTextField();
            jTextTime.setEditable(false);
            jTextTime.setText("00:00:00");
            jTextTime.setMaximumSize(new Dimension(60, 18));
            sysField.add(jTextTime);
            
            time = new Time(0,0,0);
            time.pause(true);
            time.start();
        }
        
        //BUTONS DOWN
        butDown = new JPanel();
        BoxLayout butLayout = new BoxLayout(butDown,BoxLayout.X_AXIS);
        butDown.setLayout(butLayout);
        butDown.setBorder(new LineBorder(GV.BORDER_COLOR, 2, false));
        butDown.setBackground(GV.BORDER_COLOR);
        
        bDown = new SudFrameButDown[9];
        {
            butDown.add(Box.createRigidArea(GV.DIM_RA));
            for (int i = 0; i < bDown.length; i++) {
                bDown[i] = new SudFrameButDown((i+1));
                butDown.add(bDown[i]);
                bDown[i].setText("<HTML><B>"+(i+1)+"</B></HTML>");
                bDown[i].addActionListener(bHDown);
                butDown.add(Box.createRigidArea(GV.DIM_RA));
                bDown[i].setToolTipText("<HTML>Press '"+(i+1)+"'</HTML>");
                addKeyAcceleratorButton(bDown[i], actButDown,"press"+(i+1),KeyEvent.VK_1+i,0);
                addKeyAcceleratorButton(bDown[i], actButDown,"press"+(i+1),KeyEvent.VK_NUMPAD1+i,0);
            }
            
            delBut = new SudFrameButDown(0);
            delBut.addActionListener(bHDown);
            butDown.add(delBut);
            delBut.setText("<HTML><B>Delete</B></HTML>");
            delBut.setToolTipText("<HTML>Press 'D' or 'Delete'<BR>Delete a number or <B>ALL</B> notes form a cell</HTML>");
            addKeyAcceleratorButton(delBut, actButDown,"delBut",KeyEvent.VK_D,0);
            addKeyAcceleratorButton(delBut, actButDown,"delBut",KeyEvent.VK_DELETE,0);
            
            lastBut = bDown[0];
            lastBut.setForeground(Color.BLUE);
            lastBut.requestFocus();
        }
        
        //FileChooser
        fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return f.getName().toLowerCase().endsWith(".ssud");
            }

            public String getDescription() {
                return GV.NAME + "Save File (*.ssud)";
            }
        });
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);

        fcExport = new JFileChooser();
        fcExport.setApproveButtonText("Export");

        ffXML = new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return (f.getName().toLowerCase().endsWith(".xml"));
            }

            public String getDescription() {
                return "Export File (*.xml)";
            }
        };
        ffHTML = new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return (f.getName().toLowerCase().endsWith(".html"));
            }

            public String getDescription() {
                return "Export File (*.html)";
            }
        };
        
        ffRTF = new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return (f.getName().toLowerCase().endsWith(".rtf"));
            }

            public String getDescription() {
                return "Export File (*.rtf)";
            }
        };
        
        ffCSV = new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return (f.getName().toLowerCase().endsWith(".csv"));
            }

            public String getDescription() {
                return "Export File (*.csv)";
            }
        };
        
        ffPDF = new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                return (f.getName().toLowerCase().endsWith(".pdf"));
            }

            public String getDescription() {
                return "Export File (*.pdf)";
            }
        };
        
        fcExport.addChoosableFileFilter(ffCSV);
        fcExport.addChoosableFileFilter(ffXML);
        fcExport.addChoosableFileFilter(ffHTML);
        fcExport.addChoosableFileFilter(ffRTF);
        fcExport.addChoosableFileFilter(ffPDF);
        fcExport.setAcceptAllFileFilterUsed(false);
        fcExport.setMultiSelectionEnabled(false);
       
        
        JPanel butDownAndSF = new JPanel();
        butDownAndSF.setLayout(new BorderLayout());
        butDownAndSF.add(butDown, BorderLayout.SOUTH);
        butDownAndSF.add(sysField, BorderLayout.NORTH);
        
        all.add(butDownAndSF, BorderLayout.SOUTH);
    }
    
    private void setNotEnabledItems() {
        sudSave.setEnabled(false);
        sudHint.setEnabled(false);
        
        opPref.setEnabled(false);
        
        bUp[5].setEnabled(false);
        bUp[6].setEnabled(false);
        bUp[8].setEnabled(false);
    }
    
    private JButton createButUp( String img, String img2, 
            String toolTip, Dimension d) {
        
        JButton jB = new JButton(loadIcon(img));
        jB.setRolloverIcon(loadIcon(img2));
        jB.setToolTipText(toolTip);
        jB.setSize(d);
        jB.setPreferredSize(d);
        jB.setMaximumSize(d);
        jB.setFocusPainted(false);
        jB.setBorderPainted(false);
        jB.setContentAreaFilled(false);
        jB.addActionListener(bHUp);
        
        return jB;
    }
    
    private JSeparator createButUpSeparator() {
        butUp.add(Box.createRigidArea(GV.DIM_RA));
        JSeparator jSep = new JSeparator();
        jSep.setOrientation(SwingConstants.VERTICAL);
        jSep.setMaximumSize(new Dimension(2,18));
        butUp.add(jSep);
        butUp.add(Box.createRigidArea(GV.DIM_RA));
        
        return jSep;
    }
    
    private void addKeyAcceleratorButton(JButton b,AbstractAction act, String actionName, int keyCode, int mask) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyCode, mask);
        b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, actionName);
        b.getActionMap().put(actionName, act); 
    }
    
    private void setKeyAcceleratorMenu(JMenuItem mi, int keyCode, int mask) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyCode, mask);
        mi.setAccelerator(ks);
    }
    
    private void setCtrlAcceleratorMenu(JMenuItem mi, char acc) {
        setKeyAcceleratorMenu(mi, (int) acc, Event.CTRL_MASK);
    }

    private void doExit() {
        boolean b = askSureQuestion("exit?");
        if(b) {
            setVisible(false);
            dispose();
            System.exit(0);
        } 
    }

    private void doCustomDiff() {
        String s = JOptionPane.showInputDialog(this,
                GV.INFO_NUMBER, "Custom Difficulty", JOptionPane.PLAIN_MESSAGE);
        if(s != null) {
            try {
                int i = Integer.parseInt(s.trim());
                if(i >= 1 && i <= 81) {
                    diff = i;
                    return;
                }
            } catch (Exception e) {}
            JOptionPane.showMessageDialog(this, GV.ERROR_NUMBER, "Error", 
                    JOptionPane.ERROR_MESSAGE);
            doCustomDiff();
        }
    }

    private void doAbout() {
        if(about == null) setAboutPanel();
        JOptionPane.showMessageDialog(this,about,"ABOUT", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void setAboutPanel() {
        about = new JPanel();
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        
        about.add(new JLabel("<HTML><B>"+GV.NAME+GV.VERSION+"</B> Copyright (c) 2006 Nikolay G. Georgiev</HTML>"));
        about.add(Box.createVerticalStrut(10));
        
        about.add(new JLabel("E-Mail:"));
        about.add(new HTMLLink("ngg@users.sourceforge.net", true));
        
        about.add(Box.createVerticalStrut(10));
        
        about.add(new JLabel("WEB PAGE:"));
        about.add(new HTMLLink("http://starsudoku.sf.net", false));
        
        about.add(Box.createVerticalStrut(20));
        
        about.add(new JLabel("<HTML>This program is released under the Mozilla Public License 1.1 .<BR> A copy of this is included with your copy of Star SUDOKU<BR>and can also be found at:</HTML>"));
        about.add(new HTMLLink("http://www.opensource.org/licenses/mozilla1.1.php", false));
        
        
        about.add(Box.createVerticalStrut(20));
        
        about.add(new JLabel("<HTML>iText Library - Copyright (C) 1999-2006 by Bruno Lowagie and <BR>Paulo Soares. All Rights Reserved. </HTML>"));
        about.add(new HTMLLink("http://www.lowagie.com/iText/", false));
    }
    
    private void generateNewSud() {
        if (guiGrid.isPaintPause) {
            resumePause();
        }
        boolean b = askSureQuestion("generate new Sudoku?");
        if(b) {
            setDesignMode(false);
            autoCheck = false;
            setDiffLabelVal(diff);
            sudGrid.resetGrid();
            sudGenerator.generatePuzzle(diff, nD);
            uRS.reset();
            guiGrid.resetBackGroudColorLastCellAndLoseFocus();
            setGridChange(false);
            guiGrid.repaint();
            autoCheck = true;
            time.reset();
            time.pause(false);
        }
    }
    
    private void setDiffLabelVal(int diff) {
        switch(diff) {
        case GV.DIFF_DESIGN : jTextDiff.setText("User design"); break;
        case GV.DIFF_EASY : jTextDiff.setText("Easy"); break;
        case GV.DIFF_NORMAL : jTextDiff.setText("Normal"); break;
        case GV.DIFF_HARD : jTextDiff.setText("Hard"); break;
        default : jTextDiff.setText("Custom");
        }
    }
    
    private void doUndo() {
        if (guiGrid.isPaintPause) {
            return;
        }
        int cell = uRS.undo();
        if(cell != -1) {
           sudGrid.setRealGridVal(cell); 
        }
    }
    
    private void doRedo() {
        if (guiGrid.isPaintPause) {
            return;
        }
        int cell = uRS.redo();
        if(cell != -1) {
            sudGrid.setRealGridVal(cell);
        }
    }
    
    private void doNewDesign() {
        if (guiGrid.isPaintPause) {
            resumePause();
        }
        boolean b = askSureQuestion("design new Sudoku?");
        if(b) {
            setDesignMode(true);
            sudGrid.resetGrid();
            uRS.reset();
            guiGrid.resetBackGroudColorLastCellAndLoseFocus();
            setGridChange(false);
            guiGrid.repaint();
            time.reset();
            time.pause(true);
        }
    }
    
    private void doPlayDesign() {
        if (isDesignMode) {
            int v = 0;
            autoCheck = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    v = sudGrid.getPuzzleVal(j,i);
                    if(v != 0) {
                        //sudGrid.setPuzzleVal(j,i,0);
                        sudGrid.setGridVal(j,i,v);
                        sudGrid.setDefault(j,i,true);
                        sudGrid.setEditable(j,i,false);//TODO ??
                        sudGrid.deleteAllNotes(j,i);
                    }
                 }
            }
            if(sudGenerator.solveGrid()) {
                setDesignMode(false);
                time.pause(false);
                setDiffLabelVal(-1);
                uRS.reset();
                repaint();
            } else {
                JOptionPane.showMessageDialog
                (this, GV.ERROR_PLAY, "Invalid Sudoku", JOptionPane.WARNING_MESSAGE);
                
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        v = sudGrid.getPuzzleVal(j,i);
                        if(v != 0) {
                            sudGrid.setEditable(j,i,true);
                            sudGrid.setGridVal(j,i,0);
                            sudGrid.setDefault(j,i,false);
                        }
                     }
                }
                setDesignMode(true);
                repaint();
            }
            autoCheck = false;
        }
    }
    
    private void setDesignMode(boolean b) {
        if(b) {
            isDesignMode = true;
            bUp[11].setEnabled(true);
        } else {
            isDesignMode = false;
            bUp[11].setEnabled(false);
        }
    }
    
    private void doSolve() {
        if (guiGrid.isPaintPause) {
            return;
        }
        //TODO When Sudoku is not valid ???
        boolean b = askSureQuestion("solve the grid automaticly?");
        
        if(b) {
            time.pause(true);
            uRS.reset();
            if(!sudGrid.isPuzzleSolved()) {
                autoCheck = false;
                try {
                    sudGenerator.solvePuzzle();
                    guiGrid.resetBackGroudColorLastCellAndLoseFocus();
                    autoCheck = true;
                    setGridChange(false);
                    return;
                } catch (NullPointerException e) {
                    autoCheck = true;
                    e.printStackTrace(System.out);
                }
            }
            JOptionPane.showMessageDialog(this, GV.INFO_SOLVED, "Solved", 
                    JOptionPane.INFORMATION_MESSAGE);
        } 
    }
    
    private void doClear() {
        if (guiGrid.isPaintPause) {
            return;
        }
        boolean b = askSureQuestion("clear the grid?");
        if(b) {
            time.reset();
            time.pause(false);
            autoCheck = false;
            sudGrid.clearNonDefaultCells();
            uRS.reset();
            guiGrid.resetBackGroudColorLastCellAndLoseFocus();
            setGridChange(false);
            autoCheck = true;
        }
    }
    
    //TODO Check only after 81 Cells has been fealed up
    private void doCheck() {
        if(!autoCheck) return;
        if(sudGrid.isPuzzleSolved()) {
            time.pause(true);
            JOptionPane.showMessageDialog(this, GV.INFO_SUCCESS, "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            setGridToFinished();
            guiGrid.repaint();
        }   
    }
    
    private void doSave() {
        if (guiGrid.isPaintPause) {
            resumePause();
        }
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                SerGrid sg = sudGrid.getSerGrid();
                if(jTextDiff.getText().compareTo("User design") == 0) {
                    sg.difficulty = -1;
                }
                sg.curH = time.getHours();
                sg.curM = time.getMin();
                sg.curS = time.getSec();
                
                File file = fc.getSelectedFile();
                String aPath = file.getAbsolutePath();
                if(!aPath.toLowerCase().endsWith(".ssud")) {
                    aPath = aPath + ".ssud";
                    file = new File(aPath);
                }
                
                LoadSaver.save(sg, file);
                fc.setSelectedFile(file);
                setGridChange(false);
            } catch (Exception e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(this, GV.ERROR_SAVE, "Write error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void doPause() {
        if(guiGrid.isPaintPause) {
            resumePause();
        } else {
            wasPaused = time.isPaused();
            guiGrid.isPaintPause = true;
            guiGrid.resetBackGroudColorLastCellAndLoseFocus();
            time.pause(true);
            guiGrid.paintPause();
        }
    }
    
    private void resumePause() {
        boolean b = hasTheGridBeenChanged;
        guiGrid.isPaintPause = false;
        guiGrid.resetBackGroudColorLastCellAndLoseFocus();
        time.pause(wasPaused);
        hasTheGridBeenChanged = b;
        repaint();
    }
    
    private boolean askSureQuestion(String sureFor) {
        if(hasTheGridBeenChanged) {
            StringBuffer sb = new StringBuffer();
            sb.append("Do you realy what to ");
            sb.append(sureFor);
            sb.append("\nAll of your changes will be lost!");
            int result = JOptionPane.showConfirmDialog(this,
                    sb.toString(), "" , JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, null);
            sb = null;
            if(result == JOptionPane.YES_OPTION) {
                setGridChange(false);
                return true;
            } else {
                return false;
            } 
        } else {
            return true;
        }
    }
    
    private void doLoad() {
        if (guiGrid.isPaintPause) {
            resumePause();
        }
        boolean b = askSureQuestion("load a game?");
        if(b) {
            setDesignMode(false);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    autoCheck = false;
                    SerGrid sg = LoadSaver.load(fc.getSelectedFile().getAbsolutePath());
                    
                    sudGrid.setSerGrid(sg);
                    time.setHours(sg.curH);
                    time.setMin(sg.curM);
                    time.setSec(sg.curS);
                    
                    setDiffLabelVal(sg.difficulty);
                    uRS.reset();
                    guiGrid.resetBackGroudColorLastCellAndLoseFocus();
                    setGridChange(false);
                    autoCheck = true;
                } catch (Exception e) {
                    autoCheck = true;
                    e.printStackTrace(System.out);
                    JOptionPane.showMessageDialog(this, GV.ERROR_LOAD, "Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            time.pause(false);
        }     
    }
    
    private void doExport() {
        String s; 
        if (fcExport.showDialog(this, "Export Sudoku") == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fcExport.getSelectedFile();
                String aPath = file.getAbsolutePath();
                if(fcExport.getFileFilter() == ffPDF) {
                    if(!aPath.toLowerCase().endsWith(".pdf")) {
                        aPath = aPath + ".pdf";
                        file = new File(aPath);
                    }
                    ExportPDF.export(sudGrid.getSerGrid(), file); 
                } else if(fcExport.getFileFilter() == ffXML) {
                    s = Export.getXMLString(sudGrid.getSerGrid());  
                    if(!aPath.toLowerCase().endsWith(".xml")) {
                        aPath = aPath + ".xml";
                        file = new File(aPath);
                    }
                    Export.writeString(s,file);
                } else if (fcExport.getFileFilter() == ffHTML) {
                    s = Export.getHTMLString(sudGrid.getSerGrid());  
                    if(!aPath.toLowerCase().endsWith(".html")) {
                        aPath = aPath + ".html";
                        file = new File(aPath);
                    }
                    Export.writeString(s,file);
                } else if (fcExport.getFileFilter() == ffRTF) {
                    s = Export.getRTFString(sudGrid.getSerGrid());  
                    if(!aPath.toLowerCase().endsWith(".rtf")) {
                        aPath = aPath + ".rtf";
                        file = new File(aPath);
                    }
                    Export.writeString(s,file);
                } else {
                    s = Export.getCSVString(sudGrid.getSerGrid());  
                    if(!aPath.toLowerCase().endsWith(".csv")) {
                        aPath = aPath + ".csv";
                        file = new File(aPath);
                    }
                    Export.writeString(s,file);
                }
                fc.setSelectedFile(file);
            } catch (Exception e) {
                e.printStackTrace(System.out);
                JOptionPane.showMessageDialog(this, GV.ERROR_EXPORT, "Write error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void setGridToFinished() {
        autoCheck = false;
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                sudGrid.deleteAllNotes(j,i);
                sudGrid.setEditable(j,i,false);
            }
        }
        guiGrid.resetBackGroudColorLastCellAndLoseFocus();
        autoCheck = true;
    }
    
    private void setLookAndFeel(GV.LAF laf) {
        try {
            switch (laf) {
            case METAL:
                UIManager.setLookAndFeel(GV.METAL);
                break;
            case SYSTEM:
                UIManager.setLookAndFeel(GV.SYSTEM);
                break;
            }
            
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    
    private void setGridChange(boolean isChanged) {
        hasTheGridBeenChanged = isChanged;
    }

    protected static SudFrameButDown getLastButton() {
        return lastBut;
    }
    
    protected static NumberEntry getNumberEntry() {
        return ne;
    }
    
    public void updateCellChange(int cell) {
        setGridChange(true);
        repaint();
        doCheck();
    }
    
    private class ButtonHandlerUp implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JButton tmp = (JButton) e.getSource();
                
                if (tmp == bUp[0]) {
                    generateNewSud();
                } else if(tmp == bUp[1]) {
                    doNewDesign(); 
                } else if(tmp == bUp[2]) {
                    doLoad(); 
                } else if(tmp == bUp[3]) {
                    doSave(); 
                } else if(tmp == bUp[4]) {
                    doPause(); 
                } else if(tmp == bUp[5]) {
                    doUndo(); 
                } else if(tmp == bUp[6]) {
                    doRedo(); 
                } else if(tmp == bUp[7]) {
                    doClear(); 
                } else if(tmp == bUp[9]) {
                    doSolve(); 
                } else if(tmp == bUp[10]) {
                    doExit(); 
                } else if(tmp == bUp[11]) {
                    doPlayDesign(); 
                }
             } catch (ClassCastException ex) {
                 ex.printStackTrace(System.out);
             } 
        }
    }

    private class ButtonHandlerDown implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
               SudFrameButDown tmp = (SudFrameButDown) e.getSource();
               tmp.requestFocus();
               tmp.getText();
               if (lastBut != null) {
                   lastBut.setForeground(Color.BLACK);
               } 
               lastBut = tmp;
               lastBut.setForeground(Color.BLUE);
               guiGrid.buttonDownPushed();
               
            } catch (ClassCastException ex) {
                ex.printStackTrace(System.out);
            }
        }  
    }

    private class WindowHandler implements WindowListener {

        public void windowOpened(WindowEvent arg0) {}

        public void windowClosing(WindowEvent arg0) {
            doExit();
        }

        public void windowClosed(WindowEvent arg0) {}

        public void windowIconified(WindowEvent arg0) {}

        public void windowDeiconified(WindowEvent arg0) {}

        public void windowActivated(WindowEvent arg0) {}

        public void windowDeactivated(WindowEvent arg0) {}
    }
    
    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JMenuItem tmp = (JMenuItem) e.getSource();
                
                if (tmp == sudNew) {
                    generateNewSud();
                }  else if (tmp == sudOpen) {
                    doLoad();
                }  else if (tmp == sudSaveAs) {
                    doSave();
                } else if (tmp == opDiffEasy) {
                    diff = GV.DIFF_EASY;
                } else if (tmp == opDiffNormal) {
                    diff = GV.DIFF_NORMAL;
                } else if (tmp == opDiffHard) {
                    diff = GV.DIFF_HARD;
                } else if (tmp == opDiffCustom){
                    doCustomDiff();
                } else if (tmp == opND1){
                    nD = NumDistributuon.evenlyFilled3x3Square3;
                } else if (tmp == opND2){
                    nD = NumDistributuon.evenlyDistributedNumbers;
                } else if (tmp == opND3){
                    nD = NumDistributuon.random;
                } else if (tmp == opNE1){
                    ne = NumberEntry.sntc;
                } else if (tmp == opNE2){
                    ne = NumberEntry.sctn;
                } else if (tmp == lafMetal){
                    setLookAndFeel(GV.LAF.METAL);
                } else if (tmp == lafSys){
                    setLookAndFeel(GV.LAF.SYSTEM);
                } else if (tmp == lafSys){
                    setLookAndFeel(GV.LAF.SYSTEM);
                    
                } else if (tmp == helpAbout) {
                    doAbout();
                } else if (tmp == sudExit) {
                    doExit();
                } else if (tmp == sudSolve) {
                    doSolve();
                } else if (tmp == sudDesign) {
                    doNewDesign();
                } else if (tmp == sudClear) {
                    doClear();
                } else if (tmp == sudExport) {
                    doExport();
                } 
            } catch (ClassCastException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
    
    private class ActionButDown extends  AbstractAction {
        
        public void actionPerformed(ActionEvent evt) {
            bHDown.actionPerformed(evt);
        }
    }
    
    private class ActionButUp extends  AbstractAction {
        
        public void actionPerformed(ActionEvent evt) {
            bHUp.actionPerformed(evt);
        }
    }

    private class CheckboxHandler implements ItemListener {// JV

        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
                guiGrid.enableHelpingLines();
            } else {
                guiGrid.disableHelpingLines();
            }
        }
    }
}

class SudFrameButDown extends JButton {
    private int val;
    
    public SudFrameButDown(int val) {
        super();
        this.val = val;
    }
    
    public int getVal() {
        return val;
    }
}
