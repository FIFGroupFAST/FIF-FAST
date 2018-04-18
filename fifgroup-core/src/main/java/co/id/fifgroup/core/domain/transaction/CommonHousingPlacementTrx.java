package co.id.fifgroup.core.domain.transaction;

public interface CommonHousingPlacementTrx extends CommonTrx{
	public Boolean isOverPlafond();
	public Boolean isPrivateHouse();
	public Boolean isDifferentLocation();
}
