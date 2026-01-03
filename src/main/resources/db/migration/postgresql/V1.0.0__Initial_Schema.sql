CREATE TABLE public.account (
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    user_id uuid,
    password character varying(255),
    role character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    CONSTRAINT account_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'WAITER'::character varying, 'CASHIER'::character varying])::text[]))),
    CONSTRAINT account_status_check CHECK (((status)::text = ANY ((ARRAY['PENDING_SETUP'::character varying, 'ACTIVE'::character varying, 'LOCKED'::character varying, 'DISABLED'::character varying])::text[])))
);

CREATE TABLE public.cashsession (
    difference numeric(38,2),
    initialbalance numeric(38,2) NOT NULL,
    reportedbalance numeric(38,2),
    totalsales numeric(38,2),
    closingdate timestamp(6) without time zone,
    openingdate timestamp(6) without time zone NOT NULL,
    closed_by_id uuid,
    id uuid NOT NULL,
    opened_by_id uuid,
    note text,
    status character varying(255),
    CONSTRAINT cashsession_status_check CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'CLOSED'::character varying])::text[])))
);

CREATE TABLE public.category (
    availablefrom date NOT NULL,
    availablefromtime time(0) without time zone NOT NULL,
    availableuntil date NOT NULL,
    availableuntiltime time(0) without time zone NOT NULL,
    isenabled boolean NOT NULL,
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE public.category_available_days (
    category_id uuid NOT NULL,
    day_of_week character varying(255) NOT NULL,
    CONSTRAINT category_available_days_day_of_week_check CHECK (((day_of_week)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])))
);

CREATE TABLE public.category_product (
    category_id uuid NOT NULL,
    product_id uuid NOT NULL
);

