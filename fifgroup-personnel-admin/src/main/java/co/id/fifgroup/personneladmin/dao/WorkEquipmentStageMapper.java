package co.id.fifgroup.personneladmin.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.personneladmin.domain.WorkEquipmentStage;
import co.id.fifgroup.personneladmin.domain.WorkEquipmentStageExample;

public interface WorkEquipmentStageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int countByExample(WorkEquipmentStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int deleteByExample(WorkEquipmentStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int insert(WorkEquipmentStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int insertSelective(WorkEquipmentStage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    List<WorkEquipmentStage> selectByExampleWithRowbounds(WorkEquipmentStageExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    List<WorkEquipmentStage> selectByExample(WorkEquipmentStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int updateByExampleSelective(@Param("record") WorkEquipmentStage record, @Param("example") WorkEquipmentStageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PEA_WORK_EQUIPMENT_STG
     *
     * @mbggenerated Mon Jul 08 20:50:29 ICT 2013
     */
    int updateByExample(@Param("record") WorkEquipmentStage record, @Param("example") WorkEquipmentStageExample example);
}