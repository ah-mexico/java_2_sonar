spool ALTER_LOGINADMIN.log;

def OWNER=&&OWNER;
alter session set current_schema=&&schema;

prompt @@01_ALTER_TABLE_AUTH_TB_USER
@@01_ALTER_TABLE_AUTH_TB_USER.sql

prompt @@01_ALTER_TABLE_AUTH_TB_USER_AUD.sql
@@01_ALTER_TABLE_AUTH_TB_USER_AUD.sql

SPOOL OFF
