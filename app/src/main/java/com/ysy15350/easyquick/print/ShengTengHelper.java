package com.ysy15350.easyquick.print;

import android.os.RemoteException;
import android.view.View;

import com.centerm.smartpos.aidl.printer.AidlPrinterStateChangeListener;
import com.centerm.smartpos.aidl.printer.PrintDataObject;
import com.centerm.smartpos.constant.DeviceErrorCode;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import common.CommFunAndroid;
import common.message.CommFunMessage;

/**
 * Created by yangshiyou on 2017/11/17.
 */

public class ShengTengHelper {

    private static ShengTengHelper mInstance;

    public static ShengTengHelper getInstance() {
        if (mInstance == null)
            mInstance = new ShengTengHelper();
        return mInstance;
    }

    private ShengTengHelper() {


    }

    public void print(String title, String content) {
        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                float size = 35;
                boolean isBold = true;
                boolean isUnderLine = false;


                List<PrintDataObject> list = new ArrayList<PrintDataObject>();

                list.add(new PrintDataObject(title, 35,
                        true, PrintDataObject.ALIGN.CENTER));


                list.add(new PrintDataObject(content, 10,
                        false, PrintDataObject.ALIGN.LEFT));


                try {
                    if (BaseActivity.printDev != null)
                        BaseActivity.printDev.printText(list, callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printText(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                float size = 35;
                boolean isBold = true;
                boolean isUnderLine = false;


                List<PrintDataObject> list = new ArrayList<PrintDataObject>();

                list.add(new PrintDataObject(content, 10,
                        false, PrintDataObject.ALIGN.LEFT));

                try {
                    if (BaseActivity.printDev != null)
                        BaseActivity.printDev.printText(list, callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 打印机回调对象
    private AidlPrinterStateChangeListener callback = new PrinterCallback(); // 打印机回调

    public void printTitle(String content) {

        try {
            if (!CommFunAndroid.isNullOrEmpty(content)) {

                float size = 35;
                boolean isBold = true;
                boolean isUnderLine = false;


                List<PrintDataObject> list = new ArrayList<PrintDataObject>();

                list.add(new PrintDataObject(content, 24,
                        false, PrintDataObject.ALIGN.CENTER));

                try {
                    if (BaseActivity.printDev != null)
                        BaseActivity.printDev.printText(list, callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印机回调类
     */
    private class PrinterCallback extends AidlPrinterStateChangeListener.Stub {

        @Override
        public void onPrintError(int arg0) throws RemoteException {
            // showMessage("打印机异常" + arg0, Color.RED);
            getMessStr(arg0);
        }

        @Override
        public void onPrintFinish() throws RemoteException {
            //showMessage(getString(R.string.printer_finish), "", Color.BLACK);//打印数据完成
        }

        @Override
        public void onPrintOutOfPaper() throws RemoteException {
            //showMessage(getString(R.string.printer_need_paper), "", Color.RED);//打印机缺纸，请装纸后重试
        }
    }

    private void getMessStr(int ret) {
        switch (ret) {
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_BUSY:
                CommFunMessage.showMsgBox("打印机设备忙");//打印机设备忙
                break;
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_OK:
                CommFunMessage.showMsgBox("打印成功");//打印成功
                break;
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_PRINTER_OUT_OF_PAPER:
                CommFunMessage.showMsgBox("打印机缺纸");//打印机缺纸
                break;
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_PRINTER_HEAD_OVER_HEIGH:
                CommFunMessage.showMsgBox("打印机过热");//打印机过热
                break;
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_PRINTER_OVER_HEATER:
                CommFunMessage.showMsgBox("打印机高温");//打印机高温
                break;
            case DeviceErrorCode.DEVICE_PRINTER.DEVICE_PRINTER_LOW_POWER:
                CommFunMessage.showMsgBox("打印机低电量");//打印机低电量
                break;
            default:
                CommFunMessage.showMsgBox("其他异常，异常码：" + ret);//其他异常，异常码：
                break;
        }

    }

    // 打印文本内容
    public void printText(View v) {
//        List<PrintDataObject> list = new ArrayList<PrintDataObject>();
//        list.add(new PrintDataObject(
//                getString(R.string.printer_textsize_bigger), 24));
//        list.add(new PrintDataObject(getString(R.string.printer_textsize_blod),
//                8, true));
//        list.add(new PrintDataObject(getString(R.string.printer_left), 8,
//                false, ALIGN.LEFT));
//        list.add(new PrintDataObject(getString(R.string.printer_center), 8,
//                false, ALIGN.CENTER));
//        list.add(new PrintDataObject(getString(R.string.printer_right), 8,
//                false, ALIGN.RIGHT));
//        list.add(new PrintDataObject(getString(R.string.printer_underline), 8,
//                false, ALIGN.LEFT, true));
//        list.add(new PrintDataObject(getString(R.string.printer_amount), 8,
//                true, ALIGN.LEFT, false, false));
//        list.add(new PrintDataObject("888.66", 24, true, ALIGN.LEFT, false,
//                true));
//        list.add(new PrintDataObject(getString(R.string.printer_newline), 8,
//                false, ALIGN.LEFT, false, true));
//        list.add(new PrintDataObject(
//                getString(R.string.printer_acceptorid_name)));
//        list.add(new PrintDataObject(getString(R.string.printer_line_distance),
//                8, false, ALIGN.LEFT, false, true, 40, 28));
//        for (int i = 0; i < 38; i += 3) {
//            list.add(new PrintDataObject(
//                    getString(R.string.printer_left_distance) + (i * 10), 8,
//                    false, ALIGN.LEFT, false, true, 24, 0, (i * 10)));
//        }
//        try {
//            this.printDev.printText(list, callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
