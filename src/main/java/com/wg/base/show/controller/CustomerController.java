package com.wg.base.show.controller;

import com.querydsl.core.QueryResults;
import com.wg.base.show.common.page.BasePageable;
import com.wg.base.show.common.result.Result;
import com.wg.base.show.controller.bean.CustomerAddBean;
import com.wg.base.show.controller.bean.CustomerUpdateBean;
import com.wg.base.show.entity.Customer;
import com.wg.base.show.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "用户", tags = { "用户" })
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);


    @Autowired
    private CustomerService customerService;

    @PostMapping()
    @ApiOperation(value="添加用户", notes="添加用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true)
    })
    public Result<Customer> addCustomer(@RequestBody CustomerAddBean customerAddBean){
        return Result.success(customerService.addCustomer(customerAddBean));
    }

    @GetMapping("/{id}")
    @ApiOperation(value="获取用户", notes="获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Long", name ="id", value ="用户ID",required = true)
    })
    public Result<Customer> getCustomer(@PathVariable Long id){
        return Result.success(customerService.getCustomerById(id));
    }

    @GetMapping("/login")
    @ApiOperation(value="登录", notes="登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String", name ="userName", value ="用户名",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String", name ="password", value ="密码",required = true)
    })
    public Result<String> login(String userName,String password){
        return Result.success(customerService.getLogin(userName,password));
    }

    @GetMapping("/all")
    @ApiOperation(value="获取所有用户", notes="获取所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true)
    })
    public Result<List<Customer>> getCustomerAll(){
        return Result.success(customerService.getCustomerAll());
    }

    @GetMapping("/list")
    @ApiOperation(value="获取用户--分页", notes="获取用户--分页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String", name ="customerName", value ="用户名称"),
            @ApiImplicitParam(paramType = "query",dataType = "int", name ="ageStart", value ="年龄范围"),
            @ApiImplicitParam(paramType = "query",dataType = "int", name ="ageEnd", value ="年龄范围")
    })
    public Result<QueryResults<Customer>> getCustomerPage(BasePageable basePageable, String customerName, Integer ageStart, Integer ageEnd){
        return Result.success(customerService.getCustomerByPage(basePageable,customerName,ageStart,ageEnd));
    }

    @PutMapping()
    @ApiOperation(value="更新用户", notes="更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true)
    })
    public Result<Customer> updateCustomer(@RequestBody CustomerUpdateBean customerUpdateBean){
        return Result.success(customerService.updateCustomer(customerUpdateBean));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="删除用户", notes="删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",dataType = "String", name ="access_token", value ="token",required = true),
            @ApiImplicitParam(paramType = "path",dataType = "Long", name ="id", value ="用户ID",required = true)
    })
    public Result<String> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return Result.success("0");
    }

}
