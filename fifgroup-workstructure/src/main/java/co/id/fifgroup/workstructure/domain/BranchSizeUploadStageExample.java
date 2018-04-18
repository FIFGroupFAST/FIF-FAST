package co.id.fifgroup.workstructure.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BranchSizeUploadStageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public BranchSizeUploadStageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
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
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
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

        public Criteria andOrganizationIdIsNull() {
            addCriterion("ORGANIZATION_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIsNotNull() {
            addCriterion("ORGANIZATION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdEqualTo(Long value) {
            addCriterion("ORGANIZATION_ID =", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotEqualTo(Long value) {
            addCriterion("ORGANIZATION_ID <>", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThan(Long value) {
            addCriterion("ORGANIZATION_ID >", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ORGANIZATION_ID >=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThan(Long value) {
            addCriterion("ORGANIZATION_ID <", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdLessThanOrEqualTo(Long value) {
            addCriterion("ORGANIZATION_ID <=", value, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdIn(List<Long> values) {
            addCriterion("ORGANIZATION_ID in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotIn(List<Long> values) {
            addCriterion("ORGANIZATION_ID not in", values, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdBetween(Long value1, Long value2) {
            addCriterion("ORGANIZATION_ID between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andOrganizationIdNotBetween(Long value1, Long value2) {
            addCriterion("ORGANIZATION_ID not between", value1, value2, "organizationId");
            return (Criteria) this;
        }

        public Criteria andSizeCodeIsNull() {
            addCriterion("SIZE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSizeCodeIsNotNull() {
            addCriterion("SIZE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSizeCodeEqualTo(String value) {
            addCriterion("SIZE_CODE =", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeNotEqualTo(String value) {
            addCriterion("SIZE_CODE <>", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeGreaterThan(String value) {
            addCriterion("SIZE_CODE >", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SIZE_CODE >=", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeLessThan(String value) {
            addCriterion("SIZE_CODE <", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeLessThanOrEqualTo(String value) {
            addCriterion("SIZE_CODE <=", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeLike(String value) {
            addCriterion("SIZE_CODE like", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeNotLike(String value) {
            addCriterion("SIZE_CODE not like", value, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeIn(List<String> values) {
            addCriterion("SIZE_CODE in", values, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeNotIn(List<String> values) {
            addCriterion("SIZE_CODE not in", values, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeBetween(String value1, String value2) {
            addCriterion("SIZE_CODE between", value1, value2, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andSizeCodeNotBetween(String value1, String value2) {
            addCriterion("SIZE_CODE not between", value1, value2, "sizeCode");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateIsNull() {
            addCriterion("EFFECTIVE_START_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateIsNotNull() {
            addCriterion("EFFECTIVE_START_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE =", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE <>", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateGreaterThan(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE >", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE >=", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateLessThan(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE <", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE <=", value, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateIn(List<Date> values) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE in", values, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE not in", values, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE between", value1, value2, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveStartDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("EFFECTIVE_START_DATE not between", value1, value2, "effectiveStartDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateIsNull() {
            addCriterion("EFFECTIVE_END_DATE is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateIsNotNull() {
            addCriterion("EFFECTIVE_END_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE =", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE <>", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateGreaterThan(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE >", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE >=", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateLessThan(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE <", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE <=", value, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateIn(List<Date> values) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE in", values, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE not in", values, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE between", value1, value2, "effectiveEndDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveEndDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("EFFECTIVE_END_DATE not between", value1, value2, "effectiveEndDate");
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

        public Criteria andRowNumberEqualTo(Integer value) {
            addCriterion("ROW_NUMBER =", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotEqualTo(Integer value) {
            addCriterion("ROW_NUMBER <>", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberGreaterThan(Integer value) {
            addCriterion("ROW_NUMBER >", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("ROW_NUMBER >=", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberLessThan(Integer value) {
            addCriterion("ROW_NUMBER <", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberLessThanOrEqualTo(Integer value) {
            addCriterion("ROW_NUMBER <=", value, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberIn(List<Integer> values) {
            addCriterion("ROW_NUMBER in", values, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotIn(List<Integer> values) {
            addCriterion("ROW_NUMBER not in", values, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberBetween(Integer value1, Integer value2) {
            addCriterion("ROW_NUMBER between", value1, value2, "rowNumber");
            return (Criteria) this;
        }

        public Criteria andRowNumberNotBetween(Integer value1, Integer value2) {
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

        public Criteria andIsClosedEqualTo(Boolean value) {
            addCriterion("IS_CLOSED =", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotEqualTo(Boolean value) {
            addCriterion("IS_CLOSED <>", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThan(Boolean value) {
            addCriterion("IS_CLOSED >", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IS_CLOSED >=", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThan(Boolean value) {
            addCriterion("IS_CLOSED <", value, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedLessThanOrEqualTo(Boolean value) {
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

        public Criteria andIsClosedBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_CLOSED between", value1, value2, "isClosed");
            return (Criteria) this;
        }

        public Criteria andIsClosedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_CLOSED not between", value1, value2, "isClosed");
            return (Criteria) this;
        }

        public Criteria andSizeCodeLikeInsensitive(String value) {
            addCriterion("upper(SIZE_CODE) like", value.toUpperCase(), "sizeCode");
            return (Criteria) this;
        }

        public Criteria andRawDataLikeInsensitive(String value) {
            addCriterion("upper(RAW_DATA) like", value.toUpperCase(), "rawData");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated do_not_delete_during_merge Wed Jul 03 16:06:27 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZE_STG
     *
     * @mbggenerated Wed Jul 03 16:06:27 ICT 2013
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