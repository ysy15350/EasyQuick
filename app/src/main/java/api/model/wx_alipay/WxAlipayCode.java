package api.model.wx_alipay;

/**
 * Created by yangshiyou on 2017/10/10.
 */

public class WxAlipayCode {

    //{
//        "code":200, "result":
//        "{\"head\":{\"respTime\":\"20171010163440\",\"respMsg\":\"\u6210\u529f\",
// \"version\":\"1.0\",\"respCode\":\"000000\"},\"body\":
// {\"extend\":\"\",\"totalAmount\":\"00000000010\",\
// "qrCode\":\"https:\/\/qr.alipay.com\/bax01393chcfstkr7ckl00c7\",
// \"orderCode\":\"2017101051971015\",\"merchExtendParams\":\"\"}}", "price":
//        "1.0"
    //}

    private Head head;

    private Body body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


}
