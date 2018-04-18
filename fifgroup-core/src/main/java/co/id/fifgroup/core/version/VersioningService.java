package co.id.fifgroup.core.version;

import java.util.List;

public interface VersioningService<T extends Version> {
	
	void save(T subject) throws Exception;
	
	void insertHeader(T subject);
	
	void updateHeader(T subject);
	
	void insertDetail(T subject);
	
	void delete(T subject);
	
	void deleteHeader(T subject);
	
	void deleteDetail(T subject);
	
	void updateDetail(T subject);
	
	Long getNextHeaderId();
	
	T getPreviousVersion(T subject);
	
	T findByIdAndVersionNumber(Long id, Integer versionNumber);
	
	List<Integer> findVersionsById(Long id);
	
	boolean isHaveFuture(Long id);
	
}
