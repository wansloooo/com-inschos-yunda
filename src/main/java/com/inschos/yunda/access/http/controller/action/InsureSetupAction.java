package com.inschos.yunda.access.http.controller.action;

import com.inschos.yunda.access.http.controller.bean.*;
import com.inschos.yunda.assist.kit.JsonKit;
import com.inschos.yunda.assist.kit.TimeKit;
import com.inschos.yunda.data.dao.InsureSetupDao;
import com.inschos.yunda.data.dao.WarrantyRecordDao;
import com.inschos.yunda.model.InsureSetup;
import com.inschos.yunda.model.WarrantyRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.inschos.yunda.access.http.controller.bean.IntersCommonUrlBean.toAuthorizeQuery;

@Component
public class InsureSetupAction extends BaseAction {

    private static final Logger logger = Logger.getLogger(InsureSetupAction.class);

    @Autowired
    private InsureSetupDao insureSetupDao;

    @Autowired
    private WarrantyRecordDao warrantyRecordDao;

    @Autowired
    private CommonAction commonAction;

    @Autowired
    private InsureUserAction insureUserAction;

    @Autowired
    private InsureBankAction insureBankAction;

    /**
     * 获取投保设置详情,第一次查询时，先插入投保设置记录
     *
     * @param actionBean
     * @return
     */
    public String findInsureAutoStatus(ActionBean actionBean) {
        BaseResponseBean response = new BaseResponseBean();
        InsureSetup insureSetup = new InsureSetup();
        insureSetup.cust_id = Long.valueOf(actionBean.userId);
        InsureSetup insureAutoRes = insureSetupDao.findInsureAutoInfo(insureSetup);
        InsureSetupBean.findInsureAutoResponseData insureAutoResponseData = new InsureSetupBean.findInsureAutoResponseData();
        if (insureAutoRes == null) {
            //添加投保设置记录
            long date = new Date().getTime();
            insureSetup.authorize_bank = "";
            insureSetup.authorize_status = 1;//银行卡授权设置,未授权1/已授权2
            insureSetup.auto_insure_status = 1;//自动投保设置,未开启1/已开启2
            insureSetup.auto_insure_price = "2";//每日购保金额,2/5/13
            insureSetup.auto_insure_type = 1;//购保天数,1/3/10
            insureSetup.auto_insure_end = 0;//关闭自动投保时间
            insureSetup.created_at = date;
            insureSetup.updated_at = date;
            long addRes = insureSetupDao.addInsureSetup(insureSetup);
            if (addRes == 0) {
                return json(BaseResponseBean.CODE_SUCCESS, "获取投保设置失败", response);
            } else {
                insureAutoResponseData.custId = insureSetup.cust_id;
                insureAutoResponseData.autoStatus = insureSetup.auto_insure_status;
                insureAutoResponseData.price = insureSetup.auto_insure_price;
                insureAutoResponseData.type = insureSetup.auto_insure_type;
                insureAutoResponseData.closeTime = insureSetup.auto_insure_end;
            }
        } else {
            insureAutoResponseData.custId = insureAutoRes.cust_id;
            insureAutoResponseData.autoStatus = insureAutoRes.auto_insure_status;
            insureAutoResponseData.price = insureAutoRes.auto_insure_price;
            insureAutoResponseData.type = insureAutoRes.auto_insure_type;
            insureAutoResponseData.closeTime = insureAutoRes.auto_insure_end;
        }
        response.data = insureAutoResponseData;
        return json(BaseResponseBean.CODE_SUCCESS, "获取投保设置成功", response);
    }

    /**
     * 更改投保设置
     *
     * @param actionBean
     * @return
     */
    public String updateInsureAutoStatus(ActionBean actionBean) {
        InsureSetupBean.updateInsureAutoRequset request = JsonKit.json2Bean(actionBean.body, InsureSetupBean.updateInsureAutoRequset.class);
        BaseResponseBean response = new BaseResponseBean();
        if (request == null) {
            return json(BaseResponseBean.CODE_FAILURE, "参数解析失败", response);
        }
        InsureSetup insureSetup = new InsureSetup();
        long date = new Date().getTime();
        insureSetup.cust_id = Long.valueOf(actionBean.userId);
        insureSetup.auto_insure_status = request.insureAutoStatus;
        insureSetup.auto_insure_price = request.insurePrice;
        insureSetup.auto_insure_type = request.insureType;
        insureSetup.auto_insure_end = date;
        insureSetup.updated_at = date;
        long updateRes = insureSetupDao.updateInsureAuto(insureSetup);
        if (updateRes == 0) {
            return json(BaseResponseBean.CODE_FAILURE, "更新投保设置失败", response);
        } else {
            return json(BaseResponseBean.CODE_SUCCESS, "更新投保设置成功", response);
        }
    }

