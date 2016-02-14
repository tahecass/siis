--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.0
-- Dumped by pg_dump version 9.5.0

-- Started on 2016-02-14 13:18:03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2148 (class 1262 OID 12373)
-- Dependencies: 2147
-- Name: postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- TOC entry 188 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2151 (class 0 OID 0)
-- Dependencies: 188
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 187 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 2152 (class 0 OID 0)
-- Dependencies: 187
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 184 (class 1259 OID 16427)
-- Name: CARTERA; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "CARTERA" (
    "SECUENCIA" integer NOT NULL,
    "SECUENCIA_USUARIO" integer,
    "SECUENCIA_CLIENTE" integer,
    "FECHA_PAGO" date,
    "FECHA_HORA_ACTUALIZACION" date
);


ALTER TABLE "CARTERA" OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16437)
-- Name: CLIENTES; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "CLIENTES" (
    "SECUENCIA" integer NOT NULL,
    "NOMBRE_RAZON_SOCIAL" character varying(200),
    "NIT" character varying(50),
    "SECUENCIA_PERSONA" integer
);


ALTER TABLE "CLIENTES" OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16432)
-- Name: DETALLE_CARTERA; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "DETALLE_CARTERA" (
    "SECUENCIA" integer NOT NULL,
    "SECUENCIA_CARTERA" integer,
    "VENCIMIENTO" date,
    "REFERENCIA" character varying(50),
    "NRO_FACTURA" character varying(50),
    "VALOR_FACTURA" double precision
);


ALTER TABLE "DETALLE_CARTERA" OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 16393)
-- Name: INDICADORES; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "INDICADORES" (
    "SECUENCIA_INDICADOR" integer NOT NULL,
    "SECUENCIA_USUARIO" integer,
    "NOMBRE" character varying(200),
    "OBJETIVO_ESPERADO" double precision,
    "OBJETIVO_REAL" double precision,
    "DESVIACION" double precision,
    "SECUENCA_UNIDAD" integer,
    "DOCUMENTACION" character varying(4000)
);


ALTER TABLE "INDICADORES" OWNER TO postgres;

--
-- TOC entry 2153 (class 0 OID 0)
-- Dependencies: 180
-- Name: TABLE "INDICADORES"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE "INDICADORES" IS 'tabla para manejo de la informacion de los indicadores ';


--
-- TOC entry 2154 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."SECUENCIA_INDICADOR"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."SECUENCIA_INDICADOR" IS 'Secuencia de la tabla indicadores';


--
-- TOC entry 2155 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."SECUENCIA_USUARIO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."SECUENCIA_USUARIO" IS 'SECUENCIA DE LA TABLA USUARIOS, LLAVE FORANEA QUE INDICA RESPONSABLE DEL INDICADOR';


--
-- TOC entry 2156 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."NOMBRE"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."NOMBRE" IS 'NOMBRE DEL INDICADOR';


--
-- TOC entry 2157 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."OBJETIVO_ESPERADO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."OBJETIVO_ESPERADO" IS 'META QUE SE ESPERA ALCANZAR CON EL OBJETIVO';


--
-- TOC entry 2158 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."OBJETIVO_REAL"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."OBJETIVO_REAL" IS 'META REAL ALCANZADA POR EL INDICADOR';


--
-- TOC entry 2159 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."DESVIACION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."DESVIACION" IS 'DESVIACION ENTRE EL OBJETIVO ESPERADO Y EL REAL ';


--
-- TOC entry 2160 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."SECUENCA_UNIDAD"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."SECUENCA_UNIDAD" IS 'SECUENCIA DE LA TABLA UNIDADES DE INDICADOR, LLAVE FORANEA';


--
-- TOC entry 2161 (class 0 OID 0)
-- Dependencies: 180
-- Name: COLUMN "INDICADORES"."DOCUMENTACION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "INDICADORES"."DOCUMENTACION" IS 'documentacion del indicador';


--
-- TOC entry 183 (class 1259 OID 16414)
-- Name: PERSONAS; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "PERSONAS" (
    "SECUENCIA" integer NOT NULL,
    "NOMBRE" character varying(100),
    "APELLIDOS" character varying(100),
    "TIPO" character(1),
    "IDENTIFICACION" character varying(50),
    "SECUENCIA_TIPO_IDENTIFICACION" integer,
    "CORREO_ELECTRONICO" character varying(50),
    "DIRECCION" character varying(100),
    "TELEFONO" character varying(30),
    "CELLULAR" character varying(20)
);


ALTER TABLE "PERSONAS" OWNER TO postgres;

--
-- TOC entry 2162 (class 0 OID 0)
-- Dependencies: 183
-- Name: TABLE "PERSONAS"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE "PERSONAS" IS 'TABLA PARA LA GESTION DE PERSONAS ';


--
-- TOC entry 2163 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."SECUENCIA"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."SECUENCIA" IS 'consecutivo de la tabla';


--
-- TOC entry 2164 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."NOMBRE"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."NOMBRE" IS 'nombre de la persona, ya sea empresa o persona natural';


