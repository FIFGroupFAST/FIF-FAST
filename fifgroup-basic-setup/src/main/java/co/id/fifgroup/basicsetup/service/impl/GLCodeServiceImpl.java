package co.id.fifgroup.basicsetup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.basicsetup.dao.GLCodeMapper;
import co.id.fifgroup.basicsetup.domain.GLCode;
import co.id.fifgroup.basicsetup.service.GLCodeService;

@Transactional
@Service
public class GLCodeServiceImpl implements GLCodeService {

	@Autowired
	private GLCodeMapper gLCodeMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<GLCode> getGlCodeBySegmentNum(Long segmentNum) {
		return gLCodeMapper.getGlCodeBySegmentNum(segmentNum);
	}

}
