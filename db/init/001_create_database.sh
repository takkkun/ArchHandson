echo "Create database: $MYSQL_DATABASE"
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -e \
  "CREATE DATABASE IF NOT EXISTS $MYSQL_DATABASE;
   GRANT ALL ON $MYSQL_DATABASE.* TO $MYSQL_USER@'%' IDENTIFIED BY '$MYSQL_PASSWORD'"
