package com.dstealer.hellobaby.database.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 数据库连接包装对象 非线程安全
 * Created by lishiwu on 2016/7/15.
 */
public class SQLExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLExecutor.class);
    //每次处理的数据数据量
    private static final int BATCH_OPERATION_SIZE = 5000;
    private Connection connection;

    public SQLExecutor(Connection connection) {
        this.connection = connection;
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            this.close(this.connection, null, null);
        }
    }

    /**
     * 关闭数据库连接资源
     */
    public void close() {
        this.close(this.connection, null, null);
    }

    /**
     * 提交并关闭
     */
    public void commitAndClose() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        } finally {
            this.close(this.connection, null, null);
        }
    }

    /**
     * 回滚并关闭
     */
    public void rollbackAndClose() {
        try {
            this.connection.rollback();
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
        } finally {
            this.close(this.connection, null, null);
        }
    }

    /**
     * 查询以名值对方式返回记录
     *
     * @throws Exception
     */
    public List<Map<String, String>> queryMapList(SQLStatement sqlStatement) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(sqlStatement.getSql());
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            Map<String, Integer> rsmdMap = new HashMap<>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                rsmdMap.put(rsmd.getColumnName(i), rsmd.getColumnType(i));
            }
            List<Map<String, String>> rsList = new ArrayList<>();
            Map<String, String> rowMap;
            int initialCapacity = (int) (rsmd.getColumnCount() / 0.75F + 1.0F);
            while (resultSet.next()) {
                rowMap = new HashMap<>(initialCapacity);
                for (Map.Entry<String, Integer> entry : rsmdMap.entrySet()) {
                    switch (entry.getValue()) {
                        case Types.DATE:
                        case Types.TIMESTAMP:
                            if (resultSet.getTimestamp(entry.getKey()) != null) {
                                rowMap.put(entry.getKey(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp(entry.getKey()).getTime()));
                            } else {
                                rowMap.put(entry.getKey(), null);
                            }
                            break;
                        default:
                            rowMap.put(entry.getKey(), resultSet.getString(entry.getKey()));
                    }
                }
                rsList.add(rowMap);
            }
            return rsList;
        } finally {
            this.close(null, preparedStatement, resultSet);
        }
    }

    /**
     * 查询结果以行数组列表形式返回。
     * 注意:第0行为数据标题，数据自第1行开始
     *
     * @param sqlStatement
     * @return
     * @throws SQLException
     */
    public List<String[]> queryHeaderArrayList(SQLStatement sqlStatement) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // 查询结果集
            preparedStatement = this.connection.prepareStatement(sqlStatement.getSql());
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            resultSet = preparedStatement.executeQuery();
            // 构建返回对象
            List<String[]> rsList = new LinkedList<>(); // 数组列表
            int initialCapacity = resultSet.getMetaData().getColumnCount();
            // 添加列名行
            String[] titleRow = new String[initialCapacity];
            for (int i = 0; i < initialCapacity; i++) {
                titleRow[i] = resultSet.getMetaData().getColumnLabel(i + 1);
            }
            rsList.add(titleRow);
            // 添加数据行
            while (resultSet.next()) {
                String[] rowArray = new String[initialCapacity];
                for (int i = 0; i < initialCapacity; i++) {
                    String colData = resultSet.getString(i + 1);
                    rowArray[i] = colData == null ? "" : colData;
                }
                rsList.add(rowArray);
            }
            return rsList;
        } finally {
            this.close(null, preparedStatement, resultSet);
        }
    }

    /**
     * 从数据库中读取数据
     *
     * @param sqlStatement
     * @param resultRSHandler
     * @return
     * @throws SQLException
     */
    public <T> T queryOne(SQLStatement sqlStatement, RSHandler<T> resultRSHandler) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = sqlStatement.getSql();

            if (!sql.toUpperCase().contains("LIMIT ") && sql.toUpperCase().contains("SELECT ")) {
                sql += " LIMIT 1";
            }
            preparedStatement = this.connection.prepareStatement(sql);
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultRSHandler.parseFrom(resultSet);
            }
            return null;
        } finally {
            this.close(null, preparedStatement, resultSet);
        }
    }

    /**
     * 从数据库中读取数据
     *
     * @param sqlStatement
     * @param resultRSHandler
     * @return
     * @throws SQLException
     */
    public <T> List<T> queryList(SQLStatement sqlStatement, RSHandler<T> resultRSHandler) throws SQLException {
        List<T> list = new LinkedList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(sqlStatement.getSql());
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultRSHandler.parseFrom(resultSet));
            }
            return list;
        } finally {
            this.close(null, preparedStatement, resultSet);
        }
    }

    /**
     * 数据保存
     *
     * @param sql
     * @param t      数据
     * @param parser 参数处理
     * @return
     */
    public <T> void save(String sql, T t, PSParser<T> parser) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(sql);
            parser.parseTo(preparedStatement, t);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        } finally {
            this.close(null, preparedStatement, null);
        }
    }

    /**
     * 数据批量保存
     *
     * @param sql
     * @param dataList 数据
     * @param parser   参数处理
     * @return
     */
    public <T> void save(String sql, List<T> dataList, PSParser<T> parser) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(sql);
            //批量处理
            for (int i = 0, len = dataList.size(); i < len; i++) {
                parser.parseTo(preparedStatement, dataList.get(i));
                preparedStatement.addBatch();
                if (i % BATCH_OPERATION_SIZE == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        } finally {
            this.close(null, preparedStatement, null);
        }
    }

    /**
     * 执行数据创建并获取自动生成的key,返回生成的key列表
     *
     * @param sqlStatement
     * @return
     * @throws SQLException
     */
    public List<BigDecimal> saveAndGetKey(SQLStatement sqlStatement) throws SQLException {
        if (!sqlStatement.getSql().toLowerCase().trim().startsWith("insert")) {
            throw new SQLException("Not a insert statement");
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement(sqlStatement.getSql(), PreparedStatement.RETURN_GENERATED_KEYS);
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            //由于大多数的insert操作为单条，但是存在多条的可能性
            List<BigDecimal> keyList = new ArrayList<>(1);
            while (resultSet.next()) {
                keyList.add(resultSet.getBigDecimal(1));
            }
            this.connection.commit();
            return keyList;
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        } finally {
            this.close(null, preparedStatement, resultSet);
        }
    }

    /**
     * 执行数据更新
     *
     * @param sqlStatement
     * @throws SQLException
     */
    public int saveOrUpdate(SQLStatement sqlStatement) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(sqlStatement.getSql());
            this.fillStatement(preparedStatement, sqlStatement.getParamList());
            int hits = preparedStatement.executeUpdate();
            this.connection.commit();
            return hits;
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        } finally {
            this.close(null, preparedStatement, null);
        }
    }

    /**
     * 执行数据更新
     *
     * @param sqls
     * @throws SQLException
     */
    public void saveOrUpdate(List<String> sqls) throws SQLException {
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            for (String sql : sqls) {
                stmt.executeUpdate(sql);
            }
            this.connection.commit();
        } catch (SQLException e) {
            this.connection.rollback();
            throw e;
        } finally {
            this.close(null, stmt, null);
        }
    }

    /**
     * 关闭数据库资源
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    private void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
    }

    /**
     * 设置PreparedStatement参数
     *
     * @param stmt
     * @param params
     * @throws SQLException
     */
    private void fillStatement(PreparedStatement stmt, List<Object> params)
            throws SQLException {
        ParameterMetaData pmd = stmt.getParameterMetaData();
        int stmtCount = pmd.getParameterCount();
        int paramsCount = params == null ? 0 : params.size();
        if (stmtCount != paramsCount) {
            throw new SQLException("Wrong number of parameters: expected " + stmtCount + ", was given " + paramsCount);
        }
        if (params == null) {
            return;
        }
        for (int i = 0; i < params.size(); i++) {
            int type = pmd.getParameterType(i + 1);
            if (params.get(i) == null) {
                stmt.setNull(i + 1, type);
                continue;
            }
            switch (type) {
                case Types.TIMESTAMP:
                    stmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) params.get(i)).getTime()));
                    break;
                default:
                    stmt.setObject(i + 1, params.get(i));
            }
        }
    }
}
