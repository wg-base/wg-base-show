package com.wg.base.show.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//@WebAppConfiguration：测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的；value指定web应用的根；
@WebAppConfiguration()
//@ContextHierarchy：指定容器层次
/*@ContextHierarchy({
        @ContextConfiguration(locations = {
                "classpath:applicationContext.xml" //这里的applicationContext.xml文件，如果有特殊的bean需要配置，则需要放在src/test/resources目录下
        }),
        @ContextConfiguration({
                "classpath:spring-mvc.xml"
        })
})*/
public class CustomerControllerTest extends AbstractTestNGSpringContextTests {

    private static String baseUrl = "";
    private static String addCustomerUrl = "";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    CustomerControllerTest() {
        executeSql("sql/mysql/schema.sql");
        executeSql("sql/mysql/import-data.sql");
    }

    @BeforeClass
    public void setUp() {
        //MockMvcBuilders.webAppContextSetup(wac).build()创建一个MockMvc进行测试
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    private void executeSql(String sqlPath) {
        // todo 执行sql文件
    }

    @Test
    public void testAddCustomer() throws Exception {
        String param="{\n" +
                "  \"age\": 27,\n" +
                "  \"customerName\": \"wang15\",\n" +
                "  \"password\": \"123454566\",\n" +
                "  \"phone\": \"18332555553\"\n" +
                "}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer/add",param))//perform用于执行一个请求
                .andDo(MockMvcResultHandlers.print())  //增加一个结果处理器
                .andExpect(MockMvcResultMatchers.status().isOk()) //执行完成后的断言
                .andReturn(); //执行完成后返回相应的结果
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        //采用Asser的方式进行断言
        Assert.assertEquals(jsonObject.get("code"), "0");
    }

    @Test
    public void testGetCustomer() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))//perform用于执行一个请求
                .andDo(MockMvcResultHandlers.print())  //增加一个结果处理器
                .andExpect(MockMvcResultMatchers.status().isOk()) //执行完成后的断言
                .andReturn(); //执行完成后返回相应的结果
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        //采用Asser的方式进行断言
        Assert.assertEquals(jsonObject.get("code"), "0");
    }

    @Test
    public void testLogin() {
    }

    @Test
    public void testGetCustomerAll() {
    }

    @Test
    public void testGetCustomerPage() {
    }

    @Test
    public void testUpdateCustomer() {
    }

    @Test
    public void testDeleteCustomer() {
    }
}