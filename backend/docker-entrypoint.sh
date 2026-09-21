#!/bin/sh
set -e

php artisan config:clear
php artisan migrate --force
php artisan db:seed --force --class=DummyAccountSeeder || true

PORT="${PORT:-8080}"
exec php artisan serve --host=0.0.0.0 --port="$PORT"
