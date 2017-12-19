package com.ysy15350.easyquick.print;

import common.CommFunAndroid;
import common.printer_helper.AidlUtil;

/**
 * Created by yangshiyou on 2017/11/17.
 */

public class ShangMiPrintHelper {

    public static void print(String title, String content) {
        printTitle(title);
        printText(content);
    }

    public static void printText(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                float size = 24;
                boolean isBold = false;
                boolean isUnderLine = false;

                AidlUtil.getInstance().printText(content, size, isBold, isUnderLine, 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printTitle(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                float size = 35;
                boolean isBold = true;
                boolean isUnderLine = false;

                AidlUtil.getInstance().printText("\t\t\t\t\t\t\t\t" + content, size, isBold, isUnderLine, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
