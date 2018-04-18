package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.domain.Kelurahan;
import co.id.fifgroup.core.domain.KelurahanExample;

public interface KelurahanService {

	public List<Kelurahan> getKelurahansByExample(KelurahanExample kelurahanExample);
	public List<Kelurahan> getKelurahansByExample(KelurahanExample kelurahanExample, int limit, int offset);
	public int getCountKelurahanByExample(KelurahanExample kelurahanExample);
}
