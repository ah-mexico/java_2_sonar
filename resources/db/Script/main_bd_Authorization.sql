SET SERVEROUTPUT ON SIZE 1000000
SET PAGESIZE 10000
SET TIMING OFF
SET LONG 1000
SET LINESIZE 1000

SPOOL main_prestadores_login_admin.txt

def OWNER=&&OWNER;
alter session set current_schema=&&schema;


PROMPT  << ('_______________             __________________')>>
PROMPT  << ('_______________ WS_SERVICIO __________________')>>
PROMPT  << ('_______________             __________________')>>

@WS_SERVICIO.sql;

PROMPT  << ('_______________           __________________')>>
PROMPT  << ('_______________ WS_STATUS __________________')>>
PROMPT  << ('_______________           __________________')>>

@WS_STATUS.sql;

PROMPT  << ('_______________              __________________')>>
PROMPT  << ('_______________ WS_PROPIEDAD __________________')>>
PROMPT  << ('_______________              __________________')>>

@WS_PROPIEDAD.sql;

PROMPT  << ('_______________           __________________')>>
PROMPT  << ('_______________ WS_METODO __________________')>>
PROMPT  << ('_______________           __________________')>>

@WS_METODO.sql;

PROMPT  << ('_______________             __________________')>>
PROMPT  << ('_______________ WS_PETICION __________________')>>
PROMPT  << ('_______________             __________________')>>

@WS_PETICION.sql;

PROMPT  << ('_______________             __________________')>>
PROMPT  << ('_______________ WS_ELEMENTO __________________')>>
PROMPT  << ('_______________             __________________')>>

@WS_ELEMENTO.sql;

PROMPT  << ('_______________                       __________________')>>
PROMPT  << ('_______________ WS_ELEMENTO_PROPIEDAD __________________')>>
PROMPT  << ('_______________                       __________________')>>

@WS_ELEMENTO_PROPIEDAD.sql;

PROMPT  << ('_______________                    __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_SERVICIO __________________')>>
PROMPT  << ('_______________                    __________________')>>

@SEQ_PK_WS_SERVICIO.sql;

PROMPT  << ('_______________                     __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_PROPIEDAD __________________')>>
PROMPT  << ('_______________                     __________________')>>

@SEQ_PK_WS_PROPIEDAD.sql;

PROMPT  << ('_______________                  __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_METODO __________________')>>
PROMPT  << ('_______________                  __________________')>>

@SEQ_PK_WS_METODO.sql;

PROMPT  << ('_______________                    __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_PETICION __________________')>>
PROMPT  << ('_______________                    __________________')>>

@SEQ_PK_WS_PETICION.sql;

PROMPT  << ('_______________                    __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_ELEMENTO __________________')>>
PROMPT  << ('_______________                    __________________')>>

@SEQ_PK_WS_ELEMENTO.sql;

PROMPT  << ('_______________                              __________________')>>
PROMPT  << ('_______________ SEQ_PK_WS_ELEMENTO_PROPIEDAD __________________')>>
PROMPT  << ('_______________                              __________________')>>

@SEQ_PK_WS_ELEMENTO_PROPIEDAD.sql;

PROMPT  << ('_______________                 __________________')>>
PROMPT  << ('__________ CREA_TBUP_HOM_TIPPER __________________')>>
PROMPT  << ('_______________                 __________________')>>

@CREA_TBUP_HOM_TIPPER.sql;


PROMPT  << ('_______________                  __________________')>>
PROMPT  << ('_______________ INSERT_WS_STATUS __________________')>>
PROMPT  << ('_______________                  __________________')>>

@INSERT_WS_STATUS.sql;

PROMPT  << ('_______________                  __________________')>>
PROMPT  << ('_______________ INSERT_SERVICIOS __________________')>>
PROMPT  << ('_______________                  __________________')>>

@INSERT_SERVICIOS.sql;

PROMPT  << ('_______________                    __________________')>>
PROMPT  << ('_______________ PKG_WS_WEBSERVICES __________________')>>
PROMPT  << ('_______________                    __________________')>>

@PKG_WS_WEBSERVICES.sql;

PROMPT  << ('_______________                 __________________')>>
PROMPT  << ('_______________ PCK_PRESTADORES __________________')>>
PROMPT  << ('_______________                 __________________')>>

@PCK_PRESTADORES.sql;


PROMPT  << ('_______________                 __________________')>>
PROMPT  << ('_______ INSERT_XML_SUC_PRESTADOR__________________')>>
PROMPT  << ('_______________                 __________________')>>

@INSERT_XML_SUC_PRESTADOR.sql;



spool off;
