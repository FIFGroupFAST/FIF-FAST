package co.id.fifgroup.ssoa.dao;


import java.util.List;

import co.id.fifgroup.ssoa.domain.RetirementBast;
import co.id.fifgroup.ssoa.domain.RetirementImg;
import co.id.fifgroup.ssoa.dto.RetirementImgDTO;

public interface RetirementNonEBSImgMapper {
   

    int deleteByPrimaryKey(String id);
    List<RetirementImg> selectDetailImageByHeaderId(Long id);
    int insert(RetirementImgDTO record);

    RetirementImgDTO selectByPrimaryKey(Long id);
    
    List<RetirementImgDTO> selectByHeaderKey(Long id);
    
    int updateByPrimaryKey(RetirementImgDTO record);
    
    }