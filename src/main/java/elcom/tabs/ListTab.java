package elcom.tabs;

import elcom.entities.Task;
import java.util.List;

// Tab for viewing multiple tasks
public class ListTab extends Tab implements TaskSelector {
    private Task selectedTask;

    public ListTab(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String getTitle() {
        return "Список задач";
    }

    // Proxy logic for TaskPresenter
    @Override
    public Task getSelectedTask() {
        return selectedTask;
    }
    @Override
    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
    }
}
