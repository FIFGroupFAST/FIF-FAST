package co.id.fifgroup.audit;

public class AuditFunction {
	public static final AuditFunction SYSTEM_ADMIN = new AuditFunction("0",
			"System Admin");

	public static final AuditFunction OPERATION_ADMIN = new AuditFunction("1",
			"Operation Admin");

	public static final AuditFunction GROUP_SETUP = new AuditFunction("2",
			"Group Setup");

	public static final AuditFunction USER_SETUP = new AuditFunction("3",
			"User Setup");

	public static final AuditFunction LOCK_USER = new AuditFunction("4",
			"Lock User");

	public static final AuditFunction UNLOCK_USER = new AuditFunction("5",
			"Unlock User");

	public static final AuditFunction CHANGE_PASSWORD = new AuditFunction("6",
			"Change Password");

	public static final AuditFunction RESET_PASSWORD = new AuditFunction("7",
			"Reset Password");

	public static final AuditFunction BRANCH_SETUP = new AuditFunction("8",
			"Branch Setup");

	public static final AuditFunction HOLIDAY_SETUP = new AuditFunction("9",
			"Holiday Setup");

	public static final AuditFunction REFERENCE_SETUP = new AuditFunction("10",
			"Reference Setup");

	public static final AuditFunction UT_ACCOUNT_ACCESS_RIGHT = new AuditFunction(
			"11", "Account Access Right");

	public static final AuditFunction EXCHANGE_RATE_SETUP = new AuditFunction(
			"12", "Exchange Rate Setup");

	public static final AuditFunction UTMC_SETUP = new AuditFunction("13",
			"UTMC Setup");

	public static final AuditFunction FUND_SETUP = new AuditFunction("14",
			"Fund Setup");

	public static final AuditFunction FRONT_END_LOAD = new AuditFunction("15",
			"Front End Load");

	public static final AuditFunction EXIT_FEE = new AuditFunction("16",
			"Exit Fee");

	public static final AuditFunction SWITCH_FEE = new AuditFunction("17",
			"Switch Fee");

	public static final AuditFunction COMMISSION_TRAILER = new AuditFunction(
			"18", "Commission Trailer");

	public static final AuditFunction PROMOTION_STD = new AuditFunction("19",
			"Promotion Discount");

	public static final AuditFunction INVESTMENT_PACKAGE = new AuditFunction(
			"20", "Investment Package");

	public static final AuditFunction BUNDLED_PRODUCT = new AuditFunction("21",
			"Bundled Product");

	public static final AuditFunction DISCOUNT = new AuditFunction("22",
			"Regular Discount");

	public static final AuditFunction UT_ACCOUNT_DATA = new AuditFunction("23",
			"UT Account Data Maintenance");

	public static final AuditFunction SUBSCRIPTION_STD = new AuditFunction(
			"24", "Subscription Standard");

	public static final AuditFunction SUBSCRIPTION_PKG = new AuditFunction(
			"25", "Subscription Package");

	public static final AuditFunction RSP = new AuditFunction("26", "RSP");

	public static final AuditFunction REDEMPTION = new AuditFunction("27",
			"Redemption");

	public static final AuditFunction SWITCH = new AuditFunction("28", "Switch");

	public static final AuditFunction COOLING_OFF = new AuditFunction("29",
			"Cooling Off");

	public static final AuditFunction PLEDGE = new AuditFunction("30", "Pledge");

	public static final AuditFunction UNPLEDGE = new AuditFunction("31",
			"Unpledge");

	public static final AuditFunction INTERNAL_TRANSFER = new AuditFunction(
			"32", "Internal Transfer");

	public static final AuditFunction EXTERNAL_TRANSFER_IN = new AuditFunction(
			"33", "External Transfer In");

	public static final AuditFunction EXTERNAL_TRANSFER_OUT = new AuditFunction(
			"34", "External Transfer Out");

	public static final AuditFunction ORDER_CANCELLATION = new AuditFunction(
			"35", "Order Cancellation");

	public static final AuditFunction RSP_AMENDMENT = new AuditFunction("36",
			"RSP Amendment");

	public static final AuditFunction FEE_WAIVER = new AuditFunction("37",
			"Fee Waiver");

	public static final AuditFunction UPDATE_FUND_PRICE = new AuditFunction(
			"38", "Update Fund Price");

	public static final AuditFunction EPF_CONFIRMATION = new AuditFunction(
			"39", "EPF Confirmation");

	public static final AuditFunction FUND_REPRICE = new AuditFunction("40",
			"Fund Reprice");

	public static final AuditFunction DIVIDEND_SETUP = new AuditFunction("41",
			"Dividend Setup");

	public static final AuditFunction UNIT_SPLIT_OR_MERGE_SETUP = new AuditFunction(
			"42", "Unit Split/Merge Setup");

	public static final AuditFunction FUND_CLOSURE = new AuditFunction("43",
			"Fund Closure");

	public static final AuditFunction BRANCH_CLOSURE = new AuditFunction("44",
			"Branch Closure");

	public static final AuditFunction ACCOUNT_MERGE = new AuditFunction("45",
			"Account Merge");

	public static final AuditFunction ACCOUNT_TRANSFER_OTHER_BRANCH = new AuditFunction(
			"46", "Account Transfer Other Branch");

	/* public static final AuditFunction ...(please add more as necessary) */

	private final String id;

	private final String description;

	protected AuditFunction(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "AuditFunction [description=" + description + ", id=" + id + "]";
	}
}
