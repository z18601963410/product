package com.ike.order.config.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * SQL语句拦截器,设置数据源类型的KEY
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    //正则表达式
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 拦截器
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //判断是否属于事务管理
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        //获取拦截对象的变量数组(即sql语句的组成部分)
        Object[] objects = invocation.getArgs();
        //获取数组第一个元素(即sql第一个单词: select/update/insert/delete)
        MappedStatement ms = (MappedStatement) objects[0];
        //设置默认的数据源类型
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        if (!synchronizationActive) {
            //判断是否属于select
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                // selectKey 为自增id查询主键(SELECT LAST_INSERT_ID())方法，使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    //获取SQL语句
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    //去掉空格等多余字符
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    //字符串匹配:是否为insert/delete/update
                    if (sql.matches(REGEX)) {
                        //主库执行
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        //通过随机数选择从库进行读操作
                        lookupKey = DynamicDataSourceHolder.randomSetLookUpKey();
                    }
                }
            }
        } else {
            //事务管理的交由主库处理
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Strategy, SqlCommonType [{}]..", ms.getId(), lookupKey, ms.getSqlCommandType().name());
        //线程变量:设置数据源类型
        DynamicDataSourceHolder.setDbType(lookupKey);
        //调用下一个拦截器
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties arg0) {
    }

}
