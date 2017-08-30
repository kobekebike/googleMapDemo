package com.test.zmap_controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ld.utils.JsonUtil;
import com.test.model.AreaNode;
import com.test.model.AreaNodeCriteria;
import com.test.model.AreaNodeCriteria.Criteria;
import com.test.zmap_service.AreaNodeService;

@Controller
@RequestMapping("/view/areaNode.do")
public class AreaNodeController extends BasicController {

	@Autowired
	private AreaNodeService areaNodeService;
	
	@RequestMapping(params = "method=updateAreaNode")
	public void updateBoInfo(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		try {
			String areaNodeId = request.getParameter("areaNodeId");
			String createTime = request.getParameter("createTime");
			String jobName = request.getParameter("jobName");
			String jobGroup = request.getParameter("jobGroup");
			String jobStatus = request.getParameter("jobStatus");
			String description = request.getParameter("description");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			AreaNode areaNode = new AreaNode();
//			areaNode.setJobId(new Integer(areaNodeId));
//			if(StringUtils.isNotBlank(createTime)){
//				areaNode.setCreateTime(sdf.parse(createTime));
//			}
//			areaNode.setUpdateTime(new Date());
//			areaNode.setJobName(jobName);
//			areaNode.setJobGroup(jobGroup);
//			areaNode.setJobStatus(jobStatus);
//			areaNode.setDescription(description);
			int i = areaNodeService.updateAreaNode(areaNode);
			if(i == 1){
				result = true;
			} else {
				result = false;
			}
			writeBasicResult(response, result, "成功");
		} catch (Exception e) {
			writeBasicResult(response, result, "失败");
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(params="method=deleteAreaNodeByIds")
	public void deleteBankInfoByIds(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String[] ids){
		boolean result = false;
		int count = areaNodeService.deleteAreaNodesByIds(ids);
		result = count== 1 ? true : false;
		writeBasicResult(response, result, "");
	}
	
	@RequestMapping(params = "method=getAreaNodeList")
	public String getBoInfoBeanList(HttpServletRequest request,HttpServletResponse response,@RequestParam Integer page,@RequestParam Integer rows){
		AreaNodeCriteria areaNodeCriteria = new AreaNodeCriteria();
		String jobName = request.getParameter("job_name");
		String jobGroup = request.getParameter("job_group");
		String jobStatus = request.getParameter("job_status");
		
		Criteria criteria = areaNodeCriteria.createCriteria();
		try{
			int start=this.getLimitStart(page, rows);
			int end=this.getLimitEnd(rows);
			areaNodeCriteria.setLimitStart(start);
			areaNodeCriteria.setLimitEnd(end);
//			if(StringUtils.isNotBlank(jobName)){
//		    	criteria.andJobNameLike("%"+jobName+"%");
//		    }
//		    if(StringUtils.isNotBlank(jobGroup)){
//				criteria.andJobGroupLike("%" + jobGroup + "%");
//			}
//			if(StringUtils.isNotBlank(jobStatus)){
//				criteria.andJobStatusLike("%" + jobStatus + "%");
//			}
			
			List<AreaNode> list = areaNodeService.getAreaNodeListByCriteria(areaNodeCriteria);
			writeJsonUsingPager(list, list.size(), response);
		}catch(Exception e){
			e.printStackTrace();
			writeJsonArray(null, response);
		}
		return null;
	}
	
	//根据条件搜索总条数
	@RequestMapping(params = "method=areaNodeCount")
	public void areaNodeCount(HttpServletRequest request,HttpServletResponse response){
		String jobName = request.getParameter("job_name");
		String jobGroup = request.getParameter("job_group");
		String jobStatus = request.getParameter("job_status");
		
		AreaNodeCriteria areaNodeCriteria = new AreaNodeCriteria();
		Criteria criteria = areaNodeCriteria.createCriteria();
		
//	    if(StringUtils.isNotBlank(jobName)){
//	    	criteria.andJobNameLike("%"+jobName+"%");
//	    }
//	    if(StringUtils.isNotBlank(jobGroup)){
//			criteria.andJobGroupLike("%" + jobGroup + "%");
//		}
//		if(StringUtils.isNotBlank(jobStatus)){
//			criteria.andJobStatusLike("%" + jobStatus + "%");
//		}
		int count = areaNodeService.getCountAreaNode(areaNodeCriteria);
		writeJsonStr(String.valueOf(count), response);
	}
	
	@RequestMapping(params = "method=saveAreaNode")
	public void saveBoInfo(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		try {
			String createTime = request.getParameter("createTime");
			String jobName = request.getParameter("jobName");
			String jobGroup = request.getParameter("jobGroup");
			String jobStatus = request.getParameter("jobStatus");
			String description = request.getParameter("description");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			AreaNode areaNode = new AreaNode();
			if(StringUtils.isNotBlank(createTime)){
				//areaNode.setCreateTime(sdf.parse(createTime));
			}
//			areaNode.setJobName(jobName);
//			areaNode.setJobGroup(jobGroup);
//			areaNode.setJobStatus(jobStatus);
//			areaNode.setDescription(description);
			areaNode.setLng(new BigDecimal(22.122121));
			areaNode.setLat(new BigDecimal(120.122121));
			areaNode.setNodeOrder(1);
			areaNode.setZoneId(1);
			areaNode.setCreateDate(new Date());
			int i = areaNodeService.insertAreaNode(areaNode);
			if(i == 1){
				result = true;
			} else {
				result = false;
			}
			writeBasicResult(response, result, "成功");
		} catch (Exception e) {
			writeBasicResult(response, result, "失败");
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(params = "method=saveAreaNodes")
	public void saveAreaNodes(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		try {
			String jsonListStr = request.getParameter("jsonListStr");
			List<AreaNode> list = JsonUtil.json2List(jsonListStr, AreaNode.class);
			areaNodeService.insertAreaNodeList(list);
			writeBasicResult(response, result, "成功");
		} catch (Exception e) {
			writeBasicResult(response, result, "失败");
			e.printStackTrace();
		}
	}
}
