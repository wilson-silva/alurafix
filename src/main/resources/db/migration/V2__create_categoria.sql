CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL
);

insert into category(title, color) values('free', 'AZUL');
insert into category(title, color) values('back-end', 'AMARELO');
insert into category(title, color) values('games', 'VERMELHO');

