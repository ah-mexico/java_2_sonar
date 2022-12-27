CREATE TABLE WS_ELEMENTO
(
	id_elemento        NUMBER NOT NULL,    --  identificador del elemento 
	id_metodo          NUMBER NOT NULL,    --  identificador de la capacidad o metodo 
	id_elemento_padre  NUMBER,    --  identificador del elemento padre 
	nombre             VARCHAR2(500) NOT NULL,    --  nombre del elemento 
	minima_ocurrencia  NUMBER(5) DEFAULT 1 NOT NULL,    --  indica la cantidad minima de veces que puede aparecer el elemento 
	maxima_ocurrencia  NUMBER(5) DEFAULT 1,    --  indica la cantidad maxima de veces que puede aparecer el elemento, si es nulo la cantidad es indeterminada 
	nivel              NUMBER(3) NOT NULL,    --  indica la profundidad o nivel del elemento 
	secuencia          NUMBER(5) NOT NULL,    --  indica el orden del elemento como hijo 
	valor              VARCHAR2(4000)    --  valor del elemento o variable a registrar 
)
;

COMMENT ON TABLE WS_ELEMENTO IS 'registro de elementos incluidos en el xml de petición'
;
COMMENT ON COLUMN WS_ELEMENTO.id_elemento        IS 'identificador del elemento'
;
COMMENT ON COLUMN WS_ELEMENTO.id_metodo          IS 'identificador de la capacidad o metodo'
;
COMMENT ON COLUMN WS_ELEMENTO.id_elemento_padre  IS 'identificador del elemento padre'
;
COMMENT ON COLUMN WS_ELEMENTO.nombre             IS 'nombre del elemento'
;
COMMENT ON COLUMN WS_ELEMENTO.minima_ocurrencia  IS 'indica la cantidad minima de veces que puede aparecer el elemento'
;
COMMENT ON COLUMN WS_ELEMENTO.maxima_ocurrencia  IS 'indica la cantidad maxima de veces que puede aparecer el elemento, si es nulo la cantidad es indeterminada'
;
COMMENT ON COLUMN WS_ELEMENTO.nivel              IS 'indica la profundidad o nivel del elemento'
;
COMMENT ON COLUMN WS_ELEMENTO.secuencia          IS 'indica el orden del elemento como hijo'
;
COMMENT ON COLUMN WS_ELEMENTO.valor              IS 'valor del elemento o variable a registrar'
;

--  Create Indexes 
CREATE INDEX IDX_WS_ELEMENTO_WS_ELEMENTO ON WS_ELEMENTO
(id_elemento_padre ASC)
;

CREATE INDEX IDX_WS_ELEMENTO_WS_METODO ON WS_ELEMENTO
(id_metodo ASC)
;

--  Create Primary Key Constraints 
ALTER TABLE WS_ELEMENTO ADD CONSTRAINT PK_WS_ELEMENTO 
	PRIMARY KEY (id_elemento) 
 USING INDEX 
;

--  Create Foreign Key Constraints 
ALTER TABLE WS_ELEMENTO ADD CONSTRAINT FK_WS_ELEMENTO_WS_ELEMENTO 
	FOREIGN KEY (id_elemento_padre) REFERENCES WS_ELEMENTO (id_elemento)
;

ALTER TABLE WS_ELEMENTO ADD CONSTRAINT FK_WS_ELEMENTO_WS_METODO 
	FOREIGN KEY (id_metodo) REFERENCES WS_METODO (id_metodo)
;


-- Grant/Revoke object privileges 
grant select on WS_ELEMENTO to consultar_gaudi;
grant select, insert, update, delete on WS_ELEMENTO to permisos_gaudi;

