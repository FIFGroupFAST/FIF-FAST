package co.id.fifgroup.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.KelurahanExample;

public interface KelurahanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int countByExample(KelurahanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insert(Kelurahan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int insertSelective(Kelurahan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<Kelurahan> selectByExampleWithRowbounds(KelurahanExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    List<Kelurahan> selectByExample(KelurahanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Kelurahan record, @Param("example") KelurahanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table FS_MST_KELURAHAN_TEST
     *
     * @mbggenerated Thu Feb 28 18:40:22 ICT 2013
     */
    int updateByExample(@Param("record") Kelurahan record, @Param("example") KelurahanExample example);
}