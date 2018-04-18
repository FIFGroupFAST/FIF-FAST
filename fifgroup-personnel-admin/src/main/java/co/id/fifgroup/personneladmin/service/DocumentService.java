package co.id.fifgroup.personneladmin.service;

import java.util.List;

import co.id.fifgroup.personneladmin.dto.DocumentDTO;

import co.id.fifgroup.personneladmin.domain.Document;
import co.id.fifgroup.personneladmin.domain.DocumentExample;

public interface DocumentService {

	public List<Document> selectByExample(DocumentExample example);
	
	public List<DocumentDTO> getDocumentsByPersonIdAndCompanyId(Long personId, Long companyId, String documentType);
	
	public void save(Document document);
	
	public void update(Document document);
	
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId);
	
	public void updateByExample(DocumentDTO document, DocumentExample example);
}
