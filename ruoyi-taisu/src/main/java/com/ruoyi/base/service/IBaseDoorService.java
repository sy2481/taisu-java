package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.BaseDoor;

/**
 * 門Service接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface IBaseDoorService 
{
    /**
     * 查詢門
     * 
     * @param id 門主鍵
     * @return 門
     */
    public BaseDoor selectBaseDoorById(Long id);

    /**
     * 查詢門列表
     * 
     * @param baseDoor 門
     * @return 門集合
     */
    public List<BaseDoor> selectBaseDoorList(BaseDoor baseDoor);

    /**
     * 新增門
     * 
     * @param baseDoor 門
     * @return 結果
     */
    public int insertBaseDoor(BaseDoor baseDoor);

    /**
     * 修改門
     * 
     * @param baseDoor 門
     * @return 結果
     */
    public int updateBaseDoor(BaseDoor baseDoor);

    /**
     * 批量刪除門
     * 
     * @param ids 需要刪除門的主鍵集合
     * @return 結果
     */
    public int deleteBaseDoorByIds(Long[] ids);

    /**
     * 刪除門信息
     * 
     * @param id 門主鍵
     * @return 結果
     */
    public int deleteBaseDoorById(Long id);

    /**
     * 查詢類型
     *
     * @return 結果
     */
    public List<String> selectTypeEnum();
}
