CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL
);

insert into categories(title, color) values('LIVRE', 'BLUE');
insert into categories(title, color) values('back-end', 'YELLOW');
insert into categories(title, color) values('games', 'RED');

