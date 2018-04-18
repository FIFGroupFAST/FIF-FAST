package co.id.fifgroup.core.finder;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import co.id.fifgroup.core.dto.SupplierDTO;


public interface SupplierFinder {
	List<SupplierDTO> getSupplierByType(@Param("type") String type);
	List<SupplierDTO> findByExample(@Param("supplier")SupplierDTO example);
	List<SupplierDTO> findByExample(@Param("supplier")SupplierDTO example, RowBounds rowBounds);
	List<SupplierDTO> findByExampleAndPersonId(@Param("supplier") SupplierDTO example, @Param("personId")Long personId, RowBounds rowBounds);
	Integer countByExample(@Param("supplier")SupplierDTO example);
	Integer countByExampleAndPersonId(@Param("supplier") SupplierDTO example, @Param("personId") Long personId);
	SupplierDTO getBranchBod(@Param("branchCode") String branchCode, @Param("companyGlCode") String companyGlCode);
	List<SupplierDTO> findSupplierType();
	
}
