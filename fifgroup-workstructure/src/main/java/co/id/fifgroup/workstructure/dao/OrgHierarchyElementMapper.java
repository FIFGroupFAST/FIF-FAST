package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.OrgHierarchyElement;
import co.id.fifgroup.workstructure.domain.OrgHierarchyElementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrgHierarchyElementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int countByExample(OrgHierarchyElementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int deleteByExample(OrgHierarchyElementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int deleteByPrimaryKey(Long orgHierElementId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int insert(OrgHierarchyElement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int insertSelective(OrgHierarchyElement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    List<OrgHierarchyElement> selectByExampleWithRowbounds(OrgHierarchyElementExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    List<OrgHierarchyElement> selectByExample(OrgHierarchyElementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    OrgHierarchyElement selectByPrimaryKey(Long orgHierElementId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int updateByExampleSelective(@Param("record") OrgHierarchyElement record, @Param("example") OrgHierarchyElementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int updateByExample(@Param("record") OrgHierarchyElement record, @Param("example") OrgHierarchyElementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int updateByPrimaryKeySelective(OrgHierarchyElement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_ORG_HIER_ELEMENTS
     *
     * @mbggenerated Wed Apr 03 21:32:50 ICT 2013
     */
    int updateByPrimaryKey(OrgHierarchyElement record);
}