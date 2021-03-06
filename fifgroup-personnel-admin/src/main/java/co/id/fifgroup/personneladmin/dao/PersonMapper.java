package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.Person;
import co.id.fifgroup.personneladmin.domain.PersonExample;

public interface PersonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long personId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Person> selectByExampleWithBLOBsWithRowbounds(PersonExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Person> selectByExampleWithBLOBs(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Person> selectByExampleWithRowbounds(PersonExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Person> selectByExample(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    Person selectByPrimaryKey(Long personId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleWithBLOBs(@Param("record") Person record, @Param("example") PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeyWithBLOBs(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_PEOPLE
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(Person record);
}