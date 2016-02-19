# --- !Ups

CREATE TABLE message (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    recipient varchar(255) NOT NULL,
    viewed boolean NOT NULL,
    sent timestamp NOT NULL,
    text varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE message;