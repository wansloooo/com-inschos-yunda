package com.inschos.yunda.access.http.controller.action;

import com.inschos.yunda.access.http.controller.bean.*;
import com.inschos.yunda.assist.kit.*;
import com.inschos.yunda.data.dao.*;
import com.inschos.yunda.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.inschos.yunda.assist.kit.JsonKit;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class IntersAction extends BaseAction {

    private static final Logger logger = Logger.getLogger(IntersAction.class);

    @Autowired
    private JointLoginDao jointLoginDao;

    private String login_url = "";//账号服务接口地址

    private String prepare_url = "";//预投保接口地址

    private String insure_url = "";//投保接口地址

    /**
     * 联合登录
     *
     * @return json
     * @params actionBean
     * @access public
     * <p>
     * 韵达触发联合登录，获取报文信息请求账号服务(登录/注册)，获取account_id,manager_id,user_id,token
     */
    public String jointLogin(HttpServletRequest httpServletRequest) {
        JointLoginBean request = JsonKit.json2Bean(HttpKit.readRequestBody(httpServletRequest), JointLoginBean.class);
        BaseResponse response = new BaseResponse();
        //判空
        if (request == null) {
            return json(BaseResponse.CODE_FAILURE, "请检查报文格式是否正确", response);
        }
        if (request.insured_name.isEmpty() || request.insured_code.isEmpty() || request.insured_phone == 0) {
            return json(BaseResponse.CODE_FAILURE, "姓名,证件号,手机号不能为空", response);
        }
        //TODO 触发联合登录

        long date = new Date().getTime();
        JointLogin jointLogin = new JointLogin();
        jointLogin.login_start = date;
        jointLogin.phone = request.insured_phone;
        int login_id = jointLoginDao.addLoginRecord(jointLogin);
        if (login_id == 0) {
            return json(BaseResponse.CODE_FAILURE, "操作失败", response);
        } else {
            return json(BaseResponse.CODE_SUCCESS, "操作成功", response);
        }
    }

    /**
     * 授权查询
     *
     * @return json
     * @params actionBean
     * @access public
     * 授权查询同样需要触发联合登录
     */
    public String authorizationQuery(HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse();
        return json(BaseResponse.CODE_FAILURE, "操作失败", response);
    }

    /**
     * 预投保
     *
     * @return json
     * @params actionBean
     * @access public
     */
    public String prepareInusre(HttpServletRequest httpServletRequest) {
        BaseResponse response = new BaseResponse();
        return json(BaseResponse.CODE_FAILURE, "操作失败", response);
    }

}
