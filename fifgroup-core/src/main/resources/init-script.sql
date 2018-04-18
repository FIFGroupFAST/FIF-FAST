-- lookup communication type
INSERT INTO BSE_LOOKUP_HDR(LOOKUP_ID, NAME, DESCRIPTION, LOOKUP_TYPE, GROUP_ID, PARENT_ID, DATA_TYPE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE)
VALUES(BSE_LOOKUP_HDR_S.NEXTVAL, 'MST_COMMUNICATION_TYPE', 'List Of Communication Type', 'DEPENDENT', '1', NULL, 'TEXT', -1, SYSDATE, -1, SYSDATE);

INSERT INTO BSE_LOOKUP_DEPENDENTS(LOOKUP_ID, DETAIL_CODE, MEANING, DESCRIPTION, COMPANY_SCOPE, PARENT_DETAIL_CODE, PARENT_HDR_ID, DATE_FROM, DATE_TO, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE)
VALUES (BSE_LOOKUP_HDR_S.CURRVAL, 'FAX', 'Faximile', 'Faximile', -1, NULL, NULL, TRUNC(SYSDATE), TRUNC(TO_DATE('4712/12/31', 'yyyy/mm/dd')), -1, SYSDATE, -1, SYSDATE);
INSERT INTO BSE_LOOKUP_DEPENDENTS(LOOKUP_ID, DETAIL_CODE, MEANING, DESCRIPTION, COMPANY_SCOPE, PARENT_DETAIL_CODE, PARENT_HDR_ID, DATE_FROM, DATE_TO, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE)
VALUES (BSE_LOOKUP_HDR_S.CURRVAL, 'MAIL', 'Email', 'Email', -1, NULL, NULL, TRUNC(SYSDATE), TRUNC(TO_DATE('4712/12/31', 'yyyy/mm/dd')), -1, SYSDATE, -1, SYSDATE);
INSERT INTO BSE_LOOKUP_DEPENDENTS(LOOKUP_ID, DETAIL_CODE, MEANING, DESCRIPTION, COMPANY_SCOPE, PARENT_DETAIL_CODE, PARENT_HDR_ID, DATE_FROM, DATE_TO, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE)
VALUES (BSE_LOOKUP_HDR_S.CURRVAL, 'PHONE', 'Phone', 'Phone', -1, NULL, NULL, TRUNC(SYSDATE), TRUNC(TO_DATE('4712/12/31', 'yyyy/mm/dd')), -1, SYSDATE, -1, SYSDATE);

-- end lookup communication type