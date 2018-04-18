package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetMutation;

public interface AssetMutationInterface {
	
	public List<AssetMutation> findAll();
	public List<AssetMutation> findAll1();
	public List<AssetMutation> findAll2();
	
	public List<AssetMutation> search(String param1);
	
	public List<AssetMutation> remove(Boolean param1);
}
