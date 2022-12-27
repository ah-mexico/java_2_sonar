CREATE TABLE WS_ELEMENTO_PROPIEDAD
(
	id_elemento   NUMBER NOT NULL,    --  identificador del elemento 
	id_propiedad  NUMBER NOT NULL    --  identificador de la propiedad 
)
;

COMMENT ON TABLE WS_ELEMENTO_PROPIEDAD IS 'enlaza los elementos a las propiedades, ya sean comunes o especificos del elemento'
;
COMMENT ON COLUMN WS_ELEMENTO_PROPIEDAD.id_elemento   IS 'identificador del elemento'
;
COMMENT ON COLUMN WS_ELEMENTO_PROPIEDAD.id_propiedad  IS 'identificador de la propiedad'
;

--  Create Indexes 
CREATE INDEX IDX_WS_ELEMENTO_PR_WS_PROPIED ON WS_ELEMENTO_PROPIEDAD
(id_propiedad ASC)
;

CREATE INDEX IDX_WS_ELEMENTO_PRO_WS_ELEMEN ON WS_ELEMENTO_PROPIEDAD
(id_elemento ASC)
;

--  Create Primary Key Constraints 
ALTER TABLE WS_ELEMENTO_PROPIEDAD ADD CONSTRAINT PK_WS_ELEMENTO_PROPIEDAD 
	PRIMARY KEY (id_elemento, id_propiedad) 
 USING INDEX 
;

--  Create Foreign Key Constraints 
ALTER TABLE WS_ELEMENTO_PROPIEDAD ADD CONSTRAINT FK_WS_ELEMENTO_PRO_WS_ELEMENTO 
	FOREIGN KEY (id_elemento) REFERENCES WS_ELEMENTO (id_elemento)
;

ALTER TABLE WS_ELEMENTO_PROPIEDAD ADD CONSTRAINT FK_WS_ELEMENTO_PR_WS_PROPIEDAD 
	FOREIGN KEY (id_propiedad) REFERENCES WS_PROPIEDAD (id_propiedad)
;

-- Grant/Revoke object privileges 
grant select on WS_ELEMENTO_PROPIEDAD to consultar_gaudi;
grant select, insert, update, delete on WS_ELEMENTO_PROPIEDAD to permisos_gaudi;