--
-- TOC entry 2165 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."APELLIDOS"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."APELLIDOS" IS 'apellidos de la persona';


--
-- TOC entry 2166 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."TIPO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."TIPO" IS 'Tipo de persona Natural, juridica,etc';


--
-- TOC entry 2167 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."IDENTIFICACION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."IDENTIFICACION" IS 'numero de identificacion de la persona para las personas naturales';


--
-- TOC entry 2168 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."SECUENCIA_TIPO_IDENTIFICACION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."SECUENCIA_TIPO_IDENTIFICACION" IS 'secuencia de la tabla tipo de indentificacion, llave foranea';


--
-- TOC entry 2169 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."CORREO_ELECTRONICO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."CORREO_ELECTRONICO" IS 'correo electronico';


--
-- TOC entry 2170 (class 0 OID 0)
-- Dependencies: 183
-- Name: COLUMN "PERSONAS"."DIRECCION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PERSONAS"."DIRECCION" IS 'Direccion de la persona';


--
-- TOC entry 181 (class 1259 OID 16401)
-- Name: UNIDADES; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "UNIDADES" (
    "SECUENCIA_UNIDAD" integer NOT NULL,
    "SIMBOLO" character varying(10),
    "NOMBRE" character varying(200),
    "DOCUMENTACION" character varying(4000)
);


ALTER TABLE "UNIDADES" OWNER TO postgres;

--
-- TOC entry 2171 (class 0 OID 0)
-- Dependencies: 181
-- Name: TABLE "UNIDADES"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE "UNIDADES" IS 'TABLA PARA ALMACENAR LAS UNIDADES DE UN INDICADOR';


--
-- TOC entry 2172 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN "UNIDADES"."SECUENCIA_UNIDAD"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "UNIDADES"."SECUENCIA_UNIDAD" IS 'CONSECUTIVO DE LA TABLA UNIDAD';


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN "UNIDADES"."SIMBOLO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "UNIDADES"."SIMBOLO" IS 'simbolo de la unidad (%, unid, $) ';


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN "UNIDADES"."NOMBRE"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "UNIDADES"."NOMBRE" IS 'nombre de la unida';


--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 181
-- Name: COLUMN "UNIDADES"."DOCUMENTACION"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "UNIDADES"."DOCUMENTACION" IS 'Documentacion de cada unidad';


--
-- TOC entry 182 (class 1259 OID 16409)
-- Name: USUARIOS; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "USUARIOS" (
    "SECUENCIA" integer NOT NULL,
    "CUENTA" character varying(50),
    "CLAVE" character varying(50),
    "TIPO" character(1),
    "SECUENCIA_PERSONA" integer,
    "ESTADO" character(1)
);


ALTER TABLE "USUARIOS" OWNER TO postgres;

--
-- TOC entry 2176 (class 0 OID 0)
-- Dependencies: 182
-- Name: TABLE "USUARIOS"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE "USUARIOS" IS 'TABLA PARA LA GESTION DE LA INFORMACION DE LOS USUARIOS QUE TIENEN ACCESO A LA APLICACION';


--
-- TOC entry 2177 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."SECUENCIA"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."SECUENCIA" IS 'Consecutivo de la tabla Usuarios';


--
-- TOC entry 2178 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."CUENTA"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."CUENTA" IS 'NOMBRE DE USUARIO USADO PARA INICIAR SESION';


--
-- TOC entry 2179 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."CLAVE"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."CLAVE" IS 'clave de inicio de sesion de la aplicacion';


--
-- TOC entry 2180 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."TIPO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."TIPO" IS 'tipo de usuario que manejara la aplicacion ';


--
-- TOC entry 2181 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."SECUENCIA_PERSONA"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."SECUENCIA_PERSONA" IS 'secuencia de la persona asociada a la cuenta, llave foranea de la tabla personas';


--
-- TOC entry 2182 (class 0 OID 0)
-- Dependencies: 182
-- Name: COLUMN "USUARIOS"."ESTADO"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "USUARIOS"."ESTADO" IS 'estado de los usuarios que inicien sesion en la aplicacion';


--
-- TOC entry 2140 (class 0 OID 16427)
-- Dependencies: 184
-- Data for Name: CARTERA; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "CARTERA" ("SECUENCIA", "SECUENCIA_USUARIO", "SECUENCIA_CLIENTE", "FECHA_PAGO", "FECHA_HORA_ACTUALIZACION") FROM stdin;
1	\N	5	2015-10-01	2016-02-11
2	\N	6	2015-09-01	2016-01-11
3	\N	7	2015-08-01	2016-02-01
\.


--
-- TOC entry 2142 (class 0 OID 16437)
-- Dependencies: 186
-- Data for Name: CLIENTES; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "CLIENTES" ("SECUENCIA", "NOMBRE_RAZON_SOCIAL", "NIT", "SECUENCIA_PERSONA") FROM stdin;
5	SURTIGAS	\N	\N
6	AGUAS DE CARTAGENA	\N	\N
7	COMFENALCO	\N	\N
\.


