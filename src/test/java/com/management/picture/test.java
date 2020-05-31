package com.management.picture;

import com.alibaba.fastjson.JSON;
import com.management.picture.model.Tag;
import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * Json测试
 *
 * @author Yue Wu
 */
public class test {

    @Test
    public void test1() {
        List<Tag> list = new ArrayList<>();
        list.add(new Tag(1,"涩图1"));
        list.add(new Tag(2,"涩图2"));
        list.add(new Tag(3,"涩图3"));
        list.add(new Tag(4,"涩图4"));
        list.add(new Tag(5,"涩图5"));
        System.out.println(list + "\n");

        String json = JSON.toJSONString(list);
        System.out.println(json);

       List<Tag> jsonList = JSON.parseArray(json,Tag.class);
        System.out.println(jsonList.get(2).getTag_name());
    }
}
