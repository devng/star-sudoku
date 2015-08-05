/* LoadSaver created on 12.07.2006 */
package com.devng.starsudoku.io;

import java.io.*;

public class LoadSaver {

    public static SerGrid load(String file)
            throws ClassNotFoundException, IOException {

        FileInputStream fs = new FileInputStream(file);
        ObjectInputStream is = new ObjectInputStream(fs);
        SerGrid res = (SerGrid) is.readObject();
        is.close();
        is = null;
        fs = null;
        return res;
    }

    public static void save(SerGrid grid, File file)
            throws IOException {

        FileOutputStream fs = new FileOutputStream(file);
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(grid);
        os.close();
        os = null;
        fs = null;
    }
}
