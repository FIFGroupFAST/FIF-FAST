package co.id.fifgroup.basicsetup.common;


public interface HistoricalService<T extends History> {

	public boolean hasFuture(T object);
	public boolean isCurrent(T object);
	public boolean isFuture(T object);
	public boolean isPastHistory(T object);
}
