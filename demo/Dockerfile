FROM openjdk:17-jdk-slim
#생성된 컨테이너 안에서 명령어에 대한 작업 디렉터리를 나타냄
WORKDIR /app

#모든 소스코드 파일 및 기타 /app 에 카피
COPY . .

# 개행문자 오류 해결 [unix와 window 시스템 차이]
RUN sed -i 's/\r$//' gradlew

RUN chmod +x ./gradlew
RUN ./gradlew clean build

ARG JAR_FILE=/build/libs/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV SERVER_PORT=8080

EXPOSE $SERVER_PORT

ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=$SERVER_PORT app.jar"]
