package co.id.fifgroup.systemadmin.domain;

import java.io.Serializable;
import java.util.Date;

public class MenuDetail implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.MENU_DTL_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.MENU_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Long menuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.MENU_ITEM_TYPE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private String menuItemType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.SEQUENCE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Integer sequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.PROMPT
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private String prompt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.ACTION_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Long actionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Date dateFrom;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.DATE_TO
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Date dateTo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SAM_MENU_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SAM_MENU_DTL
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.MENU_DTL_ID
     *
     * @return the value of SAM_MENU_DTL.MENU_DTL_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.MENU_DTL_ID
     *
     * @param id the value for SAM_MENU_DTL.MENU_DTL_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.MENU_ID
     *
     * @return the value of SAM_MENU_DTL.MENU_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.MENU_ID
     *
     * @param menuId the value for SAM_MENU_DTL.MENU_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.MENU_ITEM_TYPE
     *
     * @return the value of SAM_MENU_DTL.MENU_ITEM_TYPE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public String getMenuItemType() {
        return menuItemType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.MENU_ITEM_TYPE
     *
     * @param menuItemType the value for SAM_MENU_DTL.MENU_ITEM_TYPE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setMenuItemType(String menuItemType) {
        this.menuItemType = menuItemType == null ? null : menuItemType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.SEQUENCE
     *
     * @return the value of SAM_MENU_DTL.SEQUENCE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.SEQUENCE
     *
     * @param sequence the value for SAM_MENU_DTL.SEQUENCE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.PROMPT
     *
     * @return the value of SAM_MENU_DTL.PROMPT
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.PROMPT
     *
     * @param prompt the value for SAM_MENU_DTL.PROMPT
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt == null ? null : prompt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.ACTION_ID
     *
     * @return the value of SAM_MENU_DTL.ACTION_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.ACTION_ID
     *
     * @param actionId the value for SAM_MENU_DTL.ACTION_ID
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.DATE_FROM
     *
     * @return the value of SAM_MENU_DTL.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.DATE_FROM
     *
     * @param dateFrom the value for SAM_MENU_DTL.DATE_FROM
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.DATE_TO
     *
     * @return the value of SAM_MENU_DTL.DATE_TO
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.DATE_TO
     *
     * @param dateTo the value for SAM_MENU_DTL.DATE_TO
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.CREATED_BY
     *
     * @return the value of SAM_MENU_DTL.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.CREATED_BY
     *
     * @param createdBy the value for SAM_MENU_DTL.CREATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.CREATION_DATE
     *
     * @return the value of SAM_MENU_DTL.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.CREATION_DATE
     *
     * @param creationDate the value for SAM_MENU_DTL.CREATION_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.LAST_UPDATED_BY
     *
     * @return the value of SAM_MENU_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for SAM_MENU_DTL.LAST_UPDATED_BY
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SAM_MENU_DTL.LAST_UPDATE_DATE
     *
     * @return the value of SAM_MENU_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SAM_MENU_DTL.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for SAM_MENU_DTL.LAST_UPDATE_DATE
     *
     * @mbggenerated Tue Mar 19 11:30:22 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}