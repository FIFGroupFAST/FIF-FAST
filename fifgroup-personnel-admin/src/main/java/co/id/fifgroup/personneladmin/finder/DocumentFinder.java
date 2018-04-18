package co.id.fifgroup.personneladmin.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.dto.DocumentDTO;

public interface DocumentFinder {

	public List<DocumentDTO> getDocumentsByPersonIdAndCompanyId(@Param("personId") Long personId, @Param("companyId") Long companyId, @Param("documentType") String documentType);
	
}
