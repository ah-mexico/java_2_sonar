CREATE TABLE WS_METODO
(
	id_metodo    NUMBER NOT NULL,    --  identificador de la capacidad 
	id_servicio  NUMBER NOT NULL,    --  identificador del servicio 
	nombre       VARCHAR2(200) NOT NULL,    --  nombre de la capacidad 
	timeout      NUMBER(8) DEFAULT 60 NOT NULL    --  establece el tiempo de espera maximo en segundos para la respuesta de la capacidad 
)
;

COMMENT ON TABLE WS_METODO IS 'registro de capacidades o metodos expuestos por los servicios'
;
COMMENT ON COLUMN WS_METODO.id_metodo    IS 'identificador de la capacidad'
;
COMMENT ON COLUMN WS_METODO.id_servicio  IS 'identificador del servicio'
;
COMMENT ON COLUMN WS_METODO.nombre       IS 'nombre de la capacidad'
;
COMMENT ON COLUMN WS_METODO.timeout      IS 'establece el tiempo de espera maximo en segundos para la respuesta de la capacidad'
;

--  Create Indexes 
CREATE INDEX IDX_WS_METODO_WS_SERVICIO ON WS_METODO
(id_servicio ASC)
;

--  Create Primary Key Constraints 
ALTER TABLE WS_METODO ADD CONSTRAINT PK_WS_METODO 
	PRIMARY KEY (id_metodo) 
 USING INDEX 
;

--  Create Foreign Key Constraints 
ALTER TABLE WS_METODO ADD CONSTRAINT FK_WS_METODO_WS_SERVICIO 
	FOREIGN KEY (id_servicio) REFERENCES WS_SERVICIO (id_servicio)
;



-- Grant/Revoke object privileges 
grant select on WS_METODO to consultar_gaudi;
grant select, insert, update, delete on WS_METODO to permisos_gaudi;
