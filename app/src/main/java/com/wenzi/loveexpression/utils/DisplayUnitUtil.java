package com.wenzi.loveexpression.utils;

import android.content.Context;

/*
 * sp, px, dp单位转换
 */
public class DisplayUnitUtil {
    private final static String LOG_TAG = "DisplayUnitUtil";

    final static String DISPLAY_UNIT_PX = "px";
    final static String DISPLAY_UNIT_SP = "sp";
    final static String DISPLAY_UNIT_DP = "dp";
    final static String DISPLAY_UNIT_DIP = "dip";

    public static int getPixelValue(Context context, String jsonValue) {
        if (context == null || jsonValue == null || jsonValue.isEmpty()) {
            return 0;
        }

        String unitStr;
        if (jsonValue.endsWith("sp")) {
            unitStr = DISPLAY_UNIT_SP;
        } else if (jsonValue.endsWith("dp")) {
            unitStr = DISPLAY_UNIT_DP;
        } else if (jsonValue.endsWith("dip")) {
            unitStr = DISPLAY_UNIT_DIP;
        } else {
            unitStr = DISPLAY_UNIT_PX;
        }
        int value = 0;
        try {
            if (unitStr.equals(DISPLAY_UNIT_DP)) {
                value = dip2px(context, Float.parseFloat(jsonValue.substring(0, jsonValue.length() - 2)));

            } else if (unitStr.equals(DISPLAY_UNIT_DIP)) {
                value = dip2px(context, Float.parseFloat(jsonValue.substring(0, jsonValue.length() - 3)));

            } else if (unitStr.equals(DISPLAY_UNIT_SP)) {
                value = sp2px(context, Float.parseFloat(jsonValue.substring(0, jsonValue.length() - 2)));

            } else {
                value = (int) Float.parseFloat(jsonValue.substring(0, jsonValue.length() - 2));         // unitStr == "px"

            }
        } catch (Exception e) {
            LogUtil.e(LOG_TAG, "Exception =" + e.toString());
        }

        return value;
    }

    public static int getPixelValue(Context context, String unitStr, float srcValue) {
        if (context == null || unitStr == null || unitStr.isEmpty()) {
            return (int) srcValue;
        }

        int value;
        if (unitStr.equals(DISPLAY_UNIT_DP)) {
            value = dip2px(context, srcValue);

        } else if (unitStr.equals(DISPLAY_UNIT_DIP)) {
            value = dip2px(context, srcValue);

        } else if (unitStr.equals(DISPLAY_UNIT_SP)) {
            value = sp2px(context, srcValue);

        } else {
            value = (int)srcValue;         // unitStr == "px"

        }
        return value;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 计算公式: dp = px / (ppi / 160)
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * px = dp * ppi/160
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * sp = px / (ppi / 160)
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *  px = sp * ppi / 160
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}