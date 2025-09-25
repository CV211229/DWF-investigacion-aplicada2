package sv.edu.udb.service;

import lombok.Getter;
import sv.edu.udb.event.ProductEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sv.edu.udb.config.RabbitMQConfig;


@Service
public class EventPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    public void publishProductEvent(ProductEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    event
            );
            System.out.println("Evento publicado: " + event.getEventType() + " - Producto: " + event.getProductName());
        } catch (Exception e) {
            System.err.println("Error publicando evento: " + e.getMessage());
        }
    }
}
