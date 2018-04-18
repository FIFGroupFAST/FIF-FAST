package co.id.fifgroup.systemadmin.domain;

import java.util.ArrayList;
import java.util.List;

public class FieldPermissionExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public FieldPermissionExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
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
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
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
            addCriterion("FIELD_PERMISSION_ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("FIELD_PERMISSION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("FIELD_PERMISSION_ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("FIELD_PERMISSION_ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("FIELD_PERMISSION_ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("FIELD_PERMISSION_ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("FIELD_PERMISSION_ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("FIELD_PERMISSION_ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("FIELD_PERMISSION_ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("FIELD_PERMISSION_ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("FIELD_PERMISSION_ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("FIELD_PERMISSION_ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdIsNull() {
            addCriterion("RESPONSIBILITY_ID is null");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdIsNotNull() {
            addCriterion("RESPONSIBILITY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdEqualTo(Long value) {
            addCriterion("RESPONSIBILITY_ID =", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdNotEqualTo(Long value) {
            addCriterion("RESPONSIBILITY_ID <>", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdGreaterThan(Long value) {
            addCriterion("RESPONSIBILITY_ID >", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdGreaterThanOrEqualTo(Long value) {
            addCriterion("RESPONSIBILITY_ID >=", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdLessThan(Long value) {
            addCriterion("RESPONSIBILITY_ID <", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdLessThanOrEqualTo(Long value) {
            addCriterion("RESPONSIBILITY_ID <=", value, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdIn(List<Long> values) {
            addCriterion("RESPONSIBILITY_ID in", values, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdNotIn(List<Long> values) {
            addCriterion("RESPONSIBILITY_ID not in", values, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdBetween(Long value1, Long value2) {
            addCriterion("RESPONSIBILITY_ID between", value1, value2, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andResponsibilityIdNotBetween(Long value1, Long value2) {
            addCriterion("RESPONSIBILITY_ID not between", value1, value2, "responsibilityId");
            return (Criteria) this;
        }

        public Criteria andPermissionNameIsNull() {
            addCriterion("PERMISSION_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPermissionNameIsNotNull() {
            addCriterion("PERMISSION_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPermissionNameEqualTo(String value) {
            addCriterion("PERMISSION_NAME =", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameNotEqualTo(String value) {
            addCriterion("PERMISSION_NAME <>", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameGreaterThan(String value) {
            addCriterion("PERMISSION_NAME >", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameGreaterThanOrEqualTo(String value) {
            addCriterion("PERMISSION_NAME >=", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameLessThan(String value) {
            addCriterion("PERMISSION_NAME <", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameLessThanOrEqualTo(String value) {
            addCriterion("PERMISSION_NAME <=", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameLike(String value) {
            addCriterion("PERMISSION_NAME like", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameNotLike(String value) {
            addCriterion("PERMISSION_NAME not like", value, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameIn(List<String> values) {
            addCriterion("PERMISSION_NAME in", values, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameNotIn(List<String> values) {
            addCriterion("PERMISSION_NAME not in", values, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameBetween(String value1, String value2) {
            addCriterion("PERMISSION_NAME between", value1, value2, "permissionName");
            return (Criteria) this;
        }

        public Criteria andPermissionNameNotBetween(String value1, String value2) {
            addCriterion("PERMISSION_NAME not between", value1, value2, "permissionName");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated do_not_delete_during_merge Tue Feb 19 16:18:33 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andPermissionNameLikeInsensitive(String value) {
            addCriterion("upper(PERMISSION_NAME) like", value.toUpperCase(), "permissionName");
            return this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

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

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value) {
            super();
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }
    }
}