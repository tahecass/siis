--
-- PostgreSQL database cluster dump
--

-- Started on 2016-02-11 22:42:14

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md5244af1e2823d5eaeeffc42c5096d8260';






-- Completed on 2016-02-11 22:42:14

--
-- PostgreSQL database cluster dump complete
--

