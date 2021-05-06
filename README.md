### docker

```
docker build . -t time-tracker-app-backend
docker run \
    -p 5433:5432 \
    -e POSTGRES_DB=time_tracker \
    -e POSTGRES_USER=time_tracker \
    -e POSTGRES_PASSWORD=password \
    --name time-tracker-db \
    postgres
docker run \
    --network host
    -v "$(pwd)/src:/time-tracker-app-backend/src" \
    --name time-tracker-backend \
    time-tracker-app-backend \
    ./mvnw spring-boot:run
```
