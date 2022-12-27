INSERT INTO WS_SERVICIO (id_servicio,nombre,aplicacion,url,content_type,user_agent)
VALUES (SEQ_PK_WS_SERVICIO.NEXTVAL,'Prestadores','LOGIN_ADMIN','http://osiapppre02.colsanitas.com/services/ProxyPrestadores?wsdl','text/xml;charset=UTF-8;','Apache-HttpClient/4.1.1 (java 1.5)');

INSERT INTO WS_METODO (id_metodo,id_servicio,nombre,timeout)
VALUES (SEQ_PK_WS_METODO.NEXTVAL, SEQ_PK_WS_SERVICIO.CURRVAL,'http://www.colsanitas.com/Prestadores/CuadroMedico',30);

COMMIT;
