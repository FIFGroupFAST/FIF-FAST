package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.ssoa.domain.SystemProperty;
import co.id.fifgroup.ssoa.domain.SystemPropertyExample;
import co.id.fifgroup.ssoa.dto.SystemPropertyDTO;

public interface SystemPropertyService {
	public List<SystemProperty> getSystemPropertyByExample(SystemPropertyExample systemPropertyExample);
	public SystemProperty getSystemPropertyById(String systemPropertyCode);
	public List<SystemPropertyDTO> getSystemPropertyDtoBySystemPropertyCodeName(String systemPropertyCode, String systemPropertyName);
	public int getCountSystemPropertyDtoBySystemPropertyCodeName(String systemPropertyCode, String systemPropertyName);
	public void save(SystemProperty systemProperty) throws Exception;
	public boolean isExistSystemPropertyForUpdate(SystemProperty subject);
	public int countByExample(SystemPropertyExample example);
	public void delete(SystemPropertyDTO systemPropertyDto) throws Exception;
}