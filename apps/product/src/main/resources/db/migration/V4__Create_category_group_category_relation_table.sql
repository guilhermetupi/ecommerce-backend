CREATE TABLE category_group_category (
    category_group_id   UUID NOT NULL,
    category_id         UUID NOT NULL,
    PRIMARY KEY (category_group_id, category_id),
    CONSTRAINT fk_category_category_group
    FOREIGN KEY (category_group_id)
    REFERENCES category_group(id),
    CONSTRAINT fk_category_group_category
    FOREIGN KEY (category_id)
    REFERENCES category(id)
);