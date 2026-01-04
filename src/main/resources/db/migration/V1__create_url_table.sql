CREATE TABLE IF NOT EXISTS url (
    id BIGSERIAL PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL UNIQUE,
    short_code VARCHAR(255) NOT NULL UNIQUE,
    click_count BIGINT DEFAULT 0
);

CREATE INDEX idx_url_short_code ON url(short_code);