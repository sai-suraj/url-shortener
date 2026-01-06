-- H2 database schema for testing
CREATE TABLE IF NOT EXISTS url_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_code VARCHAR(255) NOT NULL,
    click_count BIGINT DEFAULT 0,
    CONSTRAINT uk_short_code UNIQUE (short_code)
);

CREATE INDEX IF NOT EXISTS idx_url_entity_short_code ON url_entity(short_code);
