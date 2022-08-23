package com.ruoyi.base.utils;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;

/**
 * 根据身份证获取性别和生日
 *
 * @author 兴跃
 */
public class IDcard {
    /**
     * 18位身份证号
     */
    private static final Integer EIGHTEEN_ID_CARD = 18;

    /**
     * 判断身份证号长度
     * 长度只能是 8位、10位、18位
     * 18位需要验证号码是否正确；
     */
    public static void checkIdCard(String idCard) throws RuntimeException {
        if (StringUtils.isBlank(idCard)) {
            throw new RuntimeException("身份证/台胞证号为空");
        }
        if (idCard.length() != 8 && idCard.length() != 10 && idCard.length() != 18) {
            throw new RuntimeException("身份证/台胞证号输入有误");
        }
        if (idCard.length() == 18) {
            //18位，需要判断格式
            if (!IdcardUtils.validateCard(idCard)) {
                throw new RuntimeException("身份证号输入有误");
            }
        }
    }

    /**
     * 传入身份证号和用户
     */
    public static void competeUserByIdcard(SysUser user) {
        if (StringUtils.isNotBlank(user.getIdCard()) && user.getIdCard().length() == 18) {
            user.setSex("男".equals(IDcard.getSex(user.getIdCard())) ? "0" : "1");
            user.setBirthday(IDcard.getBirthday(user.getIdCard()));
        }
    }


    /**
     * 性别
     */
    public static String getSex(String IDCard) {
        String sex = "";
        if (IDCard.length() == EIGHTEEN_ID_CARD) {
            // 判断性别
            if (Integer.parseInt(IDCard.substring(16).substring(0, 1)) % 2 == 0) {
                sex = "女";
            } else {
                sex = "男";
            }
        }
        return sex;
    }

    /**
     * 生日
     */
    public static String getBirthday(String IDCard) {
        String birthday = "";
        String year = "";
        String month = "";
        String day = "";
        if (IDCard.length() == EIGHTEEN_ID_CARD) {
            // 身份证上的年份
            year = IDCard.substring(6).substring(0, 4);
            // 身份证上的月份
            month = IDCard.substring(10).substring(0, 2);
            //身份证上的日期
            day = IDCard.substring(12).substring(0, 2);
        }
        birthday = year + "-" + month + "-" + day;
        return birthday;
    }

    public static void main(String[] args) {
        String sex = getSex("411524199811026585");
        System.out.println(sex);
        String birthday = getBirthday("411524199812016537");
        System.out.println(birthday);
    }
}
