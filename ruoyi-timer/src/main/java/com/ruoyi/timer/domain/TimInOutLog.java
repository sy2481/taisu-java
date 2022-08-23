package com.ruoyi.timer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  InOutLog
 * @author xxy
 * @date 2022-03-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimInOutLog implements Serializable
{
    /** 安全督导员安全检点时间 / 车辆入出厂覆核人员覆核时间 */
    private String datetime;

    /** 施工区域名称：2-7区 */
    private String areano;

    /** 入出厂卡机之IP */
    private String ip;

    /** 本单编号 or 申请单编号 */

    private String vhno;

    /** 工程编号 */

    private String egno;

    /** 工程名称 */

    private String egnm;

    /** 作业类别 */

    private String oprenvt21;

    /** 身份证号，车牌号 */
    private String idno;

    /** 姓名/所属厂商 */

    private String nm;

    /** 10入场证 */
    private String ipltlic;

    /** 发证厂区代码 */
    private String pz;

    /** 承揽商名称 */
    private String tkvnd;

    /** 预定入场时间 */
    private String begtime;

    /** 预定出场时间 */
    private String endtime;

    /** 15延长工时-入厂施工时间 */
    private String intime;

    /** 延长工时-出厂施工时间 */
    private String outtime;

    /** 安全督导员 / 覆核人员 =Extender */
    private String inspector;

    /** 洽公同意人员 ... only for IEM */
    private String inspector2;

    /** 标注: 0 表示人员派送; C 表示厂商车辆派送; T 表示 */
    private String mk;

    /** 专业别代号，工安(头头)：XT*；其余的是普通作业人员 */
    private String profsid;

    /** 人员出场时间，所有人员出场时，会注记时间；入场，会清空 */
    private String opltdattm;

    /** 工安出厂注记时间 */
    private String doutdattm;

    /** 厂门代号 */
    private String fctdornm;

    /** 静脉ID */
    private String fvid;

    private Integer aid;
}
