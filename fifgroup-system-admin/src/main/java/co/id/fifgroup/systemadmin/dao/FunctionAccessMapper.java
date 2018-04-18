package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.systemadmin.domain.FunctionAccess;
import co.id.fifgroup.systemadmin.domain.FunctionAccessExample;

public interface FunctionAccessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int countByExample(FunctionAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int insert(FunctionAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int insertSelective(FunctionAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    List<FunctionAccess> selectByExample(FunctionAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    FunctionAccess selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByExampleSelective(@Param("record") FunctionAccess record, @Param("example") FunctionAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByExample(@Param("record") FunctionAccess record, @Param("example") FunctionAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByPrimaryKeySelective(FunctionAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_CUSTOM_FUNCTION_ACCESS
     *
     * @mbggenerated Tue Feb 19 16:18:33 ICT 2013
     */
    int updateByPrimaryKey(FunctionAccess record);
}