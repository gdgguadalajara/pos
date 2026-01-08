create table Account (
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    user_id uuid unique,
    password varchar(255),
    role varchar(255) not null check ((role in ('ADMIN','WAITER','CASHIER','PREPARER'))),
    status varchar(255) not null check ((status in ('PENDING_SETUP','ACTIVE','LOCKED','DISABLED'))),
    username varchar(255) not null unique,
    primary key (id)
);

create table account_production_center (
    account_id uuid not null,
    production_center_id uuid not null,
    primary key (account_id, production_center_id)
);

create table BusinessProfile (
    id uuid not null,
    address varchar(500),
    ticketFooter varchar(1000),
    email varchar(255),
    legalName varchar(255) not null,
    logoUrl varchar(255),
    name varchar(255) not null,
    phone varchar(255),
    taxId varchar(255),
    website varchar(255),
    primary key (id)
);

create table CashSession (
    difference numeric(38,2),
    initialBalance numeric(38,2) not null,
    reportedBalance numeric(38,2),
    totalExpenses numeric(38,2),
    totalSales numeric(38,2),
    closingDate timestamp(6),
    openingDate timestamp(6) not null,
    closed_by_id uuid,
    id uuid not null,
    opened_by_id uuid,
    note TEXT,
    status varchar(255) check ((status in ('OPEN','CLOSED'))),
    primary key (id)
);

