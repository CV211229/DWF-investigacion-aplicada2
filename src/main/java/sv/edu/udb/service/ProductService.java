package sv.edu.udb.service;

import sv.edu.udb.dto.ProductRequest;
import sv.edu.udb.dto.ProductResponse;
import sv.edu.udb.entity.Product;
import sv.edu.udb.event.ProductEvent;
import sv.edu.udb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EventPublisherService eventPublisherService;

    public ProductResponse createProduct(ProductRequest request) {
        // Validar que el nombre no exista
        if (productRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + request.getName());
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product savedProduct = productRepository.save(product);

        // Publicar evento asíncrono
        ProductEvent event = new ProductEvent(
                "CREATED",
                savedProduct.getId(),
                savedProduct.getName(),
                "Producto creado exitosamente"
        );
        eventPublisherService.publishProductEvent(event);

        return mapToResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        // Validar nombre único si cambió
        if (!product.getName().equals(request.getName()) &&
                productRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe un producto con el nombre: " + request.getName());
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product updatedProduct = productRepository.save(product);

        // Publicar evento de actualización
        ProductEvent event = new ProductEvent(
                "UPDATED",
                updatedProduct.getId(),
                updatedProduct.getName(),
                "Producto actualizado exitosamente"
        );
        eventPublisherService.publishProductEvent(event);

        return mapToResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        productRepository.delete(product);

        // Publicar evento de eliminación
        ProductEvent event = new ProductEvent(
                "DELETED",
                product.getId(),
                product.getName(),
                "Producto eliminado exitosamente"
        );
        eventPublisherService.publishProductEvent(event);
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        return response;
    }
}
