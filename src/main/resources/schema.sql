CREATE TABLE IF NOT EXISTS students (
    id int not null primary key AUTO_INCREMENT,
    name varchar(256) not null,
    birthday date not null,
    email varchar(256) not null,
    is_admin bit DEFAULT 0,
    created_at datetime,
    updated_at datetime
);