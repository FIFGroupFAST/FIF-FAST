package co.id.fifgroup.personneladmin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.personneladmin.domain.HousingAllowanceAutomatic;

public interface HousingAllowanceAutomaticMapper {

	/* add by PMW, ticket 15111616041796, 15-Feb-2016 */
	public List<HousingAllowanceAutomatic> selectHousingAllowanceAutomaticInquiry(@Param("personId") Long personId,@Param("houseAllowance") String houseAllowance);
	
	public int checkFutureExist(@Param("personId") Long personId);
	
	public void insertHousingAllowance(HousingAllowanceAutomatic housingAllowanceAutomatic);
	
	public void updateDateFromPreviousVersion(HousingAllowanceAutomatic housingAllowanceAutomatic);
	
	public void updateByExample(HousingAllowanceAutomatic housingAllowanceAutomatic);
	/* end add */
	
}
