package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.ssoa.domain.AssetLabeling;

public interface AssetLabelingInterface {
	public List<AssetLabeling> findAllAsset();
	public List<AssetLabeling> findAll();
	public List<AssetLabeling> search(String param1);
	public List<AssetLabeling> remove(Boolean param1);
}