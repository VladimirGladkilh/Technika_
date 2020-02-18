create table TECHNIKA_COST (
    ID varchar(36) not null,
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
    CONTRAGENT_ID varchar(36) not null,
    MAIN_DOC_ID varchar(36),
    SUBJECT varchar(512) not null,
    COST_PRICE double precision,
    UPD_ID varchar(36),
    PAYER_ID varchar(36),
    --
    primary key (ID)
);