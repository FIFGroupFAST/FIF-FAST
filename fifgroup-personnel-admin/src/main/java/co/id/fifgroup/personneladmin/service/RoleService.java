package co.id.fifgroup.personneladmin.service;

import java.util.List;

import co.id.fifgroup.personneladmin.domain.Role;
import co.id.fifgroup.personneladmin.domain.RoleExample;

public interface RoleService {

	public List<Role> selectByExample(RoleExample example);
	
	public void save(Role role);
	
	public void update(Role role);
	
	public void deleteByPersonIdAndCompanyId(Long personId, Long companyId);
	
	public void saveRoles(Long personId, Long companyId, List<Role> listRole) throws Exception;
	
	public void updateRoles(Long personId, Long companyId, List<Role> listRole) throws Exception;
}
