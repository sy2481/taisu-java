package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.EgsTsOprEnvt;

import java.util.List;

public interface EgsTsOprEnvtMapper {
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
     * 删除作業類別
     *
     * @param oprId 作業類別主键
     * @return 结果
     */
    public int deleteEgsTsOprEnvtByOprId(Long oprId);

    /**
     * 批量删除作業類別
     *
     * @param oprIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEgsTsOprEnvtByOprIds(Long[] oprIds);

    /**
     * 是否存在同編號的作業類型
     *
     * @param egsTsOprEnvt
     * @return
     */
    public List<EgsTsOprEnvt> existEgsTsOprEnvt(EgsTsOprEnvt egsTsOprEnvt);

    /**
     * 查询作業類別列表
     *
     * @param oprCds 作業類別
     * @return 作業類別集合
     */
    public List<EgsTsOprEnvt> selectEgsTsOprEnvtListByOprCds(String[] oprCds);
}
