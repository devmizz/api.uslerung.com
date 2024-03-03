drop table if exists invitee cascade;
drop table if exists meeting cascade;
drop table if exists schedule cascade;
drop table if exists slack_user cascade;
drop table if exists users cascade;

create table invitee
(
    id                         uuid not null,
    created_at                 timestamp(6),
    updated_at                 timestamp(6),
    meeting_id                 uuid,
    user_id                    uuid,
    attendance_decision_status varchar(255) check (attendance_decision_status in ('PENDING', 'CONFIRMED', 'REFUSED')),
    primary key (id)
);

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
    slack_id     varchar(255),
    display_name varchar(255),
    real_name    varchar(255),
    email        varchar(255),
    image_origin varchar(255),
    primary key (id)
);

create table schedule
(
    id         uuid        not null,
    created_at timestamp(6),
    updated_at timestamp(6),
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
    title            varchar(255),
    meeting_type     varchar(255) check (meeting_type in ('NORMAL', 'REGULAR', 'EMERGENCY')),
    meeting_location varchar(255) check (meeting_location in
                                         ('NO_WINDOW_CONFERENCE_ROOM', 'WINDOW_CONFERENCE_ROOM', 'OUTSIDE')),
    outside_location varchar(255),
    primary key (id)
);

alter table if exists invitee
    add foreign key (meeting_id)
        references meeting (id);

alter table users
    add foreign key (slack_user_id)
        references slack_user (id);

alter table if exists meeting
    add foreign key (id)
        references schedule (id);

alter table if exists meeting_attendee_ids
    add foreign key (meeting_id)
        references meeting (id);
