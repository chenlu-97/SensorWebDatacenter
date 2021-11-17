package com.sensorweb.datacenterlaadsservice.service;

import com.sensorweb.datacenterlaadsservice.dao.EntryMapper;
import com.sensorweb.datacenterlaadsservice.entity.*;
import com.sensorweb.datacenterlaadsservice.feign.ObsFeignClient;
import com.sensorweb.datacenterlaadsservice.feign.SensorFeignClient;
import com.sensorweb.datacenterlaadsservice.util.LAADSConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class InsertLAADSService implements LAADSConstant {

    @Autowired
    private EntryMapper entryMapper;

    @Autowired
    private ObsFeignClient obsFeignClient;

    @Autowired
    private SensorFeignClient sensorFeignClient;

    @Value("${datacenter.path.laads}")
    private String filePath;

    @Scheduled(cron = "00 30 00 * * ?")//每天的00：30分执行一次
    public void insertDataByDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        String stop = format.format(calendar.getTime());
        calendar.add(Calendar.DATE,-1);
        String start = format.format(calendar.getTime()).replace("00:00:00", "23:59:59");
        String bbox = "90.55,24.5,112.417,34.75";//长江流域经纬度范围
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    List<SatelliteInstrument> satelliteInstruments = getSatelliteInstruments();
//                    if (satelliteInstruments!=null && satelliteInstruments.size()>0) {
//                        for (SatelliteInstrument satelliteInstrument:satelliteInstruments) {
                            //通过接口内容可知AM1M卫星生产Terra MODIS, PM1M卫星生产Aqua MODIS, AMPM卫星生产Combined Aqua和Terra MODIS
                            //对于AMPM卫星,由于目前无法确定那些产品事Terra MODIS,所以暂时只接入AM1M和PM1M卫星的数据
//                            if (satelliteInstrument.getName().equals("AM1M") || satelliteInstrument.getName().equals("PM1M")) {
                        String[] products = new String[] {"MOD11A1","MOD11A2","MYD11A1","MOD13A2","MCD12Q1","MCD19A2"};
                        for (String product:products) {
                            boolean flag = insertData2("MODIS", start, stop, bbox, product);
                            if (flag) {
                                log.info("LAADS接入时间: " + calendar.getTime().toString() + "Status: Success");
                                System.out.println("LAADS接入时间: " + calendar.getTime().toString() + "Status: Success");
//                                    }
//                                }

//                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("LAADS接入时间: " + calendar.getTime().toString() + "Status: Fail");
                    System.out.println("LAADS接入时间: " + calendar.getTime().toString() + "Status: Fail");
                }
            }
        }).start();
    }


    @Scheduled(cron = "00 10 00 * * ?")//每天的00：10分执行一次
    public void insertData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
//        String time = format.format(calendar.getTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        String time = null;
                        if(entryMapper.selectNew()!=null){
                            time = entryMapper.selectNew().getStart().plusSeconds(24*60*60).toString();
                        }
