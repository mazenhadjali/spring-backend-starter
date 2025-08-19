# ====== Build stage ======
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first to cache deps
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline --batch-mode

# Copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package --batch-mode

# ====== Run stage ======
FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN useradd -m spring
USER spring

COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8000
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
