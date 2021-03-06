package co.id.fifgroup.systemadmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.systemadmin.domain.User;
import co.id.fifgroup.systemadmin.domain.UserExample;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    List<User> selectByExampleWithRowbounds(UserExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SAM_USERS
     *
     * @mbggenerated Fri Sep 27 17:34:46 ICT 2013
     */
    int updateByPrimaryKey(User record);
}