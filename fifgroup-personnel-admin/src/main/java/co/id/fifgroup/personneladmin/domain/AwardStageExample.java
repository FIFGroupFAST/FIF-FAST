package co.id.fifgroup.personneladmin.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AwardStageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public AwardStageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andUploadIdIsNull() {
            addCriterion("UPLOAD_ID is null");
            return (Criteria) this;
        }

        public Criteria andUploadIdIsNotNull() {
            addCriterion("UPLOAD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUploadIdEqualTo(Long value) {
            addCriterion("UPLOAD_ID =", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdNotEqualTo(Long value) {
            addCriterion("UPLOAD_ID <>", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdGreaterThan(Long value) {
            addCriterion("UPLOAD_ID >", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdGreaterThanOrEqualTo(Long value) {
            addCriterion("UPLOAD_ID >=", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdLessThan(Long value) {
            addCriterion("UPLOAD_ID <", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdLessThanOrEqualTo(Long value) {
            addCriterion("UPLOAD_ID <=", value, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdIn(List<Long> values) {
            addCriterion("UPLOAD_ID in", values, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdNotIn(List<Long> values) {
            addCriterion("UPLOAD_ID not in", values, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdBetween(Long value1, Long value2) {
            addCriterion("UPLOAD_ID between", value1, value2, "uploadId");
            return (Criteria) this;
        }

        public Criteria andUploadIdNotBetween(Long value1, Long value2) {
            addCriterion("UPLOAD_ID not between", value1, value2, "uploadId");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberIsNull() {
            addCriterion("EMPLOYEE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberIsNotNull() {
            addCriterion("EMPLOYEE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberEqualTo(String value) {
            addCriterion("EMPLOYEE_NUMBER =", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberNotEqualTo(String value) {
            addCriterion("EMPLOYEE_NUMBER <>", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberGreaterThan(String value) {
            addCriterion("EMPLOYEE_NUMBER >", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberGreaterThanOrEqualTo(String value) {
            addCriterion("EMPLOYEE_NUMBER >=", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberLessThan(String value) {
            addCriterion("EMPLOYEE_NUMBER <", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberLessThanOrEqualTo(String value) {
            addCriterion("EMPLOYEE_NUMBER <=", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberLike(String value) {
            addCriterion("EMPLOYEE_NUMBER like", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberNotLike(String value) {
            addCriterion("EMPLOYEE_NUMBER not like", value, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberIn(List<String> values) {
            addCriterion("EMPLOYEE_NUMBER in", values, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberNotIn(List<String> values) {
            addCriterion("EMPLOYEE_NUMBER not in", values, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberBetween(String value1, String value2) {
            addCriterion("EMPLOYEE_NUMBER between", value1, value2, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberNotBetween(String value1, String value2) {
            addCriterion("EMPLOYEE_NUMBER not between", value1, value2, "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andAwardDateIsNull() {
            addCriterion("AWARD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andAwardDateIsNotNull() {
            addCriterion("AWARD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andAwardDateEqualTo(Date value) {
            addCriterionForJDBCDate("AWARD_DATE =", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("AWARD_DATE <>", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateGreaterThan(Date value) {
            addCriterionForJDBCDate("AWARD_DATE >", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("AWARD_DATE >=", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateLessThan(Date value) {
            addCriterionForJDBCDate("AWARD_DATE <", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("AWARD_DATE <=", value, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateIn(List<Date> values) {
            addCriterionForJDBCDate("AWARD_DATE in", values, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("AWARD_DATE not in", values, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("AWARD_DATE between", value1, value2, "awardDate");
            return (Criteria) this;
        }

        public Criteria andAwardDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("AWARD_DATE not between", value1, value2, "awardDate");
            return (Criteria) this;
        }

        public Criteria andJobCodeIsNull() {
            addCriterion("JOB_CODE is null");
            return (Criteria) this;
        }

        public Criteria andJobCodeIsNotNull() {
            addCriterion("JOB_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andJobCodeEqualTo(String value) {
            addCriterion("JOB_CODE =", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeNotEqualTo(String value) {
            addCriterion("JOB_CODE <>", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeGreaterThan(String value) {
            addCriterion("JOB_CODE >", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeGreaterThanOrEqualTo(String value) {
            addCriterion("JOB_CODE >=", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeLessThan(String value) {
            addCriterion("JOB_CODE <", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeLessThanOrEqualTo(String value) {
            addCriterion("JOB_CODE <=", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeLike(String value) {
            addCriterion("JOB_CODE like", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeNotLike(String value) {
            addCriterion("JOB_CODE not like", value, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeIn(List<String> values) {
            addCriterion("JOB_CODE in", values, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeNotIn(List<String> values) {
            addCriterion("JOB_CODE not in", values, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeBetween(String value1, String value2) {
            addCriterion("JOB_CODE between", value1, value2, "jobCode");
            return (Criteria) this;
        }

        public Criteria andJobCodeNotBetween(String value1, String value2) {
            addCriterion("JOB_CODE not between", value1, value2, "jobCode");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIsNull() {
            addCriterion("AWARD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIsNotNull() {
            addCriterion("AWARD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAwardTypeEqualTo(String value) {
            addCriterion("AWARD_TYPE =", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotEqualTo(String value) {
            addCriterion("AWARD_TYPE <>", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThan(String value) {
            addCriterion("AWARD_TYPE >", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("AWARD_TYPE >=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThan(String value) {
            addCriterion("AWARD_TYPE <", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLessThanOrEqualTo(String value) {
            addCriterion("AWARD_TYPE <=", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLike(String value) {
            addCriterion("AWARD_TYPE like", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotLike(String value) {
            addCriterion("AWARD_TYPE not like", value, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeIn(List<String> values) {
            addCriterion("AWARD_TYPE in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotIn(List<String> values) {
            addCriterion("AWARD_TYPE not in", values, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeBetween(String value1, String value2) {
            addCriterion("AWARD_TYPE between", value1, value2, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardTypeNotBetween(String value1, String value2) {
            addCriterion("AWARD_TYPE not between", value1, value2, "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeIsNull() {
            addCriterion("AWARD_PRIZE is null");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeIsNotNull() {
            addCriterion("AWARD_PRIZE is not null");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeEqualTo(String value) {
            addCriterion("AWARD_PRIZE =", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeNotEqualTo(String value) {
            addCriterion("AWARD_PRIZE <>", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeGreaterThan(String value) {
            addCriterion("AWARD_PRIZE >", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeGreaterThanOrEqualTo(String value) {
            addCriterion("AWARD_PRIZE >=", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeLessThan(String value) {
            addCriterion("AWARD_PRIZE <", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeLessThanOrEqualTo(String value) {
            addCriterion("AWARD_PRIZE <=", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeLike(String value) {
            addCriterion("AWARD_PRIZE like", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeNotLike(String value) {
            addCriterion("AWARD_PRIZE not like", value, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeIn(List<String> values) {
            addCriterion("AWARD_PRIZE in", values, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeNotIn(List<String> values) {
            addCriterion("AWARD_PRIZE not in", values, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeBetween(String value1, String value2) {
            addCriterion("AWARD_PRIZE between", value1, value2, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeNotBetween(String value1, String value2) {
            addCriterion("AWARD_PRIZE not between", value1, value2, "awardPrize");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("AMOUNT =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("AMOUNT >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("AMOUNT <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("AMOUNT <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("AMOUNT in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("AMOUNT not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("AMOUNT not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andRowNumberIsNull() {
            addCriterion("ROW_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andRowNumberIsNotNull() {
            addCriterion("ROW_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andRowNumberEqualTo(Long value) {
            addCriterion("ROW_NUMBER =", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotEqualTo(Long value) {
            addCriterion("ROW_NUMBER <>", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberGreaterThan(Long value) {
            addCriterion("ROW_NUMBER >", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberGreaterThanOrEqualTo(Long value) {
            addCriterion("ROW_NUMBER >=", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberLessThan(Long value) {
            addCriterion("ROW_NUMBER <", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberLessThanOrEqualTo(Long value) {
            addCriterion("ROW_NUMBER <=", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberIn(List<Long> values) {
            addCriterion("ROW_NUMBER in", values, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotIn(List<Long> values) {
            addCriterion("ROW_NUMBER not in", values, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberBetween(Long value1, Long value2) {
            addCriterion("ROW_NUMBER between", value1, value2, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotBetween(Long value1, Long value2) {
            addCriterion("ROW_NUMBER not between", value1, value2, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRawDataIsNull() {
            addCriterion("RAW_DATA is null");
            return (Criteria) this;
        }

        public Criteria andRawDataIsNotNull() {
            addCriterion("RAW_DATA is not null");
            return (Criteria) this;
        }

        public Criteria andRawDataEqualTo(String value) {
            addCriterion("RAW_DATA =", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotEqualTo(String value) {
            addCriterion("RAW_DATA <>", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataGreaterThan(String value) {
            addCriterion("RAW_DATA >", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataGreaterThanOrEqualTo(String value) {
            addCriterion("RAW_DATA >=", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLessThan(String value) {
            addCriterion("RAW_DATA <", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLessThanOrEqualTo(String value) {
            addCriterion("RAW_DATA <=", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataLike(String value) {
            addCriterion("RAW_DATA like", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotLike(String value) {
            addCriterion("RAW_DATA not like", value, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataIn(List<String> values) {
            addCriterion("RAW_DATA in", values, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotIn(List<String> values) {
            addCriterion("RAW_DATA not in", values, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataBetween(String value1, String value2) {
            addCriterion("RAW_DATA between", value1, value2, "rawData");
            return (Criteria) this;
        }

        public Criteria andRawDataNotBetween(String value1, String value2) {
            addCriterion("RAW_DATA not between", value1, value2, "rawData");
            return (Criteria) this;
        }

        public Criteria andIsClosedIsNull() {
            addCriterion("IS_CLOSED is null");
            return (Criteria) this;
        }

        public Criteria andIsClosedIsNotNull() {
            addCriterion("IS_CLOSED is not null");
            return (Criteria) this;
        }

        public Criteria andIsClosedEqualTo(boolean value) {
            addCriterion("IS_CLOSED =", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotEqualTo(boolean value) {
            addCriterion("IS_CLOSED <>", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThan(boolean value) {
            addCriterion("IS_CLOSED >", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThanOrEqualTo(boolean value) {
            addCriterion("IS_CLOSED >=", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThan(boolean value) {
            addCriterion("IS_CLOSED <", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThanOrEqualTo(boolean value) {
            addCriterion("IS_CLOSED <=", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedIn(List<Boolean> values) {
            addCriterion("IS_CLOSED in", values, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotIn(List<Boolean> values) {
            addCriterion("IS_CLOSED not in", values, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedBetween(boolean value1, boolean value2) {
            addCriterion("IS_CLOSED between", value1, value2, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotBetween(boolean value1, boolean value2) {
            addCriterion("IS_CLOSED not between", value1, value2, "isClosed");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberLikeInsensitive(String value) {
            addCriterion("upper(EMPLOYEE_NUMBER) like", value.toUpperCase(), "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andJobCodeLikeInsensitive(String value) {
            addCriterion("upper(JOB_CODE) like", value.toUpperCase(), "jobCode");
            return (Criteria) this;
        }

        public Criteria andAwardTypeLikeInsensitive(String value) {
            addCriterion("upper(AWARD_TYPE) like", value.toUpperCase(), "awardType");
            return (Criteria) this;
        }

        public Criteria andAwardPrizeLikeInsensitive(String value) {
            addCriterion("upper(AWARD_PRIZE) like", value.toUpperCase(), "awardPrize");
            return (Criteria) this;
        }

        public Criteria andRawDataLikeInsensitive(String value) {
            addCriterion("upper(RAW_DATA) like", value.toUpperCase(), "rawData");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated do_not_delete_during_merge Wed Jul 03 21:35:24 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_AWARD_STG
     *
     * @mbggenerated Wed Jul 03 21:35:24 ICT 2013
     */
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