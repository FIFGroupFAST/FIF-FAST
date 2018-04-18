package co.id.fifgroup.personneladmin.domain;

import java.util.ArrayList;
import java.util.List;

public class DpaAccountStageExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public DpaAccountStageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
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
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
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

        public Criteria andUserNameIsNull() {
            addCriterion("USER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("USER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("USER_NAME =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("USER_NAME <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("USER_NAME >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("USER_NAME >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("USER_NAME <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("USER_NAME <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("USER_NAME like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("USER_NAME not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("USER_NAME in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("USER_NAME not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("USER_NAME between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("USER_NAME not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("PASSWORD is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("PASSWORD is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("PASSWORD =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("PASSWORD <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("PASSWORD >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("PASSWORD >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("PASSWORD <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("PASSWORD <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("PASSWORD like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("PASSWORD not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("PASSWORD in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("PASSWORD not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("PASSWORD between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("PASSWORD not between", value1, value2, "password");
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

        public Criteria andUserNameLikeInsensitive(String value) {
            addCriterion("upper(USER_NAME) like", value.toUpperCase(), "userName");
            return (Criteria) this;
        }

        public Criteria andPasswordLikeInsensitive(String value) {
            addCriterion("upper(PASSWORD) like", value.toUpperCase(), "password");
            return (Criteria) this;
        }

        public Criteria andEmployeeNumberLikeInsensitive(String value) {
            addCriterion("upper(EMPLOYEE_NUMBER) like", value.toUpperCase(), "employeeNumber");
            return (Criteria) this;
        }

        public Criteria andRawDataLikeInsensitive(String value) {
            addCriterion("upper(RAW_DATA) like", value.toUpperCase(), "rawData");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated do_not_delete_during_merge Wed Jul 03 21:36:17 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PEA_DPA_ACCOUNT_STG
     *
     * @mbggenerated Wed Jul 03 21:36:17 ICT 2013
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