#!/bin/bash

# Mise à jour
echo "--- UPDATE ---"

apt update > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo REUSSITE

    # Installation paquet utils
    echo ""
    echo "--- INSTALLATION APT-UTILS ---"
    apt-get install apt-utils -y > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo REUSSITE
    else
        echo ECHEC
    fi

    # Ajout configuration timezone
    echo ""
    echo "--- CONFIG TIMEZONE Europe/Paris ---"
    DEBIAN_FRONTEND=noninteractive apt-get install --reinstall tzdata > /dev/null 2>&1 && ln -sf /usr/share/zoneinfo/Europe/Paris /etc/localtime
    if [ $? -eq 0 ]; then
        echo REUSSITE
    else
        echo ECHEC
    fi

    # Installation Java
    echo ""
    echo "--- INSTALLATION DE JAVA 22 ---"
    apt-get install wget -y > /dev/null 2>&1 &&
    wget https://download.oracle.com/java/22/latest/jdk-22_linux-x64_bin.deb > /dev/null 2>&1 &&
    dpkg -i jdk-22_linux-x64_bin.deb > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo REUSSITE
    else
        echo ECHEC
    fi

    # Installation Git et Maven
    echo ""
    echo "--- INSTALLATION DE MAVEN ---"
    echo ' ' >> ~/.bashrc
    echo 'echo java : $(java --version)' >> ~/.bashrc
    echo 'echo mvn : $(mvn --version)' >> ~/.bashrc
    apt-get install maven -y > /dev/null 2>&1
    if [ $? -eq 0 ]; then
        echo REUSSITE
    else
        echo ECHEC
    fi

    # FIN
    echo ""
    echo "Faite : "
    echo "cd ~/Projects/"
    echo "ACCEDER A VOTRE PROJET puis :"
    echo "mvn spring-boot:run"
else
    echo 'ECHEC (probleme de connection ?)'
    echo Arret des operations.
fi