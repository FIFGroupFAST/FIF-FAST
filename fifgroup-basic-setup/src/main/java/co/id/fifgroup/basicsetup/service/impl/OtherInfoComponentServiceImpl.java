package co.id.fifgroup.basicsetup.service.impl;

import static co.id.fifgroup.core.validation.ValidationRule.isNumeric;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import co.id.fifgroup.basicsetup.common.OtherInfoComponentValue;
import co.id.fifgroup.basicsetup.constant.OtherInfoDataType;
import co.id.fifgroup.basicsetup.constant.OtherInfoType;
import co.id.fifgroup.basicsetup.constant.ScopeType;
import co.id.fifgroup.basicsetup.dao.OtherInfoDetailMapper;
import co.id.fifgroup.basicsetup.dao.OtherInfoHeaderMapper;
import co.id.fifgroup.basicsetup.domain.LookupHeader;
import co.id.fifgroup.basicsetup.domain.OtherInfoComponent;
import co.id.fifgroup.basicsetup.domain.OtherInfoDetail;
import co.id.fifgroup.basicsetup.domain.OtherInfoDetailExample;
import co.id.fifgroup.basicsetup.domain.OtherInfoHeader;
import co.id.fifgroup.basicsetup.domain.OtherInfoHeaderExample;
import co.id.fifgroup.basicsetup.service.LookupService;
import co.id.fifgroup.basicsetup.service.OtherInfoComponentService;
import co.id.fifgroup.core.ui.lookup.KeyValue;
import co.id.fifgroup.core.ui.lookup.LookupCriteria;
import co.id.fifgroup.core.ui.lookup.LookupWindow;
import co.id.fifgroup.core.util.ErrorMessageUtil;

@Transactional
@Service("otherInfoComponentServiceImpl")
public class OtherInfoComponentServiceImpl implements OtherInfoComponentService {

	private static Logger log = LoggerFactory.getLogger(OtherInfoComponentServiceImpl.class);
	
	@Autowired
	private OtherInfoHeaderMapper otherInfoHeaderMapper;
	@Autowired
	private OtherInfoDetailMapper otherInfoDetailMapper;
	@Autowired
	private LookupService lookupServiceImpl;
	
