package co.id.fifgroup.ssoa.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.ssoa.domain.AssetTransfer;
import co.id.fifgroup.ssoa.domain.Retirement;
import co.id.fifgroup.ssoa.dto.RetirementDTO;
import co.id.fifgroup.ssoa.dto.StockOpnameDTO;

public interface RetirementMapper {
   

    int deleteByPrimaryKey(String id);

    int insert(Retirement record);

    RetirementDTO getBranchCode (Long id);
    
    Retirement selectByPrimaryKey(Long id);
    
    RetirementDTO selectByAvmTrxId(String avmTrxId);
    
    RetirementDTO selectByAvmTrxIdBast(String avmTrxIdBast);

    int updateByPrimaryKey(Retirement record);
    
    int updateRvNumber(Retirement record);
    
    int updateBASTByPrimaryKey(Retirement record);
    
    RetirementDTO getLastRequestNo();
    
    RetirementDTO getLastBastDocNo();
    
    public List<KeyValue> getLookupKeyValues(@Param("lookupName") String lookupName,@Param("key")String key);
    
    public void insertRetirementToEbs(RetirementDTO retirementDto);
    
}