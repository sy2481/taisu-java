import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.ruoyi.hik.dto.HEEmpDto;
import com.ruoyi.hik.dto.HikUser;
import com.ruoyi.hik.exception.HikException;
import com.ruoyi.hik.utils.HiKBaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class HikUserUtil {

    private static final Logger log = LoggerFactory.getLogger(HikUserUtil.class);

    public static HEEmpDto create(HikUser user){
        Map<String, String> path = HiKBaseUtil.getPath("/api/resource/v2/person/single/add");
        String contentType = "application/json";
        //log.info("传入信息>>>>>>>>hikUser:{}",user.toString());
        String body = getJsonBody(user,true);
        //log.info("新增用户传输的参数为>>>>>>>>>>body:{}",body);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        JSONObject json = JSON.parseObject(result);
        if("0".equals(json.getString("code"))){
            String personId = json.getJSONObject("data").getString("personId");
            String faceId = json.getJSONObject("data").getString("faceId");
                HEEmpDto hEEmpDto = new HEEmpDto();
            hEEmpDto.setPersonId(personId);
            hEEmpDto.setFaceId(faceId);
            return hEEmpDto;
        }else {
            log.error("新增用户信息失败>>>>>>>>>>>>>>>>>{}",result);
            throw new HikException("新增海康人员失败");
        }
//        HEEmpDto hEEmpDto = new HEEmpDto();
//        hEEmpDto.setPersonId("111");
//        hEEmpDto.setFaceId("faceId");
//        return hEEmpDto;
    }

    /**
     * 把User转换为json字符串
     * @param user
     * @param isCreate 是否是创建
     * @return
     */
    private static String getJsonBody(HikUser user, boolean isCreate){
        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();

        if(!isCreate){
            if(StringUtils.isEmpty(user.getPersonId())){
                throw new HikException("personId不能为空");
            }
        }

        if(!StringUtils.isEmpty(user.getPersonId())){
            jsonBody.put("personId", user.getPersonId());
        }
        if (StringUtils.isEmpty(user.getPersonName())) {
            throw new HikException("员工名称不能为空");
        } else {
            jsonBody.put("personName", user.getPersonName());
        }

        if(!StringUtils.isEmpty(user.getGender())){
            jsonBody.put("gender", user.getGender());
        }else{
            throw new HikException("性别不能为空");
        }

        if(StringUtils.isEmpty(user.getOrgIndexCode())){
            throw new HikException("orgIndexCode不能为空");
        }else{
            jsonBody.put("orgIndexCode", user.getOrgIndexCode());
        }

        if(!StringUtils.isEmpty(user.getBirthday())){
            jsonBody.put("birthday", user.getBirthday());
        }

        if(!StringUtils.isEmpty(user.getPhoneNo())){
            jsonBody.put("phoneNo", user.getPhoneNo());
        }

        if(!StringUtils.isEmpty(user.getEmail())){
            jsonBody.put("email", user.getEmail());
        }

        if(!StringUtils.isEmpty(user.getCertificateType())){
            jsonBody.put("certificateType", user.getCertificateType());
        }


        if(!StringUtils.isEmpty(user.getCertificateNo())){
            jsonBody.put("certificateNo", user.getCertificateNo());
        }

        if(!StringUtils.isEmpty(user.getJobNo())){
            jsonBody.put("jobNo", user.getJobNo());
        }


        if(isCreate) { //如果是创建才上传人脸。
            if(user.getFaces() != null && !user.getFaces().isEmpty()) {
                List<Map<String, String>> faces = user.getFaces().stream().map(face -> {
                    Map<String, String> faceData = new HashMap<>(2);
                    faceData.put("faceData", face);
                    return faceData;
                }).collect(Collectors.toList());

                jsonBody.put("faces", faces);
            }

        }
        return  jsonBody.toJSONString();
    }

    /**
     * 修改人脸
     * @param faceId 人员的id
     * @param faceData 人脸base64字符串
     * @return
     */
    public static  JSONObject updateFace(String faceId, String faceData){
        Map<String, String> path = HiKBaseUtil.getPath("/api/resource/v1/face/single/update");
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("faceId",faceId);
        jsonBody.put("faceData",faceData);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, jsonBody.toJSONString(), null, null, contentType , null);// post请求application/json类型参数
        log.info("根据id查询,获取到的信息为>>>>>>>>>>>>>>>>>{}",result);
        JSONObject json = JSON.parseObject(result);
        if("0".equals(json.getString("code"))){
            return json.getJSONObject("data");
        }
        throw new HikException("修改人脸失败");
    }

    /**
     * 批量新增用户
     * @param users
     * @return 成功返回true 失败返回false
     */
    public static boolean createList(List<HikUser> users){
        Map<String, String> path = HiKBaseUtil.getPath("/api/resource/v1/person/batch/add");
        String contentType = "application/json";
        List<String> msgs  = users.stream().map(user -> getJsonBody(user, false)).collect(Collectors.toList());
        String body = CollectionUtil.join(msgs, ",");
        body = "["+body+"]";
        log.info("批量新增人员，发送的信息为>>>>>>>>>>>>>>>>>>个数{}",users);
        String result = ArtemisHttpUtil.doPostStringArtemis(path,body , null, null, contentType , null);// post请求application/json类型参数
        log.info("批量新增人员，收到的信息为>>>>>>>>>>{}",result);
        JSONObject json = JSON.parseObject(result);
        if(json.getIntValue("code") == 0){
            return true;
        }
        return false;
    }

    /**
     * 新增人脸
     * @param personId 人员的id
     * @param faceData 人脸base64字符串
     * @return
     */
    public static  JSONObject createFace(String personId,String faceData){
        Map<String, String> path = HiKBaseUtil.getPath("/api/resource/v1/face/single/add");
        String contentType = "application/json";
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("personId",personId);
        jsonBody.put("faceData",faceData);
        String result = ArtemisHttpUtil.doPostStringArtemis(path, jsonBody.toJSONString(), null, null, contentType , null);// post请求application/json类型参数
        log.info("创建人脸,获取到的信息为>>>>>>>>>>>>>>>>>{}",result);
        JSONObject json = JSON.parseObject(result);
        if("0".equals(json.getString("code"))){
            return json.getJSONObject("data");
        }
        return null;
    }

}
