CREATE OR REPLACE PACKAGE FIF_SYNC_TO_GASYS AS 

  PROCEDURE SYNC_MAIN(P_MSG OUT VARCHAR2);
  
  PROCEDURE CREATE_ASSIGNMENT(P_ASSIGNMENT_ID IN NUMBER);
  
  PROCEDURE CREATE_PEOPLE(P_HCMS_PERSON_ID IN NUMBER, P_GA_PERSON_ID OUT NUMBER);
  
  PROCEDURE CREATE_JOB(P_HCMS_JOB_ID IN NUMBER, P_IS_CWK BOOLEAN, P_GA_JOB_ID OUT NUMBER);
  
  PROCEDURE CREATE_LOCATION(P_HCMS_LOCATION_ID IN NUMBER, P_GA_LOCATION_ID OUT NUMBER);
  
  PROCEDURE CREATE_POSITION(P_HCMS_JOB_ID IN NUMBER, P_HCMS_LOCATION_ID IN NUMBER, P_GA_JOB_ID IN NUMBER, P_GA_LOCATION_ID IN NUMBER, P_GA_POSITION_ID OUT NUMBER);
  
  PROCEDURE NEW_HIRE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE);
  
  PROCEDURE TERMINATE_EMPLOYEE(P_HCMS_PERSON_ID IN NUMBER, P_TERMINATION_DATE IN DATE);
  
  PROCEDURE TRANSFER_EMPLOYEE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE);
  
  PROCEDURE CREATE_POSITION_HIERARCHY(P_MSG_OUT OUT VARCHAR2);

  PROCEDURE CREATE_POS_HIER_BRANCH(P_HCMS_HIER_NAME IN VARCHAR2);
  
  PROCEDURE CREATE_POS_HIER_HO(P_HCMS_HIER_NAME IN VARCHAR2);
  
  PROCEDURE CREATE_NEW_POS_VERSION(P_POS_HIER_NAME IN VARCHAR2);
  
  FUNCTION IS_NUMBER(P_PARAM IN VARCHAR2) RETURN NUMBER;
  
  --FUNCTION MEANING_RESOLVER(P_CODE IN VARCHAR2, P_MAX_LENGTH IN NUMBER, P_SOURCE IN VARCHAR2) RETURN VARCHAR2;
  
  FUNCTION GET_POSITION(P_HCMS_LOCATION_ID IN NUMBER, P_HCMS_JOB_ID IN NUMBER) RETURN NUMBER;
  
  FUNCTION GET_CODE_COMBINATION(P_GA_PERSON_ID IN NUMBER, P_HCMS_ORG_ID IN NUMBER, P_HCMS_COMPANY_ID IN NUMBER) RETURN NUMBER;
  
  FUNCTION PROCESS_TRANSFER(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2;
  
  FUNCTION PROCESS_HIRE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2;
  
  FUNCTION PROCESS_TERMINATION(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2;
  
  FUNCTION PROCESS_POS_HIER_BRANCH(P_HCMS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2;
  
  FUNCTION PROCESS_POS_HIER_HO(P_HCMS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2;
  
  FUNCTION PROCESS_NEW_POS_VERSION(P_POS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2;

  
END FIF_SYNC_TO_GASYS;
/


CREATE OR REPLACE PACKAGE BODY FIF_SYNC_TO_GASYS 
AS
  
  V_ERROR_MSG VARCHAR2(4000);
  EXCEPT_OTHER EXCEPTION;
  
  PROCEDURE SYNC_MAIN(P_MSG OUT VARCHAR2) 
  IS
    CURSOR CUR_POOL IS
    SELECT 
      SYN_ID,
      EXECUTION_TYPE,
      SCHEMA,
      TABLE_NAME,
      TRIGGER_TIME,
      PK_COLUMN,
      PK_VALUE,
      AFFECTED_COLUMN,
      EXECUTION_TIME,
      IS_SUCCEED,
      ERROR_CODE,
      ERROR_MESSAGE,
      NEW_VALUE,
      OLD_VALUE
    FROM SYN_POOL 
    where not exists (select 1 from syn_pool_dtl spd 
                      where spd.syn_id = syn_pool.syn_id 
                      and spd.app = 'GASYS' 
                      and spd.phase = 'COMPLETED'
                      and spd.status = 'NORMAL');
    
    V_SYN_DTL_ID NUMBER;
    V_ACTION_TYPE VARCHAR2(20);
    V_PERSON_ID NUMBER;
    V_EFFECTIVE_START_DATE DATE;
    V_REF_ID NUMBER;
    V_TERMINATION_REASON VARCHAR2(255);
    V_SOURCE VARCHAR2(50);
    
    V_IS_HAS_ERROR BOOLEAN := FALSE;
    
  BEGIN
    
    FOR CUR_P IN CUR_POOL
    LOOP
      
      V_ERROR_MSG := NULL;
      
      select syn_pool_dtl_s.nextval into v_syn_dtl_id from dual;
      
      INSERT INTO syn_pool_dtl(
        SYN_DTL_ID, SYN_ID, EXECUTION_TIME, APP, PHASE, STATUS, MESSAGE
      ) values (
        v_syn_dtl_id, cur_p.syn_id, sysdate, 'GASYS', 'RUNNING', 'NORMAL', null
      );
      
      COMMIT;
      
      BEGIN
        
        CASE
          WHEN UPPER(CUR_P.table_name) = 'PEA_PRIMARY_ASSIGNMENTS' THEN 
            BEGIN
              
              select 
                ACTION_TYPE, PERSON_ID, EFFECTIVE_START_DATE, REF_ID, SOURCE 
              INTO V_ACTION_TYPE, V_PERSON_ID, V_EFFECTIVE_START_DATE, V_REF_ID, V_SOURCE 
              from pea_primary_assignments where assignment_id = cur_p.pk_value;
              
              CASE 
                WHEN V_ACTION_TYPE = 'HIRE' THEN
                  IF V_SOURCE = 'TERMINATION' THEN
                    TRANSFER_EMPLOYEE(V_PERSON_ID, V_EFFECTIVE_START_DATE); --Transfer Within Group
                  ELSE
                    NEW_HIRE(V_PERSON_ID, V_EFFECTIVE_START_DATE);
                  END IF;
                WHEN V_ACTION_TYPE = 'TERMINATION' THEN
                  
                  BEGIN
                    select termination_reason
                    INTO V_TERMINATION_REASON
                    from ter_requests tr
                    join ter_types tt on tt.type_id = tr.termination_type_id
                    where tr.request_id = V_REF_ID;
                  EXCEPTION
                    WHEN NO_DATA_FOUND THEN
                      V_ERROR_MSG := 'ERROR WHEN GET TERMINATION REQUEST DATA BY REF_ID '||V_REF_ID;
                      RAISE EXCEPT_OTHER;
                  END;
                  
                  IF V_TERMINATION_REASON <> 'TRANSFER OUT WITHIN GROUP' THEN
                    TERMINATE_EMPLOYEE(V_PERSON_ID, V_EFFECTIVE_START_DATE);
                  END IF;
                  
                WHEN V_ACTION_TYPE IN ('TRANSFER','DEMOTION','PROMOTION') THEN
                  TRANSFER_EMPLOYEE(V_PERSON_ID, V_EFFECTIVE_START_DATE);
              END CASE;
              
            END;
         
        END CASE;
        
        update syn_pool_dtl
        set phase = 'COMPLETED', status = 'NORMAL'
        where syn_dtl_id = v_syn_dtl_id;
        
        COMMIT;
      EXCEPTION
        WHEN OTHERS THEN
          BEGIN
            ROLLBACK;
            update syn_pool_dtl
            set phase = 'COMPLETED', status = 'ERROR', MESSAGE = V_ERROR_MSG
            where syn_dtl_id = v_syn_dtl_id;
             V_IS_HAS_ERROR := TRUE;
            COMMIT;
          END;
      
      END;      
    END LOOP;
    
    IF V_IS_HAS_ERROR THEN
      P_MSG := 'Process Finished with Error, Please check GASYS error log from syn_pool_dtl';
    ELSE
      P_MSG := 'Process Finished with No Error';
    END IF;
    
  END SYNC_MAIN;
                               
  PROCEDURE CREATE_ASSIGNMENT(P_ASSIGNMENT_ID IN NUMBER)
  IS
    V_HCMS_PERSON_ID pea_primary_assignments.PERSON_ID%TYPE;
    V_HCMS_NPK pea_personal_informations.EMPLOYEE_NUMBER%TYPE;
    V_HCMS_JOB_CODE wos_job_infos.info_value%type;
    V_HCMS_JOB_NAME WOS_JOBS.JOB_NAME%TYPE;
    V_HCMS_LOCATION_CODE wos_locations.location_code%TYPE;
    V_HCMS_LOCATION_NAME wos_locations.location_NAME%TYPE;
    V_HCMS_COMPANY_NAME bse_companies.company_name%TYPE;
    V_HCMS_JOB_ID NUMBER;
    V_HCMS_LOCATION_ID NUMBER;
    V_GA_PERSON_ID NUMBER;
    V_GA_JOB_ID NUMBER;
    V_GA_JOB_NAME VARCHAR2(100);
    V_GA_LOCATION_ID NUMBER;
    V_GA_LOCATION_CODE VARCHAR2(100);
    V_GA_ORGANIZATION_ID NUMBER;
    V_GA_POSITION_ID NUMBER;
    V_GA_ASSIGNMENT_ID NUMBER;
    V_HCMS_ORG_ID NUMBER;
    V_HCMS_COMPANY_ID NUMBER;
    
    V_DATE_FROM DATE;
    V_DATE_TO DATE;
    V_BUSINESS_GROUP_ID NUMBER;
    V_ASSIGNMENT_STATUS_TYPE_ID NUMBER;
    
    V_PERSON_CNT NUMBER;
    V_CODE_COMBINATION_ID NUMBER;
  BEGIN
    
    BEGIN
      SELECT 
        ppa.person_id hcms_person_id,
        ppi.employee_number hcms_npk,
        WJ.JOB_ID HCMS_JOB_ID,
        wji.info_value hcms_job_code,
        wj.job_name hcms_job_name,
        WL.LOCATION_ID HCMS_LOCATION_ID,
        wl.location_code hcms_location_code,
        wl.location_name hcms_location_name,
        bc.company_name hcms_company_name,
        pap.person_id ga_person_id,
        haou.organization_id ga_organization_id,
        PPA.EFFECTIVE_START_DATE,
        PPA.EFFECTIVE_END_DATE,
        PPA.ORGANIZATION_ID,
        PPA.COMPANY_ID
      INTO
        V_HCMS_PERSON_ID,
        V_HCMS_NPK,
        V_HCMS_JOB_ID,
        V_HCMS_JOB_CODE,
        V_HCMS_JOB_NAME,
        V_HCMS_LOCATION_ID,
        V_HCMS_LOCATION_CODE,
        V_HCMS_LOCATION_NAME,
        V_HCMS_COMPANY_NAME,
        V_GA_PERSON_ID,
        V_GA_ORGANIZATION_ID,
        V_DATE_FROM,
        V_DATE_TO,
        V_HCMS_ORG_ID,
        V_HCMS_COMPANY_ID
      FROM pea_primary_assignments PPA
      JOIN pea_personal_informations ppi on ppa.person_id = ppi.person_id and ppa.company_id = ppi.company_id and ppa.effective_start_date between ppi.effective_start_date and ppi.effective_end_date
      join wos_jobs wj on wj.job_id = ppa.job_id
      join wos_job_versions wjv on wjv.job_id = wj.job_id and ppa.effective_start_date between wjv.date_from and wjv.date_to
      join wos_organization_versions wov on wov.organization_id = ppa.organization_id and ppa.effective_start_date between wov.date_from and wov.date_to
      join wos_locations wl on wl.location_id = wov.location_id
      join bse_companies bc on bc.company_id = ppa.company_id and ppa.effective_start_date between bc.effective_start_date and bc.effective_end_date
      left join wos_job_infos wji on wji.version_id = wjv.version_id and wji.info_id = 6
      left join hr.per_all_people_f@gl.us.oracle.com pap on pap.employee_number = ppi.employee_number and ppa.effective_start_date between pap.effective_start_date and pap.effective_end_date
      left join hr.hr_all_organization_units@gl.us.oracle.com haou on haou.name = bc.company_name
      where ppa.assignment_id = P_ASSIGNMENT_ID;
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN CREATE ASSIGNMENT,FAILED TO QUERY DATA FROM ASSIGNMENT HCMS, ASSIGNMENT_ID '||P_ASSIGNMENT_ID;
        RAISE EXCEPT_OTHER;
    
    END;
    
    IF V_GA_ORGANIZATION_ID IS NULL THEN
      V_ERROR_MSG := 'ERROR WHEN CREATE ASSIGNMENT.COULD NOT FIND ORGANIZATION_ID FROM GA SYS, ORGANIZATION_NAME '||V_HCMS_COMPANY_NAME;
      RAISE EXCEPT_OTHER;
    END IF;
    
    IF V_GA_PERSON_ID IS NULL THEN
      CREATE_PEOPLE(V_HCMS_PERSON_ID, V_GA_PERSON_ID);
    END IF;
    
    SELECT COUNT(1) INTO V_PERSON_CNT FROM PEA_PEOPLE WHERE PERSON_ID = V_HCMS_PERSON_ID;
    
    IF V_PERSON_CNT > 0 THEN
      CREATE_JOB(V_HCMS_JOB_ID, FALSE, V_GA_JOB_ID); --CREATE JOB FOR NPK
    ELSE
      CREATE_JOB(V_HCMS_JOB_ID, TRUE, V_GA_JOB_ID); --CREATE JOB FOR CWK
    END IF;
    
    CREATE_LOCATION(V_HCMS_LOCATION_ID, V_GA_LOCATION_ID);
    
    CREATE_POSITION(V_HCMS_JOB_ID, V_HCMS_LOCATION_ID, V_GA_JOB_ID, V_GA_LOCATION_ID, V_GA_POSITION_ID);
    
    V_CODE_COMBINATION_ID := GET_CODE_COMBINATION(V_GA_PERSON_ID, V_HCMS_ORG_ID, V_HCMS_COMPANY_ID);
    
    BEGIN
    
      SELECT ORGANIZATION_ID INTO V_BUSINESS_GROUP_ID FROM HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM WHERE NAME = 'FIF Business Group';
      
      SELECT ASSIGNMENT_STATUS_TYPE_ID INTO V_ASSIGNMENT_STATUS_TYPE_ID FROM PER_ASSIGNMENT_STATUS_TYPES@GL.US.ORACLE.COM WHERE PER_SYSTEM_STATUS = 'ACTIVE_ASSIGN' AND DEFAULT_FLAG = 'Y';

      SELECT MAX(ASSIGNMENT_ID) + 1 INTO V_GA_ASSIGNMENT_ID FROM PER_ALL_ASSIGNMENTS_F@GL.US.ORACLE.COM; 
      
      INSERT INTO PER_ALL_ASSIGNMENTS_F@GL.US.ORACLE.COM(
        ASSIGNMENT_ID,
        EFFECTIVE_START_DATE,
        EFFECTIVE_END_DATE,
        BUSINESS_GROUP_ID,
        POSITION_ID,
        JOB_ID,
        LOCATION_ID,
        PRIMARY_FLAG,
        PERSON_ID,
        ORGANIZATION_ID,
        ASSIGNMENT_SEQUENCE,
        ASSIGNMENT_TYPE,
        ASSIGNMENT_STATUS_TYPE_ID,
        DEFAULT_CODE_COMB_ID,
        CREATED_BY,
        CREATION_DATE,
        LAST_UPDATED_BY,
        LAST_UPDATE_DATE
      ) VALUES (
        V_GA_ASSIGNMENT_ID,
        V_DATE_FROM,
        V_DATE_TO,
        V_BUSINESS_GROUP_ID,
        V_GA_POSITION_ID,
        V_GA_JOB_ID,
        V_GA_LOCATION_ID,
        'Y',
        V_GA_PERSON_ID,
        V_GA_ORGANIZATION_ID,
        1,
        'M',
        V_ASSIGNMENT_STATUS_TYPE_ID,
        V_CODE_COMBINATION_ID,
        -1,
        SYSDATE,
        -1,
        SYSDATE
      );
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN CREATE ASSIGNMENT. FAILED INSERTING TO PER_ALL_ASSIGNMENTS_F CAUSED BY '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    END;
    
  END CREATE_ASSIGNMENT;
  
  FUNCTION IS_NUMBER(P_PARAM IN VARCHAR2) RETURN NUMBER
  IS
    V_NUM NUMBER;
  BEGIN
    V_NUM := TO_NUMBER(P_PARAM);
    RETURN 1;
  EXCEPTION
    WHEN OTHERS THEN
      RETURN 0;
    
  END IS_NUMBER;
  
  PROCEDURE CREATE_PEOPLE(P_HCMS_PERSON_ID IN NUMBER, P_GA_PERSON_ID OUT NUMBER)
  IS
    V_PERSON_ID NUMBER;
    V_EFFECTIVE_START_DATE DATE;
    V_EFFECTIVE_END_DATE DATE;
    V_FULL_NAME VARCHAR2(255);
    V_HIRE_DATE DATE;
    V_BIRTH_DATE DATE;
    V_EMPLOYEE_NUMBER VARCHAR2(100);
    V_PTKP_STATUS VARCHAR2(50);
    V_NATIONALITY VARCHAR2(10);
    V_NATIONAL_NUMBER VARCHAR2(100);
    V_NPWP VARCHAR2(100);
    V_SEX VARCHAR2(10);
    V_BLOOD VARCHAR2(5);
    V_ASTRA_JOIN_DATE DATE;
    V_ACCOUNT_NUMBER VARCHAR2(20);
    V_ACCOUNT_NAME VARCHAR2(100);
    V_BANK_NAME VARCHAR2(100);
    V_BRANCH_NAME VARCHAR2(255);
    V_BUSINESS_GROUP_ID NUMBER;
    V_PERSON_TYPE_ID NUMBER;
    V_PERIOD_OF_SERVICE_ID NUMBER;
    V_PERSON_TYPE_USAGE_ID NUMBER;
  BEGIN
    
    SELECT ORGANIZATION_ID INTO V_BUSINESS_GROUP_ID FROM HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM WHERE NAME = 'FIF Business Group';
    
    SELECT PERSON_TYPE_ID INTO V_PERSON_TYPE_ID FROM PER_PERSON_TYPES@GL.US.ORACLE.COM PPT 
      JOIN HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM HAOU ON PPT.BUSINESS_GROUP_ID = HAOU.ORGANIZATION_ID
      WHERE HAOU.NAME = 'FIF Business Group' AND PPT.SYSTEM_PERSON_TYPE = 'EMP';
    
    --1. Get Person Information from HCMS
    BEGIN
        select 
            ppi.effective_start_date,
            ppi.effective_end_date,
            ppi.full_name,
            ppi.hire_date,
            ppi.birth_date,
            ppi.employee_number,
            ppi.ptkp_status,
            DECODE(ppi.nationality_code,'ID','INA',ppi.nationality_code) NATIONALITY,
            pai.national_id_number,
            pai.npwp,
            decode(ppi.gender_code,'MALE','M','FEMALE','F') SEX,
            ppi.blood_type,
            ppi.astra_join_date,
            pbi.account_number,
            pbi.account_name,
            pbi.bank_name,
            pbi.branch_name
          INTO
            V_EFFECTIVE_START_DATE,
            V_EFFECTIVE_END_DATE,
            V_FULL_NAME,
            V_HIRE_DATE,
            V_BIRTH_DATE,
            V_EMPLOYEE_NUMBER,
            V_PTKP_STATUS,
            V_NATIONALITY,
            V_NATIONAL_NUMBER,
            V_NPWP,
            V_SEX,
            V_BLOOD,
            V_ASTRA_JOIN_DATE,
            V_ACCOUNT_NUMBER,
            V_ACCOUNT_NAME,
            V_BANK_NAME,
            V_BRANCH_NAME
          from pea_personal_informations ppi
          left join pea_account_informations pai on pai.person_id = ppi.person_id and ppi.company_id = pai.company_id and sysdate between pai.effective_start_date and pai.effective_end_date
          left join pea_bank_informations pbi on pbi.person_id = ppi.person_id and pbi.company_id = pai.company_id and sysdate between pbi.effective_start_date and pbi.effective_end_date
          where ppi.person_id = P_HCMS_PERSON_ID
          and sysdate between ppi.effective_start_date and ppi.effective_end_date;    
      EXCEPTION
        WHEN OTHERS THEN
            V_ERROR_MSG := 'ERROR WHEN QUERY PERSONAL INFO FROM HCMS, PERSON_ID '||P_HCMS_PERSON_ID||'. '||SUBSTR(SQLERRM,1,4000);
            RAISE EXCEPT_OTHER;
      END;
          
      
      --2. Insert into PER_ALL_PEOPLE
      BEGIN 
        SELECT MAX(PERSON_ID) + 1 INTO V_PERSON_ID FROM HR.PER_ALL_PEOPLE_F@GL.US.ORACLE.COM; 
        INSERT INTO HR.PER_ALL_PEOPLE_F@GL.US.ORACLE.COM (
          PERSON_ID,
          EFFECTIVE_START_DATE,
          EFFECTIVE_END_DATE,
          START_DATE,
          PERSON_TYPE_ID,
          LAST_NAME,
          BUSINESS_GROUP_ID,
          CURRENT_EMPLOYEE_FLAG,
          CURRENT_EMP_OR_APL_FLAG,
          DATE_OF_BIRTH,
          EMPLOYEE_NUMBER,
          FULL_NAME,
          MARITAL_STATUS,
          NATIONALITY,
          NATIONAL_IDENTIFIER,
          SEX,
          COORD_BEN_NO_CVG_FLAG,
          DPDNT_VLNTRY_SVCE_FLAG,
          ATTRIBUTE1,
          ATTRIBUTE6,
          ATTRIBUTE11,
          ATTRIBUTE12,
          ATTRIBUTE13,
          ATTRIBUTE14,
          ATTRIBUTE15,
          ATTRIBUTE16,
          CREATED_BY,
          CREATION_DATE,
          LAST_UPDATED_BY,
          LAST_UPDATE_DATE
        ) VALUES (
          V_PERSON_ID,
          V_EFFECTIVE_START_DATE,
          V_EFFECTIVE_END_DATE,
          V_HIRE_DATE,
          V_PERSON_TYPE_ID,
          V_FULL_NAME,
          V_BUSINESS_GROUP_ID,
          'Y',
          'Y',
          V_BIRTH_DATE,
          V_EMPLOYEE_NUMBER,
          V_FULL_NAME,
          V_PTKP_STATUS,
          V_NATIONALITY,
          V_NATIONAL_NUMBER,
          V_SEX,
          'N',
          'N',
          V_BLOOD,
          V_ASTRA_JOIN_DATE,
          V_ACCOUNT_NUMBER,
          V_ACCOUNT_NAME,
          V_BANK_NAME,
          V_BRANCH_NAME,
          V_PTKP_STATUS,
          V_NPWP,
          -1,
          SYSDATE,
          -1,
          SYSDATE
        );
        P_GA_PERSON_ID := V_PERSON_ID;
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN CREATE PEOPLE, FAILED INSERTING INTO HR.PER_ALL_PEOPLE_F. '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    END; 
    
    --3. Insert Into Period Of Service
    BEGIN
      SELECT MAX(PERIOD_OF_SERVICE_ID) + 1 INTO V_PERIOD_OF_SERVICE_ID FROM per_periods_of_service@GL.US.ORACLE.COM; 
      INSERT INTO per_periods_of_service@GL.US.ORACLE.COM(
        PERIOD_OF_SERVICE_ID,
        DATE_START,
        PERSON_ID,
        BUSINESS_GROUP_ID,
        CREATED_BY,
        CREATION_DATE,
        LAST_UPDATED_BY,
        LAST_UPDATE_DATE
      ) VALUES(
        V_PERIOD_OF_SERVICE_ID,
        V_HIRE_DATE,
        V_PERSON_ID,
        V_BUSINESS_GROUP_ID,
        -1,
        SYSDATE,
        -1,
        SYSDATE
      );
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN CREATE PEOPLE, FAILED INSERTING INTO PER_PERIODS_OF_SERVICE. '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    
    END;
    
    --4. Insert into PER_PERSON_TYPE_USAGES
    BEGIN
      SELECT MAX(PERSON_TYPE_USAGE_ID) + 1 INTO V_PERSON_TYPE_USAGE_ID FROM PER_PERSON_TYPE_USAGES_F@GL.US.ORACLE.COM; 
      INSERT INTO PER_PERSON_TYPE_USAGES_F@GL.US.ORACLE.COM(
        PERSON_TYPE_USAGE_ID,
        PERSON_ID,
        PERSON_TYPE_ID,
        EFFECTIVE_START_DATE,
        EFFECTIVE_END_DATE,  
        OBJECT_VERSION_NUMBER,
        CREATED_BY,
        CREATION_DATE,
        LAST_UPDATED_BY,
        LAST_UPDATE_DATE
      ) VALUES (
        V_PERSON_TYPE_USAGE_ID,
        V_PERSON_ID,
        V_PERSON_TYPE_ID,
        V_EFFECTIVE_START_DATE,
        V_EFFECTIVE_END_DATE,
        1,
        -1,
        SYSDATE,
        -1,
        SYSDATE
      );
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN CREATE PEOPLE. FAILED INSERTING INTO PER_PERSON_TYPE_USAGES_F. '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    
    END;
  
  
  END CREATE_PEOPLE;
  
  /* Author : out212
   * Purpose : Membuat Job di GASYS berdasarkan ID Job HCMS
   *
   * Steps :
   * 1. Cek pada table mapping berdasarkan HCMS_JOB_ID
   * 2. Jika pada tabel mapping sudah terdefine maka akan langsung mengembalikan nilai JOB_ID pada GASYS
   * 3. Jika pada table mapping terdapat lebih dari satu GA_JOB_ID maka akan mengambil nilai GA_JOB_ID paling maksimum
   * 4. Jika pada table mapping belum tersedia maka akan membuat Job baru di GASYS
   * 5. Cek pada job definition berdasarkan job code, jika sudah terdefine maka gunakan job_definition_id yang ada
   * 6. Jika pada job definition belum tersedia job code yang akan dibentuk, maka akan insert ke tabel tersebut
   *
   */
  PROCEDURE CREATE_JOB(P_HCMS_JOB_ID IN NUMBER, P_IS_CWK BOOLEAN, P_GA_JOB_ID OUT NUMBER)
  IS
    V_JOB_GROUP VARCHAR2(100);
    V_DATE_FROM DATE;
    V_DATE_TO DATE;
    V_GA_JOB_CODE VARCHAR2(100);
    V_GA_JOB_ID NUMBER;
    V_DEFINITION_ID NUMBER;
    V_JOB_ID NUMBER;
    V_JOB_DEF_CNT NUMBER;
    V_JOB_CODE VARCHAR2(100);
    v_job_cnt number;
  BEGIN
    
    --Check form table mapping
    select count(1) into v_job_cnt from syn_gasys_job_mapping where hc_job_id = p_hcms_job_id;
    if v_job_cnt = 1 then
      select ga_job_id into p_ga_job_id from syn_gasys_job_mapping where hc_job_id = p_hcms_job_id;
    else if v_job_cnt > 1 then -- Get more than one rows
        select max(ga_job_id) into p_ga_job_id from syn_gasys_job_mapping where hc_job_id = p_hcms_job_id;
    else if v_job_cnt = 0 then --Job is not exist, create new Job
          
          begin --Get Job Detail from HCMS
            select 
              WJ.JOB_CODE, jg.description, wjv.date_from, wjv.date_to, wji.info_value
            into
              V_JOB_CODE, V_JOB_GROUP, V_DATE_FROM, V_DATE_TO, V_GA_JOB_CODE
            from wos_jobs wj
            join wos_job_versions wjv on wj.job_id = wjv.job_id and sysdate between wjv.date_from and wjv.date_to
            left join wos_job_infos wji on wji.version_id = wjv.version_id
            LEFT JOIN bse_other_info_dtl DTL ON dtl.other_info_dtl_id = WJI.INFO_ID 
            LEFT JOIN bse_other_info_hdr HDR ON hdr.other_info_hdr_id = dtl.other_info_hdr_id
            left join (select  bld.detail_code, bld.description from bse_lookup_hdr hdr join bse_lookup_dependents bld on hdr.lookup_id = bld.lookup_id  where name = 'WSU_JOB_GROUP') jg on jg.detail_code = wjv.job_group_code
            where wj.job_id = p_hcms_job_id
              and dtl.DETAIL_CODE = 'HRMS_JOB_CODE'
              AND hdr.FORM_NAME = 'JOB';
          exception
            when no_data_found then
              v_error_msg := 'ERROR WHEN CREATE JOB, NO JOB FOUND FROM HCMS SOURCE, JOB_ID '||p_hcms_job_id;
              raise except_other;
          end;
          
          --Check Job Code from GASYS
          SELECT COUNT(1) INTO V_JOB_DEF_CNT FROM HR.PER_JOB_DEFINITIONS@GL.US.ORACLE.COM WHERE SEGMENT1 = V_GA_JOB_CODE;
          
          if v_job_def_cnt > 0 then --Job code is already exist
            begin
              if v_job_def_cnt = 1 then
                SELECT JOB_DEFINITION_ID INTO V_DEFINITION_ID FROM HR.PER_JOB_DEFINITIONS@GL.US.ORACLE.COM WHERE SEGMENT1 = V_GA_JOB_CODE;     
              else if v_job_def_cnt > 1 then --Contains more than one Job Code
                  IF P_IS_CWK THEN
                    SELECT max(JOB_DEFINITION_ID) INTO V_DEFINITION_ID FROM HR.PER_JOB_DEFINITIONS@GL.US.ORACLE.COM WHERE SEGMENT1 = V_GA_JOB_CODE AND SEGMENT2 LIKE '%CWK%';
                  ELSE
                    SELECT MAX(JOB_DEFINITION_ID) INTO V_DEFINITION_ID FROM HR.PER_JOB_DEFINITIONS@GL.US.ORACLE.COM WHERE SEGMENT1 = V_GA_JOB_CODE AND SEGMENT2 NOT LIKE '%CWK%';
                  END IF;
                end if;
              end if;
              
              BEGIN --get Job By Job Definition
                SELECT JOB_ID INTO V_JOB_ID FROM HR.PER_JOBS@GL.US.ORACLE.COM WHERE JOB_DEFINITION_ID = V_DEFINITION_ID;
                p_ga_job_id := v_job_id;
              EXCEPTION 
                WHEN NO_DATA_FOUND THEN
                  BEGIN
                    SELECT MAX(JOB_ID) + 1 INTO V_JOB_ID FROM PER_JOBS@GL.US.ORACLE.COM;
                    INSERT INTO HR.PER_JOBS@GL.US.ORACLE.COM(
                      JOB_ID, BUSINESS_GROUP_ID, JOB_DEFINITION_ID, DATE_FROM, DATE_TO,
                      NAME, JOB_GROUP_ID, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY
                    )VALUES(
                      V_JOB_ID, 81, V_DEFINITION_ID, V_DATE_FROM, V_DATE_TO,
                      V_GA_JOB_CODE||' '||V_JOB_GROUP, 21, SYSDATE, -1, SYSDATE, -1
                    );
                  
                    INSERT INTO syn_gasys_job_mapping(HC_JOB_ID, HC_JOB_CODE, GA_JOB_ID, GA_JOB_CODE, CREATION_DATE, LAST_UPDATE_DATE
                    ) VALUES (
                      P_HCMS_JOB_ID, V_JOB_CODE, V_JOB_ID, V_GA_JOB_CODE, sysdate, sysdate
                    );
                  END;
              END;            
            end;
          else -- if job code is not exist, create new Job
            begin
              SELECT MAX(JOB_DEFINITION_ID) + 1 INTO V_DEFINITION_ID FROM PER_JOB_DEFINITIONS@GL.US.ORACLE.COM;
              SELECT MAX(JOB_ID) + 1 INTO V_JOB_ID FROM PER_JOBS@GL.US.ORACLE.COM;
        
              INSERT INTO HR.PER_JOB_DEFINITIONS@GL.US.ORACLE.COM (
                JOB_DEFINITION_ID, ENABLED_FLAG, ID_FLEX_NUM, SUMMARY_FLAG, SEGMENT1, SEGMENT2
              ) VALUES (
                V_DEFINITION_ID, 'Y', 50269, 'N', V_GA_JOB_CODE, V_JOB_GROUP
              );
              
              INSERT INTO HR.PER_JOBS@GL.US.ORACLE.COM(
                JOB_ID, BUSINESS_GROUP_ID, JOB_DEFINITION_ID, DATE_FROM, DATE_TO,
                NAME, JOB_GROUP_ID, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY
              )VALUES(
                V_JOB_ID, 81, V_DEFINITION_ID, V_DATE_FROM, V_DATE_TO,
                V_GA_JOB_CODE||' '||V_JOB_GROUP, 21, SYSDATE, -1, SYSDATE, -1
              );
              
              INSERT INTO syn_gasys_job_mapping(HC_JOB_ID, HC_JOB_CODE, GA_JOB_ID, GA_JOB_CODE, CREATION_DATE, LAST_UPDATE_DATE
              ) VALUES (
                P_HCMS_JOB_ID, V_JOB_CODE, V_JOB_ID, V_GA_JOB_CODE, sysdate, sysdate
              );
              
              P_GA_JOB_ID := V_JOB_ID;
          
            EXCEPTION
              WHEN OTHERS THEN
                  V_ERROR_MSG := 'ERROR WHEN CREATE JOB. FAILED INSERTING TO HR.PER_JOBS HCMS_JOB_ID '||P_HCMS_JOB_ID||'. '||SUBSTR(SQLERRM,1,4000);
                  RAISE EXCEPT_OTHER;
                  
            end;
          end if;
        end if;
      end if;
    end if;
  END CREATE_JOB;
  
  PROCEDURE NEW_HIRE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE)
  IS
    V_GA_PERSON_ID NUMBER;
    V_ASSIGNMENT_ID NUMBER;
  BEGIN
  
    CREATE_PEOPLE(P_HCMS_PERSON_ID, V_GA_PERSON_ID);
    
    SELECT ASSIGNMENT_ID INTO V_ASSIGNMENT_ID 
    FROM PEA_PRIMARY_ASSIGNMENTS 
    WHERE PERSON_ID = P_HCMS_PERSON_ID
    AND P_PROCESS_DATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
    
    CREATE_ASSIGNMENT(V_ASSIGNMENT_ID);
    
  END NEW_HIRE;
  
  /*
  * Author : out212
  * Purpose : Membuat lokasi pada GASYS berdasarkan LOCATION_ID pada HCMS dan mengembalikan nilai LOCATION_ID dari GASYS
  *
  * Step :
  * 1. Cek GA_LOCATION_ID pada table mapping berdasarkan HCMS_LOCATION_ID, jika sudah terdefine maka akan mengembalikan nilai GA_LOCATION_ID
  * 2. Jika GA_LOCATION_ID lebih dari satum, maka akan diambil nilai maksimum
  * 3. Jika belum terdefine pada table mapping maka akan membuat Location baru pada GASYS
  *
  */
  PROCEDURE CREATE_LOCATION(P_HCMS_LOCATION_ID IN NUMBER, P_GA_LOCATION_ID OUT NUMBER)
  IS
    V_LOCATION_CODE VARCHAR2(100);
    V_LOCATION_NAME VARCHAR2(255);
    V_DESCRIPTION VARCHAR2(100);
    V_DATE_FROM DATE;
    V_DATE_TO DATE;
    V_CITY_CODE VARCHAR2(100);
    V_CITY VARCHAR2(100);
    V_COUNTRY_CODE VARCHAR2(10);
    V_ZONE_CODE VARCHAR2(50);
    V_PROV_CODE VARCHAR2(100);
    V_PROV VARCHAR2(100);
    V_LOCATION_ID NUMBER;
    V_COMPANY_ID NUMBER;
    
    V_HCMS_LOCATION_CODE VARCHAR2(50);
    V_HCMS_LOCATION_NAME VARCHAR2(255);
    V_GA_LOCATION_ID NUMBER;
    V_GA_LOCATION_ID_TMP NUMBER;
    V_GA_LOCATION_CODE VARCHAR2(60);
    V_VALID_LOCATION_CODE VARCHAR2(60);
    V_LOCATION_CNT NUMBER;
    
    
  BEGIN   
    --Check Table Mapping.
    select count(1) into v_location_cnt from syn_gasys_location_mapping where hc_location_id = p_hcms_location_id;
    if v_location_cnt = 1 then --Location already exist in table mapping
      select ga_location_id into p_ga_location_id from syn_gasys_location_mapping where hc_location_id = p_hcms_location_id;
    else if v_location_cnt > 1 then --Get more than one rows location from table mapping
      select max(ga_location_id) into p_ga_location_id from syn_gasys_location_mapping where hc_location_id = p_hcms_location_id;
    else if v_location_cnt = 0 then --Location is not exist, create new location
          
          -- check from table existng
          SELECT  count(1)
          into v_location_cnt
          FROM wos_locations wl
          left join hr.hr_locations_all@gl.us.oracle.com hla on  
              DECODE (fif_sync_to_gasys.is_number(SUBSTR (REPLACE (hla.location_code, ' ', '*'), 1, 5)),
                1, SUBSTR (hla.location_code, 1, 5), 
                0, DECODE ( fif_sync_to_gasys.is_number (SUBSTR (hla.location_code, 1, 3)),
                                    1, SUBSTR (hla.location_code, 1, 3) || '00',
                                    0, hla.location_code)
                )= wl.location_code
          where hla.location_id is not null
          and wl.location_id = p_hcms_location_id;
          
          if v_location_cnt = 1 then --data exist and only insert into table mapping
            
            SELECT  
              wl.location_code,
              wl.location_name,
              hla.location_id,
              hla.location_code
            into 
              v_location_code,
              v_location_name,
              p_ga_location_id,
              v_valid_location_code
            FROM wos_locations wl
            left join hr.hr_locations_all@gl.us.oracle.com hla on  
                DECODE (fif_sync_to_gasys.is_number(SUBSTR (REPLACE (hla.location_code, ' ', '*'), 1, 5)),
                  1, SUBSTR (hla.location_code, 1, 5), 
                  0, DECODE ( fif_sync_to_gasys.is_number (SUBSTR (hla.location_code, 1, 3)),
                                      1, SUBSTR (hla.location_code, 1, 3) || '00',
                                      0, hla.location_code)
                  )= wl.location_code
            where hla.location_id is not null
            and wl.location_id = p_hcms_location_id;
           
            --insert into table mapping
            BEGIN
              INSERT INTO SYN_GASYS_LOCATION_MAPPING(
                HC_LOCATION_ID, HC_LOCATION_CODE, GA_LOCATION_ID, GA_LOCATION_CODE,CREATION_DATE, LAST_UPDATE_DATE
              ) VALUES(
                P_HCMS_LOCATION_ID, V_LOCATION_CODE, p_ga_location_id, V_VALID_LOCATION_CODE, sysdate, sysdate             
              );
            EXCEPTION
              WHEN OTHERS THEN
              V_ERROR_MSG := 'ERROR WHEN CREATE LOCATION. FAILED INSESRTING TO SYN_GASYS_LOCATION_MAPPING. '||SUBSTR(SQLERRM,1,4000);
              RAISE EXCEPT_OTHER;
            END;  
            
          else if v_location_cnt = 0 then --data is not exist, create new location
              
                  BEGIN
                    SELECT  
                      WL.LOCATION_CODE, WL.LOCATION_NAME, WL.COMPANY_ID, WLV.DESCRIPTION, WLV.DATE_FROM, WLV.DATE_TO,
                      WLV.CITY_CODE, FMC.CITY, wlv.country_code, wlv.zone_code, wlv.province_code, FMP.PROVINSI
                    INTO 
                      V_LOCATION_CODE, V_LOCATION_NAME, V_COMPANY_ID, V_DESCRIPTION, V_DATE_FROM, V_DATE_TO,
                      V_CITY_CODE, V_CITY, V_COUNTRY_CODE, V_ZONE_CODE, V_PROV_CODE, V_PROV
                    FROM WOS_LOCATIONS WL
                    JOIN WOS_LOCATION_VERSIONS WLV ON WLV.LOCATION_ID = WL.LOCATION_ID AND SYSDATE BETWEEN WLV.DATE_FROM AND WLV.DATE_TO
                    LEFT JOIN FS_MST_CITIES FMC ON FMC.CITY_CODE = WLV.CITY_CODE
                    LEFT JOIN FS_MST_PROVINSI FMP ON FMP.PROV_CODE = WLV.PROVINCE_CODE
                    WHERE WL.LOCATION_ID = P_HCMS_LOCATION_ID;
                  EXCEPTION
                    WHEN OTHERS THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE LOCATION, FAILED QUERYING FROM LOCATION HCMS. LOCATION_ID '||P_HCMS_LOCATION_ID;
                      RAISE EXCEPT_OTHER;
                  END;
      
                  V_VALID_LOCATION_CODE := V_LOCATION_CODE||' '||V_LOCATION_NAME;
      
                  IF LENGTH(V_VALID_LOCATION_CODE) > 60 THEN
                    V_VALID_LOCATION_CODE := V_LOCATION_CODE;      
                  END IF;
      
                  BEGIN
                    SELECT MAX(LOCATION_ID) + 1 INTO V_LOCATION_ID FROM HR_LOCATIONS_ALL@GL.US.ORACLE.COM;
            
                    INSERT INTO HR_LOCATIONS_ALL@GL.US.ORACLE.COM(
                      LOCATION_ID, LOCATION_CODE, DESCRIPTION, SHIP_TO_LOCATION_ID, SHIP_TO_SITE_FLAG,
                      RECEIVING_SITE_FLAG, BILL_TO_SITE_FLAG, IN_ORGANIZATION_FLAG, OFFICE_SITE_FLAG,
                      INACTIVE_DATE, STYLE, TOWN_OR_CITY, COUNTRY, ENTERED_BY,
                      CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATE_DATE
                    ) VALUES(
                      V_LOCATION_ID, V_VALID_LOCATION_CODE, V_DESCRIPTION, V_LOCATION_ID, 'Y',
                      'Y', 'Y', 'Y', 'Y',
                      V_DATE_TO, V_COUNTRY_CODE, V_CITY, V_COUNTRY_CODE, -1,
                      -1, SYSDATE, -1, SYSDATE
                    );
                   
                  EXCEPTION
                    WHEN OTHERS THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE LOCATION. FAILED INSERTING TO HR_LOCATIONS_ALL CAUSED BY '||SUBSTR(SQLERRM,1,4000);
                      RAISE EXCEPT_OTHER;
                  END;
        
                  BEGIN
                    INSERT INTO SYN_GASYS_LOCATION_MAPPING(
                      HC_LOCATION_ID, HC_LOCATION_CODE, GA_LOCATION_ID, GA_LOCATION_CODE, CREATION_DATE, LAST_UPDATE_DATE
                    ) VALUES(
                      P_HCMS_LOCATION_ID, V_LOCATION_CODE, V_LOCATION_ID, V_VALID_LOCATION_CODE, sysdate, sysdate             
                    );
                  EXCEPTION
                    WHEN OTHERS THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE LOCATION. FAILED INSESRTING TO HCMS_TO_GASYS_LOCATION_MAPPING. '||SUBSTR(SQLERRM,1,4000);
                      RAISE EXCEPT_OTHER;
                  END;  
                    
                  P_GA_LOCATION_ID := V_LOCATION_ID;
          
            end if; --end of create new location
          end if;
          
        end if;
      end if;
    end if;
    
  END CREATE_LOCATION;
  
  PROCEDURE CREATE_POSITION(P_HCMS_JOB_ID IN NUMBER, P_HCMS_LOCATION_ID IN NUMBER, P_GA_JOB_ID IN NUMBER, P_GA_LOCATION_ID IN NUMBER, P_GA_POSITION_ID OUT NUMBER)
  IS
  
  V_POS_CNT NUMBER;
  V_POSITION_ID NUMBER;
  V_POSITION_DEFINITION_ID NUMBER;
  V_POSITION_CODE VARCHAR2(100);
  V_POSITION_NAME VARCHAR2(255);
  V_GA_LOCATION_ID NUMBER;
  V_GA_LOCATION_CODE VARCHAR2(255);
  V_GA_JOB_CODE VARCHAR2(255);
  V_GA_JOB_NAME VARCHAR2(255);
  V_GA_JOB_ID NUMBER;
  V_ORGANIZATION_ID NUMBER;
  V_CODE_SEQ NUMBER;
  BEGIN
    
    SELECT COUNT(1) INTO V_POS_CNT FROM SYN_GASYS_POSITION_MAPPING WHERE HC_LOCATION_ID = P_HCMS_LOCATION_ID AND HC_JOB_ID = P_HCMS_JOB_ID;
    
    IF V_POS_CNT = 1 THEN
      
      SELECT GA_POSITION_ID 
      INTO V_POSITION_ID 
      FROM SYN_GASYS_POSITION_MAPPING 
      WHERE HC_LOCATION_ID = P_HCMS_LOCATION_ID 
      AND HC_JOB_ID = P_HCMS_JOB_ID;
    
    ELSE IF V_POS_CNT > 1 THEN
        
        SELECT MAX(GA_POSITION_ID) 
        INTO V_POSITION_ID 
        FROM SYN_GASYS_POSITION_MAPPING 
        WHERE HC_LOCATION_ID = P_HCMS_LOCATION_ID 
        AND HC_JOB_ID = P_HCMS_JOB_ID;
        
        ELSE IF V_POS_CNT = 0 THEN       
            
            SELECT 
              LOCATION_CODE
            INTO
              V_GA_LOCATION_CODE
            FROM 
              HR_LOCATIONS_ALL@GL.US.ORACLE.COM
            WHERE LOCATION_ID = P_GA_LOCATION_ID;
            
            SELECT 
              SEGMENT1, 
              SEGMENT2, 
              JOB_ID
            INTO
              V_GA_JOB_CODE,
              V_GA_JOB_NAME,
              V_GA_JOB_ID
            FROM PER_JOBS@GL.US.ORACLE.COM PJ
            JOIN PER_JOB_DEFINITIONS@GL.US.ORACLE.COM PJD ON PJD.JOB_DEFINITION_ID = PJ.JOB_DEFINITION_ID
            WHERE JOB_ID = P_GA_JOB_ID;
            
            SELECT ORGANIZATION_ID INTO V_ORGANIZATION_ID FROM HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM WHERE UPPER(NAME) = UPPER('FIF Business Group');
            
            SELECT COUNT(1) INTO V_POS_CNT FROM PER_ALL_POSITIONS@GL.US.ORACLE.COM WHERE LOCATION_ID = V_GA_LOCATION_ID AND JOB_ID = V_GA_JOB_ID AND ORGANIZATION_ID = V_ORGANIZATION_ID;
            
            IF V_POS_CNT = 1 THEN
            
              SELECT POSITION_ID INTO V_POSITION_ID FROM PER_ALL_POSITIONS@GL.US.ORACLE.COM WHERE LOCATION_ID = V_GA_LOCATION_ID AND JOB_ID = V_GA_JOB_ID AND ORGANIZATION_ID = V_ORGANIZATION_ID;
              
            ELSE IF V_POS_CNT > 1 THEN 
              
               SELECT MAX(POSITION_ID) INTO V_POSITION_ID FROM PER_ALL_POSITIONS@GL.US.ORACLE.COM WHERE LOCATION_ID = V_GA_LOCATION_ID AND JOB_ID = V_GA_JOB_ID AND ORGANIZATION_ID = V_ORGANIZATION_ID;
              
              ELSE IF V_POS_CNT = 0 THEN --CREATE NEW POSITION  
                  
                  SELECT COUNT(0) INTO V_CODE_SEQ FROM PER_POSITION_DEFINITIONS@GL.US.ORACLE.COM WHERE SEGMENT1 LIKE 'HCM%';
                  V_CODE_SEQ := V_CODE_SEQ +1;
                  V_POSITION_CODE := 'HCM'||TO_CHAR(V_CODE_SEQ);
                  V_POSITION_NAME := V_POSITION_CODE||' '||V_GA_LOCATION_CODE||' '||V_GA_JOB_NAME;
                  
                  IF LENGTH(V_POSITION_NAME) > 240 THEN
                    V_POSITION_NAME := V_POSITION_CODE||' '||V_GA_LOCATION_CODE||' '||V_GA_JOB_NAME;
                    IF LENGTH(V_POSITION_NAME) > 240 THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE POSITION, POSITION NAME IS TOO LONG, NAME '||V_POSITION_NAME;
                      RAISE EXCEPT_OTHER;
                    END IF;
                  END IF;
            
                  BEGIN
                    SELECT MAX(POSITION_DEFINITION_ID) + 1 INTO V_POSITION_DEFINITION_ID FROM PER_POSITION_DEFINITIONS@GL.US.ORACLE.COM;
                    INSERT INTO PER_POSITION_DEFINITIONS@GL.US.ORACLE.COM(
                      POSITION_DEFINITION_ID, ENABLED_FLAG, SUMMARY_FLAG,
                      ID_FLEX_NUM, SEGMENT1, SEGMENT2
                    ) VALUES (
                      V_POSITION_DEFINITION_ID, 'Y', 'N',
                      50270, V_POSITION_CODE, V_GA_LOCATION_CODE||' '||V_GA_JOB_NAME
                    );
                  
                  EXCEPTION
                    WHEN OTHERS THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE POSITION, FAILED INSERTING TO PER_POSITION_DEFINITIONS. '||SUBSTR(SQLERRM,1,4000);
                      RAISE EXCEPT_OTHER;
                  
                  END;
                  
                  BEGIN
                    SELECT MAX(POSITION_ID)+1 INTO V_POSITION_ID FROM PER_ALL_POSITIONS@GL.US.ORACLE.COM;
                    INSERT INTO PER_ALL_POSITIONS@GL.US.ORACLE.COM(
                      POSITION_ID, POSITION_DEFINITION_ID, JOB_ID, BUSINESS_GROUP_ID,
                      ORGANIZATION_ID, DATE_EFFECTIVE, LOCATION_ID, NAME
                    )VALUES(
                      V_POSITION_ID, V_POSITION_DEFINITION_ID, V_GA_JOB_ID, V_ORGANIZATION_ID,
                      V_ORGANIZATION_ID, TRUNC(SYSDATE), V_GA_LOCATION_ID, V_POSITION_NAME
                    );
                    
                    INSERT INTO SYN_GASYS_POSITION_MAPPING(
                      HC_LOCATION_ID, HC_JOB_ID,
                      GA_POSITION_ID, GA_POSITION_NAME
                    ) VALUES(
                      P_HCMS_LOCATION_ID, P_HCMS_JOB_ID, 
                      V_POSITION_ID, V_POSITION_NAME
                    );
                  
                  EXCEPTION
                    WHEN OTHERS THEN
                      V_ERROR_MSG := 'ERROR WHEN CREATE POSITION. FAILED INSERTING TO PER_ALL_POSITIONS. '||SUBSTR(SQLERRM,1,4000);
                      RAISE EXCEPT_OTHER;
                  END;
                --END CREATE NEW POSITION
                END IF;
              END IF;
            END IF;
          END IF;
      END IF;
    END IF;
    
    P_GA_POSITION_ID := V_POSITION_ID;
    
  END CREATE_POSITION;
  
  PROCEDURE TERMINATE_EMPLOYEE(P_HCMS_PERSON_ID IN NUMBER, P_TERMINATION_DATE IN DATE)
  IS
    CURSOR CUR_ASSIGNMENT(P_PERSON_ID IN NUMBER) IS
    SELECT * FROM HR.per_all_assignments_f@GL.US.ORACLE.COM WHERE PERSON_ID = P_PERSON_ID AND SYSDATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
    
    CURSOR CUR_PEOPLE(P_PERSON_ID IN NUMBER) IS
    SELECT * FROM HR.per_all_people_f@GL.US.ORACLE.COM WHERE PERSON_ID = P_PERSON_ID AND SYSDATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
    
    V_HCMS_NPK VARCHAR2(50);
    V_GL_PERSON_ID NUMBER;
    V_TERMINATION_DATE DATE;
    V_REASON VARCHAR2(255);
    V_ASSIGN_STATUS_TYPE_ID NUMBER;
    V_DATE_FROM DATE;
    v_DATE_TO DATE;
    V_PERSON_TYPE_ID NUMBER;
    V_ASSIGNMENT_ID NUMBER;
  BEGIN
    
    --GET TERMINATION INFO FROM HCMS
    SELECT 
      PPI.EMPLOYEE_NUMBER,
      TR.TERMINATION_DATE,
      TT.TERMINATION_REASON
    INTO
      V_HCMS_NPK,
      V_TERMINATION_DATE,
      V_REASON
    FROM ter_requests TR
    JOIN pea_personal_informations PPI ON PPI.PERSON_ID = TR.PERSON_ID AND TR.COMPANY_ID = PPI.COMPANY_ID
    JOIN TER_TYPES TT ON TT.TYPE_ID = TR.TERMINATION_TYPE_ID
    WHERE
      TR.TERMINATION_DATE BETWEEN PPI.EFFECTIVE_START_DATE AND PPI.EFFECTIVE_END_DATE
      AND TR.PERSON_ID = P_HCMS_PERSON_ID 
      AND TERMINATION_DATE = P_TERMINATION_DATE;
      
    --UPDATE PERIOD SERVICE
    BEGIN
      SELECT PERSON_ID 
      INTO V_GL_PERSON_ID 
      FROM HR.per_all_people_f@GL.US.ORACLE.COM 
      WHERE EMPLOYEE_NUMBER = V_HCMS_NPK 
      AND SYSDATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        V_ERROR_MSG := 'ERROR WHEN TERMINATE EMPLOYEE, CANNOT FIND PERSONAL INFO WITH NPK '||V_HCMS_NPK;
        RAISE EXCEPT_OTHER;
    END;  
    
    BEGIN
      UPDATE per_periods_of_service@GL.US.ORACLE.COM 
      SET
        ACTUAL_TERMINATION_DATE = V_TERMINATION_DATE,
        LAST_STANDARD_PROCESS_DATE = V_TERMINATION_DATE,
        LEAVING_REASON = 'R',
        NOTIFIED_TERMINATION_DATE = V_TERMINATION_DATE,
        COMMENTS = V_REASON
      WHERE
        PERSON_ID = V_GL_PERSON_ID;
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN TERMINATE EMPLOYEE. FAILED UPDATING PER_PERIODS_OF_SERVICE. CAUSED BY '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    END;    
    --END UPDATE PERIOD SERVICE
    
    
    --INSERT ASSIGNMENT
    SELECT ASSIGNMENT_STATUS_TYPE_ID INTO V_ASSIGN_STATUS_TYPE_ID 
    FROM PER_ASSIGNMENT_STATUS_TYPES@GL.US.ORACLE.COM WHERE PER_SYSTEM_STATUS = 'TERM_ASSIGN'  AND ACTIVE_FLAG = 'Y' AND DEFAULT_FLAG = 'Y';
      
    V_DATE_FROM := V_TERMINATION_DATE;
    V_DATE_TO := TO_DATE('31-DEC-4712','DD-MON-YYYY');
    
    BEGIN
      FOR CUR_A IN CUR_ASSIGNMENT(V_GL_PERSON_ID)
      LOOP
        UPDATE HR.per_all_assignments_f@GL.US.ORACLE.COM
        SET EFFECTIVE_END_DATE = V_TERMINATION_DATE - 1
        WHERE ASSIGNMENT_ID = CUR_A.ASSIGNMENT_ID;
        
        SELECT MAX(ASSIGNMENT_ID) + 1 INTO V_ASSIGNMENT_ID FROM HR.per_all_assignments_f@GL.US.ORACLE.COM;
        
        INSERT INTO HR.per_all_assignments_f@GL.US.ORACLE.COM(
          ASSIGNMENT_ID,
          EFFECTIVE_START_DATE,
          EFFECTIVE_END_DATE,
          BUSINESS_GROUP_ID,
          PRIMARY_FLAG,
          PERSON_ID,
          ORGANIZATION_ID,
          ASSIGNMENT_SEQUENCE,
          ASSIGNMENT_TYPE,
          ASSIGNMENT_STATUS_TYPE_ID,
          PO_HEADER_ID,
          PO_LINE_ID,
          PROJECTED_ASSIGNMENT_END,
          GRADE_LADDER_PGM_ID,
          SUPERVISOR_ASSIGNMENT_ID,
          PROGRAM_APPLICATION_ID,
          PROGRAM_ID,
          PROGRAM_UPDATE_DATE,
          ASS_ATTRIBUTE_CATEGORY,
          ASS_ATTRIBUTE1,
          ASS_ATTRIBUTE2,
          ASS_ATTRIBUTE3,
          ASS_ATTRIBUTE4,
          ASS_ATTRIBUTE5,
          ASS_ATTRIBUTE6,
          ASS_ATTRIBUTE7,
          ASS_ATTRIBUTE8,
          ASS_ATTRIBUTE9,
          ASS_ATTRIBUTE10,
          ASS_ATTRIBUTE11,
          ASS_ATTRIBUTE12,
          ASS_ATTRIBUTE13,
          ASS_ATTRIBUTE14,
          ASS_ATTRIBUTE15,
          ASS_ATTRIBUTE16,
          ASS_ATTRIBUTE17,
          ASS_ATTRIBUTE18,
          ASS_ATTRIBUTE19,
          ASS_ATTRIBUTE20,
          ASS_ATTRIBUTE21,
          ASS_ATTRIBUTE22,
          ASS_ATTRIBUTE23,
          ASS_ATTRIBUTE24,
          ASS_ATTRIBUTE25,
          ASS_ATTRIBUTE26,
          ASS_ATTRIBUTE27,
          ASS_ATTRIBUTE28,
          ASS_ATTRIBUTE29,
          ASS_ATTRIBUTE30,
          LAST_UPDATE_DATE,
          LAST_UPDATED_BY,
          LAST_UPDATE_LOGIN,
          CREATED_BY,
          CREATION_DATE,
          TITLE,
          NOTICE_PERIOD,
          NOTICE_PERIOD_UOM,
          EMPLOYEE_CATEGORY,
          WORK_AT_HOME,
          JOB_POST_SOURCE_NAME,
          POSTING_CONTENT_ID,
          PERIOD_OF_PLACEMENT_DATE_START,
          VENDOR_ID,
          VENDOR_EMPLOYEE_NUMBER,
          VENDOR_ASSIGNMENT_NUMBER,
          ASSIGNMENT_CATEGORY,
          PROJECT_TITLE,
          APPLICANT_RANK,
          VENDOR_SITE_ID,
          RECRUITER_ID,
          GRADE_ID,
          POSITION_ID,
          JOB_ID,
          PAYROLL_ID,
          LOCATION_ID,
          PERSON_REFERRED_BY_ID,
          SUPERVISOR_ID,
          SPECIAL_CEILING_STEP_ID,
          RECRUITMENT_ACTIVITY_ID,
          SOURCE_ORGANIZATION_ID,
          PEOPLE_GROUP_ID,
          SOFT_CODING_KEYFLEX_ID,
          VACANCY_ID,
          PAY_BASIS_ID,
          APPLICATION_ID,
          ASSIGNMENT_NUMBER,
          CHANGE_REASON,
          COMMENT_ID,
          DATE_PROBATION_END,
          DEFAULT_CODE_COMB_ID,
          EMPLOYMENT_CATEGORY,
          FREQUENCY,
          INTERNAL_ADDRESS_LINE,
          MANAGER_FLAG,
          NORMAL_HOURS,
          PERF_REVIEW_PERIOD,
          PERF_REVIEW_PERIOD_FREQUENCY,
          PERIOD_OF_SERVICE_ID,
          PROBATION_PERIOD,
          PROBATION_UNIT,
          SAL_REVIEW_PERIOD,
          SAL_REVIEW_PERIOD_FREQUENCY,
          SET_OF_BOOKS_ID,
          SOURCE_TYPE,
          TIME_NORMAL_FINISH,
          TIME_NORMAL_START,
          BARGAINING_UNIT_CODE,
          LABOUR_UNION_MEMBER_FLAG,
          HOURLY_SALARIED_CODE,
          CONTRACT_ID,
          COLLECTIVE_AGREEMENT_ID,
          CAGR_ID_FLEX_NUM,
          CAGR_GRADE_DEF_ID,
          ESTABLISHMENT_ID,
          REQUEST_ID
        ) VALUES (
          V_ASSIGNMENT_ID,
          V_DATE_FROM,
          V_DATE_TO,
          CUR_A.BUSINESS_GROUP_ID,
          CUR_A.PRIMARY_FLAG,
          CUR_A.PERSON_ID,
          CUR_A.ORGANIZATION_ID,
          CUR_A.ASSIGNMENT_SEQUENCE,
          CUR_A.ASSIGNMENT_TYPE,
          V_ASSIGN_STATUS_TYPE_ID,
          CUR_A.PO_HEADER_ID,
          CUR_A.PO_LINE_ID,
          CUR_A.PROJECTED_ASSIGNMENT_END,
          CUR_A.GRADE_LADDER_PGM_ID,
          CUR_A.SUPERVISOR_ASSIGNMENT_ID,
          CUR_A.PROGRAM_APPLICATION_ID,
          CUR_A.PROGRAM_ID,
          CUR_A.PROGRAM_UPDATE_DATE,
          CUR_A.ASS_ATTRIBUTE_CATEGORY,
          CUR_A.ASS_ATTRIBUTE1,
          CUR_A.ASS_ATTRIBUTE2,
          CUR_A.ASS_ATTRIBUTE3,
          CUR_A.ASS_ATTRIBUTE4,
          CUR_A.ASS_ATTRIBUTE5,
          CUR_A.ASS_ATTRIBUTE6,
          CUR_A.ASS_ATTRIBUTE7,
          CUR_A.ASS_ATTRIBUTE8,
          CUR_A.ASS_ATTRIBUTE9,
          CUR_A.ASS_ATTRIBUTE10,
          CUR_A.ASS_ATTRIBUTE11,
          CUR_A.ASS_ATTRIBUTE12,
          CUR_A.ASS_ATTRIBUTE13,
          CUR_A.ASS_ATTRIBUTE14,
          CUR_A.ASS_ATTRIBUTE15,
          CUR_A.ASS_ATTRIBUTE16,
          CUR_A.ASS_ATTRIBUTE17,
          CUR_A.ASS_ATTRIBUTE18,
          CUR_A.ASS_ATTRIBUTE19,
          CUR_A.ASS_ATTRIBUTE20,
          CUR_A.ASS_ATTRIBUTE21,
          CUR_A.ASS_ATTRIBUTE22,
          CUR_A.ASS_ATTRIBUTE23,
          CUR_A.ASS_ATTRIBUTE24,
          CUR_A.ASS_ATTRIBUTE25,
          CUR_A.ASS_ATTRIBUTE26,
          CUR_A.ASS_ATTRIBUTE27,
          CUR_A.ASS_ATTRIBUTE28,
          CUR_A.ASS_ATTRIBUTE29,
          CUR_A.ASS_ATTRIBUTE30,
          SYSDATE,
          -1,
          CUR_A.LAST_UPDATE_LOGIN,
          CUR_A.CREATED_BY,
          CUR_A.CREATION_DATE,
          CUR_A.TITLE,
          CUR_A.NOTICE_PERIOD,
          CUR_A.NOTICE_PERIOD_UOM,
          CUR_A.EMPLOYEE_CATEGORY,
          CUR_A.WORK_AT_HOME,
          CUR_A.JOB_POST_SOURCE_NAME,
          CUR_A.POSTING_CONTENT_ID,
          CUR_A.PERIOD_OF_PLACEMENT_DATE_START,
          CUR_A.VENDOR_ID,
          CUR_A.VENDOR_EMPLOYEE_NUMBER,
          CUR_A.VENDOR_ASSIGNMENT_NUMBER,
          CUR_A.ASSIGNMENT_CATEGORY,
          CUR_A.PROJECT_TITLE,
          CUR_A.APPLICANT_RANK,
          CUR_A.VENDOR_SITE_ID,
          CUR_A.RECRUITER_ID,
          CUR_A.GRADE_ID,
          CUR_A.POSITION_ID,
          CUR_A.JOB_ID,
          CUR_A.PAYROLL_ID,
          CUR_A.LOCATION_ID,
          CUR_A.PERSON_REFERRED_BY_ID,
          CUR_A.SUPERVISOR_ID,
          CUR_A.SPECIAL_CEILING_STEP_ID,
          CUR_A.RECRUITMENT_ACTIVITY_ID,
          CUR_A.SOURCE_ORGANIZATION_ID,
          CUR_A.PEOPLE_GROUP_ID,
          CUR_A.SOFT_CODING_KEYFLEX_ID,
          CUR_A.VACANCY_ID,
          CUR_A.PAY_BASIS_ID,
          CUR_A.APPLICATION_ID,
          CUR_A.ASSIGNMENT_NUMBER,
          CUR_A.CHANGE_REASON,
          CUR_A.COMMENT_ID,
          CUR_A.DATE_PROBATION_END,
          CUR_A.DEFAULT_CODE_COMB_ID,
          CUR_A.EMPLOYMENT_CATEGORY,
          CUR_A.FREQUENCY,
          CUR_A.INTERNAL_ADDRESS_LINE,
          CUR_A.MANAGER_FLAG,
          CUR_A.NORMAL_HOURS,
          CUR_A.PERF_REVIEW_PERIOD,
          CUR_A.PERF_REVIEW_PERIOD_FREQUENCY,
          CUR_A.PERIOD_OF_SERVICE_ID,
          CUR_A.PROBATION_PERIOD,
          CUR_A.PROBATION_UNIT,
          CUR_A.SAL_REVIEW_PERIOD,
          CUR_A.SAL_REVIEW_PERIOD_FREQUENCY,
          CUR_A.SET_OF_BOOKS_ID,
          CUR_A.SOURCE_TYPE,
          CUR_A.TIME_NORMAL_FINISH,
          CUR_A.TIME_NORMAL_START,
          CUR_A.BARGAINING_UNIT_CODE,
          CUR_A.LABOUR_UNION_MEMBER_FLAG,
          CUR_A.HOURLY_SALARIED_CODE,
          CUR_A.CONTRACT_ID,
          CUR_A.COLLECTIVE_AGREEMENT_ID,
          CUR_A.CAGR_ID_FLEX_NUM,
          CUR_A.CAGR_GRADE_DEF_ID,
          CUR_A.ESTABLISHMENT_ID,
          CUR_A.REQUEST_ID
        );
      END LOOP;
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN TERMINATE EMPLOYEE. FAILED INSERTING NEW HISTORY INTO per_all_assignments_f. CAUSED BY '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;   
    END;
    
    --INSERT PEOPLE
    BEGIN
      
      SELECT PERSON_TYPE_ID INTO V_PERSON_TYPE_ID FROM PER_PERSON_TYPES@GL.US.ORACLE.COM PPT 
      JOIN HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM HAOU ON PPT.BUSINESS_GROUP_ID = HAOU.ORGANIZATION_ID
      WHERE HAOU.NAME = 'FIF Business Group' AND PPT.SYSTEM_PERSON_TYPE = 'EX_EMP';
      
      FOR CUR_P IN CUR_PEOPLE(V_GL_PERSON_ID)
      LOOP
        
        UPDATE HR.per_all_people_f@GL.US.ORACLE.COM
        SET EFFECTIVE_END_DATE = V_TERMINATION_DATE - 1
        WHERE PERSON_ID = V_GL_PERSON_ID AND SYSDATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
        
        INSERT INTO HR.per_all_people_f@GL.US.ORACLE.COM(
          PERSON_ID,
          EFFECTIVE_START_DATE,
          EFFECTIVE_END_DATE,
          START_DATE,
          PERSON_TYPE_ID,
          LAST_NAME,
          BUSINESS_GROUP_ID,
          COORD_BEN_MED_EXT_ER,
          COORD_BEN_MED_CVG_STRT_DT,
          COORD_BEN_MED_CVG_END_DT,
          PARTY_ID,
          NPW_NUMBER,
          CURRENT_NPW_FLAG,
          ATTRIBUTE23,
          ATTRIBUTE24,
          ATTRIBUTE25,
          ATTRIBUTE26,
          ATTRIBUTE27,
          ATTRIBUTE28,
          ATTRIBUTE29,
          ATTRIBUTE30,
          LAST_UPDATE_DATE,
          LAST_UPDATED_BY,
          LAST_UPDATE_LOGIN,
          CREATED_BY,
          CREATION_DATE,
          PER_INFORMATION_CATEGORY,
          PER_INFORMATION1,
          PER_INFORMATION2,
          PER_INFORMATION3,
          PER_INFORMATION4,
          PER_INFORMATION5,
          PER_INFORMATION6,
          PER_INFORMATION7,
          PER_INFORMATION8,
          PER_INFORMATION9,
          PER_INFORMATION10,
          PER_INFORMATION11,
          PER_INFORMATION12,
          PER_INFORMATION13,
          PER_INFORMATION14,
          PER_INFORMATION15,
          PER_INFORMATION16,
          PER_INFORMATION17,
          PER_INFORMATION18,
          PER_INFORMATION19,
          PER_INFORMATION20,
          PER_INFORMATION21,
          PER_INFORMATION22,
          PER_INFORMATION23,
          PER_INFORMATION24,
          PER_INFORMATION25,
          PER_INFORMATION26,
          PER_INFORMATION27,
          PER_INFORMATION28,
          PER_INFORMATION29,
          PER_INFORMATION30,
          DATE_OF_DEATH,
          ORIGINAL_DATE_OF_HIRE,
          TOWN_OF_BIRTH,
          REGION_OF_BIRTH,
          COUNTRY_OF_BIRTH,
          GLOBAL_PERSON_ID,
          COORD_BEN_MED_PL_NAME,
          COORD_BEN_MED_INSR_CRR_NAME,
          COORD_BEN_MED_INSR_CRR_IDENT,
          TITLE,
          VENDOR_ID,
          WORK_SCHEDULE,
          WORK_TELEPHONE,
          COORD_BEN_MED_PLN_NO,
          COORD_BEN_NO_CVG_FLAG,
          DPDNT_ADOPTION_DATE,
          DPDNT_VLNTRY_SVCE_FLAG,
          RECEIPT_OF_DEATH_CERT_DATE,
          USES_TOBACCO_FLAG,
          BENEFIT_GROUP_ID,
          REQUEST_ID,
          PROGRAM_APPLICATION_ID,
          PROGRAM_ID,
          PROGRAM_UPDATE_DATE,
          ATTRIBUTE_CATEGORY,
          ATTRIBUTE1,
          ATTRIBUTE2,
          ATTRIBUTE3,
          ATTRIBUTE4,
          ATTRIBUTE5,
          ATTRIBUTE6,
          ATTRIBUTE7,
          ATTRIBUTE8,
          ATTRIBUTE9,
          ATTRIBUTE10,
          ATTRIBUTE11,
          ATTRIBUTE12,
          ATTRIBUTE13,
          ATTRIBUTE14,
          ATTRIBUTE15,
          ATTRIBUTE16,
          ATTRIBUTE17,
          ATTRIBUTE18,
          ATTRIBUTE19,
          ATTRIBUTE20,
          ATTRIBUTE21,
          ATTRIBUTE22,
          APPLICANT_NUMBER,
          BACKGROUND_CHECK_STATUS,
          BACKGROUND_DATE_CHECK,
          BLOOD_TYPE,
          COMMENT_ID,
          CORRESPONDENCE_LANGUAGE,
          CURRENT_APPLICANT_FLAG,
          CURRENT_EMP_OR_APL_FLAG,
          CURRENT_EMPLOYEE_FLAG,
          DATE_EMPLOYEE_DATA_VERIFIED,
          DATE_OF_BIRTH,
          EMAIL_ADDRESS,
          EMPLOYEE_NUMBER,
          EXPENSE_CHECK_SEND_TO_ADDRESS,
          FAST_PATH_EMPLOYEE,
          FIRST_NAME,
          FTE_CAPACITY,
          FULL_NAME,
          HOLD_APPLICANT_DATE_UNTIL,
          HONORS,
          INTERNAL_LOCATION,
          KNOWN_AS,
          LAST_MEDICAL_TEST_BY,
          LAST_MEDICAL_TEST_DATE,
          MAILSTOP,
          MARITAL_STATUS,
          MIDDLE_NAMES,
          NATIONALITY,
          NATIONAL_IDENTIFIER,
          OFFICE_NUMBER,
          ON_MILITARY_SERVICE,
          ORDER_NAME,
          PRE_NAME_ADJUNCT,
          PREVIOUS_LAST_NAME,
          PROJECTED_START_DATE,
          REHIRE_AUTHORIZOR,
          REHIRE_REASON,
          REHIRE_RECOMMENDATION,
          RESUME_EXISTS,
          RESUME_LAST_UPDATED,
          REGISTERED_DISABLED_FLAG,
          SECOND_PASSPORT_EXISTS,
          SEX,
          STUDENT_STATUS,
          SUFFIX
        )VALUES(
          CUR_P.PERSON_ID,
          V_DATE_FROM,
          V_DATE_TO,
          CUR_P.START_DATE,
          V_PERSON_TYPE_ID,
          CUR_P.LAST_NAME,
          CUR_P.BUSINESS_GROUP_ID,
          CUR_P.COORD_BEN_MED_EXT_ER,
          CUR_P.COORD_BEN_MED_CVG_STRT_DT,
          CUR_P.COORD_BEN_MED_CVG_END_DT,
          CUR_P.PARTY_ID,
          CUR_P.NPW_NUMBER,
          CUR_P.CURRENT_NPW_FLAG,
          CUR_P.ATTRIBUTE23,
          CUR_P.ATTRIBUTE24,
          CUR_P.ATTRIBUTE25,
          CUR_P.ATTRIBUTE26,
          CUR_P.ATTRIBUTE27,
          CUR_P.ATTRIBUTE28,
          CUR_P.ATTRIBUTE29,
          CUR_P.ATTRIBUTE30,
          SYSDATE,
          -1,
          CUR_P.LAST_UPDATE_LOGIN,
          CUR_P.CREATED_BY,
          CUR_P.CREATION_DATE,
          CUR_P.PER_INFORMATION_CATEGORY,
          CUR_P.PER_INFORMATION1,
          CUR_P.PER_INFORMATION2,
          CUR_P.PER_INFORMATION3,
          CUR_P.PER_INFORMATION4,
          CUR_P.PER_INFORMATION5,
          CUR_P.PER_INFORMATION6,
          CUR_P.PER_INFORMATION7,
          CUR_P.PER_INFORMATION8,
          CUR_P.PER_INFORMATION9,
          CUR_P.PER_INFORMATION10,
          CUR_P.PER_INFORMATION11,
          CUR_P.PER_INFORMATION12,
          CUR_P.PER_INFORMATION13,
          CUR_P.PER_INFORMATION14,
          CUR_P.PER_INFORMATION15,
          CUR_P.PER_INFORMATION16,
          CUR_P.PER_INFORMATION17,
          CUR_P.PER_INFORMATION18,
          CUR_P.PER_INFORMATION19,
          CUR_P.PER_INFORMATION20,
          CUR_P.PER_INFORMATION21,
          CUR_P.PER_INFORMATION22,
          CUR_P.PER_INFORMATION23,
          CUR_P.PER_INFORMATION24,
          CUR_P.PER_INFORMATION25,
          CUR_P.PER_INFORMATION26,
          CUR_P.PER_INFORMATION27,
          CUR_P.PER_INFORMATION28,
          CUR_P.PER_INFORMATION29,
          CUR_P.PER_INFORMATION30,
          CUR_P.DATE_OF_DEATH,
          CUR_P.ORIGINAL_DATE_OF_HIRE,
          CUR_P.TOWN_OF_BIRTH,
          CUR_P.REGION_OF_BIRTH,
          CUR_P.COUNTRY_OF_BIRTH,
          CUR_P.GLOBAL_PERSON_ID,
          CUR_P.COORD_BEN_MED_PL_NAME,
          CUR_P.COORD_BEN_MED_INSR_CRR_NAME,
          CUR_P.COORD_BEN_MED_INSR_CRR_IDENT,
          CUR_P.TITLE,
          CUR_P.VENDOR_ID,
          CUR_P.WORK_SCHEDULE,
          CUR_P.WORK_TELEPHONE,
          CUR_P.COORD_BEN_MED_PLN_NO,
          CUR_P.COORD_BEN_NO_CVG_FLAG,
          CUR_P.DPDNT_ADOPTION_DATE,
          CUR_P.DPDNT_VLNTRY_SVCE_FLAG,
          CUR_P.RECEIPT_OF_DEATH_CERT_DATE,
          CUR_P.USES_TOBACCO_FLAG,
          CUR_P.BENEFIT_GROUP_ID,
          CUR_P.REQUEST_ID,
          CUR_P.PROGRAM_APPLICATION_ID,
          CUR_P.PROGRAM_ID,
          CUR_P.PROGRAM_UPDATE_DATE,
          CUR_P.ATTRIBUTE_CATEGORY,
          CUR_P.ATTRIBUTE1,
          CUR_P.ATTRIBUTE2,
          CUR_P.ATTRIBUTE3,
          CUR_P.ATTRIBUTE4,
          CUR_P.ATTRIBUTE5,
          CUR_P.ATTRIBUTE6,
          CUR_P.ATTRIBUTE7,
          CUR_P.ATTRIBUTE8,
          CUR_P.ATTRIBUTE9,
          CUR_P.ATTRIBUTE10,
          CUR_P.ATTRIBUTE11,
          CUR_P.ATTRIBUTE12,
          CUR_P.ATTRIBUTE13,
          CUR_P.ATTRIBUTE14,
          CUR_P.ATTRIBUTE15,
          CUR_P.ATTRIBUTE16,
          CUR_P.ATTRIBUTE17,
          CUR_P.ATTRIBUTE18,
          CUR_P.ATTRIBUTE19,
          CUR_P.ATTRIBUTE20,
          CUR_P.ATTRIBUTE21,
          CUR_P.ATTRIBUTE22,
          CUR_P.APPLICANT_NUMBER,
          CUR_P.BACKGROUND_CHECK_STATUS,
          CUR_P.BACKGROUND_DATE_CHECK,
          CUR_P.BLOOD_TYPE,
          CUR_P.COMMENT_ID,
          CUR_P.CORRESPONDENCE_LANGUAGE,
          CUR_P.CURRENT_APPLICANT_FLAG,
          CUR_P.CURRENT_EMP_OR_APL_FLAG,
          CUR_P.CURRENT_EMPLOYEE_FLAG,
          CUR_P.DATE_EMPLOYEE_DATA_VERIFIED,
          CUR_P.DATE_OF_BIRTH,
          CUR_P.EMAIL_ADDRESS,
          CUR_P.EMPLOYEE_NUMBER,
          CUR_P.EXPENSE_CHECK_SEND_TO_ADDRESS,
          CUR_P.FAST_PATH_EMPLOYEE,
          CUR_P.FIRST_NAME,
          CUR_P.FTE_CAPACITY,
          CUR_P.FULL_NAME,
          CUR_P.HOLD_APPLICANT_DATE_UNTIL,
          CUR_P.HONORS,
          CUR_P.INTERNAL_LOCATION,
          CUR_P.KNOWN_AS,
          CUR_P.LAST_MEDICAL_TEST_BY,
          CUR_P.LAST_MEDICAL_TEST_DATE,
          CUR_P.MAILSTOP,
          CUR_P.MARITAL_STATUS,
          CUR_P.MIDDLE_NAMES,
          CUR_P.NATIONALITY,
          CUR_P.NATIONAL_IDENTIFIER,
          CUR_P.OFFICE_NUMBER,
          CUR_P.ON_MILITARY_SERVICE,
          CUR_P.ORDER_NAME,
          CUR_P.PRE_NAME_ADJUNCT,
          CUR_P.PREVIOUS_LAST_NAME,
          CUR_P.PROJECTED_START_DATE,
          CUR_P.REHIRE_AUTHORIZOR,
          CUR_P.REHIRE_REASON,
          CUR_P.REHIRE_RECOMMENDATION,
          CUR_P.RESUME_EXISTS,
          CUR_P.RESUME_LAST_UPDATED,
          CUR_P.REGISTERED_DISABLED_FLAG,
          CUR_P.SECOND_PASSPORT_EXISTS,
          CUR_P.SEX,
          CUR_P.STUDENT_STATUS,
          CUR_P.SUFFIX
        );
        
      END LOOP;
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN TERMINATE EMPLOYEE. FAILED INSERTING NEW HISTORY INTO per_all_people_f. CAUSED BY '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;
    
    END;
    
    
    
  END TERMINATE_EMPLOYEE;
  
  PROCEDURE TRANSFER_EMPLOYEE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE)
  IS
    CURSOR CUR_ASSIGNMENT(P_PERSON_ID IN NUMBER) IS
    SELECT * FROM HR.per_all_assignments_f@GL.US.ORACLE.COM WHERE PERSON_ID = P_PERSON_ID AND SYSDATE BETWEEN EFFECTIVE_START_DATE AND EFFECTIVE_END_DATE;
    
    V_HCMS_PERSON_ID pea_primary_assignments.PERSON_ID%TYPE;
    V_HCMS_NPK pea_personal_informations.EMPLOYEE_NUMBER%TYPE;
    V_HCMS_JOB_CODE wos_job_infos.info_value%type;
    V_HCMS_JOB_NAME WOS_JOBS.JOB_NAME%TYPE;
    V_HCMS_LOCATION_CODE wos_locations.location_code%TYPE;
    V_HCMS_LOCATION_NAME wos_locations.location_NAME%TYPE;
    V_HCMS_COMPANY_NAME bse_companies.company_name%TYPE;
    V_HCMS_JOB_ID NUMBER;
    V_HCMS_LOCATION_ID NUMBER;
    V_GL_PERSON_ID NUMBER;
    V_GL_JOB_ID NUMBER;
    V_GL_JOB_NAME VARCHAR2(100);
    V_GL_LOCATION_ID NUMBER;
    V_GL_LOCATION_CODE VARCHAR2(100);
    V_GL_ORGANIZATION_ID NUMBER;
    V_GL_POSITION_ID NUMBER;
    V_GL_ASSIGNMENT_ID NUMBER;
    
    V_HCMS_ORG_ID NUMBER; 
    V_HCMS_COMPANY_ID NUMBER;
    V_CODE_COMBINATION_ID NUMBER;
    
    V_DATE_FROM DATE;
    V_DATE_TO DATE;
    
    V_PERSON_CNT NUMBER;
    V_ASSIGNMENT_ID number;
  BEGIN
    
    BEGIN
      SELECT 
        ppa.person_id hcms_person_id,
        ppi.employee_number hcms_npk,
        WJ.JOB_ID HCMS_JOB_ID,
        wji.info_value hcms_job_code,
        wj.job_name hcms_job_name,
        WL.LOCATION_ID HCMS_LOCATION_ID,
        wl.location_code hcms_location_code,
        wl.location_name hcms_location_name,
        bc.company_name hcms_company_name,
        pap.person_id ga_person_id,
        haou.organization_id ga_organization_id,
        PPA.EFFECTIVE_START_DATE,
        PPA.EFFECTIVE_END_DATE,
        PPA.COMPANY_ID,
        PPA.ORGANIZATION_ID
      INTO
        V_HCMS_PERSON_ID,
        V_HCMS_NPK,
        V_HCMS_JOB_ID,
        V_HCMS_JOB_CODE,
        V_HCMS_JOB_NAME,
        V_HCMS_LOCATION_ID,
        V_HCMS_LOCATION_CODE,
        V_HCMS_LOCATION_NAME,
        V_HCMS_COMPANY_NAME,
        V_GL_PERSON_ID,
        V_GL_ORGANIZATION_ID,
        V_DATE_FROM,
        V_DATE_TO,
        V_HCMS_COMPANY_ID,
        V_HCMS_ORG_ID
      FROM pea_primary_assignments PPA
      JOIN pea_personal_informations ppi on ppa.person_id = ppi.person_id and ppa.company_id = ppi.company_id and ppa.effective_start_date between ppi.effective_start_date and ppi.effective_end_date
      join wos_jobs wj on wj.job_id = ppa.job_id
      join wos_job_versions wjv on wjv.job_id = wj.job_id and ppa.effective_start_date between wjv.date_from and wjv.date_to
      join wos_organization_versions wov on wov.organization_id = ppa.organization_id and ppa.effective_start_date between wov.date_from and wov.date_to
      join wos_locations wl on wl.location_id = wov.location_id
      join bse_companies bc on bc.company_id = ppa.company_id and ppa.effective_start_date between bc.effective_start_date and bc.effective_end_date
      left join wos_job_infos wji on wji.version_id = wjv.version_id and wji.info_id = 6
      left join hr.per_all_people_f@gl.us.oracle.com pap on pap.employee_number = ppi.employee_number and ppa.effective_start_date between pap.effective_start_date and pap.effective_end_date
      left join hr.hr_all_organization_units@gl.us.oracle.com haou on haou.name = bc.company_name
      WHERE PPA.PERSON_ID = P_HCMS_PERSON_ID
      AND P_PROCESS_DATE BETWEEN PPA.EFFECTIVE_START_DATE AND PPA.EFFECTIVE_END_DATE;
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN TRANSFER EMPLOYEE, FAILED TO QUERY DATA FROM pea_primary_assignments, PERSON_ID '||P_HCMS_PERSON_ID;
        RAISE EXCEPT_OTHER;
    
    END;
    
    IF V_GL_ORGANIZATION_ID IS NULL THEN
      V_ERROR_MSG := 'ERROR WHEN CREATE ASSIGNMENT.COULD NOT FIND ORGANIZATION_ID FROM GA SYS, ORGANIZATION_NAME '||V_HCMS_COMPANY_NAME;
      RAISE EXCEPT_OTHER;
    END IF;
    
    IF V_GL_PERSON_ID IS NULL THEN
      CREATE_PEOPLE(V_HCMS_PERSON_ID, V_GL_PERSON_ID);
    END IF;
    
    SELECT COUNT(1) INTO V_PERSON_CNT FROM PEA_PEOPLE WHERE PERSON_ID = P_HCMS_PERSON_ID;
    
    IF V_PERSON_CNT > 0 THEN
      CREATE_JOB(V_HCMS_JOB_ID, FALSE, V_GL_JOB_ID); --CREATE JOB FOR NPK
    ELSE
      CREATE_JOB(V_HCMS_JOB_ID, TRUE, V_GL_JOB_ID); --CREATE JOB FOR CWK
    END IF;
    
    CREATE_LOCATION(V_HCMS_LOCATION_ID, V_GL_LOCATION_ID);
    CREATE_POSITION(V_HCMS_JOB_ID, V_HCMS_LOCATION_ID, V_GL_JOB_ID, V_GL_LOCATION_ID, V_GL_POSITION_ID);
    
    V_CODE_COMBINATION_ID := GET_CODE_COMBINATION(V_GL_PERSON_ID, V_HCMS_ORG_ID, V_HCMS_COMPANY_ID);
    
    --INSERT NEW HISTORY INTO per_all_assignments_f
    
    V_DATE_FROM := P_PROCESS_DATE;
    V_DATE_TO := TO_DATE('31-DEC-4712','DD-MON-YYYY');
    
    BEGIN
      FOR CUR_A IN CUR_ASSIGNMENT(V_GL_PERSON_ID)
      LOOP
        UPDATE HR.per_all_assignments_f@GL.US.ORACLE.COM
        SET EFFECTIVE_END_DATE = P_PROCESS_DATE - 1
        WHERE ASSIGNMENT_ID = CUR_A.ASSIGNMENT_ID;
        
        SELECT MAX(ASSIGNMENT_ID) + 1 INTO V_ASSIGNMENT_ID FROM HR.per_all_assignments_f@GL.US.ORACLE.COM;
        
        INSERT INTO HR.per_all_assignments_f@GL.US.ORACLE.COM(
          ASSIGNMENT_ID,
          EFFECTIVE_START_DATE,
          EFFECTIVE_END_DATE,
          BUSINESS_GROUP_ID,
          PRIMARY_FLAG,
          PERSON_ID,
          ORGANIZATION_ID,
          ASSIGNMENT_SEQUENCE,
          ASSIGNMENT_TYPE,
          ASSIGNMENT_STATUS_TYPE_ID,
          PO_HEADER_ID,
          PO_LINE_ID,
          PROJECTED_ASSIGNMENT_END,
          GRADE_LADDER_PGM_ID,
          SUPERVISOR_ASSIGNMENT_ID,
          PROGRAM_APPLICATION_ID,
          PROGRAM_ID,
          PROGRAM_UPDATE_DATE,
          ASS_ATTRIBUTE_CATEGORY,
          ASS_ATTRIBUTE1,
          ASS_ATTRIBUTE2,
          ASS_ATTRIBUTE3,
          ASS_ATTRIBUTE4,
          ASS_ATTRIBUTE5,
          ASS_ATTRIBUTE6,
          ASS_ATTRIBUTE7,
          ASS_ATTRIBUTE8,
          ASS_ATTRIBUTE9,
          ASS_ATTRIBUTE10,
          ASS_ATTRIBUTE11,
          ASS_ATTRIBUTE12,
          ASS_ATTRIBUTE13,
          ASS_ATTRIBUTE14,
          ASS_ATTRIBUTE15,
          ASS_ATTRIBUTE16,
          ASS_ATTRIBUTE17,
          ASS_ATTRIBUTE18,
          ASS_ATTRIBUTE19,
          ASS_ATTRIBUTE20,
          ASS_ATTRIBUTE21,
          ASS_ATTRIBUTE22,
          ASS_ATTRIBUTE23,
          ASS_ATTRIBUTE24,
          ASS_ATTRIBUTE25,
          ASS_ATTRIBUTE26,
          ASS_ATTRIBUTE27,
          ASS_ATTRIBUTE28,
          ASS_ATTRIBUTE29,
          ASS_ATTRIBUTE30,
          LAST_UPDATE_DATE,
          LAST_UPDATED_BY,
          LAST_UPDATE_LOGIN,
          CREATED_BY,
          CREATION_DATE,
          TITLE,
          NOTICE_PERIOD,
          NOTICE_PERIOD_UOM,
          EMPLOYEE_CATEGORY,
          WORK_AT_HOME,
          JOB_POST_SOURCE_NAME,
          POSTING_CONTENT_ID,
          PERIOD_OF_PLACEMENT_DATE_START,
          VENDOR_ID,
          VENDOR_EMPLOYEE_NUMBER,
          VENDOR_ASSIGNMENT_NUMBER,
          ASSIGNMENT_CATEGORY,
          PROJECT_TITLE,
          APPLICANT_RANK,
          VENDOR_SITE_ID,
          RECRUITER_ID,
          GRADE_ID,
          POSITION_ID,
          JOB_ID,
          PAYROLL_ID,
          LOCATION_ID,
          PERSON_REFERRED_BY_ID,
          SUPERVISOR_ID,
          SPECIAL_CEILING_STEP_ID,
          RECRUITMENT_ACTIVITY_ID,
          SOURCE_ORGANIZATION_ID,
          PEOPLE_GROUP_ID,
          SOFT_CODING_KEYFLEX_ID,
          VACANCY_ID,
          PAY_BASIS_ID,
          APPLICATION_ID,
          ASSIGNMENT_NUMBER,
          CHANGE_REASON,
          COMMENT_ID,
          DATE_PROBATION_END,
          DEFAULT_CODE_COMB_ID,
          EMPLOYMENT_CATEGORY,
          FREQUENCY,
          INTERNAL_ADDRESS_LINE,
          MANAGER_FLAG,
          NORMAL_HOURS,
          PERF_REVIEW_PERIOD,
          PERF_REVIEW_PERIOD_FREQUENCY,
          PERIOD_OF_SERVICE_ID,
          PROBATION_PERIOD,
          PROBATION_UNIT,
          SAL_REVIEW_PERIOD,
          SAL_REVIEW_PERIOD_FREQUENCY,
          SET_OF_BOOKS_ID,
          SOURCE_TYPE,
          TIME_NORMAL_FINISH,
          TIME_NORMAL_START,
          BARGAINING_UNIT_CODE,
          LABOUR_UNION_MEMBER_FLAG,
          HOURLY_SALARIED_CODE,
          CONTRACT_ID,
          COLLECTIVE_AGREEMENT_ID,
          CAGR_ID_FLEX_NUM,
          CAGR_GRADE_DEF_ID,
          ESTABLISHMENT_ID,
          REQUEST_ID
        ) VALUES (
          V_ASSIGNMENT_ID,
          V_DATE_FROM,
          V_DATE_TO,
          CUR_A.BUSINESS_GROUP_ID,
          CUR_A.PRIMARY_FLAG,
          CUR_A.PERSON_ID,
          CUR_A.ORGANIZATION_ID,
          CUR_A.ASSIGNMENT_SEQUENCE,
          CUR_A.ASSIGNMENT_TYPE,
          CUR_A.ASSIGNMENT_STATUS_TYPE_ID,
          CUR_A.PO_HEADER_ID,
          CUR_A.PO_LINE_ID,
          CUR_A.PROJECTED_ASSIGNMENT_END,
          CUR_A.GRADE_LADDER_PGM_ID,
          CUR_A.SUPERVISOR_ASSIGNMENT_ID,
          CUR_A.PROGRAM_APPLICATION_ID,
          CUR_A.PROGRAM_ID,
          CUR_A.PROGRAM_UPDATE_DATE,
          CUR_A.ASS_ATTRIBUTE_CATEGORY,
          CUR_A.ASS_ATTRIBUTE1,
          CUR_A.ASS_ATTRIBUTE2,
          CUR_A.ASS_ATTRIBUTE3,
          CUR_A.ASS_ATTRIBUTE4,
          CUR_A.ASS_ATTRIBUTE5,
          CUR_A.ASS_ATTRIBUTE6,
          CUR_A.ASS_ATTRIBUTE7,
          CUR_A.ASS_ATTRIBUTE8,
          CUR_A.ASS_ATTRIBUTE9,
          CUR_A.ASS_ATTRIBUTE10,
          CUR_A.ASS_ATTRIBUTE11,
          CUR_A.ASS_ATTRIBUTE12,
          CUR_A.ASS_ATTRIBUTE13,
          CUR_A.ASS_ATTRIBUTE14,
          CUR_A.ASS_ATTRIBUTE15,
          CUR_A.ASS_ATTRIBUTE16,
          CUR_A.ASS_ATTRIBUTE17,
          CUR_A.ASS_ATTRIBUTE18,
          CUR_A.ASS_ATTRIBUTE19,
          CUR_A.ASS_ATTRIBUTE20,
          CUR_A.ASS_ATTRIBUTE21,
          CUR_A.ASS_ATTRIBUTE22,
          CUR_A.ASS_ATTRIBUTE23,
          CUR_A.ASS_ATTRIBUTE24,
          CUR_A.ASS_ATTRIBUTE25,
          CUR_A.ASS_ATTRIBUTE26,
          CUR_A.ASS_ATTRIBUTE27,
          CUR_A.ASS_ATTRIBUTE28,
          CUR_A.ASS_ATTRIBUTE29,
          CUR_A.ASS_ATTRIBUTE30,
          SYSDATE,
          -1,
          CUR_A.LAST_UPDATE_LOGIN,
          CUR_A.CREATED_BY,
          CUR_A.CREATION_DATE,
          CUR_A.TITLE,
          CUR_A.NOTICE_PERIOD,
          CUR_A.NOTICE_PERIOD_UOM,
          CUR_A.EMPLOYEE_CATEGORY,
          CUR_A.WORK_AT_HOME,
          CUR_A.JOB_POST_SOURCE_NAME,
          CUR_A.POSTING_CONTENT_ID,
          CUR_A.PERIOD_OF_PLACEMENT_DATE_START,
          CUR_A.VENDOR_ID,
          CUR_A.VENDOR_EMPLOYEE_NUMBER,
          CUR_A.VENDOR_ASSIGNMENT_NUMBER,
          CUR_A.ASSIGNMENT_CATEGORY,
          CUR_A.PROJECT_TITLE,
          CUR_A.APPLICANT_RANK,
          CUR_A.VENDOR_SITE_ID,
          CUR_A.RECRUITER_ID,
          CUR_A.GRADE_ID,
          V_GL_POSITION_ID,
          V_GL_JOB_ID,
          CUR_A.PAYROLL_ID,
          V_GL_LOCATION_ID,
          CUR_A.PERSON_REFERRED_BY_ID,
          CUR_A.SUPERVISOR_ID,
          CUR_A.SPECIAL_CEILING_STEP_ID,
          CUR_A.RECRUITMENT_ACTIVITY_ID,
          CUR_A.SOURCE_ORGANIZATION_ID,
          CUR_A.PEOPLE_GROUP_ID,
          CUR_A.SOFT_CODING_KEYFLEX_ID,
          CUR_A.VACANCY_ID,
          CUR_A.PAY_BASIS_ID,
          CUR_A.APPLICATION_ID,
          CUR_A.ASSIGNMENT_NUMBER,
          CUR_A.CHANGE_REASON,
          CUR_A.COMMENT_ID,
          CUR_A.DATE_PROBATION_END,
          V_CODE_COMBINATION_ID,
          CUR_A.EMPLOYMENT_CATEGORY,
          CUR_A.FREQUENCY,
          CUR_A.INTERNAL_ADDRESS_LINE,
          CUR_A.MANAGER_FLAG,
          CUR_A.NORMAL_HOURS,
          CUR_A.PERF_REVIEW_PERIOD,
          CUR_A.PERF_REVIEW_PERIOD_FREQUENCY,
          CUR_A.PERIOD_OF_SERVICE_ID,
          CUR_A.PROBATION_PERIOD,
          CUR_A.PROBATION_UNIT,
          CUR_A.SAL_REVIEW_PERIOD,
          CUR_A.SAL_REVIEW_PERIOD_FREQUENCY,
          CUR_A.SET_OF_BOOKS_ID,
          CUR_A.SOURCE_TYPE,
          CUR_A.TIME_NORMAL_FINISH,
          CUR_A.TIME_NORMAL_START,
          CUR_A.BARGAINING_UNIT_CODE,
          CUR_A.LABOUR_UNION_MEMBER_FLAG,
          CUR_A.HOURLY_SALARIED_CODE,
          CUR_A.CONTRACT_ID,
          CUR_A.COLLECTIVE_AGREEMENT_ID,
          CUR_A.CAGR_ID_FLEX_NUM,
          CUR_A.CAGR_GRADE_DEF_ID,
          CUR_A.ESTABLISHMENT_ID,
          CUR_A.REQUEST_ID
        );
      END LOOP;
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN TERMINATE EMPLOYEE. FAILED INSERTING NEW HISTORY INTO per_all_assignments_f. CAUSED BY '||SUBSTR(SQLERRM,1,4000);
        RAISE EXCEPT_OTHER;   
    END;
  END TRANSFER_EMPLOYEE;
  
--  FUNCTION MEANING_RESOLVER(P_CODE IN VARCHAR2, P_MAX_LENGTH IN NUMBER, P_SOURCE IN VARCHAR2) RETURN VARCHAR2
--  IS
--    V_VALID_CODE VARCHAR2(100);
--    v_array apex_application_global.vc_arr2;
--    V_REPLACEMENT VARCHAR2(255);
--  BEGIN
--    V_VALID_CODE := '';
--    v_array := apex_util.string_to_table(UPPER(P_CODE),' ');
--    for i in 1..v_array.count
--    loop
--      BEGIN
--        SELECT REPLACEMENT INTO V_REPLACEMENT FROM syn_word_dictionary WHERE word = UPPER(v_array(i));
--        
--        V_VALID_CODE := V_VALID_CODE||' '||UPPER(V_REPLACEMENT);
--        
--      EXCEPTION
--        WHEN NO_DATA_FOUND THEN
--           V_VALID_CODE := V_VALID_CODE||' '||UPPER(V_ARRAY(i));
--      END;
--    end loop;
--    
--    V_VALID_CODE := TRIM(V_VALID_CODE);
--    
--    IF LENGTH(V_VALID_CODE) > P_MAX_LENGTH THEN
--      V_ERROR_MSG := 'ERROR WHEN CREATE '||P_SOURCE||', CANNOT RESOLVE LENGTH. '||P_SOURCE||'_CODE '||V_VALID_CODE||'. PLEASE ADD WORD REPLACEMENT INTO WORD_DICTIONARY RELATED WITH THE '||P_SOURCE||' CODE';
--      RAISE EXCEPT_OTHER;
--    END IF;
--    
--    RETURN V_VALID_CODE;
--  END MEANING_RESOLVER;
  
  
  PROCEDURE CREATE_POSITION_HIERARCHY(P_MSG_OUT OUT VARCHAR2)
  IS
    V_PR_POSITION_STRUCTURE_ID NUMBER;
    V_PO_POSITION_STRUCTURE_ID NUMBER;
  BEGIN
    dbms_output.put_line('---Start Execution Position Hierarchy HO-----------');
    CREATE_POS_HIER_HO('FIF_ORGANIZATION_HIERARCHY');
    CREATE_POS_HIER_HO('AMF_ORGANIZATION_HIERARCHY');
    
    dbms_output.put_line('---Start Execution Position Hierarchy BRANCH-----------');
    CREATE_POS_HIER_BRANCH('FIF_ORGANIZATION_HIERARCHY');
    CREATE_POS_HIER_BRANCH('AMF_ORGANIZATION_HIERARCHY');
    
    
    CREATE_NEW_POS_VERSION('FIF PR Hierarchy');
    CREATE_NEW_POS_VERSION('FIF PO Hierarchy (Without PR)');
   
    COMMIT;
    P_MSG_OUT := 'OK';
    
  EXCEPTION
    WHEN EXCEPT_OTHER THEN
      ROLLBACK;
      P_MSG_OUT := V_ERROR_MSG;
    WHEN OTHERS THEN
      ROLLBACK;
      V_ERROR_MSG := SUBSTR(SQLERRM,1,3500);
  END CREATE_POSITION_HIERARCHY;
  
  PROCEDURE CREATE_NEW_POS_VERSION(P_POS_HIER_NAME IN VARCHAR2)
  IS
    CURSOR CUR_HCMS_HIER IS
      SELECT 
        PARENT_ID,
        POSITION_ID
      FROM SYN_GASYS_POS_HIER;
    
    V_POSITION_STRUCTURE_ID NUMBER;
    V_LAST_VERSION_ID NUMBER;
    V_POS_STRUCTURE_VERSION_ID NUMBER;
    V_OBJ_VERSION_NUMBER NUMBER;
    V_NEXT_VERSION_NUMBER NUMBER;
    V_BUSINESS_GROUP_ID NUMBER;
  BEGIN
    
    BEGIN
      SELECT POSITION_STRUCTURE_ID INTO V_POSITION_STRUCTURE_ID FROM PER_POSITION_STRUCTURES@GL.US.ORACLE.COM WHERE NAME = 'FIF PO Hierarchy (Without PR)';
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        V_ERROR_MSG := P_POS_HIER_NAME||' IS NOT FOUND';
        RAISE EXCEPT_OTHER;
    END;
    
    SELECT MAX(POS_STRUCTURE_VERSION_ID) INTO V_LAST_VERSION_ID  FROM PER_POS_STRUCTURE_VERSIONS@GL.US.ORACLE.COM WHERE POSITION_STRUCTURE_ID = V_POSITION_STRUCTURE_ID;
    
    --INSERT NEW VERSION
    BEGIN
      
      SELECT MAX(POS_STRUCTURE_VERSION_ID), MAX(OBJECT_VERSION_NUMBER), MAX(VERSION_NUMBER) INTO V_POS_STRUCTURE_VERSION_ID, V_OBJ_VERSION_NUMBER, V_NEXT_VERSION_NUMBER FROM PER_POS_STRUCTURE_VERSIONS@GL.US.ORACLE.COM;
      SELECT ORGANIZATION_ID INTO V_BUSINESS_GROUP_ID FROM HR_ALL_ORGANIZATION_UNITS@GL.US.ORACLE.COM WHERE NAME = 'FIF Business Group';
      
      V_POS_STRUCTURE_VERSION_ID := V_POS_STRUCTURE_VERSION_ID + 1;
      V_OBJ_VERSION_NUMBER := V_OBJ_VERSION_NUMBER + 1;
      V_NEXT_VERSION_NUMBER := V_NEXT_VERSION_NUMBER +1;
    
      INSERT INTO PER_POS_STRUCTURE_VERSIONS@GL.US.ORACLE.COM(
        POS_STRUCTURE_VERSION_ID,
        OBJECT_VERSION_NUMBER,
        VERSION_NUMBER,
        DATE_FROM,
        POSITION_STRUCTURE_ID,
        BUSINESS_GROUP_ID
      )VALUES(
        V_POS_STRUCTURE_VERSION_ID,
        V_OBJ_VERSION_NUMBER,
        V_NEXT_VERSION_NUMBER,
        TRUNC(SYSDATE + 1),
        V_POSITION_STRUCTURE_ID,
        V_BUSINESS_GROUP_ID
      );
    
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN INSERTING TO PER_POS_STRUCTURE_VERSIONS, CAUSED BY '||SUBSTR(SQLERRM, 1, 3500);
        RAISE EXCEPT_OTHER;
    END;
    
    --END DATE PREVIOUS VERSIONS
    BEGIN
      UPDATE PER_POS_STRUCTURE_VERSIONS@GL.US.ORACLE.COM
      SET DATE_TO = TRUNC(SYSDATE)
      WHERE POS_STRUCTURE_VERSION_ID = V_LAST_VERSION_ID;
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN UPDATING PER_POS_STRUCTURE_VERSIONS, CAUSED BY '||SUBSTR(SQLERRM, 1, 3500);
        RAISE EXCEPT_OTHER;
    END;
    
    --INSERT ELEMENT
    BEGIN
      FOR CUR_E IN CUR_HCMS_HIER
      LOOP
        
        INSERT INTO PER_POS_STRUCTURE_ELEMENTS@GL.US.ORACLE.COM(
          POS_STRUCTURE_VERSION_ID,
          BUSINESS_GROUP_ID,
          POS_STRUCTURE_ELEMENT_ID,
          PARENT_POSITION_ID,
          SUBORDINATE_POSITION_ID
        ) VALUES (
          V_POS_STRUCTURE_VERSION_ID,
          V_BUSINESS_GROUP_ID,
          (SELECT MAX(POS_STRUCTURE_ELEMENT_ID) + 1 FROM PER_POS_STRUCTURE_ELEMENTS@GL.US.ORACLE.COM),
          CUR_E.PARENT_ID,
          CUR_E.POSITION_ID
        );
      
      END LOOP;
    EXCEPTION
      WHEN OTHERS THEN
        V_ERROR_MSG := 'ERROR WHEN INSERTING INTO PER_POS_STRUCTURE_ELEMENTS, CAUSED BY '||SUBSTR(SQLERRM, 1, 3500);
        RAISE EXCEPT_OTHER;
    
    END;
  
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      RAISE EXCEPT_OTHER;
    
  END CREATE_NEW_POS_VERSION;
  
  PROCEDURE CREATE_POS_HIER_BRANCH(P_HCMS_HIER_NAME IN VARCHAR2)
  IS
    CURSOR CUR_BRANCH(P_ORG_HIER_NAME IN VARCHAR2) IS
    SELECT distinct
      WOHE.PARENT_ID,
      WO_P.ORGANIZATION_NAME PARENT,
      wov_p.head_of_organization parent_hoo_id,
      wov_p.location_id parent_location_id,
      WOHE.ORGANIZATION_ID,
      WO_C.ORGANIZATION_NAME,
      BHV.BRANCH_NAME,
      WJ.JOB_ID HOO_JOB_ID,
      WJ.JOB_NAME HOO_BRANCH,
      WL.LOCATION_ID HOO_LOCATION_ID,
      WL.LOCATION_NAME
    FROM WOS_ORGANIZATION_HIER WOH
    JOIN WOS_ORG_HIER_VERSIONS WOHV ON WOH.ORG_HIER_ID = WOHV.ORG_HIER_ID and sysdate BETWEEN WOHV.DATE_FROM and WOHV.DATE_TO
    JOIN WOS_ORG_HIER_ELEMENTS WOHE ON WOHE.VERSION_ID = WOHV.VERSION_ID
    join BRANCH_HIERARCHY_V bhv on BHV.ORGANIZATION_ID = WOHE.ORGANIZATION_ID
    JOIN WOS_ORGANIZATIONS WO_P ON WO_P.ORGANIZATION_ID = WOHE.parent_id
    JOIN WOS_ORGANIZATION_VERSIONS WOV_P ON WOV_P.ORGANIZATION_ID = WOHE.PARENT_ID AND SYSDATE BETWEEN WOV_P.DATE_FROM AND WOV_P.DATE_TO
    JOIN WOS_ORGANIZATIONS WO_C ON WO_C.ORGANIZATION_ID = WOHE.ORGANIZATION_ID
    JOIN WOS_ORGANIZATION_VERSIONS WOV_B ON WOV_B.ORGANIZATION_ID = BHV.BRANCH_ID and sysdate between wov_b.date_from and wov_b.date_to
    JOIN WOS_LOCATIONS WL ON WL.LOCATION_ID = WOV_B.LOCATION_ID
    join wos_jobs wj on wj.job_id = WOV_B.HEAD_OF_ORGANIZATION
    WHERE WOH.ORG_HIER_NAME = P_ORG_HIER_NAME 
    and UPPER(BHV.BRANCH_NAME) <> 'HEAD OFFICE'
    ORDER BY WO_C.ORGANIZATION_NAME ASC;
  
    CURSOR CUR_CHILD(P_ORG_ID IN NUMBER) IS
    SELECT DISTINCT
      WOV.LOCATION_ID,
      PPA.JOB_ID
    FROM PEA_PRIMARY_ASSIGNMENTS PPA
    JOIN WOS_ORGANIZATION_VERSIONS WOV ON WOV.ORGANIZATION_ID = PPA.ORGANIZATION_ID
    WHERE PPA.ORGANIZATION_ID = P_ORG_ID
    AND SYSDATE BETWEEN PPA.EFFECTIVE_START_DATE AND PPA.EFFECTIVE_END_DATE;
  
  V_GA_HOO_POSITION_ID NUMBER;
  V_GA_LOCATION_ID NUMBER;
  V_GA_JOB_ID NUMBER;
  V_GA_POSITION_ID NUMBER;
  V_POS_HIER_CNT NUMBER;
  
  BEGIN
    
    FOR CUR_B IN CUR_BRANCH(P_HCMS_HIER_NAME)
    LOOP
        --DEFINE POSITION HOO BRANCH
        --dbms_output.put_line('2. Getting HOO position');
        V_GA_HOO_POSITION_ID := GET_POSITION(CUR_B.HOO_LOCATION_ID, CUR_B.HOO_JOB_ID);
        
        FOR CUR_C IN CUR_CHILD(CUR_B.ORGANIZATION_ID)
        LOOP
          --dbms_output.put_line('2. Getting position');
          V_GA_POSITION_ID := GET_POSITION(CUR_C.LOCATION_ID, CUR_C.JOB_ID);
          if v_ga_position_id = v_ga_hoo_position_id then
             V_GA_HOO_POSITION_ID := GET_POSITION(CUR_B.PARENT_LOCATION_ID, CUR_B.PARENT_HOO_ID);
          end if;
            
          SELECT COUNT(1) INTO V_POS_HIER_CNT FROM SYN_GASYS_POS_HIER WHERE POSITION_ID = V_GA_POSITION_ID;
          IF V_POS_HIER_CNT = 0 THEN
              insert into syn_gasys_pos_hier(
                PARENT_ID, POSITION_ID, CREATION_DATE, LAST_UPDATE_DATE 
              )values(
                V_GA_HOO_POSITION_ID, V_GA_POSITION_ID, SYSDATE, SYSDATE
              );
          END IF;
          
          V_GA_LOCATION_ID := NULL;
          V_GA_JOB_ID := NULL;
          V_GA_POSITION_ID := NULL;
          
        END LOOP;

        V_GA_HOO_POSITION_ID := NULL;
   
    END LOOP;
  
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      RAISE EXCEPT_OTHER;
    
  END CREATE_POS_HIER_BRANCH;
  
  FUNCTION GET_POSITION(P_HCMS_LOCATION_ID IN NUMBER, P_HCMS_JOB_ID IN NUMBER) RETURN NUMBER
  IS
    V_POSITION_ID NUMBER;
    V_LOCATION_ID NUMBER;
    V_JOB_ID NUMBER;
  BEGIN
    
    CREATE_LOCATION(P_HCMS_LOCATION_ID, V_LOCATION_ID);
    CREATE_JOB(P_HCMS_JOB_ID, FALSE, V_JOB_ID);
    CREATE_POSITION(P_HCMS_JOB_ID, P_HCMS_LOCATION_ID, V_JOB_ID, V_LOCATION_ID, V_POSITION_ID);
    
    RETURN V_POSITION_ID;
  END GET_POSITION;
  
  PROCEDURE CREATE_POS_HIER_HO(P_HCMS_HIER_NAME IN VARCHAR2)
  IS
    CURSOR CUR_HO(P_HCMS_HIER_NAME IN VARCHAR2) IS
    select distinct
      wohe.parent_id parent_org_id,
      wov_p.head_of_organization parent_hoo_id,
      wov_p.location_id parent_location_id,
      wohe.organization_id,
      wov_c.head_of_organization organization_hoo_id,
      wov_c.location_id organization_location_id
    FROM WOS_ORGANIZATION_HIER WOH
    JOIN WOS_ORG_HIER_VERSIONS WOHV ON WOH.ORG_HIER_ID = WOHV.ORG_HIER_ID and sysdate BETWEEN WOHV.DATE_FROM and WOHV.DATE_TO
    JOIN WOS_ORG_HIER_ELEMENTS WOHE ON WOHE.VERSION_ID = WOHV.VERSION_ID 
    join BRANCH_HIERARCHY_V bhv on BHV.ORGANIZATION_ID = WOHE.ORGANIZATION_ID
    join wos_organization_versions wov_p on wov_p.organization_id = wohe.parent_id and sysdate BETWEEN WOV_P.DATE_FROM and WOV_P.DATE_TO
    join wos_organization_versions wov_c on wov_c.organization_id = wohe.organization_id and sysdate BETWEEN WOV_C.DATE_FROM and WOV_C.DATE_TO
    where WOH.ORG_HIER_NAME = P_HCMS_HIER_NAME
    and upper(bhv.branch_name) = upper('Head Office');
  
    CURSOR CUR_CHILD(P_ORG_ID IN NUMBER) IS
    SELECT DISTINCT
      WOV.LOCATION_ID,
      PPA.JOB_ID
    FROM PEA_PRIMARY_ASSIGNMENTS PPA
    JOIN WOS_ORGANIZATION_VERSIONS WOV ON WOV.ORGANIZATION_ID = PPA.ORGANIZATION_ID and sysdate BETWEEN WOV.DATE_FROM and WOV.DATE_TO
    WHERE PPA.ORGANIZATION_ID = P_ORG_ID
    AND SYSDATE BETWEEN PPA.EFFECTIVE_START_DATE AND PPA.EFFECTIVE_END_DATE;
    
    V_GA_HOO_POSITION_ID NUMBER;
    V_GA_LOCATION_ID NUMBER;
    V_GA_JOB_ID NUMBER;
    V_GA_POSITION_ID NUMBER;
    V_POS_HIER_CNT NUMBER;
  
  BEGIN
    FOR CUR_B IN CUR_HO(P_HCMS_HIER_NAME)
    LOOP  
        --dbms_output.put_line('1. Getting HOO position');
        V_GA_HOO_POSITION_ID := GET_POSITION(CUR_B.ORGANIZATION_LOCATION_ID, CUR_B.ORGANIZATION_HOO_ID);
        FOR CUR_C IN CUR_CHILD(CUR_B.ORGANIZATION_ID)
        LOOP
            dbms_output.put_line('2. Getting position');
            V_GA_POSITION_ID := GET_POSITION(CUR_C.LOCATION_ID, CUR_C.JOB_ID);
            if v_ga_position_id = v_ga_hoo_position_id then
              V_GA_HOO_POSITION_ID := GET_POSITION(CUR_B.PARENT_LOCATION_ID, CUR_B.PARENT_HOO_ID);
            end if;
            
            SELECT COUNT(1) INTO V_POS_HIER_CNT FROM SYN_GASYS_POS_HIER WHERE POSITION_ID = V_GA_POSITION_ID;
            IF V_POS_HIER_CNT = 0 THEN
                insert into syn_gasys_pos_hier(
                  PARENT_ID, POSITION_ID, CREATION_DATE, LAST_UPDATE_DATE 
                )values(
                  V_GA_HOO_POSITION_ID, V_GA_POSITION_ID, SYSDATE, SYSDATE
                );
            END IF;     
          
          V_GA_LOCATION_ID := NULL;
          V_GA_JOB_ID := NULL;
          V_GA_POSITION_ID := NULL;
          
        END LOOP;
         
        V_GA_HOO_POSITION_ID := NULL;
   
    END LOOP;
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      RAISE EXCEPT_OTHER;
  END CREATE_POS_HIER_HO;
  
  FUNCTION GET_CODE_COMBINATION(P_GA_PERSON_ID IN NUMBER, P_HCMS_ORG_ID IN NUMBER, P_HCMS_COMPANY_ID IN NUMBER) RETURN NUMBER
  IS
    V_SEGMENT1 VARCHAR2(10);
    V_SEGMENT2 VARCHAR2(10);
    V_SEGMENT3 VARCHAR2(10);
    V_SEGMENT4 VARCHAR2(10);
    V_SEGMENT5 VARCHAR2(10);
    V_SEGMENT6 VARCHAR2(10);
    V_SEGMENT7 VARCHAR2(10);
    V_SEGMENT8 VARCHAR2(10);
    
    V_CODE_COMBINATION_ID NUMBER;
    V_CHART_OF_ACCOUNT_ID NUMBER;
  BEGIN
    
    v_segment4 := '70110101';
    v_segment5 := '0000';
    v_segment6 := '00';
    v_segment7 := '00000';
    v_segment8 := '00';
    
    /**
    GET GL COMPANY CODE / SEGMENT1
    **/
    begin
      select bc.gl_code 
      into V_SEGMENT1
      from bse_companies bc
      where bc.company_id = p_hcms_company_id
      and sysdate between bc.effective_start_date and bc.effective_end_date;
    exception
      when no_data_found then
        v_error_msg := 'NO COMPANY FOUND, COMPANY_ID '||p_hcms_org_id;
        raise except_other;
    end;
    
    if V_SEGMENT1 is null then
      v_error_msg := 'COMPANY GL CODE IS NULL, COMPANY_ID '||p_hcms_org_id;
      raise except_other;
    end if;
    
     /**
    GET GL BRANCH CODE / SEGMENT2
    **/
    begin
      select gl_code into V_segment2 from branch_hierarchy_v where organization_id = p_hcms_org_id;
    exception
      when no_data_found then
        v_error_msg := 'CANNOT FIND BRANCH FOR ORGANIZATION ID '||P_HCMS_ORG_ID;
        RAISE EXCEPT_OTHER;
    end;
    
    if v_segment2 is null then
      v_error_msg := 'BRANCH GL CODE IS NULL, ORGANIZATION_ID '||p_hcms_org_id;
      raise except_other;
    end if;
    
     /**
    GET COST CENTER / SEGMENT3
    **/
    
    begin
      select cost_center_code 
      into v_segment3
      from wos_organization_versions where trunc(sysdate) between date_from and date_to and organization_id = p_hcms_org_id;
     exception
      when no_data_found then
        v_error_msg := 'NO ORGANIZATION FOUND, ORGANIZATION_ID '||P_HCMS_ORG_ID;
        RAISE EXCEPT_OTHER;
    end;
    
    if v_segment3 is null then
      v_error_msg := 'COST CENTER IS NULL, ORGANIZATION_ID '||p_hcms_org_id;
      raise except_other;
    end if;
    
    begin
      select code_combination_id 
      into v_code_combination_id
      from GL_CODE_COMBINATIONS@GL.US.ORACLE.COM
      where 
        segment1 = v_segment1
        and segment2 = v_segment2
        and segment3 = v_segment3
        and segment4 = v_segment4
        and segment5 = v_segment5
        and segment6 = v_segment6
        and segment7 = v_segment7
        and segment8 = v_segment8;
    exception
      when no_data_found then
        begin
          SELECT GL_CODE_COMBINATIONS_S.NEXTVAL@GL.US.ORACLE.COM INTO V_CODE_COMBINATION_ID FROM DUAL;
          
          SELECT CHART_OF_ACCOUNTS_ID INTO V_CHART_OF_ACCOUNT_ID FROM gl_sets_of_books@GL.US.ORACLE.COM WHERE SHORT_NAME = 'FIF_SOB';
        
          INSERT INTO GL_CODE_COMBINATIONS@GL.US.ORACLE.COM(
            CODE_COMBINATION_ID,
            LAST_UPDATE_DATE,
            LAST_UPDATED_BY,
            CHART_OF_ACCOUNTS_ID,
            DETAIL_POSTING_ALLOWED_FLAG,
            DETAIL_BUDGETING_ALLOWED_FLAG,
            ACCOUNT_TYPE,
            ENABLED_FLAG,
            SUMMARY_FLAG,
            SEGMENT1,
            SEGMENT2,
            SEGMENT3,
            SEGMENT4,
            SEGMENT5,
            SEGMENT6,
            SEGMENT7,
            SEGMENT8
          ) VALUES (
            V_CODE_COMBINATION_ID,
            SYSDATE,
            -1,
            V_CHART_OF_ACCOUNT_ID,
            'Y',
            'Y',
            'E',
            'Y',
            'N',
            v_segment1,
            v_segment2,
            v_segment3,
            v_segment4,
            v_segment5,
            v_segment6,
            v_segment7,
            v_segment8
          );
        
        exception
          when others then
            v_error_msg := 'ERROR WHEN INSERTING TO GL_CODE_COMBINATIONS, CAUSED BY '||SUBSTR(SQLERRM,1,3500);
            RAISE EXCEPT_OTHER;
         
        end;
      when others then
        v_error_msg := 'ERROR WHEN QUERYING TO GL_CODE_COMBINATIONS, CAUSED BY '||SUBSTR(SQLERRM,1,3500);
        RAISE EXCEPT_OTHER;
    end;
    
        
    RETURN V_CODE_COMBINATION_ID;
  END GET_CODE_COMBINATION;
  
   FUNCTION PROCESS_TRANSFER(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2
   AS
    V_RESULT VARCHAR(4000);
   BEGIN
      TRANSFER_EMPLOYEE(P_HCMS_PERSON_ID, P_PROCESS_DATE); 
      V_RESULT := 'OK';
      RETURN V_RESULT;
   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
   END PROCESS_TRANSFER;
   
   FUNCTION PROCESS_HIRE(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2
   AS
    V_RESULT VARCHAR(4000);
   BEGIN
      NEW_HIRE(P_HCMS_PERSON_ID, P_PROCESS_DATE); 
      V_RESULT := 'OK';
      RETURN V_RESULT;
   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
   END PROCESS_HIRE;
  
   FUNCTION PROCESS_TERMINATION(P_HCMS_PERSON_ID IN NUMBER, P_PROCESS_DATE IN DATE) RETURN VARCHAR2
   AS
    V_RESULT VARCHAR(4000);
   BEGIN
      TERMINATE_EMPLOYEE(P_HCMS_PERSON_ID, P_PROCESS_DATE); 
      V_RESULT := 'OK';
      RETURN V_RESULT;
   EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
   END PROCESS_TERMINATION;
   
   
  FUNCTION PROCESS_POS_HIER_BRANCH(P_HCMS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2
  AS
    V_RESULT VARCHAR(4000);
  BEGIN
    CREATE_POS_HIER_BRANCH(P_HCMS_HIER_NAME);
    V_RESULT := 'OK';
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
  END PROCESS_POS_HIER_BRANCH;
  
  FUNCTION PROCESS_POS_HIER_HO(P_HCMS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2
  AS
    V_RESULT VARCHAR(4000);
  BEGIN
    CREATE_POS_HIER_HO(P_HCMS_HIER_NAME);
    V_RESULT := 'OK';
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
  END PROCESS_POS_HIER_HO;
  
  FUNCTION PROCESS_NEW_POS_VERSION(P_POS_HIER_NAME IN VARCHAR2) RETURN VARCHAR2
  AS
    V_RESULT VARCHAR(4000);
  BEGIN
    CREATE_NEW_POS_VERSION(P_POS_HIER_NAME);
    V_RESULT := 'OK';
  EXCEPTION
    WHEN OTHERS THEN
      ROLLBACK;
      V_RESULT := V_ERROR_MSG;
      RETURN V_RESULT;
  END PROCESS_NEW_POS_VERSION;
   
END FIF_SYNC_TO_GASYS;
/
