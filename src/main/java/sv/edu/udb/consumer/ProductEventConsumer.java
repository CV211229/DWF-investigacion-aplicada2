package sv.edu.udb.consumer;

import sv.edu.udb.event.ProductEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ProductEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(ProductEventConsumer.class);

    @RabbitListener(queues = "product-queue")
    public void handleProductEvent(ProductEvent event) {
        if (event == null) {
            logger.warn("Evento nulo recibido");
            return;
        }

        logger.info("=== EVENTO RECIBIDO ===");
        logger.info("Tipo: {}", event.getEventType());
        logger.info("Producto ID: {}", event.getProductId());
        logger.info("Nombre: {}", event.getProductName());
        logger.info("Mensaje: {}", event.getMessage());
        logger.info("Timestamp: {}", event.getTimestamp());
        logger.info("=========================");
    }
}