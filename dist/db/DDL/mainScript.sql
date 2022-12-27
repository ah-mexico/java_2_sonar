/** Script para carga de objetos LOGINADMIN
  *
  * legranados@colsanitas.com
  * 02/01/2013
  * Version 1.0.0
  *
  */
spool LOGINADMIN_DDL_v1_0_0.log;

def OWNER=&&OWNER;
alter session set current_schema=&&schema;

--prompt @@create.sql
--@@create.sql

prompt @@package.sql
@@package.sql

--prompt @@grant.sql
--@@grant.sql

SPOOL OFF
