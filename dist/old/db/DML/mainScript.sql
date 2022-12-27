/** Script para carga de objetos LOGINADMIN
  *
  * legranados@colsanitas.com
  * 02/01/2013
  * Version 1.0.0
  *
  */
 
set pagesize 10000
set linesize 10000
set serveroutput  on size 1000000
set ARRAYSIZE 2
spool LOGINADMIN_DDL_v12_0_0.log;

def OWNER=&&OWNER;
alter session set current_schema=&&schema;

prompt @@jobLimpiezaRelaciones.sql
@@jobLimpiezaRelaciones.sql

SPOOL OFF
