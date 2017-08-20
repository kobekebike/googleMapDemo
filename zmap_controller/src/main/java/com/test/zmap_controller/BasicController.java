package com.test.zmap_controller;

import com.ld.constant.Constant;
import com.ld.model.SystemEnv;
import com.ld.model.SystemEnvCriteria;
import com.ld.service.SystemEnvService;
import com.ld.utils.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ld.constant.Constant.*;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

public class BasicController {
    public static final Logger log = Logger.getLogger(BasicController.class);


    private static String REQUEST_URL;

    protected void writeJsonUsingPager(List beans, long totalRecord, HttpServletResponse response) {
        writeJsonUsingPager(beans, totalRecord, response, DateUtils.DEFAULT_LONGDATE_FORMAT);
    }

    protected void writeJsonUsingPager(List beans, long totalRecord, HttpServletResponse response, String dateFormat) {
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(dateFormat));
        try {

            JSONArray arr = JSONArray.fromObject(beans, config);
            String val = "{total:" + totalRecord + ",rows:" + arr.toString() + "}";
            writeJson(val, response);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    protected int getLimitStart(int page, int rows) {
        return (page - 1) * rows;
    }

    protected int getLimitEnd(int rows) {
        return rows;
    }

    protected void writeJson(Object obj, HttpServletResponse response) {
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(DateUtils.DEFAULT_LONGDATE_FORMAT));
        writeJsonStr(JSONObject.fromObject(obj, config).toString(), response);
    }

