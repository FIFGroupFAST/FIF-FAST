package co.id.fifgroup.workstructure.domain;

import java.io.Serializable;
import java.util.Date;

public class GradeSetElement implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ELEMENT_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long elementId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.GRADE_SEQUENCE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long gradeSequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.GRADE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long gradeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.CONCORDANCE_LEVEL
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long concordanceLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long createdBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date creationDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Long lastUpdatedBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private Date lastUpdateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WOS_GRADE_SET_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ELEMENT_ID
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.GRADE_SET_ELEMENT_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getElementId() {
        return elementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ELEMENT_ID
     *
     * @param elementId the value for WOS_GRADE_SET_ELEMENTS.GRADE_SET_ELEMENT_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ID
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SET_ID
     *
     * @param id the value for WOS_GRADE_SET_ELEMENTS.GRADE_SET_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SEQUENCE
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.GRADE_SEQUENCE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getGradeSequence() {
        return gradeSequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_SEQUENCE
     *
     * @param gradeSequence the value for WOS_GRADE_SET_ELEMENTS.GRADE_SEQUENCE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setGradeSequence(Long gradeSequence) {
        this.gradeSequence = gradeSequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_ID
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.GRADE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getGradeId() {
        return gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.GRADE_ID
     *
     * @param gradeId the value for WOS_GRADE_SET_ELEMENTS.GRADE_ID
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.CONCORDANCE_LEVEL
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.CONCORDANCE_LEVEL
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getConcordanceLevel() {
        return concordanceLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.CONCORDANCE_LEVEL
     *
     * @param concordanceLevel the value for WOS_GRADE_SET_ELEMENTS.CONCORDANCE_LEVEL
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setConcordanceLevel(Long concordanceLevel) {
        this.concordanceLevel = concordanceLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.CREATED_BY
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.CREATED_BY
     *
     * @param createdBy the value for WOS_GRADE_SET_ELEMENTS.CREATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.CREATION_DATE
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.CREATION_DATE
     *
     * @param creationDate the value for WOS_GRADE_SET_ELEMENTS.CREATION_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATED_BY
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATED_BY
     *
     * @param lastUpdatedBy the value for WOS_GRADE_SET_ELEMENTS.LAST_UPDATED_BY
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATE_DATE
     *
     * @return the value of WOS_GRADE_SET_ELEMENTS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column WOS_GRADE_SET_ELEMENTS.LAST_UPDATE_DATE
     *
     * @param lastUpdateDate the value for WOS_GRADE_SET_ELEMENTS.LAST_UPDATE_DATE
     *
     * @mbggenerated Wed Apr 03 21:32:49 ICT 2013
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}