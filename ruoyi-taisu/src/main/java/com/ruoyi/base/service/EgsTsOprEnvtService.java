package com.ruoyi.base.service;

import com.ruoyi.base.domain.EgsTsOprEnvt;

import java.util.List;

public interface EgsTsOprEnvtService {
    /**
     * 查询作業類別
     *
     * @param oprId 作業類別主键
     * @return 作業類別
     */
    public EgsTsOprEnvt selectEgsTsOprEnvtByOprId(Long oprId);

    /**
     * 查询作業類別列表
     *
     * @param egsTsOprEnvt 作業類別
     * @return 作業類別集合
     */
    public List<EgsTsOprEnvt> selectEgsTsOprEnvtList(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 新增作業類別
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    public int insertEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 修改作業類別
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    public int updateEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 批量删除作業類別
     *
     * @param oprIds 需要删除的作業類別主键集合
     * @return 结果
     */
    public int deleteEgsTsOprEnvtByOprIds(Long[] oprIds);

    /**
     * 删除作業類別信息
     *
     * @param oprId 作業類別主键
     * @return 结果
     */
    public int deleteEgsTsOprEnvtByOprId(Long oprId);

    /**
     * 是否存在同編號的作業類型
     *
     * @param egsTsOprEnvt
     * @return
     */
    public EgsTsOprEnvt existEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 保存证照
     *
     * @param egsTsOprEnvt 作業類別
     * @return 结果
     */
    public int saveEntrys(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 根據作業類型編號獲取名稱
     *
     * @param oprCds
     * @return
     */
    public String getOprNamesByOprCds(String oprCds);
}
