package com.inschos.yunda.access.http.controller.request;

import com.inschos.yunda.access.http.controller.action.InsureSetupAction;
import com.inschos.yunda.access.http.controller.action.IntersAction;
import com.inschos.yunda.access.http.controller.bean.ActionBean;
import com.inschos.yunda.annotation.GetActionBeanAnnotation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * User: wangsl
 * Date: 2018/06/22
 * Time: 17:12
 * 韵达项目-投保设置
 */
@Controller
@RequestMapping("/webapi")
//TODO 路由统一也用小驼峰命名规则
public class InsureSetupController {

    private static final Logger logger = Logger.getLogger(InsureSetupController.class);
    @Autowired
    private InsureSetupAction insureSetupAction;

    /**
     * 获取自动投保状态
     *
     * @return json
     * @params actionBean
     * @access public
     */
    @GetActionBeanAnnotation
    @RequestMapping("/findInsureAutoStatus/**")
    @ResponseBody
    public String findInsureAutoStatus(ActionBean actionBean) {
        return insureSetupAction.findInsureAutoStatus(actionBean);
    }

    /**
     * 更改自动投保
     *
     * @return json
     * @params actionBean
     * @access public
     */
    @GetActionBeanAnnotation
    @RequestMapping("/updateInsureAutoStatus/**")
    @ResponseBody
    public String updateInsureAutoStatus(ActionBean actionBean) {
        return insureSetupAction.updateInsureAutoStatus(actionBean);
    }


}
