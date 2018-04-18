package co.id.fifgroup.ssoa.dao;


import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.dto.RetirementBastDTO;

public interface RetirementBastMapper {
   

    int deleteByPrimaryKey(String id);
    List<RetirementBast> selectDetailBastByHeaderId(Long id);

    int insert(RetirementBastDTO record);

    RetirementBastDTO selectByPrimaryKey(Long id);
    
    List<RetirementBastDTO> selectByHeaderKey(Long id);
    
    int updateByPrimaryKey(RetirementBastDTO record);
    
    }