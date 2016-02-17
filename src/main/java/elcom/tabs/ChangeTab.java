package elcom.tabs;

import elcom.entities.Task;

import java.util.ArrayList;

/**
 * Created by Nikita Shkaruba on 15.02.16.
 * <p>
 * My Contacts:
 * Email: sh.nickita@list.ru
 * GitHub: github.com/SigmaOne
 * Vk: vk.com/wavemeaside
 */
public class ChangeTab extends Tab {
    public ChangeTab(Task task) {
        this.tasks = new ArrayList();

        tasks.add(task);
    }

    @Override
    public String getTitle() {
        return "Правка заявки №" + tasks.get(0).getId();
    }
}