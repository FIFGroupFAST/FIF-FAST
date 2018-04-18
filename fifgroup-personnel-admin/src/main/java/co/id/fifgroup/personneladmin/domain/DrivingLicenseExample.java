package co.id.fifgroup.personneladmin.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrivingLicenseExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public DrivingLicenseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
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
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
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

        public Criteria andDrivingLicenseIdIsNull() {
            addCriterion("DRIVING_LICENSE_ID is null");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdIsNotNull() {
            addCriterion("DRIVING_LICENSE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdEqualTo(Long value) {
            addCriterion("DRIVING_LICENSE_ID =", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdNotEqualTo(Long value) {
            addCriterion("DRIVING_LICENSE_ID <>", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdGreaterThan(Long value) {
            addCriterion("DRIVING_LICENSE_ID >", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdGreaterThanOrEqualTo(Long value) {
            addCriterion("DRIVING_LICENSE_ID >=", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdLessThan(Long value) {
            addCriterion("DRIVING_LICENSE_ID <", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdLessThanOrEqualTo(Long value) {
            addCriterion("DRIVING_LICENSE_ID <=", value, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdIn(List<Long> values) {
            addCriterion("DRIVING_LICENSE_ID in", values, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdNotIn(List<Long> values) {
            addCriterion("DRIVING_LICENSE_ID not in", values, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdBetween(Long value1, Long value2) {
            addCriterion("DRIVING_LICENSE_ID between", value1, value2, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andDrivingLicenseIdNotBetween(Long value1, Long value2) {
            addCriterion("DRIVING_LICENSE_ID not between", value1, value2, "drivingLicenseId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIsNull() {
            addCriterion("ACCOUNT_INFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIsNotNull() {
            addCriterion("ACCOUNT_INFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdEqualTo(Long value) {
            addCriterion("ACCOUNT_INFO_ID =", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotEqualTo(Long value) {
            addCriterion("ACCOUNT_INFO_ID <>", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdGreaterThan(Long value) {
            addCriterion("ACCOUNT_INFO_ID >", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ACCOUNT_INFO_ID >=", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdLessThan(Long value) {
            addCriterion("ACCOUNT_INFO_ID <", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdLessThanOrEqualTo(Long value) {
            addCriterion("ACCOUNT_INFO_ID <=", value, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdIn(List<Long> values) {
            addCriterion("ACCOUNT_INFO_ID in", values, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotIn(List<Long> values) {
            addCriterion("ACCOUNT_INFO_ID not in", values, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdBetween(Long value1, Long value2) {
            addCriterion("ACCOUNT_INFO_ID between", value1, value2, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andAccountInfoIdNotBetween(Long value1, Long value2) {
            addCriterion("ACCOUNT_INFO_ID not between", value1, value2, "accountInfoId");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeIsNull() {
            addCriterion("LICENSE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeIsNotNull() {
            addCriterion("LICENSE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeEqualTo(String value) {
            addCriterion("LICENSE_TYPE =", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeNotEqualTo(String value) {
            addCriterion("LICENSE_TYPE <>", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeGreaterThan(String value) {
            addCriterion("LICENSE_TYPE >", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeGreaterThanOrEqualTo(String value) {
            addCriterion("LICENSE_TYPE >=", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeLessThan(String value) {
            addCriterion("LICENSE_TYPE <", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeLessThanOrEqualTo(String value) {
            addCriterion("LICENSE_TYPE <=", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeLike(String value) {
            addCriterion("LICENSE_TYPE like", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeNotLike(String value) {
            addCriterion("LICENSE_TYPE not like", value, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeIn(List<String> values) {
            addCriterion("LICENSE_TYPE in", values, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeNotIn(List<String> values) {
            addCriterion("LICENSE_TYPE not in", values, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeBetween(String value1, String value2) {
            addCriterion("LICENSE_TYPE between", value1, value2, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseTypeNotBetween(String value1, String value2) {
            addCriterion("LICENSE_TYPE not between", value1, value2, "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIsNull() {
            addCriterion("LICENSE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIsNotNull() {
            addCriterion("LICENSE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberEqualTo(String value) {
            addCriterion("LICENSE_NUMBER =", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotEqualTo(String value) {
            addCriterion("LICENSE_NUMBER <>", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberGreaterThan(String value) {
            addCriterion("LICENSE_NUMBER >", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberGreaterThanOrEqualTo(String value) {
            addCriterion("LICENSE_NUMBER >=", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLessThan(String value) {
            addCriterion("LICENSE_NUMBER <", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLessThanOrEqualTo(String value) {
            addCriterion("LICENSE_NUMBER <=", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLike(String value) {
            addCriterion("LICENSE_NUMBER like", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotLike(String value) {
            addCriterion("LICENSE_NUMBER not like", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIn(List<String> values) {
            addCriterion("LICENSE_NUMBER in", values, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotIn(List<String> values) {
            addCriterion("LICENSE_NUMBER not in", values, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberBetween(String value1, String value2) {
            addCriterion("LICENSE_NUMBER between", value1, value2, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotBetween(String value1, String value2) {
            addCriterion("LICENSE_NUMBER not between", value1, value2, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andValidUntilIsNull() {
            addCriterion("VALID_UNTIL is null");
            return (Criteria) this;
        }

        public Criteria andValidUntilIsNotNull() {
            addCriterion("VALID_UNTIL is not null");
            return (Criteria) this;
        }

        public Criteria andValidUntilEqualTo(Date value) {
            addCriterion("VALID_UNTIL =", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilNotEqualTo(Date value) {
            addCriterion("VALID_UNTIL <>", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilGreaterThan(Date value) {
            addCriterion("VALID_UNTIL >", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilGreaterThanOrEqualTo(Date value) {
            addCriterion("VALID_UNTIL >=", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilLessThan(Date value) {
            addCriterion("VALID_UNTIL <", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilLessThanOrEqualTo(Date value) {
            addCriterion("VALID_UNTIL <=", value, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilIn(List<Date> values) {
            addCriterion("VALID_UNTIL in", values, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilNotIn(List<Date> values) {
            addCriterion("VALID_UNTIL not in", values, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilBetween(Date value1, Date value2) {
            addCriterion("VALID_UNTIL between", value1, value2, "validUntil");
            return (Criteria) this;
        }

        public Criteria andValidUntilNotBetween(Date value1, Date value2) {
            addCriterion("VALID_UNTIL not between", value1, value2, "validUntil");
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

        public Criteria andLicenseTypeLikeInsensitive(String value) {
            addCriterion("upper(LICENSE_TYPE) like", value.toUpperCase(), "licenseType");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLikeInsensitive(String value) {
            addCriterion("upper(LICENSE_NUMBER) like", value.toUpperCase(), "licenseNumber");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated do_not_delete_during_merge Thu May 23 20:54:16 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DRIVING_LICENSES
     *
     * @mbggenerated Thu May 23 20:54:16 ICT 2013
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