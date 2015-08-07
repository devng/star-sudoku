/* ExportPDF created on 13.09.2006 */
package com.devng.starsudoku.io;

import com.devng.starsudoku.GV;
import com.devng.starsudoku.SudokuGrid;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class ExportPDF {

    public static void export(SerGrid sg, File file) throws Exception {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(52.f);
            PdfPCell cell;
            int val;
            boolean isDefault;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    val = SudokuGrid.getGridVal(sg.grid[j][i]);
                    isDefault = SudokuGrid.isDefault(sg.grid[j][i]);
                    if (isDefault) {
                        cell = new PdfPCell(new Paragraph("" + val));
                    } else {
                        cell = new PdfPCell(new Paragraph(""));
                    }
                    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                    cell.setFixedHeight(30.f);
                    table.addCell(cell);
                }
            }

            document.add(table);
            //document.add(new Paragraph("\n"));
            Paragraph p = new Paragraph(
                    new Chunk("Generated with " + GV.NAME + " (" + GV.WEB_PAGE + ")",
                            new Font(Font.TIMES_ROMAN, 8)));
            p.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p);
        } finally {
            document.close();
        }

    }
}
