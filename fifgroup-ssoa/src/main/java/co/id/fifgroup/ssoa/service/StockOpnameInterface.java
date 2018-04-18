package co.id.fifgroup.ssoa.service;

import java.util.List;

import co.id.fifgroup.ssoa.domain.ApprovalFlow;
import co.id.fifgroup.ssoa.domain.StockOpname;

public interface StockOpnameInterface {
	
	public List<ApprovalFlow> findAllStatusAsset();

	public List<StockOpname> findAllRegisteredResult();
	public List<StockOpname> findAllRegisteredDetail();
	public List<StockOpname> findAllUnregistered();
	public List<StockOpname> search(String param1);
}