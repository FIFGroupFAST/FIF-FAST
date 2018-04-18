package co.id.fifgroup.personneladmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.personneladmin.dto.DocumentDTO;
import co.id.fifgroup.personneladmin.service.DocumentService;
import co.id.fifgroup.personneladmin.util.FileUtil;

import co.id.fifgroup.personneladmin.dao.DocumentMapper;
import co.id.fifgroup.personneladmin.domain.Document;
import co.id.fifgroup.personneladmin.domain.DocumentExample;
import co.id.fifgroup.personneladmin.finder.DocumentFinder;

@Transactional
@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentMapper documentMapper;
	@Autowired
	private DocumentFinder documentFinder;
	
	@Override
	public List<Document> selectByExample(DocumentExample example) {
		return documentMapper.selectByExample(example);
	}

	@Override
	public List<DocumentDTO> getDocumentsByPersonIdAndCompanyId(Long personId,
			Long companyId, String documentType) {
		return documentFinder.getDocumentsByPersonIdAndCompanyId(personId, companyId, documentType);
	}

	@Override
	public void save(Document document) {
		documentMapper.insert(document);
	}

	@Override
	public void update(Document document) {
		documentMapper.updateByPrimaryKeySelective(document);
	}

	@Override
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId) {
		List<DocumentDTO> listDocuments = getDocumentsByPersonIdAndCompanyId(personId, 
				companyId, null);
		if (listDocuments.size() > 0) {
			for (DocumentDTO document : listDocuments) {
				if (document.isEditable()) {
					if (document.getFilePath() != null) {
						FileUtil.deleteFile(document.getFilePath());
					}
				}
			}
			DocumentExample example = new DocumentExample();
			example.createCriteria().andCompanyIdEqualTo(companyId).andPersonIdEqualTo(personId);
			documentMapper.deleteByExample(example);			
		}
	}
	
	@Override
	public void updateByExample(DocumentDTO document, DocumentExample example) {
		documentMapper.updateByExampleSelective(document, example);
	}

}
