package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepository repository;
    private EmbeddedDatabase dataSource;

    final List<Product> FIND_ALL = Arrays.asList(
            new Product(1l, "Milk", 95l),
            new Product(2l, "Tomatoes", 130l),
            new Product(3l, "Sugar", 70l),
            new Product(4l, "Pasta", 85l),
            new Product(5l, "Bread", 45l));

    final Product FIND_BY_ID = new Product(3l, "Sugar", 70l);
    final Product UPDATED_PRODUCT = new Product(4l, "Tea", 100l);
    final Product SAVE_PRODUCT = new Product(6l, "Apples", 80l);

    final List<Product> PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(2l, "Tomatoes", 130l),
            new Product(3l, "Sugar", 70l),
            new Product(4l, "Pasta", 85l),
            new Product(5l, "Bread", 45l));
    @BeforeEach
    private void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }
    @Test
    public void findAllTest() {
        List<Product> actual = repository.findAll();
        assertEquals(FIND_ALL, actual);
    }
    @Test
    public void findByIdTest() {
        assertEquals(FIND_BY_ID, repository.findById(3l).get());
        assertEquals(Optional.empty(), repository.findById(10l));
        assertEquals(Optional.empty(), repository.findById(null));
    }
    @Test
    public void updateTest() {
        repository.update(new Product(4l, "Tea", 100l));
        assertEquals(UPDATED_PRODUCT, repository.findById(4l).get());
    }
    @Test
    public void saveTest() {
        repository.save(new Product(6l, "Apples", 80l));
        assertEquals(SAVE_PRODUCT, repository.findById(6l).get());
    }
    @Test
    public void deleteTest() {
        repository.delete(1l);
        assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
        assertFalse(repository.findById(1l).isPresent());
    }
    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}

