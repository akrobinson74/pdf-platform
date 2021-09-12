FROM postgres:13.4-alpine

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENV POSTGRES_USER=dbadmin
ENV POSTGRES_DB=pdf_platform
ENV POSTGRES_PASSWORD=XXXXXXXXXXXXXXXXXXX

EXPOSE 5432