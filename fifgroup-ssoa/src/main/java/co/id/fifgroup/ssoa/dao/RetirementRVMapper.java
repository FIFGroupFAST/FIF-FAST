package co.id.fifgroup.ssoa.dao;


import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementRV;
import co.id.fifgroup.ssoa.dto.RetirementRVDTO;

public interface RetirementRVMapper {
   

    int deleteByPrimaryKey(Long id);
    List<RetirementRV> selectDetailBastByHeaderId(Long id);

    int insert(RetirementRVDTO record);

    RetirementRVDTO selectByPrimaryKey(Long id);
    
    List<RetirementRVDTO> selectByHeaderKey(Long id);
    
    int updateByPrimaryKey(RetirementRVDTO record);
    
    }