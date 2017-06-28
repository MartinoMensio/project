# Istruzioni per l'inizializzazione dei dati

## Creare il database

Con il seguente comando si può creare un database con nome DB_NAME

```
createdb -h localhost -U postgres DB_NAME
```

## Creare le tabelle

```
psql -h localhost -U postgres -d DB_NAME -f schema.sql
```

## Mettere dei dati di esempio nel db

```
psql -h localhost -U postgres -d DB_NAME -f DATI.sql
```

## Altro

Per eliminare il database il comando è:

```
dropdb -h localhost -U postgres DB_NAME
```

Per l'esecuzione su Windows può essere necessario specificare come host l'indirizzo ip indicato nella console di docker.
