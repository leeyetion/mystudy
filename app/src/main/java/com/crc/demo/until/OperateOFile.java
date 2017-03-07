package com.crc.demo.until;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crcement on 17/2/26.
 */

public class OperateOFile {
    public static List<String> getFileName(String path)
    {
        File file = new File(path);
        String [] fileName = file.list();
        List<String> fileList=new ArrayList<String>();
        for (int i=0;i<fileName.length;i++){
            fileList.add(fileName[i]);
        }
        return fileList;
    }

}
