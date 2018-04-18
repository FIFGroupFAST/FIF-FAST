package co.id.fifgroup.service;

import java.util.List;

import co.id.fifgroup.core.service.WorkflowService;
import co.id.fifgroup.domain.Information;

public interface InformationService extends WorkflowService {
	//INSERT, UPDATE, DELETE
	public void insertInformation (Information information);
	public void updateInformation (Information information);
	public void deleteInformation (int informationId);
	//SELECT
	public List<Information> getAllInformation();
	public List<Information> getInformationByFilter(Information information);
	
	String getSupervisor(String employeeNumber);
}