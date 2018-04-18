package co.id.fifgroup.core.finder;

import co.id.fifgroup.core.domain.interfacing.Budget;

public interface BudgetFinder {
	
	/**
	 * Call function check_available_budget
	 * @param budget
	 * @return true or false
	 */
	public boolean checkBudgetAvailability(Budget budget);
	
	/**
	 * Call function book_budget
	 * 
	 * @param budget
	 * @return true or false
	 */
	public void bookBudget(Budget budget);
	
	/**
	 * call function force_book_budget
	 * 
	 * @param budget
	 */
	public void forceBookBudget(Budget budget);
	
	/**
	 * call function actualize_budget
	 * 
	 * @param budget
	 */
	public void actualizeBudget(Budget budget);
	
	/**
	 * call function force_actualize_budget
	 * 
	 * @param budget
	 */
	public void forceActualizeBudget(Budget budget);
	
	/**
	 * call function release budget
	 * 
	 * @param budget
	 */
	public void releaseBudget(Budget budget);
	
	/**
	 * call function correction_budget
	 * 
	 * @param budget
	 */
	public void reverseBudget(Budget budget);
}
