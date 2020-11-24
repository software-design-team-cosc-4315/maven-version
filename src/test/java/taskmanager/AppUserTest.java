package taskmanager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppUserTest {
    
    private static AppUser test_user;
    private static AppUser manager;
    
    
    @BeforeAll
    public static void init() {
        test_user = new AppUser();
        manager = new AppUser();
        SystemController.current_state = SystemController.State.UNAUTHENTICATED;
        SystemController.current_user = manager;
    }
    
    
    @Test
    @Order(1)
    public void testSetBaseUser() {
        test_user.setRole(AppUser.UserType.BASE_USER);
        assertEquals(test_user.role(), AppUser.UserType.BASE_USER);
    }
    
    @Test
    @Order(2)
    public void testSetManager() {
        manager.setRole(AppUser.UserType.MANAGER);
        assertEquals(manager.role(), AppUser.UserType.MANAGER);
        
    }
    
    @Test
    @Order(3)
    public void testManagerSetSelfID() {
        manager.setId(99);
        assertEquals(manager.ID(), 99);
    }
    
    @Test
    @Order(4)
    public void testManagerSetUserID() {
        test_user.setId(78);
        assertEquals(test_user.ID(), 78);
    }
    
    @Test
    @Order(5)
    public void testUnauthenticatedUserSetSelfID() {
        SystemController.current_user = test_user;
        test_user.setId(25);
        assertEquals(test_user.ID(), 78);
    }
    
    @Test
    @Order(6)
    public void testAuthenticatedUserSetSelfID() {
        SystemController.current_state = SystemController.State.RECENTLY_AUTHENTICATED;
        test_user.setId(25);
        assertEquals(test_user.ID(), 25);
    }
    
}