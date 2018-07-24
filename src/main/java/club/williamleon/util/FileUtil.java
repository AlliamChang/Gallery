package club.williamleon.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by 53068 on 2018/4/15 0015.
 */
public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{
        File targetDir = new File(filePath);
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
