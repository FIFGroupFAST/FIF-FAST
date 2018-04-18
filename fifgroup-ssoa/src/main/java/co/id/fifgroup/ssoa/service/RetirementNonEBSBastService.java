package co.id.fifgroup.ssoa.service;

import java.util.List;
import java.util.UUID;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;
import co.id.fifgroup.ssoa.dto.RetirementDTO;


public interface RetirementNonEBSBastService extends WorkflowService{
	
	public List<KeyValue> getLookupKeyValues(String lookupName, String key);
	public void submitBAST(RetirementDTO retirementDTO, UUID personUUID, List<RetirementBastDTO> listBast) throws FifException, Exception;
	
	
}