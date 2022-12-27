-- Create table
create table TBUP_HOM_TIPPER
(
  origen           VARCHAR2(50) not null,
  nombre_origen    VARCHAR2(500),
  codigo_ori       VARCHAR2(100) not null,
  codigo_loc       VARCHAR2(100) not null,
  codigo_hom       VARCHAR2(100) not null,
  desc_codigo      VARCHAR2(500)
);

-- Create/Recreate primary, unique and foreign key constraints 
alter table TBUP_HOM_TIPPER
  add primary key (origen,codigo_ori)
  using index ;
--
-- Grant/Revoke object privileges 
grant select on TBUP_HOM_TIPPER to consultar_gaudi;
grant select, insert, update, delete on TBUP_HOM_TIPPER to permisos_gaudi;


--
insert into TBUP_HOM_TIPPER values ('BH','','1','CC','CC','Cédula de Ciudadanía');
insert into TBUP_HOM_TIPPER values ('BH','','4','NI','NI','N.I.T.');
insert into TBUP_HOM_TIPPER values ('BH','','2','CE','CE','Cédula de Extranjería');
insert into TBUP_HOM_TIPPER values ('BH','','6','PA','PA','Pasaporte');
--
commit;
--  
