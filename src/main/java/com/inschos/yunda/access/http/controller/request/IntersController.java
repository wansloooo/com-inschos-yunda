package com.inschos.yunda.access.http.controller.request;

import com.inschos.yunda.access.http.controller.action.IntersAction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * User: wangsl
 * Date: 2018/06/23
 * Time: 17:12
 * 韵达项目对外接口:联合登录接口,授权查询接口,预投保接口,保险结果推送韵达
 */
@Controller
@RequestMapping("/webapi")
public class IntersController {

    private static final Logger logger = Logger.getLogger(IntersController.class);
    @Autowired
    private IntersAction intersAction;

    /**
     * 联合登录
     *
     * @return json
     * @params actionBean
     * @params channel_code|渠道代号
     * @params insured_name|姓名
     * @params insured_code|证件号
     * @params insured_phone|电话
     * @params insured_email|y邮箱
     * @params insured_province|省
     * @params insured_city|市
     * @params insured_county|县
     * @params insured_address|详细地址
     * @params bank_name|银行名称
     * @params bank_code|银行卡号
     * @params bank_phone|预留手机号
     * @params bank_address|开户行地址
     * @access public
     */
    @RequestMapping("/joint_login/**")
    @ResponseBody
    public String JointLogin(HttpServletRequest request) {
        return intersAction.jointLogin(request);
    }

    /**
     * 授权查询
     *
     * @return json
     * @params channel_code|渠道代号
     * @params insured_name|姓名
     * @params insured_code|证件号
     * @params insured_phone|电话
     */
    @RequestMapping("/authorization_query/**")
    @ResponseBody
    public String AuthorizationQuery(HttpServletRequest request) {
        return intersAction.authorizationQuery(request);
    }

    /**
     * 预投保
     * @params account_id
     * @params biz_content
     * @params sign
     * @params timestamp
     * @return
     */
    @RequestMapping("/get_prepare/**")
    @ResponseBody
    public String PrepareInusre(HttpServletRequest request){
        return intersAction.prepareInusre(request);
    }

    /**
     * 保险推送韵达
     * @params ordersId
     * @params payTime
     * @params effectiveTime
     * @params type
     * @params status
     * @params ordersName
     * @params companyName
     * @return
     */
    @RequestMapping("/call_back_yunda/**")
    @ResponseBody
    public String CallBackYunda(HttpServletRequest request){
        return intersAction.CallBackYunda(request);
    }



}
