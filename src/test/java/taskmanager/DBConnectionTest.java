package taskmanager;

import static org.junit.Assert.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBConnectionTest {

    @Test
    @Order(1)
    public void connect() {
        assertEquals(true, DBConnection.connect());
    }

    @Test
    @Order(2)
    public void disconnect() {
        assertEquals(true, DBConnection.disconnect());
    }
}
