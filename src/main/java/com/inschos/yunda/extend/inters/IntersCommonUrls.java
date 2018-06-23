package com.inschos.yunda.extend.inters;

/**
 * 接口地址公共信息
 */
public class IntersCommonUrls {

    private static final String SERVER_HOST_ACCOUNT = "http://122.14.202.146:9200/account";

    private static final String SERVER_HOST_INSURE = "http://122.14.202.146:9200/trading";

    private static final String SERVER_HOST_YUNDA = "http://yunda.com/test";

    private static final String SERVER_HOST_TEST = "https://api-yunda.inschos.com/webapi";

    public static String getServerHostAccount() {
        return SERVER_HOST_ACCOUNT;
    }

    public static String getServerHostInsure() {
        return SERVER_HOST_INSURE;
    }

    public static String getServerHostCallBackYunda() {
        return SERVER_HOST_YUNDA;
    }

    public static String getServerHostTest() {
        return SERVER_HOST_TEST;
    }

    /**
     * 联合登录
     */
    public static final String toJointLogin = IntersCommonUrls.getServerHostAccount() + "/jointLogin";

    /**
     * 投保
     */
    public static final String toInsured = IntersCommonUrls.getServerHostInsure() + "/insure";

    /**
     * 支付
     */
    public static final String toPay = IntersCommonUrls.getServerHostInsure() + "/pay";

    /**
     * 微信签约
     */
    public static final String toWechatContract = IntersCommonUrls.getServerHostInsure() + "/wechatContract";

    /**
     * 微信代扣
     */
    public static final String toWechatPay = IntersCommonUrls.getServerHostInsure() + "/wechatPay";

    /**
     * 出单
     */
    public static final String toIssue = IntersCommonUrls.getServerHostInsure() + "/issue";

    /**
     * 授权查询(微信+银行卡)
     */
    public static final String toAuthorizeQuery = IntersCommonUrls.getServerHostInsure() + "/authorizeQuery";

    /**
     * 投保推送韵达
     */
    public static final String toCallBackYunda = IntersCommonUrls.getServerHostCallBackYunda() + "/call_back_yunda";

    /**
     * 测试http请求接口
     */
    public static final String toHttpTest = IntersCommonUrls.getServerHostTest() + "/joint_login?bank_code=6217001210078544622 &bank_address=11&channel_code=YD&insured_province=320000&insured_county=320982&insured_code=342225199504065369&insured_phone=15856218334&insured_city=320900&insured_name=曹桥桥&insured_address=11&bank_phone=111&bank_name=11&insured_email=111&channel_order_code=1111";
}
