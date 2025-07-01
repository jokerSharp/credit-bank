CREATE TABLE IF NOT EXISTS employment
(
    employment_id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    status                  VARCHAR        NOT NULL,
    employer_inn            VARCHAR(12)    NOT NULL,
    salary                  DECIMAL(21, 2) NOT NULL,
    position                VARCHAR        NOT NULL,
    work_experience_total   INTEGER        NOT NULL,
    work_experience_current INTEGER        NOT NULL
);

CREATE TABLE IF NOT EXISTS passport
(
    passport_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    series       VARCHAR(4) NOT NULL,
    number       VARCHAR(6) NOT NULL,
    issue_branch VARCHAR,
    issue_date   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS client
(
    client_id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name       VARCHAR(30) NOT NULL,
    last_name        VARCHAR(30) NOT NULL,
    middle_name      VARCHAR(30),
    birth_date       DATE        NOT NULL,
    email            VARCHAR     NOT NULL,
    gender           VARCHAR,
    marital_status   VARCHAR,
    dependent_amount INTEGER,
    passport_id      UUID        NOT NULL,
    employment_id    UUID,
    account_number   VARCHAR(20),
    FOREIGN KEY (passport_id) REFERENCES passport (passport_id),
    FOREIGN KEY (employment_id) REFERENCES employment (employment_id)
);

CREATE TABLE IF NOT EXISTS credit
(
    credit_id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount            DECIMAL(21, 2) NOT NULL,
    term              INTEGER        NOT NULL,
    monthly_payment   DECIMAL(21, 2) NOT NULL,
    rate              DECIMAL(5, 2)  NOT NULL,
    psk               DECIMAL(21, 2) NOT NULL,
    payment_schedule  JSONB          NOT NULL,
    insurance_enabled BOOLEAN        NOT NULL,
    salary_client     BOOLEAN        NOT NULL,
    credit_status     VARCHAR
);

CREATE TABLE IF NOT EXISTS statement
(
    statement_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id      UUID      NOT NULL,
    credit_id      UUID,
    status         VARCHAR   NOT NULL,
    creation_date  TIMESTAMP NOT NULL,
    applied_offer  JSONB,
    sign_date      TIMESTAMP,
    ses_code       VARCHAR,
    status_history JSONB,
    FOREIGN KEY (client_id) REFERENCES client (client_id),
    FOREIGN KEY (credit_id) REFERENCES credit (credit_id)
);