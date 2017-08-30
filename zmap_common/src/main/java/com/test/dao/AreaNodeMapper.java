package com.test.dao;

import com.test.model.AreaNode;
import com.test.model.AreaNodeCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaNodeMapper {
    int countByExample(AreaNodeCriteria example);

    int deleteByExample(AreaNodeCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(AreaNode record);

    int insertSelective(AreaNode record);

    List<AreaNode> selectByExample(AreaNodeCriteria example);

    AreaNode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AreaNode record, @Param("example") AreaNodeCriteria example);

    int updateByExample(@Param("record") AreaNode record, @Param("example") AreaNodeCriteria example);

    int updateByPrimaryKeySelective(AreaNode record);

    int updateByPrimaryKey(AreaNode record);
}