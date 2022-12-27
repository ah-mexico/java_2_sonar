/** Script para carga de objetos LOGINADMIN
  *
  * legranados@colsanitas.com
  * 02/01/2013
  * Version 1.0.0
  *
  */
spool LOGINADMIN_afiliado_v1_0_0.log;

def OWNER=&&OWNER;
alter session set current_schema=&&schema;

insert into auth_tb_user
  (user_id,
   user_name,
   user_last_name,
   user_mail,
   blocked_user,
   user_description,
   user_tipo_doc,
   user_document)
values
  ('Afiliado', 'Afiliado', 'Afiliado', null,0,null,null,null);


commit;

SPOOL OFF