CREATE TABLE public.floor (
    gridheight integer NOT NULL,
    gridwidth integer NOT NULL,
    levelorder integer NOT NULL,
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE public.invitation (
    createdat timestamp(6) without time zone NOT NULL,
    expiresat timestamp(6) without time zone NOT NULL,
    usedat timestamp(6) without time zone,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    token character varying(255) NOT NULL
);

CREATE TABLE public.payment (
    amount numeric(38,2) NOT NULL,
    changegiven numeric(38,2),
    createdat timestamp(6) without time zone NOT NULL,
    id uuid NOT NULL,
    ticket_id uuid NOT NULL,
    externalreference character varying(255),
    method character varying(255) NOT NULL,
    CONSTRAINT payment_method_check CHECK (((method)::text = ANY ((ARRAY['CASH'::character varying, 'CREDIT_CARD'::character varying, 'DEBIT_CARD'::character varying, 'TRANSFER'::character varying, 'OTHER'::character varying])::text[])))
);

CREATE TABLE public.product (
    availablefrom date NOT NULL,
    availablefromtime time(0) without time zone NOT NULL,
    availableuntil date NOT NULL,
    availableuntiltime time(0) without time zone NOT NULL,
    isenabled boolean NOT NULL,
    price numeric(38,2) NOT NULL,
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

CREATE TABLE public.product_available_days (
    product_id uuid NOT NULL,
    day_of_week character varying(255) NOT NULL,
    CONSTRAINT product_available_days_day_of_week_check CHECK (((day_of_week)::text = ANY ((ARRAY['MONDAY'::character varying, 'TUESDAY'::character varying, 'WEDNESDAY'::character varying, 'THURSDAY'::character varying, 'FRIDAY'::character varying, 'SATURDAY'::character varying, 'SUNDAY'::character varying])::text[])))
);

CREATE TABLE public.restauranttable (
    height integer,
    posx integer NOT NULL,
    posy integer NOT NULL,
    width integer,
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    floor_id uuid,
    id uuid NOT NULL,
    ticket_id uuid,
    name character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    CONSTRAINT restauranttable_status_check CHECK (((status)::text = ANY ((ARRAY['AVAILABLE'::character varying, 'OCCUPIED'::character varying, 'RESERVED'::character varying, 'CLEANING'::character varying, 'NOT_AVAILABLE'::character varying])::text[])))
);

CREATE TABLE public.ticket (
    dinercount integer NOT NULL,
    totalamount numeric(38,2) NOT NULL,
    closedat timestamp(6) without time zone,
    createdat timestamp(6) without time zone,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    status character varying(255) NOT NULL,
    tablesnapshot json,
    CONSTRAINT ticket_status_check CHECK (((status)::text = ANY ((ARRAY['OPEN'::character varying, 'PAID'::character varying, 'CANCELED'::character varying])::text[])))
);

CREATE TABLE public.ticketitem (
    unitprice numeric(38,2) NOT NULL,
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    author_id uuid NOT NULL,
    id uuid NOT NULL,
    originalproductid uuid,
    ticket_id uuid,
    productname character varying(255) NOT NULL,
    status character varying(255) NOT NULL,
    productsnapshot json,
    CONSTRAINT ticketitem_status_check CHECK (((status)::text = ANY ((ARRAY['ADDED'::character varying, 'ORDERED'::character varying, 'SERVED'::character varying, 'PAID'::character varying, 'CANCELED'::character varying])::text[])))
);

CREATE TABLE public.user_pos (
    createdat timestamp(6) without time zone NOT NULL,
    updatedat timestamp(6) without time zone,
    id uuid NOT NULL,
    email character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_user_id_key UNIQUE (user_id);

ALTER TABLE ONLY public.account
    ADD CONSTRAINT account_username_key UNIQUE (username);

ALTER TABLE ONLY public.cashsession
    ADD CONSTRAINT cashsession_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.category_available_days
    ADD CONSTRAINT category_available_days_pkey PRIMARY KEY (category_id, day_of_week);

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.category_product
    ADD CONSTRAINT category_product_pkey PRIMARY KEY (category_id, product_id);

ALTER TABLE ONLY public.floor
    ADD CONSTRAINT floor_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.invitation
    ADD CONSTRAINT invitation_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.invitation
    ADD CONSTRAINT invitation_token_key UNIQUE (token);

ALTER TABLE ONLY public.payment
    ADD CONSTRAINT payment_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.product_available_days
    ADD CONSTRAINT product_available_days_pkey PRIMARY KEY (product_id, day_of_week);

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.restauranttable
    ADD CONSTRAINT restauranttable_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.restauranttable
    ADD CONSTRAINT restauranttable_ticket_id_key UNIQUE (ticket_id);

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.ticketitem
    ADD CONSTRAINT ticketitem_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_pos
    ADD CONSTRAINT user_pos_email_key UNIQUE (email);

ALTER TABLE ONLY public.user_pos
    ADD CONSTRAINT user_pos_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT fk1vdl14um7axd8rc6daho7atfu FOREIGN KEY (user_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.product_available_days
    ADD CONSTRAINT fk2ppoobvaj1s8l5xs9oxmowft0 FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.category_product
    ADD CONSTRAINT fk55qix17xkimf4qtvdesj17igh FOREIGN KEY (product_id) REFERENCES public.product(id);

ALTER TABLE ONLY public.category_product
    ADD CONSTRAINT fk93baldcqyk0pnf4lbfrq4lib5 FOREIGN KEY (category_id) REFERENCES public.category(id);

ALTER TABLE ONLY public.payment
    ADD CONSTRAINT fka8060tn2ahf51m5dkujnperot FOREIGN KEY (ticket_id) REFERENCES public.ticket(id);

ALTER TABLE ONLY public.cashsession
    ADD CONSTRAINT fkhkkiy8iaeud3ea5aqk4tdk1qc FOREIGN KEY (opened_by_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.ticketitem
    ADD CONSTRAINT fkiye407wpveos507r9fpnmsom9 FOREIGN KEY (ticket_id) REFERENCES public.ticket(id);

ALTER TABLE ONLY public.restauranttable
    ADD CONSTRAINT fkjqqmn2ahevt8h7isfhc2mr0h1 FOREIGN KEY (ticket_id) REFERENCES public.ticket(id);

ALTER TABLE ONLY public.cashsession
    ADD CONSTRAINT fkjs8t67ihrfo72g5xlhubs5s3e FOREIGN KEY (closed_by_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.restauranttable
    ADD CONSTRAINT fklgko8wgq5g9uttdtbptnteadq FOREIGN KEY (floor_id) REFERENCES public.floor(id);

ALTER TABLE ONLY public.account
    ADD CONSTRAINT fkokmc0m1yico9hmte4w16wgjvw FOREIGN KEY (user_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.ticketitem
    ADD CONSTRAINT fkonfxjpkeh0hkc3pkbd7v6bfci FOREIGN KEY (author_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.invitation
    ADD CONSTRAINT fkqhri2ver75ouob5verv508c6t FOREIGN KEY (user_id) REFERENCES public.user_pos(id);

ALTER TABLE ONLY public.category_available_days
    ADD CONSTRAINT fkss606wiif65x2ia8tfqwon4bn FOREIGN KEY (category_id) REFERENCES public.category(id);
