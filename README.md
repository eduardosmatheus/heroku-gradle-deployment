# Setup Docker Newtork

```bash

docker network create new-life-net
```

# Running PostgreSQL database

```bash
docker pull postgres:latest -- currently is the 11.1.x

# Initialize PostgreSQL inside network, exposing port 32768
docker run -p 32768:5432 --name spring-postgres --network new-life-net -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=spring -e POSTGRES_DB=spring-postgres -d postgres
```

# Running Spring API

```bash
cd server

# Dockerize Spring app
docker build --rm -t elemental/spring-postgres-server .

# Initialize Spring app inside network, exposing port 7010
docker run -p 7010:8080 -d --network new-life-net --name=spring-kotlin elemental/spring-postgres-server

```

# Add Spring application to reliable hosts in PostgreSQL

- Inside `pg_hba.conf` in this root directory, add the Docker network's gateway (not **yours**):

```
host    all             all             172.18.0.1/32           trust
```
- Then, push it inside the running postgres container
#### TO DO: It should be "password" instead of trust. See [PostgreSQL Password Authentication](https://www.postgresql.org/docs/11/auth-password.html) to understand why.

```bash
docker cp pg_hba.conf spring-postgres:/var/lib/postgresql/data
```