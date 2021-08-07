package com.sensorweb.datacenterproductservice.service;

import com.sensorweb.datacenterproductservice.dao.ProductMapper;
import com.sensorweb.datacenterproductservice.entity.Product;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
public class InsertProductService {

    @Autowired
    ProductMapper productMapper;

    @Value("${datacenter.path.product}")
    private String filePath;

    public void InsertProduct(Product info) {
        String url = info.getDownloadAddress();
        String serviceName = info.getServiceName();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        try {
            if (serviceName.equals("PMStation") && url.endsWith("Interpolated.tif")) {
                File temp = new File(filePath + "Interpolated/");
                if (!temp.exists()) {
                    temp.mkdirs();
                }
                DataCenterUtils.downloadHttpUrl(url, filePath + "Interpolated/", fileName);
                info.setDownloadAddress(filePath + "Interpolated/" + fileName);
            } else {
                File file = new File(filePath + serviceName + "/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                DataCenterUtils.downloadHttpUrl(url, filePath + serviceName + "/", fileName);
                info.setDownloadAddress(filePath + serviceName + "/" + fileName);
            }
            productMapper.insertSelective(info);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 转移缓存路径的文件到目标文件夹
     */
    public void moveFileByPath(String tmpDir, String target) {
        File startFile=new File(tmpDir);
        File endDirection=new File(target);
        if(!endDirection.exists()) {
            endDirection.mkdirs();
        }
        File endFile=new File(endDirection + File.separator+ startFile.getName());
        try {
            if (startFile.renameTo(endFile)) {
                System.out.println("文件移动成功！目标路径：{"+endFile.getAbsolutePath()+"}");
            } else {
                System.out.println("文件移动失败！起始路径：{"+startFile.getAbsolutePath()+"}");
            }
        }catch(Exception e) {
            System.out.println("文件移动出现异常！起始路径：{"+startFile.getAbsolutePath()+"}");
        }
    }



    public boolean insertAirPollutionPrediction(Product product) {
        boolean statue =productMapper.insertData(product);
        return statue;
    }


}
