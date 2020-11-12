package taskmanager;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBConnectionTest {

    @Test
    @Order(1)
    public void connect() {
        assertTrue(DBConnection.connect());
    }

    @Test
    @Order(2)
    public void disconnect() {
        assertTrue(DBConnection.disconnect());
    }
}
