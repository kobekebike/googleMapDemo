package com.test.zmap_service;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.dao.AreaNodeMapper;
import com.test.dao.ElectoralAreaMapper;
import com.test.model.AreaNode;
import com.test.model.AreaNodeCriteria;
import com.test.model.ElectoralArea;


@Service
public class AreaNodeService {
	@Autowired
	AreaNodeMapper areaNodeMapper;
	@Autowired
	ElectoralAreaMapper electoralAreaMapper;
	
	/**
     * 功能：向数据库中插入一个VO对象
     */
	public int insertAreaNode(AreaNode areaNode){
		return areaNodeMapper.insert(areaNode);
	} 
	
	/**
     * 功能：向数据库中插入一个VO对象
     */
	public void insertAreaNodeList(List<AreaNode> list){
		if(list==null||list.size()==0){
			return ;
		}
		AreaNode temp = list.get(0);
		AreaNodeCriteria criteria = new AreaNodeCriteria();
		Integer zoneId = temp.getZoneId();
		criteria.createCriteria().andZoneIdEqualTo(zoneId);
		
		ElectoralArea electoralArea = electoralAreaMapper.selectByPrimaryKey(zoneId);
		//先判断当前这个区域，是否存在选区，如果存在，则需要重新创建一个选区，只是属性用这个选区的
		List<AreaNode> oldList = areaNodeMapper.selectByExample(criteria);
		boolean flag = false;//当前选区id是否存在对应结点
		if(oldList!=null&&oldList.size()>0){
			flag = true;
		}
		
		if(flag){
			electoralArea.setId(null);
			electoralAreaMapper.insert(electoralArea);
		}
		for(int i=0,max=list.size();i<max;i++){
			AreaNode areaNode = list.get(i);
			areaNode.setZoneId(electoralArea.getId());
			areaNode.setCreateDate(new Date());
			areaNodeMapper.insert(areaNode);
		}
	} 

	/**
     * 功能：向数据库中更新一个VO对象
     */
	public int updateAreaNode(AreaNode areaNode){
		return areaNodeMapper.updateByPrimaryKey(areaNode);
	}

	/**
     * 功能：根据主键获取vo对象
     */
	public AreaNode getAreaNodeById(int id){
		AreaNode areaNode = areaNodeMapper.selectByPrimaryKey(id);
		return areaNode;
	}
	
	
	/**
     * 功能：根据主键删除vo对象
     * @param id vo的主键
     */
	public void deleteAreaNodeById(int id){
		areaNodeMapper.deleteByPrimaryKey(id);
	}

	/**
     * 功能：根据AreaNodeCriteria获取vo集合
     */
	public List<AreaNode> getAreaNodeListByCriteria(AreaNodeCriteria areaNodeCriteria) {
		if(areaNodeCriteria.getOrderByClause()==null){
			areaNodeCriteria.setOrderByClause("create_date desc");
		}
		List<AreaNode> list = areaNodeMapper.selectByExample(areaNodeCriteria);	
		return list;
	}
	
	/**
     * 功能：根据一组id删除vo集合
     * @param List<String Or Integer> ids的集合
     */
	public int deleteAreaNodesByIds(String[] keys){
		List<Integer> ids=new ArrayList<Integer>();
		for(String keystr:keys){
			ids.add(Integer.parseInt(keystr));
		}
		AreaNodeCriteria areaNodeCriteria=new AreaNodeCriteria();
		areaNodeCriteria.createCriteria().andIdIn(ids);
		return areaNodeMapper.deleteByExample(areaNodeCriteria);
	}

	/**
     * 功能：返回分页列表
     * @return type : list
     */
	public List<AreaNode> queryAreaNodePage(int start,int end,AreaNodeCriteria areaNodeCriteria){
		areaNodeCriteria.setLimitStart(start);
		areaNodeCriteria.setLimitEnd(end);
		return areaNodeMapper.selectByExample(areaNodeCriteria);
	}
	/**
	 * 功能：根据条件获取记录数
	 * @param dataInfoCriteria
	 * @return
	 */
	public int getCountAreaNode(AreaNodeCriteria areaNodeCriteria){
		return areaNodeMapper.countByExample(areaNodeCriteria);
	}
	
	public List<Point2D.Double> setNodeListToPointList(List<AreaNode> nodeList){
		List<Point2D.Double> list = new ArrayList<Point2D.Double>();
		if(nodeList==null||nodeList.size()==0){
			return list;
		}
		for(int i=0,max=nodeList.size();i<max;i++){
			AreaNode areaNode = nodeList.get(i);
			Point2D.Double point = new Point2D.Double((areaNode.getLat()).doubleValue(),(areaNode.getLng()).doubleValue());
			list.add(point);
		}
		
		return list;
	}
	
}
