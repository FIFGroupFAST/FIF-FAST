package co.id.fifgroup.ssoa.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.id.fifgroup.core.domain.transaction.AbstractCommonTrx;


public class AssetLabeling extends AbstractCommonTrx {
	private boolean check;
	
	private String action;
	private String no_asset;
	private String has_printed;
	private String reprinting_reason;	
	private String type;
	private String branch_code;	
	private String branch_name;
	private String branchName;
	private String location_code;	
	private String location_name;
	private String company_id;
	private String item_category;	
	private String date_of_service;
	private String serial_number;
	private String own_type;
	private String update_by;
	private String update_date;
	private String assetNumber;
	
	private InputStream logoLabel;
	
	private Long   labelingId;
	private Date   labelingDate;
	private Long   branchId;
	private Long   companyId;
	private String description;	
	private Long   createdBy;
	private Date   creationDate;
	private Long   lastUpdateBy;
	private Date   lastUpdateDate;
	private String labeling;
	private String fullName;
	private Date   startDate;
	private Date   endDate;
	
	
	
	public AssetLabeling() {
		
	}
	
	
	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public AssetLabeling(boolean check, String action, String no_asset, String has_printed, String reprinting_reason, String type, String branch_code,
			String branch_name, String location_code, String location_name, String company_id, String item_category, String date_of_service,
			String description, String serial_number, String own_type, String update_by, String update_date) {
		super();
		this.check = check;
		this.action = action;
		this.no_asset = no_asset;
		this.has_printed = has_printed;
		this.reprinting_reason = reprinting_reason;
		this.type = type;
		this.branch_code = branch_code;
		this.branch_name = branch_name;
		this.location_code = location_code;
		this.location_name = location_name;
		this.company_id = company_id;
		this.item_category = item_category;
		this.date_of_service = date_of_service;
		this.description = description;
		this.serial_number = serial_number;
		this.own_type = own_type;
		this.update_by = update_by;
		this.update_date = update_date;
	}

	
	
	public InputStream getLogoLabel() {
		return logoLabel;
	}

	public void setLogoLabel(InputStream logoLabel) {
		this.logoLabel = logoLabel;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}


	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getNo_asset() {
		return no_asset;
	}

	public void setNo_asset(String no_asset) {
		this.no_asset = no_asset;
	}

	public String getHas_printed() {
		return has_printed;
	}

	public void setHas_printed(String has_printed) {
		this.has_printed = has_printed;
	}

	public String getReprinting_reason() {
		return reprinting_reason;
	}

	public void setReprinting_reason(String reprinting_reason) {
		this.reprinting_reason = reprinting_reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}

	public String getDate_of_service() {
		return date_of_service;
	}

