package co.id.fifgroup.ssoa.dao;

import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.domain.SystemPropertyExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SystemPropertyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int countByExample(SystemPropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int deleteByPrimaryKey(String systemPropertyCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int insert(SystemProperty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int insertSelective(SystemProperty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    List<SystemProperty> selectByExampleWithRowbounds(SystemPropertyExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    List<SystemProperty> selectByExample(SystemPropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    SystemProperty selectByPrimaryKey(String systemPropertyCode);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int updateByExampleSelective(@Param("record") SystemProperty record, @Param("example") SystemPropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int updateByExample(@Param("record") SystemProperty record, @Param("example") SystemPropertyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int updateByPrimaryKeySelective(SystemProperty record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_MODULES
     *
     * @mbggenerated Wed Mar 13 19:09:01 ICT 2013
     */
    int updateByPrimaryKey(SystemProperty record);
}