    /**
     * 获取授权/签约状态
     *
     * @param actionBean
     * @return
     */
    public String findAuthorizeStatus(ActionBean actionBean) {
        BaseResponseBean response = new BaseResponseBean();
        CommonBean.findAuthorizeRequset authorizeRequset = new CommonBean.findAuthorizeRequset();
        authorizeRequset.token = actionBean.token;
        String interName = "获取授权/签约状态";
        String result = commonAction.httpRequest(toAuthorizeQuery, "", interName, authorizeRequset.token);
        CommonBean.findAuthorizeResponse authorizeResponse = JsonKit.json2Bean(result,CommonBean.findAuthorizeResponse.class);
        if(authorizeResponse.code==200&&authorizeResponse.data!=null&&authorizeResponse.data.bank!=null&&authorizeResponse.data.weixin!=null){
            response.data = result;
            return json(BaseResponseBean.CODE_SUCCESS, interName+"成功", response);
        }else{
            return json(BaseResponseBean.CODE_FAILURE, interName+"失败", response);
        }
    }

    /**
     * 获取签约信息(url)
     *
     * @param actionBean
     * @return
     */
    public String findWhetContractUrl(ActionBean actionBean) {
        InsureSetupBean.doWechatContractRequset request = JsonKit.json2Bean(actionBean.body, InsureSetupBean.doWechatContractRequset.class);
        BaseResponseBean response = new BaseResponseBean();
        if (request == null) {
            return json(BaseResponseBean.CODE_FAILURE, "参数解析失败", response);
        }
        String token = actionBean.token;
        WarrantyRecord warrantyRecord = new WarrantyRecord();
        warrantyRecord.cust_id = Long.valueOf(actionBean.userId);
        //前一天的保单信息
        warrantyRecord.day_start = TimeKit.getDayStartTime() - 24 * 60 * 60 * 1000;
        warrantyRecord.day_end = TimeKit.getDayEndTime() - 24 * 60 * 60 * 1000;
        WarrantyRecord warrantyRecoedRes = warrantyRecordDao.findLastDayWarrantyRecord(warrantyRecord);
        if (warrantyRecoedRes == null || warrantyRecoedRes.warranty_uuid == null) {
            return json(BaseResponseBean.CODE_FAILURE, "您没有进行预投保，不能进行微信签约", response);
        }
        CommonBean.doWecahtContractRequset doWecahtContractRequset = new CommonBean.doWecahtContractRequset();
        //签约参数待定
        doWecahtContractRequset.warrantyUuid = warrantyRecoedRes.warranty_uuid;
        doWecahtContractRequset.payNo = warrantyRecoedRes.pay_no;
        doWecahtContractRequset.clientIp = request.clientIp;
        InsureUserBean.userInfoRequest userInfoRequest = new InsureUserBean.userInfoRequest();
        userInfoRequest.token = actionBean.token;
        InsureUserBean.userInfoResponse userInfoResponse = insureUserAction.findUserInfoById(userInfoRequest);
        doWecahtContractRequset.wechatAccount = userInfoResponse.data.phone;
        doWecahtContractRequset.insuredName = userInfoResponse.data.name;
        doWecahtContractRequset.insuredCode = userInfoResponse.data.papersCode;
        doWecahtContractRequset.insuredPhone = userInfoResponse.data.phone;
        String interName = "获取签约链接";
        String result = commonAction.httpRequest(toAuthorizeQuery, JsonKit.bean2Json(doWecahtContractRequset), interName, token);
        CommonBean.findAuthorizeResponse authorizeResponse = JsonKit.json2Bean(result,CommonBean.findAuthorizeResponse.class);
        if(authorizeResponse.code==200&&authorizeResponse.data!=null&&authorizeResponse.data.bank!=null&&authorizeResponse.data.weixin!=null){
            response.data = result;
            return json(BaseResponseBean.CODE_SUCCESS, interName+"成功", response);
        }else{
            return json(BaseResponseBean.CODE_FAILURE, interName+"失败", response);
        }
    }
}
