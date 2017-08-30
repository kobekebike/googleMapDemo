package com.test.dao;

import com.test.model.ElectoralArea;
import com.test.model.ElectoralAreaCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ElectoralAreaMapper {
    int countByExample(ElectoralAreaCriteria example);

    int deleteByExample(ElectoralAreaCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(ElectoralArea record);

    int insertSelective(ElectoralArea record);

    List<ElectoralArea> selectByExample(ElectoralAreaCriteria example);

    ElectoralArea selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ElectoralArea record, @Param("example") ElectoralAreaCriteria example);

    int updateByExample(@Param("record") ElectoralArea record, @Param("example") ElectoralAreaCriteria example);

    int updateByPrimaryKeySelective(ElectoralArea record);

    int updateByPrimaryKey(ElectoralArea record);
}