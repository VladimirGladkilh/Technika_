-- begin TECHNIKA_POST
create table TECHNIKA_POST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_POST
-- begin TECHNIKA_OFFICE
create table TECHNIKA_OFFICE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CITY varchar(255),
    STREET varchar(255),
    HOUSE varchar(20),
    OFFICE varchar(20),
    --
    primary key (ID)
)^
-- end TECHNIKA_OFFICE
-- begin TECHNIKA_COST
create table TECHNIKA_COST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUM varchar(255) not null,
    DAT date not null,
    CONTRAGENT_ID uuid not null,
    MAIN_DOC_ID uuid,
    SUBJECT varchar(512) not null,
    COST_PRICE double precision,
    UPD_ID uuid,
    PAYER_ID uuid,
    --
    primary key (ID)
)^
-- end TECHNIKA_COST
-- begin TECHNIKA_VENDOR
create table TECHNIKA_VENDOR (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_VENDOR
-- begin TECHNIKA_COMPONENT
create table TECHNIKA_COMPONENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DEVICE_ID uuid not null,
    PRIM varchar(512),
    SERIAL_NUMBER varchar(255) not null,
    PRICE double precision,
    LOW_PRICE double precision,
    BUSY boolean,
    COSTNAME_ID uuid,
    --
    primary key (ID)
)^
-- end TECHNIKA_COMPONENT
-- begin TECHNIKA_DEVICE_TYPE
create table TECHNIKA_DEVICE_TYPE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end TECHNIKA_DEVICE_TYPE
-- begin TECHNIKA_TECHIKA
create table TECHNIKA_TECHIKA (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_TECHIKA
-- begin TECHNIKA_MOVEMENT
create table TECHNIKA_MOVEMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PERSON_ID uuid not null,
    TECHNIKA_ID uuid not null,
    START_DATE date not null,
    END_DATE date,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_MOVEMENT
-- begin TECHNIKA_PAYER
create table TECHNIKA_PAYER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_PAYER
-- begin TECHNIKA_DEVICE
create table TECHNIKA_DEVICE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TYPE_ID uuid not null,
    VENDOR_ID uuid not null,
    MODEL varchar(255) not null,
    PRIM varchar(512),
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_DEVICE
-- begin TECHNIKA_PERSON
create table TECHNIKA_PERSON (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    FAMILIA varchar(255) not null,
    IMYA varchar(255) not null,
    OTCHESTVO varchar(255),
    EMAIL varchar(50),
    PHONE varchar(50),
    OFFICE_ID uuid,
    POST_ID uuid,
    --
    primary key (ID)
)^
-- end TECHNIKA_PERSON
-- begin TECHNIKA_EQUIPMENT
create table TECHNIKA_EQUIPMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TECHNIKA_ID uuid not null,
    COMPONENT_ID uuid not null,
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_EQUIPMENT
-- begin TECHNIKA_CONTRAGENT
create table TECHNIKA_CONTRAGENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CONTACT varchar(255),
    PHONE varchar(255),
    PRIM varchar(255),
    --
    primary key (ID)
)^
-- end TECHNIKA_CONTRAGENT
