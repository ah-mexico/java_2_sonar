SET SERVEROUTPUT ON SIZE 1000000
SET PAGESIZE 10000
SET TIMING OFF
SET LONG 1000
set linesize 1000
--
SPOOL PKG_WS_WEBSERVICES.txt
--
CREATE OR REPLACE PACKAGE PKG_WS_WEBSERVICES AS
/******************************************************************************
   NAME:       PKG_WS_WEBSERVICES
   PURPOSE:    proveer un medio parametrizable de consumo de web services

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        22/09/2015  mtapiero         1. Created this package.
******************************************************************************/

PROCEDURE PR_GENERAR_REQUEST(
	p_aplicacion IN WS_SERVICIO.aplicacion%TYPE,
	p_servicio IN WS_SERVICIO.nombre%TYPE,
	p_metodo IN WS_METODO.nombre%TYPE,
	p_argumentos IN VARCHAR2,
	p_txt_xml IN OUT NOCOPY VARCHAR2,
	p_cod_err OUT NUMBER, 
	p_msg_err OUT VARCHAR2);	

PROCEDURE PR_INVOCAR_METODO_WS(
	p_aplicacion IN WS_SERVICIO.aplicacion%TYPE,
	p_servicio IN WS_SERVICIO.nombre%TYPE,
	p_metodo IN WS_METODO.nombre%TYPE,
	p_argumentos IN VARCHAR2,
	p_usuario IN VARCHAR2,
	p_id_peticion OUT WS_PETICION.id_peticion%TYPE, 
	p_status_code OUT WS_STATUS.status_code%TYPE, 
	p_cod_err OUT NUMBER, 
	p_msg_err OUT VARCHAR2);

FUNCTION fn_buscar_argumento(
	p_llave	IN VARCHAR2, 
	p_argumentos IN VARCHAR2, 
	p_indice IN VARCHAR2 DEFAULT '')
RETURN VARCHAR2;

END PKG_WS_WEBSERVICES;
/
SHOW ERRORS;

CREATE OR REPLACE PACKAGE BODY PKG_WS_WEBSERVICES AS
/******************************************************************************
   NAME:       PKG_WS_WEBSERVICES
   PURPOSE:    proveer un medio parametrizable de consumo de web services

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        22/09/2015  mtapiero         1. Created this package.
*******************************************************************************/


-------------------------------------------------------------------------------
--                                 FUNCIONES                                 --
-------------------------------------------------------------------------------
FUNCTION fn_buscar_argumento(
	p_llave	IN VARCHAR2, 
	p_argumentos IN VARCHAR2, 
	p_indice IN VARCHAR2 DEFAULT '')
RETURN VARCHAR2
AS
  /* *********************************************************************************** */
  /* Nombre: Misael Tapiero                                                              */
  /* Versión PL/SQL : 1                                                                  */
  /* Proposito General : Obtiene el valor un argumento si existe.                        */
  /* Fecha Modificación : 22 de Septiembre de 2015                                       */
  /* Comentario del Cambio : Se crea la version inicial de la funcion.                   */  
  /* *********************************************************************************** */
	c_caracter_cierre CONSTANT CHAR(1) := '^';
	v_llave  VARCHAR2(50);
	v_valor  VARCHAR2(4000);
BEGIN
	BEGIN
		-- CONSTRUYE LA LLAVE
		IF p_indice IS NULL OR LENGTH(p_indice) = 0 THEN
			v_llave := p_llave || '=';
		ELSE
			v_llave := SUBSTR(p_llave,1, INSTR(p_llave,'$', 1, 2) - 1) || ':' || p_indice || '$=';
		END IF;
		-- BUSCA EL VALOR DE LA LLAVE EN LOS ARGUMENTOS
		IF INSTR(p_argumentos,v_llave) <> 0 THEN
			v_valor := SUBSTR(p_argumentos,
							INSTR(p_argumentos,v_llave, 1, 1) + LENGTH(v_llave),
							INSTR(p_argumentos,c_caracter_cierre, INSTR(p_argumentos,v_llave, 1, 1) + LENGTH(v_llave)) - INSTR(p_argumentos,v_llave, 1, 1) - LENGTH(v_llave)
							);
		ELSE
			v_valor := '';
		END IF;
	EXCEPTION
		WHEN OTHERS THEN
			v_valor := NULL;
	END;
	
	RETURN v_valor;
