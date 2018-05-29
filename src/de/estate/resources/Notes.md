# 4 Synchronisation with Locking Protocols

```
. ~db2inst1/sqllib/db2profile
db2 connect to VSISP user vsisp42
```
* YPzU5ED6

## 4.1.
### a)

current isolation level: CS - Cursor Stability
```
db2 VALUES CURRENT ISOLATION
```

### b)

```
db2 "DROP TABLE OPK"
db2 "CREATE TABLE OPK (ID INT, NAME VARCHAR(255))"
db2 "INSERT INTO OPK VALUES(1, 'Hello')"
db2 "INSERT INTO OPK VALUES(2, 'Swag')"
db2 "INSERT INTO OPK VALUES(3, 'Life')"
db2 "INSERT INTO OPK VALUES(3, 'Boi')"
 ```

### c)

```
export DB2OPTIONS='+c'
db2 "SELECT * FROM OPK"
db2 "INSERT INTO OPK VALUES(5, 'Juicy')"
db2 commit
db2 "SELECT * FROM OPK"
```

In CS Ist jede Reihe einzelnd gesperrt, zb. die vom OPK.ID = 2

### d)
```
db2 SET ISOLATION = RS
db2 "SELECT * FROM OPK"
db2 "INSERT INTO OPK VALUES(6, 'orange')"
db2 commit
db2 "SELECT * FROM OPK"
```

## 4.2

Bei RS wird tritt ein Phantom Read auf, 
weil ein neues Eintrag hinzugefügt wird, 
der aber nicht in der Querry (ID>2) angezeigt wird.
Bei RR tritt kein Phantom Read auf, 
aber die zweite Verbindung muss warten bis die erste
Verbindung Commitet hat, da RR Die ganze Tabelle sperrt.

### a)

Erste Verbindung:

```
export DB20PTIONS='+c'
db2 SET ISOLATION = RS
db2 "SELECT * FROM OPK WHERE ID > 2"
```

zweite Verbindung:
```
db2 "INSERT INTO OPK values(23, 'SwagBoyFly')"
db2 commit
```

Erste Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID > 2"
```

### b)

Erste Verbindung:

```
export DB20PTIONS='+c'
db2 SET ISOLATION = RR
db2 "SELECT * FROM OPK WHERE ID > 2"
```

Zweite Verbindung:

```
db2 "INSERT INTO OPK values(55,'SwagBoyCry')"
```
wartet bis die erste commited

Erste Verbindung:
```
db2 commit
```

Zweite Verbindung kann inserten

### c)

Erste Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID > 1"
```

Zweite Verbindung:
```
db2 "UPDATE OPK SET SET NAME = "ChampagnePapi" WHERE ID = 3"
```

zweite Verbindung muss warten bis die erste commited, 
da RR die ganze Tabelle OPK sperrt?

erste Verbindung:
```
commit
```

## 4.3
### a)
zweite Verbindung:
```
export DB2OPTIONS='+c'
```

erste Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID = 1"
```

zweite Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID = 2"
db2 "UPDATE OPK SET NAME = "SwagPapi" WHERE ID = 1"
```

erste Verbindung:
```
db2 "UPDATE OPK SET NAME = "WineDaddy" WHERE ID = 2"
```

Commite Verbindung 1 und 2:

```
db2 commit
```

die erste Connection kann das Tupel von der zweiten Connetion updated wegen CS,
aber die zweite Connection kann das Tupel der ersten Connection nicht updaten 
wegen RR (Inentional Read)

### b)

zweite Verbindung:
```
export DB2OPTIONS='+c'
db2 SET ISOLATION = RS
```

erste Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID = 1"
```

zweite Verbindung:
```
db2 "SELECT * FROM OPK WHERE ID = 2"
db2 "UPDATE OPK SET SET NAME = "SwagPapi" WHERE ID = 1"
```

erste Verbindung:
```
db2 "UPDATE OPK SET SET NAME = "WineDaddy" WHERE ID = 2"
```

Commite Verbindung 1 und 2:

```
db2 commit
```

das erste update wird blockiert und ab dem zweiten gibts ein Deadlock,
da die Verbindungen aufeinander warten.










