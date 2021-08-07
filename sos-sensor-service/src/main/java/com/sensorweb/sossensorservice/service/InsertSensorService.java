package com.sensorweb.sossensorservice.service;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.dao.*;
import com.sensorweb.sossensorservice.entity.*;
import com.sensorweb.sossensorservice.entity.Category;
import com.sensorweb.sossensorservice.entity.Quantity;
import com.sensorweb.sossensorservice.entity.QuantityRange;
import com.sensorweb.sossensorservice.entity.Text;
import com.sensorweb.sossensorservice.entity.Vector;
import lombok.extern.slf4j.Slf4j;
import net.opengis.OgcProperty;
import net.opengis.OgcPropertyList;
import net.opengis.sensorml.v20.*;
import net.opengis.swe.v20.*;
import org.apache.commons.lang3.StringUtils;
import org.isotc211.v2005.gmd.CIAddress;
import org.isotc211.v2005.gmd.CIResponsibleParty;
import org.isotc211.v2005.gmd.CITelephone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vast.ows.OWSException;
import org.vast.ows.sos.InsertSensorReaderV20;
import org.vast.ows.sos.InsertSensorRequest;
import org.vast.ows.sos.InsertSensorResponse;
import org.vast.ows.sos.InsertSensorWriterV20;
import org.vast.ows.swe.InsertSensorResponseWriterV20;
import org.vast.util.TimeExtent;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class InsertSensorService {

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

    @Value("${datacenter.path.sensorml}")
    private String uploadPath;


    /**
     * 生成InsertSensor响应文档
     * @param procedureId
     * @param
     * @return
     */
    public Element getInsertSensorResponse(String procedureId) throws OWSException {
        InsertSensorResponse response = new InsertSensorResponse();
        response.setAssignedOffering("");
        response.setAssignedProcedureId(procedureId);

        InsertSensorResponseWriterV20 writer = new InsertSensorResponseWriterV20();
        DOMHelper domHelper = new DOMHelper();

        return writer.buildXMLResponse(domHelper, response, "2.0");
    }

    /**
     * 解析InsertSensor请求参数，返回InsertSensorRequest对象，该对象包含sensorML的所有信息，后续的数据存储就是操作此对象
     * @param requestContent
     * @return
     * @throws IOException
     * @throws OWSException
     */
    public InsertSensorRequest getInsertSensorRequest(String requestContent) throws IOException, OWSException {
        if (StringUtils.isBlank(requestContent)) {
            return null;
        }
        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(requestContent.getBytes()),false);
        InsertSensorReaderV20 insertSensorReader = new InsertSensorReaderV20();
        return insertSensorReader.readXMLQuery(domHelper, domHelper.getRootElement());
    }

    /**
     * 解析InsertSensor请求文档，将其中的sensorml内容写入文件保存，返回文件路径
     * @param insertSensorRequest
     * @return
     * @throws OWSException
     */
    public String getSensorML(InsertSensorRequest insertSensorRequest) throws OWSException {
        DOMHelper domHelper = new DOMHelper();
        InsertSensorWriterV20 insertSensorWriter = new InsertSensorWriterV20();
        Element rootElement = insertSensorWriter.buildXMLQuery(domHelper, insertSensorRequest);
        NodeList nodeList = rootElement.getElementsByTagName("swes:procedureDescription");
        Node node = nodeList.item(0).getFirstChild();

        //写入文件
        String fileName = DataCenterUtils.generateUUID() + ".xml";
        try {
            DataCenterUtils.write2File(uploadPath + fileName, DataCenterUtils.element2String((Element) node));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadPath + fileName;
    }

    /**
     * 存储数据
     * @return
     * @throws ParseException
     * @throws XMLStreamException
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String insertSensor(InsertSensorRequest insertSensorRequest) throws Exception {
        String result = "";

        //add identification
        List<Identifier> identifiers = getIdentification(insertSensorRequest);
        if (identifiers !=null && identifiers.size()>0) {
            for (Identifier identifier : identifiers) {
                identifierMapper.insertData(identifier);
            }
        }

        //add classification
        List<Classifier> classifiers = getClassification(insertSensorRequest);
        if (classifiers !=null && classifiers.size()>0) {
            for (Classifier classifier : classifiers) {
                classifierMapper.insertData(classifier);
            }
        }

        //add characteristic
        List<Characteristic> characteristics = getCharacteristic(insertSensorRequest);
        if (characteristics!=null && characteristics.size()>0) {
            for (Characteristic characteristic : characteristics) {
                characteristicMapper.insertData(characteristic);
            }
        }

        //add capability
        List<Capability> capabilities = getCapability(insertSensorRequest);
        if (capabilities!=null && capabilities.size()>0) {
            for (Capability capability : capabilities) {
                capabilityMapper.insertData(capability);
            }
        }

        //add validTime
        ValidTime validTime = getValidTime(insertSensorRequest);
        if (validTime!=null) {
            validTimeMapper.insertData(validTime);
        }

        //add contact
        List<Contact> contacts = getContact(insertSensorRequest);
        if (contacts!=null && contacts.size()>0) {
            for (Contact contact : contacts) {
                contactMapper.insertData(contact);
            }
        }

        //add position
        List<Position> positions = getPosition(insertSensorRequest);
        if (positions!=null && positions.size()>0) {
            for (Position position : positions) {
                positionMapper.insertData(position);
            }
        }

        //add keywords
        Keyword keyword = getKeyword(insertSensorRequest);
        if (keyword!=null) {
            keywordMapper.insertData(keyword);
        }

        //add component(如果是传感器平台的话)
        //判断是否为平台还是传感器
        if (isPlatform(insertSensorRequest)) {
            List<Component> components = getComponents(insertSensorRequest);
            if (components!=null && components.size()>0) {
                for (Component component : components) {
                    componentMapper.insertData(component);
                }
            }
        }

        //add procedure
        Procedure procedure = getProcedure(insertSensorRequest);
        if (procedure!=null) {
            int flag = procedureMapper.insertData(procedure);
            if (flag>0) {
                result = procedure.getId();
                //传感器插入成功，更新平台component内容;如果是平台则跳过
                if (!isPlatform(insertSensorRequest)) {
                    String procedureId = getPlatformId(result);
                    Procedure platform = procedureMapper.selectById(procedureId);
                    if (platform!=null) {
                        Component component = new Component();
//                            component.setName(procedure.getName());
                        component.setTitle(procedure.getName());
                        component.setHref(procedure.getId());
                        component.setPlatformId(procedureId);
                        componentMapper.insertData(component);
                        flushComponent(procedure.getName(), procedure.getId(), platform.getDescriptionFile());
                    } else {
                        System.out.println("平台不存在");
                    }
                }
            } else {
                new File(procedure.getDescriptionFile()).delete();
                System.out.println("add failure");
            }
        }

        return result;
    }

    /**
     * 解析Request请求，获取关键字信息
     * @param insertSensorRequest
     * @return
     */
    public Keyword getKeyword(InsertSensorRequest insertSensorRequest) {
        Keyword keywords = null;
        String identifier = insertSensorRequest.getProcedureDescription().getUniqueIdentifier();

        OgcPropertyList<KeywordList> keywordLists = insertSensorRequest.getProcedureDescription().getKeywordsList();
        if (keywordLists!=null && keywordLists.size()>0) {
            keywords = new Keyword();
            keywords.setProcedureId(identifier);
            List<String> keywordTemp = keywordLists.get(0).getKeywordList();
            if (keywordTemp!=null && keywordTemp.size()>0) {
                keywords.setValues(keywordTemp);
            }
        }
        return keywords;
    }

    /**
     * 解析InsertSensorRequest请求，获取传感器基本信息，并以Procedure对象返回
     * @param insertSensorRequest
     * @return
     * @throws OWSException
     */
    public Procedure getProcedure(InsertSensorRequest insertSensorRequest) throws OWSException {
        Procedure procedure = null;

        if (insertSensorRequest!=null && insertSensorRequest.getProcedureDescription()!=null) {
            procedure = new Procedure();
            procedure.setId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
            procedure.setDescription(insertSensorRequest.getProcedureDescription().getDescription());
            procedure.setDescriptionFile(getSensorML(insertSensorRequest));
            procedure.setName(insertSensorRequest.getProcedureDescription().getName());
            procedure.setStatus(1);

            //判断是否为平台还是传感器
            if (insertSensorRequest.getProcedureDescription().getQName().getLocalPart().equals("PhysicalSystem")) {
                procedure.setIsPlatform(1);
            } else {
                procedure.setIsPlatform(0);
            }
        }
        return procedure;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Identification对象集合
     * @param
     * @return
     */
    public List<Identifier> getIdentification(InsertSensorRequest insertSensorRequest) {
        List<Identifier> result = new ArrayList<>();

        OgcPropertyList<IdentifierList> identifierLists = insertSensorRequest.getProcedureDescription().getIdentificationList();
        if (identifierLists!=null && identifierLists.size()>0) {
            List<Term> identifiers = identifierLists.get(0).getIdentifierList();
            if (identifiers!=null && identifiers.size()>0) {
                for (int i = 0; i<identifiers.size(); i++) {
                    Identifier identifier = new Identifier();
                    identifier.setDefinition(identifiers.get(i).getDefinition());
                    identifier.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                    identifier.setLabel(identifiers.get(i).getLabel());
                    identifier.setValue(identifiers.get(i).getValue());

                    result.add(identifier);
                }
            }
        }

        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Classification对象集合
     * @param
     * @return
     */
    public List<Classifier> getClassification(InsertSensorRequest insertSensorRequest) {
        List<Classifier> result = new ArrayList<>();

        OgcPropertyList<ClassifierList> classifierLists = insertSensorRequest.getProcedureDescription().getClassificationList();
        if (classifierLists!=null && classifierLists.size()>0) {
            List<Term> terms = classifierLists.get(0).getClassifierList();
            if (terms!=null && terms.size()>0) {
                for (int i=0; i<terms.size(); i++) {
                    Classifier classifier = new Classifier();
                    classifier.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                    classifier.setDefinition(terms.get(i).getDefinition());
                    classifier.setLabel(terms.get(i).getLabel());
                    classifier.setValue(terms.get(i).getValue());

                    result.add(classifier);
                }
            }
        }
        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义ValidTime对象集合
     * @param
     * @return
     * @throws ParseException
     */
    public ValidTime getValidTime(InsertSensorRequest insertSensorRequest) throws ParseException {
        ValidTime result = null;

        TimeExtent timeExtent = insertSensorRequest.getProcedureDescription().getValidTime();
        if (timeExtent!=null) {
            result = new ValidTime();
            result.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
            if (timeExtent.begin()!=null) {

                result.setBeginPosition(timeExtent.begin());
            }
            if (timeExtent.end()!=null) {
                result.setEndPosition(timeExtent.end());
            }
        }

        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Capability对象集合
     * @param
     * @return
     */
    public List<Capability> getCapability(InsertSensorRequest insertSensorRequest) {
        List<Capability> result = new ArrayList<>();

        OgcPropertyList<CapabilityList> capabilityLists = insertSensorRequest.getProcedureDescription().getCapabilitiesList();
        if (capabilityLists!=null && capabilityLists.size()>0) {
            for (int i=0; i<capabilityLists.size(); i++) {
                CapabilityList temp = capabilityLists.get(i);
                OgcPropertyList<DataComponent> capabilityList = temp.getCapabilityList();
                Set<String> capNames = capabilityList.getPropertyNames();
                for (String capName : capNames) {
                    if (!capName.equals("offerings")) {
                        OgcProperty<DataComponent> components = capabilityList.getProperty(capName);
                        int num = components.getValue().getComponentCount();
                        for (int j=0; j<num; j++) {
                            DataComponent capability = components.getValue().getComponent(j);
                            Capability temp1 = new Capability();
                            temp1.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                            temp1.setName(capability.getName());
                            capability.getData().getStringValue();

                            temp1.setLabel(capability.getLabel());
                            result.add(temp1);

                            //解析DataComponent,返回集合对象
                            parseAndSaveDataRecord(capability, temp1.getProcedureId() + ":capability:" + temp1.getName());
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Characteristic对象集合
     * @param
     * @return
     */
    public List<Characteristic> getCharacteristic(InsertSensorRequest insertSensorRequest) {
        List<Characteristic> result = new ArrayList<>();

        OgcPropertyList<CharacteristicList> characteristicLists = insertSensorRequest.getProcedureDescription().getCharacteristicsList();
        if (characteristicLists!=null && characteristicLists.size()>0) {
            for (int i=0; i<characteristicLists.size(); i++) {
                CharacteristicList temp = characteristicLists.get(i);
                OgcPropertyList<DataComponent> characteristicList = temp.getCharacteristicList();
                Set<String> chaNames = characteristicList.getPropertyNames();
                for (String chaName:chaNames) {
                    OgcProperty<DataComponent> components = characteristicList.getProperty(chaName);
                    int num = components.getValue().getComponentCount();
                    for (int j=0; j<num; j++) {
                        DataComponent characteristic = components.getValue().getComponent(j);
                        Characteristic temp1 = new Characteristic();
                        temp1.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                        temp1.setName(characteristic.getName());
//                        characteristic.getData().getStringValue();
                        temp1.setLabel(characteristic.getLabel());
                        result.add(temp1);

                        //解析DataComponent,返回集合对象
                        parseAndSaveDataRecord(characteristic, temp1.getProcedureId() + ":characteristic:" + temp1.getName());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Contact对象集合
     * @param
     * @return
     */
    public List<Contact> getContact(InsertSensorRequest insertSensorRequest) {
        List<Contact> result = new ArrayList<>();

        OgcPropertyList<ContactList> contactLists = insertSensorRequest.getProcedureDescription().getContactsList();
        if (contactLists!=null && contactLists.size()>0) {
            OgcPropertyList<CIResponsibleParty> contactList = contactLists.get(0).getContactList();
            if (contactList!=null) {
                List<OgcProperty<CIResponsibleParty>> contacts =contactList.getProperties();

                for (OgcProperty<CIResponsibleParty> contact : contacts) {
                    Contact contactTemp = new Contact();
                    contactTemp.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());

                    if (!StringUtils.isBlank(contact.getValue().getIndividualName())) {
                        contactTemp.setIndividualName(contact.getValue().getIndividualName());
                    }
                    if (!StringUtils.isBlank(contact.getValue().getOrganisationName())) {
                        contactTemp.setOrganizationName(contact.getValue().getOrganisationName());
                    }
                    if (!StringUtils.isBlank(contact.getValue().getPositionName())) {
                        contactTemp.setPositionName(contact.getValue().getPositionName());
                    }
                    //address info
                    CIAddress addressTemp = contact.getValue().getContactInfo().getAddress();
                    if (addressTemp!=null) {
                        if (!StringUtils.isBlank(addressTemp.getAdministrativeArea())) {
                            contactTemp.setAdminArea(addressTemp.getAdministrativeArea());
                        }
                        if (!StringUtils.isBlank(addressTemp.getCity())) {
                            contactTemp.setCity(addressTemp.getCity());
                        }
                        if (!StringUtils.isBlank(addressTemp.getCountry())) {
                            contactTemp.setCountry(addressTemp.getCountry());
                        }
                        if (!StringUtils.isBlank(addressTemp.getPostalCode())) {
                            contactTemp.setPostalCode(addressTemp.getPostalCode());
                        }
                        if (addressTemp.getDeliveryPointList()!=null && addressTemp.getDeliveryPointList().size()>0) {
                            contactTemp.setDeliveryPoint(DataCenterUtils.list2String(addressTemp.getDeliveryPointList()));
                        }
                        if (addressTemp.getElectronicMailAddressList()!=null && addressTemp.getElectronicMailAddressList().size()>0) {
                            contactTemp.setMail(DataCenterUtils.list2String(addressTemp.getElectronicMailAddressList()));
                        }
                        contactTemp.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                    }
                    //phone info
                    CITelephone phoneTemp = contact.getValue().getContactInfo().getPhone();
                    if (phoneTemp!=null) {
                        if (phoneTemp.getVoiceList()!=null && phoneTemp.getVoiceList().size()>0) {
                            contactTemp.setTelephone(DataCenterUtils.list2String(phoneTemp.getVoiceList()));
                        }
                    }
                    result.add(contactTemp);
                }
            }
        }

        return result;
    }

    /**
     * 解析Request对象，并加以处理，最后返回自定义Position对象集合
     * @param insertSensorRequest
     * @return
     */
    public List<Position> getPosition(InsertSensorRequest insertSensorRequest) {
        List<Position> res = new ArrayList<>();

        AbstractPhysicalProcess abstractProcess = (AbstractPhysicalProcess) insertSensorRequest.getProcedureDescription();
        OgcPropertyList<Serializable> positionList = abstractProcess.getPositionList();
        if (positionList!=null && positionList.size()>0) {
            for (int i=0; i<positionList.size(); i++) {
                Position temp = new Position();
                temp.setProcedureId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());
                Map<String, String> prop = DataCenterUtils.string2Map(positionList.getProperty(i).getValue().toString());
                Set<String> keys = prop.keySet();
                if (keys.contains("Text")) {
                    temp.setName(prop.get("Text"));
                }
                if (keys.contains("Lat")) {
                    temp.setLatitude(Double.parseDouble(prop.get("Lat")));
                }
                if (keys.contains("Lon")) {
                    temp.setLongitude(Double.parseDouble(prop.get("Lon")));
                }
                if (keys.contains("Alt")) {
                    temp.setAltitude(Double.parseDouble(prop.get("Alt")));
                }
                String wkt = "";
                if (temp.getWkt()!=null) {
                    wkt = "POINT M(" + temp.getLongitude() + " " + temp.getLatitude() + " " + temp.getAltitude() + ")";
                } else {
                    wkt = "POINT(" + temp.getLongitude() + " " + temp.getLatitude() + ")";
                }
                temp.setWkt(wkt);

                res.add(temp);
            }
        }
        return res;
    }

    /**
     * 对于传感器平台，解析Components节点（Component的个数即为该平台下的传感器数）
     * @param insertSensorRequest
     * @return
     */
    public List<Component> getComponents(InsertSensorRequest insertSensorRequest) {
        List<Component> res = new ArrayList<>();

        AggregateProcess aggregateProcess = (AggregateProcess) insertSensorRequest.getProcedureDescription();
        OgcPropertyList<AbstractProcess> components = aggregateProcess.getComponentList();
        List<OgcProperty<AbstractProcess>> temps = components.getProperties();
        if (temps!=null && temps.size()>0) {
            for (OgcProperty<AbstractProcess> temp : temps) {
                Component component = new Component();
                component.setName(temp.getName());
                component.setHref(temp.getHref());
                component.setTitle(temp.getTitle());
                component.setRole(temp.getRole());
                component.setPlatformId(insertSensorRequest.getProcedureDescription().getUniqueIdentifier());

                res.add(component);
            }
        }
        return res;
    }

    /**
     * 每当传感器注册成功，实时刷新传感器平台建模文档（添加Component节点）
     * @param title
     * @param href
     * @param filePath 传感器平台的Procedure
     * @throws DOMHelperException
     */
    public void flushComponent(String title, String href, String filePath) throws DOMHelperException {
        filePath = uploadPath + filePath.substring(filePath.lastIndexOf("/") + 1);
        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(DataCenterUtils.readFromFile(filePath).getBytes()), false);
        Element element = domHelper.getElement("components/ComponentList");
        if (element==null) {
            element = domHelper.getElement("/");
            Element temp0 = domHelper.getParentDocument(element).getDocument().createElement("sml:components");
            Element temp1 = domHelper.getParentDocument(temp0).getDocument().createElement("sml:ComponentList");
            temp0.appendChild(temp1);
            element.appendChild(temp0);
            element = domHelper.getElement(element, "components/ComponentList");
        }
        Element temp = domHelper.getParentDocument(element).getDocument().createElement("sml:component");
        if (!StringUtils.isBlank(title) || !StringUtils.isBlank(href))
            temp.setAttribute("title", StringUtils.isBlank(title) ? "" : title);
        temp.setAttribute("href",StringUtils.isBlank(href) ? "" : href);
        domHelper.removeAllText(element);
        element.appendChild(temp);
        DataCenterUtils.write2File(filePath, DataCenterUtils.element2String(domHelper.getRootElement()));
    }

    /**
     * 通过传感器id查询所属的平台id
     * @param procedureId
     * @return
     */
    public String getPlatformId(String procedureId) {
        Identifier identifier = identifierMapper.selectByLabelAndProcedureId("所属平台标识符", procedureId);
        return identifier !=null ? identifier.getValue():"";
    }

    /**
     * 判断是否为平台还是传感器
     * @param insertSensorRequest
     * @return
     */
    public boolean isPlatform(InsertSensorRequest insertSensorRequest) {
        return insertSensorRequest.getProcedureDescription().getQName().getLocalPart().equals("PhysicalSystem");
    }

    /**
     * 解析DataRecord节点，将字段数据类型封装成对应的类型对象返回
     * @param dataComponent
     * @return
     */
    public void parseAndSaveDataRecord(DataComponent dataComponent, String outId) {
        if (dataComponent!=null) {
            if (dataComponent instanceof net.opengis.swe.v20.Category) {
                Category temp = new Category();
                net.opengis.swe.v20.Category category = (net.opengis.swe.v20.Category) dataComponent;
                temp.setName(category.getName());
                temp.setValue(category.getValue());
                temp.setOutId(outId);
                categoryMapper.insertData(temp);
            } else if (dataComponent instanceof net.opengis.swe.v20.Quantity) {
                net.opengis.swe.v20.Quantity quantity = (net.opengis.swe.v20.Quantity) dataComponent;
                Quantity temp = new Quantity();
                temp.setName(quantity.getName());
                temp.setValue(quantity.getValue());
                temp.setOutId(outId);
                quantityMapper.insertData(temp);
            } else if (dataComponent instanceof net.opengis.swe.v20.QuantityRange) {
                net.opengis.swe.v20.QuantityRange quantityRange = (net.opengis.swe.v20.QuantityRange) dataComponent;
                QuantityRange temp = new QuantityRange();
                temp.setName(quantityRange.getName());
                temp.setMinValue(quantityRange.getValue()[0]);
                temp.setMaxValue(quantityRange.getValue()[1]);
                temp.setOutId(outId);
                quantityRangeMapper.insertData(temp);
            } else if (dataComponent instanceof net.opengis.swe.v20.Text) {
                net.opengis.swe.v20.Text text = (net.opengis.swe.v20.Text) dataComponent;
                Text temp = new Text();
                temp.setName(text.getName());
                temp.setValue(text.getValue());
                temp.setOutId(outId);
                textMapper.insertData(temp);
            } else if (dataComponent instanceof net.opengis.swe.v20.Vector) {
                net.opengis.swe.v20.Vector vector = (net.opengis.swe.v20.Vector) dataComponent;
                Vector temp = new Vector();
                temp.setName(vector.getName());
                temp.setOutId(outId);
                OgcPropertyList<ScalarComponent> coordinateList = vector.getCoordinateList();
                for (ScalarComponent component : coordinateList) {
                    if (component.getName().equals("Lon")) {
                        temp.setLongitude(component.getData().getDoubleValue());
                    } else if (component.getName().equals("Lat")) {
                        temp.setLatitude(component.getData().getDoubleValue());
                    } else if (component.getName().equals("Alt")) {
                        temp.setAltitude(component.getData().getDoubleValue());
                    }
                }
                //处理wkt
                /*......*/
                vectorMapper.insertData(temp);
            }
        }
    }
}
