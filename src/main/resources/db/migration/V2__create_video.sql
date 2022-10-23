CREATE TABLE videos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255)NOT NULL,
    url VARCHAR(255) NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories (id)

);

insert into videos(title, description, url, category_id) values('What is a bean in Spring', 'description video one', 'https://www.youtube.com/watch?v=-PT-pXe-7UM&list=PL8iIphQOyG-CE8wY3hY2SY-MaB0rJyQO2', 1);
insert into videos(title, description, url, category_id) values('When to use @Bean in Spring', 'description video two', 'https://www.youtube.com/watch?v=R9thmwiG2ns&t=894s', 2);