--
-- TOC entry 2141 (class 0 OID 16432)
-- Dependencies: 185
-- Data for Name: DETALLE_CARTERA; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "DETALLE_CARTERA" ("SECUENCIA", "SECUENCIA_CARTERA", "VENCIMIENTO", "REFERENCIA", "NRO_FACTURA", "VALOR_FACTURA") FROM stdin;
1	1	2015-02-01	\N	FA-0000002039	16707421.57
2	1	2015-03-01	\N	FA-0000002041	12477470.02
3	1	2015-04-01	\N	FA-0000002042	49898998
\.


--
-- TOC entry 2136 (class 0 OID 16393)
-- Dependencies: 180
-- Data for Name: INDICADORES; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "INDICADORES" ("SECUENCIA_INDICADOR", "SECUENCIA_USUARIO", "NOMBRE", "OBJETIVO_ESPERADO", "OBJETIVO_REAL", "DESVIACION", "SECUENCA_UNIDAD", "DOCUMENTACION") FROM stdin;
1	1	indicador 1	100	80	20	1	\N
2	2	indicador 2	50	10	40	2	\N
\.


--
-- TOC entry 2139 (class 0 OID 16414)
-- Dependencies: 183
-- Data for Name: PERSONAS; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "PERSONAS" ("SECUENCIA", "NOMBRE", "APELLIDOS", "TIPO", "IDENTIFICACION", "SECUENCIA_TIPO_IDENTIFICACION", "CORREO_ELECTRONICO", "DIRECCION", "TELEFONO", "CELLULAR") FROM stdin;
1	TATIANA 	HERÁNDEZ	\N	\N	\N	\N	\N	\N	\N
2	KELLY	CASTILLO	\N	\N	\N	\N	\N	\N	\N
3	JHERSON	ARROYO	\N	\N	\N	\N	\N	\N	\N
4	DIANA	RODRIGUEZ	\N	\N	\N	\N	\N	\N	\N
5	SURTIGAS	\N	\N	\N	\N	\N	\N	\N	\N
6	AGUAS DE CARTAGENA	\N	\N	\N	\N	\N	\N	\N	\N
7	COMFENALCO	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 2137 (class 0 OID 16401)
-- Dependencies: 181
-- Data for Name: UNIDADES; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "UNIDADES" ("SECUENCIA_UNIDAD", "SIMBOLO", "NOMBRE", "DOCUMENTACION") FROM stdin;
\.


--
-- TOC entry 2138 (class 0 OID 16409)
-- Dependencies: 182
-- Data for Name: USUARIOS; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "USUARIOS" ("SECUENCIA", "CUENTA", "CLAVE", "TIPO", "SECUENCIA_PERSONA", "ESTADO") FROM stdin;
1	THERNANDEZ	\N	\N	1	\N
2	KCASTILLO	\N	\N	2	\N
3	JARROYO	\N	\N	3	\N
4	DRODRIGUZ	\N	\N	4	\N
\.


--
-- TOC entry 2016 (class 2606 OID 16431)
-- Name: PK_CARTERA; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CARTERA"
    ADD CONSTRAINT "PK_CARTERA" PRIMARY KEY ("SECUENCIA");


--
-- TOC entry 2020 (class 2606 OID 16441)
-- Name: PK_CLIENTES; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "CLIENTES"
    ADD CONSTRAINT "PK_CLIENTES" PRIMARY KEY ("SECUENCIA");


--
-- TOC entry 2018 (class 2606 OID 16436)
-- Name: PK_DETALLE_CARTERA; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "DETALLE_CARTERA"
    ADD CONSTRAINT "PK_DETALLE_CARTERA" PRIMARY KEY ("SECUENCIA");


--
-- TOC entry 2008 (class 2606 OID 16397)
-- Name: PK_INDICADOR; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "INDICADORES"
    ADD CONSTRAINT "PK_INDICADOR" PRIMARY KEY ("SECUENCIA_INDICADOR");


--
-- TOC entry 2014 (class 2606 OID 16421)
-- Name: PK_PERSONAS; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "PERSONAS"
    ADD CONSTRAINT "PK_PERSONAS" PRIMARY KEY ("SECUENCIA");


--
-- TOC entry 2010 (class 2606 OID 16408)
-- Name: PK_UNIDADES; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "UNIDADES"
    ADD CONSTRAINT "PK_UNIDADES" PRIMARY KEY ("SECUENCIA_UNIDAD");


--
-- TOC entry 2012 (class 2606 OID 16413)
-- Name: PK_USUARIO; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "USUARIOS"
    ADD CONSTRAINT "PK_USUARIO" PRIMARY KEY ("SECUENCIA");


--
-- TOC entry 2021 (class 2606 OID 16422)
-- Name: FK_USUARIO_PERSONA; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "USUARIOS"
    ADD CONSTRAINT "FK_USUARIO_PERSONA" FOREIGN KEY ("SECUENCIA_PERSONA") REFERENCES "PERSONAS"("SECUENCIA") ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 2150 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-02-14 13:18:03

--
-- PostgreSQL database dump complete
--

