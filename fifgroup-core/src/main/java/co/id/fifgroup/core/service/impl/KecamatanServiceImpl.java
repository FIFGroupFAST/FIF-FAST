package co.id.fifgroup.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.KecamatanMapper;
import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.KecamatanExample;
import co.id.fifgroup.core.service.KecamatanService;

@Transactional
@Service
public class KecamatanServiceImpl implements KecamatanService {

	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<Kecamatan> getKecamatansByExample(
			KecamatanExample kecamatanExample) {
		return kecamatanMapper.selectByExample(kecamatanExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Kecamatan> getKecamatansByExample(
			KecamatanExample kecamatanExample, int limit, int offset) {
		kecamatanExample.setOrderByClause("TRIM(UPPER(KECAMATAN)) ASC");
		return kecamatanMapper.selectByExampleWithRowbounds(kecamatanExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountKecamatanByExample(KecamatanExample kecamatanExample) {
		return kecamatanMapper.countByExample(kecamatanExample);
	}
	
}
