package com.sensorweb.sossensorservice.service;

import com.sensorweb.sossensorservice.dao.ProcedureMapper;
import lombok.extern.slf4j.Slf4j;
import net.opengis.fes.v20.*;
import net.opengis.fes.v20.impl.*;
import net.opengis.ows.v11.Domain;
import net.opengis.ows.v11.impl.DomainImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vast.ows.*;
import org.vast.ows.sos.SOSCapabilitiesWriterV20;
import org.vast.ows.sos.SOSOfferingCapabilities;
import org.vast.ows.sos.SOSServiceCapabilities;
import org.vast.ows.sos.SOSUtils;
import org.vast.util.ResponsibleParty;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.util.*;

@Service
@Slf4j
public class GetCapabilitiesService {

    @Autowired
    private ProcedureMapper procedureMapper;

    /**
     * 解析GetCapabilitiesRequest请求，获取请求参数，并封装成GetCapabilitiesRequest对象返回
     * @param requestContent
     * @return
     * @throws DOMHelperException
     * @throws OWSException
     */
    public GetCapabilitiesRequest getGetCapabilitiesRequest(String requestContent) throws DOMHelperException, OWSException {
        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(requestContent.getBytes()), false);
        GetCapabilitiesReader reader = new GetCapabilitiesReader();
        return reader.readXMLQuery(domHelper, domHelper.getRootElement());
    }

    /**
     * 根据解析的请求参数，自动生成GetCapabilitiesResponse文档内容，以Element对象返回
     * @param request
     * @return
     * @throws OWSException
     */
    public Element getGetCapabilitiesResponse(GetCapabilitiesRequest request) throws OWSException {
        SOSServiceCapabilities sosServiceCapabilities = new SOSServiceCapabilities();
        SOSUtils.loadRegistry();

        Set<String> contents = request.getSections();
        if (contents!=null && contents.size()>0) {
            if (contents.contains("ServiceIdentification")) {
                setServiceIdentification(sosServiceCapabilities);
            }
            if (contents.contains("ServiceProvider")) {
                setServiceProvider(sosServiceCapabilities);
            }
            if (contents.contains("OperationsMetadata")) {
                setOperationsMetadata(sosServiceCapabilities);
            }
            if (contents.contains("FilterCapabilities")) {
                setFilterCapabilities(sosServiceCapabilities);
            }
            if (contents.contains("Contents")) {
                setContents(sosServiceCapabilities);
            }
        } else {
            return null;
        }

        SOSCapabilitiesWriterV20 writer = new SOSCapabilitiesWriterV20();
        DOMHelper domHelper = new DOMHelper();

        return writer.buildXMLResponse(domHelper, sosServiceCapabilities, "2.0");
    }

    /**
     * 设置GetCapabilities中的ServiceIdentification部分
     * @param sosServiceCapabilities
     * @return
     */
    public SOSServiceCapabilities setServiceIdentification(SOSServiceCapabilities sosServiceCapabilities) {
        sosServiceCapabilities.setFees("null");
        sosServiceCapabilities.setService("SOS");
        sosServiceCapabilities.setAccessConstraints("unknown constraints");
        sosServiceCapabilities.setVersion("2.0");

        OWSIdentification owsIdentification = new OWSIdentification();
        owsIdentification.setTitle("传感网实验室自研SOS服务");
        owsIdentification.setIdentifier("sensorweb");
        owsIdentification.setDescription("主要为了实验室团队后续的数据管理而研发的服务中心");
        owsIdentification.getKeywords().add("sos");
        owsIdentification.getKeywords().add("sensorweb");
        owsIdentification.getKeywords().add("datacenter");
        owsIdentification.getMetadata().add("unknown");
        sosServiceCapabilities.setIdentification(owsIdentification);

        return sosServiceCapabilities;
    }

    /**
     * 设置GetCapabilities中的ServiceProvider部分
     * @param sosServiceCapabilities
     * @return
     */
    public SOSServiceCapabilities setServiceProvider(SOSServiceCapabilities sosServiceCapabilities) {
        OWSIdentification owsIdentification = new OWSIdentification();
        owsIdentification.setTitle("传感网实验室自研SOS服务");
        owsIdentification.setIdentifier("sensorweb");
        owsIdentification.setDescription("主要为了实验室团队后续的数据管理而研发的服务中心");
        owsIdentification.getKeywords().add("sos");
        owsIdentification.getKeywords().add("sensorweb");
        owsIdentification.getKeywords().add("datacenter");
        owsIdentification.getMetadata().add("unknown");
        sosServiceCapabilities.setIdentification(owsIdentification);

        ResponsibleParty responsibleParty = new ResponsibleParty();
        responsibleParty.setAdministrativeArea("湖北省");
        responsibleParty.setCity("武汉市");
        responsibleParty.setCountry("中国");
        responsibleParty.setEmail("xxxxxx@cug.edu.cn");
        responsibleParty.setFaxNumber("12300000000");
        responsibleParty.setPositionName("中国地质大学");
        responsibleParty.setOrganizationName("地理与信息工程学院");
        responsibleParty.setPostalCode("123000");
        responsibleParty.setRole("admin");
        sosServiceCapabilities.setServiceProvider(responsibleParty);

        return sosServiceCapabilities;
    }

    /**
     * 设置GetCapabilities中的OperationsMetadata部分
     * @param sosServiceCapabilities
     * @return
     */
    public SOSServiceCapabilities setOperationsMetadata(SOSServiceCapabilities sosServiceCapabilities) {
        Map<String, String> getServers = new HashMap<>();
        Map<String, String> postServers = new HashMap<>();
        postServers.put("InsertSensor", "http://localhost:8080/datacenter/services/InsertSensor");
        postServers.put("DescribeSensor", "http://localhost:8080/datacenter/services/DescribeSensor");
        postServers.put("InsertObservation", "http://localhost:8080/datacenter/services/InsertObservation");
        postServers.put("GetObservation", "http://localhost:8080/datacenter/services/GetObservation");
        postServers.put("DeleteObservation", "http://localhost:8080/datacenter/services/DeleteObservation");
        postServers.put("DeleteSensor", "http://localhost:8080/datacenter/services/DeleteSensor");
        postServers.put("GetCapabilities", "http://localhost:8080/datacenter/services/GetCapabilities");
        sosServiceCapabilities.setGetServers(getServers);
        sosServiceCapabilities.setPostServers(postServers);

        return sosServiceCapabilities;
    }

    /**
     * 设置GetCapabilities中的FilterCapabilities部分
     * @param sosServiceCapabilities
     * @return
     */
    public SOSServiceCapabilities setFilterCapabilities(SOSServiceCapabilities sosServiceCapabilities) {
        FilterCapabilities filterCapabilities = new FilterCapabilitiesImpl();

        Conformance conformance = new ConformanceImpl();
        Domain domain = new DomainImpl();
        domain.setName("ImplementsAdHocQuery");
        domain.setNoValues(true);
        conformance.addConstraint(domain);
        filterCapabilities.setConformance(conformance);

        ScalarCapabilities scalarCapabilities = new ScalarCapabilitiesImpl();
        scalarCapabilities.setLogicalOperators(true);
        filterCapabilities.setScalarCapabilities(scalarCapabilities);

        SpatialCapabilities spatialCapabilities = new SpatialCapabilitiesImpl();
        QName qName = new QName("http://www.opengis.net/gml/3.2", "ns:Envelope");
        spatialCapabilities.getGeometryOperands().add(qName);
        SpatialOperator spatialOperator = new SpatialOperatorImpl();
        spatialOperator.setName(SpatialOperatorName.BBOX);
        spatialCapabilities.getSpatialOperators().add(spatialOperator);
        spatialCapabilities.getGeometryOperands().add(qName);
        filterCapabilities.setSpatialCapabilities(spatialCapabilities);

        TemporalCapabilities temporalCapabilities = new TemporalCapabilitiesImpl();
        QName qName1 = new QName("http://www.opengis.net/gml/3.2", "ns:TimeInstant");
        temporalCapabilities.getTemporalOperands().add(qName1);
        TemporalOperator temporalOperator = new TemporalOperatorImpl();
        temporalOperator.setName(TemporalOperatorName.AFTER);
        temporalCapabilities.getTemporalOperators().add(temporalOperator);
        filterCapabilities.setTemporalCapabilities(temporalCapabilities);

        sosServiceCapabilities.setFilterCapabilities(filterCapabilities);
        return sosServiceCapabilities;
    }

    /**
     * 设置GetCapabilities中的Contents部分
     * @param sosServiceCapabilities
     * @return
     */
    public SOSServiceCapabilities setContents(SOSServiceCapabilities sosServiceCapabilities) {
//        List<Offering> offerings = offeringMapper.getAll();
//        if (offerings!=null && offerings.size()>0) {
        SOSOfferingCapabilities offeringCapabilities = new SOSOfferingCapabilities();
//
//            for (Offering offering : offerings) {
//                String offeringId = offering.getId();
//                offeringCapabilities.setIdentifier(offeringId);
//                offeringCapabilities.setTitle(offering.getName());
//                offeringCapabilities.getObservableProperties().addAll(DataCenterUtils.string2List(offering.getObservableProperty()));

        //根据offeringId查ProcedureId，进而获取procedure相关信息
//                Procedure procedure = procedureMapper.selectById(offering.getProcedureId());
//                if (procedure!=null) {
//                    offeringCapabilities.getProcedures().add(procedure.getId());
//                    offeringCapabilities.getProcedureFormats().add(procedure.getDescriptionFormat());
//                }

        //根据offeringId查Phenomenon
//                List<PhenOff> phenOffs = phenOffMapper.getPhenOff(offeringId);
//                if (phenOffs!=null && phenOffs.size()>0) {
//                    for (PhenOff phenOff : phenOffs) {
//                        String phenomenonId = phenOff.getPhenomenonId();
//                        Phenomenon phenomenon = phenomenonMapper.getPhenomenon(phenomenonId);
//                        offeringCapabilities.setPhenomenonTime(phenomenon);
//
//                    }
//                }

        //根据offeringId查featureOfInterest
//                List<FoiOff> foiOffs = foiOffMapper.getFoiOff(offeringId);
//                if (foiOffs!=null && foiOffs.size()>0) {
//                    for (FoiOff foiOff : foiOffs) {
//                        String foiId = foiOff.getFoiId();
//                        FeatureOfInterest featureOfInterest = foiMapper.selectById(foiId);
//                        offeringCapabilities.getFoiTypes().add(featureOfInterest.getName());
//                    }
//                }

//                offeringCapabilities.getResponseFormats().add("application/json");
//                offeringCapabilities.getResponseFormats().add("http://www.opengis.net/om/2.0");
//                offeringCapabilities.getResponseFormats().add("http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement");
//            }
//            sosServiceCapabilities.getLayers().add(offeringCapabilities);
//        }
        return null;
    }
}
