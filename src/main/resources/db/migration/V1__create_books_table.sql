CREATE TABLE books
(
    id           UUID         NOT NULL,
    title        VARCHAR(255) NOT NULL,
    author       VARCHAR(255) NOT NULL,
    available    BOOLEAN      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    author_two   VARCHAR(255),
    author_three VARCHAR(255),
    publisher    VARCHAR(255) NOT NULL,
    collection   VARCHAR(255) NOT NULL,
    isbn         VARCHAR(13)  NOT NULL,
    acquisition  INTEGER      NOT NULL,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT uc_books_acquisition UNIQUE (acquisition);