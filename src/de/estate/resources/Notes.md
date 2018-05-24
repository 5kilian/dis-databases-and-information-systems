1.a)
current isolation level: CS - Cursor Stability

1.b)
CREATE TABLE OPK
(
    ID int,
    NAME varchar(255)
);


4.2
a/b

Bei RS wird tritt ein Phantom Read auf, weil ein neues Eintrag hinzugefügt wird, 
der aber nicht in der Querry (id>2) angezeigt wird.
Bei RR tritt kein Phantom Read auf, sondern ein Fehler, weil wegen RR 
die ganze Tabellen Row gesperrt wird, um PRs zu vermeiden.




























