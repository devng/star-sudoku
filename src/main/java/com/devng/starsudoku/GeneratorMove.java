/* Move created on 30.01.2006 */
package com.devng.starsudoku;

public class GeneratorMove {

    private int y;
    private int x;
    private int valIndex;
    private int[] availableMoves;

    protected GeneratorMove(int x, int y, int[] availabeMoves) {
        this.y = y;
        this.x = x;
        this.availableMoves = availabeMoves;
    }

    protected int getX() {
        return x;
    }

    protected int getY() {
        return y;
    }

    protected int getVal() {
        return availableMoves[valIndex];
    }

    protected boolean setNextMove() {

        valIndex++;
        if (valIndex <= availableMoves.length - 1) {
            return true;
        }
        valIndex--;
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(28);
        sb.append("Move [x: ");
        sb.append(x);
        sb.append("; y: ");
        sb.append(y);
        sb.append("; val: ");
        sb.append(getVal());
        sb.append("]");
        return sb.toString();
    }
}
