package com.test.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ElectoralAreaCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public ElectoralAreaCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAreaYearIsNull() {
            addCriterion("area_year is null");
            return (Criteria) this;
        }

        public Criteria andAreaYearIsNotNull() {
            addCriterion("area_year is not null");
            return (Criteria) this;
        }

        public Criteria andAreaYearEqualTo(String value) {
            addCriterion("area_year =", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearNotEqualTo(String value) {
            addCriterion("area_year <>", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearGreaterThan(String value) {
            addCriterion("area_year >", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearGreaterThanOrEqualTo(String value) {
            addCriterion("area_year >=", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearLessThan(String value) {
            addCriterion("area_year <", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearLessThanOrEqualTo(String value) {
            addCriterion("area_year <=", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearLike(String value) {
            addCriterion("area_year like", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearNotLike(String value) {
            addCriterion("area_year not like", value, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearIn(List<String> values) {
            addCriterion("area_year in", values, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearNotIn(List<String> values) {
            addCriterion("area_year not in", values, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearBetween(String value1, String value2) {
            addCriterion("area_year between", value1, value2, "areaYear");
            return (Criteria) this;
        }

        public Criteria andAreaYearNotBetween(String value1, String value2) {
            addCriterion("area_year not between", value1, value2, "areaYear");
            return (Criteria) this;
        }

        public Criteria andFillColorIsNull() {
            addCriterion("fill_color is null");
            return (Criteria) this;
        }

        public Criteria andFillColorIsNotNull() {
            addCriterion("fill_color is not null");
            return (Criteria) this;
        }

        public Criteria andFillColorEqualTo(String value) {
            addCriterion("fill_color =", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorNotEqualTo(String value) {
            addCriterion("fill_color <>", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorGreaterThan(String value) {
            addCriterion("fill_color >", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorGreaterThanOrEqualTo(String value) {
            addCriterion("fill_color >=", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorLessThan(String value) {
            addCriterion("fill_color <", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorLessThanOrEqualTo(String value) {
            addCriterion("fill_color <=", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorLike(String value) {
            addCriterion("fill_color like", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorNotLike(String value) {
            addCriterion("fill_color not like", value, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorIn(List<String> values) {
            addCriterion("fill_color in", values, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorNotIn(List<String> values) {
            addCriterion("fill_color not in", values, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorBetween(String value1, String value2) {
            addCriterion("fill_color between", value1, value2, "fillColor");
            return (Criteria) this;
        }

        public Criteria andFillColorNotBetween(String value1, String value2) {
            addCriterion("fill_color not between", value1, value2, "fillColor");
            return (Criteria) this;
        }

        public Criteria andNodeIdsIsNull() {
            addCriterion("node_ids is null");
            return (Criteria) this;
        }

        public Criteria andNodeIdsIsNotNull() {
            addCriterion("node_ids is not null");
            return (Criteria) this;
        }

        public Criteria andNodeIdsEqualTo(String value) {
            addCriterion("node_ids =", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsNotEqualTo(String value) {
            addCriterion("node_ids <>", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsGreaterThan(String value) {
            addCriterion("node_ids >", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsGreaterThanOrEqualTo(String value) {
            addCriterion("node_ids >=", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsLessThan(String value) {
            addCriterion("node_ids <", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsLessThanOrEqualTo(String value) {
            addCriterion("node_ids <=", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsLike(String value) {
            addCriterion("node_ids like", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsNotLike(String value) {
            addCriterion("node_ids not like", value, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsIn(List<String> values) {
            addCriterion("node_ids in", values, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsNotIn(List<String> values) {
            addCriterion("node_ids not in", values, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsBetween(String value1, String value2) {
            addCriterion("node_ids between", value1, value2, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andNodeIdsNotBetween(String value1, String value2) {
            addCriterion("node_ids not between", value1, value2, "nodeIds");
            return (Criteria) this;
        }

        public Criteria andStrokeColorIsNull() {
            addCriterion("stroke_color is null");
            return (Criteria) this;
        }

        public Criteria andStrokeColorIsNotNull() {
            addCriterion("stroke_color is not null");
            return (Criteria) this;
        }

        public Criteria andStrokeColorEqualTo(String value) {
            addCriterion("stroke_color =", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorNotEqualTo(String value) {
            addCriterion("stroke_color <>", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorGreaterThan(String value) {
            addCriterion("stroke_color >", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorGreaterThanOrEqualTo(String value) {
            addCriterion("stroke_color >=", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorLessThan(String value) {
            addCriterion("stroke_color <", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorLessThanOrEqualTo(String value) {
            addCriterion("stroke_color <=", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorLike(String value) {
            addCriterion("stroke_color like", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorNotLike(String value) {
            addCriterion("stroke_color not like", value, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorIn(List<String> values) {
            addCriterion("stroke_color in", values, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorNotIn(List<String> values) {
            addCriterion("stroke_color not in", values, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorBetween(String value1, String value2) {
            addCriterion("stroke_color between", value1, value2, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andStrokeColorNotBetween(String value1, String value2) {
            addCriterion("stroke_color not between", value1, value2, "strokeColor");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(String value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(String value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(String value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(String value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(String value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLike(String value) {
            addCriterion("create_id like", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotLike(String value) {
            addCriterion("create_id not like", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<String> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<String> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(String value1, String value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(String value1, String value2) {
            addCriterion("create_id not between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("create_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("create_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("create_name =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("create_name <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("create_name >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_name >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("create_name <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("create_name <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("create_name like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("create_name not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("create_name in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("create_name not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("create_name between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("create_name not between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNull() {
            addCriterion("area_name is null");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNotNull() {
            addCriterion("area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAreaNameEqualTo(String value) {
            addCriterion("area_name =", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotEqualTo(String value) {
            addCriterion("area_name <>", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThan(String value) {
            addCriterion("area_name >", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("area_name >=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThan(String value) {
            addCriterion("area_name <", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThanOrEqualTo(String value) {
            addCriterion("area_name <=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLike(String value) {
            addCriterion("area_name like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotLike(String value) {
            addCriterion("area_name not like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameIn(List<String> values) {
            addCriterion("area_name in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotIn(List<String> values) {
            addCriterion("area_name not in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameBetween(String value1, String value2) {
            addCriterion("area_name between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotBetween(String value1, String value2) {
            addCriterion("area_name not between", value1, value2, "areaName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}