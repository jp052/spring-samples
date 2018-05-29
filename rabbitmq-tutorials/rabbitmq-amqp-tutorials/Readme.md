
# Requirements:
RabbitMQ needs to be started at localhost:5672.
Use the following docker command:

`sudo docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3.7.5-management`