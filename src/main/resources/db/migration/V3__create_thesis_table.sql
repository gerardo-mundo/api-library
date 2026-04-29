CREATE TABLE thesis
(
    id              UUID         NOT NULL,
    title           VARCHAR(255) NOT NULL,
    author          VARCHAR(255) NOT NULL,
    available       BOOLEAN      NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    author_two      VARCHAR(255),
    author_three    VARCHAR(255),
    thesis_advisor  VARCHAR(255) NOT NULL,
    university      VARCHAR(255) NOT NULL,
    bachelor_degree VARCHAR(255) NOT NULL,
    CONSTRAINT pk_thesis PRIMARY KEY (id)
);