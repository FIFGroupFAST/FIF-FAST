package co.id.fifgroup.audit.impl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.id.fifgroup.audit.AttributeComparator;
import co.id.fifgroup.audit.AttributesValuesComparison;
import co.id.fifgroup.audit.BooleanStatus;
import co.id.fifgroup.audit.TempQueue;
import co.id.fifgroup.audit.ToTraversableConverter;
import co.id.fifgroup.audit.TraversableMap;
import co.id.fifgroup.audit.ControlType.Control;
import co.id.fifgroup.audit.model.entity.AuditDetail;
import co.id.fifgroup.audit.model.entity.AuditMapping;
import co.id.fifgroup.core.audit.Traversable;

/**
 * Default attribute comparator
 * @author kiton
 *
 */
public class DefaultAttributeComparator implements AttributeComparator {
	
	private static final int CURRENT_OBJECT_TYPE = 1;
	private static final int NEW_OBJECT_TYPE = 2;
	private static final String PREFIX_GET= "get";
	private static final String REGEX_PREFIX_GET = "get.*";
	private static final String RETURN_TYPE_COLLECTION = "Collection";
	private static final String RETURN_TYPE_LIST = "List";
	private static final String RETURN_TYPE_DATE = "Date";
	//private static final String ATTRIBUTE_LIST_SEPARATOR = "\\|\\|"; edited by fajar
	private static final String ATTRIBUTE_LIST_SEPARATOR = "\\|";
	private static final String ATTRIBUTE_DELIMITER = "\\|";
	private static final String ATTRIBUTE_LOOKUP_SEPARATOR = "\\*";
	private static final int INDEX_FOR_FORMAT = 2;
	private static final int RESOLVED_CODE = 1;
	private static final int NON_RESOLVED_CODE = 0;

	private List<AttributesValuesComparison> comparisonList;
	private ToTraversableConverter traversableConverter;
	private List<AuditDetail> auditDetailList;
//	private String parentAttributeLabel;
//	private UUID parentId;
//	private String parentList;
	private TempQueue tempQueueForCurrentList;
//	private TempQueue tempQueueForNewList;
	private TempQueue tempQueueForComparison;
	
	private static Logger log =LoggerFactory.getLogger(DefaultAttributeComparator.class);
	
	private SqlSessionFactory sqlSessionFactory;
	private TraversableMap traversableMap;
	
