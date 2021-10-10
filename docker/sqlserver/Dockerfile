FROM mcr.microsoft.com/mssql/server:2019-latest

ENV ACCEPT_EULA=Y
ENV SA_PASSWORD=MSSQLServer@2019
ENV MSSQL_PID=Developer
ENV MSSQL_TCP_PORT=11433

COPY create-db.sh create-db.sh
COPY create_db.sql create_db.sql
COPY startup.sh startup.sh

CMD /bin/bash ./startup.sh

HEALTHCHECK --interval=10s --timeout=3s --start-period=10s --retries=10 \
    CMD /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P ${SA_PASSWORD} -Q "SELECT 1" || exit 1