package co.id.fifgroup.core.finder;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.domain.interfacing.GlCodeCombination;
import co.id.fifgroup.core.domain.interfacing.GlPersonInfo;
import co.id.fifgroup.core.domain.interfacing.OcopBuyer;
import co.id.fifgroup.core.domain.interfacing.OcopGlExpense;
import co.id.fifgroup.core.domain.interfacing.OcopMovementHeader;
import co.id.fifgroup.core.domain.interfacing.OcopMovementHistory;
import co.id.fifgroup.core.domain.interfacing.OcopMovementLine;
import co.id.fifgroup.core.domain.interfacing.OcopRentedItem;
import co.id.fifgroup.core.domain.interfacing.OcopVehicleInformation;
import co.id.fifgroup.core.dto.OcopInterfaceDTO;

public interface FIFRentSysFinder {
	
	public void getBuyer(OcopBuyer ocopBuyer);
	
	public Long getGlLocationId(@Param("locationCode")String locationCode);
	
	public Long getInventoryItemId(@Param("itemTypeCode")String itemTypeCode, @Param("companyCodeGl")String companyCodeGl);
	
	public Long getAgentId(@Param("buyerAgentId")Long buyerAgentId);
	
	public Long getAgentLocationId(@Param("buyerPersonId")Long buyerPersonId);
	
	public OcopVehicleInformation getVehicleInformation(@Param("itemTypeCode")String itemTypeCode, @Param("companyCodeGl")String companyCodeGl, @Param("inventoryItemId")Long inventoryItemId);
	
	public String getInventoryItemCode(@Param("inventoryItemId")Long inventoryItemId, @Param("companyCodeGl")String companyCodeGl);
	
	public void getGlExpenseCcid(OcopGlExpense ocopGlExpense);
	
	public String getLocationIdByCodeCombination(@Param("glCcId")Long glCcId);
	
	public String getConversionType();
	
	public void insert(OcopInterfaceDTO ocopInterfaceDTO);
	
	public GlPersonInfo getGlPersonInfo(@Param("employeeNumber")String employeeNumber);
	
	public void insertMovementHeader(OcopMovementHeader ocopMovementHeader);
	
	public Long getRentedItemId(@Param("poNumber")String poNumber, @Param("personId")Long personId);
	
	public void insertMovementLine(OcopMovementLine ocopMovementLine);
	
	public void updateRentItemToRetired(@Param("rentedItemId")Long rentedItemId);
	
	public void insertMovementHistory(OcopMovementHistory ocopMovementHistory);
	
	public List<OcopRentedItem> selectRentedItemByExample(OcopRentedItem example);
	
	public GlCodeCombination getGlCombination(@Param("companyId")Long companyId, @Param("itemCode")String itemCode);
	
	public Long getCodeCombinationId(GlCodeCombination glCodeCombination);
	
	public Long getChartOfAccountId(@Param("shortName")String shortName);
	
	public void insertGlCodeCombination(GlCodeCombination glCodeCombination);
	
	public void updateRentedItemById(OcopRentedItem ocopRentedItem);
	
	public GlCodeCombination getCodeCombinationByPerson(@Param("personId")Long personId, @Param("processDate")Date processDate);
}
