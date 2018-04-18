package co.id.fifgroup.systemworkflow.dao;

import co.id.fifgroup.systemworkflow.domain.MessageMapping;
import co.id.fifgroup.systemworkflow.domain.MessageMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MessageMappingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int countByExample(MessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int deleteByPrimaryKey(Long messageMappingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int insert(MessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int insertSelective(MessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<MessageMapping> selectByExampleWithRowbounds(MessageMappingExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    List<MessageMapping> selectByExample(MessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    MessageMapping selectByPrimaryKey(Long messageMappingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByExampleSelective(@Param("record") MessageMapping record, @Param("example") MessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByExample(@Param("record") MessageMapping record, @Param("example") MessageMappingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByPrimaryKeySelective(MessageMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SWF_MESSAGE_MAPPING
     *
     * @mbggenerated Tue Mar 05 20:46:12 ICT 2013
     */
    int updateByPrimaryKey(MessageMapping record);
}