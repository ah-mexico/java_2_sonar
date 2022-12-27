CREATE TABLE WS_SERVICIO
(
	id_servicio      NUMBER NOT NULL,    --  identificador del servicio 
	nombre           VARCHAR2(200) NOT NULL,    --  nombre del servicio 
	descripcion      VARCHAR2(500),    --  descripcion del servicio 
	aplicacion       VARCHAR2(500) NOT NULL,    --  nombre del proyecto o aplicación al que pretenece 
	url              VARCHAR2(1000) NOT NULL,    --  direccion del servicio 
	content_type     VARCHAR2(500) DEFAULT 'text/xml' NOT NULL,    --  Tipo de contenido del request 
	user_agent       VARCHAR2(500) DEFAULT 'Mozilla/4.0' NOT NULL,    --  Tipo de agente que consume el servicio 
	proxy            VARCHAR2(1000),    --  URL de conexion al proxy del servicio 
	username         VARCHAR2(100),    --  cuenta de usuario para conexion segura 
	password         VARCHAR2(100),    --  clave de usuario para conexion segura 
	path_wallet      VARCHAR2(1500),    --  ruta del certificado para conexiones seguras 
	password_wallet  VARCHAR2(100)    --  clave del certificado para conexiones seguras 
)
;


COMMENT ON TABLE WS_SERVICIO IS 'Servicios SOAP'
;
COMMENT ON COLUMN WS_SERVICIO.id_servicio      IS 'identificador del servicio'
;
COMMENT ON COLUMN WS_SERVICIO.nombre           IS 'nombre del servicio'
;
COMMENT ON COLUMN WS_SERVICIO.descripcion      IS 'descripcion del servicio'
;
COMMENT ON COLUMN WS_SERVICIO.aplicacion       IS 'nombre del proyecto o aplicación al que pretenece'
;
COMMENT ON COLUMN WS_SERVICIO.url              IS 'direccion del servicio'
;
COMMENT ON COLUMN WS_SERVICIO.content_type     IS 'Tipo de contenido del request'
;
COMMENT ON COLUMN WS_SERVICIO.user_agent       IS 'Tipo de agente que consume el servicio'
;
COMMENT ON COLUMN WS_SERVICIO.proxy            IS 'URL de conexion al proxy del servicio'
;
COMMENT ON COLUMN WS_SERVICIO.username         IS 'cuenta de usuario para conexion segura'
;
COMMENT ON COLUMN WS_SERVICIO.password         IS 'clave de usuario para conexion segura'
;
COMMENT ON COLUMN WS_SERVICIO.path_wallet      IS 'ruta del certificado para conexiones seguras'
;
COMMENT ON COLUMN WS_SERVICIO.password_wallet  IS 'clave del certificado para conexiones seguras'
;

--  Create Indexes 
ALTER TABLE WS_SERVICIO
	ADD CONSTRAINT UK_WS_SERVICIO_NOMBRE UNIQUE (nombre)
 USING INDEX 
;

--  Create Primary Key Constraints 
ALTER TABLE WS_SERVICIO ADD CONSTRAINT PK_WS_SERVICIO 
	PRIMARY KEY (id_servicio) 
 USING INDEX 
;

-- Grant/Revoke object privileges 
grant select on WS_SERVICIO to consultar_gaudi;
grant select, insert, update, delete on WS_SERVICIO to permisos_gaudi;
