package co.id.fifgroup.systemadmin.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.AuditTrail;
import co.id.fifgroup.systemadmin.domain.AuditTrailExample;

public interface AuditTrailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int countByExample(AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int deleteByPrimaryKey(BigDecimal sequenceNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int insert(AuditTrail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int insertSelective(AuditTrail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    List<AuditTrail> selectByExampleWithBLOBsWithRowbounds(AuditTrailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    List<AuditTrail> selectByExampleWithBLOBs(AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    List<AuditTrail> selectByExampleWithRowbounds(AuditTrailExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    List<AuditTrail> selectByExample(AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    AuditTrail selectByPrimaryKey(BigDecimal sequenceNumber);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByExampleSelective(@Param("record") AuditTrail record, @Param("example") AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByExampleWithBLOBs(@Param("record") AuditTrail record, @Param("example") AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByExample(@Param("record") AuditTrail record, @Param("example") AuditTrailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByPrimaryKeySelective(AuditTrail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByPrimaryKeyWithBLOBs(AuditTrail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_AUDIT_TRAIL
     *
     * @mbggenerated Mon Apr 15 18:20:36 ICT 2013
     */
    int updateByPrimaryKey(AuditTrail record);
}