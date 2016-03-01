--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: courses; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE courses (
    id integer NOT NULL,
    name character varying,
    course_number integer,
    department_id integer
);


ALTER TABLE courses OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_id_seq OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE courses_id_seq OWNED BY courses.id;


--
-- Name: departments; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE departments (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE departments OWNER TO "Guest";

--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE departments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE departments_id_seq OWNER TO "Guest";

--
-- Name: departments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE departments_id_seq OWNED BY departments.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE students (
    id integer NOT NULL,
    name character varying,
    enrollment_date character varying,
    major character varying
);


ALTER TABLE students OWNER TO "Guest";

--
-- Name: students_courses; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE students_courses (
    id integer NOT NULL,
    student_id integer,
    course_id integer,
    completed boolean
);


ALTER TABLE students_courses OWNER TO "Guest";

--
-- Name: students_courses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE students_courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE students_courses_id_seq OWNER TO "Guest";

--
-- Name: students_courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE students_courses_id_seq OWNED BY students_courses.id;


--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE students_id_seq OWNER TO "Guest";

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE students_id_seq OWNED BY students.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY courses ALTER COLUMN id SET DEFAULT nextval('courses_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY departments ALTER COLUMN id SET DEFAULT nextval('departments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY students ALTER COLUMN id SET DEFAULT nextval('students_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY students_courses ALTER COLUMN id SET DEFAULT nextval('students_courses_id_seq'::regclass);


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY courses (id, name, course_number, department_id) FROM stdin;
9	Math for Dummies	101	3
10	History for Smart People	500	4
\.


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_id_seq', 10, true);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY departments (id, name) FROM stdin;
3	Maths
4	History
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('departments_id_seq', 4, true);


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY students (id, name, enrollment_date, major) FROM stdin;
4	Matt	2016-03-01	Maths
5	Jacob	2016-03-02	History
\.


--
-- Data for Name: students_courses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY students_courses (id, student_id, course_id, completed) FROM stdin;
10	4	9	t
12	5	9	t
11	5	10	t
\.


--
-- Name: students_courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('students_courses_id_seq', 12, true);


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('students_id_seq', 5, true);


--
-- Name: courses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: departments_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: students_courses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY students_courses
    ADD CONSTRAINT students_courses_pkey PRIMARY KEY (id);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

