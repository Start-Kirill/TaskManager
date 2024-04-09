package by.it_academy.audit_service.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {


    @Value("${spring.rabbitmq.userAuditQueue}")
    private String AUDIT_USER_QUEUE_NAME;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public Queue userAuditQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(AUDIT_USER_QUEUE_NAME, false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

}
