-- Database: "aaa_monitor"

-- CREATE DATABASE "aaa_monitor";


-- Table: t_aaa_monitor_log

-- DROP TABLE t_aaa_monitor_log;

CREATE TABLE t_aaa_monitor_log
(
  id character varying(32) NOT NULL,
  lo_createdate timestamp without time zone,
  log_comment character varying(50),
  log_content character varying(50),
  log_type character varying(50),
  CONSTRAINT t_aaa_monitor_log_pkey PRIMARY KEY (id)
);

-- Table: t_aaa_monitor_message

-- DROP TABLE t_aaa_monitor_message;

CREATE TABLE t_aaa_monitor_message
(
  id character varying(32) NOT NULL,
  lo_createdate timestamp without time zone,
  monitor_destaddr character varying(50),
  log_date timestamp without time zone,
  monitor_msgcontent character varying(2000),
  monitor_msgtype character varying(50),
  monitor_oriaddr character varying(50),
  CONSTRAINT t_aaa_monitor_message_pkey PRIMARY KEY (id)
)