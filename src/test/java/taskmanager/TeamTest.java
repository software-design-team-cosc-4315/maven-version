package taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    private final Team testTeam = new Team();

    @BeforeEach
    void setUp() {
        SystemController.current_user = new AppUser();
        SystemController.current_user.set_role(AppUser.UserType.MANAGER);
        testTeam.set_team_ID("dab");
        testTeam.set_leader_ID(12);
        testTeam.set_leader_username("ultradab");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void team_ID() {
        String team_ID_test = "dab";
        assertEquals(testTeam.team_ID(),team_ID_test);
    }

    @Test
    public void leader_ID() {
        int lead_ID_test = 12;
        assertEquals(lead_ID_test,testTeam.leader_ID());
    }

    @Test
    public void leader_username() {
        String leader_username_test = "ultradab";
        assertEquals(testTeam.leader_username(),leader_username_test);
    }

    @Test
    public void set_team_ID() {
        //See team_id
    }

    @Test
    public void set_leader_ID() {
        //See leader_id
    }

    @Test
    public void set_leader_username() {
        //See leader username

    }
}