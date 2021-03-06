package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.OrganizationHierarchy;
import co.id.fifgroup.workstructure.domain.OrganizationHierarchyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrganizationHierarchyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int countByExample(OrganizationHierarchyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int insert(OrganizationHierarchy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int insertSelective(OrganizationHierarchy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    List<OrganizationHierarchy> selectByExampleWithRowbounds(OrganizationHierarchyExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    List<OrganizationHierarchy> selectByExample(OrganizationHierarchyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    OrganizationHierarchy selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int updateByExampleSelective(@Param("record") OrganizationHierarchy record, @Param("example") OrganizationHierarchyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int updateByExample(@Param("record") OrganizationHierarchy record, @Param("example") OrganizationHierarchyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int updateByPrimaryKeySelective(OrganizationHierarchy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORGANIZATION_HIER
     *
     * @mbggenerated Tue Apr 16 12:09:35 ICT 2013
     */
    int updateByPrimaryKey(OrganizationHierarchy record);
}