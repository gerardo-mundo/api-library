CREATE TABLE publications
(
    id           UUID         NOT NULL,
    type         VARCHAR(12)  NOT NULL,
    title        VARCHAR(255) NOT NULL,
    author       VARCHAR(255) NOT NULL,
    available    BOOLEAN      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    author_two   VARCHAR(255),
    author_three VARCHAR(255),
    author_four  VARCHAR(255),
    issn         VARCHAR(10)  NOT NULL,
    publisher    VARCHAR(255) NOT NULL,
    volume       VARCHAR(255),
    category     VARCHAR(255),
    CONSTRAINT pk_publications PRIMARY KEY (id)
);