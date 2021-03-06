package co.id.fifgroup.workstructure.dao;

import co.id.fifgroup.workstructure.domain.Lob;
import co.id.fifgroup.workstructure.domain.LobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LobMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int countByExample(LobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int deleteByExample(LobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int insert(Lob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int insertSelective(Lob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    List<Lob> selectByExampleWithRowbounds(LobExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    List<Lob> selectByExample(LobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    Lob selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Lob record, @Param("example") LobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int updateByExample(@Param("record") Lob record, @Param("example") LobExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int updateByPrimaryKeySelective(Lob record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WOS_LOBS
     *
     * @mbggenerated Mon Aug 19 15:02:22 ICT 2013
     */
    int updateByPrimaryKey(Lob record);
}