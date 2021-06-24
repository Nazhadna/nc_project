package com.company.nc_project.repository;

import com.company.nc_project.model.StoredItem;
import com.company.nc_project.model.StoredItemProjection;
import com.company.nc_project.model.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Modifying
    @Query(value = "INSERT INTO stored_items VALUES(uuid_generate_v4(), ?1, ?2, CURRENT_DATE + (SELECT lifetime FROM product WHERE id = ?2))", nativeQuery = true)
    @Transactional
    void addStoredItem(UUID clientId, UUID productId);

    @Query(value = "SELECT Cast (stored_items.id as text) id, product.name, product.place, stored_items.expiration_date FROM client INNER JOIN stored_items ON client.id=client_id " +
            "INNER JOIN product ON product.id = product_id WHERE client.id = ?1", nativeQuery = true)
    Collection<StoredItemProjection> getStoredItems(UUID clientId);

    @Modifying
    @Query(value = "DELETE FROM stored_items where stored_items.id = ?1", nativeQuery = true)
    @Transactional
    void deleteStoredItem(UUID storedItemId);
}
