package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.PeopleHistory;
import co.id.fifgroup.personneladmin.domain.PeopleHistoryExample;

public interface PeopleHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(PeopleHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(PeopleHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long historyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(PeopleHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(PeopleHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<PeopleHistory> selectByExampleWithRowbounds(PeopleHistoryExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<PeopleHistory> selectByExample(PeopleHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    PeopleHistory selectByPrimaryKey(Long historyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") PeopleHistory record, @Param("example") PeopleHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") PeopleHistory record, @Param("example") PeopleHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(PeopleHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE_HISTORIES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(PeopleHistory record);
}