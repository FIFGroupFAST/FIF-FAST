package co.id.fifgroup.core.dao;

import java.util.List;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.domain.Supplier;
import co.id.fifgroup.core.domain.SupplierExample;

public interface SupplierMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MST_SUPPLIERS_V
     *
     * @mbggenerated Fri Jun 07 10:20:59 ICT 2013
     */
    int countByExample(SupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MST_SUPPLIERS_V
     *
     * @mbggenerated Fri Jun 07 10:20:59 ICT 2013
     */
    int insert(Supplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MST_SUPPLIERS_V
     *
     * @mbggenerated Fri Jun 07 10:20:59 ICT 2013
     */
    int insertSelective(Supplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MST_SUPPLIERS_V
     *
     * @mbggenerated Fri Jun 07 10:20:59 ICT 2013
     */
    List<Supplier> selectByExampleWithRowbounds(SupplierExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MST_SUPPLIERS_V
     *
     * @mbggenerated Fri Jun 07 10:20:59 ICT 2013
     */
    List<Supplier> selectByExample(SupplierExample example);
}