	public DefaultAttributeComparator() {
		log.info("start comparison");
		comparisonList = new ArrayList<AttributesValuesComparison>();	
		auditDetailList = new ArrayList<AuditDetail>();
		tempQueueForCurrentList = new DefaultTempQueue();
//		tempQueueForNewList = new TempQueue();
		tempQueueForComparison = new DefaultTempQueue();
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AtributeComparator#getComparisonList()
	 */
	@Override
	public List<AttributesValuesComparison> getComparisonList() {
		return comparisonList;
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AtributeComparator#getAuditDetailList()
	 */
	@Override
	public List<AuditDetail> getAuditDetailList() {
		
		return auditDetailList;
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AtributeComparator#doAttributeComparison(java.lang.Object, java.lang.Object, int, java.util.UUID, int)
	 */
	@Override
	public void doAttributeComparison(Object oldObject, Object newObject, 
		int controlType, UUID auditId,int moduleId) {
		log.info("comparing objects");
		this.traversableConverter.clearCache();
		try {
 			if (oldObject != null && newObject != null 
					&& controlType == Control.UPDATE.getCode()) {
				
				compareAttributes(oldObject, newObject, controlType, auditId,"", null,moduleId,false, tempQueueForComparison);
				auditDetailList.clear();
				auditDetailList.addAll(tempQueueForComparison);
			}
			else {
				Object reviewedObject = null;
				int sourceObjectType = NEW_OBJECT_TYPE;
				if (controlType == Control.CREATE.getCode()) {
					if (newObject != null) {
						reviewedObject = newObject;
					}
					else if (oldObject != null) {
						reviewedObject = oldObject;
					}
					
				}
				else {
					sourceObjectType = CURRENT_OBJECT_TYPE;
					if (oldObject != null) {
						reviewedObject = oldObject;
					}
					else if (newObject != null){
						reviewedObject = newObject;
					}
				}
				
				if (reviewedObject != null) {
					iterateAttributes(reviewedObject, controlType, auditId, "", null, sourceObjectType,moduleId, tempQueueForCurrentList);	
					auditDetailList.clear();
					auditDetailList.addAll(tempQueueForCurrentList);
					printTempQueue(tempQueueForCurrentList);
				}
	//			else if (newObject == null) {
	//				iterateAttributes(oldObject, controlType, auditId, "", null, CURRENT_OBJECT_TYPE,moduleId, tempQueueForCurrentList);
	//				auditDetailList.clear();
	//				auditDetailList.addAll(tempQueueForCurrentList);
	//				printTempQueue(tempQueueForCurrentList);
	//	
	//			}
				
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()){
				log.debug("Exception caught", e);
			}
		}
	}
	
	private void iterateAttributes(Object sourceObject, int controlType,UUID auditId, String parentAttributeName, 
			UUID parentAttribute, int sourceObjectType,int moduleId, TempQueue tempQueue) {
		log.info("iterating attributes");
		
		Method[] methods = null;
		methods = sourceObject.getClass().getMethods();

		SqlSession session = null;
		for (int i=0;i<methods.length;i++) {
			if (methods[i].getName().matches(REGEX_PREFIX_GET)) {
				if (session == null) {
					session = sqlSessionFactory.openSession();
				}
				String attributeLabel = null;
				try {
					attributeLabel = getAttributeLabel(session, sourceObject, 
						methods[i].getName(), parentAttributeName, controlType,moduleId);
				}
				catch (Exception e) {
					if (log.isDebugEnabled()){
						log.debug(e.getMessage(), e);
					}
					if (session != null) {
						session.close();
						session = null;
					}
				}
				
				if (attributeLabel != null) {
//				parentAttributeLabel = attributeLabel;
					try {
						String returnType = methods[i].getReturnType().getSimpleName();
						Class<?> returnTypeClass = methods[i].getReturnType();
						if (returnType.equals(RETURN_TYPE_COLLECTION) || returnType.equals(RETURN_TYPE_LIST)) {
							
							// to capture the attribute name from given method name
							String derivedAttributeName =  methods[i].getName().substring(PREFIX_GET.length());
							String lowercasedFirstChar = derivedAttributeName.substring(0,1).toLowerCase();
							String attributeName = lowercasedFirstChar + derivedAttributeName.substring(1);
							
//							parentList = attributeName;
							String parentAttributeNameForList = attributeName;
							if (!parentAttributeName.isEmpty()) {
								parentAttributeNameForList = parentAttributeName + "." + parentAttributeNameForList;
							}
							Collection<?> attributeCollections = (Collection<?>) methods[i].invoke(sourceObject);
							List<Object> elementsfromCollections = new ArrayList<Object>();
								if (attributeCollections != null) {
									elementsfromCollections.addAll(attributeCollections);
								}							
							if (isTraversable(elementsfromCollections)) {	
								LinkedList<Object> linkedList = new LinkedList<Object>();	
								linkedList.addAll(elementsfromCollections);								
								UUID attributeId = UUID.randomUUID();
								UUID listId = attributeId;
								String[] splittedListLabel = attributeLabel.split(ATTRIBUTE_LIST_SEPARATOR);
								String listLabel = splittedListLabel[0];
								AuditDetail auditDetailListType = new AuditDetail(auditId, listId, listLabel, 
									"", "", NON_RESOLVED_CODE, parentAttribute);
								auditDetailList.add(auditDetailListType);
								tempQueue.add(auditDetailListType);	
								for (int index = 0; index < elementsfromCollections.size(); index++) {
									try {
										Object element = elementsfromCollections.get(index);
										if (isTraversable(element)) {
											Traversable traversableObject = traversableConverter.toTraversable(elementsfromCollections.get(index));
											attributeId = UUID.randomUUID();
											String elementTypeLabel = splittedListLabel[1].split(ATTRIBUTE_DELIMITER)[0];
											elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
										//	String objectCode = getObjectCode(element);
											String objectCodeForCurrentAttribute = "";
											String objectCodeForNewAttribute = "";
											if (sourceObjectType == CURRENT_OBJECT_TYPE) {
												objectCodeForCurrentAttribute = getObjectCode(element);
											}
											else {
												objectCodeForNewAttribute = getObjectCode(element);
											}
											AuditDetail auditDetailElementType = 
												new AuditDetail(auditId, attributeId, elementTypeLabel,
													objectCodeForCurrentAttribute, objectCodeForNewAttribute, RESOLVED_CODE, listId);
											auditDetailList.add(auditDetailElementType);
											tempQueue.add(auditDetailElementType);
											log.info("source type : " + sourceObjectType + " for " + traversableObject);

											iterateAttributes(element, controlType, auditId, parentAttributeNameForList, attributeId, 
													sourceObjectType, moduleId, tempQueue);
											if (tempQueue.getLast().equals(auditDetailElementType)) {
												tempQueue.pop();
											}
											printTempQueue(tempQueue);

										}
									} catch (Exception e) {
										if (log.isDebugEnabled()){
											log.debug(e.getMessage(), e);
										}
									}		
								}
								if (tempQueue.getLast().equals(auditDetailListType)) {
									tempQueue.pop();
								}
								printTempQueue(tempQueue);
//								parentList = null;
							}	
						}
						else {
							if (attributeLabel != null) {
								String currentValue = "";
								Object previousValue = null;
								Object uptodateValue = null;
								String newValue = "";
								Traversable currentAttribute = null;
								Traversable newAttribute = null;
								
								if (sourceObject != null) {
									Object valuefromSourceObject = (Object)methods[i].invoke(sourceObject);
									currentValue = "";
							
									if (valuefromSourceObject != null) {
										
										log.info("source Object Type : " + sourceObjectType);
										
										if (sourceObjectType == CURRENT_OBJECT_TYPE) {
											
											if(isTraversable(valuefromSourceObject)){
												currentAttribute = traversableConverter.toTraversable( valuefromSourceObject);
											}
											else {
												currentValue = valuefromSourceObject.toString();
												previousValue = valuefromSourceObject;
											}
										}
										else {
											if(isTraversable(valuefromSourceObject)){
												newAttribute = traversableConverter.toTraversable( valuefromSourceObject);
											}
											else {
												newValue = valuefromSourceObject.toString();
												uptodateValue = valuefromSourceObject;
											}
											
										}
									}
									
									UUID attributeId = UUID.randomUUID();
									log.info("current attr : " + currentAttribute + " , new attribute : " + newAttribute);
									if(currentAttribute!= null || newAttribute!=null){
//										parentId = attributeId;
//										parentAttributeLabel = attributeLabel;
										// Set searching criteria to search by parent
										
										String derivedAttributeName =  methods[i].getName().substring(PREFIX_GET.length());
										String lowercasedFirstChar = derivedAttributeName.substring(0,1).toLowerCase();
										String attributeName = lowercasedFirstChar + derivedAttributeName.substring(1);
										
//										parentList = attributeName;
										String elementTypeLabel = attributeLabel.split(ATTRIBUTE_DELIMITER)[0];
										elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
										String objectCodeForCurrentAttribute = "";
										String objectCodeForNewAttribute = "";
										if (currentAttribute != null) {
											objectCodeForCurrentAttribute = getObjectCode(currentAttribute);
										}
										else {
											objectCodeForNewAttribute = getObjectCode(newAttribute);
										}
										AuditDetail elementTypeAuditDetail = new AuditDetail(auditId, attributeId, 
												elementTypeLabel, objectCodeForCurrentAttribute, objectCodeForNewAttribute,
												RESOLVED_CODE, parentAttribute);
										auditDetailList.add(elementTypeAuditDetail);
										tempQueue.add(elementTypeAuditDetail);
									//	parentList = attributeLabel;
										String parentAttributeNameObject = attributeName;
										if (!parentAttributeName.isEmpty()) {
											parentAttributeNameObject = parentAttributeName + "." + parentAttributeNameObject;
										}
										try {
											iterateAttributes(valuefromSourceObject, controlType, auditId, parentAttributeNameObject, 
													attributeId, sourceObjectType, moduleId, tempQueue);
									//		compareAttributes(currentAttribute, newAttribute, controlType, auditId, attributeId, moduleId, true);
										} catch (Exception e) {
											log.error(e.getMessage(), e);
										}
										if (tempQueue.getLast().equals(elementTypeAuditDetail)) {
											tempQueue.pop();
										}
//										parentList=null;	
									}
									else{
										log.info("current val : " + currentValue + " , new val : " + newValue + " " + currentValue.equals(newValue));
										if (!currentValue.equals(newValue)) {
											
											String[] splittedLabel = attributeLabel.split(ATTRIBUTE_DELIMITER);
											int flagResolveCode = Integer.parseInt(splittedLabel[1]);
											String[] splittedLookupLabel = splittedLabel[0].split(ATTRIBUTE_LOOKUP_SEPARATOR);
											String labelFormat = "";
											
											if (splittedLookupLabel.length > 1) {
												String lookupCategory = splittedLookupLabel[1];
												if (!currentValue.isEmpty()) {
													currentValue = getLookupValue(currentValue, lookupCategory);
												}
												if (!newValue.isEmpty()) {
													newValue = getLookupValue(newValue, lookupCategory);
												}
											}
											if (splittedLabel.length > INDEX_FOR_FORMAT) {
												labelFormat = splittedLabel[INDEX_FOR_FORMAT];
												
												if ((returnTypeClass.isPrimitive()
														&& isPrimitiveNumberType(returnTypeClass))
													|| isSubClassOfNumber(returnTypeClass)) {
													NumberFormat numberFormat = new DecimalFormat(labelFormat);
													if (!currentValue.isEmpty()) {
														BigDecimal bigDecimal = new BigDecimal(currentValue);
													//	long parsedCurrentNumber = bigDecimal.longValue();
														currentValue = numberFormat.format(bigDecimal);
													}
													if (!newValue.isEmpty()) {
														BigDecimal bigDecimal = new BigDecimal(newValue);
													//	long parsedNewNumber = bigDecimal.longValue();
														newValue = numberFormat.format(bigDecimal);
													}
												}
												else if(returnType.equalsIgnoreCase(RETURN_TYPE_DATE)){
													SimpleDateFormat dateFormatCurrentValue = new SimpleDateFormat(labelFormat);

													if (!currentValue.equals("")) {
													//	long current = Long.parseLong(currentValue);
														currentValue = dateFormatCurrentValue.format(previousValue);
													}
													if (!newValue.equals("")) {
													//	long newVal = Long.parseLong(newValue);
														newValue = dateFormatCurrentValue.format(uptodateValue);
													}
												}
											}
											String recordedAttributeLabel = splittedLabel[0].split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
											AuditDetail auditDetail = new AuditDetail(auditId, attributeId, recordedAttributeLabel, 
											currentValue, newValue, flagResolveCode, parentAttribute);
											auditDetailList.add(auditDetail);
											tempQueue.add(auditDetail);
										}
									}	
								}
							}
						}
					} catch (IllegalArgumentException e) {
						log.error(e.getMessage(),e);
					} catch (IllegalAccessException e) {
						log.error(e.getMessage(),e);
					} catch (InvocationTargetException e) {
						log.error(e.getMessage(),e);
					}
				
			}
		}
		}
		if (session != null) {
			session.close();
		}
	}


	
	protected void compareAttributes(Object currentObject, Object newObject, int controlType,
			UUID auditId, String parentAttributeName, UUID parentAttribute,int moduleId,boolean isParent, TempQueue tempQueue) throws Exception {
			if (log.isDebugEnabled()){
				log.debug("Entering method Compare Attribute");
			}
		
			Method[] methods = null;
			Object reflectedObject = null;
			
			if (currentObject != null) {
			reflectedObject = currentObject;
			}
			else { 
			reflectedObject = newObject;
			}
			
			methods = reflectedObject.getClass().getMethods();
			SqlSession session = null;
			for (int i=0;i<methods.length;i++) {
			
			if (methods[i].getName().matches(REGEX_PREFIX_GET)) {
			
			if (session == null) {
				session = sqlSessionFactory.openSession();
			}
			String attributeLabel = null;
			
			try{
				attributeLabel = getAttributeLabel(session, 
					reflectedObject, methods[i].getName(), parentAttributeName, controlType,moduleId);
			}
			catch (Exception e) {
				log.error(e.getMessage(), e);
				if (session != null) {
					session.close();
					session = null;
				}
			}
			
			log.info("attribute label "+attributeLabel);
			if (attributeLabel != null) {
			try {
				// If return type of an variable is List or Collection then
				// Do List Comparation
				
				String returnType = methods[i].getReturnType().getSimpleName();
				Class<?> returnTypeClass = methods[i].getReturnType();
				
				if (returnType.equals(RETURN_TYPE_COLLECTION) || returnType.equals(RETURN_TYPE_LIST)) {
			
					String attributeName = getClassAttributeName(methods[i]);
					
//					parentList = attributeName;
					
					Collection<?> firstCollections = null;
					if (currentObject != null) {
						firstCollections = (Collection<?>) methods[i].invoke(currentObject);
					}
					
					Collection<?> secondCollections = null;
					if (newObject != null) {
						secondCollections = (Collection<?>)  methods[i].invoke(newObject);
					}
					List<Object> elementsfromFirstCollections = new ArrayList<Object>();
					List<Object> elementsfromSecondCollections = new ArrayList<Object>();
					if (firstCollections != null) {
						elementsfromFirstCollections.addAll(firstCollections);
					}
					if (secondCollections != null) {
						elementsfromSecondCollections.addAll(secondCollections);
					}
					
					boolean traversabilityCheck = isTraversable(elementsfromFirstCollections)
												&& isTraversable(elementsfromSecondCollections);
					
					if (traversabilityCheck) {
						
						UUID attributeId = UUID.randomUUID();
						LinkedList<Object> linkedListFirstList = new LinkedList<Object>();
						LinkedList<Object> linkedListSecondList = new LinkedList<Object>();
						
						linkedListFirstList.addAll(elementsfromFirstCollections);
						linkedListSecondList.addAll(elementsfromSecondCollections);
						
//						boolean originalOrderIsAltered = false;
						
//						parentAttributeLabel = attributeLabel;
//						parentId = attributeId;
						String parentAttributeNameForList = attributeName;
						if (!parentAttributeName.isEmpty()) {
							parentAttributeNameForList = parentAttributeName + "." + parentAttributeNameForList;
						}
						UUID listId = attributeId;
						
						String[] splittedListLabel = attributeLabel.split(ATTRIBUTE_LIST_SEPARATOR);
						String listLabel = splittedListLabel[0];
						AuditDetail auditDetailListType = new AuditDetail(auditId, listId, listLabel, "", "", 
							NON_RESOLVED_CODE, parentAttribute);
						auditDetailList.add(auditDetailListType);
						tempQueue.add(auditDetailListType);
						int indexforFirstList = 0;
						while (indexforFirstList < linkedListFirstList.size()) {
							Traversable traversableElementFromFirstList = traversableConverter.toTraversable( linkedListFirstList.get(indexforFirstList));
							boolean mapped = false;
							int indexforSecondList = 0;
							int linkedListSecondSize = linkedListSecondList.size();
							while ((!mapped) && indexforSecondList < linkedListSecondSize 
										&& linkedListFirstList.size() > 0) {
								Traversable traversableElementFromSecondList = 
									traversableConverter.toTraversable( linkedListSecondList.get(indexforSecondList));
								
								if ((traversableElementFromFirstList.getId() != null && traversableElementFromSecondList != null) 
										&& traversableElementFromFirstList.getId()
										.equals(traversableElementFromSecondList.getId())) {
									attributeId = UUID.randomUUID();
									String elementTypeLabel = splittedListLabel[1].split(ATTRIBUTE_DELIMITER)[0];
									elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
									String objectCode = getObjectCode(traversableElementFromFirstList);
									AuditDetail auditDetailElementType = 
										new AuditDetail(auditId, attributeId, elementTypeLabel, objectCode, objectCode, 
											RESOLVED_CODE, listId);
									auditDetailList.add(auditDetailElementType);
									
									tempQueue.add(auditDetailElementType);
									compareAttributes(traversableElementFromFirstList, traversableElementFromSecondList, 
											controlType, auditId, parentAttributeNameForList, attributeId,moduleId,true, tempQueue);
									
									if (tempQueue.getLast().equals(auditDetailElementType)) {
										tempQueue.pop();
									}
									linkedListFirstList.remove(traversableElementFromFirstList);
									linkedListSecondList.remove(traversableElementFromSecondList);
									mapped = true;
									
//									if (indexforFirstList != indexforSecondList) {
//										originalOrderIsAltered = true;
//									}
								}
								indexforSecondList++;
							}
							if (mapped) {
								indexforFirstList = 0;
							}
							else {
								indexforFirstList++;
							}
						}
						
						
						if (!linkedListFirstList.isEmpty()) {
							int size = linkedListFirstList.size();
							for (int j = 0; j < size; j++) {
//								compareAttributes(linkedListFirstList.get(j), null, controlType, auditId, parentAttributeNameForList, 
//										attributeId,moduleId,false);
								
								attributeId = UUID.randomUUID();
								String elementTypeLabel = splittedListLabel[1].split(ATTRIBUTE_DELIMITER)[0];
								elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
								String objectCode = getObjectCode(linkedListFirstList.get(j));
								AuditDetail auditDetailElementType = 
									new AuditDetail(auditId, attributeId, elementTypeLabel, objectCode, "", 
										RESOLVED_CODE, listId);
								auditDetailList.add(auditDetailElementType);
								tempQueue.add(auditDetailElementType);
								iterateAttributes(linkedListFirstList.get(j), controlType, auditId, parentAttributeNameForList,
										attributeId, CURRENT_OBJECT_TYPE, moduleId, tempQueue);
								if (tempQueue.getLast().equals(auditDetailElementType)) {
									tempQueue.pop();
								}
							}
							linkedListFirstList.clear();
//							originalOrderIsAltered = true;
						}
						
						if (!linkedListSecondList.isEmpty()) {
							int size = linkedListSecondList.size();
							for (int j = 0; j < size; j++) {
//								compareAttributes(linkedListFirstList.get(j), null, controlType, auditId, parentAttributeNameForList, 
//										attributeId,moduleId,false);
								attributeId = UUID.randomUUID();
								String elementTypeLabel = splittedListLabel[1].split(ATTRIBUTE_DELIMITER)[0];
								elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
								String objectCode = getObjectCode(linkedListSecondList.get(j));
								AuditDetail auditDetailElementType = 
									new AuditDetail(auditId, attributeId, elementTypeLabel, "", objectCode, 
										RESOLVED_CODE, listId);
								auditDetailList.add(auditDetailElementType);
								tempQueue.add(auditDetailElementType);
								iterateAttributes(linkedListSecondList.get(j), controlType, auditId, parentAttributeNameForList,
										attributeId, NEW_OBJECT_TYPE, moduleId, tempQueue);
								if (tempQueue.getLast().equals(auditDetailElementType)) {
									tempQueue.pop();
								}
							}
							linkedListSecondList.clear();
//							originalOrderIsAltered = true;
						}
						
//						if (originalOrderIsAltered) {
//							if (parentAttributeLabel!=null){
//							parentId = UUID.randomUUID();
//							String levelDepth[] = parentAttributeLabel.split(ATTRIBUTE_LIST_SEPARATOR);
//							UUID parent = null;
//							for(int count=0;count<levelDepth.length;count++){
//								String[] splittedLabel = levelDepth[count].split(ATTRIBUTE_DELIMITER);
//								AuditDetail auditDetail = new AuditDetail(auditId, parentId, splittedLabel[0], 
//										"", "", Integer.parseInt(splittedLabel[1]), parent);
//								auditDetailList.add(auditDetail);
//								parent = parentId;
//								parentId = UUID.randomUUID();
//								attributeId = parent;
//							}
//							isParent = false;
//							parentAttribute = attributeId;
//							
//							
//							int size = linkedListSecondList.size();
//							for (int j = 0; j < size; j++) {
//								compareAttributes(null, linkedListSecondList.get(j), controlType, auditId, parentAttributeNameForList,
//										attributeId,moduleId,false);
//							}
//							linkedListSecondList.clear();
//			
//			
//							originalOrderIsAltered = false;
//						}
//							else {
//								throw new Exception("No Mapping Found.");
//							}
//						}
						if (tempQueue.getLast().equals(auditDetailListType)) {
							tempQueue.pop();
						}
					}
//					parentList = null;
					
				}
				else {
					if (attributeLabel != null) {
						Object previousValue = null;
						Object uptodateValue = null;
						String currentValue = "";
						String newValue = "";
						Traversable currentAttibute = null;
						Traversable newAttribute = null;
						
						if (currentObject != null) {
							Object valuefromCurrentObject = (Object)methods[i].invoke(currentObject);
							currentValue = "";
							if (valuefromCurrentObject != null && isTraversable(valuefromCurrentObject)) {
								currentAttibute = traversableConverter.toTraversable( valuefromCurrentObject);
							}
							else if (valuefromCurrentObject != null){
								currentValue = valuefromCurrentObject.toString();
								previousValue = valuefromCurrentObject;
							}
						}
						if (newObject != null) {
							Object valuefromNewObject = (Object)methods[i].invoke(newObject);
							newValue = "";
							if (valuefromNewObject != null && isTraversable(valuefromNewObject)) {
								newAttribute = traversableConverter.toTraversable( valuefromNewObject);
							}
							else if(valuefromNewObject != null){
								newValue = valuefromNewObject.toString();
								uptodateValue = valuefromNewObject;
							}
						}
						
						UUID attributeId = UUID.randomUUID();
						
						// If return type is Object then do
						// Object Comparation
						if(currentAttibute!= null && newAttribute!=null){
//							parentId = attributeId;
//							parentAttributeLabel = attributeLabel;
//							// add parent to label search criteria
//							parentList = attributeLabel;
							String derivedAttributeName =  methods[i].getName().substring(PREFIX_GET.length());
							String lowercasedFirstChar = derivedAttributeName.substring(0,1).toLowerCase();
							String attributeName = lowercasedFirstChar + derivedAttributeName.substring(1);
							String parentAttributeNameForObject = attributeName;
							if (!parentAttributeName.isEmpty()) {
								parentAttributeNameForObject = parentAttributeName + "." + parentAttributeNameForObject;
							}
							String elementTypeLabel = attributeLabel.split(ATTRIBUTE_DELIMITER)[0];
							elementTypeLabel = elementTypeLabel.split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];
							String objectCode = getObjectCode(currentAttibute);
							AuditDetail elementTypeAuditDetail = new AuditDetail(auditId, attributeId, 
									elementTypeLabel, objectCode, objectCode, RESOLVED_CODE, parentAttribute);
							auditDetailList.add(elementTypeAuditDetail);
							tempQueue.add(elementTypeAuditDetail);
							compareAttributes(currentAttibute, newAttribute, 
									controlType, auditId, parentAttributeNameForObject, attributeId,moduleId,true, tempQueue);
							if (tempQueue.getLast().equals(elementTypeAuditDetail)) {
								tempQueue.pop();
							}
//							parentList = null;
						}
			
//						if (previousValue == null) {
//							
//							if (uptodateValue != null) {
//								newValue = formatValueDisplay(attributeLabel, uptodateValue, returnTypeClass);
//								int flagResolveCode = getFlagResolveCode(attributeLabel);
//								recordValueChange(auditId, attributeId, attributeLabel, currentValue, 
//									newValue, flagResolveCode, parentAttribute, tempQueue);
//							}
//						}
//						else if (uptodateValue == null) {
//							
//							if (previousValue != null) {
//								currentValue = formatValueDisplay(attributeLabel, uptodateValue, returnTypeClass);
//								int flagResolveCode = getFlagResolveCode(attributeLabel);
//								recordValueChange(auditId, attributeId, attributeLabel, currentValue, 
//									newValue, flagResolveCode, parentAttribute, tempQueue);
//							}
//						}
//						else {
//							if (!previousValue.equals(uptodateValue)) {
//								currentValue = formatValueDisplay(attributeLabel, uptodateValue, returnTypeClass);
//								newValue = formatValueDisplay(attributeLabel, uptodateValue, returnTypeClass);
//								int flagResolveCode = getFlagResolveCode(attributeLabel);
//								recordValueChange(auditId, attributeId, attributeLabel, currentValue, 
//									newValue, flagResolveCode, parentAttribute, tempQueue);
//							}
//						}
						
						
						if (!currentValue.equals(newValue)) {
							String[] splittedLabel = attributeLabel.split(ATTRIBUTE_DELIMITER);
							int flagResolveCode = Integer.parseInt(splittedLabel[1]);
							String[] splittedLookupLabel = splittedLabel[0].split(ATTRIBUTE_LOOKUP_SEPARATOR);
							String labelFormat = "";
							if (splittedLookupLabel.length > 1) {
								String lookupCategory = splittedLookupLabel[1];
								if (!currentValue.isEmpty()) {
									currentValue = getLookupValue(currentValue, lookupCategory);
								}
								if (!newValue.isEmpty()) {
									newValue = getLookupValue(newValue, lookupCategory);
								}
							}
							if (splittedLabel.length > INDEX_FOR_FORMAT) {
								labelFormat = splittedLabel[INDEX_FOR_FORMAT];
								
								if ((returnTypeClass.isPrimitive()
										&& isPrimitiveNumberType(returnTypeClass))
									|| isSubClassOfNumber(returnTypeClass)) {
									NumberFormat numberFormat = new DecimalFormat(labelFormat);
									if (!currentValue.isEmpty()) {
										BigDecimal bigDecimal = new BigDecimal(currentValue);
									//	byte parsedCurrentNumber = bigDecimal.byteValue();
										currentValue = numberFormat.format(bigDecimal);
									}
									if (!newValue.isEmpty()) {
										BigDecimal bigDecimal = new BigDecimal(newValue);
									//	byte parsedNewNumber = bigDecimal.byteValue();
										newValue = numberFormat.format(bigDecimal);
									}
								}
								else if(returnType.equalsIgnoreCase(RETURN_TYPE_DATE)){
									SimpleDateFormat dateFormatCurrentValue = new SimpleDateFormat(labelFormat);
			
									if (!currentValue.equals("")) {
									//	long current = Long.parseLong(currentValue);
										currentValue = dateFormatCurrentValue.format(previousValue);
									}
									if (!newValue.equals("")) {
									//	long newVal = Long.parseLong(newValue);
										newValue = dateFormatCurrentValue.format(uptodateValue);
									}
								}
							}
			
							String recordedAttributeLabel = splittedLabel[0].split(ATTRIBUTE_LOOKUP_SEPARATOR)[0];

							AuditDetail auditDetail = new AuditDetail(auditId, attributeId, recordedAttributeLabel, 
																currentValue, newValue, flagResolveCode, parentAttribute);
							auditDetailList.add(auditDetail);
							tempQueue.add(auditDetail);
						}
					}
				}
			} catch (IllegalArgumentException e) {
				log.error(e.getMessage(),e);
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(),e);
			} catch (InvocationTargetException e) {
				log.error(e.getMessage(),e);
			}
			  catch (Exception e) {
				log.error(e.getMessage(), e);  
			  }
			}
		}
		}
		if (session != null) {
			session.close();	
		}
	}
	
//	private int getFlagResolveCode(String attributeLabel) {
//		String[] splittedLabel = attributeLabel.split(ATTRIBUTE_DELIMITER);
//		int flagResolveCode = Integer.parseInt(splittedLabel[1]);
//		return flagResolveCode;
//	}
//	
//	private void recordValueChange(UUID auditId, UUID attributeId, String attributeLabel, 
//			String currentValue, String newValue, int flagResolveCode, 
//			UUID parentAttribute, TempQueue tempQueue) {
//		AuditDetail auditDetail = new AuditDetail(auditId, attributeId, attributeLabel, 
//				currentValue, newValue, flagResolveCode, parentAttribute);
//		auditDetailList.add(auditDetail);
//		tempQueue.add(auditDetail);
//	}
//	
//	private String formatValueDisplay(String attributeLabel, Object value, 
//			Class<?> methodReturnType) {
//		
//		String formattedValue = "";
//		String[] splittedLabel = attributeLabel.split(ATTRIBUTE_DELIMITER);
//		int flagResolveCode = Integer.parseInt(splittedLabel[1]);
//		String[] splittedLookupLabel = splittedLabel[0].split(ATTRIBUTE_LOOKUP_SEPARATOR);
//		String labelFormat = "";
//		if (splittedLookupLabel.length > 1) {
//			String lookupCategory = splittedLookupLabel[1];
//			if (value != null) {
//				formattedValue = getLookupValue(value.toString(), lookupCategory);
//			}
//			
//		}
//		if (splittedLabel.length > INDEX_FOR_FORMAT) {
//			labelFormat = splittedLabel[INDEX_FOR_FORMAT];
//			
//			if ((methodReturnType.isPrimitive()
//					&& isPrimitiveNumberType(methodReturnType))
//				|| isSubClassOfNumber(methodReturnType)) {
//				NumberFormat numberFormat = new DecimalFormat(labelFormat);
//				if (value != null) {
//					BigDecimal bigDecimal = new BigDecimal(value.toString());
//				//	byte parsedCurrentNumber = bigDecimal.byteValue();
//					formattedValue = numberFormat.format(bigDecimal);
//				}
//			}
//			else if(methodReturnType.getSimpleName().equalsIgnoreCase(RETURN_TYPE_DATE)){
//				SimpleDateFormat dateFormatCurrentValue = new SimpleDateFormat(labelFormat);
//
//				if (value != null) {
//				//	long current = Long.parseLong(currentValue);
//					formattedValue = dateFormatCurrentValue.format(value);
//				}
//				
//			}
//		}
//		return formattedValue;
//	}
		
	private boolean isTraversable(List<Object> collection) {
		log.info("Entering method isTraversable..");
		
		boolean isTraversable = false;
		Object element = null;
		int firstIndex = 0;
		
		if (collection.size() > 0) {
			element = collection.get(firstIndex);
			
			if (element != null) {
				if (isTraversable(element)) {
					isTraversable = true;
				}
			}
		}
		
		// just accept it if no element listed, it will be handled later on
		else {
			isTraversable = true;
		}
		
		if (log.isDebugEnabled()){
			log.debug("List "+collection.getClass()+" is Traversable= "+isTraversable);
		}
		
		return isTraversable;
	}
	
	private String getAttributeLabel(SqlSession session, Object currentObject, 
			String methodName, String parentAttributeName, int controlType,int moduleId)
		throws Exception{
		
		String className = ClassNameCleaner.cleanupClassName(currentObject.getClass().getName());

		String attributeLabel = null;
		
		if (className != null && !className.isEmpty()) {
			String derivedAttributeName = methodName.substring(PREFIX_GET.length());
			if (derivedAttributeName != null && !derivedAttributeName.isEmpty()) {
				String lowercasedFirstChar = derivedAttributeName.substring(0,1).toLowerCase();
				String attributeName = lowercasedFirstChar + derivedAttributeName.substring(1);
				
//				if(parentList!=null && !parentList.equals("")){
//					log.info("attribute name separated");
//					log.debug("parent list : " + parentList);
//					attributeName = parentList+ATTRIBUTE_NAME_SEPARATOR+attributeName;
//				}

				if (log.isDebugEnabled()){
					log.debug("attributeName : " + attributeName);
				}

				AuditMapping auditMapping = new AuditMapping();
				if (!parentAttributeName.isEmpty()) {
					attributeName = parentAttributeName + "." + attributeName;
				}
				auditMapping.setAttributeName(attributeName);
				auditMapping.setClassName(className);
				auditMapping.setControlType(controlType);
				auditMapping.setModuleId(moduleId);
				auditMapping.setFlagRecordCode(BooleanStatus.NO.getCode());
						
				attributeLabel = (String) session.selectOne("Audit_AuditMapping.getAttributeLabel", auditMapping);

			}
		}
							
		return attributeLabel;
		
	}
	
	private String getObjectCode(Object examinedObject) {
		String result = "";
		try {
			if (examinedObject != null) {
				Class<?> examinedClass = examinedObject.getClass();
				Method method = examinedClass.getMethod("getId");
				if (method != null) {
					result = ((Object)method.invoke(examinedObject)).toString();
				}
			}
		}
		catch (Exception e) {
			if (log.isDebugEnabled()){
				log.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see jatis.audit.AtributeComparator#printComparisonList()
	 */
	public void printComparisonList() {
		Iterator<AuditDetail> iterator = auditDetailList.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			System.out.println();
		}
	}
	protected void printTempQueue(TempQueue tempQueue) {
		Iterator<AuditDetail> iterator = tempQueue.iterator();
		System.out.println("*********************");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			System.out.println();
		}
	}
	
	/**
	 * get Variable name from given method
	 */
	private String getClassAttributeName(Method method){
		String derivedAttributeName =  method.getName().substring(PREFIX_GET.length());
		String lowercasedFirstChar = derivedAttributeName.substring(0,1).toLowerCase();
		String attributeName = lowercasedFirstChar + derivedAttributeName.substring(1);
		return attributeName;
	}
	
	private boolean isPrimitiveNumberType(Class<?> aClass) {
		return (aClass.getName().equals("double") 
				|| aClass.getName().equals("int")
				|| aClass.getName().equals("long")
				|| aClass.getName().equals("short")
				|| aClass.getName().equals("float")
				|| aClass.getName().equals("byte"));
	}
	private boolean isSubClassOfNumber(Class<?> aClass) {
		Class<?> superClass = aClass.getSuperclass();
		if (superClass.equals(java.lang.Object.class)) {
			return false;
		}
		else if(superClass.equals(java.lang.Number.class)) {
			return true;
		}
		else {
			return isSubClassOfNumber(aClass);
		}
	}
	
	private String getLookupValue(String value, String lookupCategory) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("lookupCategory", lookupCategory);
		parameterMap.put("lookupCode", value);
		SqlSession session = null;
		String lookupValue = "";
		try {
			session = sqlSessionFactory.openSession();
			lookupValue = (String) session.selectOne("Audit_AuditMapping.getResolvedValue", parameterMap);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		finally {
			if (session != null) {
				session.close();
			}
		}	
		return lookupValue;
	}
	
	private boolean isTraversable(Object o){
		if (o == null){
			return false;
		}
		return o instanceof Traversable || this.traversableMap.isAuditable(o);
	}

	@Override
	public void setToTraversableConverter(
			ToTraversableConverter traversableConverter) {
		this.traversableConverter = traversableConverter;
	}

	@Override
	public void setTraversableMap(TraversableMap traversableMap) {
		this.traversableMap = traversableMap;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
