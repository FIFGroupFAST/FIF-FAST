package co.id.fifgroup.systemadmin.domain;

import java.util.ArrayList;
import java.util.List;

public class ResponsibilityCompanyExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public ResponsibilityCompanyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
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
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
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
            addCriterion("RESP_COMPANY_ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("RESP_COMPANY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("RESP_COMPANY_ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("RESP_COMPANY_ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("RESP_COMPANY_ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("RESP_COMPANY_ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("RESP_COMPANY_ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("RESP_COMPANY_ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("RESP_COMPANY_ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("RESP_COMPANY_ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("RESP_COMPANY_ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("RESP_COMPANY_ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("COMPANY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("COMPANY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Long value) {
            addCriterion("COMPANY_ID =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Long value) {
            addCriterion("COMPANY_ID <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Long value) {
            addCriterion("COMPANY_ID >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Long value) {
            addCriterion("COMPANY_ID >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Long value) {
            addCriterion("COMPANY_ID <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Long value) {
            addCriterion("COMPANY_ID <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Long> values) {
            addCriterion("COMPANY_ID in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Long> values) {
            addCriterion("COMPANY_ID not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Long value1, Long value2) {
            addCriterion("COMPANY_ID between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Long value1, Long value2) {
            addCriterion("COMPANY_ID not between", value1, value2, "companyId");
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

        public Criteria andSecurityByAssignIsNull() {
            addCriterion("SECURITY_BY_ASSIGN is null");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignIsNotNull() {
            addCriterion("SECURITY_BY_ASSIGN is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignEqualTo(String value) {
            addCriterion("SECURITY_BY_ASSIGN =", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignNotEqualTo(String value) {
            addCriterion("SECURITY_BY_ASSIGN <>", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignGreaterThan(String value) {
            addCriterion("SECURITY_BY_ASSIGN >", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignGreaterThanOrEqualTo(String value) {
            addCriterion("SECURITY_BY_ASSIGN >=", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignLessThan(String value) {
            addCriterion("SECURITY_BY_ASSIGN <", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignLessThanOrEqualTo(String value) {
            addCriterion("SECURITY_BY_ASSIGN <=", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignLike(String value) {
            addCriterion("SECURITY_BY_ASSIGN like", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignNotLike(String value) {
            addCriterion("SECURITY_BY_ASSIGN not like", value, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignIn(List<String> values) {
            addCriterion("SECURITY_BY_ASSIGN in", values, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignNotIn(List<String> values) {
            addCriterion("SECURITY_BY_ASSIGN not in", values, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignBetween(String value1, String value2) {
            addCriterion("SECURITY_BY_ASSIGN between", value1, value2, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andSecurityByAssignNotBetween(String value1, String value2) {
            addCriterion("SECURITY_BY_ASSIGN not between", value1, value2, "securityByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignIsNull() {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN is null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignIsNotNull() {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN is not null");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN =", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignNotEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN <>", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignGreaterThan(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN >", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignGreaterThanOrEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN >=", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignLessThan(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN <", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignLessThanOrEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN <=", value, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignIn(List<Long> values) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN in", values, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignNotIn(List<Long> values) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN not in", values, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignBetween(Long value1, Long value2) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN between", value1, value2, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andOrgLevelIdByAssignNotBetween(Long value1, Long value2) {
            addCriterion("ORG_LEVEL_ID_BY_ASSIGN not between", value1, value2, "orgLevelIdByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignIsNull() {
            addCriterion("FILTER_BY_ASSIGN is null");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignIsNotNull() {
            addCriterion("FILTER_BY_ASSIGN is not null");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignEqualTo(String value) {
            addCriterion("FILTER_BY_ASSIGN =", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignNotEqualTo(String value) {
            addCriterion("FILTER_BY_ASSIGN <>", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignGreaterThan(String value) {
            addCriterion("FILTER_BY_ASSIGN >", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignGreaterThanOrEqualTo(String value) {
            addCriterion("FILTER_BY_ASSIGN >=", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignLessThan(String value) {
            addCriterion("FILTER_BY_ASSIGN <", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignLessThanOrEqualTo(String value) {
            addCriterion("FILTER_BY_ASSIGN <=", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignLike(String value) {
            addCriterion("FILTER_BY_ASSIGN like", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignNotLike(String value) {
            addCriterion("FILTER_BY_ASSIGN not like", value, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignIn(List<String> values) {
            addCriterion("FILTER_BY_ASSIGN in", values, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignNotIn(List<String> values) {
            addCriterion("FILTER_BY_ASSIGN not in", values, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignBetween(String value1, String value2) {
            addCriterion("FILTER_BY_ASSIGN between", value1, value2, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andFilterByAssignNotBetween(String value1, String value2) {
            addCriterion("FILTER_BY_ASSIGN not between", value1, value2, "filterByAssign");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentIsNull() {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN is null");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentIsNotNull() {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN is not null");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN =", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentNotEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN <>", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentGreaterThan(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN >", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN >=", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentLessThan(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN <", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN <=", new Boolean(value), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentIn(List<Boolean> values) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN in", values, "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentNotIn(List<Boolean> values) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN not in", values, "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN between", new Boolean(value1), new Boolean(value2), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andRecursiveByAssignmentNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_RECURSIVE_BY_ASSIGN not between", new Boolean(value1), new Boolean(value2), "recursiveByAssignment");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyIsNull() {
            addCriterion("SECURITY_MULTI_COY is null");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyIsNotNull() {
            addCriterion("SECURITY_MULTI_COY is not null");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyEqualTo(String value) {
            addCriterion("SECURITY_MULTI_COY =", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyNotEqualTo(String value) {
            addCriterion("SECURITY_MULTI_COY <>", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyGreaterThan(String value) {
            addCriterion("SECURITY_MULTI_COY >", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("SECURITY_MULTI_COY >=", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyLessThan(String value) {
            addCriterion("SECURITY_MULTI_COY <", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyLessThanOrEqualTo(String value) {
            addCriterion("SECURITY_MULTI_COY <=", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyLike(String value) {
            addCriterion("SECURITY_MULTI_COY like", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyNotLike(String value) {
            addCriterion("SECURITY_MULTI_COY not like", value, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyIn(List<String> values) {
            addCriterion("SECURITY_MULTI_COY in", values, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyNotIn(List<String> values) {
            addCriterion("SECURITY_MULTI_COY not in", values, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyBetween(String value1, String value2) {
            addCriterion("SECURITY_MULTI_COY between", value1, value2, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andSecurityMultiCompanyNotBetween(String value1, String value2) {
            addCriterion("SECURITY_MULTI_COY not between", value1, value2, "securityMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyIsNull() {
            addCriterion("ORG_LEVEL_ID_MULTI is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyIsNotNull() {
            addCriterion("ORG_LEVEL_ID_MULTI is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI =", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyNotEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI <>", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyGreaterThan(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI >", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyGreaterThanOrEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI >=", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyLessThan(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI <", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyLessThanOrEqualTo(Long value) {
            addCriterion("ORG_LEVEL_ID_MULTI <=", value, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyIn(List<Long> values) {
            addCriterion("ORG_LEVEL_ID_MULTI in", values, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyNotIn(List<Long> values) {
            addCriterion("ORG_LEVEL_ID_MULTI not in", values, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyBetween(Long value1, Long value2) {
            addCriterion("ORG_LEVEL_ID_MULTI between", value1, value2, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andOrganizationLevelIdMultiCompanyNotBetween(Long value1, Long value2) {
            addCriterion("ORG_LEVEL_ID_MULTI not between", value1, value2, "organizationLevelIdMultiCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyIsNull() {
            addCriterion("FILTER_MULTI is null");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyIsNotNull() {
            addCriterion("FILTER_MULTI is not null");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyEqualTo(String value) {
            addCriterion("FILTER_MULTI =", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyNotEqualTo(String value) {
            addCriterion("FILTER_MULTI <>", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyGreaterThan(String value) {
            addCriterion("FILTER_MULTI >", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("FILTER_MULTI >=", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyLessThan(String value) {
            addCriterion("FILTER_MULTI <", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyLessThanOrEqualTo(String value) {
            addCriterion("FILTER_MULTI <=", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyLike(String value) {
            addCriterion("FILTER_MULTI like", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyNotLike(String value) {
            addCriterion("FILTER_MULTI not like", value, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyIn(List<String> values) {
            addCriterion("FILTER_MULTI in", values, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyNotIn(List<String> values) {
            addCriterion("FILTER_MULTI not in", values, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyBetween(String value1, String value2) {
            addCriterion("FILTER_MULTI between", value1, value2, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andFilterMultyCompanyNotBetween(String value1, String value2) {
            addCriterion("FILTER_MULTI not between", value1, value2, "filterMultyCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyIsNull() {
            addCriterion("FLAG_RECURSIVE_MULTI is null");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyIsNotNull() {
            addCriterion("FLAG_RECURSIVE_MULTI is not null");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI =", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyNotEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI <>", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyGreaterThan(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI >", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI >=", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyLessThan(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI <", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_RECURSIVE_MULTI <=", new Boolean(value), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyIn(List<Boolean> values) {
            addCriterion("FLAG_RECURSIVE_MULTI in", values, "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyNotIn(List<Boolean> values) {
            addCriterion("FLAG_RECURSIVE_MULTI not in", values, "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_RECURSIVE_MULTI between", new Boolean(value1), new Boolean(value2), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andRecursiveMultiCompanyNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_RECURSIVE_MULTI not between", new Boolean(value1), new Boolean(value2), "recursiveMultiCompany");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserIsNull() {
            addCriterion("FLAG_OTHER_USER_MULTI is null");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserIsNotNull() {
            addCriterion("FLAG_OTHER_USER_MULTI is not null");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserEqualTo(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI =", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserNotEqualTo(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI <>", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserGreaterThan(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI >", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserGreaterThanOrEqualTo(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI >=", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserLessThan(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI <", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserLessThanOrEqualTo(boolean value) {
            addCriterion("FLAG_OTHER_USER_MULTI <=", new Boolean(value), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserIn(List<Boolean> values) {
            addCriterion("FLAG_OTHER_USER_MULTI in", values, "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserNotIn(List<Boolean> values) {
            addCriterion("FLAG_OTHER_USER_MULTI not in", values, "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_OTHER_USER_MULTI between", new Boolean(value1), new Boolean(value2), "grantAccessToOtherUser");
            return (Criteria) this;
        }

        public Criteria andGrantAccessToOtherUserNotBetween(boolean value1, boolean value2) {
            addCriterion("FLAG_OTHER_USER_MULTI not between", new Boolean(value1), new Boolean(value2), "grantAccessToOtherUser");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated do_not_delete_during_merge Mon Feb 25 18:51:54 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andSecurityByAssignLikeInsensitive(String value) {
            addCriterion("upper(SECURITY_BY_ASSIGN) like", value.toUpperCase(), "securityByAssign");
            return this;
        }

        public Criteria andFilterByAssignLikeInsensitive(String value) {
            addCriterion("upper(FILTER_BY_ASSIGN) like", value.toUpperCase(), "filterByAssign");
            return this;
        }

        public Criteria andSecurityMultiCompanyLikeInsensitive(String value) {
            addCriterion("upper(SECURITY_MULTI_COY) like", value.toUpperCase(), "securityMultiCompany");
            return this;
        }

        public Criteria andFilterMultyCompanyLikeInsensitive(String value) {
            addCriterion("upper(FILTER_MULTI) like", value.toUpperCase(), "filterMultyCompany");
            return this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_RESPONSIBILITY_COMPANIES
     *
     * @mbggenerated Mon Feb 25 18:51:54 ICT 2013
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