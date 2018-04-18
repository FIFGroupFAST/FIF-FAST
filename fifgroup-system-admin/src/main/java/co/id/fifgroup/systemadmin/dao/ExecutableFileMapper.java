package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.ExecutableFile;
import co.id.fifgroup.systemadmin.domain.ExecutableFileExample;

public interface ExecutableFileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int countByExample(ExecutableFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int insert(ExecutableFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int insertSelective(ExecutableFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    List<ExecutableFile> selectByExampleWithRowbounds(ExecutableFileExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    List<ExecutableFile> selectByExample(ExecutableFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    ExecutableFile selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int updateByExampleSelective(@Param("record") ExecutableFile record, @Param("example") ExecutableFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int updateByExample(@Param("record") ExecutableFile record, @Param("example") ExecutableFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int updateByPrimaryKeySelective(ExecutableFile record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_EXECUTABLE_FILES
     *
     * @mbggenerated Tue Mar 19 22:35:09 ICT 2013
     */
    int updateByPrimaryKey(ExecutableFile record);
}