    protected void writeJsonArray(Object obj, HttpServletResponse response) {
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(DateUtils.DEFAULT_LONGDATE_FORMAT));
        writeJsonStr(JSONArray.fromObject(obj, config).toString(), response);
    }

    protected void writeJsonStr(String text, HttpServletResponse response) {
        WriteResult.writeJsonStr(text, response);
    }

    protected void writeStr(String text, HttpServletResponse response) {
        try {
            Writer writer = response.getWriter();
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    protected void writeHtmlStr(String html, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            Writer writer = response.getWriter();
            writer.write(html);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    protected void writeBasicResult(HttpServletResponse response, boolean result, String message) {
        HashMap<String, String> rlt = new HashMap();
        rlt.put("result", result ? "true" : "false");
        rlt.put("message", message);
        writeJson(rlt, response);
    }

    /**
     * 获取请求url
     *
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request) {
        if (REQUEST_URL == null) {
            StringBuffer url = new StringBuffer();
            String scheme = request.getScheme();//返回当前链接使用的协议；比如，一般应用返回http;SSL返回https
            int port = request.getServerPort();

            url.append(scheme);        // http, https
            url.append("://");
            url.append(request.getServerName());
            if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
                url.append(':');
                url.append(request.getServerPort());
                url.append(request.getContextPath());
            }

            if (REQUEST_URL == null)
                REQUEST_URL = url.toString();
        }
        System.out.println("REQUEST_URL:" + REQUEST_URL);
        return REQUEST_URL;
    }

    /**
     * 解析参数，如果前端传递"空"的数据来，根据待解析的类型放回相应的值
     *
     * @param request
     * @param paramName
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getParameter(HttpServletRequest request, String paramName, Class<T> tClass) {
        String name = request.getParameter(paramName);

        T resT = null;
        try {
            if (Number.class.isAssignableFrom(tClass)) {
                if (StringUtils.isBlank(name)) {
                    resT = null;
                } else {
                    resT = (T) ObjectUtils.newInstance(tClass.getName(), new Object[]{name});
                }
            }
            if (String.class.isAssignableFrom(tClass)) {
                resT = StringUtils.isBlank(name) ? null : (T) name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resT;
    }

    /**
     * 发送html邮件，首先通过freemarker生成content，然后将内容发送出去
     *
     * @param servletContext
     * @param mapData        模型数据
     * @param templateName   模板名称
     * @param sysmEnvService
     * @param toAddress      收件人邮箱
     * @param subject        邮件主题
     * @return
     */
    public static boolean sendHtmlMail(ServletContext servletContext, Map<String, Object> mapData, String templateName,
                                       SystemEnvService sysmEnvService, String toAddress, String subject) {
        FreeMarkerUtil freeMarkerUtil = FreeMarkerUtil.newInstance(servletContext);
        String content = freeMarkerUtil.writeToString(mapData, templateName);
        MailSenderInfo mailSenderInfo = getMailSenderInfo(sysmEnvService, toAddress, subject, content);
        return MailSenderUtil.sendHtmlMail(mailSenderInfo);
    }

    /**
     * 获取待发送的邮件信息
     *
     * @param sysmEnvService
     * @param toAddress      收件人地址
     * @param subject        邮件主题
     * @param content        邮件内容
     * @return
     */
    private static MailSenderInfo getMailSenderInfo(SystemEnvService sysmEnvService, String toAddress, String subject, String content) {
        // 获取发送者的相关信息
        SystemEnvCriteria criteria = new SystemEnvCriteria();
        criteria.createCriteria().andKeyIn(Arrays.asList(MAIL_SERNDER_ADDRESS, MAIL_SENDER_ACCOUNT, MAIL_SERNDER_PASSWORD));
        List<SystemEnv> systemEnvList = sysmEnvService.selectByExample(criteria);

        MailSenderInfo mailSenderInfo = new MailSenderInfo();
        if (systemEnvList != null && systemEnvList.size() == 3) {
            for (SystemEnv systemEnv : systemEnvList) {
                String key = systemEnv.getKey();
                String value = systemEnv.getValue();
                if (key.equalsIgnoreCase(MAIL_SERNDER_ADDRESS)) {
                    mailSenderInfo.setFromAddress(value);
                } else if (key.equalsIgnoreCase(MAIL_SENDER_ACCOUNT)) {
                    mailSenderInfo.setUserName(value);
                } else if (key.equalsIgnoreCase(MAIL_SERNDER_PASSWORD)) {
                    mailSenderInfo.setPassword(value);
                }
            }
        } else {
            throw new RuntimeException("系统环境表中缺少发送者信息");
        }
        // 填充用户填填写的信息
        mailSenderInfo.setToAddress(toAddress);
        mailSenderInfo.setSubject(subject);
        mailSenderInfo.setContent(content);
        return mailSenderInfo;
    }
    
    //删除文件
    public void deleteFile(String filePath){
    	try{
    		String homePath = System.getProperty("ld_home");
			if(StringUtils.isBlank(homePath)){
				homePath = Constant.LD_HOME;
			}
			boolean flag = FileUtil.deleteFile(homePath + filePath);
			if(flag){
				System.err.println("删除成功");
			}else{
				System.err.println("删除失败");
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    /**
     * 功能：获取请求的真实ip地址
     * @param request
     * @return
     */
    public String getRealIpAddress(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getRemoteAddr();
		}
		return ip;
    }
    
    
    /**
     * 为字符增加空格符:合同最下方有个甲方和乙方,为甲方加空格,为了格式不乱
     * @return
     */
    public String addSpace(String str, int num){
    	int leng = str.length();
    	String space = "";
    	for(int i = 0; i < (num - leng*2); i++){
    		space += " ";
    	}
    	return str + "<w:t xml:space='preserve'>" + space + "</w:t>";
    }
    /**
     * 处理公司名:用于模板:合同最下方有个甲方和乙方,当甲方的公司名称超过15个字时,会在第二行显示
     * 
     * 甲方的最大长度:
     * 服务合同: 35
     * 顾问合同: 30
     * 协作合同: 32
     * 项目合同: 38
     * 
     */
    public String[] handleCompanyName(String companyName, int type){
    	int leng = companyName.length();
    	String[] arr = new String[2];
    	if(leng < 16){
    		if(type == 1){
    			arr[0]=addSpace(companyName,35);
    			arr[1]="<w:t xml:space='preserve'></w:t>";
    		}else if(type == 2){
    			arr[0]=addSpace(companyName,31);
    			arr[1]="<w:t xml:space='preserve'></w:t>";
    		}else if(type == 3){
    			arr[0]=addSpace(companyName,32);
    			arr[1]="<w:t xml:space='preserve'></w:t>";
    		}else if(type == 4){
    			arr[0]=addSpace(companyName,38);
    			arr[1]="<w:t xml:space='preserve'></w:t>";
    		}
    	}else{
    		if(type == 1){
    			arr[0]=addSpace(companyName.substring(0,15).trim(),35);
    			arr[1]=companyName.substring(15,leng).trim();
    		}else if(type == 2){
    			arr[0]=addSpace(companyName.substring(0,15).trim(),31);
    			arr[1]=companyName.substring(15,leng).trim();
    		}else if(type == 3){
    			arr[0]=addSpace(companyName.substring(0,15).trim(),32);
    			arr[1]=companyName.substring(15,leng).trim();
    		}else if(type == 4){
    			arr[0]=addSpace(companyName.substring(0,15).trim(),38);
    			arr[1]=companyName.substring(15,leng).trim();
    		}
    	}
    	return arr;
    }
    
   
}
