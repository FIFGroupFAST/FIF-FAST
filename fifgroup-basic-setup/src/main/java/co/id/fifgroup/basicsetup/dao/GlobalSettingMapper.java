package co.id.fifgroup.basicsetup.dao;

import co.id.fifgroup.basicsetup.domain.GlobalSetting;
import co.id.fifgroup.basicsetup.domain.GlobalSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GlobalSettingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int countByExample(GlobalSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int deleteByExample(GlobalSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int deleteByPrimaryKey(Long globalSettingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int insert(GlobalSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int insertSelective(GlobalSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    List<GlobalSetting> selectByExampleWithRowbounds(GlobalSettingExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    List<GlobalSetting> selectByExample(GlobalSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    GlobalSetting selectByPrimaryKey(Long globalSettingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByExampleSelective(@Param("record") GlobalSetting record, @Param("example") GlobalSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByExample(@Param("record") GlobalSetting record, @Param("example") GlobalSettingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByPrimaryKeySelective(GlobalSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table BSE_GLOBAL_SETTINGS
     *
     * @mbggenerated Wed Mar 20 11:18:05 ICT 2013
     */
    int updateByPrimaryKey(GlobalSetting record);
}