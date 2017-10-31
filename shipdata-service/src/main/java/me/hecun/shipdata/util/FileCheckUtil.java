package me.hecun.shipdata.util;

import me.hecun.shipdata.security.common.ResponseEnum;
import me.hecun.shipdata.security.common.exception.DataFileException;
import org.apache.commons.lang.StringUtils;

import java.io.*;

/**
 * Created by hecun on 2017/10/26.
 */
public class FileCheckUtil {

    /**
     * get the ship number
     * @param fileName
     * @return
     */
    public static String getShipNumber(String fileName) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
                fileName))) {
            String firstLine = bufferedReader.readLine();
            return StringUtils.substringAfter(firstLine, "Boat:");
        } catch (IOException e) {
            throw new DataFileException(ResponseEnum.DATA_FILE_ERROR);
        }
    }

    /**
     * check the ship data file is valid or not
     * @param fileName
     * @return
     */
    public static boolean checkDataValid(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String firstLine = bufferedReader.readLine();
            String secondLine = bufferedReader.readLine();
            return StringUtils.equalsIgnoreCase(secondLine, "Data Valid.");
        } catch (IOException e) {
            throw new DataFileException(ResponseEnum.DATA_FILE_ERROR);
        }
    }

    /**
     * filter the standard data file
     * remove the first two lines
     * replace '$' and '*' in ship data file to ''
     * @param fileName
     * @param target
     */
    public static void filterDataFile(String fileName, String target) {

        //创建文件对象
        File file = new File(fileName);
        // tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
        File tmpfile = new File(fileName + ".tmp");

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

            boolean flag = false;

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(target)) {
                    bufferedWriter.write(StringUtils.replace(line, target, "") + "\n");
                    flag = true;
                }
            }

            if (flag) {
                file.delete();
                tmpfile.renameTo(new File(fileName));
            } else
                tmpfile.delete();

        } catch (IOException e) {
            throw new DataFileException(ResponseEnum.DATA_FILE_ERROR);
        }
    }

    /**
     * get file line count
     *
     * @param fileName
     * @return
     */
    public static Integer getTotalLines(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String strLine = bufferedReader.readLine();
            Integer totalLines = 0;
            while (strLine != null) {
                totalLines++;
                strLine = bufferedReader.readLine();
            }
            return totalLines;
        } catch (IOException e) {
            throw new DataFileException(ResponseEnum.DATA_FILE_ERROR);
        }
    }
}
