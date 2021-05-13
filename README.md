## Docker

### DB
```
docker run \
    --rm \
    -p 5432:5432 \
    -e POSTGRES_DB=time_tracker \
    -e POSTGRES_USER=postgres \
    -e POSTGRES_PASSWORD=admin \
    --name time-tracker-db \
    postgres
```

### Backend
```
docker build . -t time-tracker-app-backend
docker run \
    --rm \
    --network host \
    -v "$(pwd)/src:/time-tracker-app-backend/src" \
    --name time-tracker-backend \
    time-tracker-app-backend \
    ./mvnw spring-boot:run
```
