package com.sensorweb.sossensorservice.service;

import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.dao.*;
import com.sensorweb.sossensorservice.entity.Platform;
import com.sensorweb.sossensorservice.entity.Sensor;
import com.sensorweb.sossensorservice.entity.*;
import com.sensorweb.sossensorservice.entity.Vector;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class DescribeSensorExpandService {

    @Autowired
    private CharacteristicMapper characteristicMapper;

    @Autowired
    private CapabilityMapper capabilityMapper;

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private ProcedureMapper procedureMapper;

    @Autowired
    private ValidTimeMapper validTimeMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private IdentifierMapper identifierMapper;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private VectorMapper vectorMapper;

    @Autowired
    private TextMapper textMapper;

    @Autowired
    private QuantityRangeMapper quantityRangeMapper;

    @Autowired
    private QuantityMapper quantityMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ClassifierMapper classifierMapper;

    @Autowired
    private ComponentMapper componentMapper;

    /**
     * 通过id查询Platform的所有有效信息
     */
    public Platform getPlatformInfoById(String procedureId) {
        Platform platform = new Platform();

        Procedure procedure = procedureMapper.selectById(procedureId);
        platform.setId(procedureId);
        platform.setName(procedure.getName());
        platform.setDescription(procedure.getDescription());
//        platform.setFilePath(procedure.getDescriptionFile());
        platform.setKeywords(getKeywordsById(procedureId));
//        platform.setIdentification(getIdentificationById(procedureId));
//        platform.setClassification(getClassificationById(procedureId));
//        platform.setValidTime(getValidTimeById(procedureId));
//        platform.setCapabilities(getCapabilityById(procedureId));
//        platform.setCharacteristics(getCharacteristicById(procedureId));
//        platform.setPosition(getPositionById(procedureId));
//        platform.setContact(getContactById(procedureId));

        List<String> sensorIds = getComponentByPlatformId(procedureId);
        if (sensorIds!=null && sensorIds.size()>0) {
            for (String sensorId:sensorIds) {
                platform.getSensors().add(getSensorInfoById(sensorId));
            }
        }
        return platform;
    }

    /**
     * 通过id查询Sensor的所有有效信息
     */
    public Sensor getSensorInfoById(String procedureId) {
        Sensor sensor = new Sensor();
        Procedure procedure = procedureMapper.selectById(procedureId);
        sensor.setId(procedureId);
        sensor.setName(procedure.getName());
        sensor.setDescription(procedure.getDescription());
//        sensor.setFilePath(procedure.getDescriptionFile());
        sensor.setKeywords(getKeywordsById(procedureId));
//        sensor.setIdentification(getIdentificationById(procedureId));
//        sensor.setClassification(getClassificationById(procedureId));
//        sensor.setValidTime(getValidTimeById(procedureId));
//        sensor.setCapabilities(getCapabilityById(procedureId));
//        sensor.setCharacteristics(getCharacteristicById(procedureId));
        sensor.setPosition(getPositionById(procedureId));
//        sensor.setContact(getContactById(procedureId));
        return sensor;
    }



    public Platform getPlatformInfoById123(String procedureId) {
        Platform platform = new Platform();

        Procedure procedure = procedureMapper.selectById123(procedureId);
        platform.setId(procedureId);
        platform.setName(procedure.getName());
//        platform.setDescription(procedure.getDescription());
//        platform.setFilePath(procedure.getDescriptionFile());
//        platform.setKeywords(getKeywordsById(procedureId));
//        platform.setIdentification(getIdentificationById(procedureId));
//        platform.setClassification(getClassificationById(procedureId));
//        platform.setValidTime(getValidTimeById(procedureId));
//        platform.setCapabilities(getCapabilityById(procedureId));
//        platform.setCharacteristics(getCharacteristicById(procedureId));
//        platform.setPosition(getPositionById(procedureId));
//        platform.setContact(getContactById(procedureId));

        List<String> sensorIds = getComponentByPlatformId(procedureId);
        if (sensorIds!=null && sensorIds.size()>0) {
            for (String sensorId:sensorIds) {
                platform.getSensors().add(getSensorInfoById123(sensorId));
            }
        }
        return platform;
    }

    /**
     * 通过id查询Sensor的所有有效信息
     */
    public Sensor getSensorInfoById123(String procedureId) {
        Sensor sensor = new Sensor();
        Procedure procedure = procedureMapper.selectById123(procedureId);
        sensor.setId(procedureId);
        sensor.setName(procedure.getName());
//        sensor.setDescription(procedure.getDescription());
//        sensor.setFilePath(procedure.getDescriptionFile());
//        sensor.setKeywords(getKeywordsById(procedureId));
//        sensor.setIdentification(getIdentificationById(procedureId));
//        sensor.setClassification(getClassificationById(procedureId));
//        sensor.setValidTime(getValidTimeById(procedureId));
//        sensor.setCapabilities(getCapabilityById(procedureId));
//        sensor.setCharacteristics(getCharacteristicById(procedureId));
//        sensor.setPosition(getPositionById(procedureId));
//        sensor.setContact(getContactById(procedureId));
        return sensor;
    }

    /**
     * 获取procedure的Position信息
     */
    public Position getPositionById(String procedureId) {
        return positionMapper.selectByProcedureId(procedureId);
    }

    /**
     * 获取procedure的ValidTime信息
     */
    public ValidTime getValidTimeById(String procedureId) {
        return validTimeMapper.selectByProcedureId(procedureId);
    }

    /**
     * 获取procedure的contact信息
     */
    public Contact getContactById(String procedureId) {
        return contactMapper.selectByProcedureId(procedureId);
    }

    /**
     * 获取procedure的capability信息
     */
    public List<Capability> getCapabilityById(String procedureId) {
        List<Capability> res = new ArrayList<>();

        List<Capability> capabilities = capabilityMapper.selectByProcedureId(procedureId);
        if (capabilities!=null && capabilities.size()>0) {
            List<Object> attributes = new ArrayList<>();
            for (Capability capability:capabilities) {
                //获取Category信息
                List<Category> categories = categoryMapper.selectByOutId(capability.getOutId());
                attributes.addAll(categories);
                //获取Text信息
                List<Text> texts = textMapper.selectByOutId(capability.getOutId());
                attributes.addAll(texts);
                //获取Vector信息
                List<Vector> vectors = vectorMapper.selectByOutId(capability.getOutId());
                attributes.addAll(vectors);
                //获取Quantity信息
                List<Quantity> quantities = quantityMapper.selectByOutId(capability.getOutId());
                attributes.addAll(quantities);
                //获取QuantityRange信息
                List<QuantityRange> quantityRanges = quantityRangeMapper.selectByOutId(capability.getOutId());
                attributes.addAll(quantityRanges);

                capability.setAttributes(attributes);
                res.add(capability);
            }
        }

        return res;
    }

    /**
     * 获取procedure的characteristic信息
     */
    public List<Characteristic> getCharacteristicById(String procedureId) {
        List<Characteristic> res = new ArrayList<>();
        List<Characteristic> characteristics = characteristicMapper.selectByProcedureId(procedureId);
        if (characteristics!=null && characteristics.size()>0) {
            List<Object> attributes = new ArrayList<>();
            for (Characteristic characteristic:characteristics) {
                //获取Category信息
                List<Category> categories = categoryMapper.selectByOutId(characteristic.getOutId());
                attributes.addAll(categories);
                //获取Text信息
                List<Text> texts = textMapper.selectByOutId(characteristic.getOutId());
                attributes.addAll(texts);
                //获取Vector信息
                List<Vector> vectors = vectorMapper.selectByOutId(characteristic.getOutId());
                attributes.addAll(vectors);
                //获取Quantity信息
                List<Quantity> quantities = quantityMapper.selectByOutId(characteristic.getOutId());
                attributes.addAll(quantities);
                //获取QuantityRange信息
                List<QuantityRange> quantityRanges = quantityRangeMapper.selectByOutId(characteristic.getOutId());
                attributes.addAll(quantityRanges);
                characteristic.setAttributes(attributes);
                res.add(characteristic);
            }
        }
        return res;
    }

    /**
     * 获取procedure的classification信息
     */
    public List<Classifier> getClassificationById(String procedureId) {
        return classifierMapper.selectByProcedureId(procedureId);
    }

    /**
     * 获取procedure的Identification信息
     */
    public List<Identifier> getIdentificationById(String procedureId) {
        return identifierMapper.selectByProcedureId(procedureId);
    }

    /**
     * 获取procedure的keyword信息
     */
    public Keyword getKeywordsById(String procedureId) {
        Keyword res = new Keyword();
        List<String> keywords = keywordMapper.selectByProcedureId(procedureId);
        if (keywords!=null && keywords.size()>0) {
            res.setProcedureId(procedureId);
            res.setValues(keywords);
        }
        return res;
    }

    /**
     * 通过filePath获取到SensorML内容
     * @param filePath
     * @return
     */
    public String getProcedureContentById(String filePath) {
        Map<String, Object> res = new HashMap<>();
        if (StringUtils.isBlank(filePath)) {
            res.put("content", "");
        }
        res.put("content", DataCenterUtils.readFromFile(filePath));
        return JSONObject.toJSONString(res);
    }

    /**
     * 通过关键字查询procedureId
     * @param keyword
     * @return 含有该keyword的procedureId
     */
    public List<String> getProcedureIdByKeyword(String keyword) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        return keywordMapper.selectByValue(keyword);
    }

    /**
     * 根据时间validTime查询procedureId
     * @param begin
     * @param end
     * @return id
     */
    public List<String> getProcedureIdByValidTime(Instant begin, Instant end) {
        List<String> res = new ArrayList<>();
        List<ValidTime> validTimes = validTimeMapper.selectByTime(begin, end);
        if (validTimes!=null && validTimes.size()>0) {
            for (ValidTime validTime:validTimes) {
                res.add(validTime.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过位置名称查询procedureId(模糊查询)
     * @param name
     * @return id
     */
    public List<String> getProcedureIdByPosition(String name) {
        List<String> res = new ArrayList<>();
        List<Position> positions = positionMapper.selectByName(name);
        if (positions!=null && positions.size()>0) {
            for (Position position:positions) {
                res.add(position.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过包围盒查询procedureId
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @return id
     */
    public List<String> getSensorByPosition(double minX, double minY, double maxX, double maxY) {
        List<String> res = new ArrayList<>();
        List<Position> positions = positionMapper.selectByEnvelope(minX, minY, maxX, maxY);
        if (positions!=null && positions.size()>0) {
            for (Position position:positions) {
                res.add(position.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 获取系统内所有的procedureId
     * @return id
     */
    public List<String> getAllProcedureId() {
        return procedureMapper.selectAllProcedureIds();
    }

    /**
     * 获取系统内所有的传感器Id
     * @return id
     */
    public List<String> getAllSensorIds() {
        return procedureMapper.selectAllSensorIds();
    }

    /**
     * 获取系统内所有的传感器平台Id
     * @return id
     */
    public List<String> getAllPlatformIds() {
        return procedureMapper.selectAllPlatformIds();
    }

    /**
     * 通过Identification节点信息，查询相关内容,比如可以查询传感器所属平台名称，id等信息，只需指定label即可
     * @param procedureId
     * @return name
     */
    public String getValueByIdenLabelAndProcedureId(String procedureId, String label) {
        Identifier identifier = identifierMapper.selectByLabelAndProcedureId(label, procedureId);
        return identifier !=null ? identifier.getValue():"";
    }

    /**
     * 通过Identification节点信息，查询相关内容，比如可以查询到shortName为***的所有传感器
     * @param label
     * @return procedureId
     */
    public List<String> getProcedureIdByIdenLabelAndValue(String label, String value) {
        List<String> res = new ArrayList<>();
        List<Identifier> identifiers = identifierMapper.selectByLabelAndValue(label, value);
        if (identifiers!=null && identifiers.size()>0) {
            for (Identifier identifier:identifiers) {
                res.add(identifier.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过个体名称查询相关的procedureId
     * @param individualName
     * @return id
     */
    public List<String> getProcedureIdByIndividualName(String individualName) {
        List<String> res = new ArrayList<>();
        List<Contact> contacts = contactMapper.selectByIndividualName(individualName);
        if (contacts!=null && contacts.size()>0) {
            for (Contact contact:contacts) {
                res.add(contact.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过组织名称查询相关的procedureId
     * @param organizationName
     * @return id
     */
    public List<String> getSensorByOrganizationName(String organizationName) {
        List<String> res = new ArrayList<>();
        List<Contact> contacts = contactMapper.selectByIndividualName(organizationName);
        if (contacts!=null && contacts.size()>0) {
            for (Contact contact:contacts) {
                res.add(contact.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过站点名称查询相关的procedureId
     * @param positionName
     * @return id
     */
    public List<String> getSensorByPositionName(String positionName) {
        List<String> res = new ArrayList<>();
        List<Contact> contacts = contactMapper.selectByIndividualName(positionName);
        if (contacts!=null && contacts.size()>0) {
            for (Contact contact:contacts) {
                res.add(contact.getProcedureId());
            }
        }
        return res;
    }


    /**
     * 通过查询classification节点获取相关信息，比如可以通过预期应用名称查询procedureId
     * @param label
     * @return id
     */
    public List<String> getProcedureIdByClaLabelAndValue(String label, String value) {
        List<String> res = new ArrayList<>();
        List<Classifier> classifiers = classifierMapper.selectByLabelAndValue(label, value);
        if (classifiers !=null && classifiers.size()>0) {
            for (Classifier classifier : classifiers) {
                res.add(classifier.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 通过传感器类型名称查询传感器
     * @param label
     * @return id
     */
    public String getValueByClaLabelAndProcedureId(String label, String procedureId) {
        Classifier classifier = classifierMapper.selectByLabelAndProcedureId(label, procedureId);
        return classifier!=null ? classifier.getValue():"";
    }

    /**
     * 通过包围盒查询procedureId
     * @return
     */
    public List<String> getProcedureIdByEnvelope(double minLon, double minLat, double maxLon, double maxLat) {
        List<String> res = new ArrayList<>();
        List<Position> positions = positionMapper.selectByEnvelope(minLon, minLat, maxLon, maxLat);
        if (positions!=null && positions.size()>0) {
            for (Position position:positions) {
                res.add(position.getProcedureId());
            }
        }
        return res;
    }

    /**
     * 判断该id是否已存在
     * @param procedureId
     * @return
     */
    public boolean isExist(String procedureId) {
        return procedureMapper.isExist(procedureId);
    }

    /**
     * 通过平台查传感器
     * @param platformId
     * @return
     */
    public List<String> getComponentByPlatformId(String platformId) {
        List<String> res = new ArrayList<>();
        List<Component> components = componentMapper.selectByPlatformId(platformId);
        if (components.size()>0) {
            for (Component component : components) {
                res.add(component.getHref());
            }
        }
        return res;
    }

    /**
     * 判断是否为平台还是传感器
     * @param procedureId
     * @return
     */
    public boolean isPlatform(String procedureId) {
        Procedure procedure = procedureMapper.selectById(procedureId);
        return procedure.getIsPlatform()==1;
    }

    /**
     * 获取目录树,full
     * @return
     */
    public List<Platform> getTOC() {
        List<Platform> res = new ArrayList<>();
        List<String> platformIds = getAllPlatformIds();
        if (platformIds!=null && platformIds.size()>0) {
            for (String platformId:platformIds) {
                res.add(getPlatformInfoById123(platformId));
            }
        }
        return res;
    }



    public List<Platform> getPartOfTOC123(List<String> platformId) {
        List<Platform> res = new ArrayList<>();
        for(String id :platformId){
            res.add(getPlatformInfoById(id));
        }
        return res;
    }
}
