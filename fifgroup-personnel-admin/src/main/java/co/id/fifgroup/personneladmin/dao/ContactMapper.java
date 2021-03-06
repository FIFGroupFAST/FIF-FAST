package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.Contact;
import co.id.fifgroup.personneladmin.domain.ContactExample;

public interface ContactMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(ContactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(ContactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long contactId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(Contact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(Contact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Contact> selectByExampleWithRowbounds(ContactExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Contact> selectByExample(ContactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    Contact selectByPrimaryKey(Long contactId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Contact record, @Param("example") ContactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") Contact record, @Param("example") ContactExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(Contact record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_CONTACTS
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(Contact record);
}