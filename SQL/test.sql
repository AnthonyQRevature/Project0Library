
CREATE TABLE test4
(
    id4 int PRIMARY KEY
);

CREATE TABLE test1
(
    id1 int PRIMARY KEY,
    fk int REFERENCES test4(id4) ON DELETE CASCADE
);

CREATE TABLE test2
(
    id2 int PRIMARY KEY,
    fk int REFERENCES test4(id4) ON DELETE CASCADE
);

CREATE TABLE joined
(
    id3 SERIAL PRIMARY KEY,
    id1 int REFERENCES test1(id1)
        ON DELETE CASCADE,
    id2 int REFERENCES test2(id2)
        ON DELETE CASCADE
);

INSERT INTO test4(id4) VALUES (4);
INSERT INTO test1(id1, fk) VALUES (1, 4);
INSERT INTO test2(id2, fk) VALUES (2, 4);
INSERT INTO joined(id1, id2) VALUES (1, 2);

DELETE FROM joined WHERE id3 = 1
