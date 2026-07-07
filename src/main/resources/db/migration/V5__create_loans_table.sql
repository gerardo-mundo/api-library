CREATE TABLE loans
(
    id          UUID                        NOT NULL,
    approver_id UUID                        NOT NULL,
    borrower_id UUID                        NOT NULL,
    active      BOOLEAN                     NOT NULL,
    status      VARCHAR(50)                 NOT NULL,
    borrow_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    return_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_loans PRIMARY KEY (id)
);

CREATE TABLE loan_books
(
    loan_id UUID NOT NULL,
    book_id UUID NOT NULL
);

-- Foreign Keys for loans table
ALTER TABLE loans
    ADD CONSTRAINT fk_loans_on_approver FOREIGN KEY (approver_id) REFERENCES users (id);

ALTER TABLE loans
    ADD CONSTRAINT fk_loans_on_borrower FOREIGN KEY (borrower_id) REFERENCES users (id);

-- Foreign Keys for the Many-To-Many join table
ALTER TABLE loan_books
    ADD CONSTRAINT fk_loan_books_on_loan FOREIGN KEY (loan_id) REFERENCES loans (id);

ALTER TABLE loan_books
    ADD CONSTRAINT fk_loan_books_on_book FOREIGN KEY (book_id) REFERENCES books (id);