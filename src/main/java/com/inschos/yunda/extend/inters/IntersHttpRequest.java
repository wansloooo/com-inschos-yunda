package com.inschos.yunda.extend.inters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inschos.yunda.assist.kit.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntersHttpRequest<Request extends IntersCommonRequest, Response extends IntersCommonResponse> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private IntersRequests request;
    private Class<Response> cls;
    private String url;

    public IntersHttpRequest(String url, Request request, Class<Response> cls) {
        this.url = url;
        this.request = new IntersRequests<Request>();
        this.cls = cls;
        if (request != null) {
            this.request.data = request;
            this.request.sign = IntersSignatureTools.sign(JsonKit.bean2Json(request), IntersSignatureTools.CAR_RSA_PRIVATE_KEY);
        }
        this.request.sendTime = sdf.format(new Date(System.currentTimeMillis()));
    }

    public Response post() {
        Response response;
        try {
            L.log.debug("=============================================================================================================================");
            L.log.debug(JsonKit.bean2Json(request));
            String result = HttpClientKit.post(url, JsonKit.bean2Json(request));
            L.log.debug("=============================================================================================================================");
            L.log.debug(result);
            response = JsonKit.json2Bean(result, cls);
            if (response != null) {
                response.verify = verifySignature(result);
            } else {
                try {
                    response = cls.newInstance();
                    response.state = IntersCommonResponse.RESULT_FAIL;
                    response.msg = "请求失败";
                    response.verify = false;
                } catch (InstantiationException | IllegalAccessException e) {
                    return null;
                }
            }
            return response;
        } catch (IOException e) {
            try {
                response = cls.newInstance();
                response.state = IntersCommonResponse.RESULT_FAIL;
                response.msg = "请求失败";
                response.verify = false;
                return response;
            } catch (InstantiationException | IllegalAccessException ex) {
                return null;
            }
        }
    }

    private boolean verifySignature(String responseJson) {
        JsonNode jsonNode = null;
        try {
            jsonNode = new ObjectMapper().readTree(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean flag = false;
        if (jsonNode != null) {
            JsonNode signNode = jsonNode.get("sign");
            String sign = signNode.textValue();
            if (StringKit.isEmpty(sign)) {
                flag = true;
            } else {
                String content = jsonNode.get("data").toString();
                flag = IntersSignatureTools.verify(content, sign, IntersSignatureTools.CAR_RSA_PUBLIC_KEY);
            }
        }
        return flag;
    }
}