package com.test.zmap_controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/view/zmap.do")
public class ZmapController extends BasicController {
	//测试使用，需要写成工具类
	@RequestMapping(params = "method=ajaxLatLngByAddress")
	public void ajaxLatLngByAddress(HttpServletRequest request,HttpServletResponse response1) {
		try {
			String result = "";
			String address = request.getParameter("address");
			if(StringUtils.isBlank(address)){  
				result = "输入地址为空";  
	        }else{
	        	CloseableHttpClient httpclient = HttpClients.createDefault();  
	        	try {    
	        		// 创建httpget.   
	        		HttpGet httpget = new HttpGet("https://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false&key=AIzaSyA3a5PwotknVyiN7de6eSZkxkLLpd8hm6k");
	        		// 执行get请求.      
	        		CloseableHttpResponse response = httpclient.execute(httpget);    
	        		try {    
	        			// 获取响应实体      
	        			HttpEntity entity = response.getEntity();    
	        			if (entity != null) {    
	        				// 打印响应内容      
	        				String str = EntityUtils.toString(entity,"utf-8");  
	        				JSONObject o = (JSONObject) JSON.parse(str);  
	        				JSONArray o2 = (JSONArray) o.get("results");  
	        				JSONObject o3 =  (JSONObject) o2.get(0);  
	        				JSONObject o4 = (JSONObject) o3.get("geometry");  
	        				JSONObject o5 = (JSONObject)o4.get("location");  
	        				result = "lat-->>"+o5.get("lat")+";lng-->>"+o5.get("lng");
	        				//System.err.println("lat====>>>"+o5.get("lat")+";lng=====>>>"+o5.get("lng"));  
	        			}    
	        		} finally {    
	        			response.close();    
	        		}    
	        	} catch (ClientProtocolException e) {    
	        		e.printStackTrace();  
	        	}catch (IOException e) {    
	        		e.printStackTrace();     
	        	} finally {    
	        		try {    
	        			httpclient.close();    
	        		} catch (IOException e) {    
	        			e.printStackTrace();      
	        		}    
	        	}
	        } 
			this.writeBasicResult(response1, true, result);
		}catch(Exception e) {
			this.writeBasicResult(response1, false, "获取失败");
		}
	}
	@RequestMapping(params = "method=ajaxAddressByLatLng")
	public void ajaxAddressByLatLng(HttpServletRequest request,HttpServletResponse response1) {
		try {
			//String address = this.getPara("address");
			String lng = request.getParameter("lng");
			String lat = request.getParameter("lat");
			String result = "";
			if(StringUtils.isBlank(lng) || StringUtils.isBlank(lat) ){  
				result = "经纬度为空";  
	        }else{
	        	CloseableHttpClient httpclient = HttpClients.createDefault();  
	        	try {              
	        		
	        		// 创建httpget.   ,参数还可以换成英文的。zh-CN 
	        		HttpGet httpget = new HttpGet("https://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false&&language=zh-CN&key=AIzaSyA3a5PwotknVyiN7de6eSZkxkLLpd8hm6k");    
	        		// 执行get请求.  
	        		CloseableHttpResponse response = httpclient.execute(httpget);    
	        		try {    
	        			// 获取响应实体      
	        			HttpEntity entity = response.getEntity();    
	        			// 打印响应状态      
	        			if (entity != null) {    
	        				// 打印响应内容      
	        				String str = EntityUtils.toString(entity);  
	        				JSONObject o = (JSONObject) JSON.parse(str);  
	        				JSONArray o1 = (JSONArray)o.get("results");  
	        				
	        				JSONObject o2 = (JSONObject)o1.get(0);  
	        				if(null != o2){  
	        					result = String.valueOf(o2.get("formatted_address"));  
	        				}  
	        			}  
	        		} finally {  
	        			response.close();    
	        		}    
	        	} catch (ClientProtocolException e) {    
	        		e.printStackTrace();     
	        	} catch (IOException e) {    
	        		e.printStackTrace();    
	        	} finally {    
	        		// 关闭连接,释放资源      
	        		try {    
	        			httpclient.close();    
	        		} catch (IOException e) {    
	        			e.printStackTrace();    
	        		}    
	        	}  
	        	
	        }  
			this.writeBasicResult(response1, true, result);
		}catch(Exception e) {
			this.writeBasicResult(response1, false, "获取失败");
		}
	}
}
