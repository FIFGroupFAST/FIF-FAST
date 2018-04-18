package co.id.fifgroup.workstructure.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import co.id.fifgroup.core.validation.ValidationException;
import co.id.fifgroup.workstructure.domain.Location;
import co.id.fifgroup.workstructure.domain.LocationVersion;
import co.id.fifgroup.workstructure.service.LocationSetupService;

import co.id.fifgroup.workstructure.dto.LocationDTO;
import co.id.fifgroup.workstructure.validation.LocationValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class LocationSetupTest {

	LocationSetupService locationSetupService;
	
	
	/*public void testSaveLocation() { 
		Location jakarta = new Location();
		jakarta.setLocationName("Jakarta");
		jakarta.setLocationCode("002");
		jakarta.setCompanyId(1L);
				
		//locationSetupService.save(new LocationDto(jakarta, null));
		locationSetupService.save(new LocationDto());
		
		List<LocationDto> result =  locationSetupService.getLocations();
		
		assertEquals(1, result.size());
		assertEquals(jakarta.getLocationName(), result.get(0).getLocation().getLocationName());
	}*/
	
	@Test
	public void testBeanValidation() throws Exception {
		/*ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<TestDTO>> violations = validator.validate(new TestDTO());
		
		for (ConstraintViolation<TestDTO> violation : violations) {
			System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
		}*/
		
		LocationValidator locationValidator = new LocationValidator();
		try {
			Location location = new Location();
			LocationVersion version = new LocationVersion();
			locationValidator.validate(new LocationDTO());
		}
		catch (ValidationException ve) {
			ve.getConstraintViolations();
			System.out.println("size = " + ve.getConstraintViolations().size());
			for(String key : ve.getConstraintViolations().keySet()) {
				System.out.println(ve.getConstraintViolations().get(key));
			}
		}
		
		locationSetupService.save(new LocationDTO());
		
	}
}
