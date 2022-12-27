CREATE TABLE WS_PROPIEDAD
(
	id_propiedad  NUMBER NOT NULL,    --  identificador de la propiedad 
	nombre        VARCHAR2(500) NOT NULL,    --  nombre de la propiedad 
	opcional      CHAR(1) DEFAULT 'S' NOT NULL,    --  indica si es una propiedad que debe ir solo si el valor es entregado si(S) o no(N) 
	valor         VARCHAR2(500) NOT NULL    --  valor o variable que toma la propiedad 
)
;

COMMENT ON TABLE WS_PROPIEDAD IS 'registro de propiedades de los elementos'
;
COMMENT ON COLUMN WS_PROPIEDAD.id_propiedad  IS 'identificador de la propiedad'
;
COMMENT ON COLUMN WS_PROPIEDAD.nombre        IS 'nombre de la propiedad'
;
COMMENT ON COLUMN WS_PROPIEDAD.opcional      IS 'indica si es una propiedad que debe ir solo si el valor es entregado si(S) o no(N)'
;
COMMENT ON COLUMN WS_PROPIEDAD.valor         IS 'valor o variable que toma la propiedad'
;

--  Create Primary Key Constraints 
ALTER TABLE WS_PROPIEDAD ADD CONSTRAINT PK_WS_PROPIEDAD 
	PRIMARY KEY (id_propiedad) 
 USING INDEX 
;


-- Grant/Revoke object privileges 
grant select on WS_PROPIEDAD to consultar_gaudi;
grant select, insert, update, delete on WS_PROPIEDAD to permisos_gaudi;
