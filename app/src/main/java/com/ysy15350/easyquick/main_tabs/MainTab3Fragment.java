package com.ysy15350.easyquick.main_tabs;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.account.BalanceActivity;
import com.ysy15350.easyquick.account.PaySureActivity;
import com.ysy15350.easyquick.account.RechargeActivity;
import com.ysy15350.easyquick.account.SetPayPriceActivity;
import com.ysy15350.easyquick.account.WithdrawActivity;
import com.ysy15350.easyquick.login.LoginActivity;
import com.ysy15350.easyquick.mine.MyInfoActivity;
import com.ysy15350.easyquick.others.SettingActivity;
import com.yzq.zxinglibrary.Consants;
import com.yzq.zxinglibrary.android.CaptureActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.Map;

import api.base.model.Config;
import api.base.model.Response;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseFragment;
import cn.jpush.android.api.JPushInterface;
import common.CommFun;
import common.CommFunAndroid;
import common.permission.RequestPermissionType;
import common.string.JsonConvertor;


/**
 * Created by yangshiyou on 2017/8/30.
 */

@ContentView(R.layout.activity_main_tab3)
public class MainTab3Fragment extends MVPBaseFragment<MainTab3ViewInterface, MainTab3Presenter>
        implements MainTab3ViewInterface {

    private static final String TAG = "MainTab3Fragment";

    public MainTab3Fragment() {
    }

    @Override
    public MainTab3Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab3Presenter(getActivity());
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.user_info();
        mPresenter.adlist();//广告图
    }

    @Override
    public void user_infoCallback(boolean isCache, Response response) {


        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("storeInfo", response.getResultJson());
                    UserInfo userInfo = response.getData(UserInfo.class);
                    userInfo.setUid(BaseData.getInstance().getUid());
                    BaseData.getInstance().setUserInfo(userInfo);

                    bindData();
                } else if (code == 502) {
                    showMsg(msg);

                    getActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
//                MessageBox.show("跳转");

                        }
                    }, 1 * 1000);//3秒后执行


                } else {
                    showMsg(msg);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void adlistCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    BaseData.setCache("adlist", response.getResultJson());
                    Map<String, String> map = response.getData(new TypeToken<Map<String, String>>() {
                    }.getType());

                    bindAdList(map);//绑定广告图
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bindData() {
        super.bindData();


        try {
            // if (BaseData.getInstance().getUid() != 0) {

            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            bindUserInfo(userInfo);


            //        BaseData.setCache("adlist", response.getResultJson());
            String adlistJson = BaseData.getCache("adlist");

            if (!CommFunAndroid.isNullOrEmpty(adlistJson)) {
                Map<String, String> map = JsonConvertor.fromJson(adlistJson, new TypeToken<Map<String, String>>() {
                }.getType());

                bindAdList(map);//绑定广告图
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定用户信息
     *
     * @param userInfo
     */
    private void bindUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null) {

                //设置别名，SettingActivity注销功能删除别名
                JPushInterface.setAlias(getActivity().getApplicationContext(), userInfo.getUid(), CommFunAndroid.getDeviceId(getActivity().getApplicationContext()));
                //JPushInterface.getAlias(getActivity().getApplicationContext(), userInfo.getUid());//查询别名


                //
                mHolder
                        .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getLitpic(), true)
                        .setText(R.id.tv_nickName, userInfo.getName())
                        .setText(R.id.tv_mobile, userInfo.getMobile())
                        .setText(R.id.tv_profit, String.format("%.2f", userInfo.getProfit()))
                        .setText(R.id.tv_balance, String.format("%.2f", userInfo.getBalance()))
                        .setText(R.id.tv_rate, String.format("%.2f", userInfo.getRate()))
                ;
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定广告图
     *
     * @param map
     */
    private void bindAdList(Map<String, String> map) {

        try {
            if (null != map) {
                String icon = map.get("icon");
                if (!CommFunAndroid.isNullOrEmpty(icon)) {
                    mHolder.setImageURL(R.id.img_ad, ConfigHelper.getServerUrl(Config.isDebug, false) + icon, 480, 150);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     *
     * @param view
     */
    @Event(value = R.id.btn_setting)
    private void btn_settingClick(View view) {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.ll_info)
    private void ll_infoClick(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, MyInfoActivity.class));
    }

    public static final int REQUEST_CODE_SCAN = 1;

    public static int type = 1;


    /**
     * 扫一扫
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
//        https://github.com/yuzhiqiang1993/zxing
        type = 1;
        mPresenter.checkUserinfo();

    }

    private void scanQrcode() {
        if (isLogin()) {

            if (callCameraPermission(getActivity())) {
                Intent intent = new Intent(getActivity(),
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        }
    }

    /**
     * 付钱
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {

        type = 2;
        mPresenter.checkUserinfo();


    }

    /**
     * 余额
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, BalanceActivity.class));
    }


    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, RechargeActivity.class));
    }

    /**
     * 提现
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, WithdrawActivity.class));
    }


    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {
        if (isLogin())
            startActivity(new Intent(mContext, MyInfoActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Consants.CODED_CONTENT);
                //resultTv.setText("扫描结果为：" + content);

                scanResult(content);
            }
        }
    }

    private void scanResult(String content) {


        try {
            if (CommFunAndroid.isNullOrEmpty(content)) {
                showMsg("扫描结果为空");
                return;
            } else if (!CommFun.isNullOrEmpty(content) && CommFun.isNum(content)) {

                //showMsg("微信二维码：" + content);
                mPresenter.micro_pay(content);

                return;

            } else if (content.contains("alipay") || content.contains("ALIPAY")) {
                showMsg("请使用支付宝客户端扫描");
                return;
            } else if (content.contains("weixin") || content.contains("wx")) {
                showMsg("请使用微信客户端扫描");
                return;
            } else if (CommFunAndroid.isNum(content)) {
                showMsg("无法识别二维码");
                return;
            }

            Intent intent = new Intent(getActivity(), PaySureActivity.class);
            intent.putExtra("content", content);
            startActivity(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void micro_payCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请读取文件权限
     */
    public boolean callCameraPermission(Activity activity) {
        Log.d(TAG, "callReadExtrnalStoreagePermission() called with: activity = [" + activity + "]");

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

            Log.d(TAG, "callReadExtrnalStoreagePermission() called with: checkCallPhonePermission = [" + checkCallPhonePermission + "]");

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.CAMERA}
                        , RequestPermissionType.REQUEST_CODE_ASK_CAMERA);
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanQrcode();

                } else {
                    //showMsg("拒绝");
                    // Permission Denied
                    //Toast.makeText(this, "CALL_PHONE Denied", Toast.LENGTH_SHORT).show();
                    showMsg("您已拒绝系统使用相机权限");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void checkUserinfoCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    if (type == 1) {
                        scanQrcode();
                    } else if (type == 2) {
                        if (isLogin()) {
                            startActivity(new Intent(mContext, SetPayPriceActivity.class));
                        }
                    }
                } else showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
