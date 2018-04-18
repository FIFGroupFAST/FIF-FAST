package co.id.fifgroup.ssoa.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementExample.Criteria;

public class SOPeriodExample {
	protected String orderByClause;
	protected boolean distinct;
	protected List<Criteria> oredCriteria;

	public SOPeriodExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

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
			addCriterion("SO_PERIOD_ID is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("SO_PERIOD_ID is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Long value) {
			addCriterion("SO_PERIOD_ID =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Long value) {
			addCriterion("SO_PERIOD_ID <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Long value) {
			addCriterion("SO_PERIOD_ID >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("SO_PERIOD_ID >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Long value) {
			addCriterion("SO_PERIOD_ID <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("SO_PERIOD_ID <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Long> values) {
			addCriterion("SO_PERIOD_ID in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Long> values) {
			addCriterion("SO_PERIOD_ID not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("SO_PERIOD_ID between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Long value1, Long value2) {
			addCriterion("SO_PERIOD_ID not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNull() {
			addCriterion("DESCRIPTION is null");
			return (Criteria) this;
		}

		public Criteria andDescriptionIsNotNull() {
			addCriterion("DESCRIPTION is not null");
			return (Criteria) this;
		}

		public Criteria andDescriptionEqualTo(String value) {
			addCriterion("DESCRIPTION =", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotEqualTo(String value) {
			addCriterion("DESCRIPTION <>", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThan(String value) {
			addCriterion("DESCRIPTION >", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION >=", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThan(String value) {
			addCriterion("DESCRIPTION <", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionLessThanOrEqualTo(String value) {
			addCriterion("DESCRIPTION <=", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionLike(String value) {
			addCriterion("DESCRIPTION like", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotLike(String value) {
			addCriterion("DESCRIPTION not like", value, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionIn(List<String> values) {
			addCriterion("DESCRIPTION in", values, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotIn(List<String> values) {
			addCriterion("DESCRIPTION not in", values, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionBetween(String value1, String value2) {
			addCriterion("DESCRIPTION between", value1, value2, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andDescriptionNotBetween(String value1, String value2) {
			addCriterion("DESCRIPTION not between", value1, value2, "taskRunnerName");
			return (Criteria) this;
		}

		public Criteria andStartDateIsNull() {
			addCriterion("START_DATE is null");
			return (Criteria) this;
		}

		public Criteria andStartDateIsNotNull() {
			addCriterion("START_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andStartDateEqualTo(Date value) {
			addCriterionForJDBCDate("START_DATE =", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("START_DATE <>", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateGreaterThan(Date value) {
			addCriterionForJDBCDate("START_DATE >", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("HD.START_DATE >=", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateLessThan(Date value) {
			addCriterionForJDBCDate("START_DATE <", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("HD.END_DATE <=", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andStartDateLike(Date value) {
			addCriterionForJDBCDate("START_DATE like", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateNotLike(Date value) {
			addCriterionForJDBCDate("START_DATE not like", value, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateIn(List<Date> values) {
			addCriterionForJDBCDate("START_DATE in", values, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateNotIn(List<Date> values) {
			addCriterionForJDBCDate("START_DATE not in", values, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("START_DATE between", value1, value2, "startDate");
			return (Criteria) this;
		}

		public Criteria andStartDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("START_DATE not between", value1, value2, "startDate");
			return (Criteria) this;
		}

		public Criteria andEndDateIsNull() {
			addCriterion("END_DATE is null");
			return (Criteria) this;
		}

		public Criteria andEndDateIsNotNull() {
			addCriterion("END_DATE is not null");
			return (Criteria) this;
		}

		public Criteria andEndDateEqualTo(Date value) {
			addCriterionForJDBCDate("END_DATE =", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateNotEqualTo(Date value) {
			addCriterionForJDBCDate("END_DATE <>", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateGreaterThan(Date value) {
			addCriterionForJDBCDate("END_DATE >", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("END_DATE >=", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateLessThan(Date value) {
			addCriterionForJDBCDate("END_DATE <", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateLessThanOrEqualTo(Date value) {
			addCriterionForJDBCDate("END_DATE <=", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateLike(Date value) {
			addCriterionForJDBCDate("END_DATE like", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateNotLike(Date value) {
			addCriterionForJDBCDate("END_DATE not like", value, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateIn(List<Date> values) {
			addCriterionForJDBCDate("END_DATE in", values, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateNotIn(List<Date> values) {
			addCriterionForJDBCDate("END_DATE not in", values, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("END_DATE between", value1, value2, "endDate");
			return (Criteria) this;
		}

		public Criteria andEndDateNotBetween(Date value1, Date value2) {
			addCriterionForJDBCDate("END_DATE not between", value1, value2, "endDate");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchIsNull() {
			addCriterion("INVOLVE_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchIsNotNull() {
			addCriterion("INVOLVE_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchEqualTo(Integer value) {
			addCriterion("INVOLVE_BRANCH =", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchNotEqualTo(Integer value) {
			addCriterion("INVOLVE_BRANCH <>", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchGreaterThan(Integer value) {
			addCriterion("INVOLVE_BRANCH >", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("INVOLVE_BRANCH >=", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchLessThan(Integer value) {
			addCriterion("INVOLVE_BRANCH <", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchLessThanOrEqualTo(Integer value) {
			addCriterion("INVOLVE_BRANCH <=", value, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchIn(List<Integer> values) {
			addCriterion("INVOLVE_BRANCH in", values, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchNotIn(List<Integer> values) {
			addCriterion("INVOLVE_BRANCH not in", values, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchBetween(Integer value1, Integer value2) {
			addCriterion("INVOLVE_BRANCH between", value1, value2, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andInvolveBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("INVOLVE_BRANCH not between", value1, value2, "involveBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchIsNull() {
			addCriterion("NOT_STARTED_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchIsNotNull() {
			addCriterion("NOT_STARTED_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchEqualTo(Integer value) {
			addCriterion("NOT_STARTED_BRANCH =", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchNotEqualTo(Integer value) {
			addCriterion("NOT_STARTED_BRANCH <>", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchGreaterThan(Integer value) {
			addCriterion("NOT_STARTED_BRANCH >", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("NOT_STARTED_BRANCH >=", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchLessThan(Integer value) {
			addCriterion("NOT_STARTED_BRANCH <", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchLessThanOrEqualTo(Integer value) {
			addCriterion("NOT_STARTED_BRANCH <=", value, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchIn(List<Integer> values) {
			addCriterion("NOT_STARTED_BRANCH in", values, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andStatusLike(String value) {
            addCriterion("upper(HD.STATUS_CODE) like", value.toUpperCase(), "statusCode");
            return (Criteria) this;
        }
		
		public Criteria andDescriptioLike(String value) {
            addCriterion("upper(HD.DESCRIPTION) like", value.toUpperCase(), "description");
            return (Criteria) this;
        }
        
        public Criteria andCompanyIdLike(String value) {
            addCriterion("upper(HD.COMPANY_ID) like", value.toUpperCase(), "companyId");
            return (Criteria) this;
        }
        
		public Criteria andNotStartedBranchNotIn(List<Integer> values) {
			addCriterion("NOT_STARTED_BRANCH not in", values, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchBetween(Integer value1, Integer value2) {
			addCriterion("NOT_STARTED_BRANCH between", value1, value2, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andNotStartedBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("NOT_STARTED_BRANCH not between", value1, value2, "notStartedBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchIsNull() {
			addCriterion("IN_PROCESS_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchIsNotNull() {
			addCriterion("IN_PROCESS_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchEqualTo(Integer value) {
			addCriterion("IN_PROCESS_BRANCH =", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchNotEqualTo(Integer value) {
			addCriterion("IN_PROCESS_BRANCH <>", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchGreaterThan(Integer value) {
			addCriterion("IN_PROCESS_BRANCH >", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("IN_PROCESS_BRANCH >=", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchLessThan(Integer value) {
			addCriterion("IN_PROCESS_BRANCH <", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchLessThanOrEqualTo(Integer value) {
			addCriterion("IN_PROCESS_BRANCH <=", value, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchIn(List<Integer> values) {
			addCriterion("IN_PROCESS_BRANCH in", values, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchNotIn(List<Integer> values) {
			addCriterion("IN_PROCESS_BRANCH not in", values, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchBetween(Integer value1, Integer value2) {
			addCriterion("IN_PROCESS_BRANCH between", value1, value2, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andInProcessBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("IN_PROCESS_BRANCH not between", value1, value2, "inProcessBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchIsNull() {
			addCriterion("SUBMIT_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchIsNotNull() {
			addCriterion("SUBMIT_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchEqualTo(Integer value) {
			addCriterion("SUBMIT_BRANCH =", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchNotEqualTo(Integer value) {
			addCriterion("SUBMIT_BRANCH <>", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchGreaterThan(Integer value) {
			addCriterion("SUBMIT_BRANCH >", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("SUBMIT_BRANCH >=", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchLessThan(Integer value) {
			addCriterion("SUBMIT_BRANCH <", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchLessThanOrEqualTo(Integer value) {
			addCriterion("SUBMIT_BRANCH <=", value, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchIn(List<Integer> values) {
			addCriterion("SUBMIT_BRANCH in", values, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchNotIn(List<Integer> values) {
			addCriterion("SUBMIT_BRANCH not in", values, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchBetween(Integer value1, Integer value2) {
			addCriterion("SUBMIT_BRANCH between", value1, value2, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andSubmitBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("SUBMIT_BRANCH not between", value1, value2, "submitBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchIsNull() {
			addCriterion("APPROVED_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchIsNotNull() {
			addCriterion("APPROVED_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchEqualTo(Integer value) {
			addCriterion("APPROVED_BRANCH =", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchNotEqualTo(Integer value) {
			addCriterion("APPROVED_BRANCH <>", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchGreaterThan(Integer value) {
			addCriterion("APPROVED_BRANCH >", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("APPROVED_BRANCH >=", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchLessThan(Integer value) {
			addCriterion("APPROVED_BRANCH <", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchLessThanOrEqualTo(Integer value) {
			addCriterion("APPROVED_BRANCH <=", value, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchIn(List<Integer> values) {
			addCriterion("APPROVED_BRANCH in", values, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchNotIn(List<Integer> values) {
			addCriterion("APPROVED_BRANCH not in", values, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchBetween(Integer value1, Integer value2) {
			addCriterion("APPROVED_BRANCH between", value1, value2, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andApprovedBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("APPROVED_BRANCH not between", value1, value2, "approvedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchIsNull() {
			addCriterion("CLOSED_BRANCH is null");
			return (Criteria) this;
		}

		public Criteria andClosedBranchIsNotNull() {
			addCriterion("CLOSED_BRANCH is not null");
			return (Criteria) this;
		}

		public Criteria andClosedBranchEqualTo(Integer value) {
			addCriterion("CLOSED_BRANCH =", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchNotEqualTo(Integer value) {
			addCriterion("CLOSED_BRANCH <>", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchGreaterThan(Integer value) {
			addCriterion("CLOSED_BRANCH >", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchGreaterThanOrEqualTo(Integer value) {
			addCriterion("CLOSED_BRANCH >=", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchLessThan(Integer value) {
			addCriterion("CLOSED_BRANCH <", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchLessThanOrEqualTo(Integer value) {
			addCriterion("CLOSED_BRANCH <=", value, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchIn(List<Integer> values) {
			addCriterion("CLOSED_BRANCH in", values, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchNotIn(List<Integer> values) {
			addCriterion("CLOSED_BRANCH not in", values, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchBetween(Integer value1, Integer value2) {
			addCriterion("CLOSED_BRANCH between", value1, value2, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andClosedBranchNotBetween(Integer value1, Integer value2) {
			addCriterion("CLOSED_BRANCH not between", value1, value2, "closedBranch");
			return (Criteria) this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("STATUS is null");
			return (Criteria) this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("STATUS is not null");
			return (Criteria) this;
		}

		public Criteria andStatusEqualTo(String value) {
			addCriterion("STATUS =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(String value) {
			addCriterion("STATUS <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(String value) {
			addCriterion("STATUS >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(String value) {
			addCriterion("STATUS >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(String value) {
			addCriterion("STATUS <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(String value) {
			addCriterion("STATUS <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<String> values) {
			addCriterion("STATUS in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<String> values) {
			addCriterion("STATUS not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(String value1, String value2) {
			addCriterion("STATUS between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(String value1, String value2) {
			addCriterion("STATUS not between", value1, value2, "status");
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

        public Criteria andLastUpdateByIsNull() {
            addCriterion("LAST_UPDATE_BY is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateByIsNotNull() {
        	addCriterion("LAST_UPDATE_BY is not null");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByEqualTo(Long value) {
        	addCriterion("LAST_UPDATE_BY =", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByNotEqualTo(Long value) {
        	addCriterion("LAST_UPDATE_BY <>", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByGreaterThan(Long value) {
        	addCriterion("LAST_UPDATE_BY >", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByGreaterThanOrEqualTo(Long value) {
        	addCriterion("LAST_UPDATE_BY >=", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByLessThan(Long value) {
        	addCriterion("LAST_UPDATE_BY <", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByLessThanOrEqualTo(Long value) {
        	addCriterion("LAST_UPDATE_BY <=", value, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByIn(List<Long> values) {
        	addCriterion("LAST_UPDATE_BY in", values, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByNotIn(List<Long> values) {
        	addCriterion("LAST_UPDATE_BY not in", values, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByBetween(Long value1, Long value2) {
        	addCriterion("LAST_UPDATE_BY between", value1, value2, "lastUpdateBy");
        	return (Criteria) this;
        }

        public Criteria andLastUpdateByNotBetween(Long value1, Long value2) {
        	addCriterion("LAST_UPDATE_BY not between", value1, value2, "lastUpdateBy");
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

        public Criteria andDescriptionLikeInsensitive(String value) {
        	addCriterion("upper(DESCRIPTION) like", value.toUpperCase(), "taskRunnerName");
        	return (Criteria) this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
        	addCriterion("upper(STATUS) like", value.toUpperCase(), "status");
        	return (Criteria) this;
        }
	}

	public static class Criteria extends GeneratedCriteria {
		protected Criteria() {
			super();
		}
	}

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