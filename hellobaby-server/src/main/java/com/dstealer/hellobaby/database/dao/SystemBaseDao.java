package com.dstealer.hellobaby.database.dao;

import com.dstealer.hellobaby.database.common.DSManager;
import com.dstealer.hellobaby.database.common.SQLExecutor;
import com.dstealer.hellobaby.database.common.SQLStatement;
import com.dstealer.hellobaby.database.pojo.*;

import java.util.List;

/**
 * 系统数据源管理
 * Created by LiShiwu on 02/18/2017.
 */
public class SystemBaseDao {
    /**
     * 系统使用数据源定义
     */
    public static final String SYSTEM_DATASOURCE = "SYSTEM";

    /**
     * 获取事务SQL执行器
     *
     * @return
     * @throws Exception
     */
    protected static SQLExecutor getTxExecutor() throws Exception {
        return new SQLExecutor(DSManager.SINGLETON.getTransactionConnection(SYSTEM_DATASOURCE));
    }

    /**
     * 获取只读SQL执行器
     *
     * @return
     * @throws Exception
     */
    protected static SQLExecutor getReadOnlyExecutor() throws Exception {
        return new SQLExecutor(DSManager.SINGLETON.getReadonlyConnection(SYSTEM_DATASOURCE));
    }

    /**
     * 根据Job名称获取SQLMonitor事件
     *
     * @param jobName
     * @return
     * @throws Exception
     */
    public static List<EventMonitorPojo> getEventMonitorByJobName(String jobName) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_event_monitor WHERE job_name = ?", jobName);
            return executor.queryList(builder.build(), EventMonitorPojo.INSTANCE);
        } finally {
            executor.close();
        }
    }

    /**
     * 根据Job名称获取SQLMail事件,以及附件
     *
     * @param jobName
     * @return
     * @throws Exception
     */
    public static List<EventMailPojo> getEventMailByJobName(String jobName) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_event_mail WHERE job_name = ?", jobName);
            List<EventMailPojo> pojoList = executor.queryList(builder.build(), EventMailPojo.INSTANCE);
            for (EventMailPojo pojo : pojoList) {
                builder = SQLStatement.newBuilder();
                builder.append("SELECT * FROM t_event_mail_attachment WHERE mail_id = ?", pojo.getId());
                pojo.setAttachments(executor.queryList(builder.build(), EventMailAttachmentPojo.INSTANCE));
            }
            return pojoList;
        } finally {
            executor.close();
        }
    }

    /**
     * 获取全部数据源
     *
     * @return
     * @throws Exception
     */
    public static List<DatasourcePojo> getDatasource() throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_datasource");
            return executor.queryList(builder.build(), DatasourcePojo.INSTANCE);
        } finally {
            executor.close();
        }
    }

    /**
     * 获取全部Job配置
     *
     * @return
     * @throws Exception
     */
    public static List<JobConfigPojo> getJobConfig() throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_job_config");
            return executor.queryList(builder.build(), JobConfigPojo.INSTANCE);
        } finally {
            executor.close();
        }
    }

    /**
     * 获取指定主机目录监控配置
     *
     * @param hostName
     * @return
     * @throws Exception
     */
    public static List<DirMonitorPojo> getDirMonitor(String hostName) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_dir_monitor WHERE host_name = ?", hostName);
            return executor.queryList(builder.build(), DirMonitorPojo.INSTANCE);
        } finally {
            executor.close();
        }
    }

    /**
     * 查找CPU监控配置信息
     *
     * @param hostName
     * @return
     * @throws Exception
     */
    public static CPUMonitorPojo findCPUMonitor(String hostName) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_cpu_monitor WHERE host_name = ? LIMIT 1", hostName);
            return executor.queryOne(builder.build(), CPUMonitorPojo.HANDLER);
        } finally {
            executor.close();
        }
    }

    /**
     * 查找CPU监控配置信息
     *
     * @param hostName
     * @return
     * @throws Exception
     */
    public static MemMonitorPojo findMemMonitor(String hostName) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_mem_monitor WHERE host_name = ? LIMIT 1", hostName);
            return executor.queryOne(builder.build(), MemMonitorPojo.HANDLER);
        } finally {
            executor.close();
        }
    }

    /**
     * 查找主机配置信息
     *
     * @return
     * @throws Exception
     */
    public static List<HostCfgPojo> getHostCfg() throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_host_cfg");
            return executor.queryList(builder.build(), HostCfgPojo.HANDLER);
        } finally {
            executor.close();
        }
    }
}
