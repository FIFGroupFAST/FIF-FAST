package co.id.fifgroup.core.domain;

import java.io.Serializable;
import java.util.Date;

public class ZipCode implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String zipCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.SUB_ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String subZipCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.ZIP_DESC
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String zipDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String provinsiCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String cityCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.KEC_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String kecamatanCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.KEL_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String kelurahanCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private String updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column FS_MST_ZIP_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.ZIP_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.ZIP_CODE
     *
     * @param zipCode the value for FS_MST_ZIP_TEST.ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.SUB_ZIP_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.SUB_ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getSubZipCode() {
        return subZipCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.SUB_ZIP_CODE
     *
     * @param subZipCode the value for FS_MST_ZIP_TEST.SUB_ZIP_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setSubZipCode(String subZipCode) {
        this.subZipCode = subZipCode == null ? null : subZipCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.ZIP_DESC
     *
     * @return the value of FS_MST_ZIP_TEST.ZIP_DESC
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getZipDesc() {
        return zipDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.ZIP_DESC
     *
     * @param zipDesc the value for FS_MST_ZIP_TEST.ZIP_DESC
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setZipDesc(String zipDesc) {
        this.zipDesc = zipDesc == null ? null : zipDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.PROV_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getProvinsiCode() {
        return provinsiCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.PROV_CODE
     *
     * @param provinsiCode the value for FS_MST_ZIP_TEST.PROV_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setProvinsiCode(String provinsiCode) {
        this.provinsiCode = provinsiCode == null ? null : provinsiCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.CITY_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.CITY_CODE
     *
     * @param cityCode the value for FS_MST_ZIP_TEST.CITY_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.KEC_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.KEC_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getKecamatanCode() {
        return kecamatanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.KEC_CODE
     *
     * @param kecamatanCode the value for FS_MST_ZIP_TEST.KEC_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setKecamatanCode(String kecamatanCode) {
        this.kecamatanCode = kecamatanCode == null ? null : kecamatanCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.KEL_CODE
     *
     * @return the value of FS_MST_ZIP_TEST.KEL_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getKelurahanCode() {
        return kelurahanCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.KEL_CODE
     *
     * @param kelurahanCode the value for FS_MST_ZIP_TEST.KEL_CODE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setKelurahanCode(String kelurahanCode) {
        this.kelurahanCode = kelurahanCode == null ? null : kelurahanCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.CREATE_BY
     *
     * @return the value of FS_MST_ZIP_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.CREATE_BY
     *
     * @param createBy the value for FS_MST_ZIP_TEST.CREATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.CREATE_DATE
     *
     * @return the value of FS_MST_ZIP_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.CREATE_DATE
     *
     * @param createDate the value for FS_MST_ZIP_TEST.CREATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.UPDATE_BY
     *
     * @return the value of FS_MST_ZIP_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.UPDATE_BY
     *
     * @param updateBy the value for FS_MST_ZIP_TEST.UPDATE_BY
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FS_MST_ZIP_TEST.UPDATE_DATE
     *
     * @return the value of FS_MST_ZIP_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FS_MST_ZIP_TEST.UPDATE_DATE
     *
     * @param updateDate the value for FS_MST_ZIP_TEST.UPDATE_DATE
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}