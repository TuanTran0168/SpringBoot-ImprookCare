# Sử dụng hình ảnh Maven để xây dựng dự án
FROM maven:3.9.4-openjdk-19 AS build
# Thiết lập thư mục làm việc
WORKDIR /app
# Sao chép tất cả các tệp vào container
COPY . /app
# Xây dựng ứng dụng Spring Boot với Maven
RUN mvn clean install

# Sử dụng hình ảnh OpenJDK để chạy ứng dụng
FROM openjdk:19-jdk-slim
# Thiết lập thư mục làm việc cho ứng dụng
WORKDIR /app
# Sao chép tệp JAR đã được xây dựng từ giai đoạn xây dựng
COPY --from=build /app/target/*.jar /app/app.jar
# Thiết lập biến môi trường cho cổng dịch vụ
ENV SERVER_PORT=2024
# Chạy ứng dụng Spring Boot
CMD ["java", "-jar", "app.jar"]
