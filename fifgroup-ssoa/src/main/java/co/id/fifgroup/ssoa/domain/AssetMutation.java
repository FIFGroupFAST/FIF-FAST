package co.id.fifgroup.ssoa.domain;

public class AssetMutation {
	private boolean check;
	private String no_asset;	
	private String type;	
	private String branch_code;	
	private String branch_name;	
	private String location_code;	
	private String location_name;	
	private String company_id;	
	private String item_category;	
	private String date_of_service;
	private String description;
	private String serial_number;
	private String own_type;
	private String stock_opname_result;
	private String recomendation;
	public AssetMutation() {
		
	}
	public AssetMutation(boolean check, String no_asset, String type, String branch_code, String branch_name,
			String location_code, String location_name, String company_id, String item_category, String date_of_service,
			String description, String serial_number, String own_type, String stock_opname_result,
			String recomendation) {
		super();
		this.check = check;
		this.no_asset = no_asset;
		this.type = type;
		this.branch_code = branch_code;
		this.branch_name = branch_name;
		this.location_code = location_code;
		this.location_name = location_name;
		this.company_id = company_id;
		this.item_category = item_category;
		this.date_of_service = date_of_service;
		this.description = description;
		this.serial_number = serial_number;
		this.own_type = own_type;
		this.stock_opname_result = stock_opname_result;
		this.recomendation = recomendation;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	
	public String getNo_asset() {
		return no_asset;
	}
	public void setNo_asset(String no_asset) {
		this.no_asset = no_asset;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public String getLocation_code() {
		return location_code;
	}
	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public String getDate_of_service() {
		return date_of_service;
	}
	public void setDate_of_service(String date_of_service) {
		this.date_of_service = date_of_service;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getOwn_type() {
		return own_type;
	}
	public void setOwn_type(String own_type) {
		this.own_type = own_type;
	}
	public String getStock_opname_result() {
		return stock_opname_result;
	}
	public void setStock_opname_result(String stock_opname_result) {
		this.stock_opname_result = stock_opname_result;
	}
	public String getRecomendation() {
		return recomendation;
	}
	public void setRecomendation(String recomendation) {
		this.recomendation = recomendation;
	}
	
	
	
}
