package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.EgsTsOprEnvt;
import com.ruoyi.base.mapper.EgsTsOprEnvtMapper;
import com.ruoyi.base.service.EgsTsOprEnvtService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class EgsTsOprEnvtServiceImpl implements EgsTsOprEnvtService {
    @Autowired
    private EgsTsOprEnvtMapper egsTsOprEnvtMapper;

    /**
     * 查询作業類別
     *
     * @param oprId 作業類別主键
     * @return 作業類別
     */
    @Override
    public EgsTsOprEnvt selectEgsTsOprEnvtByOprId(Long oprId) {
        return egsTsOprEnvtMapper.selectEgsTsOprEnvtByOprId(oprId);
    }

    /**
     * 查询作業類別列表
     *
     * @param egsTsOprEnvt 作業類別
     * @return 作業類別
     */
    @Override
    public List<EgsTsOprEnvt> selectEgsTsOprEnvtList(EgsTsOprEnvt egsTsOprEnvt) {
        return egsTsOprEnvtMapper.selectEgsTsOprEnvtList(egsTsOprEnvt);
    }

    /**
     * 新增作業類別
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    @Override
    public int insertEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt) {
        if (egsTsOprEnvt.getOprCd() != null && egsTsOprEnvt.getOprCd().length() > 3)
            throw new RuntimeException("作業類別代碼長度不得超過3個字符(中文字符占2個字符)");
        canAddEdit(egsTsOprEnvt);

        egsTsOprEnvt.setCreateBy(SecurityUtils.getUsername());
        egsTsOprEnvt.setCreateTime(DateUtils.getNowDate());
        return egsTsOprEnvtMapper.insertEgsTsOprEnvt(egsTsOprEnvt);
    }

    /**
     * 修改作業類別
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    @Override
    public int updateEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt) {
        if (egsTsOprEnvt.getOprCd() != null && egsTsOprEnvt.getOprCd().length() > 3)
            throw new RuntimeException("作業類別代碼長度不得超過3個字符(中文字符占2個字符)");
        canAddEdit(egsTsOprEnvt);

        egsTsOprEnvt.setUpdateBy(SecurityUtils.getUsername());
        egsTsOprEnvt.setUpdateTime(DateUtils.getNowDate());
        return egsTsOprEnvtMapper.updateEgsTsOprEnvt(egsTsOprEnvt);
    }

    /**
     * 批量删除作業類別
     *
     * @param oprIds 需要删除的作業類別主键
     * @return 结果
     */
    @Override
    public int deleteEgsTsOprEnvtByOprIds(Long[] oprIds) {
        canDelete(oprIds);
        return egsTsOprEnvtMapper.deleteEgsTsOprEnvtByOprIds(oprIds);
    }

    private void canAddEdit(EgsTsOprEnvt egsTsOprEnvt) {
        EgsTsOprEnvt oldVo = this.existEgsTsOprEnvt(egsTsOprEnvt);
        if (oldVo != null) {
            throw new ServiceException("保存作業類型【" + egsTsOprEnvt.getOprCd() + "-" + egsTsOprEnvt.getOprNm() + "】失败，已存在同編號的作業類型。");
        }
    }

    private void canDelete(Long[] ids) {
        /*if (egsOprCertService.existEgsOprCertByOprIds(ids) > 0) {
            throw new ServiceException("删除作業類別失败，該作業類別已綁定證件，清先解綁證照再進行刪除。");
        }*/
    }

    /**
     * 删除作業類別信息
     *
     * @param oprId 作業類別主键
     * @return 结果
     */
    @Override
    public int deleteEgsTsOprEnvtByOprId(Long oprId) {
        return egsTsOprEnvtMapper.deleteEgsTsOprEnvtByOprId(oprId);
    }

    /**
     * 是否存在同編號的作業類型
     *
     * @param egsTsOprEnvt
     * @return
     */
    @Override
    public EgsTsOprEnvt existEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt) {
        List<EgsTsOprEnvt> list = egsTsOprEnvtMapper.existEgsTsOprEnvt(egsTsOprEnvt);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 保存证照
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    public int saveEntrys(EgsTsOprEnvt egsTsOprEnvt) {
        /*Long oprId = egsTsOprEnvt.getOprId();
        List<String> certIds = egsTsOprEnvt.getCertIds();
        //获取证照实体
        List<EgsOprCert> list = new ArrayList<EgsOprCert>();
        for (String certId : certIds) {
            EgsOprCert entity = new EgsOprCert();
            entity.setOprId(oprId);
            entity.setCertId(Long.valueOf(certId));
            list.add(entity);
        }
        return egsOprCertService.saveBatch(list, oprId);*/
        return -1;
    }

    /**
     * 根據作業類型編號獲取名稱
     *
     * @param oprCds F;A;H;C
     * @return
     */
    @Override
    public String getOprNamesByOprCds(String oprCds) {
        if (StringUtils.isEmpty(oprCds)) {
            return "";
        }
        List<EgsTsOprEnvt> list = egsTsOprEnvtMapper.selectEgsTsOprEnvtList(null);
        String[] oprCdArr = oprCds.split(";");
        String oprNames = "";
        for (String item : oprCdArr) {
            List<EgsTsOprEnvt> myList = list.stream().filter(x -> StringUtils.nvl(x.getOprCd(), "").equals(item)).collect(Collectors.toList());
            if (myList.size() > 0) {
                oprNames += ";" + myList.get(0).getOprNm();
            }
        }
        oprNames = oprNames.substring(1);
        return oprNames;
    }
}