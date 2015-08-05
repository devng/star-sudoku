/* ExportHTML created on 11.09.2006 */
package com.devng.starsudoku.io;

import com.devng.starsudoku.GV;
import com.devng.starsudoku.SudokuGrid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Export {
    public static String getHTMLString(SerGrid sg) {
        StringBuilder sb = new StringBuilder(3000);
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("\t<title>Star SUDOKU</title>\n");
        sb.append("\t<style type=\"text/css\">\n");
        sb.append("\t\ttd {\n");
        sb.append("\t\t\tfont-weight: bold;\n");
        sb.append("\t\t\tfont-size: 1.8em;\n");
        sb.append("\t\t}\n");
        sb.append("\t</style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<table border=2 cellpadding=4 cellspacing=0>\n");

        for (int i = 0; i < 9; i++) {
            sb.append("\t<tr>\n");
            for (int j = 0; j < 9; j++) {
                int val = SudokuGrid.getGridVal(sg.grid[j][i]);
                boolean isDefault = SudokuGrid.isDefault(sg.grid[j][i]);
                sb.append("\t\t<td>&nbsp;");
                if (!isDefault) {
                    sb.append("&nbsp;&nbsp;</td>\n");
                } else {
                    sb.append(val);
                    sb.append("&nbsp;</td>\n");
                }
            }
            sb.append("\t</tr>\n");
        }

        sb.append("</table>\n");
        sb.append("<p>Generated with Star SUDOKU (<a href=\"" + GV.WEB_PAGE + "\" target=\"_blank\">" + GV.WEB_PAGE + "</a>)</p>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");

        return sb.toString();
    }

    public static String getXMLString(SerGrid sg) {
        StringBuilder sb = new StringBuilder(3000);
        sb.append("<starsudoku>\n");
        sb.append("\t<table rows=\"9\" cols=\"9\">\n");

        for (int i = 0; i < 9; i++) {
            sb.append("\t\t<tr>\n");
            for (int j = 0; j < 9; j++) {
                int val = SudokuGrid.getGridVal(sg.grid[j][i]);
                boolean isDefault = SudokuGrid.isDefault(sg.grid[j][i]);
                sb.append("\t\t\t<td");
                if (!isDefault) {
                    sb.append(" visible=\"false\">0</td>\n");
                } else {
                    sb.append(" visible=\"true\">");
                    sb.append(val);
                    sb.append("</td>\n");
                }
            }
            sb.append("\t\t</tr>\n");
        }
        sb.append("\t</table>\n");
        sb.append("</starsudoku>\n");

        return sb.toString();
    }

    public static String getCSVString(SerGrid sg) {
        StringBuilder sb = new StringBuilder(200);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int val = SudokuGrid.getGridVal(sg.grid[j][i]);
                boolean isDefault = SudokuGrid.isDefault(sg.grid[j][i]);
                if (isDefault) {
                    sb.append(val);
                }
                if (j != 8) {
                    sb.append(',');
                }
            }
            sb.append(' ');
            if (i != 8) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static String getRTFString(SerGrid sg) {
        StringBuilder sb = new StringBuilder(30000);
        int val = 0;
        boolean isDefault = false;
        sb.append("{\\rtf1\\ansi\\deff1\\adeflang1025");
        sb.append("{\\fonttbl{\\f0\\froman\\fprq2\\fcharset0 Thorndale{\\*\\falt Times New Roman};}{\\f1\\froman\\fprq2\\fcharset0 Thorndale{\\*\\falt Times New Roman};}{\\f2\\fswiss\\fprq2\\fcharset0 Albany{\\*\\falt Arial};}{\\f3\\froman\\fprq2\\fcharset0 Thorndale{\\*\\falt Times New Roman};}{\\f4\\fswiss\\fprq2\\fcharset0 Albany{\\*\\falt Arial};}{\\f5\\fnil\\fprq2\\fcharset0 Andale Sans UI{\\*\\falt Arial Unicode MS};}{\\f6\\fswiss\\fprq2\\fcharset0 Andale Sans UI{\\*\\falt Arial Unicode MS};}{\\f7\\fnil\\fprq2\\fcharset0 Tahoma;}{\\f8\\fnil\\fprq0\\fcharset0 Tahoma;}{\\f9\\fswiss\\fprq2\\fcharset0 Tahoma;}{\\f10\\fswiss\\fprq0\\fcharset0 Tahoma;}}");
        sb.append("{\\colortbl;\\red0\\green0\\blue0;\\red128\\green128\\blue128;}");
        sb.append("{\\stylesheet{\\s1\\rtlch\\af9\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\snext1 Normal;}");
        sb.append("{\\s2\\sb240\\sa120\\keepn\\rtlch\\af7\\afs28\\lang255\\ltrch\\dbch\\af5\\langfe255\\hich\\f2\\fs28\\lang1031\\loch\\f2\\fs28\\lang1031\\sbasedon1\\snext3 Heading;}");
        sb.append("{\\s3\\sa120\\rtlch\\af9\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon1\\snext3 Body Text;}");
        sb.append("{\\s4\\sa120\\rtlch\\af10\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon3\\snext4 List;}");
        sb.append("{\\s5\\sb120\\sa120\\rtlch\\af8\\afs24\\lang255\\ai\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\i\\loch\\fs24\\lang1031\\i\\sbasedon1\\snext5 caption;}");
        sb.append("{\\s6\\rtlch\\af8\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon1\\snext6 Index;}");
        sb.append("{\\s7\\sb240\\sa120\\keepn\\rtlch\\af1\\afs28\\lang255\\ltrch\\dbch\\af1\\langfe255\\hich\\f4\\fs28\\lang1031\\loch\\f4\\fs28\\lang1031\\sbasedon1\\snext3 Heading;}");
        sb.append("{\\s8\\sb120\\sa120\\rtlch\\af10\\afs24\\lang255\\ai\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\i\\loch\\fs24\\lang1031\\i\\sbasedon1\\snext8 caption;}");
        sb.append("{\\s9\\rtlch\\af10\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon1\\snext9 Index;}");
        sb.append("{\\s10\\rtlch\\af9\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon1\\snext10 Table Contents;}");
        sb.append("{\\s11\\qc\\rtlch\\af9\\afs24\\lang255\\ai\\ab\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\i\\b\\loch\\fs24\\lang1031\\i\\b\\sbasedon10\\snext11 Table Heading;}");
        sb.append("{\\s12\\rtlch\\af9\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031\\sbasedon1\\snext12 Table Contents;}");
        sb.append("{\\s13\\qc\\rtlch\\af9\\afs24\\lang255\\ai\\ab\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\i\\b\\loch\\fs24\\lang1031\\i\\b\\sbasedon12\\snext13 Table Heading;}");
        sb.append("}");
        sb.append("{\\info{\\creatim\\yr2006\\mo9\\dy12\\hr22\\min54}{\\revtim\\yr1601\\mo1\\dy1\\hr0\\min0}{\\printim\\yr1601\\mo1\\dy1\\hr0\\min0}{\\comment StarWriter}{\\vern6800}}\\deftab709");
        sb.append("{\\*\\pgdsctbl");
        sb.append("{\\pgdsc0\\pgdscuse195\\pgwsxn11905\\pghsxn16837\\marglsxn1134\\margrsxn1134\\margtsxn1134\\margbsxn1134\\pgdscnxt0 Standard;}}");
        sb.append("{\\*\\pgdscno0}\\paperh16837\\paperw11905\\margl1134\\margr1134\\margt1134\\margb1134\\sectd\\sbknone\\pgwsxn11905\\pghsxn16837\\marglsxn1134\\margrsxn1134\\margtsxn1134\\margbsxn1134\\ftnbj\\ftnstart1\\ftnrstcont\\ftnnar\\aenddoc\\aftnrstcont\\aftnstart1\\aftnnrlc");
        sb.append("\\trowd\\trql\\trrh-567\\trpaddft3\\trpaddt55\\trpaddfl3\\trpaddl55\\trpaddfb3\\trpaddb55\\trpaddfr3\\trpaddr55\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx568\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx1137\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx1706\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx2275\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx2844\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx3412\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx3981\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx4550\\clbrdrt\\brdrs\\brdrw1\\brdrcf1\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clbrdrr\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx5119\\clvertalc\\cellx9635");
        sb.append("\\pard\\intbl\\pard\\plain \\intbl\\ltrpar\\s11\\qc\\rtlch\\af9\\afs36\\lang255\\ai\\ab\\ltrch\\dbch\\af6\\langfe255\\hich\\fs36\\lang1031\\loch\\fs36\\lang1031 {\\rtlch \\ltrch\\loch\\f1\\fs36\\lang1031\\i0\\b0 ");
        val = SudokuGrid.getGridVal(sg.grid[0][0]);
        isDefault = SudokuGrid.isDefault(sg.grid[0][0]);
        sb.append((val == 0 || !isDefault) ? " " : val);
        sb.append("}");
        for (int i = 0; i < 9; i++) {
            if (i != 0) {
                sb.append("\\cell\\row\\pard \\trowd\\trql\\trrh-567\\trpaddft3\\trpaddt55\\trpaddfl3\\trpaddl55\\trpaddfb3\\trpaddb55\\trpaddfr3\\trpaddr55\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx568\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx1137\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx1706\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx2275\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx2844\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx3412\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx3981\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx4550\\clbrdrl\\brdrs\\brdrw1\\brdrcf1\\clbrdrb\\brdrs\\brdrw1\\brdrcf1\\clbrdrr\\brdrs\\brdrw1\\brdrcf1\\clvertalc\\cellx5119\\clvertalc\\cellx9635");
                sb.append("\\pard\\intbl\\pard\\plain \\intbl\\ltrpar\\s10\\qc\\rtlch\\af9\\afs36\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs36\\lang1031\\loch\\fs36\\lang1031 {\\rtlch \\ltrch\\loch\\f1\\fs36\\lang1031\\i0\\b0 ");
                val = SudokuGrid.getGridVal(sg.grid[0][i]);
                isDefault = SudokuGrid.isDefault(sg.grid[0][i]);
                sb.append((val == 0 || !isDefault) ? " " : val);
                sb.append("}");
            }
            for (int j = 1; j < 9; j++) {
                sb.append("\\cell\\pard\\plain \\intbl\\ltrpar\\s10\\qc\\rtlch\\af9\\afs36\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs36\\lang1031\\loch\\fs36\\lang1031 {\\rtlch \\ltrch\\loch\\f1\\fs36\\lang1031\\i0\\b0 ");
                val = SudokuGrid.getGridVal(sg.grid[j][i]);
                isDefault = SudokuGrid.isDefault(sg.grid[j][i]);
                sb.append((val == 0 || !isDefault) ? " " : val);
                sb.append("}");
            }
            sb.append("\\cell\\pard\\plain \\intbl\\ltrpar\\s10\\qc\\rtlch\\af9\\afs36\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs36\\lang1031\\loch\\fs36\\lang1031 ");
        }
        sb.append("\\cell\\row\\pard \\pard\\plain \\ltrpar\\s1\\ql\\rtlch\\af9\\afs24\\lang255\\ltrch\\dbch\\af6\\langfe255\\hich\\fs24\\lang1031\\loch\\fs24\\lang1031 ");
        sb.append("\\par \\pard\\plain \\ltrpar\\s1\\cf0{\\*\\hyphen2\\hyphlead2\\hyphtrail2\\hyphmax0}\\ql {\\rtlch \\ltrch\\loch\\f1\\fs20\\lang1031\\i0\\b0 Generated with Star SUDOKU (" + GV.WEB_PAGE + ")}\\par }");
        return sb.toString();
    }

    public static void writeString(String s, File file)
            throws IOException {
        FileWriter f1 = new FileWriter(file);
        BufferedWriter f2 = new BufferedWriter(f1);
        f2.write(s);
        f2.close();
        f1.close();
    }
}
