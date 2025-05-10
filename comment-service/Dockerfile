# Sử dụng base image Java 17
FROM openjdk:17-jdk-slim

# Tạo thư mục làm việc trong container
WORKDIR /app

# Copy file JAR từ thư mục target vào container
COPY target/comment-service-0.0.1-SNAPSHOT.jar comment-service.jar

# Mở cổng 8083
EXPOSE 8083

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "comment-service.jar"]
