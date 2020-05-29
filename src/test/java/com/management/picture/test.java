package com.management.picture;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

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
        List<String> list = new ArrayList<>();
        list.add("内筒1");
        list.add("内筒2");
        list.add("内筒3");
        list.add("内筒4");
        list.add("内筒5");
        System.out.println(list + "\n");

        String json = JSON.toJSONString(list);
        System.out.println(json);

       List<String> jsonList = JSON.parseObject(json,List.class);
        System.out.println(jsonList);
    }
}
