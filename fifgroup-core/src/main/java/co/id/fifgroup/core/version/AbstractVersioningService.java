package co.id.fifgroup.core.version;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.transaction.annotation.Transactional;

import co.id.fifgroup.core.util.DateUtil;
import co.id.fifgroup.core.validation.EndDateValidator;
import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.core.validation.VersionValidator;

public abstract class AbstractVersioningService<T extends Version> implements VersioningService<T>{
	
	@Transactional
	@Override
	public void save(T subject) throws Exception {
		//insert new setup
		if(subject.getId() == null) {
			VersionValidator versionValidator = new VersionValidator();
			try {
				versionValidator.validate(subject);
				try {
					validate(subject);
				} catch (ValidationException e) {
					throw e;
				}
			} catch (ValidationException e) {
				Map<String, String> errors = new HashMap<String, String>();
				errors.putAll(e.getConstraintViolations());
				try {
					validate(subject);
				} catch (ValidationException e1) {
					errors.putAll(e1.getConstraintViolations());
				}
				if(errors.size() > 0) throw new ValidationException(errors);
			}
			
			subject.setVersionNumber(1);
			subject.setId(getNextHeaderId());
			try {
				insertHeader(subject);
				insertDetail(subject);
			} catch (Exception e) {
				subject.setId(null);
				throw e;				
			}
		} else {
			T subjectOriginal = findByIdAndVersionNumber(subject.getId(), subject.getVersionNumber());
			if(subject.getVersionId() != null) {
				if(subjectOriginal.isCurrent()) {  // end date current
					EndDateValidator endDateValidator = new EndDateValidator();
					endDateValidator.validate(subject);					
				} else if(subjectOriginal.isFuture()) {  // edit future
					VersionValidator versionValidator = new VersionValidator();
					try {
						versionValidator.validate(subject);
						try {
							validate(subject);
						} catch (ValidationException e) {
							throw e;
						}
					} catch (ValidationException e) {
						Map<String, String> errors = new HashMap<String, String>();
						errors.putAll(e.getConstraintViolations());
						try {
							validate(subject);
						} catch (ValidationException e1) {
							errors.putAll(e1.getConstraintViolations());
						}
						if(errors.size() > 0) throw new ValidationException(errors);
					}
					
					T previousVersion = getPreviousVersion(subject);
					if(previousVersion != null) {
						if(previousVersion.isCurrent()) {
							Calendar calendarPrev = Calendar.getInstance();
							calendarPrev.setTime(subject.getDateFrom());
							calendarPrev.add(Calendar.DAY_OF_MONTH, -1);
							previousVersion.setDateTo(DateUtils.truncate(calendarPrev.getTime(), Calendar.DATE));
							updateDetail(previousVersion);
						}
					}					
				}
				updateDetail(subject);
				updateHeader(subject);
				
			} else {   // insert new future
				VersionValidator versionValidator = new VersionValidator();
				try {
					versionValidator.validate(subject);
					try {
						validate(subject);
					} catch (ValidationException e) {
						throw e;
					}
				} catch (ValidationException e) {
					Map<String, String> errors = new HashMap<String, String>();
					errors.putAll(e.getConstraintViolations());
					try {
						validate(subject);
					} catch (ValidationException e1) {
						errors.putAll(e1.getConstraintViolations());
					}
					if(errors.size() > 0) throw new ValidationException(errors);
				}
				
				subject.setVersionNumber(subject.getVersionNumber() + 1);
				T previousVersion = getPreviousVersion(subject);
				if(previousVersion != null) {
					if(previousVersion.isCurrent()) {
						Calendar calendarPrev = Calendar.getInstance();
						calendarPrev.setTime(subject.getDateFrom());
						calendarPrev.add(Calendar.DAY_OF_MONTH, -1);
						previousVersion.setDateTo(DateUtils.truncate(calendarPrev.getTime(), Calendar.DATE));
						updateDetail(previousVersion);
					}
				}
				insertDetail(subject);
				updateHeader(subject);
			}
		}
	}

	@Transactional
	@Override
	public void delete(T subject) {
		if(subject.isFuture()) {
			T previous = getPreviousVersion(subject);
			if(previous != null) {
				deleteDetail(subject);
				updateHeader(subject);
				if(previous.isCurrent()) {
					previous.setDateTo(DateUtil.MAX_DATE);
					updateDetail(previous);
				}
			}
			else {
				deleteDetail(subject);
				deleteHeader(subject);
			}
		}
	}

	@Override
	public final T getPreviousVersion(T subject) {
		return findByIdAndVersionNumber(subject.getId(), subject.getVersionNumber() - 1);
	}
	
	public abstract void validate(T subject) throws ValidationException;
	
}
