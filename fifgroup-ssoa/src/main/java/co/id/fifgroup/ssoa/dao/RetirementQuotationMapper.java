package co.id.fifgroup.ssoa.dao;


import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementQuotation;
import co.id.fifgroup.ssoa.dto.RetirementQuotationDTO;

public interface RetirementQuotationMapper {
   

    int deleteByPrimaryKey(String id);

    int insert(RetirementQuotation record);

    RetirementQuotation selectByPrimaryKey(Long id);
    
    List<RetirementQuotationDTO> selectByHeaderKey(Long id);
    
    int updateByPrimaryKey(RetirementQuotation record);
    
    }