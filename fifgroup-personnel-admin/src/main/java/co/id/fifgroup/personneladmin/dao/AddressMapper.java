package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.Address;
import co.id.fifgroup.personneladmin.domain.AddressExample;

public interface AddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int countByExample(AddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByExample(AddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int deleteByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insert(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int insertSelective(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Address> selectByExampleWithRowbounds(AddressExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    List<Address> selectByExample(AddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    Address selectByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByExample(@Param("record") Address record, @Param("example") AddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKeySelective(Address record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_ADDRESSES
     *
     * @mbggenerated Mon Jun 03 20:18:16 ICT 2013
     */
    int updateByPrimaryKey(Address record);
}