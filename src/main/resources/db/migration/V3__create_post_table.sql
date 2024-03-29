create table blog.post (
    id bigint generated by default as identity,
    title varchar(255) not null,
    content varchar(255) not null,
    creation_date timestamp not null,
    generated_type VARCHAR(255) not null,
    moderation_date timestamp null,
    moderation_reason varchar(255) null,
    created_by varchar(255) not null,
    created_date timestamp not null,
    last_modified_by varchar(255) not null,
    last_modified_date timestamp not null,
    version bigint not null,
    primary key (id)
);

create unique index blog.post_index on blog.post (title, content);

create table audit.post_aud (
    id int8 not null,
    rev int4 not null,
    revtype int2,
    title varchar(255),
    content varchar(255),
    creation_date timestamp,
    generated_type VARCHAR(255),
    moderation_date timestamp null,
    moderation_reason varchar(255) null,
    created_by varchar(255),
    created_date timestamp,
    last_modified_by varchar(255),
    last_modified_date timestamp,
    version bigint,
    primary key (id, rev)
);