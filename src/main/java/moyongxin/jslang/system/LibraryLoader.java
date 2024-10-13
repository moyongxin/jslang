package moyongxin.jslang.system;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibraryLoader {
    public static File tmpDir;

    public static synchronized void load(String path) {
        try {
            if (tmpDir == null) {
                tmpDir = new File(".jslang-libs/");
                if (!tmpDir.exists()) {
                    tmpDir.mkdirs();
                }
            }
            File tmpfile = new File(tmpDir, path);
            String res_path = "/" + path;
            InputStream is = LibraryLoader.class.getResourceAsStream(res_path);
            OutputStream os = new FileOutputStream(tmpfile);
            os.write(is.readAllBytes());
            os.close();
            is.close();
            System.load(tmpfile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
