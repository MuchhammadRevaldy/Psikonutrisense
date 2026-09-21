#!/bin/sh
set -e

# Render assigns the port to listen on via $PORT; Apache defaults to 80.
if [ -n "$PORT" ] && [ "$PORT" != "80" ]; then
  sed -i "s/80/$PORT/g" /etc/apache2/ports.conf /etc/apache2/sites-available/000-default.conf
fi

php artisan config:clear
php artisan migrate --force
php artisan db:seed --force --class=DummyAccountSeeder || true

exec "$@"
