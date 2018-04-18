package co.id.fifgroup.ssoa.service;

import java.util.LinkedList;
import java.util.List;

import org.jfree.util.Log;

import co.id.fifgroup.ssoa.domain.AssetMutation;

public class AssetMutationService implements AssetMutationInterface {
	
	private List<AssetMutation> assetList= new LinkedList<AssetMutation>();
	private List<AssetMutation> assetList1= new LinkedList<AssetMutation>();
	private List<AssetMutation> assetList2= new LinkedList<AssetMutation>();
	private int id = 1;
	//initialize book data
	public AssetMutationService() {
		assetList.add(new AssetMutation(false, 
										"1425", 
										"AST - FIF", 
										"FIFBRAA10600", 
										"Jakarta 2 Branch", 
										"", 
										"", 
										"", 
										"", 
										"10-Jun-2005", 
										"AST - AC/SET CASSETTE 1.5 PK DAIKIN", 
										"", 
										"", 
										"Ditemukan, Kondisi Rusak", 
										"Dijual"));
		
		assetList.add(new AssetMutation(false, 
										"3061", 
										"AST - FIF", 
										"FIFBRAA10600", 
										"Jakarta 2 Branch", 
										"", 
										"", 
										"", 
										"", 
										"14-Dec-2016", 
										"AST - AC/SET B SPLIT 1 PK PANASONIC", 
										"", 
										"", 
										"Ditemukan, Kondisi Bagus, Digunakan", 
										"Dijual"));
		
		assetList.add(new AssetMutation(false, 
										"3062", 
										"AST - FIF", 
										"FIFBRAA10600", 
										"Jakarta 2 Branch", 
										"", 
										"", 
										"", 
										"", 
										"10-Nov-2011", 
										"AST - AC/SET B SPLIT 2PK PANASONIC", 
										"", 
										"", 
										"Ditemukan, Kondisi Bagus, Tidak Digunakan", 
										"Dijual"));
		
		assetList1.add(new AssetMutation(false, 
				"3061", 
				"AST - FIF", 
				"FIFBRAA10600", 
				"Jakarta 2 Branch", 
				"", 
				"", 
				"", 
				"", 
				"14-Dec-2016", 
				"AST - AC/SET B SPLIT 1 PK PANASONIC", 
				"", 
				"", 
				"Ditemukan, Kondisi Bagus, Digunakan", 
				"Dijual"));

		assetList1.add(new AssetMutation(false, 
						"3062", 
						"AST - FIF", 
						"FIFBRAA10600", 
						"Jakarta 2 Branch", 
						"", 
						"", 
						"", 
						"", 
						"10-Nov-2011", 
						"AST - AC/SET B SPLIT 2PK PANASONIC", 
						"", 
						"", 
						"Ditemukan, Kondisi Bagus, Tidak Digunakan", 
						"Dijual"));
		
		assetList2.add(new AssetMutation(false, 
				"1425", 
				"AST - FIF", 
				"FIFBRAA10600", 
				"Jakarta 2 Branch", 
				"", 
				"", 
				"", 
				"", 
				"10-Jun-2005", 
				"AST - AC/SET CASSETTE 1.5 PK DAIKIN", 
				"", 
				"", 
				"Ditemukan, Kondisi Rusak", 
				"Dijual"));
							
	}
	
	

	@Override
	public List<AssetMutation> findAll() {
		// TODO Auto-generated method stub
		return assetList;
	}

	@Override
	public List<AssetMutation> search(String param1) {
		List<AssetMutation> result = new LinkedList<AssetMutation>();
		if (param1==null || "".equals(param1)){
			result = assetList;
		}else{
			for (AssetMutation c: assetList){
				if (c.getNo_asset().toLowerCase().contains(param1.toLowerCase())
					||c.getNo_asset().toLowerCase().contains(param1.toLowerCase())){
					result.add(c);
				}
			}
		}
		return result;
	}
	
	@Override
	public List<AssetMutation> remove(Boolean param1) {
		List<AssetMutation> result = new LinkedList<AssetMutation>();
		if (param1==null || "".equals(param1)){
			result = assetList;
		}else{
			for (AssetMutation c: assetList){
				if (c.isCheck()==true){
					result.remove(c);
				}
			}
		}
		return result;
	}



	@Override
	public List<AssetMutation> findAll1() {
		// TODO Auto-generated method stub
		return assetList1;
	}



	@Override
	public List<AssetMutation> findAll2() {
		// TODO Auto-generated method stub
		return assetList2;
	}

}
