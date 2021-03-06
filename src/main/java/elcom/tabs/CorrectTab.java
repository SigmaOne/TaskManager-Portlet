package elcom.tabs;

import elcom.entities.Task;
import java.util.ArrayList;

// Tab for making changes to tasks
public class CorrectTab extends Tab {
    public CorrectTab(Task task) {
        this.tasks = new ArrayList();

        tasks.add(task);
    }

    @Override
    public String getTitle() {
        return "Правка заявки №" + tasks.get(0).getId();
    }
}
