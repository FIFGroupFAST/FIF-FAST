package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.SpecialPermission;
import co.id.fifgroup.systemadmin.domain.SpecialPermissionExample;

public interface SpecialPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int countByExample(SpecialPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int deleteByPrimaryKey(String permissionName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int insert(SpecialPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int insertSelective(SpecialPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    List<SpecialPermission> selectByExampleWithRowbounds(SpecialPermissionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    List<SpecialPermission> selectByExample(SpecialPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    SpecialPermission selectByPrimaryKey(String permissionName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int updateByExampleSelective(@Param("record") SpecialPermission record, @Param("example") SpecialPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int updateByExample(@Param("record") SpecialPermission record, @Param("example") SpecialPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int updateByPrimaryKeySelective(SpecialPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_SPECIAL_PERMISSIONS
     *
     * @mbggenerated Wed Jan 15 10:27:06 ICT 2014
     */
    int updateByPrimaryKey(SpecialPermission record);
}