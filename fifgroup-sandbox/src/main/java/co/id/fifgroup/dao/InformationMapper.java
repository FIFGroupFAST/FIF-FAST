package co.id.fifgroup.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.domain.Information;

public interface InformationMapper {
	//INSERT, UPDATE, DELETE
	public void insertInformation (Information information);
	public void updateInformation (Information information);
	public void deleteInformation (@Param("informationId")int informationId);
	//SELECT
	public List<Information> getAllInformation();
	public List<Information> getInformationByFilter(Information information);
	
	String getSupervisor(@Param("employeeNumber")String employeeNumber);
}
