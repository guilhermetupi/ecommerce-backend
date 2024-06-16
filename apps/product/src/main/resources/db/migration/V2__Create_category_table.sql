CREATE TABLE category (
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    category_id UUID,
    name        VARCHAR(100) NOT NULL UNIQUE,
    version     INTEGER,
    created_by  UUID,
    updated_by  UUID,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT timezone( 'utc'::text, now()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted     BOOLEAN,
    CONSTRAINT  fk_category_category
    FOREIGN KEY (category_id)
    REFERENCES  category(id)
);