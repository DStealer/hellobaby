package com.dstealer.hellobaby.unknown;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by LiShiwu on 04/12/2017.
 */
public class JsonJSJavaTest {
    String string = "{ \n" +
            "\"name\":\"张\\\", \n" +
            "\"sex\":\"男\", \n" +
            "\"email\":\"zhang@123.com\" \n" +
            "}";

    @Test
    public void tt1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        for (ScriptEngineFactory factory : manager.getEngineFactories()) {
            System.out.println(factory.getEngineName());
            System.out.println(factory.getNames());
        }
    }

    @Test
    public void tt2() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("ECMAScript");
        engine.put("a", 1);
        engine.put("b", 2);
        Object object = engine.eval("(function(a,b){return a+b;})(a,b)");
        System.out.println(object);
    }

    @Test
    public void tt3() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("ECMAScript");
        engine.put("JSONObject", string);
        ScriptObjectMirror object = (ScriptObjectMirror) engine.eval("(function(JSONObject){return JSON.parse(JSONObject);})(JSONObject)");
        System.out.println(Arrays.toString(object.getOwnKeys(true)));
    }

    @Test
    public void tt4() throws Exception {
        String string = "{ \n" +
                "\"name\":\"张\", \n" +
                "\"sex\":\"男\", \n" +
                "\"email\":\"zhang@123.com\" \n" +
                "}";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("ECMAScript");
        engine.put("JSONObject", string);
        Bindings rtn = (Bindings) engine.eval("(function(JSONObject){return JSON.parse(JSONObject);})(JSONObject)");
        for (Map.Entry<String, Object> entry : rtn.entrySet()) {
            System.out.println(entry.getKey() + "<=>" + entry.getValue());
        }
    }

    @Test
    public void tt5() throws Exception {
        String string = "[ \n" +
                "{ \n" +
                "\"name\":\"张\", \n" +
                "\"sex\":\"男\", \n" +
                "\"email\":\"zhang@123.com\" \n" +
                "}, \n" +
                "{ \n" +
                "\"name\":\"张\", \n" +
                "\"sex\":\"男\", \n" +
                "\"email\":\"zhang@123.com\" \n" +
                "}, \n" +
                "{ \n" +
                "\"name\":\"邓\", \n" +
                "\"sex\":\"女\", \n" +
                "\"email\":\"zhen@123.com\" \n" +
                "} \n" +
                "] ";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("ECMAScript");
        engine.put("JSONObject", string);
        Bindings rtn = (Bindings) engine.eval("(function(JSONObject){return JSON.parse(JSONObject);})(JSONObject)");
        for (Map.Entry<String, Object> entry : rtn.entrySet()) {
            System.out.println(entry.getKey() + "<=>");
            for (Map.Entry<String, Object> iEntry : ((Map<String, Object>) entry.getValue()).entrySet()) {
                System.out.println(iEntry.getKey() + "<=>" + iEntry.getValue());
            }
        }
    }
}
