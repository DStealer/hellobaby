package com.dstealer.hellobaby;

import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.database.common.DBManager;
import com.dstealer.hellobaby.database.common.DSManager;
import com.dstealer.hellobaby.startup.EnvConfig;
import com.dstealer.hellobaby.startup.Startup;
import com.dstealer.hellobaby.template.MessageEngine;
import org.junit.BeforeClass;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @BeforeClass
    public static void setup() throws Exception {
        DBManager.SINGLETON.start();
        DSManager.SINGLETON.init();
        EnvConfig.init();
    }

    /**
     * 消息打印
     *
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    protected static <T> String printMessage(T t) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        BeanInfo beanInfo = Introspector.getBeanInfo(t.getClass(), Introspector.IGNORE_IMMEDIATE_BEANINFO);
        String propertyName;
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                stringBuilder.append(descriptor.getName()).append(":").append(descriptor.getReadMethod().invoke(t)).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 测试定时任务
     *
     * @param clazz
     * @throws Exception
     */
    protected static void scheduleJob(String jobName, Class<? extends Job> clazz) throws Exception {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.getContext().put(Constant.KEY_MESSAGE_ENGINE, new MessageEngine());
        Trigger simpleTrigger = TriggerBuilder.newTrigger().startNow().build();
        JobDetail job = JobBuilder.newJob(clazz).withIdentity(jobName).build();
        scheduler.scheduleJob(job, simpleTrigger);
        scheduler.start();
    }

    /**
     * 测试应用
     *
     * @param args
     */
    public static void main(String[] args) {
        Startup.main(args);
    }

    /**
     * 通过反射获取bean信息
     * @param o
     * @throws Exception
     */
    protected static void print(Object o) throws Exception {
        BeanInfo info = Introspector.getBeanInfo(o.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            try {

                Method method = pd.getReadMethod();
                if (method != null) {
                    System.out.println(pd.getDisplayName() + "=" + method.invoke(o, null));
                } else {
                    System.out.println(pd.getDisplayName() + "=null");
                }
            } catch (Exception e) {
                //ignore
            }
        }
    }
}
