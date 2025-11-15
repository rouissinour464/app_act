# ========== STAGE 1: BUILD ==========
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

# Copier les fichiers de build (cache Maven)
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw mvnw

# Pré-télécharger les dépendances (pour le cache Docker)
RUN ./mvnw -q -B -e -DskipTests dependency:resolve dependency:resolve-plugins || \
    mvn -q -B -e -DskipTests dependency:resolve dependency:resolve-plugins

# Copier le code source
COPY src/ src/

# Build du jar (skip tests à l'étape 1 ; ils seront exécutés à l’étape 2 du pipeline)
RUN ./mvnw -q -B -DskipTests clean package || mvn -q -B -DskipTests clean package

# ========== STAGE 2: RUNTIME ==========
FROM eclipse-temurin:17-jre-alpine

# curl pour le healthcheck
RUN apk add --no-cache curl

# Utilisateur non-root
RUN addgroup -S app && adduser -S app -G app
USER app

WORKDIR /app

# Copier l’artefact depuis l’étape build
COPY --from=build /workspace/target/gestion-etudiants-*.jar /app/app.jar

# Par défaut, l’appli écoute sur 8081
ENV SERVER_PORT=8081
# Options JVM
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -Dfile.encoding=UTF-8 -Duser.timezone=UTC"

# Expose le port interne
EXPOSE 8081

# Healthcheck (nécessite spring-boot-starter-actuator + exposition de /actuator/health)
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD sh -c 'curl -sf http://127.0.0.1:$SERVER_PORT/actuator/health | grep -q "\"status\":\"UP\"" || exit 1'

# Lancement
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]

