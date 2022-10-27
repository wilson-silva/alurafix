ALTER TABLE videos
ADD COLUMN category_id INT;

ALTER TABLE videos
ADD FOREIGN KEY (category_id)
REFERENCES categories (id);
