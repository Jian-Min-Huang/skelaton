package com.example.inventory.domain.warehouse;

import com.example.inventory.domain.warehouse.entity.StorageLocation;
import com.example.inventory.domain.warehouse.enu.LocationType;
import com.example.inventory.domain.warehouse.enu.WarehouseStatus;
import com.example.inventory.domain.warehouse.event.StockReleasedEvent;
import com.example.inventory.domain.warehouse.event.StockReservedEvent;
import com.example.inventory.domain.warehouse.event.WarehouseCreatedEvent;
import com.example.inventory.domain.warehouse.vo.Address;
import com.example.inventory.domain.warehouse.vo.StockLevel;
import com.example.shared.domain.DomainException;
import com.example.shared.domain.DomainResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private static final Address ADDRESS = new Address("Taipei", "Da'an", "Xinyi Rd", "106");

    private DomainResult<Warehouse> createDefaultWarehouse() {
        return Warehouse.create("Main Warehouse", "WH-001", ADDRESS, 1000);
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("should create warehouse with ACTIVE status and emit WarehouseCreatedEvent")
        void shouldCreateWarehouse() {
            DomainResult<Warehouse> result = createDefaultWarehouse();
            Warehouse warehouse = result.entity();

            assertNotNull(warehouse.getId());
            assertEquals("Main Warehouse", warehouse.getName());
            assertEquals("WH-001", warehouse.getCode());
            assertEquals(ADDRESS, warehouse.getAddress());
            assertEquals(1000, warehouse.getCapacity());
            assertEquals(WarehouseStatus.ACTIVE, warehouse.getStatus());
            assertFalse(warehouse.getDeleted());
            assertTrue(warehouse.getStorageLocations().isEmpty());

            assertEquals(1, result.events().size());
            assertInstanceOf(WarehouseCreatedEvent.class, result.events().getFirst());
            WarehouseCreatedEvent event = (WarehouseCreatedEvent) result.events().getFirst();
            assertEquals(warehouse.getId(), event.warehouseId());
            assertNotNull(event.occurredAt());
        }
    }

    @Nested
    @DisplayName("reserveStock")
    class ReserveStock {
        @Test
        @DisplayName("should reserve stock and emit StockReservedEvent")
        void shouldReserveStock() {
            UUID productId = UUID.randomUUID();
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(productId)
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(100, 0, 100))
                    .deleted(false)
                    .build();
            Warehouse warehouse = createDefaultWarehouse().entity()
                    .withStorageLocations(List.of(location));

            DomainResult<Warehouse> result = warehouse.reserveStock(productId, 30);
            Warehouse updated = result.entity();

            StorageLocation updatedLocation = updated.getStorageLocations().getFirst();
            assertEquals(100, updatedLocation.getStockLevel().onHand());
            assertEquals(30, updatedLocation.getStockLevel().reserved());
            assertEquals(70, updatedLocation.getStockLevel().available());

            assertEquals(1, result.events().size());
            assertInstanceOf(StockReservedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when insufficient available stock")
        void shouldThrowWhenInsufficientStock() {
            UUID productId = UUID.randomUUID();
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(productId)
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(100, 90, 10))
                    .deleted(false)
                    .build();
            Warehouse warehouse = createDefaultWarehouse().entity()
                    .withStorageLocations(List.of(location));

            assertThrows(DomainException.class, () -> warehouse.reserveStock(productId, 50));
        }
    }

    @Nested
    @DisplayName("releaseStock")
    class ReleaseStock {
        @Test
        @DisplayName("should release stock and emit StockReleasedEvent")
        void shouldReleaseStock() {
            UUID productId = UUID.randomUUID();
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(productId)
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(100, 30, 70))
                    .deleted(false)
                    .build();
            Warehouse warehouse = createDefaultWarehouse().entity()
                    .withStorageLocations(List.of(location));

            DomainResult<Warehouse> result = warehouse.releaseStock(productId, 20);
            Warehouse updated = result.entity();

            StorageLocation updatedLocation = updated.getStorageLocations().getFirst();
            assertEquals(100, updatedLocation.getStockLevel().onHand());
            assertEquals(10, updatedLocation.getStockLevel().reserved());
            assertEquals(90, updatedLocation.getStockLevel().available());

            assertEquals(1, result.events().size());
            assertInstanceOf(StockReleasedEvent.class, result.events().getFirst());
        }

        @Test
        @DisplayName("should throw DomainException when releasing more than reserved")
        void shouldThrowWhenReleasingTooMuch() {
            UUID productId = UUID.randomUUID();
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(productId)
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(100, 10, 90))
                    .deleted(false)
                    .build();
            Warehouse warehouse = createDefaultWarehouse().entity()
                    .withStorageLocations(List.of(location));

            assertThrows(DomainException.class, () -> warehouse.releaseStock(productId, 20));
        }
    }

    @Nested
    @DisplayName("StockLevel value object")
    class StockLevelTest {
        @Test
        @DisplayName("should reject null fields")
        void shouldRejectNullFields() {
            assertThrows(NullPointerException.class, () -> new StockLevel(null, 0, 0));
            assertThrows(NullPointerException.class, () -> new StockLevel(0, null, 0));
            assertThrows(NullPointerException.class, () -> new StockLevel(0, 0, null));
        }

        @Test
        @DisplayName("should reject negative values")
        void shouldRejectNegativeValues() {
            assertThrows(IllegalArgumentException.class, () -> new StockLevel(-1, 0, 0));
            assertThrows(IllegalArgumentException.class, () -> new StockLevel(0, -1, 0));
            assertThrows(IllegalArgumentException.class, () -> new StockLevel(0, 0, -1));
        }

        @Test
        @DisplayName("should reserve stock correctly")
        void shouldReserveStockCorrectly() {
            StockLevel level = new StockLevel(100, 0, 100);
            StockLevel reserved = level.reserve(30);

            assertEquals(100, reserved.onHand());
            assertEquals(30, reserved.reserved());
            assertEquals(70, reserved.available());
        }

        @Test
        @DisplayName("should release stock correctly")
        void shouldReleaseStockCorrectly() {
            StockLevel level = new StockLevel(100, 30, 70);
            StockLevel released = level.release(10);

            assertEquals(100, released.onHand());
            assertEquals(20, released.reserved());
            assertEquals(80, released.available());
        }
    }

    @Nested
    @DisplayName("StorageLocation entity")
    class StorageLocationTest {
        @Test
        @DisplayName("should update stock level")
        void shouldUpdateStockLevel() {
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(UUID.randomUUID())
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(50, 0, 50))
                    .deleted(false)
                    .build();

            StockLevel newLevel = new StockLevel(100, 10, 90);
            StorageLocation updated = location.updateStockLevel(newLevel);

            assertEquals(newLevel, updated.getStockLevel());
            assertEquals(location.getLocationCode(), updated.getLocationCode());
        }

        @Test
        @DisplayName("should change location type")
        void shouldChangeLocationType() {
            StorageLocation location = StorageLocation.builder()
                    .id(UUID.randomUUID())
                    .locationCode("A-01")
                    .productId(UUID.randomUUID())
                    .locationType(LocationType.SHELF)
                    .stockLevel(new StockLevel(50, 0, 50))
                    .deleted(false)
                    .build();

            StorageLocation updated = location.changeLocationType(LocationType.COLD_STORAGE);

            assertEquals(LocationType.COLD_STORAGE, updated.getLocationType());
        }
    }
}