create table Category (
    availableFrom date not null,
    availableFromTime time(0) not null,
    availableUntil date not null,
    availableUntilTime time(0) not null,
    isEnabled boolean not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    production_center_id uuid not null,
    description varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

create table category_available_days (
    category_id uuid not null,
    day_of_week varchar(255) not null check ((day_of_week in ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'))),
    primary key (category_id, day_of_week)
);

create table category_product (
    category_id uuid not null,
    product_id uuid not null,
    primary key (category_id, product_id)
);

create table Expense (
    amount numeric(38,2) not null,
    createdAt timestamp(6) not null,
    cash_session_id uuid,
    created_by_user_id uuid not null,
    id uuid not null,
    category varchar(255) not null check ((category in ('SUPPLIES','CLEANING','MAINTENANCE','WAGES','OTHER'))),
    description varchar(255) not null,
    primary key (id)
);

create table Floor (
    gridHeight integer not null,
    gridWidth integer not null,
    levelOrder integer not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    name varchar(255) not null,
    primary key (id)
);

create table Invitation (
    createdAt timestamp(6) not null,
    expiresAt timestamp(6) not null,
    usedAt timestamp(6),
    id uuid not null,
    user_id uuid not null,
    token varchar(255) not null unique,
    primary key (id)
);

create table Payment (
    amount numeric(38,2) not null,
    changeGiven numeric(38,2),
    createdAt timestamp(6) not null,
    cash_session_id uuid not null,
    id uuid not null,
    ticket_id uuid not null,
    externalReference varchar(255),
    method varchar(255) not null check ((method in ('CASH','CREDIT_CARD','DEBIT_CARD','TRANSFER','OTHER'))),
    primary key (id)
);

create table Product (
    availableFrom date not null,
    availableFromTime time(0) not null,
    availableUntil date not null,
    availableUntilTime time(0) not null,
    isEnabled boolean not null,
    price numeric(38,2) not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    production_center_id uuid,
    description varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

create table product_available_days (
    product_id uuid not null,
    day_of_week varchar(255) not null check ((day_of_week in ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'))),
    primary key (product_id, day_of_week)
);

create table ProductionCenter (
    isActive boolean not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    description varchar(255),
    name varchar(255) not null unique,
    primary key (id)
);

create table RestaurantTable (
    height integer,
    posX integer not null,
    posY integer not null,
    width integer,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    floor_id uuid,
    id uuid not null,
    ticket_id uuid unique,
    name varchar(255) not null,
    status varchar(255) not null check ((status in ('AVAILABLE','OCCUPIED','RESERVED','CLEANING','NOT_AVAILABLE'))),
    primary key (id)
);

create table Ticket (
    dinerCount integer not null,
    totalAmount numeric(38,2) not null,
    closedAt timestamp(6),
    createdAt timestamp(6),
    updatedAt timestamp(6),
    id uuid not null,
    user_id uuid not null,
    serviceType varchar(255) not null check ((serviceType in ('DINE_IN','TAKE_AWAY','DELIVERY'))),
    status varchar(255) not null check ((status in ('OPEN','PAID','CANCELED'))),
    tableSnapshot json,
    primary key (id)
);

create table TicketItem (
    isTakeAway boolean not null,
    unitPrice numeric(38,2) not null,
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    author_id uuid not null,
    id uuid not null,
    originalProductId uuid not null,
    ticket_id uuid,
    productName varchar(255) not null,
    status varchar(255) not null check ((status in ('ADDED','ORDERED','PREPARING','READY','DELIVERED','CANCELED'))),
    productSnapshot json,
    primary key (id)
);

create table user_pos (
    createdAt timestamp(6) not null,
    updatedAt timestamp(6),
    id uuid not null,
    email varchar(255) not null unique,
    name varchar(255) not null,
    primary key (id)
);

alter table if exists Account 
    add constraint FKokmc0m1yico9hmte4w16wgjvw 
    foreign key (user_id) 
    references user_pos;

alter table if exists account_production_center 
    add constraint FK7615g58vstvl0lny8i6p6e97t 
    foreign key (production_center_id) 
    references ProductionCenter;

alter table if exists account_production_center 
    add constraint FK57q8e3wyk813e0ghlnelb11po 
    foreign key (account_id) 
    references Account;

alter table if exists CashSession 
    add constraint FKjs8t67ihrfo72g5xlhubs5s3e 
    foreign key (closed_by_id) 
    references user_pos;

alter table if exists CashSession 
    add constraint FKhkkiy8iaeud3ea5aqk4tdk1qc 
    foreign key (opened_by_id) 
    references user_pos;

alter table if exists Category 
    add constraint FK9ec3m11c3hbjvtdv1w1nfac8l 
    foreign key (production_center_id) 
    references ProductionCenter;

alter table if exists category_available_days 
    add constraint FKss606wiif65x2ia8tfqwon4bn 
    foreign key (category_id) 
    references Category;

alter table if exists category_product 
    add constraint FK55qix17xkimf4qtvdesj17igh 
    foreign key (product_id) 
    references Product;

alter table if exists category_product 
    add constraint FK93baldcqyk0pnf4lbfrq4lib5 
    foreign key (category_id) 
    references Category;

alter table if exists Expense 
    add constraint FK9ag9pnitm8lndgy7o99i1lf3i 
    foreign key (cash_session_id) 
    references CashSession;

alter table if exists Expense 
    add constraint FKlh3yf9skcfid2d1bhfm416kkk 
    foreign key (created_by_user_id) 
    references user_pos;

alter table if exists Invitation 
    add constraint FKqhri2ver75ouob5verv508c6t 
    foreign key (user_id) 
    references user_pos;

alter table if exists Payment 
    add constraint FK1pkgyxlik7sj7ubboyygk8xc1 
    foreign key (cash_session_id) 
    references CashSession;

alter table if exists Payment 
    add constraint FKa8060tn2ahf51m5dkujnperot 
    foreign key (ticket_id) 
    references Ticket;

alter table if exists Product 
    add constraint FKeonoylap4aogqeewa57rf37qa 
    foreign key (production_center_id) 
    references ProductionCenter;

alter table if exists product_available_days 
    add constraint FK2ppoobvaj1s8l5xs9oxmowft0 
    foreign key (product_id) 
    references Product;

alter table if exists RestaurantTable 
    add constraint FKlgko8wgq5g9uttdtbptnteadq 
    foreign key (floor_id) 
    references Floor;

alter table if exists RestaurantTable 
    add constraint FKjqqmn2ahevt8h7isfhc2mr0h1 
    foreign key (ticket_id) 
    references Ticket;

alter table if exists Ticket 
    add constraint FK1vdl14um7axd8rc6daho7atfu 
    foreign key (user_id) 
    references user_pos;

alter table if exists TicketItem 
    add constraint FKonfxjpkeh0hkc3pkbd7v6bfci 
    foreign key (author_id) 
    references user_pos;

alter table if exists TicketItem 
    add constraint FKiye407wpveos507r9fpnmsom9 
    foreign key (ticket_id) 
    references Ticket;
