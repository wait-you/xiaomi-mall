package cn.wenhe9.myshop.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private String appid;
    private String bank_type;
    private String cash_fee;
    private String is_subscribe;
    private String mch_id;
    private String nonce_str;
    private String openid;
    private String out_trade_no;
    private String result_code;//支付结果
    private String return_code;
    private String sign;

    private String time_end;
    private String total_fee;//总支付价格
    private String trade_type;
    private String transaction_id;
}
