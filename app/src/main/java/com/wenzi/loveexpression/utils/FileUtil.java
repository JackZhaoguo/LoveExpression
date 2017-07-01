package com.wenzi.loveexpression.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by meizu on 16/10/31.
 */
public class FileUtil {

    private final static String LOG_TAG = "FileUtil";

    private static final String FILE_HEAD = "update_cache_";
    private static final String FILE_TAIL_TEMP = ".temp";
    private static final String FILE_TAIL_JAR = ".jar";

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：/files/fqf.txt
     * @param newPath String 复制后路径 如：/user/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread;
            File oldfile = new File(oldPath);
            if (!oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：/files/fqf
     * @param newPath String 复制后路径 如：/user/ff/fqf
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } else if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copyStream(InputStream input, OutputStream output) {
        byte[] buffer = new byte[1024 * 8];
        BufferedInputStream in = new BufferedInputStream(input, 1024 * 8);
        BufferedOutputStream out = new BufferedOutputStream(output, 1024 * 8);

        int count = 0;
        int n;
        try {
            while ((n = in.read(buffer, 0, 1024 * 8)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } catch (IOException e) {
            LogUtil.e(LOG_TAG, "Extracted IOException:" + e.toString());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                LogUtil.e(LOG_TAG, "out.close() IOException e=" + e.toString());
            }
            try {
                in.close();
            } catch (IOException e) {
                LogUtil.e(LOG_TAG, "in.close() IOException e=" + e.toString());
            }
        }
        return count;
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag;
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        return dirFile.delete();
    }


    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString;
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static String loadFile(String fileWithPath) {
        if (fileWithPath == null || fileWithPath.isEmpty()) {
            return null;
        }
        InputStream instream;
        File file;
        try {
            file = new File(fileWithPath);
        } catch (NullPointerException e) {
            LogUtil.e(LOG_TAG, "new File error=" + e.toString());
            return null;
        }

        if (file.isDirectory() || !file.exists()) {
            LogUtil.e(LOG_TAG, "file.isDirectory()=" + file.isDirectory() + ", file.exists()=" + file.exists());
            return null;
        }

        try {
            instream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LogUtil.e(LOG_TAG, "new FileInputStream error=" + e.toString());
            return null;
        }

        StringBuilder jsonBuffer = new StringBuilder();
        if (instream != null) {
            try {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while ((line = buffreader.readLine()) != null) {
                    jsonBuffer.append(line).append("\n");
                }
            } catch (IOException e) {
                LogUtil.e(LOG_TAG, "read file buffer error=" + e.toString());
                return null;
            } finally {
                try {
                    instream.close();
                } catch (IOException e) {
                    LogUtil.e(LOG_TAG, "instream.close error=" + e.toString());
                }
            }
        }

        return jsonBuffer.toString();
    }

    public static String getDefaultNewActiveJarPath(Context context) {
        return context.getCacheDir() + "/" + Constants.UPGRADE_ACTIVE_JAR_FILE_NAME;
    }

    public static String getActiveViewCachesDir(Context context) {
        File fileDir = context.getExternalCacheDir();
        if (fileDir != null) {
            // default ActiveView resources saved path.
            return fileDir.toString() + "/" + Constants.ACTIVE_CACHE_DIRECTORY;
        }
        return null;
    }

    private static final String VOLLEY_DEFAULT_CACHE_DIR = "volley";

    public static String getVollyCacheDir(Context context) {
         return context.getCacheDir() + "/" + VOLLEY_DEFAULT_CACHE_DIR;
    }

    /**
     * 重命名一个文件
     */
    public static final boolean renameFile(String srcPath, String destPath) {
        try {
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);
            return srcFile.renameTo(destFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static final String getCacheFileName(String versionName) {
        return FILE_HEAD + versionName + FILE_TAIL_TEMP;
    }

    private static final String getApkFileName(String versionName) {
        return FILE_HEAD + versionName + FILE_TAIL_JAR;
    }

    private static final String getCachePath(String packageName) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + packageName + "/InstallCache/";
    }

    /**
     * 解压zip包数据流到指定目录
     * @param inputStream
     * @param outputDirectory
     * @param isReWrite
     * @throws IOException
     */
    public static String unZipFromInputStream(InputStream inputStream, String outputDirectory, boolean isReWrite) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开压缩文件
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        String zipDirName = zipEntry.getName();
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者是文件不存在
                if(isReWrite || !file.exists()){
                    file.mkdir();
                }
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者文件不存在，则解压文件
                if(isReWrite || !file.exists()){
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        return outputDirectory + "/" +zipDirName;
    }


    /**
     * 清除缓存目录
     *
     * @param dirPath 目录
     * @return
     */

    private final static int MILLIS_ONE_HOUR = 3600000;

    public int clearCacheFolder(String dirPath, int moreThanHours) {
        if (dirPath == null || dirPath.isEmpty()) {
            return -1;
        }
        File dirFile = new File(dirPath);
        return clearCacheFolder(dirFile, System.currentTimeMillis(), moreThanHours * MILLIS_ONE_HOUR);
    }

    private int clearCacheFolder(File dir, long curTime, long moreThan) {
        int deletedNum = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.lastModified() + moreThan <= curTime) {
                        if (child.isDirectory()) {          // 删除超过设定保存时间的本地缓存
                            FileUtil.deleteDirectory(child.getAbsolutePath());
                        } else {
                            FileUtil.deleteFile(child.getAbsolutePath());
                        }
                        deletedNum++;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedNum;
    }
}
