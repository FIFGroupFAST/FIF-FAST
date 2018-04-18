package co.id.fifgroup.systemadmin.domain;

import java.util.ArrayList;
import java.util.List;

public class FunctionAccessExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public FunctionAccessExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
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
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
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
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
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
     * This class corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
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
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("CUSTOM_FUNCTION_ACCESS_ID not between", value1, value2, "id");
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

        public Criteria andFunctionIdIsNull() {
            addCriterion("FUNCTION_ID is null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIsNotNull() {
            addCriterion("FUNCTION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionIdEqualTo(Long value) {
            addCriterion("FUNCTION_ID =", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotEqualTo(Long value) {
            addCriterion("FUNCTION_ID <>", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThan(Long value) {
            addCriterion("FUNCTION_ID >", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("FUNCTION_ID >=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThan(Long value) {
            addCriterion("FUNCTION_ID <", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdLessThanOrEqualTo(Long value) {
            addCriterion("FUNCTION_ID <=", value, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdIn(List<Long> values) {
            addCriterion("FUNCTION_ID in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotIn(List<Long> values) {
            addCriterion("FUNCTION_ID not in", values, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdBetween(Long value1, Long value2) {
            addCriterion("FUNCTION_ID between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andFunctionIdNotBetween(Long value1, Long value2) {
            addCriterion("FUNCTION_ID not between", value1, value2, "functionId");
            return (Criteria) this;
        }

        public Criteria andWritableIsNull() {
            addCriterion("FLAG_WRITABLE is null");
            return (Criteria) this;
        }

        public Criteria andWritableIsNotNull() {
            addCriterion("FLAG_WRITABLE is not null");
            return (Criteria) this;
        }

        public Criteria andWritableEqualTo(boolean value) {
            addCriterion("FLAG_WRITABLE =", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableNotEqualTo(boolean value) {
            addCriterion("FLAG_WRITABLE <>", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableGreaterThan(boolean value) {
            addCriterion("FLAG_WRITABLE >", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_WRITABLE >=", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableLessThan(boolean value) {
            addCriterion("FLAG_WRITABLE <", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_WRITABLE <=", new Boolean(value), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableIn(List<Boolean> values) {
            addCriterion("FLAG_WRITABLE in", values, "writable");
            return (Criteria) this;
        }

        public Criteria andWritableNotIn(List<Boolean> values) {
            addCriterion("FLAG_WRITABLE not in", values, "writable");
            return (Criteria) this;
        }

        public Criteria andWritableBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_WRITABLE between", new Boolean(value1), new Boolean(value2), "writable");
            return (Criteria) this;
        }

        public Criteria andWritableNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_WRITABLE not between", new Boolean(value1), new Boolean(value2), "writable");
            return (Criteria) this;
        }

        public Criteria andEditableIsNull() {
            addCriterion("FLAG_EDITABLE is null");
            return (Criteria) this;
        }

        public Criteria andEditableIsNotNull() {
            addCriterion("FLAG_EDITABLE is not null");
            return (Criteria) this;
        }

        public Criteria andEditableEqualTo(boolean value) {
            addCriterion("FLAG_EDITABLE =", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotEqualTo(boolean value) {
            addCriterion("FLAG_EDITABLE <>", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableGreaterThan(boolean value) {
            addCriterion("FLAG_EDITABLE >", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_EDITABLE >=", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableLessThan(boolean value) {
            addCriterion("FLAG_EDITABLE <", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_EDITABLE <=", new Boolean(value), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableIn(List<Boolean> values) {
            addCriterion("FLAG_EDITABLE in", values, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotIn(List<Boolean> values) {
            addCriterion("FLAG_EDITABLE not in", values, "editable");
            return (Criteria) this;
        }

        public Criteria andEditableBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_EDITABLE between", new Boolean(value1), new Boolean(value2), "editable");
            return (Criteria) this;
        }

        public Criteria andEditableNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_EDITABLE not between", new Boolean(value1), new Boolean(value2), "editable");
            return (Criteria) this;
        }

        public Criteria andErasableIsNull() {
            addCriterion("FLAG_ERASABLE is null");
            return (Criteria) this;
        }

        public Criteria andErasableIsNotNull() {
            addCriterion("FLAG_ERASABLE is not null");
            return (Criteria) this;
        }

        public Criteria andErasableEqualTo(boolean value) {
            addCriterion("FLAG_ERASABLE =", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableNotEqualTo(boolean value) {
            addCriterion("FLAG_ERASABLE <>", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableGreaterThan(boolean value) {
            addCriterion("FLAG_ERASABLE >", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_ERASABLE >=", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableLessThan(boolean value) {
            addCriterion("FLAG_ERASABLE <", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_ERASABLE <=", new Boolean(value), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableIn(List<Boolean> values) {
            addCriterion("FLAG_ERASABLE in", values, "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableNotIn(List<Boolean> values) {
            addCriterion("FLAG_ERASABLE not in", values, "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_ERASABLE between", new Boolean(value1), new Boolean(value2), "erasable");
            return (Criteria) this;
        }

        public Criteria andErasableNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_ERASABLE not between", new Boolean(value1), new Boolean(value2), "erasable");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated do_not_delete_during_merge Tue Feb 19 16:18:33 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
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