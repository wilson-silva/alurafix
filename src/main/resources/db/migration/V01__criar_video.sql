CREATE TABLE video (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255)NOT NULL,
    url VARCHAR(255) NOT NULL
);

insert into video(titulo, descricao, url) values('O que é um bean em Spring Framework', 'Neste vídeo, mostro uma breve explicação do que significa um bean dentro do universo Spring Framework. Também mostro como nós, desenvolvedores, mostramos ao Spring como e quando gerenciar nossos beans.', 'https://www.youtube.com/watch?v=-PT-pXe-7UM&list=PL8iIphQOyG-CE8wY3hY2SY-MaB0rJyQO2');
insert into video(titulo, descricao, url) values('Quando utilizar @Bean em Spring', 'Neste vídeo, mostro o que é e quando utilizar a anotação @Bean dentro de uma aplicação Spring.', 'https://www.youtube.com/watch?v=S6ljIhE6mfY&list=PL8iIphQOyG-CE8wY3hY2SY-MaB0rJyQO2&index=2');

