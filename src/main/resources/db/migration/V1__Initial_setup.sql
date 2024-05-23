CREATE TABLE author (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  biography text
);

CREATE TABLE category (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL
);

CREATE TABLE publisher (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  address varchar(255)
);

CREATE TABLE book (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  title varchar(255) NOT NULL,
  isbn varchar(13) NOT NULL,
  available boolean NOT NULL,
  author_id bigint,
  category_id bigint,
  publisher_id bigint,
  location_id bigint,
  FOREIGN KEY (author_id) REFERENCES author (id),
  FOREIGN KEY (category_id) REFERENCES category (id),
  FOREIGN KEY (publisher_id) REFERENCES publisher (id)
);

CREATE TABLE user (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  username varchar(255) NOT NULL,
  PASSWORD VARCHAR(255) NOT NULL,
  ROLE VARCHAR(255) NOT NULL
);

CREATE TABLE borrow_record (
  id bigint AUTO_INCREMENT PRIMARY KEY,
  user_id bigint,
  book_id bigint,
  borrow_date date,
  return_date date,
  FOREIGN KEY (user_id) REFERENCES user (id),
  FOREIGN KEY (book_id) REFERENCES book (id)
);
