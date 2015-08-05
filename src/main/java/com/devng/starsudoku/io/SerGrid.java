/* SerGrid created on 12.07.2006 */
package com.devng.starsudoku.io;

import java.io.Serializable;

public class SerGrid implements Serializable {

    public int[][] grid;
    public int difficulty;
    public int curH;
    public int curM;
    public int curS;
    public int bestH;
    public int bestM;
    public int bestS;
    public String bestBy;

    public SerGrid() {
        grid = new int[9][9];
        bestBy = "";
    }

}