//                        boolean flag = insertData3(time.substring(0, time.indexOf("T")).replace("-",""));
                        boolean flag = insertData3("20210901");
                        if (flag) {
                            log.info("MERRA2接入时间: " + calendar.getTime().toString() + "Status: Success");
                            System.out.println("MERRA2接入时间: " + calendar.getTime().toString() + "Status: Success");
                        }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.error("MERRA2接入时间: " + calendar.getTime().toString() + "Status: Fail");
                    System.out.println("MERRA2接入时间: " + calendar.getTime().toString() + "Status: Fail");
                }
            }
        }).start();
    }

    /**
     * 通过API接口获取Modis数据信息
     * @param product
     * @param collection
     * @param start
     * @param stop
     * @param bbox
     * @throws IOException
     * @return 请求响应文档
     */
    public String getInfoByOpenSearch(String product, int collection, String start, String stop, String bbox) throws IOException {
        String param = "product=" + URLEncoder.encode(product, "utf-8") + "&collection=" + URLEncoder.encode(String.valueOf(collection), "utf-8") +
                "&start=" + URLEncoder.encode(start, "utf-8") + "&stop=" + URLEncoder.encode(stop, "utf-8") +
                "&bbox=" + URLEncoder.encode(bbox, "utf-8");
        return DataCenterUtils.doGet(LAADSConstant.LAADS_Web_Service + "/getOpenSearch", param);
    }

    /**
     * 获取OpenSearch响应文档中的totalResult
     */
    public int getTotalResult(String str) throws DocumentException {
        int res = 0;
        Document document = DocumentHelper.parseText(str);
        Element root = document.getRootElement();
        res =Integer.parseInt(((Element) root.elements("opensearch:totalResults")).getText());
        return res;
    }

    /**
     * 获取OpenSearch响应文档中的startIndex
     */
    public int getStartIndex(String str) throws DocumentException {
        int res = 0;
        Document document = DocumentHelper.parseText(str);
        Element root = document.getRootElement();
        res =Integer.parseInt(((Element) root.elements("opensearch:startIndex")).getText());
        return res;
    }

    /**
     * 获取OpenSearch响应文档中的itemsPerPage
     */
    public int getItemsPerPage(String str) throws DocumentException {
        int res = 0;
        Document document = DocumentHelper.parseText(str);
        Element root = document.getRootElement();
        res =Integer.parseInt(((Element) root.elements("opensearch:itemsPerPage")).getText());
        return res;
    }

    /**
     * 解析xml文档，获取Entry对象
     * @param document
     * @return
     * @throws DocumentException
     */
    public List<Entry> getEntryInfo(String document) throws DocumentException {
        //读取xml文档，返回Document对象
        Document xmlContent = DocumentHelper.parseText(document);
        Element root = xmlContent.getRootElement();
        List<Entry> entries = new ArrayList<>();
        for (Iterator i = root.elementIterator(); i.hasNext();) {
            Element element = (Element) i.next();
            if (element.getName().equals("entry")) {
                Entry temp = new Entry();
                for (Iterator j = element.elementIterator(); j.hasNext();) {
                    Element entryElement = (Element) j.next();
                    if (entryElement.getName().equals("id")) {
                        temp.setEntryId(entryElement.getText());
                        continue;
                    }
                    if (entryElement.getName().equals("title")) {
                        temp.setTitle(entryElement.getText());
                        continue;
                    }
                    if (entryElement.getName().equals("updated")) {
                        temp.setUpdated(entryElement.getText());
                        continue;
                    }
                    if (entryElement.getName().equals("link") && entryElement.attributeValue("rel").equals("http://esipfed.org/ns/fedsearch/1.0/data")) {
                        temp.setLink(entryElement.attributeValue("href"));
                        continue;
                    }
                    if (entryElement.getName().equals("start")) {
                        Instant instant = DataCenterUtils.utc2Instant(entryElement.getText());
                        temp.setStart(instant);
                        continue;
                    }
                    if (entryElement.getName().equals("stop")) {
                        if (!entryElement.getText().equals("Not Available")) {
                            Instant instant = DataCenterUtils.utc2Instant(entryElement.getText());
                            temp.setStop(instant);
                            continue;
                        }
                    }
                    if (entryElement.getName().equals("box")) {
                        String[] bboxes = entryElement.getText().split(",");
                        temp.setBbox(bboxes[0].trim() + " " + bboxes[1].trim() + "," + bboxes[2].trim() + " " + bboxes[3].trim());
                        continue;
                    }
                    if (entryElement.getName().equals("summary")) {
                        temp.setSummary(entryElement.getText());
                    }
                }
                if (!StringUtils.isBlank(temp.getBbox())) {
                    String[] corners = temp.getBbox().split(",");
                    String[] lowerCorner = corners[0].split(" ");
                    String[] upperCorner = corners[1].split(" ");
                    String wkt = "POLYGON((" + lowerCorner[0] + " " + lowerCorner[1] + "," + lowerCorner[0] + " " + upperCorner[1] + "," +
                            upperCorner[0] + " " + upperCorner[1] + "," + upperCorner[0] + " " + lowerCorner[1] + "," +
                            lowerCorner[0] + " " + lowerCorner[1] + "))";
                    temp.setWkt(wkt);
                }
                entries.add(temp);
            }
        }
        return entries;
    }

    /**
     * 通过url下载LAADS文件，需要Token授权的情况下
     * @param url
     * @param fileName
     * @param savePath
     */
    public String downloadFromUrl(String url, String fileName, String savePath) {
        String res = "";
        try {
//          HttpsUrlValidator.retrieveResponseFromServer(url);
            URL httpUrl = new URL(url);
//            SSLUtil.ignoreSsl();
            HttpURLConnection connection = (HttpURLConnection)httpUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
            connection.setRequestProperty("Authorization", LAADSConstant.LAADS_DOWNLOAD_TOKEN);

            //设置https协议访问
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
            InputStream inputStream = connection.getInputStream();
            byte[] getData = readInputStream(inputStream);
            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                boolean flag = saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            fos.close();
            inputStream.close();
            res = filePath + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 通过url下载LAADS文件，需要Token授权的情况下
     * @param url
     * @param fileName
     * @param savePath
     */
    public String downloadFromUrlWithProxy(String url, String fileName, String savePath) {
        String res = "";
        try {
//          HttpsUrlValidator.retrieveResponseFromServer(url);
            URL httpUrl = new URL(url);
//            SSLUtil.ignoreSsl();
            //创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 5210);
            //Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr); //SOCKS代理
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); //HTTP代理
            //其他方式可以见Proxy.Type属性
            HttpURLConnection connection = (HttpURLConnection)httpUrl.openConnection(proxy);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
            connection.setRequestProperty("Authorization", LAADSConstant.LAADS_DOWNLOAD_TOKEN);

            //设置https协议访问
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");
            InputStream inputStream = connection.getInputStream();
            byte[] getData = readInputStream(inputStream);
            // 文件保存位置
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                boolean flag = saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            fos.close();
            inputStream.close();
            res = filePath + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * 获取SatelliteInstrument，使用listSatelliteInstruments API接口
     */
    public List<SatelliteInstrument> getSatelliteInstruments() throws IOException, DocumentException {
        List<SatelliteInstrument> res = new ArrayList<>();
        String url = InsertLAADSService.LAADS_Web_Service + "/listSatelliteInstruments";
        String response = DataCenterUtils.doGet(url, "");
        Document document = DocumentHelper.parseText(response);
        Element root = document.getRootElement();
        List<Element> satelliteInstruments = root.elements("return");
        if (satelliteInstruments!=null && satelliteInstruments.size()>0) {
            for (Element satelliteInstrument:satelliteInstruments) {
                SatelliteInstrument temp = new SatelliteInstrument();
                String name = satelliteInstrument.element("name").getText();
                temp.setName(name);
                String value = satelliteInstrument.element("value").getText();
                temp.setValue(value);
                res.add(temp);
            }
        }
        return res;
    }

    /**
     * 获取Product，使用listProducts API接口
     */
    public List<LAADSProduct> getProducts() throws IOException, DocumentException {
        List<LAADSProduct> res = new ArrayList<>();
        String url = LAADSConstant.LAADS_Web_Service + "/listProducts";
        String response = DataCenterUtils.doGet(url, "");
        Document document = DocumentHelper.parseText(response);
        Element root = document.getRootElement();
        List<Element> products = root.elements("Product");
        if (products!=null && products.size()>0) {
            for (Element product:products) {
                LAADSProduct temp = new LAADSProduct();
                String name = product.element("Name").getText();
                String description = product.element("Description").getText();
                String defaultCollection = product.element("DefaultCollection").getText();
                temp.setName(name);
                temp.setDescription(description);
                temp.setDefaultCollection(defaultCollection);
                res.add(temp);
            }
        }
        return res;
    }

    /**
     * 获取Product，使用listProductsByInstrument API接口
     */
    public List<String> getProductsByInstrument(String instrument) throws Exception {
        List<String> res = new ArrayList<>();
        String url = LAADSConstant.LAADS_Web_Service + "/listProductsByInstrument";
        String param = "instrument=" + instrument;
        String response =  DataCenterUtils.doGet(url, param);
        Document document = DocumentHelper.parseText(response);
        Element root = document.getRootElement();
        List<Element> products = root.elements("return");
        if (products!=null && products.size()>0) {
            for (Element product:products) {
                res.add(product.getText());
            }
        }
        return res;
    }

    /**
     * 通过product获取Collections，使用getCollections API
     */
    public List<LAADSCollection> getCollectionsByProduct(String product) throws IOException, DocumentException {
        List<LAADSCollection> res = new ArrayList<>();
        String url = LAADSConstant.LAADS_Web_Service + "/getCollections";
        String param = "product=" + product;
        String response = DataCenterUtils.doGet(url, param);
        Document document = DocumentHelper.parseText(response);
        Element root = document.getRootElement();
        List<Element> collections = root.elements("Collection");
        if (collections!=null && collections.size()>0) {
            for (Element collection:collections) {
                String name = collection.element("Name").getText();
                String description = collection.element("Description").getText();
                LAADSCollection temp = new LAADSCollection();
                temp.setName(name);
                temp.setDescription(description);
                res.add(temp);
            }
        }
        return res;
    }

    /**
     * 数据接入，将数据存储到本地数据库，并将数据文件存储到本地
     * @param startTime "2020-11-08 00:00:00"
     * @param bbox "90.55,24.5,112.417,34.75"-->长江经济带
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertData(String satellite, String startTime, String endTime, String bbox, String productName) throws Exception {
        String procedureId = "urn:JMA:def:identifier:OGC:2.0:laads";
        String procedure = procedureId + ":" + satellite;
        String obsProperty = "";
        switch (satellite) {
            case "ANC": {
                obsProperty = "Ancillary Data";
                break;
            } case "AM1M": {
                obsProperty = "Terra MODIS";
                break;
            } case "PM1M": {
                obsProperty = "Aqua MODIS";
                break;
            } case "NPP": {
                obsProperty = "Suomi NPP VIIRS";
                break;
            } case "AMPM": {
                obsProperty = "Combined Aqua & Terra MODIS";
                break;
            }
        }
        List<String> products = getProductsByInstrument(satellite);
//        int index = 0;
        List<Observation> observations = new ArrayList<>();
        List<Entry> entryList = new ArrayList<>();
        if (products!=null && products.size()>0) {
            for (String product:products) {
                if (product.equals(productName)) {
                    List<LAADSCollection> collections = getCollectionsByProduct(product);
                    if (collections!=null && collections.size()>0) {
                        for (LAADSCollection collection:collections) {
                            String response = getInfoByOpenSearch(product, Integer.parseInt(collection.getName()), startTime, endTime, bbox);
                            List<Entry> entries = getEntryInfo(response);
                            if (entries.size()>0) {
                                for (Entry entry:entries) {
                                    if (!StringUtils.isBlank(entry.getLink())) {
                                        String fileName = entry.getLink().substring(entry.getLink().lastIndexOf("/") + 1);
                                        File file = new File(filePath);
                                        if (!file.exists()) {
                                            boolean flag = file.mkdirs();
                                        }
                                        String localPath = downloadFromUrl(entry.getLink(), fileName, filePath);
                                        entry.setFilePath(localPath);
                                        entry.setSatellite(satellite);
                                        entry.setProductType(product);
                                    }
                                    entryList.addAll(entries);
//                                    //远程文件下载到本地，并记录本地存储路径
//                                    Observation observation = new Observation();
//                                    observation.setProcedureId(procedure);
//                                    observation.setObsTime(entry.getStop());
//                                    observation.setBeginTime(entry.getStart());
//                                    observation.setEndTime(entry.getStop());
//                                    observation.setObsProperty(obsProperty);
//                                    observation.setType(product);
//                                    observation.setMapping("entry");
//                                    observation.setName(entry.getTitle());
//                                    observation.setBbox(entry.getBbox());
//                                    observation.setWkt(entry.getWkt());
//                                    observation.setOutId(entry.getId());
//                                    observations.add(observation);
//                                    boolean isWH = obsFeignClient.insertBeforeSelectWHSpa(entry.getWkt());
//                                    boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(entry.getWkt());
//                                    if(isWH==true&&isCJ==true){
//                                        observation.setGeoType(1);
//                                    }
//                                    else if(isWH==false&&isCJ==true){
//                                        observation.setGeoType(2);
//                                    }else{
//                                        observation.setGeoType(3);
//                                    }
                                }
                            }
                        }
                    }
                } else if (StringUtils.isBlank(productName)) {
                    List<LAADSCollection> collections = getCollectionsByProduct(product);
                    if (collections!=null && collections.size()>0) {
                        for (LAADSCollection collection:collections) {
//                        index++;
//                        System.out.println(index + ": " + product + ": " + collection.getName());
                            String response = getInfoByOpenSearch(product, Integer.parseInt(collection.getName()), startTime, endTime, bbox);
                            List<Entry> entries = getEntryInfo(response);
                            if (entries.size()>0) {
                                for (Entry entry:entries) {
                                    if (!StringUtils.isBlank(entry.getLink())) {
                                        String fileName = entry.getLink().substring(entry.getLink().lastIndexOf("/") + 1);
                                        File file = new File(filePath);
                                        if (!file.exists()) {
                                            boolean flag = file.mkdirs();
                                        }
                                        String localPath = downloadFromUrl(entry.getLink(), fileName, filePath);
                                        entry.setFilePath(localPath);
                                        entry.setSatellite(satellite);
                                        entry.setProductType(product);
                                    }
                                    entryList.addAll(entries);
//                                    //远程文件下载到本地，并记录本地存储路径
//                                    Observation observation = new Observation();
//                                    observation.setProcedureId(procedure);
//                                    observation.setObsTime(entry.getStop());
//                                    observation.setBeginTime(entry.getStart());
//                                    observation.setEndTime(entry.getStop());
//                                    observation.setObsProperty(obsProperty);
//                                    observation.setType(product);
//                                    observation.setMapping("entry");
//                                    observation.setName(entry.getTitle());
//                                    observation.setBbox(entry.getBbox());
//                                    observation.setWkt(entry.getWkt());
//                                    observation.setOutId(entry.getId());
//                                    boolean isWH = obsFeignClient.insertBeforeSelectWHSpa(entry.getWkt());
//                                    boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(entry.getWkt());
//                                    if(isWH==true&&isCJ==true){
//                                        observation.setGeoType(1);
//                                    }
//                                    else if(isWH==false&&isCJ==true){
//                                        observation.setGeoType(2);
//                                    }else{
//                                        observation.setGeoType(3);
//                                    }
//                                    observations.add(observation);
                                }
                            }
                        }
                    }
                }
            }
//            boolean flag = sensorFeignClient.isExist(procedureId);
//            if (flag) {
//                int status = obsFeignClient.insertDataBatch(observations);
//                if (status>0) {
//                    entryMapper.insertDataBatch(entryList);
//                }
//            } else {
//                log.info("procedure: " + procedure + "不存在");
//                throw new Exception("procedure: " + procedure + "不存在");
//            }
            entryMapper.insertDataBatch(entryList);
        }
        return true;
    }


    /**
     * 数据接入，将数据存储到本地数据库，并将数据文件存储到本地，（由于有些卫星和产品的页面访问会卡住，这边直接从产品的数据集页面下载数据）
     * @param startTime "2020-11-08 00:00:00"
     * @param bbox "90.55,24.5,112.417,34.75"-->长江经济带
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertData2(String satellite, String startTime, String endTime, String bbox, String productName) throws Exception {
        List<Entry> entryList = new ArrayList<>();
        List<LAADSCollection> collections = getCollectionsByProduct(productName);
        if (collections!=null && collections.size()>0) {
            for (LAADSCollection collection:collections) {
                String response = getInfoByOpenSearch(productName, Integer.parseInt(collection.getName()), startTime, endTime, bbox);
                List<Entry> entries = getEntryInfo(response);
                if (entries.size()>0) {
                    for (Entry entry:entries) {
                        if (!StringUtils.isBlank(entry.getLink())) {
                            String fileName = entry.getLink().substring(entry.getLink().lastIndexOf("/") + 1);
                            File file = new File(filePath);
                            if (!file.exists()) {
                                boolean flag = file.mkdirs();
                            }
                            String localPath = downloadFromUrl(entry.getLink(), fileName, filePath);
                            entry.setFilePath(localPath);
                            entry.setSatellite(satellite);
                            entry.setProductType(productName);
                        }
                        entryMapper.insertData(entry);
                    }
                }
            }
        }
        return true;
    }


    /**
     * 数据接入，将数据存储到本地数据库，并将数据文件存储到本地，MERRA2产品下载，由于和modis下载源头略微不同，但是可以相对的复用，测试用
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertData3( String time) throws Exception {
        List<Entry> entryList = new ArrayList<>();
        String fileName ="MERRA2_400.tavg1_2d_aer_Nx."+time+".nc4";
        String mounth = time.substring(4,6);
//        String downloadPath = LAADSConstant.MERRA2_Web_Service+"2021"+"/"+fileName;
        String downloadPath ="https://goldsmr4.gesdisc.eosdis.nasa.gov/data/MERRA2/M2T1NXAER.5.12.4/2021/"+ "/"+mounth+"/" + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            boolean flag = file.mkdirs();
        }

            Entry entry = new Entry();
            String localPath = downloadFromUrl(downloadPath, fileName, filePath);
        if(localPath != null) {
            entry.setFilePath(localPath);
            entry.setSatellite("MERRA2");
            entry.setProductType("MERRA2");
            entry.setEntryId(fileName);
            entry.setBbox("-180.0,-90.0,180.0,90.0");
            entry.setLink(downloadPath);
            entry.setStart(DataCenterUtils.string2Instant(time));
            entryList.add(entry);
            int i = entryMapper.insertDataBatch(entryList);
            return i > 0;
        }else{
            return false;
        }
    }

//    /**
//     * 数据接入，将数据存储到本地数据库，并将数据文件存储到本地，MERRA2产品下载，由于和modis下载源头略微不同，但是可以相对的复用，测试用
//     */
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//    public boolean insertData4( String time) throws Exception {
//        List<Entry> entryList = new ArrayList<>();
//        String fileName ="MERRA2_400.tavg1_2d_aer_Nx.20210922.nc4";
////        String downloadPath = LAADSConstant.MERRA2_Web_Service+"2021"+"/"+fileName;
//        String downloadPath ="https://goldsmr4.gesdisc.eosdis.nasa.gov/data/MERRA2/M2T1NXAER.5.12.4/2021/09/MERRA2_400.tavg1_2d_aer_Nx.20210922.nc4";
//        File file = new File(filePath);
//        if (!file.exists()) {
//            boolean flag = file.mkdirs();
//        }
//        Entry entry = new Entry();
//        String localPath = downloadFromUrlWithProxy(downloadPath, fileName, filePath);
//        entry.setFilePath(localPath);
//        entry.setSatellite("MERRA2");
//        entry.setProductType("MERRA2");
//        entryList.add(entry);
//        int i = entryMapper.insertDataBatch(entryList);
//        return i>0;
//    }

}
