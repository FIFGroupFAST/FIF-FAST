package co.id.fifgroup.basicsetup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.basicsetup.dto.SequenceGeneratorDTO;
 

public interface SequenceGeneratorFinder {
	public int getCountSequenceGeneratorBySequenceGeneratorId(@Param("sequenceGeneratorId") String sequenceGeneratorId);
	public List<SequenceGeneratorDTO> getSequenceGeneratorBySequenceGeneratorIdWithRowBounds(@Param("sequenceGeneratorId") String sequenceGeneratorId, RowBounds rowbounds);
	public List<SequenceGeneratorDTO> getSequenceGeneratorByTrxCodeWithRowBounds(@Param("trxCode")String trxCode, @Param("groupId") Long groupId);
	public int getCountSequenceGeneratorDtoByTrxCode(@Param("trxCode")String trxCode, @Param("groupId") Long groupId);
	public void executeSequenceGenerator(@Param("sequenceName") String sequenceName, @Param("increment") String increment, @Param("maxValue") String maxValue, @Param("minValue") String minValue);
	public void dropSequenceGenerator(@Param("sequenceName") String sequenceName);
}
