CREATE TABLE WS_PETICION
(
	id_peticion    NUMBER NOT NULL,    --  identificador del consumo o peticion 
	id_servicio    NUMBER NOT NULL,    --  identificador del servicio 
	id_metodo      NUMBER NOT NULL,    --  identificador de la capacidad o metodo 
	usuario        VARCHAR2(200) NOT NULL,    --  usuario o aplicacion que realiza la peticion 
	fecha_ini      DATE DEFAULT SYSDATE NOT NULL,    --  fecha y hora de inicio de la peticion 
	request        XMLTYPE NOT NULL,    --  xml de peticion 
	fecha_fin      DATE,    --  fecha y hora de fin de la peticion 
	response       XMLTYPE,    --  xml de respuesta 
	status_code    NUMBER(3),    --  indica el estado de respuesta de la peticion 
	reason_phrase  VARCHAR2(256)    --  descripcion del estado de la peticion 
)
;

COMMENT ON TABLE WS_PETICION IS 'registro de peticiones a servicios web'
;
COMMENT ON COLUMN WS_PETICION.id_peticion    IS 'identificador del consumo o peticion'
;
COMMENT ON COLUMN WS_PETICION.id_servicio    IS 'identificador del servicio'
;
COMMENT ON COLUMN WS_PETICION.id_metodo      IS 'identificador de la capacidad o metodo'
;
COMMENT ON COLUMN WS_PETICION.usuario        IS 'usuario o aplicacion que realiza la peticion'
;
COMMENT ON COLUMN WS_PETICION.fecha_ini      IS 'fecha y hora de inicio de la peticion'
;
COMMENT ON COLUMN WS_PETICION.request        IS 'xml de peticion'
;
COMMENT ON COLUMN WS_PETICION.fecha_fin      IS 'fecha y hora de fin de la peticion'
;
COMMENT ON COLUMN WS_PETICION.response       IS 'xml de respuesta'
;
COMMENT ON COLUMN WS_PETICION.status_code    IS 'indica el estado de respuesta de la peticion'
;
COMMENT ON COLUMN WS_PETICION.reason_phrase  IS 'descripcion del estado de la peticion'
;

--  Create Indexes 
CREATE INDEX IDX_WS_PETICION_WS_SERVICIO ON WS_PETICION
(id_servicio ASC)
;

CREATE INDEX IDX_WS_PETICION_WS_STATUS ON WS_PETICION
(status_code ASC)
;

CREATE INDEX IDX_WS_PETICION_WS_METODO ON WS_PETICION
(id_metodo ASC)
;

--  Create Primary Key Constraints 
ALTER TABLE WS_PETICION ADD CONSTRAINT PK_WS_PETICION 
	PRIMARY KEY (id_peticion) 
 USING INDEX 
;

--  Create Foreign Key Constraints 
ALTER TABLE WS_PETICION ADD CONSTRAINT FK_WS_PETICION_WS_SERVICIO 
	FOREIGN KEY (id_servicio) REFERENCES WS_SERVICIO (id_servicio)
;

ALTER TABLE WS_PETICION ADD CONSTRAINT FK_WS_PETICION_WS_METODO 
	FOREIGN KEY (id_metodo) REFERENCES WS_METODO (id_metodo)
;

ALTER TABLE WS_PETICION ADD CONSTRAINT FK_WS_PETICION_WS_STATUS 
	FOREIGN KEY (status_code) REFERENCES WS_STATUS (status_code)
;


-- Grant/Revoke object privileges 
grant select on WS_PETICION to consultar_gaudi;
grant select, insert, update, delete on WS_PETICION to permisos_gaudi;
