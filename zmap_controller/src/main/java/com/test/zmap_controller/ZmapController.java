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
	        	result =  GoogleMapUtil.getLatlngByAddress(address); 
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
	        	result =  GoogleMapUtil.getAddressBylatlng(lat,lng); 
	        }  
			this.writeBasicResult(response, true, result);
		}catch(Exception e) {
			this.writeBasicResult(response, false, "获取失败");
		}
	}

}
