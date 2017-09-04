import com.imusica.desafio.domain.Record;
import com.imusica.desafio.service.AgendaService;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by gustavoamg on 30/05/17.
 * Test class for AgendaService
 */
public class AgendaServiceTests {

    @Test
    public void gendaServiceCreationTest(){
        AgendaService agendaService = AgendaService.getService();

        assertNotNull(agendaService, "Agenda service creation failed!");
    }

    @Test
    public void recordOperationTest(){
        AgendaService agendaService = AgendaService.getService();

        //Create Record
        Record record = agendaService.addRecod("Test User", "22222222222");

        assertNotNull(record, "AgendaService failed to create record");
        assertTrue(record.getId() > 0);
        assertEquals("Test User", record.getName(), "Name field failed to create");
        assertEquals("22222222222", record.getPhone(), "Phone field failed to create");

        //Update Record
        boolean updateStats = agendaService.updateRecord(record.getId(), "Test User Edited", "22222223333");
        assertTrue(updateStats, "Failed to update record");

        //Recover Record
        Record updatedRecord = agendaService.getRecord(record.getId());
        assertNotNull(updatedRecord, "Failed to retrieve the record");

        assertEquals("Test User Edited", record.getName(), "Name field failed to update");
        assertEquals("22222223333", record.getPhone(), "Phone field failed to update");

        //Delete Record
        int recordId = updatedRecord.getId();
        boolean removeStatus = agendaService.removeRecord(updatedRecord.getId());
        assertTrue(removeStatus, "Failed to remove the record");

        Record removedRecord = agendaService.getRecord(recordId);
        assertNull(removedRecord, "Failed to remove the record");

    }
}
