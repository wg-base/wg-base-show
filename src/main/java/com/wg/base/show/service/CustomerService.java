package com.wg.base.show.service;

import com.querydsl.core.QueryResults;
import com.wg.base.show.common.page.BasePageable;
import com.wg.base.show.controller.bean.CustomerAddBean;
import com.wg.base.show.controller.bean.CustomerUpdateBean;
import com.wg.base.show.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(CustomerAddBean customerAddBean);

    Customer getCustomerById(Long id);

    String getLogin(String userName,String password);

    List<Customer> getCustomerAll();

    QueryResults<Customer> getCustomerByPage(BasePageable basePageable, String customerName, Integer ageStart, Integer ageEnd);

    Customer updateCustomer(CustomerUpdateBean customerUpdateBean);

    void deleteCustomer(Long id);
}
