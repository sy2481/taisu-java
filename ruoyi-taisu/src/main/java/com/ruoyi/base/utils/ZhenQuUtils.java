package com.ruoyi.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.vo.AccessToken;
import com.ruoyi.base.vo.AccessTokenVO;
import com.ruoyi.base.vo.MacSuccessVo;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 兴跃
 */
@Slf4j
@Component
public class ZhenQuUtils {


    @Value("${zhenqu.host}")
    private String host;

    @Value("${zhenqu.licence}")
    private String licence;
    /**
     * 化工人员
     */
    @Value("${zhenqu.userHost}")
    private String userHost;
    /**
     * 鉴权字段
     */
    @Value("${zhenqu.authorization}")
    private String authorization;

    /**
     * 账户名
     */
    @Value("${zhenqu.appId}")
    private String appId;
    /**
     * 报警订阅
     */
    @Value("${dingyue.host}")
    private String url;

    /**
     * 是否订阅
     */
    @Value("${scheduling.buildIdSendEnabled}")
    private boolean buildIdSendEnabled;


    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 去缓存中取，不存在就请求获取Token,并且保存到缓存中
     *
     * @return
     */

    public AccessTokenVO getAccessToken() {

        AccessTokenVO accessTokenVO = new AccessTokenVO();
        AccessToken token1 = getToken();
        if (token1 != null) {
            accessTokenVO.setErrorCode(0);
            accessTokenVO.setErrorMsg(null);
            accessTokenVO.setAccessToken(token1);
            return accessTokenVO;
        }
        JSONObject json = new JSONObject();
        json.put("licence", licence);
        String token = HttpUtils.sendJsonPost(host + "/getAccessTokenV2", json.toJSONString());
        JSONObject parseObject = JSONObject.parseObject(token);
        Integer errorCode = (Integer) parseObject.get("errorCode");
        Object errorMsg = parseObject.get("errorMsg");
        accessTokenVO.setErrorCode(errorCode);
        accessTokenVO.setErrorMsg(errorMsg);
        if (errorCode == 0) {
            Object data = parseObject.get("data");
            redisCache.setCacheObject("accessToken", JSON.toJSONString(data), 2, TimeUnit.HOURS);
            AccessToken accessTokens = JSONArray.parseObject(JSON.toJSONString(data), AccessToken.class);
            accessTokenVO.setAccessToken(accessTokens);
        }

        return accessTokenVO;
    }


    /**
     * 从缓存中获取Token
     *
     * @return
     */
    public AccessToken getToken() {
        String accessToken = redisCache.getCacheObject("accessToken");
        AccessToken accessTokens = JSONArray.parseObject(accessToken, AccessToken.class);
        return accessTokens;
    }


    /**
     * 获取电量
     */
    public List<MacSuccessVo> getBattery(String accessToken, List<String> macList) {
//    String mac="{ \"data\": { \"success\": [{ \"mac\": \"1918EBCC1231\",\"battery\": 3,\"workStatus\": 1, \"online\": true, \"errorMsg\": null }, { \"mac\": \"1918EBCC1231\",\"battery\": 3,\"workStatus\": 1, \"online\": true, \"errorMsg\": null }, { \"mac\": \"1918EBCC1231\",\"battery\": 3,\"workStatus\": 1, \"online\": true, \"errorMsg\": null } ], \"error\": [ { \"mac\": \"1918B0000001\",\"battery\": null,\"workStatus\": null,\"online\": null, \"errorMsg\": \"blt mac does not exist\" }]}, \"errorCode\": 0, \"errorMsg\": []}";
        JSONObject json = new JSONObject();
        json.put("macList", macList);
        String mac = HttpUtils.sendJsonPost(host + "/api/devicesV3/bltInfoByMac?accessToken=" + accessToken, json.toJSONString());
        JSONObject parseObject = JSONObject.parseObject(mac);
        Integer errorCode = (Integer) parseObject.get("errorCode");
        if (errorCode == 0) {
            Object data = parseObject.get("data");
            String datas = JSON.toJSONString(data);
            JSONObject datasObject = JSONObject.parseObject(datas);
            Object success = datasObject.get("success");
            List<MacSuccessVo> listStr = JSON.parseArray(JSON.toJSONString(success), MacSuccessVo.class);
            return listStr;
        }
        List<MacSuccessVo> list=new ArrayList<>();
        return list;
    }


    /*************************************************************************************************************/
    /**
     * 化工人员批量Token
     */
    public String getUserToken() {
        String subscribeToken = getSubscribeToken();
        if (StringUtils.isNotBlank(subscribeToken)) {
            return subscribeToken;
        }
        JSONObject json = new JSONObject();
        json.put("buildIdList", getBuildId());
        json.put("appId", appId);
        String token = sendJsonPost(userHost + "/api/v2/get-token", json.toJSONString(),"Authorization",authorization);
        log.info("token:"+token);
        System.out.println("token:"+token);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        JSONObject parseObject = JSONObject.parseObject(token);
        Integer code = (Integer) parseObject.get("code");
        if (code == 0) {
            String data = parseObject.get("data").toString();
            redisCache.setCacheObject("subscribeToken", data, 2, TimeUnit.HOURS);
            return data;
        }
        return null;
    }

    /**
     * 从缓存中取token令牌
     */

    public String getSubscribeToken() {
        String subscribeToken = redisCache.getCacheObject("subscribeToken");
        return subscribeToken;
    }

    /**
     * 获取建筑ID
     */
    public List<String> getBuildId() {
        List<String > list=new ArrayList<>();
        List<SysDept> plant = sysDeptService.getPlant();
        plant.forEach(sysDept -> {
            if (StringUtils.isNotBlank(sysDept.getBuildId())){
                list.add(sysDept.getBuildId());
            }
        });
        return list;
    }


    /**
     * 开始订阅
     */
  @PostConstruct
    public void buildIdSend() {
      if(!buildIdSendEnabled){
          return;
      }

        List<String> buildId = getBuildId();
        buildId.forEach(s -> {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(url).append("?buildId=" + s);
            log.info("订阅地址：{}", stringBuffer.toString());
            System.out.println("订阅地址："+ stringBuffer.toString());
            subscribe(stringBuffer.toString(),s);
        });
    }

    /**
     * 订阅
     */
    public void subscribe(String requestServerUrl,String buildId) {
        log.info("订阅开始");
        List<String> list = new ArrayList<>();
        list.add("areaAlarm");
        list.add("alarmEvent");
        list.add("jobAlarmEvent");

        String subscribeToken = getUserToken();
        JSONObject json = new JSONObject();
        json.put("buildId", buildId);
        json.put("requestServerUrl", requestServerUrl);
        for (int i = 0; i < list.size(); i++) {
            json.put("type", list.get(i));
            String response = sendJsonPost(userHost + "/api/v2/subscribe", json.toJSONString(),"token", subscribeToken);
            log.info("响应-{}", response);
            System.out.println("响应:"+response);
        }
        log.info("订阅结束");
    }


    /**
     * 发送请求
     *
     * @param reqUrl
     * @param json
     * @param value
     * @return
     */
    public String sendJsonPost(String reqUrl, String json,String name, String value) {
        log.info("sendPost - {}-{}-{}:{}", reqUrl, json, name,value);
        String result = "";
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.setRequestProperty(name, value);
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(json.getBytes("utf-8"));
            os.flush();
            os.close();
            if (httpURLConnection.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream())
                );
                String data = "";
                StringBuilder builder = new StringBuilder();
                while ((data = reader.readLine()) != null) {
                    builder.append(data);
                }
                result = builder.toString();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("recv - {}", result);
        return result;
    }


}
