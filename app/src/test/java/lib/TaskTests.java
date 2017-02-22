package lib;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import task.Task;

import static org.junit.Assert.assertEquals;

/**
 * Created by barakm on 19/02/2017.
 */
public class TaskTests {
    private Task m_Task;

    @Before
    public void beforeTest() {
        Task.TaskDate taskDate = new Task.TaskDate();
        Date date = new Date(24102017);
        m_Task = new Task(taskDate, "testTask", "Barak");
    }

    @Test
    public void setTaskAsComplete() throws Exception {
        m_Task.setTaskAsComplete();
        assertEquals(m_Task.isComplete(), true);
    }

    @Test
    public void setTaskAsInComplete() throws Exception {
        m_Task.setTaskAsComplete();
        assertEquals(m_Task.isComplete(), true);
        m_Task.setTaskAsInComplete();
        assertEquals(m_Task.isComplete(), false);
    }

    @Test
    public void isComplete() throws Exception {
        assertEquals(m_Task.isComplete(), false);
    }

}