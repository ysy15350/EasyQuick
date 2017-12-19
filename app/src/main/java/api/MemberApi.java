package api;

import java.io.File;

import api.base.ApiCallBack;


public interface MemberApi {

    public void getTelVerify(int type,String mobile, ApiCallBack callBack);

    public void shopRegister(String mobile, String password, String verify, ApiCallBack callBack);


    /**
     * 上传文件
     *
     * @param file
     * @param callBack
     */
    public void uppicture(File file, ApiCallBack callBack);

    /**
     * 个人资料
     *
     * @param uid
     * @param callBack
     */
    public void user_info(ApiCallBack callBack);

    /**
     * 修改个人资料
     *
     * @param callBack
     */
    public void save_info(String headimgurl, String nickname, String cards, String address, ApiCallBack callBack);


    /**
     * 店铺个人资料
     *
     * @param callBack
     */
    public void store_info(ApiCallBack callBack);

    /**
     * 修改店铺资料
     *
     * @param icon         头像图片id
     * @param name         店铺名称
     * @param license_icon 执照图片id
     * @param mobile       手机号
     * @param address      地址
     * @param business     经营范围  选择’,’组合ID   示例1,2
     * @param description  店铺描述
     * @param callBack
     */
    public void store_save_info(int icon, String name, String license, String license_icon, String mobile, String address, String business, String description, String pay_password, ApiCallBack callBack);

    /**
     * 修改手机号
     *
     * @param password
     * @param mobile
     * @param mobile_code
     * @param callBack
     */
    public void save_mobile(String password, String mobile, String mobile_code, ApiCallBack callBack);

    /**
     * 找回密码
     *
     * @param password
     * @param mobile
     * @param mobile_code
     * @param callBack
     */
    public void save_password(String password, String mobile, String mobile_code, ApiCallBack callBack);


    /**
     * 广告图
     *
     * @param type
     * @param callBack
     */
    public void adlist(int type, ApiCallBack callBack);

    /**
     * 店铺分类
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void store_cate(int page, int pageSize, ApiCallBack callBack);


    /**
     * 店铺列表
     *
     * @param lat
     * @param log
     * @param business 分类id
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void store_list(double lat, double log, int business, int page, int pageSize, ApiCallBack callBack);


    /**
     * 银行列表
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void bank_list(int page, int pageSize, ApiCallBack callBack);

    /**
     * 银行卡列表
     *
     * @param type
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void user_bank(int type, int page, int pageSize, ApiCallBack callBack);

    /**
     * 添加银行卡
     *
     * @param type
     * @param username
     * @param mobile
     * @param bank_id
     * @param open_bank
     * @param callBack
     */
    public void add_user_bank(int type, String username, String mobile, String number, int bank_id, String open_bank, ApiCallBack callBack);

    /**
     * 订单列表
     *
     * @param start_time
     * @param end_time
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void order_list(String start_time, String end_time, int page, int pageSize, ApiCallBack callBack);

    /**
     * 充值预下单
     *
     * @param type
     * @param price
     * @param callBack
     */
    public void order_change(int type, int price, ApiCallBack callBack);


    /**
     * 提现
     *
     * @param txprice
     * @param bank_id
     * @param callBack
     */
    public void withdrawals(float txprice, int bank_id, ApiCallBack callBack);

    /**
     * 提现汇率
     *
     * @param callBack
     */
    public void tx_rate(ApiCallBack callBack);

    /**
     * 支付密码验证
     *
     * @param password
     * @param callBack
     */
    public void personPayPassword(String password, ApiCallBack callBack);


    /**
     * 扫一扫余额支付
     *
     * @param sid
     * @param price
     * @param callBack
     */
    public void user_payment(String sid, String price, String remark, ApiCallBack callBack);


    /**
     * 扫一扫余额支付
     *
     * @param type     1：个人付款给商家，2商家付款给商家
     * @param uid_a    付款方id
     * @param uid_b    收款方id
     * @param price
     * @param remark
     * @param callBack
     */
    public void user_payment(int type, String uid_a, String uid_b, String price, String remark, ApiCallBack callBack);

    /**
     * 余额明细
     *
     * @param callBack
     */
    public void balance_list(int page, int pageSize, ApiCallBack callBack);

    /**
     * 收益明细
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void profit_list(int page, int pageSize, ApiCallBack callBack);


    /**
     * 生成支付宝微信二维码
     *
     * @param uid
     * @param type
     * @param price
     * @param callBack
     */
    public void wx_alipay_code(int uid, int type, float price, String remark, ApiCallBack callBack);


    /**
     * 商家邀请的会员交易记录接口
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void personWater(int page, int pageSize, ApiCallBack callBack);

    /**
     * 商家佣金记录
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void shopYonjinWater(int page, int pageSize, ApiCallBack callBack);

    /**
     * 检查用户信息是否完善接口（type:1个人，2商家， uid：用户id）
     *
     * @param callBack
     */
    public void checkUserinfo(ApiCallBack callBack);

    /**
     * 银行卡删除
     *
     * @param bank_id
     * @param callBack
     */
    public void del_user_bank(int bank_id, ApiCallBack callBack);

}