END fn_buscar_argumento;


FUNCTION fn_generar_elemento(
	p_elemento	IN WS_ELEMENTO.id_elemento%TYPE, 
	p_argumentos IN VARCHAR2, 
	p_txt_xml IN OUT NOCOPY VARCHAR2, 
	p_indice IN VARCHAR2,
	p_msg_err IN OUT NOCOPY VARCHAR2)
RETURN BOOLEAN
AS
  /* *********************************************************************************** */
  /* Nombre: Misael Tapiero                                                              */
  /* Versión PL/SQL : 1                                                                  */
  /* Proposito General : Construye el texto del elemento invocado y sus hijos.           */
  /* Fecha Modificación : 22 de Septiembre de 2015                                       */
  /* Comentario del Cambio : Se crea la version inicial de la funcion.                   */  
  /* *********************************************************************************** */
	e_argumento   EXCEPTION;
	e_elemento    EXCEPTION;
	e_propiedad   EXCEPTION;
	v_nombre      WS_ELEMENTO.nombre%TYPE;
	v_min_ocurr   WS_ELEMENTO.minima_ocurrencia%TYPE;
	v_max_ocurr   WS_ELEMENTO.maxima_ocurrencia%TYPE;
	v_nivel       WS_ELEMENTO.nivel%TYPE;
	v_secuencia   WS_ELEMENTO.secuencia%TYPE;
	v_valor       WS_ELEMENTO.valor%TYPE;
	v_cant_hijos  NUMBER(5);
	v_indice      NUMBER(5);
	v_identacion  VARCHAR2(500) := '';
	v_contenido   VARCHAR2(4000) := '';
	v_correcto    BOOLEAN := TRUE;
	CURSOR CR_ELEMENTOS_HIJOS(p_elem_padre  WS_ELEMENTO.id_elemento%TYPE)
		IS
		SELECT id_elemento
		FROM WS_ELEMENTO
		WHERE id_elemento_padre = p_elem_padre
		ORDER BY secuencia asc;
	CURSOR CR_PROPIEDADES(p_elemento  WS_ELEMENTO.id_elemento%TYPE)
		IS
		SELECT P.nombre, P.opcional, P.valor
		FROM WS_ELEMENTO_PROPIEDAD EP, WS_PROPIEDAD P
		WHERE EP.id_elemento = p_elemento AND
			P.id_propiedad = EP.id_propiedad
		ORDER BY P.id_propiedad asc;
