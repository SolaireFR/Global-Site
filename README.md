# Commandes utiles
```bash
# SPRING
docker exec -it ubuntu-spring-1 bash

apt-get update && apt-get install -y dos2unix && cp /setup.sh /setup-copy.sh && dos2unix /setup-copy.sh && /setup-copy.sh

cd ~/Projects/Global-Site/

mvn spring-boot:run

# MYSQL
docker exec -it mysql_server bash

mysql -u root -proot

USE global_site_db;
```