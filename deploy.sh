#!/bin/bash

# === Variables ===
APP_NAME="ETU003657"

SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"
LIB_DIR="lib"
TOMCAT_WEBAPPS="/home/itu/Documents/tomcat/webapps"

SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"
JAKARTA_JAR="$LIB_DIR/jakartaee-migration-1.0.8-shaded.jar"
POSTGRESQL_JAR="$LIB_DIR/postgresql-42.6.0.jar"

CLASSPATH="$SERVLET_API_JAR:$JAKARTA_JAR:$POSTGRESQL_JAR"

LOG_FILE="logs.txt"

# === Nettoyage préalable du fichier log ===
if [ -f "$LOG_FILE" ]; then
    rm "$LOG_FILE"
fi

# === Fonctions ===

clean_build_directory() {
    echo "[INFO] Nettoyage du répertoire de build..."
    rm -rf "$BUILD_DIR"
    mkdir -p "$BUILD_DIR/WEB-INF/classes"
    mkdir -p "$BUILD_DIR/WEB-INF/lib"
}

compile_java_files() {
    echo "[INFO] Compilation des fichiers source Java..."
    find "$SRC_DIR" -name "*.java" > source.txt
    javac -cp "$CLASSPATH" -d "$BUILD_DIR/WEB-INF/classes" @source.txt 2>> "$LOG_FILE"
    if [ $? -ne 0 ]; then
        echo "[ERREUR] Échec de la compilation. Voir $LOG_FILE pour plus de détails."
        cat "$LOG_FILE"
        rm source.txt
        exit 1
    fi
    rm source.txt
}

copy_web_files() {
    echo "[INFO] Copie des fichiers web..."
    cp -r "$WEB_DIR/"* "$BUILD_DIR/" 2>> "$LOG_FILE"
}

copy_lib_files() {
    echo "[INFO] Copie des bibliothèques..."
    if [ -d "$LIB_DIR" ]; then
        cp "$LIB_DIR"/*.jar "$BUILD_DIR/WEB-INF/lib/" 2>> "$LOG_FILE"
    else
        echo "[AVERTISSEMENT] Le dossier $LIB_DIR n'existe pas ou est vide." >> "$LOG_FILE"
    fi
}

copy_jsp_files() {
    if [ -d "./web" ]; then
        echo "[INFO] Copie des fichiers JSP complémentaires..."
        cp -r "./web/"* "$BUILD_DIR/" 2>> "$LOG_FILE"
    fi
}

create_war() {
    echo "[INFO] Création du fichier WAR..."
    cd "$BUILD_DIR" || exit
    jar -cvf "$APP_NAME.war" . 2>> "../$LOG_FILE"
    if [ $? -ne 0 ]; then
        echo "[ERREUR] Échec de la création du fichier WAR. Voir $LOG_FILE."
        exit 1
    fi
    cd ..
}

deploy_to_tomcat() {
    echo "[INFO] Déploiement de l'application sur Tomcat..."
    cp "$BUILD_DIR/$APP_NAME.war" "$TOMCAT_WEBAPPS/" 2>> "$LOG_FILE"
    if [ $? -ne 0 ]; then
        echo "[ERREUR] Échec du déploiement sur Tomcat."
        exit 1
    fi
}

restart_tomcat() {
    echo "[INFO] Redémarrage de Tomcat..."
    sh "/home/itu/Documents/tomcat/bin/shutdown.sh" 2>> "$LOG_FILE"
    sh "/home/itu/Documents/tomcat/bin/startup.sh" 2>> "$LOG_FILE"
    if [ $? -ne 0 ]; then
        echo "[ERREUR] Échec du redémarrage de Tomcat."
        exit 1
    fi
}

# === Exécution ===

clean_build_directory
compile_java_files
copy_web_files
copy_lib_files
copy_jsp_files
create_war
deploy_to_tomcat
restart_tomcat

# Nettoyage si aucun log d'erreur
if [ ! -s "$LOG_FILE" ]; then
    rm "$LOG_FILE"
fi

echo "[INFO] Déploiement terminé avec succès."
read -p "Appuyez sur Entrée pour quitter..."