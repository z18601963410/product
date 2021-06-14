package com.ike.product.config.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * 动态数据源帮助类:动态数据源key的配置
 */
public class DynamicDataSourceHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    //线程变量:当前数据源类型。拦截器对数据源类型进行设置,动态数据源获取key
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    //主库key设置
    public static final String DB_MASTER = "master";
    //从库数量
    public static final int DB_SLAVE_COUNT = 3;
    //从库key设置
    public static final String DB_SLAVE_1 = "slave1";
    public static final String DB_SLAVE_2 = "slave2";
    public static final String DB_SLAVE_3 = "slave3";
    //随机数对象(随机分配从库处理请求)
    private static final Random random = new Random(10);

    /**
     * 获取数据源类型
     *
     * @return 返回数据源类型
     */
    public static String getDbType() {

        String db = contextHolder.get();
        if (db == null) {
            db = DB_MASTER;
        }

        return db;
    }

    /**
     * 设置线程的dbType
     *
     * @param str 数据源类型
     */
    static void setDbType(String str) {
        logger.debug("所使用的数据源为：" + str);
        contextHolder.set(str);
    }

    /**
     * 通过随机数返回从库key
     *
     * @return 从库key
     */
    static String randomSetLookUpKey() {
        String lookupKey;
        //通过随机数选择从库进行读操作
        int i = random.nextInt(DynamicDataSourceHolder.DB_SLAVE_COUNT) + 1;
        switch (i) {
            case 2:
                lookupKey = DynamicDataSourceHolder.DB_SLAVE_2;
                break;
            case 3:
                lookupKey = DynamicDataSourceHolder.DB_SLAVE_3;
                break;
            default:
                lookupKey = DynamicDataSourceHolder.DB_SLAVE_1;
        }
        logger.debug("数据源key：" + lookupKey);
        return lookupKey;
    }

}
