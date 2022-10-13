CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cor ENUM('AZUL','AMARELO','VERMELHO','VERDE','ROXO')
);

insert into categoria(title, cor) values('livre', 'AZUL');
insert into categoria(title, cor) values('back-end', 'AMARELO');
insert into categoria(title, cor) values('jogos', 'VERMELHO');

