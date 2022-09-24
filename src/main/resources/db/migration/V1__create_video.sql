CREATE TABLE video (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255)NOT NULL,
    url VARCHAR(255) NOT NULL
);

insert into video(title, description, url) values('What is a bean in Spring', 'description video one', 'https://www.youtube.com/watch?v=-PT-pXe-7UM&list=PL8iIphQOyG-CE8wY3hY2SY-MaB0rJyQO2');
insert into video(title, description, url) values('When to use @Bean in Spring', 'description video two', 'https://www.youtube.com/watch?v=R9thmwiG2ns&t=894s');