BEGIN
	BEGIN
		-- CONSULTA LA INFORMACION DEL ELEMENTO
		SELECT E.nombre, E.minima_ocurrencia, E.maxima_ocurrencia, E.nivel, E.secuencia, E.valor, COUNT(H.id_elemento)
		INTO v_nombre, v_min_ocurr, v_max_ocurr, v_nivel, v_secuencia, v_valor, v_cant_hijos
		FROM WS_ELEMENTO E, WS_ELEMENTO H
		WHERE E.id_elemento = p_elemento AND
			E.id_elemento = H.id_elemento_padre(+)
		GROUP BY E.nombre, E.minima_ocurrencia, E.maxima_ocurrencia, E.nivel, E.secuencia, E.valor;
		-- GENERA LA IDENTACION DEL ELEMENTO
		FOR i IN 1..v_nivel - 1 LOOP
			v_identacion := v_identacion || CHR(9);
		END LOOP;
		-- VALIDA LA CANTIDAD DE HIJOS SEGUN LA OCURRENCIA PARAMETRIZADA
		IF v_min_ocurr IN (0,1) AND NVL(v_max_ocurr,99999) = 1 THEN
			v_indice := 1;
		ELSIF LENGTH(v_valor) > 0 THEN
			IF v_valor LIKE '$%$' THEN
				v_contenido := fn_buscar_argumento(v_valor, p_argumentos, p_indice);
				v_indice := TO_NUMBER(v_contenido);
			ELSE
				v_indice := TO_NUMBER(v_valor);
			END IF;
			IF v_indice < v_min_ocurr OR v_indice > NVL(v_max_ocurr,99999) THEN
				p_msg_err := 'Ocurrencia no valida del elemento ' || p_elemento || ' - ' || UPPER(v_nombre);
				DBMS_OUTPUT.PUT_LINE(p_msg_err);
				RAISE e_elemento;
			END IF;
		ELSE
			p_msg_err := 'Ocurrencia no valida del elemento ' || p_elemento || ' - ' || UPPER(v_nombre);
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
			RAISE e_elemento;
		END IF;
		FOR i IN 1..v_indice LOOP
			-- CREA EL ENCABEZADO DEL ELEMENTO
			p_txt_xml := p_txt_xml || v_identacion || '<' || v_nombre;
			-- AGREGA LAS PROPIEDADES DEL ELEMENTO
			FOR R_PROPIEDAD IN CR_PROPIEDADES(p_elemento) LOOP
				IF R_PROPIEDAD.valor LIKE '$%$' THEN
					-- BUSCA EL VALOR PARA LA PROPIEDAD DENTRO DE LOS ARGUMENTOS
					v_contenido := fn_buscar_argumento(R_PROPIEDAD.valor, p_argumentos);
					IF v_contenido IS NOT NULL THEN
						IF LENGTH(v_contenido) > 0 THEN
							p_txt_xml := p_txt_xml || ' ' || R_PROPIEDAD.nombre || '="' || v_contenido || '"';
						ELSIF LENGTH(v_contenido) = 0 AND R_PROPIEDAD.opcional = 'N' THEN
							p_msg_err := 'No se encuentra el argumento para la propiedad ' || UPPER(R_PROPIEDAD.nombre) || ' del elemento ' || p_elemento || ' - ' || UPPER(v_nombre);
							DBMS_OUTPUT.PUT_LINE(p_msg_err);
							RAISE e_propiedad;
						END IF;
					ELSE
						p_msg_err := 'Error al buscar el argumento para la propiedad ' || UPPER(R_PROPIEDAD.nombre) || ' del elemento ' || p_elemento || ' - ' || UPPER(v_nombre);
						DBMS_OUTPUT.PUT_LINE(p_msg_err);
						RAISE e_propiedad;
					END IF;
				ELSE
					p_txt_xml := p_txt_xml || ' ' || R_PROPIEDAD.nombre || '="' || R_PROPIEDAD.valor || '"';
				END IF;
			END LOOP;
			p_txt_xml := p_txt_xml || '>';
			IF v_cant_hijos > 0 THEN
				p_txt_xml := p_txt_xml || CHR(13) || CHR(10);
				-- INVOCA LOS HIJOS SEGUN EL ORDEN DE SECUENCIA
				IF v_max_ocurr > 1 THEN
					IF LENGTH(p_indice) > 0 THEN
						v_contenido := '.' || i;
					ELSE
						v_contenido := i;
					END IF;
				ELSE
					v_contenido := '';
				END IF;
				FOR R_ELEMENTO_HIJO IN CR_ELEMENTOS_HIJOS(p_elemento) LOOP
					IF NOT fn_generar_elemento(R_ELEMENTO_HIJO.id_elemento, p_argumentos, p_txt_xml, p_indice || v_contenido, p_msg_err) THEN
						RAISE e_elemento;
					END IF;
				END LOOP;
				-- CIERRA EL ELEMENTO
				p_txt_xml := p_txt_xml || v_identacion || '</' || v_nombre || '>' || CHR(13) || CHR(10);
			ELSIF v_valor LIKE '$%$' THEN
				-- BUSCA LA VARIABLE DENTRO DE LOS ARGUMENTOS
				v_contenido := fn_buscar_argumento(v_valor, p_argumentos, p_indice);
				IF LENGTH(v_contenido) > 0 THEN
					p_txt_xml := p_txt_xml || v_contenido;
				ELSIF v_min_ocurr > 0 THEN
					p_msg_err := 'No se encuentra el argumento para la variable  ' || UPPER(v_valor) || ' del elemento ' || p_elemento || ' - ' || UPPER(v_nombre);
					DBMS_OUTPUT.PUT_LINE(p_msg_err);
					RAISE e_argumento;
				END IF;
				-- CIERRA EL ELEMENTO
				p_txt_xml := p_txt_xml || '</' || v_nombre || '>' || CHR(13) || CHR(10);
			ELSE
				-- CIERRA EL ELEMENTO
				p_txt_xml := p_txt_xml || v_valor || '</' || v_nombre || '>' || CHR(13) || CHR(10);
			END IF;
		END LOOP;
	EXCEPTION
	WHEN e_argumento THEN
		v_correcto := FALSE;
	WHEN e_elemento THEN
		v_correcto := FALSE;
	WHEN e_propiedad THEN
		v_correcto := FALSE;
	WHEN OTHERS THEN
		p_msg_err := 'Un error fue encontrado - '||SQLCODE||' -ERROR- '||SQLERRM;
		DBMS_OUTPUT.PUT_LINE(p_msg_err);
		v_correcto := FALSE;
	END;	
	RETURN v_correcto;
