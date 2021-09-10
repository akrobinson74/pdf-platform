for i in {1..30};
do
  /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P MSSQLServer@2019 -d master -i create_db.sql
  if [ $? -eq 0 ]
  then
    echo "Database creation was successful..."
    break
  else
    echo "'sqlcmd' not yet available :(..."
    sleep 1
  fi
done