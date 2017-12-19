package com.ysy15350.easyquick.mine;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jph.takephoto.model.TImage;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.ysy15350.easyquick.R;
import com.ysy15350.easyquick.account.MainBusinessListActivity;
import com.ysy15350.easyquick.account.SetPayPwdActivity;
import com.ysy15350.easyquick.author.UpdatePwdActivity;
import com.ysy15350.easyquick.image_select.ImgSelectActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.base.model.Config;
import api.base.model.Response;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.data.entity.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;
import custom_view.popupwindow.PhotoSelectPopupWindow;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 个人资料
 */
@ContentView(R.layout.activity_user_info)
public class MyInfoActivity extends MVPBaseActivity<MyInfoViewInterface, MyInfoPresenter>
        implements MyInfoViewInterface, Validator.ValidationListener {


    @Override
    protected MyInfoPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new MyInfoPresenter(MyInfoActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("个人资料");

        setMenuText("保存");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mUserInfo == null) {
            String storeInfoJson = BaseData.getCache("storeInfo");
            if (!CommFunAndroid.isNullOrEmpty(storeInfoJson)) {
                UserInfo storeInfo = JsonConvertor.fromJson(storeInfoJson, UserInfo.class);
                bindUserInfo(storeInfo);
            }

            mPresenter.store_info();
        }
    }


    @Override
    public void store_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {

                    BaseData.setCache("storeInfo", response.getResultJson());

                    UserInfo storeInfo = response.getData(UserInfo.class);
                    bindUserInfo(storeInfo);
                } else {
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    UserInfo mUserInfo;


    private void bindUserInfo(UserInfo userInfo) {
        try {
            if (userInfo != null) {
                mUserInfo = userInfo;
                mHolder
                        .setImageURL(R.id.img_head, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getLitpic(), true)
                        .setText(R.id.et_nickName, userInfo.getName())
                        .setText(R.id.et_mobile, userInfo.getMobile())
                        .setText(R.id.et_license, userInfo.getLicense())
                        //.setImageURL(R.id.img_id_card_front, ConfigHelper.getServerUrl(Config.isDebug, false) + userInfo.getLicense_litpic(), 90, 50)
                        .setText(R.id.et_address, userInfo.getAddress())
                        .setText(R.id.tv_business_title, userInfo.getBusiness())
                        .setText(R.id.et_description, userInfo.getDescription())
                ;

                View btn_id_card_front = mHolder.getView(R.id.btn_id_card_front);
                View btn_id_card_back = mHolder.getView(R.id.btn_id_card_back);

                int license_status = userInfo.getLicense_status();
                if (license_status == 1) {//可上传
                    btn_id_card_front.setEnabled(true);
                    btn_id_card_back.setEnabled(true);
                    mHolder
                            .setBackgroundColor(R.id.btn_id_card_front, R.color.btn_normal)
                            .setBackgroundColor(R.id.btn_id_card_back, R.color.btn_normal)
                            .setText(R.id.btn_id_card_front, "点击上传")
                            .setText(R.id.btn_id_card_back, "点击上传");
                } else if (license_status == 2)//不可上传
                {
                    btn_id_card_front.setEnabled(false);
                    btn_id_card_back.setEnabled(false);

                    mHolder
                            .setBackgroundColor(R.id.btn_id_card_front, R.color.btn_disable)
                            .setBackgroundColor(R.id.btn_id_card_back, R.color.btn_disable)
                            .setText(R.id.btn_id_card_front, "已上传")
                            .setText(R.id.btn_id_card_back, "已上传");
                }

                String license_icon = userInfo.getLicense_icon();

                if (!CommFunAndroid.isNullOrEmpty(license_icon)) {
                    if (license_icon.contains(",")) {
                        String[] id_cards_img = license_icon.split(",");
                        if (id_cards_img != null && id_cards_img.length == 2) {
                            CommFunAndroid.setSharedPreferences("img_id_card_front_id", id_cards_img[0]);
                            CommFunAndroid.setSharedPreferences("img_id_card_back_id", id_cards_img[1]);
                        }
                    }
                }

                String license_litpic = userInfo.getLicense_litpic();
                if (!CommFunAndroid.isNullOrEmpty(license_litpic)) {
                    if (license_litpic.contains(",")) {
                        String[] id_cards_img_url = license_litpic.split(",");
                        if (id_cards_img_url != null && id_cards_img_url.length == 2) {
                            mHolder.setImageURL(R.id.img_id_card_front, ConfigHelper.getServerUrl(Config.isDebug, false) + id_cards_img_url[0], 90, 50)
                                    .setImageURL(R.id.img_id_card_back, ConfigHelper.getServerUrl(Config.isDebug, false) + id_cards_img_url[1], 90, 50);
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传头像
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {

        getPhoto("img_head");
    }

    /**
     * 上传身份证正面
     *
     * @param view
     */
    @Event(value = R.id.img_id_card_front)
    private void img_id_card_frontClick(View view) {

        //getPhoto("id_card_front");
    }

    /**
     * 上传身份证正面
     *
     * @param view
     */
    @Event(value = R.id.btn_id_card_front)
    private void btn_id_card_frontClick(View view) {

        getPhoto("id_card_front");
    }


    @Event(value = R.id.img_id_card_back)
    private void img_id_card_backClick(View view) {

        //getPhoto("id_card_back");
    }

    @Event(value = R.id.btn_id_card_back)
    private void btn_id_card_backClick(View view) {

        getPhoto("id_card_back");
    }


    private void getPhoto(String pic_type) {


        CommFunAndroid.setSharedPreferences("pic_type", pic_type);

        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PhotoSelectPopupWindow.PopListener() {
            @Override
            public void onTakePhotoClick() {
                getPhoto(2);
            }

            @Override
            public void onSelectPhotoClick() {
                getPhoto(1);
            }

            @Override
            public void onCancelClick() {

            }
        });
    }


    private void getPhoto(int type) {
        Intent intent = new Intent(this, ImgSelectActivity.class);
        CommFunAndroid.setSharedPreferences("type", type + "");
        String pic_type = CommFunAndroid.getSharedPreferences("pic_type");
        if ("icon".equals(pic_type)) {
            intent.putExtra("width", 300);
            intent.putExtra("height", 300);
        } else {
            intent.putExtra("width", 250);
            intent.putExtra("height", 160);
        }
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    /**
     * 选择主营范围
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_9)
    private void ll_menu_9Click(View view) {
        Intent intent = new Intent(this, MainBusinessListActivity.class);

        startActivityForResult(intent, BUSINESS_REQUEST);
    }

    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_11)
    private void ll_menu_11Click(View view) {
        Intent intent = new Intent(this, UpdatePwdActivity.class);

        startActivity(intent);

    }

    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_12)
    private void ll_menu_12Click(View view) {
        Intent intent = new Intent(this, SetPayPwdActivity.class);

        startActivityForResult(intent, PAY_PWD_REQUEST);

    }

    @Event(value = R.id.ll_menu)
    private void ll_menuClick(View view) {

        String nickname = mHolder.getViewText(R.id.et_nickName);
        String cards = mHolder.getViewText(R.id.et_cardNo);
        String license = mHolder.getViewText(R.id.et_license);
        String mobile = mHolder.getViewText(R.id.et_mobile);
        String address = mHolder.getViewText(R.id.et_address);
//        BaseData.setCache("myInfoBusiness_ids", ids);
//        BaseData.setCache("myInfoBusiness_titles", titles);
        String bus_id = BaseData.getCache("myInfoBusiness_ids");
        String description = mHolder.getViewText(R.id.et_description);

        if (mUserInfo != null) {
            mUserInfo.setName(nickname);
            mUserInfo.setLicense(license);
            mUserInfo.setMobile(mobile);
            mUserInfo.setAddress(address);
            mUserInfo.setBus_id(bus_id);
            mUserInfo.setDescription(description);

            store_save_info(mUserInfo);
        }
    }


    @Override
    public void save_infoCallback(boolean isCache, Response response) {

        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mPresenter.store_info();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void store_save_info(UserInfo userInfo) {
        try {
            if (userInfo != null) {

                int icon = userInfo.getIcon();
                String name = userInfo.getName();
                String licenes = userInfo.getLicense();
                String license_icon = userInfo.getLicense_icon();
                String mobile = userInfo.getMobile();
                String address = userInfo.getAddress();
                String business = userInfo.getBus_id();
                String description = userInfo.getDescription();
                String pay_password = userInfo.getPay_password();

                mPresenter.store_save_info(icon, name, licenes, license_icon, mobile, address, business, description, pay_password);
            } else
                showMsg("用户信息丢失，请重试");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store_save_infoCallback(boolean isCache, Response response) {
        try {
            if (response != null) {
                int code = response.getCode();
                String msg = response.getMessage();
                if (code == 200) {
                    mPresenter.store_info();
                    //this.finish();
                }
                showMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }

    private static final int PHOTO_REQUEST = 100;// 选择照片
    private static final int PAY_PWD_REQUEST = 101;// 设置支付密码
    private static final int BUSINESS_REQUEST = 102;// 选择经营范围


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PHOTO_REQUEST) {
                if (null != data) {//(resultCode == RESULT_OK) {
                    ArrayList<TImage> images = (ArrayList<TImage>) data.getSerializableExtra("images");
                    if (images != null && !images.isEmpty()) {
                        String path = images.get(0).getOriginalPath();
                        if (CommFunAndroid.isNullOrEmpty(path))
                            path = images.get(0).getCompressPath();

                        File file = new File(path);
                        if (file != null && file.exists()) {

                            String pic_type = CommFunAndroid.getSharedPreferences("pic_type");


                            if ("img_head".equals(pic_type)) {
                                ImageView img_head = mHolder.getView(R.id.img_head);

                                img_head.setImageURI(Uri.parse(path));
                            } else if ("id_card_front".equals(pic_type)) {

                                ImageView img_id_card_front = mHolder.getView(R.id.img_id_card_front);

                                img_id_card_front.setImageURI(Uri.parse(path));
                            } else if ("id_card_back".equals(pic_type)) {

                                ImageView img_id_card_back = mHolder.getView(R.id.img_id_card_back);

                                img_id_card_back.setImageURI(Uri.parse(path));
                            }


                        }
                        uploadImage(file);
                    }
                }
            } else if (requestCode == PAY_PWD_REQUEST) {
                String password = data.getStringExtra("password");
                UserInfo userInfo = BaseData.getInstance().getUserInfo();
                if (userInfo != null) {
                    userInfo.setPay_password(password);
                }
                store_save_info(userInfo);
            } else if (requestCode == BUSINESS_REQUEST) {
                if (data != null && resultCode == RESULT_OK) {
                    String ids = data.getStringExtra("ids");
                    String titles = data.getStringExtra("titles");
                    BaseData.setCache("myInfoBusiness_ids", ids);
                    BaseData.setCache("myInfoBusiness_titles", titles);

                    mHolder.setText(R.id.tv_business_title, titles);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(File file) {
        if (file != null && file.exists()) {
            mPresenter.uppicture(file);
        } else {
            showMsg("文件不存在");
        }

    }

    @Override
    public void uppictureCallback(Response response) {
        try {
            if (response != null) {
                //String msg = response.getMessage();
                if (response.getCode() == 200) {
                    showMsg("上传成功");
                    //\/Uploads\/Picture\/2017-09-26\/59c9ca728b2f4.png
                    String imgUrl = ConfigHelper.getServerUrl(Config.isDebug, false) + response.getPic();

                    String pic_type = CommFunAndroid.getSharedPreferences("pic_type");


                    if ("img_head".equals(pic_type)) {

                        mHolder.setImageURL(R.id.img_head, imgUrl, true);

                        //mPresenter.save_info("icon", response.getId() + "");

                        if (mUserInfo != null) {
                            mUserInfo.setIcon(response.getId());
                        }
                    } else if ("id_card_front".equals(pic_type)) {

                        mHolder.setImageURL(R.id.img_id_card_front, imgUrl, 90, 50);
                        if (mUserInfo != null) {
                            CommFunAndroid.setSharedPreferences("img_id_card_front_id" + mUserInfo.getId(), "" + response.getId());
                            String img_id_card_back_id = CommFunAndroid.getSharedPreferences("img_id_card_back_id" + mUserInfo.getId());
                            mUserInfo.setLicense_icon(response.getId() + "," + img_id_card_back_id);
                        }
                    } else if ("id_card_back".equals(pic_type)) {

                        mHolder.setImageURL(R.id.img_id_card_back, imgUrl, 90, 50);
                        if (mUserInfo != null) {
                            CommFunAndroid.setSharedPreferences("img_id_card_back_id" + mUserInfo.getId(), "" + response.getId());
                            String img_id_card_front_id = CommFunAndroid.getSharedPreferences("img_id_card_front_id" + mUserInfo.getId());
                            mUserInfo.setLicense_icon(img_id_card_front_id + "," + response.getId());
                        }
                    }
                    //showMsg(mUserInfo.getLicense_icon());
                    store_save_info(mUserInfo);
                } else {
                    showMsg("上传失败");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
