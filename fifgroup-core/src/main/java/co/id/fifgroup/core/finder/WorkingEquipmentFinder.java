package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.ui.lookup.KeyValue;

public interface WorkingEquipmentFinder {
	
	public List<KeyValue> findWorkingEquipmentByCompanyIdAndActive(@Param("companyId") Long companyId, @Param("inactive") Boolean inactive);
	
	public KeyValue findWorkingEquipmentById(@Param("workingEquipmentId") Long workingEquipmentId);
}
