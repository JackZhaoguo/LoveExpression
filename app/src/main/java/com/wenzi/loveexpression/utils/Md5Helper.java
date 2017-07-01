package com.wenzi.loveexpression.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by fengsh on 14-12-6.
 */
public class Md5Helper {
    private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    // 求文件内容的HASH值
    public static String md5sum(String filename) {
        InputStream is = null;
        try {
            is = new FileInputStream(filename);
            return md5sum(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    // 求文件内容的HASH值
    public static String md5sum(InputStream is) {
        try {
            int numRead = 0;
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while ((numRead = is.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            String str = encodeHex(md5.digest());
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = hexDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = hexDigits[0x0F & data[i]];
        }
        return new String(out);
    }

    public static String md5sumHeadTail(String filename, final int blockSize) {
        InputStream is = null;
        try {
            File tempFile = new File(filename);
            final long fileLength = tempFile.length();

            if (fileLength < blockSize * 2) {
                LogUtil.e("md5sumHeadTail file length is: " + fileLength);
                return null;
            }

            is = new FileInputStream(filename);

            int numRead = 0;
            int totalRead = 0;
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while ((totalRead < blockSize) && (numRead = is.read(buffer, 0, (blockSize - totalRead > buffer.length ? buffer.length : blockSize - totalRead))) > 0) {
                md5.update(buffer, 0, numRead);
                totalRead += numRead;
            }

            is.close();

            is = new FileInputStream(filename);

            long skipSize = fileLength - blockSize;
            while (skipSize > 0) {
                long numSkip = is.skip(skipSize);
                if (numSkip > 0) {
                    skipSize -= numSkip;
                } else {
                    LogUtil.e("skip file return: " + numSkip);
                }
            }

            // read until file end
            while ((numRead = is.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }

            String str = encodeHex(md5.digest());
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 描述：转换字节数组为16进制字串
     *
     * @since v0.1
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i], true));
        }
        return resultSb.toString();
    }

    public static String byteArrayToHexString(byte[] b, int beginPos, int length) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = beginPos; i < length + beginPos; i++) {
            resultSb.append(byteToHexString(b[i], true));
        }
        return resultSb.toString();
    }

    public static String byteArrayToHexStringLittleEnding(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i], false));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b, boolean bigEnding) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return (bigEnding) ? String.valueOf(hexDigits[d1] + hexDigits[d2]) : String.valueOf(hexDigits[d2] + hexDigits[d1]);
    }

    /**
     * 传入原始字符串,返回其MD5值.
     *
     * @param origin
     * @return
     */
    public static String MD5Encode(String origin) {
        return MD5Encode(origin, null);
    }

    /**
     * 把16进制字符串转换为byte数组
     *
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        if (s.length() % 2 != 0) {
            throw new RuntimeException("Error Hex String length");
        }
        byte[] result = new byte[s.length() / 2];
        for (int i = 0; i < s.length();) {
            int bytepos = i / 2;
            char c = s.charAt(i++);
            char c2 = s.charAt(i++);
            result[bytepos] = Integer.decode("0x" + c + c2).byteValue();
        }
        return result;
    }

    /**
     * MD5摘要
     *
     * @param origin 摘要原文
     * @param encoding 字符串origin 的编码
     * @return
     */
    public static String MD5Encode(String origin, String encoding) {
        String resultString = null;

        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (encoding == null) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(encoding)));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return resultString;
    }

    public static byte[] MD5Encode(byte origin[]) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(origin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
