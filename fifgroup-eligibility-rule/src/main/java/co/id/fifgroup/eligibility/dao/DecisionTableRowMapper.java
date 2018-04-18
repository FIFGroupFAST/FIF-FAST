package co.id.fifgroup.eligibility.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.eligibility.domain.DecisionTableRow;
import co.id.fifgroup.eligibility.domain.DecisionTableRowExample;

public interface DecisionTableRowMapper {
    
    int countByExample(@Param("modulePrefix") String modulePrefix, @Param("example") DecisionTableRowExample example);
  
    int deleteByExample(@Param("modulePrefix") String modulePrefix, @Param("example") DecisionTableRowExample example);

    int deleteByPrimaryKey(@Param("modulePrefix") String modulePrefix, @Param("id") Long id);

    int insert(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record);
    
    /**
	 * [16022316000474] HCMS ph 2- CAM - performance upload career path
	 * Jika ingin insert data dalam jumlah banyak, lebih bagus menggunakan metode bulk
	 * ex: insert data per 500 records
	 * By Jatis (HS) 24/03/2016
	 */
    int insertBulk(@Param("modulePrefix") String modulePrefix, @Param("record") List<DecisionTableRow> record);
    //END [16022316000474]
    
    int insertSelective(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record);

    List<DecisionTableRow> selectByExampleWithBLOBs(@Param("modulePrefix") String modulePrefix, @Param("example") DecisionTableRowExample example);

    List<DecisionTableRow> selectByExample(@Param("modulePrefix") String modulePrefix, @Param("example") DecisionTableRowExample example);

    DecisionTableRow selectByPrimaryKey(@Param("modulePrefix") String modulePrefix, @Param("id") Long id);

    int updateByExampleSelective(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record, @Param("example") DecisionTableRowExample example);

    int updateByExampleWithBLOBs(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record, @Param("example") DecisionTableRowExample example);

    int updateByExample(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record, @Param("example") DecisionTableRowExample example);

    int updateByPrimaryKeySelective(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record);

    int updateByPrimaryKeyWithBLOBs(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record);

    int updateByPrimaryKey(@Param("modulePrefix") String modulePrefix, @Param("record") DecisionTableRow record);
}