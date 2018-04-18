package co.id.fifgroup.ssoa.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.apache.ibatis.session.RowBounds;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;

import co.id.fifgroup.avm.exception.FifException;
import co.id.fifgroup.core.constant.ContentType;
import co.id.fifgroup.core.ui.GlobalVariable;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.util.DownloadUtil;
import co.id.fifgroup.ssoa.constants.AssetLabelingPrintingType;
import co.id.fifgroup.ssoa.constants.SOPeriodStatus;
import co.id.fifgroup.ssoa.dao.AssetLabelingDetailMapper;
import co.id.fifgroup.ssoa.dao.AssetLabelingMapper;
import co.id.fifgroup.ssoa.dao.BranchMapper;
import co.id.fifgroup.ssoa.dao.SSOALocationMapper;
import co.id.fifgroup.ssoa.domain.AssetLabeling;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetail;
import co.id.fifgroup.ssoa.domain.AssetLabelingDetailExample;
import co.id.fifgroup.ssoa.domain.AssetLabelingExample;
import co.id.fifgroup.ssoa.domain.Branch;
import co.id.fifgroup.ssoa.domain.BranchExample;
import co.id.fifgroup.ssoa.domain.SSOALocation;
import co.id.fifgroup.ssoa.domain.SSOALocationExample;
import co.id.fifgroup.ssoa.dto.AssetLabelingDTO;
import co.id.fifgroup.ssoa.dto.AssetLabelingDetailDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDTO;
import co.id.fifgroup.ssoa.dto.AssetTransferDetailDTO;
import co.id.fifgroup.ssoa.dto.ParameterDTO;
import co.id.fifgroup.ssoa.finder.AssetLabelingFinder;
import co.id.fifgroup.ssoa.service.AssetLabelingService;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.type.WhenResourceMissingTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;

@Transactional
@Service("assetLabelingService")
public class AssetLabelingServiceImpl implements AssetLabelingService {
	private List<AssetLabeling> assetList1 = new LinkedList<AssetLabeling>();
	private List<AssetLabeling> assetList = new LinkedList<AssetLabeling>();
	
	private int id = 1;

	@Autowired
	private AssetLabelingMapper assetLabelingMapper;
	
	@Autowired
	private SSOALocationMapper ssoaLocationMapper;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BranchMapper branchMapper;
	
	
	@Autowired
	private AssetLabelingDetailMapper assetLabelingDetailMapper;
	// initialize book data

	// Finder
	@Autowired
	private AssetLabelingFinder assetLabelingFinder;
	
	@Autowired
	private ModelMapper modelMapper;

	public AssetLabelingServiceImpl() {
		
	}

	@Override
	public List<AssetLabeling> findAllAsset() {
		// TODO Auto-generated method stub
		return assetList1;
	}

	@Override
	public List<AssetLabeling> findAll() {
		// TODO Auto-generated method stub
		return assetList;
	}

	@Override
	public List<AssetLabeling> search(String param1) {
		List<AssetLabeling> result = new LinkedList<AssetLabeling>();
		if (param1 == null || "".equals(param1)) {
			result = assetList;
		} else {
			for (AssetLabeling c : assetList) {
				if (c.getNo_asset().toLowerCase().contains(param1.toLowerCase())
						|| c.getNo_asset().toLowerCase().contains(param1.toLowerCase())) {
					result.add(c);
				}
			}
		}
		return result;
	}

