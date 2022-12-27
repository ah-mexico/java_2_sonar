SET SERVEROUTPUT ON SIZE 1000000
SET PAGESIZE 10000
SET TIMING OFF
SET LONG 1000
set linesize 1000
--
SPOOL PCK_PRESTADORES.txt
--
--
CREATE OR REPLACE PACKAGE PCK_PRESTADORES IS
  ------------------------------------------------------------------------------
  -- Autor : nmendez
  -- Fecha : 22/03/2016
  -- Proposito General: Paquete que permite consultar los datos de un prestador.
  ------------------------------------------------------------------------------
  --Modificaciones
  -- Version :
  -- Autor   :
  -- Fecha   :
  -- Comentario:
  ------------------------------------------------------------------------------
  --

  -- Definicion de tipos
  TYPE CUR_PRESTADOR IS REF CURSOR;
  --

  ---------------------------------------------------
  -- Procedimiento: PC_CONSULTA_SUCURSALES_PRESTADOR
  -- Propósito: consulta los datos de un prestador.
  ---------------------------------------------------
  ----------------------------------------------------------------------
  -- Versión  Autor      Fecha         Comentario
  -- Ver 1.0  NMENDEZ  23/03/2016      Version Inicial de documentación.
  ----------------------------------------------------------------------

  PROCEDURE PC_CONSULTA_SUC_PRESTADOR (I_CODIGO_PER IN VARCHAR2,
                                       I_CODIGO_PRE IN VARCHAR2,
                                       I_NRO_ID     IN VARCHAR2,
                                       I_NOM_SUC    IN VARCHAR2,
                                       I_TIPO_ID    IN VARCHAR2,
                                       O_PRESTADOR  IN OUT CUR_PRESTADOR);


  ---------------------------------------------------
  -- Procedimiento: PC_CONSULTA_DATOS_PRESTADOR
  -- Propósito: consulta los datos de un prestador.
  ---------------------------------------------------
  ----------------------------------------------------------------------
  -- Versión  Autor      Fecha         Comentario
  -- Ver 1.0  NMENDEZ  29/03/2016      Version Inicial de documentación.
  ----------------------------------------------------------------------

  PROCEDURE PC_CONSULTA_DATOS_PRESTADOR (I_CODIGO_PER IN VARCHAR2,
                                         I_CODIGO_NEG IN VARCHAR2,
                                         I_ESTADO     IN VARCHAR2,
                                         I_NOMBRES    IN VARCHAR2,
                                         I_NRO_ID     IN VARCHAR2,
                                         I_TIPO_ID    IN VARCHAR2,
                                         I_RAZON_SOC  IN VARCHAR2,
                                         O_CODIGO_PER OUT VARCHAR2,
                                         O_COD_TIPO_NEG OUT VARCHAR2,
                                         O_ESTADO     OUT VARCHAR2,
                                         O_NOMBRES    OUT VARCHAR2,
                                         O_NRO_ID     OUT VARCHAR2,
                                         O_TIPO_ID    OUT VARCHAR2,
                                         O_RAZON_SOC  OUT VARCHAR2);

END PCK_PRESTADORES;
/

SHOW ERRORS;

