CREATE TABLE category_product (
    category_id     UUID NOT NULL,
    product_id      UUID NOT NULL,
    PRIMARY KEY (category_id, product_id),
    CONSTRAINT fk_product_category
    FOREIGN KEY (category_id)
    REFERENCES category(id),
    CONSTRAINT fk_category_product
    FOREIGN KEY (product_id)
    REFERENCES product(id)
);