	@Override
	public List<AssetLabeling> remove(Boolean param1) {
		List<AssetLabeling> result = new LinkedList<AssetLabeling>();
		if (param1 == null || "".equals(param1)) {
			result = assetList;
		} else {
			for (AssetLabeling c : assetList) {
				if (c.isCheck() == true) {
					result.remove(c);
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public AMedia save(AssetLabelingDTO assetLabelingDto) throws Exception {
		AMedia aimagen = null;
		//AssetLabeling assetLabeling = assetLabelingMapper.selectByPrimaryKey(assetLabelingDto.getLabelingId());
		
		if(assetLabelingDto.getLabelingId() == null ){
			
			assetLabelingMapper.insert(assetLabelingDto);
			for (AssetLabelingDetailDTO assetLabelingDetailDTO : assetLabelingDto.getAssetLabelingDto()) {
				assetLabelingDetailDTO.setLabelingId(assetLabelingDto.getLabelingId());
				assetLabelingDetailDTO.setAssetId(assetLabelingDetailDTO.getAssetId());
				assetLabelingDetailDTO.setReprintingReason(assetLabelingDetailDTO.getReprintingReason());
				assetLabelingDetailDTO.setCreatedBy(assetLabelingDto.getCreatedBy());
				assetLabelingDetailDTO.setCreationDate(assetLabelingDto.getCreationDate());
				assetLabelingDetailDTO.setLastUpdateDate(assetLabelingDto.getLastUpdateDate());
				assetLabelingDetailDTO.setLastUpdatedBy(assetLabelingDto.getLastUpdateBy());
				assetLabelingDetailMapper.insert(assetLabelingDetailDTO);
				
				AssetLabelingDetailDTO asset = (AssetLabelingDetailDTO)assetLabelingDetailMapper.getPrintCountAsset(assetLabelingDetailDTO.getAssetId());
				asset.setPrintedCount((asset.getPrintedCount())+1);
				asset.setLastPrintedDate(new Date());
				asset.setAssetId(assetLabelingDetailDTO.getAssetId());
				assetLabelingDetailMapper.updatePrintCountPrintDateAsset(asset);
				
				InputStream image = Thread.currentThread().getContextClassLoader().getResourceAsStream("/report/fif.png");
				assetLabelingDto.setLogoLabel(image);
			}
		} 
		
		Connection conn = DataSourceUtils.getConnection(dataSource);
		Map<String, Object> param = new HashMap<String, Object>();
		
		String outputFileName;
		outputFileName = assetLabelingDto.getLabelingId()+".pdf";
		
		System.out.println("outputFileName=="+outputFileName);
		
		ClassLoader classLoader = getClass().getClassLoader();
		URL image = this.getClass().getClassLoader().getResource("report/fif.png");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("labelingId", assetLabelingDto.getLabelingId());
		params.put("logo", image);
	
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(classLoader.getResource("report/Label.jasper"));
		jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		jasperReport.setWhenResourceMissingType(WhenResourceMissingTypeEnum.NULL);
	
		JasperPrint jPrint = (JasperPrint) JasperFillManager .fillReport(jasperReport, params, conn);
		
		try {
			File temp_file = File.createTempFile("AssetLabeling",".pdf");
			//JasperExportManager.exportReportToPdfFile(jPrint, temp_file.getAbsolutePath());
			//DownloadUtil.downloadFile(temp_file.getAbsolutePath(), ContentType.APPLICATIONFILE.getValue());
			//String s1="var iFrame = $('#iFramePdf');iFrame.attr('src', '"+temp_file.getAbsolutePath()+"'); printTrigger('iFramePdf');";
			/*String s1="document.getElementById('iFramePdf').src='"+temp_file.getAbsolutePath()+"'; printTrigger('iFramePdf');";
			System.out.println("s1=="+s1);
			Clients.evalJavaScript(s1);*/
			
			//File file = new File(temp_file.getAbsolutePath());
			//byte[] bFile = new byte[(int) file.length()];
			FileOutputStream fos = new FileOutputStream (temp_file); 
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos); // your output goes here
	        exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT,"var pp = getPrintParams();pp.interactive =pp.constants.interactionLevel.automatic;print(pp);");
	        exporter.exportReport();
	        //"var pp = this.getPrintParams();pp.interactive=pp.constants.interactionLevel.silent;pp.NumCopies=1; this.disclosed= true ;this.print({bUI: false,bSilent: false,bShrinkToFit: true,printParams:pp});");
	        
	        /*try {
	            baos.writeTo(fos);
	        } catch(IOException ioe) {
	            // Handle exception here
	            ioe.printStackTrace();
	        } finally {
	            fos.close();
	        }*/
	        
			aimagen = new AMedia("AssetLabeling.pdf", "pdf", "application/pdf", temp_file,true);
			
			
			//File f = new File(temp_file.getAbsolutePath());
			//f.delete();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//jasperReport.
		return aimagen;
	}

	@Transactional(readOnly = true)
	public List<AssetLabelingDTO> getParameterDtoByExample(AssetLabelingExample example, int limit, int offset) {
		return assetLabelingFinder.selectByExample(example, new RowBounds(offset, limit));
	}
	
	@Transactional(readOnly = true)
	public List<AssetLabelingDTO> getParameterDtoByExample(AssetLabelingExample example) {
		return assetLabelingFinder.selectByExample(example);
	}
	
	@Transactional(readOnly = true)
	public int countByExample(AssetLabelingExample example) {
		return assetLabelingFinder.countByExample(example);
	}	
	
	@Override
	@Transactional(readOnly=true)
	public  AssetLabelingDTO getParameterById(Long id) {
		AssetLabelingDTO assetLabelingDTO = new AssetLabelingDTO();
		AssetLabeling assetLabeling = assetLabelingMapper.selectByPrimaryKey(id);
		assetLabelingDTO = modelMapper.map(assetLabeling, AssetLabelingDTO.class);
		
		//assetLabelingDTO = modelMapper.map(assetLabeling, AssetLabelingDTO.class);
		//List<AssetLabelingDetailDTO> assetLabelingDetail = assetLabelingDetailMapper.selectByHeaderKey(id);
		//assetLabelingDTO.setAssetLabelingDetailDto(assetLabelingDetail);
		return assetLabelingDTO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<KeyValue> getLabelingPrintingType() {
		return assetLabelingFinder.getLookupKeyValues(AssetLabelingPrintingType.CODE.getCode(), null);
		
	}
	@Override
	@Transactional(readOnly=true)
	public List<AssetLabelingDetailDTO> getAssetLabelingDetailDtoByParameterId(Long id) {
		AssetLabelingDetailExample example = new AssetLabelingDetailExample();
		example.createCriteria().andParameterHdrIdEqualTo(id);
		List<AssetLabelingDetailDTO> returnList = new ArrayList<>();
		
		for(AssetLabelingDetail trd : assetLabelingDetailMapper.selectByExample(example)){
			AssetLabelingDetailDTO trdDto = new AssetLabelingDetailDTO();
			trdDto = modelMapper.map(trd, AssetLabelingDetailDTO.class);
			/*trdDto.setMainTask(taskServiceImpl.getTaskById(trdDto.getTaskId()));
			if(trdDto.getSccuessTaskId() != null){
				if(trdDto.getSccuessTaskId() != -1){
					trdDto.setSuccessTask(taskServiceImpl.getTaskById(trdDto.getSccuessTaskId()));
				}
				if(trdDto.getErrorTask()!= null){
					if(trdDto.getErrorTaskId() != -1){
						trdDto.setErrorTask(taskServiceImpl.getTaskById(trdDto.getErrorTaskId()));
					}
				}
			}*/
			returnList.add(trdDto);
		
		}
		
		return returnList;
		
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void delete(AssetLabelingDTO assetLabelingDto) throws Exception{
		
		assetLabelingDetailMapper.deleteByHeaderKey(assetLabelingDto.getLabelingId());   
		assetLabelingMapper.deleteByPrimaryKey(assetLabelingDto.getLabelingId());
			
	}

	@Override
	@Transactional(readOnly = true)
	public List<Branch> getBranchByExample(BranchExample example, int limit, int offset) {
		return branchMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	@Transactional(readOnly = true)
	public int countBranchByExample(BranchExample example) {
		return branchMapper.countByExample(example);
	}

	public Branch getBranchById(Long id,Long companyId) {
		return branchMapper.selectByPrimaryKey(id,companyId);
	}
	
	public AssetLabelingDetail getDetailByPrimaryKey(Long id) {
		return assetLabelingDetailMapper.selectByHeaderKey(id);
	}

	@Override
	public List<SSOALocation> getLocationByExample(SSOALocationExample example, int limit, int offset) {
		return ssoaLocationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, limit));
	}

	@Override
	public int countLocationByExample(SSOALocationExample example) {
		return ssoaLocationMapper.countByExample(example);
	}

	@Override
	public int countAssetByCriteria(AssetLabelingDetailExample example) {
		return assetLabelingDetailMapper.countAssetByCriteria(example);
	}

	@Override
	public List<AssetLabelingDetailDTO> getAssetByCriteria(AssetLabelingDetailExample example, int limit, int offset) {
		return assetLabelingDetailMapper.selectAssetByCriteria(example, new RowBounds(offset, limit));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AssetLabelingDetailDTO> getDetailAssetsByPrimaryKey(Long id) {
		return assetLabelingDetailMapper.selectDetailByHeaderId(id);
		
	}
	
}