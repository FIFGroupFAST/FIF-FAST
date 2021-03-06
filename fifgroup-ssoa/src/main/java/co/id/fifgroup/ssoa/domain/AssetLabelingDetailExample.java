package co.id.fifgroup.ssoa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetTransferDetailExample.Criteria;

public class AssetLabelingDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public AssetLabelingDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
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
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
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

        public Criteria andIdIsNull() {
            addCriterion("PARAMETER_DTL_CODE is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("PARAMETER_DTL_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("PARAMETER_DTL_CODE =", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("PARAMETER_DTL_CODE <>", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("PARAMETER_DTL_CODE >", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PARAMETER_DTL_CODE >=", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("PARAMETER_DTL_CODE <", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("PARAMETER_DTL_CODE <=", value, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("PARAMETER_DTL_CODE in", values, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("PARAMETER_DTL_CODE not in", values, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("PARAMETER_DTL_CODE between", value1, value2, "parameterDtlCode");
            return (Criteria) this;
        }
        
        public Criteria andAssetNumberLike(String value) {
            addCriterion("upper(ASSET_NUMBER) like", value.toUpperCase(), "assetNumber");
            return (Criteria) this;
        }
        
        public Criteria andLocationIdLike(Long value) {
            addCriterion("upper(LOCATION_ID) like", value, "locationId");
            return (Criteria) this;
        }
        
        public Criteria andPrintedLike(Long value) {
            addCriterion("upper(PRINTED_COUNT) like", value, "printedCount");
            return (Criteria) this;
        }
        public Criteria andPrintedYesLike(Long value) {
            addCriterion("PRINTED_COUNT >", value, "printedCount");
            return (Criteria) this;
        }
        public Criteria andPrintedNoLike(Long value) {
            addCriterion("PRINTED_COUNT =", value, "printedCount");
            return (Criteria) this;
        }
        
        public Criteria andBranchIdLike(Long value) {
            addCriterion("upper(A.BRANCH_ID) like", value, "branchId");
            return (Criteria) this;
        }
        
        public Criteria andCompanyIdLike(Long value) {
            addCriterion("upper(A.COMPANY_ID) like", value , "companyId");
            return (Criteria) this;
        }
        
        public Criteria andRetiredFlagLike(String value) {
            addCriterion("upper(A.RETIRED_FLAG) like", value.toUpperCase() , "retiredFlag");
            return (Criteria) this;
        }
        
        public Criteria andDatePlaceInServiceBetween(Date value1, Date value2) {
            addCriterion("DATE_PLACED_IN_SERVICE between", value1, value2, "datePlaceInService");
            return (Criteria) this;
        }
        
        public Criteria andDatePlaceInServiceGreaterThan(Date value) {
            addCriterion("TRUNC(DATE_PLACED_IN_SERVICE) >=", value, "datePlaceInService");
            return (Criteria) this;
        }
        
        public Criteria andDatePlaceInServiceLessThan(Date value) {
            addCriterion("TRUNC(DATE_PLACED_IN_SERVICE) <=", value, "datePlaceInService");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("PARAMETER_DTL_CODE not between", value1, value2, "parameterDtlCode");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdIsNull() {
            addCriterion("PARAMETER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdIsNotNull() {
            addCriterion("PARAMETER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdEqualTo(Long id) {
            addCriterion("PARAMETER_CODE =", id, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdNotEqualTo(Long id) {
            addCriterion("PARAMETER_CODE <>", id, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdGreaterThan(Long value) {
            addCriterion("PARAMETER_CODE >", value, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PARAMETER_CODE >=", value, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdLessThan(Long value) {
            addCriterion("PARAMETER_CODE <", value, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdLessThanOrEqualTo(Long value) {
            addCriterion("PARAMETER_CODE <=", value, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdIn(List<Long> values) {
            addCriterion("PARAMETER_CODE in", values, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdNotIn(List<Long> values) {
            addCriterion("PARAMETER_CODE not in", values, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdBetween(Long value1, Long value2) {
            addCriterion("PARAMETER_CODE between", value1, value2, "taskRunnerHdrId");
            return (Criteria) this;
        }

        public Criteria andParameterHdrIdNotBetween(Long value1, Long value2) {
            addCriterion("PARAMETER_CODE not between", value1, value2, "taskRunnerHdrId");
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
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 19 22:59:29 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_PARAMETER_DTL
     *
     * @mbggenerated Tue Mar 19 22:59:29 ICT 2013
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