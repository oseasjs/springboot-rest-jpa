create table blog.user_auth (
    id bigint generated by default as identity,
    username varchar(255) not null,
    password varchar(255) not null,
    created_by varchar(255) not null,
    created_date timestamp not null,
    last_modified_by varchar(255) not null,
    last_modified_date timestamp not null,
    version bigint not null,
    primary key (id)
);

create unique index blog.user_auth_index on blog.user_auth (username);

create table audit.user_auth_aud (
    id int8 not null,
    rev int4 not null,
    revtype int2,
    username varchar(255),
    password varchar(255),
    created_by varchar(255),
    created_date timestamp,
    last_modified_by varchar(255),
    last_modified_date timestamp,
    version bigint,
    primary key (id, rev)
);