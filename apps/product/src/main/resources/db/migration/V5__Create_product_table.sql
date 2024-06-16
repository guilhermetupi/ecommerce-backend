CREATE TABLE product (
    id                      UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name                    VARCHAR(255) NOT NULL UNIQUE,
    description             TEXT NOT NULL,
    important_information   TEXT,
    price                   DECIMAL NOT NULL,
    version                 INTEGER,
    created_by              UUID,
    updated_by              UUID,
    created_at              TIMESTAMP WITH TIME ZONE DEFAULT timezone( 'utc'::text, now()) NOT NULL,
    updated_at                 TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted                 BOOLEAN
);