	public void setDate_of_service(String date_of_service) {
		this.date_of_service = date_of_service;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getOwn_type() {
		return own_type;
	}

	public void setOwn_type(String own_type) {
		this.own_type = own_type;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public Long getLabelingId() {
		return labelingId;
	}

	public void setLabelingId(Long labelingId) {
		this.labelingId = labelingId;
	}

	public Date getLabelingDate() {
		return labelingDate;
	}

	public void setLabelingDate(Date labelingDate) {
		this.labelingDate = labelingDate;
	}

	
	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getLabeling() {
		return labeling;
	}

	public void setLabeling(String labeling) {
		this.labeling = labeling;
	}
	
	  public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
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
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
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
            addCriterion("MENU_ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("MENU_ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("MENU_ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("MENU_ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("MENU_ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("MENU_ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("MENU_ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("MENU_ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("MENU_ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("MENU_ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("MENU_ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("MENU_ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNull() {
            addCriterion("MENU_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMenuNameIsNotNull() {
            addCriterion("MENU_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMenuNameEqualTo(String value) {
            addCriterion("MENU_NAME =", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotEqualTo(String value) {
            addCriterion("MENU_NAME <>", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThan(String value) {
            addCriterion("MENU_NAME >", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("MENU_NAME >=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThan(String value) {
            addCriterion("MENU_NAME <", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLessThanOrEqualTo(String value) {
            addCriterion("MENU_NAME <=", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameLike(String value) {
            addCriterion("MENU_NAME like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotLike(String value) {
            addCriterion("MENU_NAME not like", value, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameIn(List<String> values) {
            addCriterion("MENU_NAME in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotIn(List<String> values) {
            addCriterion("MENU_NAME not in", values, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameBetween(String value1, String value2) {
            addCriterion("MENU_NAME between", value1, value2, "menuName");
            return (Criteria) this;
        }

        public Criteria andMenuNameNotBetween(String value1, String value2) {
            addCriterion("MENU_NAME not between", value1, value2, "menuName");
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


        public Criteria andDescriptionLike(String value) {
            addCriterion("DESCRIPTION like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("DESCRIPTION not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("DESCRIPTION in", values, "description");
            return (Criteria) this;
        }

      

        public Criteria andDateFromIsNull() {
            addCriterion("DATE_FROM is null");
            return (Criteria) this;
        }

        public Criteria andDateFromIsNotNull() {
            addCriterion("DATE_FROM is not null");
            return (Criteria) this;
        }

        public Criteria andDateFromEqualTo(Date value) {
            addCriterion("DATE_FROM =", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromNotEqualTo(Date value) {
            addCriterion("DATE_FROM <>", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromGreaterThan(Date value) {
            addCriterion("DATE_FROM >", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromGreaterThanOrEqualTo(Date value) {
            addCriterion("DATE_FROM >=", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromLessThan(Date value) {
            addCriterion("DATE_FROM <", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromLessThanOrEqualTo(Date value) {
            addCriterion("DATE_FROM <=", value, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromIn(List<Date> values) {
            addCriterion("DATE_FROM in", values, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromNotIn(List<Date> values) {
            addCriterion("DATE_FROM not in", values, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromBetween(Date value1, Date value2) {
            addCriterion("DATE_FROM between", value1, value2, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateFromNotBetween(Date value1, Date value2) {
            addCriterion("DATE_FROM not between", value1, value2, "dateFrom");
            return (Criteria) this;
        }

        public Criteria andDateToIsNull() {
            addCriterion("DATE_TO is null");
            return (Criteria) this;
        }

        public Criteria andDateToIsNotNull() {
            addCriterion("DATE_TO is not null");
            return (Criteria) this;
        }

        public Criteria andDateToEqualTo(Date value) {
            addCriterion("DATE_TO =", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToNotEqualTo(Date value) {
            addCriterion("DATE_TO <>", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToGreaterThan(Date value) {
            addCriterion("DATE_TO >", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToGreaterThanOrEqualTo(Date value) {
            addCriterion("DATE_TO >=", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToLessThan(Date value) {
            addCriterion("DATE_TO <", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToLessThanOrEqualTo(Date value) {
            addCriterion("DATE_TO <=", value, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToIn(List<Date> values) {
            addCriterion("DATE_TO in", values, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToNotIn(List<Date> values) {
            addCriterion("DATE_TO not in", values, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToBetween(Date value1, Date value2) {
            addCriterion("DATE_TO between", value1, value2, "dateTo");
            return (Criteria) this;
        }

        public Criteria andDateToNotBetween(Date value1, Date value2) {
            addCriterion("DATE_TO not between", value1, value2, "dateTo");
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

        public Criteria andlastUpdateByIsNull() {
            addCriterion("LAST_UPDATE_BY is null");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByIsNotNull() {
            addCriterion("LAST_UPDATE_BY is not null");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByEqualTo(Long value) {
            addCriterion("LAST_UPDATE_BY =", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByNotEqualTo(Long value) {
            addCriterion("LAST_UPDATE_BY <>", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByGreaterThan(Long value) {
            addCriterion("LAST_UPDATE_BY >", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByGreaterThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATE_BY >=", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByLessThan(Long value) {
            addCriterion("LAST_UPDATE_BY <", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByLessThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATE_BY <=", value, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByIn(List<Long> values) {
            addCriterion("LAST_UPDATE_BY in", values, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByNotIn(List<Long> values) {
            addCriterion("LAST_UPDATE_BY not in", values, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATE_BY between", value1, value2, "lastUpdateBy");
            return (Criteria) this;
        }

        public Criteria andlastUpdateByNotBetween(Long value1, Long value2) {
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

        public Criteria andMenuNameLikeInsensitive(String value) {
            addCriterion("upper(MENU_NAME) like", value.toUpperCase(), "menuName");
            return (Criteria) this;
        }

        public Criteria andDescriptionLikeInsensitive(String value) {
            addCriterion("upper(DESCRIPTION) like", value.toUpperCase(), "description");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 19 11:30:22 ICT 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SAM_MENU_HDR
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
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
