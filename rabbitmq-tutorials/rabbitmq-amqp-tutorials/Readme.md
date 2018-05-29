
# Requirements:
RabbitMQ needs to be started at localhost:5672.
Use the following docker command:

`sudo docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3.7.5-management`

# commands for runing the spring boot applications
* run mvnw clean package from console or IDE

Tut1:
java -jar target/rabbitmq-amqp-tutorials-0.0.1-SNAPSHOT.jar --spring.profiles.active=hello-world,sender
java -jar target/rabbitmq-amqp-tutorials-0.0.1-SNAPSHOT.jar --spring.profiles.active=hello-world,receiver

Tut2:
java -jar target/rabbitmq-amqp-tutorials-0.0.1-SNAPSHOT.jar --spring.profiles.active=work-queues,sender
java -jar target/rabbitmq-amqp-tutorials-0.0.1-SNAPSHOT.jar --spring.profiles.active=work-queues,receiver
