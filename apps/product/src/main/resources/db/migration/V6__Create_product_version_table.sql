CREATE TABLE product_version (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name        VARCHAR(100) NOT NULL UNIQUE,
    version     INTEGER,
    created_by  UUID,
    updated_by  UUID,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT timezone( 'utc'::text, now()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted     BOOLEAN
);