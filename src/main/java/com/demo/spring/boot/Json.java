package com.demo.spring.boot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Json {

    /**
     * 返回对象浏览器获取内容类型为json格式:
     * Response Headers
     *   Content-Type:application/json;charset=UTF-8
     * @return JsonArray
     */
    @RequestMapping("/json")
    public JsonArray json() {
        JsonArray ja = new JsonArray();

        JsonObject jo1 = new JsonObject();
        jo1.addProperty("name","demo");
        jo1.addProperty("age","19");

        JsonObject jo2 = new JsonObject();
        jo2.addProperty("name","test");
        jo2.addProperty("age","20");

        JsonObject jo3 = new JsonObject();
        jo3.addProperty("name","wawa");
        jo3.addProperty("age","18");

        ja.add(jo1);
        ja.add(jo2);
        ja.add(jo3);
        return ja;
    }

}