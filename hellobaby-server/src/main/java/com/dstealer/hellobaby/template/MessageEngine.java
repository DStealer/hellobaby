package com.dstealer.hellobaby.template;

import com.dstealer.hellobaby.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;
import java.util.Properties;

/**
 * 消息模板引擎
 * Created by LiShiwu on 02/19/2017.
 */
public class MessageEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEngine.class);
    private TemplateEngine templateEngine;

    public MessageEngine() {
        this(null);
    }

    /**
     * 使用默认消息转换
     *
     * @param defaultMessages
     */
    public MessageEngine(Properties defaultMessages) {
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        if (defaultMessages != null && defaultMessages.size() > 0) {
            StandardMessageResolver messageResolver = new StandardMessageResolver();
            messageResolver.setDefaultMessages(defaultMessages);
            this.templateEngine.setMessageResolver(messageResolver);
        }
        LOGGER.info("Message engine init complete");
    }

    /**
     * 执行消息渲染
     *
     * @param template
     * @param object
     * @return
     */
    public String process(String template, Object object) {
        return this.process(template, object, null);
    }

    /**
     * 执行消息渲染
     *
     * @param template
     * @param object
     * @param additional 额外的变量
     * @return
     */
    public String process(String template, Object object, Map<String, Object> additional) {
        Context context = new Context();
        context.setVariable(Constant.KEY_PARAMETER, object);
        if (additional != null) {
            for (Map.Entry<String, Object> entry : additional.entrySet()) {
                if (!Constant.KEY_PARAMETER.equals(entry.getKey())) {
                    context.setVariable(entry.getKey(), entry.getValue());
                }
            }
        }
        return this.templateEngine.process(template, context);
    }
}
