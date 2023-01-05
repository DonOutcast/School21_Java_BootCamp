package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private DataSource dataSource;
    private final String findAllQuery = "SELECT * FROM shop.product";
    private final String findByIdQuery = "SELECT * FROM shop.product WHERE identifier = ";
    private final String updateQuery = "UPDATE shop.product SET name = ?, price = ? WHERE identifier = ?";
    private final String saveQuery = "INSERT INTO shop.product(name, price) VALUES (?, ?)";
    private final String deleteQuery = "DELETE FROM shop.product WHERE identifier = ";
    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public List<Product> findAll() {
        List<Product> lists = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(findAllQuery)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getLong(1),
                        rs.getString(2), rs.getLong(3));
                lists.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lists;
    }
    @Override
    public Optional<Product> findById(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(findByIdQuery + id)) {
            ResultSet rs = st.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }
            Product product = new Product(rs.getLong(1),
                    rs.getString(2), rs.getLong(3));
            return Optional.of(product);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
    @Override
    public void update(Product product) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(updateQuery)) {
            st.setString(1, product.getName());
            st.setLong(2, product.getPrice());
            st.setLong(3, product.getIdentifier());
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void save(Product product) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(saveQuery)) {
            st.setString(1, product.getName());
            st.setLong(2, product.getPrice());
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void delete(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(deleteQuery + id)) {
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
