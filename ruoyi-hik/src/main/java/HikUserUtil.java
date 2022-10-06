import com.hikvision.artemis.sdk.ArtemisHttpUtil;

import java.util.Map;

public class HikUserUtil {

//    public static HEEmpDto create(HikUser user){
//        Map<String, String> path = HiKBaseUtil.getPath("/api/resource/v2/person/single/add");
//        String contentType = "application/json";
//        //log.info("传入信息>>>>>>>>hikUser:{}",user.toString());
//        String body = getJsonBody(user,true);
//        //log.info("新增用户传输的参数为>>>>>>>>>>body:{}",body);
//        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
//        JSONObject json = JSON.parseObject(result);
//        if("0".equals(json.getString("code"))){
//            String personId = json.getJSONObject("data").getString("personId");
//            String faceId = json.getJSONObject("data").getString("faceId");
//            HEEmpDto hEEmpDto = new HEEmpDto();
//            hEEmpDto.setPersonId(personId);
//            hEEmpDto.setFaceId(faceId);
//            return hEEmpDto;
//        }else {
//            log.error("新增用户信息失败>>>>>>>>>>>>>>>>>{}",result);
//            throw new HikException("新增海康人员失败");
//        }
////        HEEmpDto hEEmpDto = new HEEmpDto();
////        hEEmpDto.setPersonId("111");
////        hEEmpDto.setFaceId("faceId");
////        return hEEmpDto;
//    }
}
