package com.sensorweb.datacenterproductservice.service;

import com.sensorweb.datacenterproductservice.dao.ProductMapper;
import com.sensorweb.datacenterproductservice.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GetProductService {

    @Autowired
    ProductMapper productMapper;

    public List<Product> getproductByattribute(String name, String times, String spatial, String dim) {
        return productMapper.selectByattribute(name,times,spatial,dim);
    }

    public List<Product> getAllproduct() {
        return productMapper.selectAllproduct();
    }

    public List<Product> getproductByServiceAndTime(String service, String time) {
        return productMapper.selectByServiceAndTime(service, time);
    }
}
