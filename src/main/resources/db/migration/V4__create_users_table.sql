CREATE TABLE users
(
    id         UUID                        NOT NULL,
    name       VARCHAR(255)                NOT NULL,
    password   VARCHAR(255)                NOT NULL,
    email      VARCHAR(255)                NOT NULL,
    role       VARCHAR(255)                NOT NULL,
    is_active  BOOLEAN                     NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_login TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id           UUID         NOT NULL,
    employee_key VARCHAR(255) NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE student
(
    id            UUID         NOT NULL,
    enrollment_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);

ALTER TABLE employee
    ADD CONSTRAINT uc_employee_employeekey UNIQUE (employee_key);

ALTER TABLE student
    ADD CONSTRAINT uc_student_enrollmentid UNIQUE (enrollment_id);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE employee
    ADD CONSTRAINT FK_EMPLOYEE_ON_ID FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE student
    ADD CONSTRAINT FK_STUDENT_ON_ID FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE;