package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.BankInformation;
import co.id.fifgroup.personneladmin.domain.BankInformationExample;

public interface BankInformationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int countByExample(BankInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int deleteByExample(BankInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int deleteByPrimaryKey(Long bankInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int insert(BankInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int insertSelective(BankInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    List<BankInformation> selectByExampleWithRowbounds(BankInformationExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    List<BankInformation> selectByExample(BankInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    BankInformation selectByPrimaryKey(Long bankInfoId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int updateByExampleSelective(@Param("record") BankInformation record, @Param("example") BankInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int updateByExample(@Param("record") BankInformation record, @Param("example") BankInformationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int updateByPrimaryKeySelective(BankInformation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_BANK_INFORMATIONS
     *
     * @mbggenerated Fri May 31 11:40:27 ICT 2013
     */
    int updateByPrimaryKey(BankInformation record);
}