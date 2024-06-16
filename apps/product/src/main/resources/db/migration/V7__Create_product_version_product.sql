CREATE TABLE product_version_product (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id          UUID NOT NULL,
    product_version_id  UUID NOT NULL,
    name                VARCHAR(100) NOT NULL UNIQUE,
    version             INTEGER,
    created_by          UUID,
    updated_by          UUID,
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT timezone( 'utc'::text, now()) NOT NULL,
    updated_at          TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted             BOOLEAN,
    CONSTRAINT  fk_product_version_product_product
    FOREIGN KEY (product_id)
    REFERENCES  product(id),
    CONSTRAINT  fk_product_version_product_product_version
    FOREIGN KEY (product_version_id)
    REFERENCES  product_version(id)
);