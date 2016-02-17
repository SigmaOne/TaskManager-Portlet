package elcom.ejbs;

import elcom.entities.*;
import java.util.List;
import java.util.Map;

// Handles database data retrieving
public interface DataProvider {
    Task instantiateTaskByTemplate(TaskTemplate tt);

    boolean tryPersist(Object o);

    Comment getCommentEntityByContent(String content);
    Contact getOrganisationEntityByName(String name);
    Employee getEmployeeEntityByName(String name);
    Group getGroupEntityByName(String name);
    Priority getPriorityEntityByName(String name);
    Status getStatusEntityByName(String name);
    Task getTaskEntityById(long id);
    TaskTemplate getTasktemplateEntityByName(String name);
    Vendor getVendorEntityByName(String name);

    List<Comment> getAllComments();
    List<Comment> getTaskComments(Task task);
    List<Contact> getAllOrganisations();
    List<Employee> getAllEmployees();
    List<Group> getAllGroups();
    List<Priority> getAllPriorities();
    List<Status> getAllStatuses();
    List<Task> getTasks(Map<String, String> filters);
    List<Task> getAllTasks();
    List<TaskTemplate> getAllTaskTemplates();
    List<Vendor> getAllVendors();
}