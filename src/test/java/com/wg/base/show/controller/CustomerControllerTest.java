package com.wg.base.show.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wg.base.show.controller.bean.CustomerAddBean;
import com.wg.base.show.controller.bean.CustomerUpdateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
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

@SpringBootTest
@TestExecutionListeners(listeners = DependencyInjectionTestExecutionListener.class)
public class CustomerControllerTest extends BaseTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    CustomerControllerTest() {
        executeSql("sql/mysql/schema.sql");
        executeSql("sql/m+=-0954 +  ysql/import-data.sql");
    }

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    private void executeSql(String sqlPath) {
        // todo 执行sql文件
    }

    @Test
    public void testAddCustomer() throws Exception {
        CustomerAddBean customerAddBean = new CustomerAddBean();
        customerAddBean.setAge(27);
        customerAddBean.setCustomerName("wangliheng28");
        customerAddBean.setPassword("1222222");
        customerAddBean.setPhone("18332555233");
        String requestJson = JSONObject.toJSONString(customerAddBean);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testGetCustomer() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test(dependsOnMethods = "testAddCustomer")
    public void testLogin() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/login?userName=wangliheng25&password=1222222"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testGetCustomerAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testGetCustomerPage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/list?pageNumber=1&pageSize=10" +
                "&ageStart=20&ageEnd=30&customerName=wangliheng1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerUpdateBean customerUpdateBean = new CustomerUpdateBean();
        customerUpdateBean.setId(1L);
        customerUpdateBean.setCustomerName("wangliheng");
        customerUpdateBean.setPhone("18332555233");
        String json = JSONObject.toJSONString(customerUpdateBean);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/customer").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/customer/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }
}