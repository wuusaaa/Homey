package Task;

/**
 * Created by Raz on 12/20/2016.
 */

public class Task {

    private TaskDate m_TaskDate;
    private String m_TaskName;
    private Boolean m_IsDone;
    private String m_Asignee;

    public Task(TaskDate i_TaskDate, String i_TaskName, String i_Asignee) {
        m_TaskDate = i_TaskDate;
        m_TaskName = i_TaskName;
        m_IsDone = false;
        m_Asignee = i_Asignee;
    }

    public void setTaskAsComplete() {
        m_IsDone = true;
    }

    public void setTaskAsInComplete() {
        m_IsDone = false;
    }

    public Boolean isComplete() {
        return m_IsDone;
    }


}
