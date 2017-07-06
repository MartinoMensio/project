#!/bin/sh

DB_NAME=lab4

export PGPASSWORD=ai-user-password

dropdb -h localhost -U postgres $DB_NAME
createdb -h localhost -U postgres $DB_NAME || exit 1
# extension needed for security
echo "create extension pgcrypto" | psql -h localhost -U postgres -d $DB_NAME || exit 1
psql -h localhost -U postgres -d $DB_NAME -f schema.sql || exit 1
psql -h localhost -U postgres -d $DB_NAME -f values_tables.sql || exit 1
psql -h localhost -U postgres -d $DB_NAME -f users_example.sql || exit 1
psql -h localhost -U postgres -d $DB_NAME -f values_topics.sql || exit 1
psql -h localhost -U postgres -d $DB_NAME -f messages_example.sql || exit 1
