package com.dstealer.hellobaby.notify;

import com.alibaba.fastjson.JSONObject;
import com.dstealer.hellobaby.startup.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信通知工具类
 * Created by lishiwu on 2016/11/1.
 */
public class WechatNotify {
    private static final Logger LOGGER = LoggerFactory.getLogger(WechatNotify.class);

    /**
     * 发送微信报警信息
     *
     * @param message
     * @return
     */
    public static boolean notify(String message) {
        return notify(message, null);
    }

    /**
     * 发送微信报警,使用String.format格式化信息
     *
     * @param template
     * @param params
     * @return
     */
    public static boolean notify(String template, Object... params) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("moduleName", EnvConfig.moduleName);
        jsonObject.put("message", params == null || params.length == 0 ? template : String.format(template, params));
        LOGGER.info("notify wechat:" + jsonObject.toJSONString());
        return true;
    }
}