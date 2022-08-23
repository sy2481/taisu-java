package com.ruoyi.common.shardbatis.converter;

import com.ruoyi.common.shardbatis.builder.ShardConfigHolder;
import com.ruoyi.common.shardbatis.strategy.ShardStrategy;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlConverterFactory {
    private static final Log log = LogFactory.getLog(SqlConverterFactory.class);
    private static SqlConverterFactory factory = new SqlConverterFactory();

    private final String REGEX_PREFIX = "\\s+[\\w\\[\\]]*\\.?[\\w\\[\\]]*\\.?";
    private final String REGEX_SUFIX = "\\]?(\\s+|$)";
    private final String SPACE = " ";

    public static SqlConverterFactory getInstance() {
        return factory;
    }


    public String convert(String sql, Object params, String mapperId) {

        ShardConfigHolder configFactory = ShardConfigHolder.getInstance();
        Map<String, ShardStrategy> strategyRegister = configFactory.getStrategyRegister();
        String factoryCode = configFactory.getFactoryCode();//厂编号
        Iterator<String> iterators = strategyRegister.keySet().iterator();
        while (iterators.hasNext()) {

            String tableName = iterators.next().toUpperCase();

            //获取分表策略并转换sql

            ShardStrategy strategy = configFactory.getStrategy(tableName);

            String shardTable = strategy.getTargetTableName(tableName, params, mapperId, factoryCode);

            Pattern pattern= Pattern.compile(jointRegex(tableName),Pattern.CASE_INSENSITIVE);
            Matcher matcher=pattern.matcher(sql);
            sql= matcher.replaceAll(jointShard(shardTable));

            //sql = sql.toUpperCase().replaceAll(jointRegex(tableName), jointShard(shardTable));

        }

        return sql;

    }

    /**
     * 生成匹配正则
     *
     * @param tabelName 表名
     * @return
     */

    private String jointRegex(String tabelName) {

        String tableNameNew = new StringBuffer(REGEX_PREFIX).append(tabelName).append(REGEX_SUFIX).toString();
        return tableNameNew;
    }

    /**
     * 拼接分表名
     *
     * @param shardTable 分表名
     * @return
     */
    private String jointShard(String shardTable) {
        return new StringBuffer(SPACE).append(shardTable).append(SPACE).toString();
    }


}
