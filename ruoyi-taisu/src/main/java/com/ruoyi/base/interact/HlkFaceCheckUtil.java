package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.base.utils.HaiKangCodeMap;
import com.ruoyi.base.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author shiva   2022-04-02 19:10
 */
@Component
public class HlkFaceCheckUtil {

    @Value("${server.port}")
    private String port;

    public String hikFaceJudge(String fileUrl) {
        String result = null;
        try {
            // 通过future 拿返回值
            FutureTask<String> futureTask = new FutureTask<>(() -> faceIsQualified(fileUrl));
            //执行线程
            new Thread(futureTask).start();
            //等待返回现成阻塞，最长等待3秒
            result = futureTask.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //最后返回
        return result;
    }

    /**
     * 判断人脸是否合格
     */
    public String faceIsQualified(String fileUrl) {

        String msg = "";
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        //平台的ip端口
        ArtemisConfig.host = "183.249.124.14:4443";
        //密钥appkey
        ArtemisConfig.appKey = "21793701";
        //密钥appSecret
        ArtemisConfig.appSecret = "LebqtW7hzZo7DufplYMP";
        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + "/api/frs/v1/face/picture/check";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };
        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        String faceBase64 = HttpUtils.requestUrlToBase64("http://127.0.0.1:" + port + fileUrl);
        jsonBody.put("facePicBinaryData", faceBase64);
        String body = jsonBody.toJSONString();

        /**
         * STEP6：调用接口
         */
        // post请求application/json类型参数
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);
        JSONObject parseObject = JSONObject.parseObject(result);
        //数据结果
        JSONObject data = (JSONObject) parseObject.get("data");
        //成功状态，直接返回
        if ("0".equals(parseObject.get("code")) && data != null && (Boolean) data.get("checkResult")) {
            return null;
        }
        if (data != null) {
            //提示错误信息
            msg = HaiKangCodeMap.HaiKangCode.get(data.get("statusCode").toString());
        } else {
            //msg = (String) parseObject.get("msg");
            msg = "照片不符合，请重新拍照";
        }
        return msg;
    }

}
