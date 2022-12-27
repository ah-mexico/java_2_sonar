CREATE TABLE WS_STATUS
(
	status_code           NUMBER(3) NOT NULL,    --  identificador de estado de respuesta HTTP de las constantes de Oracle 
	nombre                VARCHAR2(200) NOT NULL,    --  nombre del estado 
	descripcion           VARCHAR2(1000),    --  descripcion del estado 
	descripcion_solucion  VARCHAR2(1000)    --  indica la posible solucion o medida en caso que se presente, en el caso que sea una incidencia 
)
;

COMMENT ON TABLE WS_STATUS IS 'Registro de estados de respuesta de consumos de servicios web'
;
COMMENT ON COLUMN WS_STATUS.status_code           IS 'identificador de estado de respuesta HTTP de las constantes de Oracle'
;
COMMENT ON COLUMN WS_STATUS.nombre                IS 'nombre del estado'
;
COMMENT ON COLUMN WS_STATUS.descripcion           IS 'descripcion del estado'
;
COMMENT ON COLUMN WS_STATUS.descripcion_solucion  IS 'indica la posible solucion o medida en caso que se presente, en el caso que sea una incidencia'
;

--  Create Primary Key Constraints 
ALTER TABLE WS_STATUS ADD CONSTRAINT PK_WS_STATUS 
	PRIMARY KEY (status_code) 
 USING INDEX 
;


-- Grant/Revoke object privileges 
grant select on WS_STATUS to consultar_gaudi;
grant select, insert, update, delete on WS_STATUS to permisos_gaudi;
