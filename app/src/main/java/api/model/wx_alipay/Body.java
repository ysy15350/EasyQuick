package api.model.wx_alipay;

/**
 * Created by yangshiyou on 2017/10/10.
 */

public class Body {
    //        "body\":
//        // {\"extend\":\"\",\"totalAmount\":\"00000000010\",\
//// "qrCode\":\"https:\/\/qr.alipay.com\/bax01393chcfstkr7ckl00c7\",
//// \"orderCode\":\"2017101051971015\",\"merchExtendParams\":\"\"}
    private String extend;
    private String totalAmount;
    private String qrCode;
    private String orderCode;
    private String merchExtendParams;

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMerchExtendParams() {
        return merchExtendParams;
    }

    public void setMerchExtendParams(String merchExtendParams) {
        this.merchExtendParams = merchExtendParams;
    }
}
