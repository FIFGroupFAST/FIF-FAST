package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.Date;

public class City implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String cityCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.CITY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String provCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.DATI2
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String dati2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_CITIES_TEST.DIGIT_ZIP
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String digitZip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.CITY_CODE
     *
     * @return the value of FS_MST_CITIES_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.CITY_CODE
     *
     * @param cityCode the value for FS_MST_CITIES_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.CITY
     *
     * @return the value of FS_MST_CITIES_TEST.CITY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.CITY
     *
     * @param city the value for FS_MST_CITIES_TEST.CITY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.PROV_CODE
     *
     * @return the value of FS_MST_CITIES_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getProvCode() {
        return provCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.PROV_CODE
     *
     * @param provCode the value for FS_MST_CITIES_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode == null ? null : provCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.CREATE_BY
     *
     * @return the value of FS_MST_CITIES_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.CREATE_BY
     *
     * @param createBy the value for FS_MST_CITIES_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.CREATE_DATE
     *
     * @return the value of FS_MST_CITIES_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.CREATE_DATE
     *
     * @param createDate the value for FS_MST_CITIES_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.UPDATE_BY
     *
     * @return the value of FS_MST_CITIES_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.UPDATE_BY
     *
     * @param updateBy the value for FS_MST_CITIES_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.UPDATE_DATE
     *
     * @return the value of FS_MST_CITIES_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.UPDATE_DATE
     *
     * @param updateDate the value for FS_MST_CITIES_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.DATI2
     *
     * @return the value of FS_MST_CITIES_TEST.DATI2
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getDati2() {
        return dati2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.DATI2
     *
     * @param dati2 the value for FS_MST_CITIES_TEST.DATI2
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setDati2(String dati2) {
        this.dati2 = dati2 == null ? null : dati2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_CITIES_TEST.DIGIT_ZIP
     *
     * @return the value of FS_MST_CITIES_TEST.DIGIT_ZIP
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getDigitZip() {
        return digitZip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_CITIES_TEST.DIGIT_ZIP
     *
     * @param digitZip the value for FS_MST_CITIES_TEST.DIGIT_ZIP
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setDigitZip(String digitZip) {
        this.digitZip = digitZip == null ? null : digitZip.trim();
    }
}