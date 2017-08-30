package com.test.zmap_service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.ElectoralAreaMapper;
import com.test.model.ElectoralArea;
import com.test.model.ElectoralAreaCriteria;


@Service
public class ElectoralAreaService {
	@Autowired
	ElectoralAreaMapper electoralAreaMapper;
	
	/**
     * 功能：向数据库中插入一个VO对象
     */
	public int insertElectoralArea(ElectoralArea areaNode){
		return electoralAreaMapper.insert(areaNode);
	} 

	/**
     * 功能：向数据库中更新一个VO对象
     */
	public int updateElectoralArea(ElectoralArea areaNode){
		return electoralAreaMapper.updateByPrimaryKey(areaNode);
	}

	/**
     * 功能：根据主键获取vo对象
     */
	public ElectoralArea getElectoralAreaById(int id){
		ElectoralArea areaNode = electoralAreaMapper.selectByPrimaryKey(id);
		return areaNode;
	}
	
	
	/**
     * 功能：根据主键删除vo对象
     * @param id vo的主键
     */
	public void deleteElectoralAreaById(int id){
		electoralAreaMapper.deleteByPrimaryKey(id);
	}

	/**
     * 功能：根据ElectoralAreaCriteria获取vo集合
     */
	public List<ElectoralArea> getElectoralAreaListByCriteria(ElectoralAreaCriteria areaNodeCriteria) {
		if(areaNodeCriteria.getOrderByClause()==null){
			areaNodeCriteria.setOrderByClause("create_time desc");
		}
		List<ElectoralArea> list = electoralAreaMapper.selectByExample(areaNodeCriteria);	
		return list;
	}
	
	/**
     * 功能：根据一组id删除vo集合
     * @param List<String Or Integer> ids的集合
     */
	public int deleteElectoralAreasByIds(String[] keys){
		List<Integer> ids=new ArrayList<Integer>();
		for(String keystr:keys){
			ids.add(Integer.parseInt(keystr));
		}
		ElectoralAreaCriteria areaNodeCriteria=new ElectoralAreaCriteria();
		areaNodeCriteria.createCriteria().andIdIn(ids);
		return electoralAreaMapper.deleteByExample(areaNodeCriteria);
	}

	/**
     * 功能：返回分页列表
     * @return type : list
     */
	public List<ElectoralArea> queryElectoralAreaPage(int start,int end,ElectoralAreaCriteria areaNodeCriteria){
		areaNodeCriteria.setLimitStart(start);
		areaNodeCriteria.setLimitEnd(end);
		return electoralAreaMapper.selectByExample(areaNodeCriteria);
	}
	/**
	 * 功能：根据条件获取记录数
	 * @param dataInfoCriteria
	 * @return
	 */
	public int getCountElectoralArea(ElectoralAreaCriteria areaNodeCriteria){
		return electoralAreaMapper.countByExample(areaNodeCriteria);
	}
	
}
