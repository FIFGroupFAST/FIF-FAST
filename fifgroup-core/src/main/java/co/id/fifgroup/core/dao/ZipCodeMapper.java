package co.id.fifgroup.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.domain.ZipCode;
import co.id.fifgroup.core.domain.ZipCodeExample;

public interface ZipCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int countByExample(ZipCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insert(ZipCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insertSelective(ZipCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<ZipCode> selectByExampleWithRowbounds(ZipCodeExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<ZipCode> selectByExample(ZipCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExampleSelective(@Param("record") ZipCode record, @Param("example") ZipCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_ZIP_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExample(@Param("record") ZipCode record, @Param("example") ZipCodeExample example);
}