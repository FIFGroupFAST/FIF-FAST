package co.id.fifgroup.core.service;

import java.util.List;

import co.id.fifgroup.core.domain.Kecamatan;
import co.id.fifgroup.core.domain.KecamatanExample;

public interface KecamatanService {

	public List<Kecamatan> getKecamatansByExample(KecamatanExample kecamatanExample);
	public List<Kecamatan> getKecamatansByExample(KecamatanExample kecamatanExample, int limit, int offset);
	public int getCountKecamatanByExample(KecamatanExample kecamatanExample);
}
