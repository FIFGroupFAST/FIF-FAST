package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemadmin.domain.FieldPermission;
import co.id.fifgroup.systemadmin.domain.FieldPermissionExample;

public interface FieldPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int countByExample(FieldPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int insert(FieldPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int insertSelective(FieldPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    List<FieldPermission> selectByExample(FieldPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    FieldPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByExampleSelective(@Param("record") FieldPermission record, @Param("example") FieldPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByExample(@Param("record") FieldPermission record, @Param("example") FieldPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByPrimaryKeySelective(FieldPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_FIELD_PERMISSIONS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByPrimaryKey(FieldPermission record);
}