	@Override
	@Transactional(readOnly=true)
	public OtherInfoComponent getOtherInfoComponent(String formName, Long groupId, Long companyId) {
		OtherInfoHeader otherInfoHeader = getOtherInfoHeader(formName, groupId);
		if(otherInfoHeader != null) {
			OtherInfoDetailExample otherInfoDetailExample = new OtherInfoDetailExample();
			List<Long> scope = new ArrayList<Long>();
			scope.add(ScopeType.COMMON.getValue());
			scope.add(companyId);
			otherInfoDetailExample.createCriteria().andOtherInfoHdrIdEqualTo(otherInfoHeader.getOtherInfoHdrId()).andCompanyScopeIn(scope);
			otherInfoDetailExample.setOrderByClause("OTHER_INFO_DTL_ID ASC");
			List<OtherInfoDetail> resultOtherInfoDetail = otherInfoDetailMapper.selectByExample(otherInfoDetailExample);
			OtherInfoComponent otherInfoComponent = new OtherInfoComponent();
			List<OtherInfoDetail> otherInfoDetailComponent = new ArrayList<OtherInfoDetail>();
			
			Grid grid = new Grid();
			Columns columns = new Columns();
			Column column1 = new Column();
			column1.setWidth("250px");
			column1.setAlign("right");
			columns.appendChild(column1);
			columns.appendChild(new Column());
			grid.appendChild(columns);
			
			Rows rows = new Rows();
			
			for(OtherInfoDetail otherInfoDetail : resultOtherInfoDetail) {
				if(otherInfoDetail.getDataType().equals(OtherInfoDataType.NUMERIC.toString()) || otherInfoDetail.getDataType().equals(OtherInfoDataType.TEXT.toString())) {
					Row row = new Row();
					Hbox hbox = new Hbox();
					Label lblComponent = new Label(otherInfoDetail.getPromptName());
					hbox.appendChild(lblComponent);
					if(otherInfoDetail.getIsMandatory()) {
						Label lblMandatory = new Label(Labels.getLabel("common.required"));
						lblMandatory.setStyle("color:red");
						hbox.appendChild(lblMandatory);
					}
					Textbox txtComponent = new Textbox();
					txtComponent.setId(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
					txtComponent.setMaxlength(otherInfoDetail.getMaxLength());
					txtComponent.setWidth("250px");
					txtComponent.setVisible(otherInfoDetail.getIsDisplayed());
					hbox.setVisible(otherInfoDetail.getIsDisplayed());
					row.appendChild(hbox);
					row.appendChild(txtComponent);
					rows.appendChild(row);
				} else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.DATE.toString())) {
					Row row = new Row();
					Hbox hbox = new Hbox();
					Label lblComponent = new Label(otherInfoDetail.getPromptName());
					hbox.appendChild(lblComponent);
					if(otherInfoDetail.getIsMandatory()) {
						Label lblMandatory = new Label(Labels.getLabel("common.required"));
						lblMandatory.setStyle("color:red");
						hbox.appendChild(lblMandatory);
					}
					Datebox dtbComponent = new Datebox();
					dtbComponent.setId(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
					dtbComponent.setWidth("100px");
					dtbComponent.setVisible(otherInfoDetail.getIsDisplayed());
					hbox.setVisible(otherInfoDetail.getIsDisplayed());
					row.appendChild(hbox);
					row.appendChild(dtbComponent);
					rows.appendChild(row);
				} else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.BOOLEAN.toString())) {
					Row row = new Row();
					Hbox hbox = new Hbox();
					Label lblComponent = new Label(otherInfoDetail.getPromptName());
					hbox.appendChild(lblComponent);
					if(otherInfoDetail.getIsMandatory()) {
						Label lblMandatory = new Label(Labels.getLabel("common.required"));
						lblMandatory.setStyle("color:red");
						hbox.appendChild(lblMandatory);
					}
					Checkbox chkComponent = new Checkbox();
					chkComponent.setId(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
					chkComponent.setVisible(otherInfoDetail.getIsDisplayed());
					hbox.setVisible(otherInfoDetail.getIsDisplayed());
					row.appendChild(hbox);
					row.appendChild(chkComponent);
					rows.appendChild(row);
				} else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.LOOKUP.toString())) {
					final LookupHeader lookupHeader = lookupServiceImpl.getLookupHeaderByPrimaryKey(otherInfoDetail.getLookupId());
					Row row = new Row();
					Hbox hbox = new Hbox();
					Label lblComponent = new Label(otherInfoDetail.getPromptName());
					hbox.appendChild(lblComponent);
					if(otherInfoDetail.getIsMandatory()) {
						Label lblMandatory = new Label(Labels.getLabel("common.required"));
						lblMandatory.setStyle("color:red");
						hbox.appendChild(lblMandatory);
					}
					LookupWindow lookupWindowComponent = new LookupWindow();
					lookupWindowComponent.setTitle(otherInfoDetail.getPromptName());
					lookupWindowComponent.setSearchText(otherInfoDetail.getPromptName());
					lookupWindowComponent.setHeaderLabel(otherInfoDetail.getPromptName());
					lookupWindowComponent.setWidth("250px");
					lookupWindowComponent.setVisible(otherInfoDetail.getIsDisplayed());
					hbox.setVisible(otherInfoDetail.getIsDisplayed());
					lookupWindowComponent.setGroupId(groupId);
					lookupWindowComponent.setCompanyId(companyId);
					lookupWindowComponent.setLookupCriteria(new LookupCriteria() {
						
						/**
						 * 
						 */
						private static final long serialVersionUID = -2652346099066901224L;

						@Override
						public String getParentDetailCode() {
							return null;
						}
						
						@Override
						public String getLookupName() {
							return lookupHeader.getName();
						}
					});
					lookupWindowComponent.setId(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
					row.appendChild(hbox);
					row.appendChild(lookupWindowComponent);
					rows.appendChild(row);
				}
				otherInfoDetailComponent.add(otherInfoDetail);
			}
			grid.appendChild(rows);
			otherInfoComponent.setComponent(grid);
			otherInfoComponent.setOtherInfoDetail(otherInfoDetailComponent);
			return otherInfoComponent;
		}
		return null;
	}

	@Transactional(readOnly=true)
	private OtherInfoHeader getOtherInfoHeader(String formName, Long groupId) {
		if(groupId != null) {
			OtherInfoHeaderExample otherInfoHeaderExample = new OtherInfoHeaderExample();
			otherInfoHeaderExample.createCriteria().andFormNameEqualTo(formName).andGroupIdEqualTo(groupId);
			List<OtherInfoHeader> resultOtherInfoHeader = otherInfoHeaderMapper.selectByExample(otherInfoHeaderExample);
			if(resultOtherInfoHeader.size() == 1) {
				return resultOtherInfoHeader.get(0);
			}			
		}
		return null;
	}

	@Override
	public boolean validate(OtherInfoComponent otherInfoComponent) {
		boolean validate = true;
		for(OtherInfoDetail otherInfoDetail : otherInfoComponent.getOtherInfoDetail()) {
			Component component = otherInfoComponent.getComponent().getFellowIfAny(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
			if(component != null) {
				if(otherInfoDetail.getDataType().equals(OtherInfoDataType.TEXT.toString()) || otherInfoDetail.getDataType().equals(OtherInfoDataType.NUMERIC.toString())) {
					Textbox txtComponent = (Textbox) component;
					if(otherInfoDetail.getDataType().equals(OtherInfoDataType.TEXT.toString())) {
						validate &= validateText(txtComponent, otherInfoDetail);
					} else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.NUMERIC.toString())) {
						validate &= validateNumeric(txtComponent, otherInfoDetail);
					}
				} else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.DATE.toString())) {
					Datebox dtbComponent = (Datebox) component;
					validate &= validateDate(dtbComponent, otherInfoDetail);
				}  else if(otherInfoDetail.getDataType().equals(OtherInfoDataType.LOOKUP.toString())) {
					LookupWindow lookupWindowComponent = (LookupWindow) component;
					validate &= validateLookup(lookupWindowComponent, otherInfoDetail);
				}
			}
		}
		return validate;
	}
	
	private void setErrorMessage(Component component, String errMsg) {
		ErrorMessageUtil.setErrorMessage(component, errMsg);
	}
	
	private boolean validateText(Textbox txtComponent, OtherInfoDetail otherInfoDetail) {
		ErrorMessageUtil.clearErrorMessage(txtComponent);
		if(otherInfoDetail.getIsMandatory()) {
			if(isNullOrEmpty(txtComponent.getValue())) {
				setErrorMessage(txtComponent, Labels.getLabel("common.mustBeFill", new Object[] { otherInfoDetail.getPromptName() }));
				return false;
			}
		}
		return true;
	}
	
	private boolean validateNumeric(Textbox txtComponent, OtherInfoDetail otherInfoDetail) {
		ErrorMessageUtil.clearErrorMessage(txtComponent);
		if(otherInfoDetail.getIsMandatory()) {
			if(isNullOrEmpty(txtComponent.getValue())) {
				setErrorMessage(txtComponent, Labels.getLabel("common.mustBeFill", new Object[] { otherInfoDetail.getPromptName() }));
				return false;
			}
		}
		if(!isNumeric(txtComponent.getValue()) && !isNullOrEmpty(txtComponent.getValue())) {
			setErrorMessage(txtComponent, Labels.getLabel("common.mustBeNumeric", new Object[] { otherInfoDetail.getPromptName() }));
			return false;
		}
		return true;
	}
	
	private boolean validateDate(Datebox dtbComponent, OtherInfoDetail otherInfoDetail) {
		ErrorMessageUtil.clearErrorMessage(dtbComponent);
		if(otherInfoDetail.getIsMandatory()) {
			if(dtbComponent.getValue() == null) {
				setErrorMessage(dtbComponent, Labels.getLabel("common.mustBeFill", new Object[] { otherInfoDetail.getPromptName() }));
				return false;
			}
		}
		return true;
	}
	
	private boolean validateLookup(LookupWindow lookupWindowComponent, OtherInfoDetail otherInfoDetail) {
		ErrorMessageUtil.clearErrorMessage(lookupWindowComponent);
		if(otherInfoDetail.getIsMandatory()) {
			if(lookupWindowComponent.getKeyValue() == null) {
				setErrorMessage(lookupWindowComponent, Labels.getLabel("common.mustBeFill", new Object[] { otherInfoDetail.getPromptName() }));
				return false;
			}
		}
		return true;
	}

	@Override
	public void setOtherInfoDetailValue(OtherInfoComponent otherInfoComponent,
			OtherInfoComponentValue otherInfoComponentValue) {
		Component component = otherInfoComponent.getComponent();
		DateFormat df = new SimpleDateFormat(OtherInfoType.FORMAT_DATE.getValue());
		for(OtherInfoDetail otherInfoDetail : otherInfoComponent.getOtherInfoDetail()) {
			Component componentOtherInfo = component.getFellowIfAny(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
			if(componentOtherInfo != null) {
				if(OtherInfoDataType.TEXT.toString().equals(otherInfoDetail.getDataType()) || OtherInfoDataType.NUMERIC.toString().equals(otherInfoDetail.getDataType())) {
					Textbox txtComponent = (Textbox) componentOtherInfo;
					otherInfoComponentValue.setValue(otherInfoDetail.getOtherInfoDtlId(), txtComponent.getValue());
				} else if(OtherInfoDataType.DATE.toString().equals(otherInfoDetail.getDataType())) {
					Datebox dtbComponent = (Datebox) componentOtherInfo;
					otherInfoComponentValue.setValue(otherInfoDetail.getOtherInfoDtlId(), dtbComponent.getValue() == null ? null : df.format(dtbComponent.getValue()).toString());
				} else if(OtherInfoDataType.BOOLEAN.toString().equals(otherInfoDetail.getDataType())) {
					Checkbox chkComponent = (Checkbox) componentOtherInfo;
					otherInfoComponentValue.setValue(otherInfoDetail.getOtherInfoDtlId(), chkComponent.isChecked() ? OtherInfoType.BOOLEAN_TRUE.getValue() : OtherInfoType.BOOLEAN_FALSE.getValue());
				} else if(OtherInfoDataType.LOOKUP.toString().equals(otherInfoDetail.getDataType())) {
					LookupWindow lookupWindowComponent = (LookupWindow) componentOtherInfo;
					KeyValue keyValue = lookupWindowComponent.getKeyValue();
					otherInfoComponentValue.setValue(otherInfoDetail.getOtherInfoDtlId(), keyValue == null ? null : keyValue.getKey().toString());
				}
			}
		}
	}

	@Override
	public void setOtherInfoDetailValueToComponent(
			OtherInfoComponent otherInfoComponent, Long otherInfoDetailIdInValue, String value) {
		if(otherInfoDetailIdInValue != null) {
			for(OtherInfoDetail otherInfoDetail : otherInfoComponent.getOtherInfoDetail()) {
				if(otherInfoDetail.getOtherInfoDtlId().longValue() == otherInfoDetailIdInValue.longValue()) {
					Component component = otherInfoComponent.getComponent().getFellowIfAny(otherInfoDetail.getDetailCode() + OtherInfoType.SUFFIX_ID + "_" + otherInfoDetail.getOtherInfoDtlId());
					if(component != null) {
						if(OtherInfoDataType.TEXT.toString().equals(otherInfoDetail.getDataType()) || OtherInfoDataType.NUMERIC.toString().equals(otherInfoDetail.getDataType())) {
							Textbox txtComponent = (Textbox) component;
							txtComponent.setValue(value);
						} else if(OtherInfoDataType.DATE.toString().equals(otherInfoDetail.getDataType())) {
							Datebox dtbComponent = (Datebox) component;
							DateFormat dateFormat = new SimpleDateFormat(OtherInfoType.FORMAT_DATE.getValue());
							dateFormat.setLenient(false);
							try {
								if(value != null && !value.equals("")) {
									dtbComponent.setValue(dateFormat.parse(value));									
								}
							} catch (WrongValueException | ParseException e) {
								log.error(e.getMessage(), e);
							}
						} else if(OtherInfoDataType.BOOLEAN.toString().equals(otherInfoDetail.getDataType())) {
							Checkbox chkComponent = (Checkbox) component;
							if(OtherInfoType.BOOLEAN_TRUE.getValue().equals(value)) {
								chkComponent.setChecked(true);
							} else {
								chkComponent.setChecked(false);
							}
						} else if(OtherInfoDataType.LOOKUP.toString().equals(otherInfoDetail.getDataType())) {
							LookupWindow lookupWindowComponent = (LookupWindow) component;
							LookupHeader lookupHeader = lookupServiceImpl.getLookupHeaderByPrimaryKey(otherInfoDetail.getLookupId());
							try {
								lookupWindowComponent.setValueByCode(lookupHeader.getName(), value);
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
						}
						break;
					}
				}
			}			
		}
	}

	@Override
	public void disableAllComponent(Grid grid, boolean disabled) {
		for(Component row : grid.getRows().getChildren()) {
			for(Component component : row.getChildren()) {
				if(component instanceof Textbox) {
					Textbox txtComponent = (Textbox) component;
					txtComponent.setDisabled(disabled);
				} else if(component instanceof Datebox) {
					Datebox dtbComponent = (Datebox) component;
					dtbComponent.setDisabled(disabled);
				} else if(component instanceof Checkbox) {
					Checkbox chkComponent = (Checkbox) component;
					chkComponent.setDisabled(disabled);
				} else if(component instanceof LookupWindow) {
					LookupWindow lookupWindowComponent = (LookupWindow) component;
					lookupWindowComponent.setDisabled(disabled);
				}
			}
		}
	}
	
}
