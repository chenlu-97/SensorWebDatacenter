package com.sensorweb.sosobsservice.service;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sosobsservice.dao.ObservationMapper;
import com.sensorweb.sosobsservice.entity.Observation;
import com.sensorweb.sosobsservice.feign.SensorFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vast.ogc.om.IObservation;
import org.vast.ogc.om.OMUtils;
import org.vast.ows.OWSException;
import org.vast.ows.sos.InsertObservationReaderV20;
import org.vast.ows.sos.InsertObservationRequest;
import org.vast.ows.sos.InsertObservationResponse;
import org.vast.ows.sos.InsertObservationResponseWriterV20;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.vast.xml.XMLWriterException;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Service
public class InsertObservationService {

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private SensorFeignClient sensorFeignClient;

    /**
     * 根据返回的插入成功的观测id，自动生成InsertObservationResponse
     * @param obsIds
     * @return
     * @throws OWSException
     */
    public Element getInsertObservationResponse(List<String> obsIds) throws OWSException {
        InsertObservationResponseWriterV20 writer = new InsertObservationResponseWriterV20();
        InsertObservationResponse response = new InsertObservationResponse();
        response.setObsId(DataCenterUtils.list2String(obsIds));
        DOMHelper domHelper = new DOMHelper();

        return writer.buildXMLResponse(domHelper, response, "2.0");
    }

    /**
     * 通过InsertObservation请求，将Observation插入到数据库中，返回插入成功的Observation的id
     * @param iObservation
     * @return
     * @throws ParseException
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertObservation(IObservation iObservation) throws Exception {
        Observation observation = getObservation(iObservation);

        //插入观测数据
        if (sensorFeignClient.isExist(observation.getProcedureId())) {
//            observationMapper.insertData(observation);
            //OM注册逻辑修改
        } else {
            log.info("This procedure is not exist");
            throw new Exception("This procedure is not exist");
        }

    }

    /**
     * 解析InsertObservation请求文档，将其中的om内容以字符串形式返回
     * @param observation
     * @return
     * @throws XMLWriterException
     */
    public String getOM(IObservation observation) throws XMLWriterException {
        OMUtils omUtils = new OMUtils(OMUtils.V2_0);
        DOMHelper domHelper = new DOMHelper();
        Element element = omUtils.writeObservation(domHelper, observation, "2.0");

        return DataCenterUtils.element2String(element);
    }

    /**
     * 解析InsertObservation请求字符串信息，返回请求对象InsertObservationRequest
     * @param requestContent
     * @return
     * @throws DOMHelperException
     * @throws OWSException
     */
    public InsertObservationRequest getInsertObservationRequest(String requestContent) throws DOMHelperException, OWSException {
        if (StringUtils.isBlank(requestContent)) {
            return null;
        }
        DOMHelper domHelper = new DOMHelper(new ByteArrayInputStream(requestContent.getBytes()), false);
        InsertObservationReaderV20 reader = new InsertObservationReaderV20();

        return reader.readXMLQuery(domHelper, domHelper.getRootElement());
    }

    /**
     * 解析InsertObservationRequestion请求对象，获取Observation集合
     * @param request
     * @return
     */
    public List<IObservation> getObservation(InsertObservationRequest request) {
        if (request==null) {
            return null;
        } else {
            return request.getObservations();
        }
    }

    /**
     * 解析IObservation对象，获取观测结果
     * @param iObservation
     * @return
     * @throws ParseException
     */
    public Observation getObservation(IObservation iObservation) {
        Observation observation = new Observation();
        if (iObservation!=null) {
            observation.setDescription(iObservation.getDescription());
            observation.setProcedureId(iObservation.getProcedure().getUniqueIdentifier());
            observation.setObsTime(iObservation.getResultTime());
            observation.setObsProperty(iObservation.getObservedProperty().getHref());
            observation.setType(iObservation.getType());
        }
        return observation;
    }

    /**
     * 插入自定义Observation对象数据
     */
    public int insertObservationData(Observation observation) {
        return observationMapper.insertData(observation);
    }

    /**
     * 批量插入自定义Observation对象数据
     */
    public int insertObservationDataBatch(List<Observation> observations) {
        return observationMapper.insertDataBatch(observations);
    }




    public boolean insertBeforeSelectWHSpa(String wkt) {return observationMapper.selectWHSpa(wkt); }
    public boolean insertBeforeSelectCJSpa(String wkt) {
        return observationMapper.selectCJSpa(wkt);
    }
    public boolean insertBeforeSelectCHSpa(String wkt) {
        return observationMapper.selectCHSpa(wkt);
    }

}
