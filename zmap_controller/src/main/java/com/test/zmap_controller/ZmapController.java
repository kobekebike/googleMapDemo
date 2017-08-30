package com.test.zmap_controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.utils.GoogleMapUtil;
@Controller
@RequestMapping("/view/zmap.do")
public class ZmapController extends BasicController {
	@RequestMapping(params = "method=ajaxLatLngByAddress")
	public void ajaxLatLngByAddress(HttpServletRequest request,HttpServletResponse response) {
		String address = request.getParameter("address");
		String result = "";
		try {
			if(StringUtils.isBlank(address) ){  
				result = "请输入详细地址";  
	        }else{
	        	String str =  GoogleMapUtil.getLatlngByAddress(address); 
 				JSONObject o = (JSONObject) JSON.parse(str);  
 				JSONArray o2 = (JSONArray) o.get("results");  
 				JSONObject o3 =  (JSONObject) o2.get(0);  
 				
 				JSONObject o4 = (JSONObject) o3.get("geometry");  
 				System.err.println(o4.toJSONString());
 				System.err.println(((JSONObject) o3.get("address_components")).toJSONString());
 				JSONObject o5 = (JSONObject)o4.get("location");  
 				result = "lat-->>"+o5.get("lat")+";lng-->>"+o5.get("lng");
	        }  
			this.writeBasicResult(response, true, result);
		}catch(Exception e) {
			this.writeBasicResult(response, false, "获取失败");
		}
	}
	@RequestMapping(params = "method=ajaxAddressByLatLng")
	public void ajaxAddressByLatLng(HttpServletRequest request,HttpServletResponse response) {
		String lat = request.getParameter("lat");
		String lng = request.getParameter("lng");
		String result = "";
		try {
			if(StringUtils.isBlank(lng) || StringUtils.isBlank(lat) ){  
				result = "经度或者纬度为空，请填写";  
	        }else{
	        	String str =  GoogleMapUtil.getAddressBylatlng(lat,lng); 
				// 打印响应内容      
				JSONObject o = (JSONObject) JSON.parse(str);  
				JSONArray o1 = (JSONArray)o.get("results");  
				JSONObject o2 = (JSONObject)o1.get(0);  
				if(null != o2){  
					result = String.valueOf(o2.get("formatted_address"));  
				}  
	        }  
			this.writeBasicResult(response, true, result);
		}catch(Exception e) {
			this.writeBasicResult(response, false, "获取失败");
		}
	}

}
