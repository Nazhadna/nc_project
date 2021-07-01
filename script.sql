CREATE TABLE public.gender
(
    id uuid NOT NULL,
    name "char",
    CONSTRAINT gender_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.gender
    OWNER to postgres;

INSERT INTO gender VALUES (uuid_generate_v4(), 'm');
INSERT INTO gender VALUES (uuid_generate_v4(), 'f');



CREATE TABLE public.client
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    age integer,
    gender_id uuid,
    email text COLLATE pg_catalog."default",
    password text COLLATE pg_catalog."default",
    CONSTRAINT client_pkey PRIMARY KEY (id),
    CONSTRAINT client_gender_id_fkey FOREIGN KEY (gender_id)
        REFERENCES public.gender (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.client
    OWNER to postgres;






CREATE TABLE public.country
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT country_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.country
    OWNER to postgres;
	
INSERT INTO country VALUES (uuid_generate_v4(), 'Italy');
INSERT INTO country VALUES (uuid_generate_v4(), 'Ukraine');
INSERT INTO country VALUES (uuid_generate_v4(), 'Greece');





CREATE TABLE public.dish
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    description text COLLATE pg_catalog."default",
    recipe text COLLATE pg_catalog."default",
    country_id uuid,
    CONSTRAINT dish_pkey PRIMARY KEY (id),
    CONSTRAINT dish_country_id_fkey FOREIGN KEY (country_id)
        REFERENCES public.country (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.dish
    OWNER to postgres;




CREATE TABLE public.client_dish
(
    id uuid NOT NULL,
    client_id uuid,
    dish_id uuid,
    CONSTRAINT client_dish_pkey PRIMARY KEY (id),
    CONSTRAINT client_dish_client_id_fkey FOREIGN KEY (client_id)
        REFERENCES public.client (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT client_dish_dish_id_fkey FOREIGN KEY (dish_id)
        REFERENCES public.dish (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.client_dish
    OWNER to postgres;



CREATE TABLE public.place
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT place_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.place
    OWNER to postgres;

INSERT INTO place VALUES (uuid_generate_v4(), 'fridge');
INSERT INTO place VALUES (uuid_generate_v4(), 'freezer');
INSERT INTO place VALUES (uuid_generate_v4(), 'no fridge');
INSERT INTO place VALUES (uuid_generate_v4(), 'no matter');





CREATE TABLE public.product
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    lifetime integer,
    place_id uuid,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_place_id_fkey FOREIGN KEY (place_id)
        REFERENCES public.place (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.product
    OWNER to postgres;



CREATE TABLE public.units
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default",
    CONSTRAINT units_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.units
    OWNER to postgres;


INSERT INTO units VALUES (uuid_generate_v4(), 'grams');
INSERT INTO units VALUES (uuid_generate_v4(), 'pieces');




CREATE TABLE public.dish_product
(
    id uuid NOT NULL,
    product_id uuid,
    dish_id uuid,
    quantity integer,
    units_id uuid,
    CONSTRAINT dish_product_pkey PRIMARY KEY (id),
    CONSTRAINT dish_product_dish_id_fkey FOREIGN KEY (dish_id)
        REFERENCES public.dish (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT dish_product_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT dish_product_units_id_fkey FOREIGN KEY (units_id)
        REFERENCES public.units (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.dish_product
    OWNER to postgres;





CREATE TABLE public.stored_items
(
    id uuid NOT NULL,
    client_id uuid,
    product_id uuid,
    expiration_date date,
    CONSTRAINT available_products_pkey PRIMARY KEY (id),
    CONSTRAINT available_products_client_id_fkey FOREIGN KEY (client_id)
        REFERENCES public.client (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT available_products_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.stored_items
    OWNER to postgres;
