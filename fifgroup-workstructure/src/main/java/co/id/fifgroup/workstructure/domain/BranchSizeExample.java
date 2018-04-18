package co.id.fifgroup.workstructure.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BranchSizeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public BranchSizeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
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
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
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

        public Criteria andIdIsNull() {
            addCriterion("ORG_BRANCH_SIZE_ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ORG_BRANCH_SIZE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ORG_BRANCH_SIZE_ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ORG_BRANCH_SIZE_ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ORG_BRANCH_SIZE_ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ORG_BRANCH_SIZE_ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ORG_BRANCH_SIZE_ID not between", value1, value2, "id");
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

        public Criteria andCreatedByIsNull() {
            addCriterion("CREATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("CREATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("CREATED_BY =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("CREATED_BY <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("CREATED_BY >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("CREATED_BY >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("CREATED_BY <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("CREATED_BY <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("CREATED_BY in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("CREATED_BY not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("CREATED_BY between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("CREATED_BY not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreationDateIsNull() {
            addCriterion("CREATION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreationDateIsNotNull() {
            addCriterion("CREATION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreationDateEqualTo(Date value) {
            addCriterion("CREATION_DATE =", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotEqualTo(Date value) {
            addCriterion("CREATION_DATE <>", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateGreaterThan(Date value) {
            addCriterion("CREATION_DATE >", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATION_DATE >=", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateLessThan(Date value) {
            addCriterion("CREATION_DATE <", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATION_DATE <=", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateIn(List<Date> values) {
            addCriterion("CREATION_DATE in", values, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotIn(List<Date> values) {
            addCriterion("CREATION_DATE not in", values, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateBetween(Date value1, Date value2) {
            addCriterion("CREATION_DATE between", value1, value2, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATION_DATE not between", value1, value2, "creationDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIsNull() {
            addCriterion("LAST_UPDATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIsNotNull() {
            addCriterion("LAST_UPDATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY =", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY <>", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByGreaterThan(Long value) {
            addCriterion("LAST_UPDATED_BY >", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY >=", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByLessThan(Long value) {
            addCriterion("LAST_UPDATED_BY <", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByLessThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY <=", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIn(List<Long> values) {
            addCriterion("LAST_UPDATED_BY in", values, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotIn(List<Long> values) {
            addCriterion("LAST_UPDATED_BY not in", values, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATED_BY between", value1, value2, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATED_BY not between", value1, value2, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("LAST_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("LAST_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(Date value) {
            addCriterion("LAST_UPDATE_DATE >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(Date value) {
            addCriterion("LAST_UPDATE_DATE <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andSizeCodeLikeInsensitive(String value) {
            addCriterion("upper(SIZE_CODE) like", value.toUpperCase(), "sizeCode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated do_not_delete_during_merge Tue Apr 16 12:09:35 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_BRANCH_SIZES
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
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