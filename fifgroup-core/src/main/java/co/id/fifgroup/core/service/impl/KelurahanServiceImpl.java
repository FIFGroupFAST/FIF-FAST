package co.id.fifgroup.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.dao.KelurahanMapper;
import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.KelurahanExample;
import co.id.fifgroup.core.service.KelurahanService;

@Transactional
@Service
public class KelurahanServiceImpl implements KelurahanService {

	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	@Override
	@Transactional(readOnly=true)
	public List<Kelurahan> getKelurahansByExample(
			KelurahanExample kelurahanExample) {
		return kelurahanMapper.selectByExample(kelurahanExample);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Kelurahan> getKelurahansByExample(
			KelurahanExample kelurahanExample, int limit, int offset) {
		kelurahanExample.setOrderByClause("TRIM(UPPER(KELURAHAN)) ASC");
		return kelurahanMapper.selectByExampleWithRowbounds(kelurahanExample, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly=true)
	public int getCountKelurahanByExample(KelurahanExample kelurahanExample) {
		return kelurahanMapper.countByExample(kelurahanExample);
	}
}
