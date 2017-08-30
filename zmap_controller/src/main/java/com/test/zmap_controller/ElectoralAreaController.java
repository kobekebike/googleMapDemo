package com.test.zmap_controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.model.AreaNode;
import com.test.model.AreaNodeCriteria;
import com.test.model.ElectoralArea;
import com.test.model.ElectoralAreaCriteria;
import com.test.model.ElectoralAreaCriteria.Criteria;
import com.test.zmap_service.AreaNodeService;
import com.test.zmap_service.ElectoralAreaService;

@Controller
@RequestMapping("/view/electoralArea.do")
public class ElectoralAreaController extends BasicController {

	@Autowired
	private ElectoralAreaService electoralAreaService;
	@Autowired
	private AreaNodeService areaNodeService;
	
	@RequestMapping(params = "method=updateElectoralArea")
	public void updateBoInfo(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		try {
			String electoralAreaId = request.getParameter("electoralAreaId");
			String createTime = request.getParameter("createTime");
			String jobName = request.getParameter("jobName");
			String jobGroup = request.getParameter("jobGroup");
			String jobStatus = request.getParameter("jobStatus");
			String description = request.getParameter("description");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ElectoralArea electoralArea = new ElectoralArea();
//			electoralArea.setJobId(new Integer(electoralAreaId));
//			if(StringUtils.isNotBlank(createTime)){
//				electoralArea.setCreateTime(sdf.parse(createTime));
//			}
//			electoralArea.setUpdateTime(new Date());
//			electoralArea.setJobName(jobName);
//			electoralArea.setJobGroup(jobGroup);
//			electoralArea.setJobStatus(jobStatus);
//			electoralArea.setDescription(description);
			int i = electoralAreaService.updateElectoralArea(electoralArea);
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
	
	
	@RequestMapping(params="method=deleteElectoralAreaByIds")
	public void deleteBankInfoByIds(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String[] ids){
		boolean result = false;
		int count = electoralAreaService.deleteElectoralAreasByIds(ids);
		result = count== 1 ? true : false;
		writeBasicResult(response, result, "");
	}
	
	@RequestMapping(params = "method=getElectoralAreaList")
	public String getBoInfoBeanList(HttpServletRequest request,HttpServletResponse response,@RequestParam Integer page,@RequestParam Integer rows){
		ElectoralAreaCriteria electoralAreaCriteria = new ElectoralAreaCriteria();
		String jobName = request.getParameter("job_name");
		String jobGroup = request.getParameter("job_group");
		String jobStatus = request.getParameter("job_status");
		
		Criteria criteria = electoralAreaCriteria.createCriteria();
		try{
			int start=this.getLimitStart(page, rows);
			int end=this.getLimitEnd(rows);
			electoralAreaCriteria.setLimitStart(start);
			electoralAreaCriteria.setLimitEnd(end);
//			if(StringUtils.isNotBlank(jobName)){
//		    	criteria.andJobNameLike("%"+jobName+"%");
//		    }
//		    if(StringUtils.isNotBlank(jobGroup)){
//				criteria.andJobGroupLike("%" + jobGroup + "%");
//			}
//			if(StringUtils.isNotBlank(jobStatus)){
//				criteria.andJobStatusLike("%" + jobStatus + "%");
//			}
			
			List<ElectoralArea> list = electoralAreaService.getElectoralAreaListByCriteria(electoralAreaCriteria);
			writeJsonUsingPager(list, list.size(), response);
		}catch(Exception e){
			e.printStackTrace();
			writeJsonArray(null, response);
		}
		return null;
	}
	
	//根据条件搜索总条数
	@RequestMapping(params = "method=electoralAreaCount")
	public void electoralAreaCount(HttpServletRequest request,HttpServletResponse response){
		String jobName = request.getParameter("job_name");
		String jobGroup = request.getParameter("job_group");
		String jobStatus = request.getParameter("job_status");
		
		ElectoralAreaCriteria electoralAreaCriteria = new ElectoralAreaCriteria();
		Criteria criteria = electoralAreaCriteria.createCriteria();
		
//	    if(StringUtils.isNotBlank(jobName)){
//	    	criteria.andJobNameLike("%"+jobName+"%");
//	    }
//	    if(StringUtils.isNotBlank(jobGroup)){
//			criteria.andJobGroupLike("%" + jobGroup + "%");
//		}
//		if(StringUtils.isNotBlank(jobStatus)){
//			criteria.andJobStatusLike("%" + jobStatus + "%");
//		}
		int count = electoralAreaService.getCountElectoralArea(electoralAreaCriteria);
		writeJsonStr(String.valueOf(count), response);
	}
	
	@RequestMapping(params = "method=saveElectoralArea")
	public void saveElectoralArea(HttpServletRequest request, HttpServletResponse response) {
		boolean result = false;
		System.err.println("save");
		try {
			String createTime = request.getParameter("createTime");
			String jobName = request.getParameter("jobName");
			String jobGroup = request.getParameter("jobGroup");
			String jobStatus = request.getParameter("jobStatus");
			String description = request.getParameter("description");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ElectoralArea electoralArea = new ElectoralArea();
			electoralArea.setAreaYear("2017");
			electoralArea.setFillColor("red");
			electoralArea.setNodeIds("1,2,3");
			electoralArea.setStrokeColor("lala");
			electoralArea.setCreateTime(new Date());
			electoralArea.setAreaName("区域A");
			
			if(StringUtils.isNotBlank(createTime)){
			}
//			electoralArea.setJobName(jobName);
//			electoralArea.setJobGroup(jobGroup);
//			electoralArea.setJobStatus(jobStatus);
//			electoralArea.setDescription(description);
			int i = electoralAreaService.insertElectoralArea(electoralArea);
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
	//根据年份，查找所有选区
	@RequestMapping(params = "method=getElectoralAreaListByYear")
	public void getElectoralAreaListByYear(HttpServletRequest request, HttpServletResponse response) {
		ElectoralAreaCriteria electoralAreaCriteria = new ElectoralAreaCriteria();
		String areaYear = Calendar.getInstance().get(Calendar.YEAR)+"";
		Criteria criteria = electoralAreaCriteria.createCriteria();
		try{
	    	criteria.andAreaYearEqualTo(areaYear);
			List<ElectoralArea> list = electoralAreaService.getElectoralAreaListByCriteria(electoralAreaCriteria);
			writeJsonUsingPager(list, list.size(), response);
		}catch(Exception e){
			e.printStackTrace();
			writeJsonArray(null, response);
		}
	}
	
	//根据选区表示，查找选区结点
		@RequestMapping(params = "method=getNodeByAreaId")
		public void getNodeByAreaId(HttpServletRequest request, HttpServletResponse response) {
			AreaNodeCriteria areaNodeCriteria = new AreaNodeCriteria();
			String areaId = request.getParameter("areaId");
			if(StringUtils.isBlank(areaId)){
				return ;
			}
			com.test.model.AreaNodeCriteria.Criteria criteria = areaNodeCriteria.createCriteria();
			try{
		    	criteria.andZoneIdEqualTo(Integer.parseInt(areaId));
				List<AreaNode> list = areaNodeService.getAreaNodeListByCriteria(areaNodeCriteria);
				writeJsonUsingPager(list, list.size(), response);
			}catch(Exception e){
				e.printStackTrace();
				writeJsonArray(null, response);
			}
		}
	
}
