package sv.edu.udb.event;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {
    private String eventType; // CREATED, UPDATED, DELETED
    private Long productId;
    private String productName;
    private LocalDateTime timestamp;
    private String message;

    public ProductEvent(String eventType, Long productId, String productName, String message) {
        this.eventType = eventType;
        this.productId = productId;
        this.productName = productName;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }


    public String getEventType() {
        return eventType;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}