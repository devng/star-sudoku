/* undoRedoStack created on 22.07.2006 */
package net.sourceforge.starsudoku;

import javax.swing.JButton;

public class UndoRedoStack {

    private int[] newStack;
    private int[] oldStack;
    private int start;
    private int end;
    private int popBack;
    private int size;
    private JButton undo;
    private JButton redo;

    
    public UndoRedoStack(int size) {
        if(size < 3) {
            throw new IllegalArgumentException("Stack size must be atleast 3.");
        }
        newStack = new int[size];
        oldStack = new int[size];
        start = popBack =  0;
        end = size - 1;
        this.size = size;
    }
    
    public void setUndoButton(JButton u) {
        undo = u;
    }
    
    public void setRedoButton(JButton r) {
        redo = r;
    }
    
    private void updateButttons() {
        int nextStart = start -1;
        
        if(nextStart < 0) {
            nextStart = size - 1;
        }
        if(nextStart == end) {
            undo.setEnabled(false);
        } else {
            undo.setEnabled(true);
        }
        
        if(popBack == start) {
            redo.setEnabled(false);
        } else {
            redo.setEnabled(true);
        }
    }
     
    public void push(int oldVal, int newVal) {
        if(SudokuGrid.isDefault(oldVal) || !SudokuGrid.isEditable(oldVal)) {
            return;
        }
        
        start = (start + 1) % size;
        popBack = start;
        if(start == end) {
            end = (end + 1) % size;
        }
        newStack[start] = newVal;
        oldStack[start] = oldVal;
        updateButttons();
    }
    
    public int undo() {
        int r = oldStack[start];
        start--;
        if(start < 0) {
            start = size-1;
        }
        
        if(start == end) {
            start = (start + 1) % size;
        }
        updateButttons();
        return r;
    }
    
    public int redo() {
        if(popBack == start) {
            updateButttons();
            return -1;
        } else {
            start = (start + 1) % size;
            updateButttons();
            return newStack[start];
        }
    }
    
    public void reset() {
        start = popBack = 0;
        end = size - 1;
        undo.setEnabled(false);
        redo.setEnabled(false);
    }
}
