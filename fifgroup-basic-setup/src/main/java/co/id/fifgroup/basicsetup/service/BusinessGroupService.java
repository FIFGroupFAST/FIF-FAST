package co.id.fifgroup.basicsetup.service;
import java.util.List;

import co.id.fifgroup.basicsetup.domain.BusinessGroup;
import co.id.fifgroup.basicsetup.domain.BusinessGroupExample;
import co.id.fifgroup.basicsetup.dto.BusinessGroupDTO;

/**
 * @author Achmad Ridwan
 * @version 1.0
 * @created 14-Feb-2013 3:08:53 PM
 */
public interface BusinessGroupService {
	
    public int countByExample(BusinessGroupExample example);
    public List<BusinessGroup> getBusinessGroupByExample(BusinessGroupExample example, int limit, int offset);
    public boolean isExistBusinessGroupForUpdate(BusinessGroup record);
    public void save(BusinessGroup businessGroup) throws Exception;
	public List<BusinessGroup> getBusinessGroupByExample(BusinessGroupExample businessGroupExample);
    public List<BusinessGroupDTO> getBusinessGroupDtoByGroupName(String groupName);
    public int getCountBusinessGroupDtoByGroupName(String groupName);
	
}