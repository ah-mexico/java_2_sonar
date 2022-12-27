prompt jobLimpiezaRelaciones

DECLARE
   job_num NUMBER;
BEGIN
  SYS.DBMS_JOB.SUBMIT (job        => job_num
                       ,what       => 'PCK_LOGINADMIN_JOBS.SP_LIMPIEZA_CREADOS;'
                       ,next_date  => to_date(trunc(SYSDATE) ||' 23:00:00','dd/mm/yyyy hh24:mi:ss')
                       ,interval   => 'SYSDATE + 1'
                       ,no_parse   => FALSE
                       );
  
  dbms_output.put_line('job numero '|| job_num);
  
  SYS.DBMS_JOB.run(job_num);
  
  COMMIT;
                         
END; 
/
 
