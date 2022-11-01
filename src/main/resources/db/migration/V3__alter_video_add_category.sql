ALTER TABLE videos
ADD COLUMN category_id INT;

ALTER TABLE videos
ADD FOREIGN KEY (category_id)
REFERENCES categories (id);

UPDATE videos SET category_id = 1 WHERE category_id IS NULL;

