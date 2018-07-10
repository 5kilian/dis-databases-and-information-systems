CREATE TABLE SOLDID
(
    SOLDID int PRIMARY KEY NOT NULL,
    SHOPID int NOT NULL,
    ARTICLEID int NOT NULL,
    SALES int NOT NULL,
    DATE varchar(255),
    REVENUE int,
    CONSTRAINT SHOPID_fk FOREIGN KEY (SHOPID) REFERENCES SHOPID (SHOPID),
    CONSTRAINT ARTICLEID_fk FOREIGN KEY (ARTICLEID) REFERENCES ARTICLEID (ARTICLEID)
);
CREATE UNIQUE INDEX SOLDID_SOLDID_uindex ON SOLDID (SOLDID);


--quater one Hamburg item 1
SELECT COUNT(ARTICLEID), STADTID
FROM SOLDID s, SHOPID p
WHERE s.DATE BETWEEN #01/01/2018# AND #01/04/2018#
AND s.SHOPID = p.SHOPID
AND p.NAME = "Hamburg"
AND s.ARTICLEID = 1
GROUP BY STADTID;

--Hamburg total items for whole year
SELECT COUNT(ARTICLEID), STADTID
FROM SOLDID s, SHOPID p
WHERE s.DATE BETWEEN #01/01/2018# AND #31/12/2018#
AND s.SHOPID = p.SHOPID
AND p.NAME = "Hamburg"
GROUP BY STADTID;

--Total for item 1 over all states
SELECT COUNT(ARTICLEID)
FROM SOLDID s
WHERE s.ARTICLEID = 1;