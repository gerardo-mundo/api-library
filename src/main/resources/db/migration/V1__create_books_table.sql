CREATE TABLE books
(
    id           UUID         NOT NULL,
    title        VARCHAR(255) NOT NULL,
    author       VARCHAR(255) NOT NULL,
    publisher    VARCHAR(255) NOT NULL,
    collection   VARCHAR(255),
    acquisition  INTEGER      NOT NULL,
    isbn         VARCHAR(255) NOT NULL,
    available    BOOLEAN      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    author_two   VARCHAR(255),
    author_three VARCHAR(255),
    CONSTRAINT pk_books PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT uc_books_isbn UNIQUE (isbn);