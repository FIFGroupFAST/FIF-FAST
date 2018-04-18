package co.id.fifgroup.systemadmin.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AuditDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public AuditDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
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
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
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

        public Criteria andAttributeIdIsNull() {
            addCriterion("ATTRIBUTE_ID is null");
            return (Criteria) this;
        }

        public Criteria andAttributeIdIsNotNull() {
            addCriterion("ATTRIBUTE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeIdEqualTo(byte[] value) {
            addCriterion("ATTRIBUTE_ID =", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdNotEqualTo(byte[] value) {
            addCriterion("ATTRIBUTE_ID <>", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdGreaterThan(byte[] value) {
            addCriterion("ATTRIBUTE_ID >", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdGreaterThanOrEqualTo(byte[] value) {
            addCriterion("ATTRIBUTE_ID >=", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdLessThan(byte[] value) {
            addCriterion("ATTRIBUTE_ID <", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdLessThanOrEqualTo(byte[] value) {
            addCriterion("ATTRIBUTE_ID <=", value, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdIn(List<byte[]> values) {
            addCriterion("ATTRIBUTE_ID in", values, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdNotIn(List<byte[]> values) {
            addCriterion("ATTRIBUTE_ID not in", values, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdBetween(byte[] value1, byte[] value2) {
            addCriterion("ATTRIBUTE_ID between", value1, value2, "attributeId");
            return (Criteria) this;
        }

        public Criteria andAttributeIdNotBetween(byte[] value1, byte[] value2) {
            addCriterion("ATTRIBUTE_ID not between", value1, value2, "attributeId");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberIsNull() {
            addCriterion("SEQUENCE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberIsNotNull() {
            addCriterion("SEQUENCE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberEqualTo(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER =", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberNotEqualTo(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER <>", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberGreaterThan(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER >", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER >=", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberLessThan(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER <", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SEQUENCE_NUMBER <=", value, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberIn(List<BigDecimal> values) {
            addCriterion("SEQUENCE_NUMBER in", values, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberNotIn(List<BigDecimal> values) {
            addCriterion("SEQUENCE_NUMBER not in", values, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEQUENCE_NUMBER between", value1, value2, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andSequenceNumberNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SEQUENCE_NUMBER not between", value1, value2, "sequenceNumber");
            return (Criteria) this;
        }

        public Criteria andAttributeNameIsNull() {
            addCriterion("ATTRIBUTE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAttributeNameIsNotNull() {
            addCriterion("ATTRIBUTE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeNameEqualTo(String value) {
            addCriterion("ATTRIBUTE_NAME =", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameNotEqualTo(String value) {
            addCriterion("ATTRIBUTE_NAME <>", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameGreaterThan(String value) {
            addCriterion("ATTRIBUTE_NAME >", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_NAME >=", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameLessThan(String value) {
            addCriterion("ATTRIBUTE_NAME <", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameLessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_NAME <=", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameLike(String value) {
            addCriterion("ATTRIBUTE_NAME like", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameNotLike(String value) {
            addCriterion("ATTRIBUTE_NAME not like", value, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameIn(List<String> values) {
            addCriterion("ATTRIBUTE_NAME in", values, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameNotIn(List<String> values) {
            addCriterion("ATTRIBUTE_NAME not in", values, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_NAME between", value1, value2, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeNameNotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_NAME not between", value1, value2, "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueIsNull() {
            addCriterion("ATTRIBUTE_CURRENT_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueIsNotNull() {
            addCriterion("ATTRIBUTE_CURRENT_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueEqualTo(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE =", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueNotEqualTo(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE <>", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueGreaterThan(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE >", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE >=", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueLessThan(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE <", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueLessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE <=", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueLike(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE like", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueNotLike(String value) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE not like", value, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueIn(List<String> values) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE in", values, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueNotIn(List<String> values) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE not in", values, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE between", value1, value2, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueNotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_CURRENT_VALUE not between", value1, value2, "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueIsNull() {
            addCriterion("ATTRIBUTE_NEW_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueIsNotNull() {
            addCriterion("ATTRIBUTE_NEW_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueEqualTo(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE =", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueNotEqualTo(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE <>", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueGreaterThan(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE >", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE >=", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueLessThan(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE <", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueLessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE <=", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueLike(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE like", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueNotLike(String value) {
            addCriterion("ATTRIBUTE_NEW_VALUE not like", value, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueIn(List<String> values) {
            addCriterion("ATTRIBUTE_NEW_VALUE in", values, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueNotIn(List<String> values) {
            addCriterion("ATTRIBUTE_NEW_VALUE not in", values, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_NEW_VALUE between", value1, value2, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueNotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_NEW_VALUE not between", value1, value2, "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeIsNull() {
            addCriterion("FLAG_RESOLVE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeIsNotNull() {
            addCriterion("FLAG_RESOLVE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeEqualTo(Short value) {
            addCriterion("FLAG_RESOLVE_CODE =", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeNotEqualTo(Short value) {
            addCriterion("FLAG_RESOLVE_CODE <>", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeGreaterThan(Short value) {
            addCriterion("FLAG_RESOLVE_CODE >", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeGreaterThanOrEqualTo(Short value) {
            addCriterion("FLAG_RESOLVE_CODE >=", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeLessThan(Short value) {
            addCriterion("FLAG_RESOLVE_CODE <", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeLessThanOrEqualTo(Short value) {
            addCriterion("FLAG_RESOLVE_CODE <=", value, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeIn(List<Short> values) {
            addCriterion("FLAG_RESOLVE_CODE in", values, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeNotIn(List<Short> values) {
            addCriterion("FLAG_RESOLVE_CODE not in", values, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeBetween(Short value1, Short value2) {
            addCriterion("FLAG_RESOLVE_CODE between", value1, value2, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andFlagResolveCodeNotBetween(Short value1, Short value2) {
            addCriterion("FLAG_RESOLVE_CODE not between", value1, value2, "flagResolveCode");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryIsNull() {
            addCriterion("LOOKUP_CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryIsNotNull() {
            addCriterion("LOOKUP_CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryEqualTo(String value) {
            addCriterion("LOOKUP_CATEGORY =", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryNotEqualTo(String value) {
            addCriterion("LOOKUP_CATEGORY <>", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryGreaterThan(String value) {
            addCriterion("LOOKUP_CATEGORY >", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("LOOKUP_CATEGORY >=", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryLessThan(String value) {
            addCriterion("LOOKUP_CATEGORY <", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryLessThanOrEqualTo(String value) {
            addCriterion("LOOKUP_CATEGORY <=", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryLike(String value) {
            addCriterion("LOOKUP_CATEGORY like", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryNotLike(String value) {
            addCriterion("LOOKUP_CATEGORY not like", value, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryIn(List<String> values) {
            addCriterion("LOOKUP_CATEGORY in", values, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryNotIn(List<String> values) {
            addCriterion("LOOKUP_CATEGORY not in", values, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryBetween(String value1, String value2) {
            addCriterion("LOOKUP_CATEGORY between", value1, value2, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryNotBetween(String value1, String value2) {
            addCriterion("LOOKUP_CATEGORY not between", value1, value2, "lookupCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeNameLikeInsensitive(String value) {
            addCriterion("upper(ATTRIBUTE_NAME) like", value.toUpperCase(), "attributeName");
            return (Criteria) this;
        }

        public Criteria andAttributeCurrentValueLikeInsensitive(String value) {
            addCriterion("upper(ATTRIBUTE_CURRENT_VALUE) like", value.toUpperCase(), "attributeCurrentValue");
            return (Criteria) this;
        }

        public Criteria andAttributeNewValueLikeInsensitive(String value) {
            addCriterion("upper(ATTRIBUTE_NEW_VALUE) like", value.toUpperCase(), "attributeNewValue");
            return (Criteria) this;
        }

        public Criteria andLookupCategoryLikeInsensitive(String value) {
            addCriterion("upper(LOOKUP_CATEGORY) like", value.toUpperCase(), "lookupCategory");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated do_not_delete_during_merge Mon Apr 15 18:20:36 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_AUDIT_DETAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
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