package base.data.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/9/13.
 */

@Table(name = "userInfo")
public class UserInfo {

//    {"code":200,"result":
// {"headimgurl":"\/Uploads\/Picture\/2017-09-26\/59c9d06ed6614.jpeg",
// "nickname":"aa","mobile":"15212341234",
// "cards":"65565656","address":"aaaafffdd",
// "balance":"0.00",
// "total_price":0,
// "day_total_price":0,
// "rate":"9.9"}}


    /**
     * 主键
     */
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "uid")
    private int uid;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "cards")
    private String cards;

    @Column(name = "address")
    private String address;

    @Column(name = "pay_password")
    private String pay_password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "headimgurl")
    private String headimgurl;//图片id

    @Column(name = "balance")
    private float balance;

    @Column(name = "total_price")
    private float total_price;

    @Column(name = "day_total_price")
    private float day_total_price;

    @Column(name = "rate")
    private float rate;

    @Column(name = "reg_type")
    private String reg_type;//商家

    @Column(name = "type")
    private int type;//1:商家；2：经销商


    //商家新增

    @Column(name = "icon")
    private int icon;

    @Column(name = "litpic")
    private String litpic;

    @Column(name = "name")
    private String name;

    @Column(name = "license")
    private String license;

    @Column(name = "license_icon")
    private String license_icon;

    @Column(name = "license_litpic")
    private String license_litpic;

    @Column(name = "bus_id")
    private String bus_id;

    @Column(name = "business")
    private String business;

    @Column(name = "description")
    private String description;

    @Column(name = "profit")
    private float profit;

    @Column(name = "license_status")
    private int license_status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public float getDay_total_price() {
        return day_total_price;
    }

    public void setDay_total_price(float day_total_price) {
        this.day_total_price = day_total_price;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicense_icon() {
        return license_icon;
    }

    public void setLicense_icon(String license_icon) {
        this.license_icon = license_icon;
    }

    public String getLicense_litpic() {
        return license_litpic;
    }

    public void setLicense_litpic(String license_litpic) {
        this.license_litpic = license_litpic;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public int getLicense_status() {
        return license_status;
    }

    public void setLicense_status(int license_status) {
        this.license_status = license_status;
    }
}
