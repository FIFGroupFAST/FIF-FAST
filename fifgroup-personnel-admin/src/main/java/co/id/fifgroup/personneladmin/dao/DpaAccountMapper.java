package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.DpaAccount;
import co.id.fifgroup.personneladmin.domain.DpaAccountExample;

public interface DpaAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int countByExample(DpaAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int deleteByExample(DpaAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int deleteByPrimaryKey(Long dpaAccountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int insert(DpaAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int insertSelective(DpaAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    List<DpaAccount> selectByExampleWithRowbounds(DpaAccountExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    List<DpaAccount> selectByExample(DpaAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    DpaAccount selectByPrimaryKey(Long dpaAccountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int updateByExampleSelective(@Param("record") DpaAccount record, @Param("example") DpaAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int updateByExample(@Param("record") DpaAccount record, @Param("example") DpaAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int updateByPrimaryKeySelective(DpaAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_DPA_ACCOUNTS
     *
     * @mbggenerated Wed Jun 12 15:14:22 ICT 2013
     */
    int updateByPrimaryKey(DpaAccount record);
}