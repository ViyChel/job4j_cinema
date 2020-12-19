CREATE DATABASE cinema;

\c cinema;

CREATE TABLE public.halls
(
    id integer NOT NULL DEFAULT nextval('halls_id_seq'::regclass),
    "row" integer NOT NULL,
    place integer NOT NULL,
    status_free boolean,
    price integer,
    CONSTRAINT halls_pkey PRIMARY KEY ("row", place)
);

CREATE TABLE public.accounts
(
    id integer NOT NULL DEFAULT nextval('accounts_id_seq'::regclass),
    name character varying(200) COLLATE pg_catalog."default",
    phone character varying(20) COLLATE pg_catalog."default",
    halls_row integer,
    halls_place integer,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT accounts_halls_fkey FOREIGN KEY (halls_place, halls_row)
        REFERENCES public.halls (place, "row") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);