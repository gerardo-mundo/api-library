CREATE TABLE book_loans
(
    id                     UUID                        NOT NULL,
    is_active              BOOLEAN                     NOT NULL,
    loan_date              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    return_date            TIMESTAMP WITHOUT TIME ZONE,
    due_date               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status                 SMALLINT,
    lender_id              UUID                        NOT NULL,
    employee_authorizer_id UUID                        NOT NULL,
    CONSTRAINT pk_book_loans PRIMARY KEY (id)
);

CREATE TABLE loan_books
(
    book_id UUID NOT NULL,
    loan_id UUID NOT NULL
);

ALTER TABLE book_loans
    ADD CONSTRAINT FK_BOOK_LOANS_ON_EMPLOYEE_AUTHORIZER FOREIGN KEY (employee_authorizer_id) REFERENCES employee (id);

ALTER TABLE book_loans
    ADD CONSTRAINT FK_BOOK_LOANS_ON_LENDER FOREIGN KEY (lender_id) REFERENCES users (id);

ALTER TABLE loan_books
    ADD CONSTRAINT fk_loaboo_on_book FOREIGN KEY (book_id) REFERENCES books (id);

ALTER TABLE loan_books
    ADD CONSTRAINT fk_loaboo_on_book_loan FOREIGN KEY (loan_id) REFERENCES book_loans (id);