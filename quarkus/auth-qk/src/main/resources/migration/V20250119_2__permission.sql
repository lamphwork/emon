-- table roles
create table if not exists roles
(
    name        varchar(200) primary key,
    description varchar(500),
    active      bool not null default true
);

-- table permissions
create table if not exists permissions
(
    name        varchar(200) primary key,
    description varchar(500),
    active      bool not null
);

-- table role_permissions
create table if not exists role_permissions
(
    id              varchar(100) primary key,
    role_name       varchar(200) not null,
    permission_name varchar(200) not null
);
create index idx_role_permission_1 on role_permissions (role_name);

-- table account_roles
create table if not exists account_roles
(
    id         varchar(100) primary key,
    account_id varchar(100) not null,
    role_name  varchar(200) not null,
    active     bool         not null default true
);
create index idx_account_roles_1 on account_roles (account_id);

-- table account_permissions
create table if not exists account_permissions
(
    id              varchar(100) primary key,
    account_id      varchar(100) not null,
    permission_name varchar(200) not null,
    active          bool         not null
);
create index idx_account_permissions_1 on account_permissions (account_id);
