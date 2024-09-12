# Initialiser le projet
```bash
# Sous Windows
docker compose up -d --build ; Get-Content setup-mysql.sql | docker exec -i global_site_mysql mysql -u root -proot ; docker cp .\setup-spring.sh global_site_spring:/setup-tmp.sh ; docker exec -it global_site_spring bash

apt-get update && apt-get install -y dos2unix && cp /setup-tmp.sh /setup.sh && dos2unix /setup.sh && ./setup.sh && cd /root/Projects/Global-Site && mvn spring-boot:run
```