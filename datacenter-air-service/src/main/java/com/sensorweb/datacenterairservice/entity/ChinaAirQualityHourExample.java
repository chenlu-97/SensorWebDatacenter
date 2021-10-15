package com.sensorweb.datacenterairservice.entity;

import java.util.ArrayList;
import java.util.List;

public class ChinaAirQualityHourExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChinaAirQualityHourExample() {
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

        public Criteria andAqiIsNull() {
            addCriterion("aqi is null");
            return (Criteria) this;
        }

        public Criteria andAqiIsNotNull() {
            addCriterion("aqi is not null");
            return (Criteria) this;
        }

        public Criteria andAqiEqualTo(String value) {
            addCriterion("aqi =", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiNotEqualTo(String value) {
            addCriterion("aqi <>", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiGreaterThan(String value) {
            addCriterion("aqi >", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiGreaterThanOrEqualTo(String value) {
            addCriterion("aqi >=", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiLessThan(String value) {
            addCriterion("aqi <", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiLessThanOrEqualTo(String value) {
            addCriterion("aqi <=", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiLike(String value) {
            addCriterion("aqi like", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiNotLike(String value) {
            addCriterion("aqi not like", value, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiIn(List<String> values) {
            addCriterion("aqi in", values, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiNotIn(List<String> values) {
            addCriterion("aqi not in", values, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiBetween(String value1, String value2) {
            addCriterion("aqi between", value1, value2, "aqi");
            return (Criteria) this;
        }

        public Criteria andAqiNotBetween(String value1, String value2) {
            addCriterion("aqi not between", value1, value2, "aqi");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andCoIsNull() {
            addCriterion("co is null");
            return (Criteria) this;
        }

        public Criteria andCoIsNotNull() {
            addCriterion("co is not null");
            return (Criteria) this;
        }

        public Criteria andCoEqualTo(String value) {
            addCriterion("co =", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoNotEqualTo(String value) {
            addCriterion("co <>", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoGreaterThan(String value) {
            addCriterion("co >", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoGreaterThanOrEqualTo(String value) {
            addCriterion("co >=", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoLessThan(String value) {
            addCriterion("co <", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoLessThanOrEqualTo(String value) {
            addCriterion("co <=", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoLike(String value) {
            addCriterion("co like", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoNotLike(String value) {
            addCriterion("co not like", value, "co");
            return (Criteria) this;
        }

        public Criteria andCoIn(List<String> values) {
            addCriterion("co in", values, "co");
            return (Criteria) this;
        }

        public Criteria andCoNotIn(List<String> values) {
            addCriterion("co not in", values, "co");
            return (Criteria) this;
        }

        public Criteria andCoBetween(String value1, String value2) {
            addCriterion("co between", value1, value2, "co");
            return (Criteria) this;
        }

        public Criteria andCoNotBetween(String value1, String value2) {
            addCriterion("co not between", value1, value2, "co");
            return (Criteria) this;
        }

        public Criteria andCo24hIsNull() {
            addCriterion("co_24h is null");
            return (Criteria) this;
        }

        public Criteria andCo24hIsNotNull() {
            addCriterion("co_24h is not null");
            return (Criteria) this;
        }

        public Criteria andCo24hEqualTo(String value) {
            addCriterion("co_24h =", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hNotEqualTo(String value) {
            addCriterion("co_24h <>", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hGreaterThan(String value) {
            addCriterion("co_24h >", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hGreaterThanOrEqualTo(String value) {
            addCriterion("co_24h >=", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hLessThan(String value) {
            addCriterion("co_24h <", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hLessThanOrEqualTo(String value) {
            addCriterion("co_24h <=", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hLike(String value) {
            addCriterion("co_24h like", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hNotLike(String value) {
            addCriterion("co_24h not like", value, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hIn(List<String> values) {
            addCriterion("co_24h in", values, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hNotIn(List<String> values) {
            addCriterion("co_24h not in", values, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hBetween(String value1, String value2) {
            addCriterion("co_24h between", value1, value2, "co24h");
            return (Criteria) this;
        }

        public Criteria andCo24hNotBetween(String value1, String value2) {
            addCriterion("co_24h not between", value1, value2, "co24h");
            return (Criteria) this;
        }

        public Criteria andNo2IsNull() {
            addCriterion("no2 is null");
            return (Criteria) this;
        }

        public Criteria andNo2IsNotNull() {
            addCriterion("no2 is not null");
            return (Criteria) this;
        }

        public Criteria andNo2EqualTo(String value) {
            addCriterion("no2 =", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotEqualTo(String value) {
            addCriterion("no2 <>", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2GreaterThan(String value) {
            addCriterion("no2 >", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2GreaterThanOrEqualTo(String value) {
            addCriterion("no2 >=", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2LessThan(String value) {
            addCriterion("no2 <", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2LessThanOrEqualTo(String value) {
            addCriterion("no2 <=", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2Like(String value) {
            addCriterion("no2 like", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotLike(String value) {
            addCriterion("no2 not like", value, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2In(List<String> values) {
            addCriterion("no2 in", values, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotIn(List<String> values) {
            addCriterion("no2 not in", values, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2Between(String value1, String value2) {
            addCriterion("no2 between", value1, value2, "no2");
            return (Criteria) this;
        }

        public Criteria andNo2NotBetween(String value1, String value2) {
            addCriterion("no2 not between", value1, value2, "no2");
            return (Criteria) this;
        }

        public Criteria andNo224hIsNull() {
            addCriterion("no2_24h is null");
            return (Criteria) this;
        }

        public Criteria andNo224hIsNotNull() {
            addCriterion("no2_24h is not null");
            return (Criteria) this;
        }

        public Criteria andNo224hEqualTo(String value) {
            addCriterion("no2_24h =", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hNotEqualTo(String value) {
            addCriterion("no2_24h <>", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hGreaterThan(String value) {
            addCriterion("no2_24h >", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hGreaterThanOrEqualTo(String value) {
            addCriterion("no2_24h >=", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hLessThan(String value) {
            addCriterion("no2_24h <", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hLessThanOrEqualTo(String value) {
            addCriterion("no2_24h <=", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hLike(String value) {
            addCriterion("no2_24h like", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hNotLike(String value) {
            addCriterion("no2_24h not like", value, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hIn(List<String> values) {
            addCriterion("no2_24h in", values, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hNotIn(List<String> values) {
            addCriterion("no2_24h not in", values, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hBetween(String value1, String value2) {
            addCriterion("no2_24h between", value1, value2, "no224h");
            return (Criteria) this;
        }

        public Criteria andNo224hNotBetween(String value1, String value2) {
            addCriterion("no2_24h not between", value1, value2, "no224h");
            return (Criteria) this;
        }

        public Criteria andO3IsNull() {
            addCriterion("o3 is null");
            return (Criteria) this;
        }

        public Criteria andO3IsNotNull() {
            addCriterion("o3 is not null");
            return (Criteria) this;
        }

        public Criteria andO3EqualTo(String value) {
            addCriterion("o3 =", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3NotEqualTo(String value) {
            addCriterion("o3 <>", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3GreaterThan(String value) {
            addCriterion("o3 >", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3GreaterThanOrEqualTo(String value) {
            addCriterion("o3 >=", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3LessThan(String value) {
            addCriterion("o3 <", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3LessThanOrEqualTo(String value) {
            addCriterion("o3 <=", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3Like(String value) {
            addCriterion("o3 like", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3NotLike(String value) {
            addCriterion("o3 not like", value, "o3");
            return (Criteria) this;
        }

        public Criteria andO3In(List<String> values) {
            addCriterion("o3 in", values, "o3");
            return (Criteria) this;
        }

        public Criteria andO3NotIn(List<String> values) {
            addCriterion("o3 not in", values, "o3");
            return (Criteria) this;
        }

        public Criteria andO3Between(String value1, String value2) {
            addCriterion("o3 between", value1, value2, "o3");
            return (Criteria) this;
        }

        public Criteria andO3NotBetween(String value1, String value2) {
            addCriterion("o3 not between", value1, value2, "o3");
            return (Criteria) this;
        }

        public Criteria andO324hIsNull() {
            addCriterion("o3_24h is null");
            return (Criteria) this;
        }

        public Criteria andO324hIsNotNull() {
            addCriterion("o3_24h is not null");
            return (Criteria) this;
        }

        public Criteria andO324hEqualTo(String value) {
            addCriterion("o3_24h =", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hNotEqualTo(String value) {
            addCriterion("o3_24h <>", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hGreaterThan(String value) {
            addCriterion("o3_24h >", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hGreaterThanOrEqualTo(String value) {
            addCriterion("o3_24h >=", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hLessThan(String value) {
            addCriterion("o3_24h <", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hLessThanOrEqualTo(String value) {
            addCriterion("o3_24h <=", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hLike(String value) {
            addCriterion("o3_24h like", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hNotLike(String value) {
            addCriterion("o3_24h not like", value, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hIn(List<String> values) {
            addCriterion("o3_24h in", values, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hNotIn(List<String> values) {
            addCriterion("o3_24h not in", values, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hBetween(String value1, String value2) {
            addCriterion("o3_24h between", value1, value2, "o324h");
            return (Criteria) this;
        }

        public Criteria andO324hNotBetween(String value1, String value2) {
            addCriterion("o3_24h not between", value1, value2, "o324h");
            return (Criteria) this;
        }

        public Criteria andO38hIsNull() {
            addCriterion("o3_8h is null");
            return (Criteria) this;
        }

        public Criteria andO38hIsNotNull() {
            addCriterion("o3_8h is not null");
            return (Criteria) this;
        }

        public Criteria andO38hEqualTo(String value) {
            addCriterion("o3_8h =", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hNotEqualTo(String value) {
            addCriterion("o3_8h <>", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hGreaterThan(String value) {
            addCriterion("o3_8h >", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hGreaterThanOrEqualTo(String value) {
            addCriterion("o3_8h >=", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hLessThan(String value) {
            addCriterion("o3_8h <", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hLessThanOrEqualTo(String value) {
            addCriterion("o3_8h <=", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hLike(String value) {
            addCriterion("o3_8h like", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hNotLike(String value) {
            addCriterion("o3_8h not like", value, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hIn(List<String> values) {
            addCriterion("o3_8h in", values, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hNotIn(List<String> values) {
            addCriterion("o3_8h not in", values, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hBetween(String value1, String value2) {
            addCriterion("o3_8h between", value1, value2, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38hNotBetween(String value1, String value2) {
            addCriterion("o3_8h not between", value1, value2, "o38h");
            return (Criteria) this;
        }

        public Criteria andO38h24hIsNull() {
            addCriterion("o3_8h_24h is null");
            return (Criteria) this;
        }

        public Criteria andO38h24hIsNotNull() {
            addCriterion("o3_8h_24h is not null");
            return (Criteria) this;
        }

        public Criteria andO38h24hEqualTo(String value) {
            addCriterion("o3_8h_24h =", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hNotEqualTo(String value) {
            addCriterion("o3_8h_24h <>", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hGreaterThan(String value) {
            addCriterion("o3_8h_24h >", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hGreaterThanOrEqualTo(String value) {
            addCriterion("o3_8h_24h >=", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hLessThan(String value) {
            addCriterion("o3_8h_24h <", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hLessThanOrEqualTo(String value) {
            addCriterion("o3_8h_24h <=", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hLike(String value) {
            addCriterion("o3_8h_24h like", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hNotLike(String value) {
            addCriterion("o3_8h_24h not like", value, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hIn(List<String> values) {
            addCriterion("o3_8h_24h in", values, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hNotIn(List<String> values) {
            addCriterion("o3_8h_24h not in", values, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hBetween(String value1, String value2) {
            addCriterion("o3_8h_24h between", value1, value2, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andO38h24hNotBetween(String value1, String value2) {
            addCriterion("o3_8h_24h not between", value1, value2, "o38h24h");
            return (Criteria) this;
        }

        public Criteria andPm10IsNull() {
            addCriterion("pm10 is null");
            return (Criteria) this;
        }

        public Criteria andPm10IsNotNull() {
            addCriterion("pm10 is not null");
            return (Criteria) this;
        }

        public Criteria andPm10EqualTo(String value) {
            addCriterion("pm10 =", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10NotEqualTo(String value) {
            addCriterion("pm10 <>", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10GreaterThan(String value) {
            addCriterion("pm10 >", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10GreaterThanOrEqualTo(String value) {
            addCriterion("pm10 >=", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10LessThan(String value) {
            addCriterion("pm10 <", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10LessThanOrEqualTo(String value) {
            addCriterion("pm10 <=", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10Like(String value) {
            addCriterion("pm10 like", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10NotLike(String value) {
            addCriterion("pm10 not like", value, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10In(List<String> values) {
            addCriterion("pm10 in", values, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10NotIn(List<String> values) {
            addCriterion("pm10 not in", values, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10Between(String value1, String value2) {
            addCriterion("pm10 between", value1, value2, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm10NotBetween(String value1, String value2) {
            addCriterion("pm10 not between", value1, value2, "pm10");
            return (Criteria) this;
        }

        public Criteria andPm1024hIsNull() {
            addCriterion("pm10_24h is null");
            return (Criteria) this;
        }

        public Criteria andPm1024hIsNotNull() {
            addCriterion("pm10_24h is not null");
            return (Criteria) this;
        }

        public Criteria andPm1024hEqualTo(String value) {
            addCriterion("pm10_24h =", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hNotEqualTo(String value) {
            addCriterion("pm10_24h <>", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hGreaterThan(String value) {
            addCriterion("pm10_24h >", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hGreaterThanOrEqualTo(String value) {
            addCriterion("pm10_24h >=", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hLessThan(String value) {
            addCriterion("pm10_24h <", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hLessThanOrEqualTo(String value) {
            addCriterion("pm10_24h <=", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hLike(String value) {
            addCriterion("pm10_24h like", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hNotLike(String value) {
            addCriterion("pm10_24h not like", value, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hIn(List<String> values) {
            addCriterion("pm10_24h in", values, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hNotIn(List<String> values) {
            addCriterion("pm10_24h not in", values, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hBetween(String value1, String value2) {
            addCriterion("pm10_24h between", value1, value2, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm1024hNotBetween(String value1, String value2) {
            addCriterion("pm10_24h not between", value1, value2, "pm1024h");
            return (Criteria) this;
        }

        public Criteria andPm25IsNull() {
            addCriterion("pm2_5 is null");
            return (Criteria) this;
        }

        public Criteria andPm25IsNotNull() {
            addCriterion("pm2_5 is not null");
            return (Criteria) this;
        }

        public Criteria andPm25EqualTo(String value) {
            addCriterion("pm2_5 =", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25NotEqualTo(String value) {
            addCriterion("pm2_5 <>", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25GreaterThan(String value) {
            addCriterion("pm2_5 >", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25GreaterThanOrEqualTo(String value) {
            addCriterion("pm2_5 >=", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25LessThan(String value) {
            addCriterion("pm2_5 <", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25LessThanOrEqualTo(String value) {
            addCriterion("pm2_5 <=", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25Like(String value) {
            addCriterion("pm2_5 like", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25NotLike(String value) {
            addCriterion("pm2_5 not like", value, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25In(List<String> values) {
            addCriterion("pm2_5 in", values, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25NotIn(List<String> values) {
            addCriterion("pm2_5 not in", values, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25Between(String value1, String value2) {
            addCriterion("pm2_5 between", value1, value2, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm25NotBetween(String value1, String value2) {
            addCriterion("pm2_5 not between", value1, value2, "pm25");
            return (Criteria) this;
        }

        public Criteria andPm2524hIsNull() {
            addCriterion("pm2_5_24h is null");
            return (Criteria) this;
        }

        public Criteria andPm2524hIsNotNull() {
            addCriterion("pm2_5_24h is not null");
            return (Criteria) this;
        }

        public Criteria andPm2524hEqualTo(String value) {
            addCriterion("pm2_5_24h =", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hNotEqualTo(String value) {
            addCriterion("pm2_5_24h <>", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hGreaterThan(String value) {
            addCriterion("pm2_5_24h >", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hGreaterThanOrEqualTo(String value) {
            addCriterion("pm2_5_24h >=", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hLessThan(String value) {
            addCriterion("pm2_5_24h <", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hLessThanOrEqualTo(String value) {
            addCriterion("pm2_5_24h <=", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hLike(String value) {
            addCriterion("pm2_5_24h like", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hNotLike(String value) {
            addCriterion("pm2_5_24h not like", value, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hIn(List<String> values) {
            addCriterion("pm2_5_24h in", values, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hNotIn(List<String> values) {
            addCriterion("pm2_5_24h not in", values, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hBetween(String value1, String value2) {
            addCriterion("pm2_5_24h between", value1, value2, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPm2524hNotBetween(String value1, String value2) {
            addCriterion("pm2_5_24h not between", value1, value2, "pm2524h");
            return (Criteria) this;
        }

        public Criteria andPositionNameIsNull() {
            addCriterion("position_name is null");
            return (Criteria) this;
        }

        public Criteria andPositionNameIsNotNull() {
            addCriterion("position_name is not null");
            return (Criteria) this;
        }

        public Criteria andPositionNameEqualTo(String value) {
            addCriterion("position_name =", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotEqualTo(String value) {
            addCriterion("position_name <>", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameGreaterThan(String value) {
            addCriterion("position_name >", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameGreaterThanOrEqualTo(String value) {
            addCriterion("position_name >=", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLessThan(String value) {
            addCriterion("position_name <", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLessThanOrEqualTo(String value) {
            addCriterion("position_name <=", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameLike(String value) {
            addCriterion("position_name like", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotLike(String value) {
            addCriterion("position_name not like", value, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameIn(List<String> values) {
            addCriterion("position_name in", values, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotIn(List<String> values) {
            addCriterion("position_name not in", values, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameBetween(String value1, String value2) {
            addCriterion("position_name between", value1, value2, "positionName");
            return (Criteria) this;
        }

        public Criteria andPositionNameNotBetween(String value1, String value2) {
            addCriterion("position_name not between", value1, value2, "positionName");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantIsNull() {
            addCriterion("primary_pollutant is null");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantIsNotNull() {
            addCriterion("primary_pollutant is not null");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantEqualTo(String value) {
            addCriterion("primary_pollutant =", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantNotEqualTo(String value) {
            addCriterion("primary_pollutant <>", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantGreaterThan(String value) {
            addCriterion("primary_pollutant >", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantGreaterThanOrEqualTo(String value) {
            addCriterion("primary_pollutant >=", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantLessThan(String value) {
            addCriterion("primary_pollutant <", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantLessThanOrEqualTo(String value) {
            addCriterion("primary_pollutant <=", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantLike(String value) {
            addCriterion("primary_pollutant like", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantNotLike(String value) {
            addCriterion("primary_pollutant not like", value, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantIn(List<String> values) {
            addCriterion("primary_pollutant in", values, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantNotIn(List<String> values) {
            addCriterion("primary_pollutant not in", values, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantBetween(String value1, String value2) {
            addCriterion("primary_pollutant between", value1, value2, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andPrimaryPollutantNotBetween(String value1, String value2) {
            addCriterion("primary_pollutant not between", value1, value2, "primaryPollutant");
            return (Criteria) this;
        }

        public Criteria andQualityIsNull() {
            addCriterion("quality is null");
            return (Criteria) this;
        }

        public Criteria andQualityIsNotNull() {
            addCriterion("quality is not null");
            return (Criteria) this;
        }

        public Criteria andQualityEqualTo(String value) {
            addCriterion("quality =", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityNotEqualTo(String value) {
            addCriterion("quality <>", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityGreaterThan(String value) {
            addCriterion("quality >", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityGreaterThanOrEqualTo(String value) {
            addCriterion("quality >=", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityLessThan(String value) {
            addCriterion("quality <", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityLessThanOrEqualTo(String value) {
            addCriterion("quality <=", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityLike(String value) {
            addCriterion("quality like", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityNotLike(String value) {
            addCriterion("quality not like", value, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityIn(List<String> values) {
            addCriterion("quality in", values, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityNotIn(List<String> values) {
            addCriterion("quality not in", values, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityBetween(String value1, String value2) {
            addCriterion("quality between", value1, value2, "quality");
            return (Criteria) this;
        }

        public Criteria andQualityNotBetween(String value1, String value2) {
            addCriterion("quality not between", value1, value2, "quality");
            return (Criteria) this;
        }

        public Criteria andSo2IsNull() {
            addCriterion("so2 is null");
            return (Criteria) this;
        }

        public Criteria andSo2IsNotNull() {
            addCriterion("so2 is not null");
            return (Criteria) this;
        }

        public Criteria andSo2EqualTo(String value) {
            addCriterion("so2 =", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2NotEqualTo(String value) {
            addCriterion("so2 <>", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2GreaterThan(String value) {
            addCriterion("so2 >", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2GreaterThanOrEqualTo(String value) {
            addCriterion("so2 >=", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2LessThan(String value) {
            addCriterion("so2 <", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2LessThanOrEqualTo(String value) {
            addCriterion("so2 <=", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2Like(String value) {
            addCriterion("so2 like", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2NotLike(String value) {
            addCriterion("so2 not like", value, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2In(List<String> values) {
            addCriterion("so2 in", values, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2NotIn(List<String> values) {
            addCriterion("so2 not in", values, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2Between(String value1, String value2) {
            addCriterion("so2 between", value1, value2, "so2");
            return (Criteria) this;
        }

        public Criteria andSo2NotBetween(String value1, String value2) {
            addCriterion("so2 not between", value1, value2, "so2");
            return (Criteria) this;
        }

        public Criteria andSo224hIsNull() {
            addCriterion("so2_24h is null");
            return (Criteria) this;
        }

        public Criteria andSo224hIsNotNull() {
            addCriterion("so2_24h is not null");
            return (Criteria) this;
        }

        public Criteria andSo224hEqualTo(String value) {
            addCriterion("so2_24h =", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hNotEqualTo(String value) {
            addCriterion("so2_24h <>", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hGreaterThan(String value) {
            addCriterion("so2_24h >", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hGreaterThanOrEqualTo(String value) {
            addCriterion("so2_24h >=", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hLessThan(String value) {
            addCriterion("so2_24h <", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hLessThanOrEqualTo(String value) {
            addCriterion("so2_24h <=", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hLike(String value) {
            addCriterion("so2_24h like", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hNotLike(String value) {
            addCriterion("so2_24h not like", value, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hIn(List<String> values) {
            addCriterion("so2_24h in", values, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hNotIn(List<String> values) {
            addCriterion("so2_24h not in", values, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hBetween(String value1, String value2) {
            addCriterion("so2_24h between", value1, value2, "so224h");
            return (Criteria) this;
        }

        public Criteria andSo224hNotBetween(String value1, String value2) {
            addCriterion("so2_24h not between", value1, value2, "so224h");
            return (Criteria) this;
        }

        public Criteria andStationCodeIsNull() {
            addCriterion("station_code is null");
            return (Criteria) this;
        }

        public Criteria andStationCodeIsNotNull() {
            addCriterion("station_code is not null");
            return (Criteria) this;
        }

        public Criteria andStationCodeEqualTo(String value) {
            addCriterion("station_code =", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeNotEqualTo(String value) {
            addCriterion("station_code <>", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeGreaterThan(String value) {
            addCriterion("station_code >", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("station_code >=", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeLessThan(String value) {
            addCriterion("station_code <", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeLessThanOrEqualTo(String value) {
            addCriterion("station_code <=", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeLike(String value) {
            addCriterion("station_code like", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeNotLike(String value) {
            addCriterion("station_code not like", value, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeIn(List<String> values) {
            addCriterion("station_code in", values, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeNotIn(List<String> values) {
            addCriterion("station_code not in", values, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeBetween(String value1, String value2) {
            addCriterion("station_code between", value1, value2, "stationCode");
            return (Criteria) this;
        }

        public Criteria andStationCodeNotBetween(String value1, String value2) {
            addCriterion("station_code not between", value1, value2, "stationCode");
            return (Criteria) this;
        }

        public Criteria andTimePointIsNull() {
            addCriterion("time_point is null");
            return (Criteria) this;
        }

        public Criteria andTimePointIsNotNull() {
            addCriterion("time_point is not null");
            return (Criteria) this;
        }

        public Criteria andTimePointEqualTo(String value) {
            addCriterion("time_point =", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotEqualTo(String value) {
            addCriterion("time_point <>", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointGreaterThan(String value) {
            addCriterion("time_point >", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointGreaterThanOrEqualTo(String value) {
            addCriterion("time_point >=", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointLessThan(String value) {
            addCriterion("time_point <", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointLessThanOrEqualTo(String value) {
            addCriterion("time_point <=", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointLike(String value) {
            addCriterion("time_point like", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotLike(String value) {
            addCriterion("time_point not like", value, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointIn(List<String> values) {
            addCriterion("time_point in", values, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotIn(List<String> values) {
            addCriterion("time_point not in", values, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointBetween(String value1, String value2) {
            addCriterion("time_point between", value1, value2, "timePoint");
            return (Criteria) this;
        }

        public Criteria andTimePointNotBetween(String value1, String value2) {
            addCriterion("time_point not between", value1, value2, "timePoint");
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