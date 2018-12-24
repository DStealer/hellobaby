package com.dstealer.hellobaby.common;

import com.dstealer.hellobaby.AppTest;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiShiwu on 02/27/2017.
 */
public class ExportUtilTest extends AppTest {

    @Test
    public void test001() throws Exception {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"1", "2"});
        data.add(new String[]{"2", "3"});
        File file = new File("D:/Temporary/x.xlsx");
        file.createNewFile();
        ExportUtil.generateExcelFile(data, file);
    }
}