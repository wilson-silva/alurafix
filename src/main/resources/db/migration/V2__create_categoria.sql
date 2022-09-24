CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cor VARCHAR(255)NOT NULL
);

insert into categoria(title, cor) values('livre', '#7CFC00');
insert into categoria(title, cor) values('back-end', '#00FFFF');
insert into categoria(title, cor) values('jogos', '#FF6347');

