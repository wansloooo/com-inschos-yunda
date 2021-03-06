package com.inschos.yunda.access.http.controller.request;

import com.inschos.yunda.access.http.controller.action.IntersAction;
import com.inschos.yunda.access.http.controller.action.*;
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
 * Date: 2018/06/23
 * Time: 17:12
 * 韵达项目-保单管理:获取保单列表,获取保单详情,获取购保结果
 */
@Controller
@RequestMapping("/webapi")
public class InsureWarrantyController {

    private static final Logger logger = Logger.getLogger(InsureWarrantyController.class);
    @Autowired
    private InsureWarrantyAction insureWarrantyAction;

    /**
     * 获取保单列表
     *
     * @return json
     * @params actionBean
     * @access public
     */
    @GetActionBeanAnnotation
    @RequestMapping("/findInsureWarrantyList/**")
    @ResponseBody
    public String findInsureWarrantyList(ActionBean actionBean) {
        return insureWarrantyAction.findInsureWarrantyList(actionBean);
    }

    /**
     * 获取保单详情
     *
     * @return json
     * @params actionBean
     * @access public
     */
    @GetActionBeanAnnotation
    @RequestMapping("/findInsureWarrantyInfo/**")
    @ResponseBody
    public String findInsureWarrantyInfo(ActionBean actionBean) {
        return insureWarrantyAction.findInsureWarrantyInfo(actionBean);
    }

    /**
     * 获取购保结果
     *
     * @return
     * @params actionBean
     */
    @GetActionBeanAnnotation
    @RequestMapping("/findInsureResult/**")
    @ResponseBody
    public String findInsureResult(ActionBean actionBean) {
        return insureWarrantyAction.findInsureResult(actionBean);
    }
}
