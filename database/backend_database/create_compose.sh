#!/bin/sh

DB_NAME=lab4
HOST=backend_db

export PGPASSWORD=ai-user-password

dropdb -h $HOST -U postgres $DB_NAME
createdb -h $HOST -U postgres $DB_NAME || exit 1
# extension needed for security
echo "create extension pgcrypto" | psql -h $HOST -U postgres -d $DB_NAME || exit 1
psql -h $HOST -U postgres -d $DB_NAME -f schema.sql || exit 1
psql -h $HOST -U postgres -d $DB_NAME -f values_tables.sql || exit 1
psql -h $HOST -U postgres -d $DB_NAME -f users_example.sql || exit 1
psql -h $HOST -U postgres -d $DB_NAME -f values_topics.sql || exit 1
psql -h $HOST -U postgres -d $DB_NAME -f messages_example.sql || exit 1
