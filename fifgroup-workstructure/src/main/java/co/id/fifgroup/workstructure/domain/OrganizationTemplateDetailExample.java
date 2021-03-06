
package co.id.fifgroup.workstructure.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrganizationTemplateDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public OrganizationTemplateDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
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
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
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

        public Criteria andTemplateDtlIdIsNull() {
            addCriterion("TEMPLATE_DTL_ID is null");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdIsNotNull() {
            addCriterion("TEMPLATE_DTL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdEqualTo(Long value) {
            addCriterion("TEMPLATE_DTL_ID =", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdNotEqualTo(Long value) {
            addCriterion("TEMPLATE_DTL_ID <>", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdGreaterThan(Long value) {
            addCriterion("TEMPLATE_DTL_ID >", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TEMPLATE_DTL_ID >=", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdLessThan(Long value) {
            addCriterion("TEMPLATE_DTL_ID <", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdLessThanOrEqualTo(Long value) {
            addCriterion("TEMPLATE_DTL_ID <=", value, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdIn(List<Long> values) {
            addCriterion("TEMPLATE_DTL_ID in", values, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdNotIn(List<Long> values) {
            addCriterion("TEMPLATE_DTL_ID not in", values, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdBetween(Long value1, Long value2) {
            addCriterion("TEMPLATE_DTL_ID between", value1, value2, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateDtlIdNotBetween(Long value1, Long value2) {
            addCriterion("TEMPLATE_DTL_ID not between", value1, value2, "templateDtlId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("TEMPLATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("TEMPLATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Long value) {
            addCriterion("TEMPLATE_ID =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Long value) {
            addCriterion("TEMPLATE_ID <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Long value) {
            addCriterion("TEMPLATE_ID >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TEMPLATE_ID >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Long value) {
            addCriterion("TEMPLATE_ID <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Long value) {
            addCriterion("TEMPLATE_ID <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Long> values) {
            addCriterion("TEMPLATE_ID in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Long> values) {
            addCriterion("TEMPLATE_ID not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Long value1, Long value2) {
            addCriterion("TEMPLATE_ID between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Long value1, Long value2) {
            addCriterion("TEMPLATE_ID not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNull() {
            addCriterion("ORG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgNameIsNotNull() {
            addCriterion("ORG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNameEqualTo(String value) {
            addCriterion("ORG_NAME =", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotEqualTo(String value) {
            addCriterion("ORG_NAME <>", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThan(String value) {
            addCriterion("ORG_NAME >", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_NAME >=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThan(String value) {
            addCriterion("ORG_NAME <", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLessThanOrEqualTo(String value) {
            addCriterion("ORG_NAME <=", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameLike(String value) {
            addCriterion("ORG_NAME like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotLike(String value) {
            addCriterion("ORG_NAME not like", value, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameIn(List<String> values) {
            addCriterion("ORG_NAME in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotIn(List<String> values) {
            addCriterion("ORG_NAME not in", values, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameBetween(String value1, String value2) {
            addCriterion("ORG_NAME between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andOrgNameNotBetween(String value1, String value2) {
            addCriterion("ORG_NAME not between", value1, value2, "orgName");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIsNull() {
            addCriterion("LEVEL_CODE is null");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIsNotNull() {
            addCriterion("LEVEL_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andLevelCodeEqualTo(String value) {
            addCriterion("LEVEL_CODE =", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotEqualTo(String value) {
            addCriterion("LEVEL_CODE <>", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeGreaterThan(String value) {
            addCriterion("LEVEL_CODE >", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("LEVEL_CODE >=", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLessThan(String value) {
            addCriterion("LEVEL_CODE <", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLessThanOrEqualTo(String value) {
            addCriterion("LEVEL_CODE <=", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLike(String value) {
            addCriterion("LEVEL_CODE like", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotLike(String value) {
            addCriterion("LEVEL_CODE not like", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIn(List<String> values) {
            addCriterion("LEVEL_CODE in", values, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotIn(List<String> values) {
            addCriterion("LEVEL_CODE not in", values, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeBetween(String value1, String value2) {
            addCriterion("LEVEL_CODE between", value1, value2, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotBetween(String value1, String value2) {
            addCriterion("LEVEL_CODE not between", value1, value2, "levelCode");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdIsNull() {
            addCriterion("ORG_HEAD_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdIsNotNull() {
            addCriterion("ORG_HEAD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdEqualTo(Long value) {
            addCriterion("ORG_HEAD_ID =", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdNotEqualTo(Long value) {
            addCriterion("ORG_HEAD_ID <>", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdGreaterThan(Long value) {
            addCriterion("ORG_HEAD_ID >", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ORG_HEAD_ID >=", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdLessThan(Long value) {
            addCriterion("ORG_HEAD_ID <", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdLessThanOrEqualTo(Long value) {
            addCriterion("ORG_HEAD_ID <=", value, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdIn(List<Long> values) {
            addCriterion("ORG_HEAD_ID in", values, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdNotIn(List<Long> values) {
            addCriterion("ORG_HEAD_ID not in", values, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdBetween(Long value1, Long value2) {
            addCriterion("ORG_HEAD_ID between", value1, value2, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andOrgHeadIdNotBetween(Long value1, Long value2) {
            addCriterion("ORG_HEAD_ID not between", value1, value2, "orgHeadId");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeIsNull() {
            addCriterion("COST_CENTER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeIsNotNull() {
            addCriterion("COST_CENTER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeEqualTo(String value) {
            addCriterion("COST_CENTER_CODE =", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeNotEqualTo(String value) {
            addCriterion("COST_CENTER_CODE <>", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeGreaterThan(String value) {
            addCriterion("COST_CENTER_CODE >", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeGreaterThanOrEqualTo(String value) {
            addCriterion("COST_CENTER_CODE >=", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeLessThan(String value) {
            addCriterion("COST_CENTER_CODE <", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeLessThanOrEqualTo(String value) {
            addCriterion("COST_CENTER_CODE <=", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeLike(String value) {
            addCriterion("COST_CENTER_CODE like", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeNotLike(String value) {
            addCriterion("COST_CENTER_CODE not like", value, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeIn(List<String> values) {
            addCriterion("COST_CENTER_CODE in", values, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeNotIn(List<String> values) {
            addCriterion("COST_CENTER_CODE not in", values, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeBetween(String value1, String value2) {
            addCriterion("COST_CENTER_CODE between", value1, value2, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeNotBetween(String value1, String value2) {
            addCriterion("COST_CENTER_CODE not between", value1, value2, "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeIsNull() {
            addCriterion("PREFIX_CODE is null");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeIsNotNull() {
            addCriterion("PREFIX_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeEqualTo(String value) {
            addCriterion("PREFIX_CODE =", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeNotEqualTo(String value) {
            addCriterion("PREFIX_CODE <>", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeGreaterThan(String value) {
            addCriterion("PREFIX_CODE >", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PREFIX_CODE >=", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeLessThan(String value) {
            addCriterion("PREFIX_CODE <", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeLessThanOrEqualTo(String value) {
            addCriterion("PREFIX_CODE <=", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeLike(String value) {
            addCriterion("PREFIX_CODE like", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeNotLike(String value) {
            addCriterion("PREFIX_CODE not like", value, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeIn(List<String> values) {
            addCriterion("PREFIX_CODE in", values, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeNotIn(List<String> values) {
            addCriterion("PREFIX_CODE not in", values, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeBetween(String value1, String value2) {
            addCriterion("PREFIX_CODE between", value1, value2, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeNotBetween(String value1, String value2) {
            addCriterion("PREFIX_CODE not between", value1, value2, "prefixCode");
            return (Criteria) this;
        }

        public Criteria andIsParentIsNull() {
            addCriterion("IS_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andIsParentIsNotNull() {
            addCriterion("IS_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andIsParentEqualTo(boolean value) {
            addCriterion("IS_PARENT =", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotEqualTo(boolean value) {
            addCriterion("IS_PARENT <>", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentGreaterThan(boolean value) {
            addCriterion("IS_PARENT >", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentGreaterThanOrEqualTo(boolean value) {
            addCriterion("IS_PARENT >=", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentLessThan(boolean value) {
            addCriterion("IS_PARENT <", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentLessThanOrEqualTo(boolean value) {
            addCriterion("IS_PARENT <=", value, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentIn(List<Boolean> values) {
            addCriterion("IS_PARENT in", values, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotIn(List<Boolean> values) {
            addCriterion("IS_PARENT not in", values, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentBetween(boolean value1, boolean value2) {
            addCriterion("IS_PARENT between", value1, value2, "isParent");
            return (Criteria) this;
        }

        public Criteria andIsParentNotBetween(boolean value1, boolean value2) {
            addCriterion("IS_PARENT not between", value1, value2, "isParent");
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

        public Criteria andOrgNameLikeInsensitive(String value) {
            addCriterion("upper(ORG_NAME) like", value.toUpperCase(), "orgName");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLikeInsensitive(String value) {
            addCriterion("upper(LEVEL_CODE) like", value.toUpperCase(), "levelCode");
            return (Criteria) this;
        }

        public Criteria andCostCenterCodeLikeInsensitive(String value) {
            addCriterion("upper(COST_CENTER_CODE) like", value.toUpperCase(), "costCenterCode");
            return (Criteria) this;
        }

        public Criteria andPrefixCodeLikeInsensitive(String value) {
            addCriterion("upper(PREFIX_CODE) like", value.toUpperCase(), "prefixCode");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated do_not_delete_during_merge Tue May 13 14:29:08 ICT 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WOS_ORG_TEMPLATE_DTL
     *
     * @mbggenerated Tue May 13 14:29:08 ICT 2014
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