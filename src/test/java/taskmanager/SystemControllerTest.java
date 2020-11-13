package taskmanager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemControllerTest {
    
    
    @BeforeAll
    public static void init() {
        SystemController.create_pages();
    }
    
    
    @Test
    @Order(1)
    public void testLogout() {
        SystemController.log_out();
        assertEquals(SystemController.current_state, SystemController.State.UNAUTHENTICATED);
    }
    
    @Test
    @Order(2)
    public void testLoadCurrentTeam() {
        String team_ID = "presentationteam";
        SystemController.load_current_team(team_ID);
        assertEquals(true, SystemController.current_team.team_ID().equals(team_ID));
    }
    
    @Test
    @Order(3)
    public void testAuthenticateManager() {
        SystemController.log_out();
        String message = SystemController.authenticate("testing", new char[]{'t', 'e', 's', 't', 'i', 'n', 'g'});
        assertEquals(message, null);
        assertEquals(SystemController.current_state, SystemController.State.LOADED_MANAGERS_PAGE);
    }
    
    @Test
    @Order(4)
    public void testAuthenticateTeamLeader() {
        SystemController.log_out();
        String message = SystemController.authenticate("theotheruseragain", new char[]{'t', 'h', 'e', 'o', 't', 'h', 'e', 'r', 'u', 's', 'e', 'r', 'a', 'g', 'a', 'i', 'n'});
        assertEquals(message, null);
        assertEquals(SystemController.current_state, SystemController.State.LOADED_TEAM_LEADS_PAGE);
    }
    
    @Test
    @Order(5)
    public void testAuthenticateChillUser() {
        SystemController.log_out();
        String message = SystemController.authenticate("chilluser", new char[]{'c', 'h', 'i', 'l', 'l', 'u', 's', 'e', 'r'});
        assertEquals(message, null);
        assertEquals(SystemController.current_state, SystemController.State.LOADED_TASK_PAGE);
    }
    
}