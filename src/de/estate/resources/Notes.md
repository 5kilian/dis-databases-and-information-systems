. ~db2inst1/sqllib/db2profile
db2 connect to VSISP user vsisp42
(x)YPzU5ED6

1.a)
current isolation level: CS - Cursor Stability

1.b)
CREATE TABLE OPK
(
    ID int,
    NAME varchar(255)
)

c)
export DB20PTIONS= '+c'
db2 "select * from opk"
db2 "insert into opk values(5,'Juicy')"
db2 commit
db2 "select * from opk"

In CS Ist jede Reihe einzelnd gesperrt, zb. die vom opk.id = 2

d)
db2 set isolation = RS
db2 "select * from opk"
db2 "insert into opk values(6,'orange')"
db2 commit
db2 "select * from opk"

4.2
a/b

Bei RS wird tritt ein Phantom Read auf, weil ein neues Eintrag hinzugefügt wird, 
der aber nicht in der Querry (id>2) angezeigt wird.
Bei RR tritt kein Phantom Read auf, aber die zweite Verbindung muss warten bis die erste
Verbindung Commitet hat, da RR Die ganze Tabelle sperrt.

a)

Erste Verbindung:

export DB20PTIONS='+c'
db2 set isolation = RS
db2 "select * from opk where id > 2"

zweite Verbindung:
db2 "insert into opk values(23,'SwagBoyFly')"
db2 commit

Erste Verbindung:
db2 "select * from opk where id > 2"


b)

Erste Verbindung:

export DB20PTIONS='+c'
db2 set isolation = RR
db2 "select * from opk where id > 2"

Zweite Verbindung:

db2 "insert into opk values(55,'SwagBoyCry')"

//wartet bis die erste commited

Erste Verbindung:
commit

-> Zweite Verbindung kann inserten


c)

erste Verbindung:
db2 "select * from opk where id > 1"

zweite Verbindung:
db2 "UPDATE OPK
SET set name = "ChampagnePapi"
WHERE id = 3"

zweite Verbindung muss warten bis die erste commited, da RR die ganze Tabelle OPK sperrt?

erste Verbindung:
commit


4.3
a)
zweite Verbindung:
export DB20PTIONS='+c'

erste Verbindung:
db2 "select * from opk where id = 1"

zweite Verbindung:
db2 "select * from opk where id = 2"
db2 "UPDATE OPK
SET set name = "SwagPapi"
WHERE id = 1"

erste Verbindung:
db2 "UPDATE OPK
 SET set name = "WineDaddy"
 WHERE id = 2"

commit 1&2

die erste Connection kann das Tupel von der zweiten Connetion updated wegen CS,
aber die zweite Connection kann das Tupel der ersten Connection nicht updaten 
wegen RR (Inentional Read)

b)


zweite Verbindung:
export DB20PTIONS='+c'
db2 set isolation = RS

erste Verbindung:
db2 "select * from opk where id = 1"

zweite Verbindung:

db2 "select * from opk where id = 2"
db2 "UPDATE OPK
SET set name = "SwagPapi"
WHERE id = 1"

erste Verbindung:
db2 "UPDATE OPK
 SET set name = "WineDaddy"
 WHERE id = 2"

commit 1&2

das erste update wird blockiert und ab dem zweiten gibts ein Deadlock,
da die Verbindungen aufeinander warten.










