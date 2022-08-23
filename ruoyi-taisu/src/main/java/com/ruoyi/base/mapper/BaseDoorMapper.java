package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.BaseDoor;

/**
 * 门Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface BaseDoorMapper 
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
     * 删除門
     * 
     * @param id 門主鍵
     * @return 結果
     */
    public int deleteBaseDoorById(Long id);

    /**
     * 批量刪除門
     * 
     * @param ids 需要刪除的主鍵集合
     * @return 結果
     */
    public int deleteBaseDoorByIds(Long[] ids);
}
