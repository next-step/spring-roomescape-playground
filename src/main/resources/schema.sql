CREATE TABLE reservation (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(30) NOT NULL,
                             date DATE NOT NULL,
                             time TIME NOT NULL,
                             PRIMARY KEY (id)
);