END fn_generar_elemento;


PROCEDURE PR_GENERAR_REQUEST(
	p_aplicacion IN WS_SERVICIO.aplicacion%TYPE,
	p_servicio IN WS_SERVICIO.nombre%TYPE,
	p_metodo IN WS_METODO.nombre%TYPE,
	p_argumentos IN VARCHAR2,
	p_txt_xml IN OUT NOCOPY VARCHAR2,
	p_cod_err OUT NUMBER, 
	p_msg_err OUT VARCHAR2
) IS
  /* *********************************************************************************** */
  /* Nombre: Misael Tapiero                                                              */
  /* Versión PL/SQL : 1                                                                  */
  /* Proposito General : Construye el xml del request de la peticion.                    */
  /* Fecha Modificación : 23 de Septiembre de 2015                                       */
  /* Comentario del Cambio : Se crea la version inicial del procedimiento.               */  
  /* *********************************************************************************** */
	e_elemento     EXCEPTION;
  CURSOR CR_ELEMENTOS(p_aplicacion IN WS_SERVICIO.aplicacion%TYPE, p_servicio IN WS_SERVICIO.nombre%TYPE, p_metodo IN WS_METODO.nombre%TYPE)
		IS
		SELECT E.id_elemento, E.nombre, E.minima_ocurrencia, E.maxima_ocurrencia, E.valor
		FROM WS_SERVICIO S, WS_METODO M, WS_ELEMENTO E
		WHERE S.aplicacion = p_aplicacion AND
			S.nombre = p_servicio AND
			M.id_servicio = S.id_servicio AND
			M.nombre = p_metodo AND
			E.id_metodo = M.id_metodo AND
			E.id_elemento_padre IS NULL AND
			E.nivel = 1
		ORDER BY secuencia asc;
BEGIN
	p_txt_xml := '';
	FOR R_ELEMENTO IN CR_ELEMENTOS(p_aplicacion, p_servicio, p_metodo) LOOP
		IF NOT fn_generar_elemento(R_ELEMENTO.id_elemento, p_argumentos, p_txt_xml, NULL, p_msg_err) THEN
			RAISE e_elemento;
		END IF;
	END LOOP;
	p_cod_err := 0;
	p_msg_err := 'OK';
EXCEPTION
	WHEN e_elemento THEN
		p_cod_err := 3;
		DBMS_OUTPUT.PUT_LINE(p_msg_err);
	WHEN OTHERS THEN
		p_cod_err := SQLCODE;
		p_msg_err := 'Un error fue encontrado - '||SQLCODE||' -ERROR- '||SQLERRM;
		DBMS_OUTPUT.PUT_LINE(p_msg_err);
END PR_GENERAR_REQUEST;


PROCEDURE PR_INVOCAR_METODO_WS(
	p_aplicacion IN WS_SERVICIO.aplicacion%TYPE,
	p_servicio IN WS_SERVICIO.nombre%TYPE,
	p_metodo IN WS_METODO.nombre%TYPE,
	p_argumentos IN VARCHAR2,
	p_usuario IN VARCHAR2,
	p_id_peticion OUT WS_PETICION.id_peticion%TYPE, 
	p_status_code OUT WS_STATUS.status_code%TYPE, 
	p_cod_err OUT NUMBER, 
	p_msg_err OUT VARCHAR2
) IS
  /* *********************************************************************************** */
  /* Nombre: Misael Tapiero                                                              */
  /* Versión PL/SQL : 1                                                                  */
  /* Proposito General : Invoca un metodo del servicio web y persiste el resultado en    */
  /*                     la tabla WS_PETICION                                            */
  /* Fecha Modificación : 23 de Septiembre de 2015                                       */
  /* Comentario del Cambio : Se crea la version inicial del procedimiento.               */  
  /* *********************************************************************************** */
	e_request      EXCEPTION;
	v_id_servicio  WS_SERVICIO.id_servicio%TYPE;
	v_id_metodo    WS_METODO.id_metodo%TYPE;
	v_url          WS_SERVICIO.url%TYPE;
	v_content_type WS_SERVICIO.content_type%TYPE;
	v_user_agent   WS_SERVICIO.user_agent%TYPE;
	v_proxy        WS_SERVICIO.proxy%TYPE;
	v_username     WS_SERVICIO.username%TYPE;
	v_password     WS_SERVICIO.password%TYPE;
	v_path_wallet  WS_SERVICIO.path_wallet%TYPE;
	v_pass_wallet  WS_SERVICIO.password_wallet%TYPE;
	v_timeout      WS_METODO.timeout%TYPE;
	v_http_req     utl_http.req;
	v_http_resp    utl_http.resp;
	v_xml_request  XMLType;
	v_xml_reponse  XMLType;
	v_txt_xml      VARCHAR2(32767);
	v_clob_xml     CLOB;
	v_fecha_ini    DATE;
