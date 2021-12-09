package com.sensorweb.datacenterutil.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Element;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class DataCenterUtils {




    public Instant str2Instant(String time) {
//        String time = "2021-09-21 00:00:00";
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    /**
     * 通过Http下载文件，适用于不需要授权的情况下
     * @param url
     * @param dir
     * @param fileName
     */
    public static void downloadHttpUrl(String url, String dir, String fileName) {
        try {
            URL httpUrl = new URL(url);
            File dirFile = new File(dir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            FileUtils.copyURLToFile(httpUrl, new File(dir+"/"+fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送Get请求
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String doGet(String url, String param) throws IOException {
        //打开postman
        //这一步相当于运行main方法。
        //创建request连接 3、填写url和请求方式
        HttpGet get = new HttpGet(url + "?" + param);

        //如果有参数添加参数 get请求不需要参数，省略
        CloseableHttpClient client = HttpClients.createDefault();
        //点击发送按钮，发送请求、获取响应报文
        CloseableHttpResponse response = client.execute(get);
        //格式化响应报文
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity);
    }



    public static void sendMessage(String id, String type, String details) throws Exception {
        String url = "http://172.16.100.2:9999/ai-sensing-back-service/AI_Sensing_Back/api/ws/dps";
        String param = "id="+URLEncoder.encode(id,"utf-8")+"&type="+URLEncoder.encode(type,"utf-8")+"&details="+URLEncoder.encode(details,"utf-8")+"&progress=100";
//       Map<String ,Object> param = new HashMap<>();
//
//       param.put("id",URLEncoder.encode(id,"utf-8"));
//       param.put("type",URLEncoder.encode(type,"utf-8"));
//        param.put("details",URLEncoder.encode(details,"utf-8"));
//        param.put("progress",100);
//        String document = okHttpUtil.doGet(url, param);
        String document =  doGet(url,param);
        System.out.println("发送给前端的数据是 = " + document);
    }



    /**
     * 发送Post请求
     * @param url
     * @param request xml请求文档
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String doPost(String url, String request) throws ParseException, IOException{
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","application/xml");
        CloseableHttpClient client = HttpClients.createDefault();
        httpPost.setEntity(new StringEntity(request,"UTF-8"));
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    /**
     * 去除重复元素
     * @param lists
     * @return
     */
    public static List<String> removeDuplicate(List<List<String>> lists) {
        List<String> res = new ArrayList<>();
        if (lists!=null && lists.size()>0) {
            res = lists.get(0);
            for (int i = 0; i < lists.size(); i++) {
                res.retainAll(lists.get(i));
            }
        }
        return res;
    }

    /**
     * 去掉字符串的后缀
     * @param suffix
     * @param str
     * @return
     */
    public static String removeSuffix(String suffix, String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        return str.substring(0, str.indexOf(suffix));
    }

    /**
     * 分割=两边的值
     * @param str
     * @return
     */
    public static String splitEqula(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] temp = str.split("=");
        return temp[1].trim();
    }

    /**
     * 解析SWE数据模型字符串，转换为K-V形式
     * @param str
     * @return
     */
    public static Map<String, String> string2Map(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        Map<String, String> res = new HashMap<>();

        if (str.startsWith("DataRecord")) {
            String[] temp = str.split("\n");
            for (int i = 1; i< temp.length; i++) {
                String[] kvValue = temp[i].split(":");
                if (kvValue.length>1) {
                    res.put(kvValue[0].trim(), splitEqula(kvValue[1].trim()));
                }
            }
        } else if (str.startsWith("Text")) {
            String[] temp = str.split("=");
            res.put(temp[0].trim(), temp[1].trim());
        } else if (str.startsWith("POINT")) {
            String temp = str.substring(str.indexOf("("));
            //StringUtils.strip();去掉字符串两边的字符
            String[] xy = StringUtils.strip(temp,"()").split(" ");
            if (xy.length>1) {
                res.put("Lon", xy[0]);
                res.put("Lat", xy[1]);
            }
        } else if (str.startsWith("Vector")) {
            String[] temp = str.split("\n");
            for (int i = 1; i< temp.length; i = i+2) {
                res.put(temp[i].trim().substring(0, temp[i].trim().indexOf(":")), splitEqula(temp[i+1]));
            }
        }

        return res;
    }

    /**
     * 讲列表内的字符串信息拼成一个字符串，以英文空格“ ”分割
     * @param list
     * @return
     */
    public static String list2String(List<String> list) {
        StringBuilder result = new StringBuilder();

        if (list!=null && list.size()>0) {
            for (String temp : list) {
                result.append(temp);
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

    public static List<String> string2List(String str) {
        List<String> res = new ArrayList<>();

        if(!StringUtils.isBlank(str)) {
            String[] temp = str.split("|");
            res = Arrays.asList(temp);
        }

        return res;
    }

    /**
     * Instant转string
     * @param time
     * @return
     */
    public static String instant2String(Instant time) {
        Date tmpDate=Date.from(time);

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(tmpDate);
    }

    /**
     * UTC时间字符串转Instant时间对象
     * @param utc
     * @return
     */
    public static Instant utc2Instant(String utc) {
        if (StringUtils.isBlank(utc)) {
            return null;
        }
        return Instant.parse(utc);
    }

    /**
     * Instant转LocalDateTime
     * @param instant
     * @return
     */
    public static LocalDateTime instant2LocalDateTime(Instant instant) {
        Set<String> ss = ZoneId.getAvailableZoneIds();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

    public static Instant string2Instant(String date) {
        Date time = null;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (time!=null) {
            return time.toInstant();
        }
        return null;
    }



    /**
     * 获取月初时间
     */
    public static String getFirstDay(Calendar calendar) {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String firstDay;
        // 获取前月的第一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstDay = format.format(calendar.getTime());
        return firstDay+" "+"00:00:00";
    }


    /**
     * 获取月末时间
     */
    public static String getLastDay(Calendar calendar) {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String lastDay;
        // 获取前月的第一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        lastDay = format.format(calendar.getTime());
        return lastDay+" "+"00:00:00";
    }



    /**
     * 字符串转LocalDateTime
     * @param time
     * @return
     * @throws ParseException
     */
    public static LocalDateTime string2LocalDateTime(String time) throws ParseException {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }


    public static LocalDateTime string2LocalDateTime3(String time) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }


    /**
     * 字符串转LocalDateTime
     * @param time
     * @return
     * @throws ParseException
     */
    public static LocalDateTime string2LocalDateTime2(String time) throws ParseException {
        //处理类似2020/9/17格式的时间字符串为2020-09-17
        String[] dateTemp = time.split(" ");
        String[] date = dateTemp[0].split("/");
        int year = Integer.valueOf(date[0]);
        int month = Integer.valueOf(date[1]);
        int day = Integer.valueOf(date[2]);
        String[] timeTemp = dateTemp[1].split(":");
        int hour = Integer.valueOf(timeTemp[0]);
        int minute = Integer.valueOf(timeTemp[1]);
        int second = Integer.valueOf(timeTemp[2]);
        time = year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);

        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }

    /**
     * 读取文件内容
     * @param filePath
     * @return
     */
    public static String readFromFile(String filePath) {
        File file = new File(filePath);
        FileInputStream fis = null;
        StringBuilder stringBuilder = null;

        try {
            if (file.length()!=0) {
                fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                stringBuilder = new StringBuilder();
                while ((line=bufferedReader.readLine())!=null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                isr.close();
                fis.close();
            } else {
                System.out.println("file is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串内容写到文件
     * @param filePath
     * @param content
     */
    public static boolean write2File(String filePath, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(content);
            writer.flush();
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 从MultipartFile读取文件内容
     * @param file
     * @return
     */
    public static String readFromMultipartFile(MultipartFile file) throws IOException {
        StringBuilder stringBuilder = null;
        try {
            if (file.getSize()>0) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                stringBuilder = new StringBuilder();
                while ((line=bufferedReader.readLine())!=null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } else {
                System.out.println("file is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 生成随机字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");

    }

    /**
     * MD5加密
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * Element转字符串
     * @param element
     * @return
     */
    public static String element2String(Element element) {
        String str = "";
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(element);
            StreamResult result = new StreamResult(new StringWriter());
            transformer.transform(domSource,result);
            str = result.getWriter().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 给矩形框的四个角坐标，生成对应的wkt
     * @return
     */
    public static String coodinate2Wkt(double minX, double minY, double maxX, double maxY) {
        StringBuilder stringBuilder = new StringBuilder("POLYGON(");
        stringBuilder.append(minX);
        stringBuilder.append(" ");
        stringBuilder.append(minY);
        stringBuilder.append(",");
        stringBuilder.append(minX);
        stringBuilder.append(" ");
        stringBuilder.append(maxY);
        stringBuilder.append(",");
        stringBuilder.append(maxX);
        stringBuilder.append(" ");
        stringBuilder.append(minY);
        stringBuilder.append(",");
        stringBuilder.append(maxX);
        stringBuilder.append(" ");
        stringBuilder.append(maxY);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    /**
     * 将bean中的字段和属性值写入到list中
     * @param bean
     * @return
     */
    public static List<Object> bean2List(Object bean) {
        List<Object> res = new ArrayList<>();
        Field[] fields = bean.getClass().getDeclaredFields();
        if (fields.length>0) {
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                if(!accessible){
                    field.setAccessible(true);
                }
                try {
                    res.add(field.get(bean));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                field.setAccessible(accessible);
            }
        }
        return res;
    }

    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    /**
    * 获取系统当前时间，并转为string
     */

//    public static String getTimeNow(){
//        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
//        sd.applyPattern("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();// 获取当前时间
//        System.out.println("现在时间：" + sd.format(date));
//        String time = sd.format(date);
//        String time1 = time.substring(0, time.indexOf(":"));
//        String timeNow = time1 + ":00:00";
////        Instant timeNow = DataCenterUtils.string2LocalDateTime3(time2).atZone(ZoneId.of("GMT+8")).toInstant();
//        return  timeNow;
//    }



}
