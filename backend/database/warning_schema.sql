create table if not exists warnings (
  id bigint not null,
  address varchar(200) not null,
  lat double precision not null, -- TODO What about Decimal(9,6)?
  lng double precision not null,
  activation_date DATE not null, -- signalation date
  active boolean not null, -- true if warning is still active
  close_req integer, -- number of closing requests done by users
  confirmation integer, -- number of confirmations done by users
  comment varchar(1000), -- TODO not null??
  primary key (id),
  foreign key (warning_type_id) references warning_types(id), -- Warning has a type
  foreign key (user_warning_info_id) references users_warning_infos(id) -- User creates a warning
  -- foreign key (user_id) references users(id) -- Users ratings ?????
);

create table if not exists warning_types (
  id bigint not null,
  name varchar(30) not null,
  image BYTEA not null, -- fixed image
  primary key (id)
);

create table if not exists user_warning_infos ( --USER già presenti, vanno aggiunti solo i nuovi campi alla vecchia tabella?
  id bigint not null,
  nickname varchar(30) not null, -- redundant? probably this should be removed
  password varchar(200) not null, -- redundant?
  points integer -- create ad hoc value from 1.0 to 5.0
  primary key (id)
  foreign key (user_id) references users(id)
);
