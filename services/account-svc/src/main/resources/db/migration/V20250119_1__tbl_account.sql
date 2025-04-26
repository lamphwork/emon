create table if not exists accounts
(
    id          varchar(100) primary key,
    username    varchar(300) not null,
    password    varchar(200) not null,
    status      varchar(50)  not null,
    reason      varchar(500),
    create_time timestamptz  not null,
    update_time timestamptz
);
create unique index idx_username_uq on accounts (username);