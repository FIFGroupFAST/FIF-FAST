select 
  pp.person_id "personId",
  pi.full_name "fullName",
  pi.employee_number "employeeNumber",
  pi.marital_status "maritalStatus",
  pa.grade_id "gradeId", 
  pa.organization_id "organizationId", 
  pa.job_id "jobId",
  pa.company_id "companyId",
  pa.assignment_status "assignmentStatus",
  g.grade "grade",
  g.sub_grade "subGrade",
  j.job_code "job",
  jv.people_category_code "peopleCategory",
  jv.job_group_code "jobGroupCode",
  ld.description "jobGroup",
  o.organization_name "organizationName"
from pea_people pp
join pea_people_types pt
  on pp.person_id = pt.person_id
  and #{effectiveOnDate, jdbcType=DATE} between pt.effective_start_date and pt.effective_end_date
join pea_personal_informations pi
  on pi.person_id = pp.person_id
  and pi.company_id = pt.company_id
  and #{effectiveOnDate, jdbcType=DATE} between pi.effective_start_date and pi.effective_end_date
join pea_primary_assignments pa
  on pa.person_id = pp.person_id
  and pa.company_id = pt.company_id
  and #{effectiveOnDate, jdbcType=DATE} between pa.effective_start_date and pa.effective_end_date
join wos_grades g
  on g.grade_id = pa.grade_id
join wos_jobs j
  on j.job_id = pa.job_id
join wos_job_versions jv
  on jv.job_id = pa.job_id
  and #{effectiveOnDate, jdbcType=DATE} between jv.date_from and jv.date_to
left join bse_lookup_hdr lh
  on lh.name = 'WSU_JOB_GROUP'
left join bse_lookup_dependents ld
  on ld.lookup_id = lh.lookup_id
  and ld.detail_code = jv.job_group_code
  and ld.company_scope in (pt.company_id, -1)
join wos_organizations o
  on o.organization_id = pa.organization_id
where pp.person_id = #{personId, jdbcType=NUMERIC}