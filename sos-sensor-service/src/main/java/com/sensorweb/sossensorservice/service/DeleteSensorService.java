package com.sensorweb.sossensorservice.service;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.dao.*;
import com.sensorweb.sossensorservice.entity.Capability;
import com.sensorweb.sossensorservice.entity.Characteristic;
import com.sensorweb.sossensorservice.entity.Component;
import com.sensorweb.sossensorservice.entity.Procedure;
import com.sensorweb.sossensorservice.feign.ObsFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vast.ows.OWSException;
import org.vast.ows.swe.DeleteSensorReaderV20;
import org.vast.ows.swe.DeleteSensorRequest;
import org.vast.ows.swe.DeleteSensorResponse;
import org.vast.ows.swe.DeleteSensorResponseWriterV20;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

@Slf4j
@Service
public class DeleteSensorService {

    @Autowired
    private ObsFeignClient obsFeignClient;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private ComponentMapper componentMapper;

    @Autowired
    private CapabilityMapper capabilityMapper;

    @Autowired
    private CharacteristicMapper characteristicMapper;

    @Autowired
    private ClassifierMapper classifierMapper;

    @Autowired
    private IdentifierMapper identifierMapper;

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private ValidTimeMapper validTimeMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VectorMapper vectorMapper;

    @Autowired
    private TextMapper textMapper;

    @Autowired
    private QuantityMapper quantityMapper;

    @Autowired
    private QuantityRangeMapper quantityRangeMapper;

    @Autowired
    private DescribeSensorExpandService expandService;

    /**
     * 解析DeleteSensorRequest请求，获取DeleteSensorRequest对象
     * @param requestContent
     * @return
     * @throws DOMHelperException
     * @throws OWSException
     */
    public DeleteSensorRequest getDeleteSensorRequest(String requestContent) throws DOMHelperException, OWSException {
        if (StringUtils.isBlank(requestContent)) {
            return null;
        }

        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(requestContent.getBytes()), false);
        DeleteSensorReaderV20 reader = new DeleteSensorReaderV20();
        return reader.readXMLQuery(domHelper, domHelper.getRootElement());
    }

    /**
     * 解析DeleteSensorRequest请求，获取请求中的procedureId参数
     * @param request
     * @return
     */
    public String getProcedureId(DeleteSensorRequest request) {
        if (request==null) {
            return null;
        }
        return request.getProcedureId();
    }

    /**
     * 根据请求中的待删除的procedureId参数，删除procedure以及相关联的offering
     * @param procedureId
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteSensor(String procedureId) throws DOMHelperException {
        if (StringUtils.isBlank(procedureId)) {
            log.info("procedure为空");
            return false;
        }
        //如果是平台，则在删除平台前需要先删除附属与该平台下的所有传感器
        if (expandService.isPlatform(procedureId)) {
            //查询该平台下的所有传感器
            List<String> sensorIds = expandService.getComponentByPlatformId(procedureId);
            if (sensorIds!=null && sensorIds.size()>0) {
                for (String sensorId : sensorIds) {
                    deleteSensorById(sensorId);
                }
                componentMapper.deleteByPlatformId(procedureId);
                //删除平台
                deleteSensorById(procedureId);
            }
        } else {
            //首先查询该传感器处于哪个平台
            Component component = componentMapper.selectByHref(procedureId);
            String platformId = component.getPlatformId();
            //更新平台内容
            flushPlatform(procedureId, platformId);
            //删除component表里的相关数据
            componentMapper.deleteByHref(procedureId);
            deleteSensorById(procedureId);
        }
        return true;
    }

    /**
     * 根据id删除procedure，删除时与插入时对应，不能漏删(Component节点留在别处处理)
     * @param id
     */
    public void deleteSensorById(String id) {
        if (!StringUtils.isBlank(id)) {
            //delete identification
            identifierMapper.deleteByProcedureId(id);
            //delete classification
            classifierMapper.deleteByProcedureId(id);
            //delete characteristic
            List<Characteristic> characteristics = characteristicMapper.selectByProcedureId(id);
            if (characteristics!=null && characteristics.size()>0) {
                for (Characteristic characteristic:characteristics) {
                    String characteristicId = id + ":characteristic:" + characteristic.getName();
                    categoryMapper.deleteByOutId(characteristicId);
                    quantityMapper.deleteByOutId(characteristicId);
                    quantityRangeMapper.deleteByOutId(characteristicId);
                    textMapper.deleteByOutId(characteristicId);
                    vectorMapper.deleteByOutId(characteristicId);
                }
            }
            characteristicMapper.deleteByProcedureId(id);
            //delete capability
            List<Capability> capabilities = capabilityMapper.selectByProcedureId(id);
            if (capabilities!=null && capabilities.size()>0) {
                for (Capability capability:capabilities) {
                    String capabilityId = id + ":capability:" + capability.getName();
                    categoryMapper.deleteByOutId(capabilityId);
                    quantityMapper.deleteByOutId(capabilityId);
                    quantityRangeMapper.deleteByOutId(capabilityId);
                    textMapper.deleteByOutId(capabilityId);
                    vectorMapper.deleteByOutId(capabilityId);
                }
            }
            capabilityMapper.deleteByProcedureId(id);
            //delete validTime
            validTimeMapper.deleteByProcedureId(id);
            //delete contact
            contactMapper.deleteByProcedureId(id);
            //delete position
            positionMapper.deleteByProcedureId(id);
            //delete keywords
            keywordMapper.deleteByProcedureId(id);
            //delete sensorML File
            Procedure procedure = procedureMapper.selectById(id);
            boolean flag = new File(procedure.getDescriptionFile()).delete();
            log.info("deleteFile status: " + flag);
            //delete observation
            obsFeignClient.deleteObservationById(id);
            //delete procedure
            procedureMapper.deleteById(id);
        }
    }

    /**
     * 删除成功，返回相应请求
     * @param procedureId 成功删除的procedure的id
     * @return
     * @throws OWSException
     */
    public Element getDeleteSensorResponse(String procedureId) throws OWSException {
        DeleteSensorResponse response = new DeleteSensorResponse("SOS");
        response.setDeletedProcedure(procedureId);
        DOMHelper domHelper = new DOMHelper();
        DeleteSensorResponseWriterV20 writer = new DeleteSensorResponseWriterV20();
        return writer.buildXMLResponse(domHelper, response, "2.0");
    }

    /**
     * 更新平台xml中的内容，主要是删除部分component节点
     * @param procedureId :传感器id
     * @param platformId : 平台id
     */
    public void flushPlatform(String procedureId, String platformId) throws DOMHelperException {
        Procedure platform = procedureMapper.selectById(platformId);
        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(DataCenterUtils.readFromFile(platform.getDescriptionFile()).getBytes()), false);
        Element element = domHelper.getElement("components/ComponentList");
        NodeList nodeList = element.getElementsByTagName("sml:component");
        int count = nodeList.getLength();
        for (int i = count; i>0; i--) {
            Node node = nodeList.item(i-1);
            String href = ((Element) node).getAttribute("href");
            if (href.equals(procedureId)) {
                element.removeChild(node);
            }
        }
        DataCenterUtils.write2File(platform.getDescriptionFile(), DataCenterUtils.element2String(domHelper.getRootElement()));
    }
}
