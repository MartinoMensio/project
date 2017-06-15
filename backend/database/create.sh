#!/bin/sh

DB_NAME=lab4

export PGPASSWORD=ai-user-password

dropdb -h localhost -U postgres $DB_NAME
createdb -h localhost -U postgres $DB_NAME
# extension needed for security
echo "create extension pgcrypto" | psql -h localhost -U postgres -d $DB_NAME
psql -h localhost -U postgres -d $DB_NAME -f schema.sql
psql -h localhost -U postgres -d $DB_NAME -f values_tables.sql
psql -h localhost -U postgres -d $DB_NAME -f users_example.sql
psql -h localhost -U postgres -d $DB_NAME -f values_topics.sql
psql -h localhost -U postgres -d $DB_NAME -f messages_example.sql