CREATE OR REPLACE PACKAGE BODY PCK_PRESTADORES IS
  ------------------------------------------------------------------------------
  -- Autor : nmendez
  -- Fecha : 22/03/2016
  -- Proposito General: Paquete que permite consultar los datos de un prestador.
  ------------------------------------------------------------------------------

  --------------------------------------------------------
  --Modificaciones
  -- Version :
  -- Autor   :
  -- Fecha   :
  -- Comentario:
  --------------------------------------------------------


  --------------------------------------------------------
  -- Procedimiento: PC_CONSULTA_SUCURSALES_PRESTADOR
  -- Propósito : Consultar las sucursales de un prestador.
  --------------------------------------------------------

  PROCEDURE PC_CONSULTA_SUC_PRESTADOR (I_CODIGO_PER IN VARCHAR2,
                                       I_CODIGO_PRE IN VARCHAR2,
                                       I_NRO_ID     IN VARCHAR2,
                                       I_NOM_SUC    IN VARCHAR2,
                                       I_TIPO_ID    IN VARCHAR2,
                                       O_PRESTADOR  IN OUT CUR_PRESTADOR) IS

   -- Excepciones --
  PRESTADOR_NO_EXISTE  EXCEPTION;

  -- Constantes --
  c_ok               CONSTANT NUMBER := 0;
  c_app              CONSTANT VARCHAR2(50) := 'LOGIN_ADMIN';
  c_metodo           CONSTANT VARCHAR2(100) := 'http://www.colsanitas.com/Prestadores/CuadroMedico';
  c_servicio         CONSTANT VARCHAR2(100) := 'Prestadores';
  c_estado_ok        CONSTANT VARCHAR2(10) := 'OK';
  c_codigo_origen    CONSTANT VARCHAR2(50) := 'USRLOGADM';

  -- Variables --
  v_estado           VARCHAR2(100);
  v_cod_pre          VARCHAR2(100);
  v_cod_error        NUMBER := 0;
  v_msg_error        VARCHAR2(2000);
  v_argumentos       VARCHAR2(2000);
  v_fecha_cons       VARCHAR2(20);
  v_id_peticion      WS_PETICION.id_peticion%TYPE;
  v_status_code      WS_STATUS.status_code%TYPE;
  --
  --
  BEGIN
    --
    IF TRIM(I_NRO_ID) IS NOT NULL AND
       TRIM(I_TIPO_ID) IS NOT NULL THEN
       --
       v_cod_error := 0;
       v_msg_error := null;
       --
       v_fecha_cons := to_char(sysdate,'yyyy-mm-dd');
       --
       IF TRIM(I_NOM_SUC) IS NULL THEN
          v_argumentos := '$FECHA_CONS$='||v_fecha_cons||'^$NRO_ID$='||I_NRO_ID||'^$TIPO_ID$='||I_TIPO_ID||'^';
       ELSE
          v_argumentos := '$FECHA_CONS$='||v_fecha_cons||'^$NRO_ID$='||I_NRO_ID||'^$NOM_SUC$='||I_NOM_SUC||'^$TIPO_ID$='||I_TIPO_ID||'^';
       END IF;
       --
       pkg_ws_webservices.pr_invocar_metodo_ws(c_app, c_servicio, c_metodo, v_argumentos, c_codigo_origen, v_id_peticion, v_status_code, v_cod_error, v_msg_error);
       --
       IF v_cod_error = c_ok THEN
          --
          SELECT p.response.extract('//*[local-name()=''errorCode'']/text()').getStringVal(),
                 p.response.extract('(//*[local-name()=''codigoPrestador''])[1]/text()').getStringVal()
          INTO v_estado, v_cod_pre
          FROM WS_PETICION p
          WHERE p.id_peticion = v_id_peticion;
          --
          IF v_estado = c_estado_ok THEN
             --
             OPEN O_PRESTADOR FOR
               --
               SELECT distinct v_cod_pre, xt."codigo_sucursal" as COD_SUC, xt."nombre_sucursal" as NOM_SUC, I_NRO_ID, I_TIPO_ID
               FROM  WS_PETICION p,
                     XMLTABLE(XMLNAMESPACES('http://colsanitas.com/osi/comun/prestadores' AS "p"),
                     '//*[local-name()=''sucursales'']'
                     PASSING p.response
                     COLUMNS
                     "codigo_sucursal"    varchar2(50)     PATH 'p:codSucursalPrestador',
                     "nombre_sucursal"    varchar2(1000)   PATH 'p:nomSucursalPrestador') xt
               WHERE p.id_peticion = v_id_peticion;
          --
          END IF;
          --
       END IF;
       --
    END IF;
    --
  EXCEPTION
     WHEN PRESTADOR_NO_EXISTE THEN
         null;
     WHEN OTHERS THEN
         null;
  END PC_CONSULTA_SUC_PRESTADOR;


  -----------------------------------------------------------
  -- Procedimiento: PC_CONSULTA_DATOS_PRESTADOR
  -- Propósito : Consultar los datos basicos de un prestador.
  -----------------------------------------------------------

  PROCEDURE PC_CONSULTA_DATOS_PRESTADOR (I_CODIGO_PER IN VARCHAR2,
                                         I_CODIGO_NEG IN VARCHAR2,
                                         I_ESTADO     IN VARCHAR2,
                                         I_NOMBRES    IN VARCHAR2,
                                         I_NRO_ID     IN VARCHAR2,
                                         I_TIPO_ID    IN VARCHAR2,
                                         I_RAZON_SOC  IN VARCHAR2,
                                         O_CODIGO_PER OUT VARCHAR2,
                                         O_COD_TIPO_NEG OUT VARCHAR2,
                                         O_ESTADO     OUT VARCHAR2,
                                         O_NOMBRES    OUT VARCHAR2,
                                         O_NRO_ID     OUT VARCHAR2,
                                         O_TIPO_ID    OUT VARCHAR2,
                                         O_RAZON_SOC  OUT VARCHAR2) IS

   -- Excepciones --
  PRESTADOR_NO_EXISTE  EXCEPTION;

  -- Constantes --
  c_ok               CONSTANT NUMBER := 0;
  c_app              CONSTANT VARCHAR2(50) := 'LOGIN_ADMIN';
  c_metodo           CONSTANT VARCHAR2(100) := 'http://www.colsanitas.com/Prestadores/CuadroMedico';
  c_servicio         CONSTANT VARCHAR2(100) := 'Prestadores';
  c_estado_ok        CONSTANT VARCHAR2(10) := 'OK';
  c_codigo_origen    CONSTANT VARCHAR2(50) := 'USRLOGADM';

  -- Variables --
  v_estado           VARCHAR2(100);
  v_cod_pre          VARCHAR2(100);
  v_cod_error        NUMBER := 0;
  v_msg_error        VARCHAR2(2000);
  v_argumentos       VARCHAR2(2000);
  v_fecha_cons       VARCHAR2(20);
  v_codigo_hom       TBUP_HOM_TIPPER.CODIGO_ORI%TYPE;
  v_id_peticion      WS_PETICION.id_peticion%TYPE;
  v_status_code      WS_STATUS.status_code%TYPE;
  --
  --
  BEGIN
    --
    IF TRIM(I_NRO_ID) IS NOT NULL AND
       TRIM(I_TIPO_ID) IS NOT NULL THEN
       --
       v_cod_error := 0;
       v_msg_error := null;
       v_codigo_hom := null;
       --
       SELECT codigo_hom into v_codigo_hom FROM TBUP_HOM_TIPPER WHERE origen = 'BH' and codigo_ori = I_TIPO_ID;
       --
       v_fecha_cons := to_char(sysdate,'yyyy-mm-dd');
       --
       IF TRIM(v_codigo_hom) IS NOT NULL THEN
          --
          IF TRIM(I_RAZON_SOC) IS NULL THEN
             v_argumentos := '$FECHA_CONS$='||v_fecha_cons||'^$NRO_ID$='||I_NRO_ID||'^$TIPO_ID$='||v_codigo_hom||'^';
          ELSE
             v_argumentos := '$FECHA_CONS$='||v_fecha_cons||'^$NRO_ID$='||I_NRO_ID||'^$NOM_PRE$='||I_RAZON_SOC||'^$TIPO_ID$='||v_codigo_hom||'^';
          END IF;
          --
          pkg_ws_webservices.pr_invocar_metodo_ws(c_app, c_servicio, c_metodo, v_argumentos, c_codigo_origen, v_id_peticion, v_status_code, v_cod_error, v_msg_error);
          --
          IF v_cod_error = c_ok THEN
             --
             SELECT p.response.extract('//*[local-name()=''errorCode'']/text()').getStringVal(),
                    p.response.extract('(//*[local-name()=''codigoPrestador''])[1]/text()').getStringVal(),
                    p.response.extract('(//*[local-name()=''nombrePrestador''])[1]/text()').getStringVal(),
                    p.response.extract('(//*[local-name()=''numIdentificacion''])[1]/text()').getStringVal(),
                    p.response.extract('(//*[local-name()=''tipoIdentificacion''])[1]/text()').getStringVal()
             INTO v_estado, O_CODIGO_PER, O_RAZON_SOC, O_NRO_ID, O_COD_TIPO_NEG
             FROM WS_PETICION p
             WHERE p.id_peticion = v_id_peticion;
             --
             IF TRIM(O_RAZON_SOC) IS NOT NULL THEN
                --
                O_TIPO_ID := I_TIPO_ID;
                O_NOMBRES := O_RAZON_SOC;
                O_ESTADO := 'ACTIVO';
                --
             END IF;
             --
          END IF;
          --
       END IF;
       --
    END IF;
    --
  EXCEPTION
     WHEN PRESTADOR_NO_EXISTE THEN
         null;
     WHEN OTHERS THEN
         null;
         --dbms_output.put_line('Error: '||sqlerrm);
  END PC_CONSULTA_DATOS_PRESTADOR;


END PCK_PRESTADORES;
/

SHOW ERRORS;

SPOOL OFF;
