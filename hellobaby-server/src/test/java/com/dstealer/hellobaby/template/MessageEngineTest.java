package com.dstealer.hellobaby.template;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiShiwu on 02/19/2017.
 */
public class MessageEngineTest {
    @Test
    public void test001() throws Exception {
        MessageEngine engine = new MessageEngine();
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span th:text=\"'this is the test text'\"></span>\n" +
                "<ul th:each=\"e:${params}\">\n" +
                "    <li th:text=\"${e.key}+${e.value}\"></li>\n" +
                "</ul>\n" +
                "</body>\n" +
                "</html>";
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        System.out.println(engine.process(template, map));
    }

    @Test
    public void test002() throws Exception {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        mapList.add(map);
        map = new HashMap<>();
        map.put("key1", "value21");
        map.put("key2", "value22");
        mapList.add(map);
        MessageEngine engine = new MessageEngine();
        String template = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<span th:text=\"'this is the test text'\"></span>\n" +
                "<ul th:each=\"e:${params}\">\n" +
                "    <li th:text=\"${e}\"></li>\n" +
                "</ul>\n" +
                "</body>\n" +
                "</html>";

        System.out.println(engine.process(template, mapList));
    }

    @Test
    public void test003() throws Exception {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        mapList.add(map);
        map = new HashMap<>();
        map.put("key1", "value21");
        map.put("key2", "value22");
        mapList.add(map);
        MessageEngine engine = new MessageEngine();

        String rawTemplate = "[(${params})]";

        String textTemplate = "您好,\n" +
                "[#th:block th:each=\"item:${params}\"]" +
                "这里是Key:[(${item.key1})] ,这里是Value:[(${item.key2})]\n" +
                "[/th:block]好的";
        System.out.println(engine.process(textTemplate, mapList));
    }

    @Test
    public void test004() throws Exception {
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("task_id", "1000");
        mapList.add(map);
        map = new HashMap<>();
        map.put("task_id", "2000");
        mapList.add(map);
        MessageEngine engine = new MessageEngine();
        String textTemplate = "任务[# th:each='item:${params}']<[(${item['task_id']})]>[/]未确认超时";
        System.out.println(engine.process(textTemplate, mapList));
    }
}