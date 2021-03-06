package co.id.fifgroup.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.domain.City;
import co.id.fifgroup.core.domain.CityExample;

public interface CityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int countByExample(CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insert(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insertSelective(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<City> selectByExampleWithRowbounds(CityExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<City> selectByExample(CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExampleSelective(@Param("record") City record, @Param("example") CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_CITIES_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExample(@Param("record") City record, @Param("example") CityExample example);
}