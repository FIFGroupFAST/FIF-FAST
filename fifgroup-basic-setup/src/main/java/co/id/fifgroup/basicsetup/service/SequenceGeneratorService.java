package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.SequenceGenerator;
import co.id.fifgroup.basicsetup.domain.SequenceGeneratorExample;
import co.id.fifgroup.basicsetup.dto.SequenceGeneratorDTO;



public interface SequenceGeneratorService {
	public void save(SequenceGenerator sequenceGenerator) throws Exception;
	public int countByExample(SequenceGeneratorExample example);
	public int getCountSequenceGeneratorDtoBySequenceGeneratorId(String sequenceGeneratorId);
	public List<SequenceGeneratorDTO> getSequenceGeneratorDtoBySequenceGeneratorId(String sequenceGeneratorId, int limit, int offset);
	public List<SequenceGenerator> getSequenceGeneratorByExample(SequenceGeneratorExample sequenceGeneratorExample, int limit, int offset);
	public List<SequenceGenerator> getSequenceGeneratorByExample(SequenceGeneratorExample sequenceGeneratorExample);
	public boolean isExistSequenceGeneratorForUpdate(SequenceGenerator record);
	public int getCountSequenceGeneratorDtoByTrxCode(String trxCode, Long groupId);
	public List<SequenceGeneratorDTO> getSequenceGeneratorDtoByTrxCode(String trxCode, Long groupId);
	public String getSequenceGeneratorName(SequenceGenerator sequenceGenerator);
	public void resetSequenceGenerator(SequenceGenerator sequenceGenerator);
}
