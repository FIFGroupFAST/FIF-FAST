package co.id.fifgroup.systemworkflow.dao;

import co.id.fifgroup.systemworkflow.domain.LevelMessageMapping;
import co.id.fifgroup.systemworkflow.domain.LevelMessageMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LevelMessageMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int countByExample(LevelMessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int deleteByPrimaryKey(Long levelMessageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int insert(LevelMessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int insertSelective(LevelMessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    List<LevelMessageMapping> selectByExampleWithRowbounds(LevelMessageMappingExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    List<LevelMessageMapping> selectByExample(LevelMessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    LevelMessageMapping selectByPrimaryKey(Long levelMessageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int updateByExampleSelective(@Param("record") LevelMessageMapping record, @Param("example") LevelMessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int updateByExample(@Param("record") LevelMessageMapping record, @Param("example") LevelMessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int updateByPrimaryKeySelective(LevelMessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_LEVEL_MESSAGE_MAPPING
     *
     * @mbggenerated Thu Mar 07 15:25:30 ICT 2013
     */
    int updateByPrimaryKey(LevelMessageMapping record);
}