drop table if exists meeting cascade;
drop table if exists meeting_pending_attendee_ids;
drop table if exists meeting_attendee_ids cascade;
drop table if exists meeting_refused_attendee_ids;
drop table if exists schedule cascade;
drop table if exists slack_user cascade;
drop table if exists users cascade;

create table users
(
    id                      uuid not null,
    created_at              timestamp(6),
    updated_at              timestamp(6),
    slack_user_id           uuid unique,
    has_slack_link_approval boolean,
    primary key (id)
);

create table slack_user
(
    id           uuid not null,
    created_at   timestamp(6),
    updated_at   timestamp(6),
    display_name varchar(255),
    email        varchar(255),
    image_origin varchar(255),
    real_name    varchar(255),
    slack_id     varchar(255),
    primary key (id)
);

create table schedule
(
    created_at timestamp(6),
    updated_at timestamp(6),
    id         uuid        not null,
    dtype      varchar(31) not null,
    primary key (id)
);

create table meeting
(
    id               uuid not null,
    begin_at         timestamp(6),
    end_at           timestamp(6),
    creator_id       uuid,
    host_id          uuid,
    description      varchar(255),
    meeting_location varchar(255) check (meeting_location in
                                         ('NO_WINDOW_CONFERENCE_ROOM', 'WINDOW_CONFERENCE_ROOM', 'OUTSIDE')),
    outside_location varchar(255),
    meeting_type     varchar(255) check (meeting_type in ('NORMAL', 'REGULAR', 'EMERGENCY')),
    title            varchar(255),
    primary key (id)
);

create table meeting_attendee_ids
(
    attendee_ids uuid,
    meeting_id   uuid not null
);

create table meeting_pending_attendee_ids
(
    pending_attendee_ids uuid,
    meeting_id   uuid not null
);

create table meeting_refused_attendee_ids
(
    refused_attendee_ids uuid,
    meeting_id   uuid not null
);


alter table users
    add foreign key (slack_user_id)
        references slack_user (id);

alter table if exists meeting
    add foreign key (id)
        references schedule (id);

alter table if exists meeting_attendee_ids
    add foreign key (meeting_id)
        references meeting (id);
