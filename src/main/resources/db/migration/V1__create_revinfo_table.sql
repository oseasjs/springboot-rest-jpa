create table audit.revinfo (
    rev bigint generated by default as identity,
    revtstmp int8,
    username VARCHAR(255),
    primary key (rev)
);