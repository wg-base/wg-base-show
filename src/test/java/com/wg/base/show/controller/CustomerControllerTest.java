package com.wg.base.show.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wg.base.show.controller.bean.CustomerAddBean;
import com.wg.base.show.controller.bean.CustomerUpdateBean;
import com.wg.base.show.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest
@Transactional
public class CustomerControllerTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private Customer customer = null;

    CustomerControllerTest() {
        executeSql("sql/mysql/schema.sql");
        executeSql("sql/mysql/import-data.sql");
    }

    @BeforeClass
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    private void executeSql(String sqlPath) {
        // todo JDBC 执行sql文件
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
        //将添加后的ID保存
        customer = JSON.parseObject(jsonObject.get("data").toString()).toJavaObject(Customer.class);
        customer.setPassword(customerAddBean.getPassword());
        LOGGER.info("customer is " + customer);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test
    public void testGetCustomer() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/"+customer.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }

    @Test(dependsOnMethods = {"testAddCustomer","testGetCustomer"})
    public void testLogin() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customer/login?userName="+customer.getCustomerName()+"&password="+customer.getPassword()))
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
        customerUpdateBean.setId(customer.getId());
        customerUpdateBean.setCustomerName("wangliheng28");
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
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/customer/"+customer.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSON.parseObject(content);
        Assert.assertEquals(jsonObject.get("code"),0);
    }
}