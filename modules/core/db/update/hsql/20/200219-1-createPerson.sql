create table TECHNIKA_PERSON (
    ID varchar(36) not null,
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
    OFFICE_ID varchar(36),
    POST_ID varchar(36),
    --
    primary key (ID)
);