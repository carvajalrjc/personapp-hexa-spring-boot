#!/bin/sh

# Wait for databases to be ready
echo "Waiting for MariaDB..."
while ! nc -z ${MARIADB_HOST:-mariadb} ${MARIADB_PORT:-3306}; do
  sleep 1
done
echo "MariaDB is ready!"

echo "Waiting for MongoDB..."
while ! nc -z ${MONGODB_HOST:-mongo} ${MONGODB_PORT:-27017}; do
  sleep 1
done
echo "MongoDB is ready!"

# Determine which application to run
if [ "$APP_TYPE" = "cli" ]; then
  echo "Starting CLI application..."
  exec java -jar /app/cli/app.jar
elif [ "$APP_TYPE" = "rest" ]; then
  echo "Starting REST application..."
  exec java -jar /app/rest/app.jar
else
  echo "ERROR: APP_TYPE must be 'cli' or 'rest'"
  exit 1
fi