BEGIN
	p_id_peticion := 0;
	BEGIN
		SELECT S.id_servicio, M.id_metodo, S.url, S.content_type, S.user_agent, S.proxy, S.username, S.password, S.path_wallet, S.password_wallet, M.timeout
		INTO v_id_servicio, v_id_metodo, v_url, v_content_type, v_user_agent, v_proxy, v_username, v_password, v_path_wallet, v_pass_wallet, v_timeout
		FROM WS_SERVICIO S, WS_METODO M
		WHERE S.aplicacion = p_aplicacion AND
			S.nombre = p_servicio AND
			M.id_servicio = S.id_servicio AND
			M.nombre = p_metodo;
		PR_GENERAR_REQUEST(p_aplicacion, p_servicio, p_metodo, p_argumentos, v_txt_xml, p_cod_err, 	p_msg_err);
		IF p_cod_err <> 0 THEN
			RAISE e_request;
		END IF;
		v_xml_request := XMLTYPE.CREATEXML(v_txt_xml);
		IF (v_proxy IS NOT NULL) THEN
			UTL_HTTP.SET_PROXY(v_proxy);
		END IF;
		IF (v_path_wallet IS NOT NULL) THEN
			UTL_HTTP.SET_WALLET(v_path_wallet, v_pass_wallet);
		END IF;
		/* Se establece el tiempo limite del consumo */
		UTL_HTTP.SET_TRANSFER_TIMEOUT(v_timeout);
		UTL_HTTP.SET_DETAILED_EXCP_SUPPORT(TRUE);
		v_http_req := UTL_HTTP.BEGIN_REQUEST(v_url, 'POST','HTTP/1.1');
		IF (v_username IS NOT NULL) THEN
			UTL_HTTP.SET_AUTHENTICATION(v_http_req, v_username, v_password); -- Use HTTP Basic Authen. Scheme
		END IF;
	    UTL_HTTP.SET_HEADER(v_http_req, 'Content-Type', v_content_type);
	    UTL_HTTP.SET_HEADER(v_http_req, 'Content-Length', length(v_txt_xml));
		UTL_HTTP.SET_HEADER(v_http_req, 'User-Agent', v_user_agent);
	    UTL_HTTP.SET_HEADER(v_http_req, 'SOAPAction', p_metodo);
	    UTL_HTTP.WRITE_TEXT(v_http_req, v_txt_xml);
	    v_fecha_ini := SYSDATE;
        v_http_resp := UTL_HTTP.GET_RESPONSE(v_http_req);
        DBMS_LOB.CREATETEMPORARY(v_clob_xml,TRUE);
        DBMS_LOB.OPEN (v_clob_xml, DBMS_LOB.LOB_READWRITE);
        BEGIN
            LOOP
                UTL_HTTP.READ_TEXT(v_http_resp, v_txt_xml, 32766);
                DBMS_LOB.APPEND(v_clob_xml, v_txt_xml);
            END LOOP;
            UTL_HTTP.END_RESPONSE(v_http_resp);
        EXCEPTION
            WHEN UTL_HTTP.END_OF_BODY THEN
                UTL_HTTP.END_RESPONSE(v_http_resp);
        END;
	    v_xml_reponse := XMLTYPE.CREATEXML(v_clob_xml);
	    DBMS_LOB.CLOSE (v_clob_xml);
	    DBMS_LOB.FREETEMPORARY(v_clob_xml);
	    INSERT INTO WS_PETICION (id_peticion, id_servicio, id_metodo, usuario, fecha_ini, request, fecha_fin, response, status_code, reason_phrase)
	    VALUES (SEQ_PK_WS_PETICION.NEXTVAL, v_id_servicio, v_id_metodo, p_usuario, v_fecha_ini, v_xml_request, SYSDATE, v_xml_reponse, v_http_resp.status_code, v_http_resp.reason_phrase)
	    RETURNING id_peticion INTO p_id_peticion;
	    COMMIT;
	    p_status_code := v_http_resp.status_code;
	    p_cod_err := 0;
	    p_msg_err := 'OK';
	EXCEPTION
		WHEN NO_DATA_FOUND THEN
			p_cod_err := 1;
			p_msg_err := 'Servicio o metodo no encontrado';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN TOO_MANY_ROWS THEN
			p_cod_err := 2;
			p_msg_err := 'Se encontro mas de un servicio o metodo';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN e_request THEN
			p_cod_err := 3;
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.REQUEST_FAILED THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'La solicitud no ejecuta';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.BAD_URL THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'La URL solicitada esta mal formada';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.PROTOCOL_ERROR THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'Un error de protocolo HTTP se produce cuando se comunica con el servidor Web';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.HEADER_NOT_FOUND THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'La cabecera no se encuentra';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.END_OF_BODY THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'Se ha alcanzado el final del cuerpo de la respuesta HTTP y es superior a 32767 caracteres';
			INSERT INTO WS_PETICION (id_peticion, id_servicio, id_metodo, usuario, fecha_ini, request, fecha_fin, response, status_code, reason_phrase)
			VALUES (SEQ_PK_WS_PETICION.NEXTVAL, v_id_servicio, v_id_metodo, p_usuario, v_fecha_ini, v_xml_request, SYSDATE, NULL, v_http_resp.status_code, v_http_resp.reason_phrase)
			RETURNING id_peticion INTO p_id_peticion;
			COMMIT;
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.ILLEGAL_CALL THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'La llamada a UTL_HTTP es ilegal en el estado actual de la peticion HTTP, revisar la autenticacion';
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN UTL_HTTP.TRANSFER_TIMEOUT THEN
			p_cod_err := UTL_HTTP.GET_DETAILED_SQLCODE;
			p_msg_err := 'El tiempo de espera de lectura se ha completado';
			INSERT INTO WS_PETICION (id_peticion, id_servicio, id_metodo, usuario, fecha_ini, request, fecha_fin, response, status_code, reason_phrase)
			VALUES (SEQ_PK_WS_PETICION.NEXTVAL, v_id_servicio, v_id_metodo, p_usuario, v_fecha_ini, v_xml_request, SYSDATE, NULL, v_http_resp.status_code, v_http_resp.reason_phrase)
			RETURNING id_peticion INTO p_id_peticion;
			COMMIT;
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
		WHEN OTHERS THEN
			p_cod_err := NVL(UTL_HTTP.GET_DETAILED_SQLCODE,SQLCODE);
			p_msg_err := NVL(UTL_HTTP.GET_DETAILED_SQLERRM,SQLERRM);
			IF v_xml_request IS NOT NULL THEN
				INSERT INTO WS_PETICION (id_peticion, id_servicio, id_metodo, usuario, request, response, status_code, reason_phrase)
				VALUES (SEQ_PK_WS_PETICION.NEXTVAL, v_id_servicio, v_id_metodo, p_usuario, v_xml_request, v_xml_reponse, v_http_resp.status_code, v_http_resp.reason_phrase)
				RETURNING id_peticion INTO p_id_peticion;
				COMMIT;
			END IF;
			DBMS_OUTPUT.PUT_LINE(p_msg_err);
	END;
EXCEPTION
	WHEN OTHERS THEN
		p_cod_err := SQLCODE;
		p_msg_err := SQLERRM;
		DBMS_OUTPUT.PUT_LINE(p_msg_err);
		ROLLBACK;
END PR_INVOCAR_METODO_WS;

END PKG_WS_WEBSERVICES;
/
SHOW ERRORS;
SPOOL OFF;