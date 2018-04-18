package co.id.fifgroup.basicsetup.service;

import java.util.List;

import co.id.fifgroup.basicsetup.domain.GLCode;

public interface GLCodeService {

	public List<GLCode> getGlCodeBySegmentNum(Long segmentNum);
	
}
