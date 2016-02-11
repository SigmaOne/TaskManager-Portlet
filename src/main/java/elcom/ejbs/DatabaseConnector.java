package elcom.ejbs;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ejb.Singleton;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.io.Closeable;
import elcom.Entities.*;
import java.util.List;

// Handles database data retrieving
@Singleton
public class DatabaseConnector implements IDatabaseConnectorLocal {
    private static final String PERSISTENCE_UNIT_NAME = "MainPersistenceUnit";
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    // Query constants
    private static final String STATUS_ANY = "Любой";
    private static final String EMPLOYEE_ANY = "Все";
    // Caches
    private final List<Status> statuses;
    private final List<Priority> priorities;

    public DatabaseConnector() {
        priorities = fetchPriorities();
        statuses = fetchStatuses();
        }

    // Methods used inside class to make code more readable
    private List<Priority> fetchPriorities() {
        List<Priority> priorities = null;

        try(DBConnection dbc = new DBConnection(emf)){
            priorities = dbc.getEntityManager().createNamedQuery("select all priorities").getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return priorities;
    }
    private List<Status> fetchStatuses() {
        List<Status> statuses = null;

        try(DBConnection dbc = new DBConnection(emf)){
            statuses = dbc.getEntityManager().createNamedQuery("select all statuses").getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return statuses;
    }
    private String createReadTaskQuery(String statusFilter, String employeeFilter) {

        String queryName;

        if (statusFilter.equals(STATUS_ANY))
            if (employeeFilter.equals(EMPLOYEE_ANY))
                queryName = "select all tasks";
            else
                queryName = "select tasks by employee";
        else
            if (employeeFilter.equals(EMPLOYEE_ANY))
                queryName = "select tasks by status";
            else
                queryName = "select tasks by employee and status";

        return queryName;
    }
    // Methods used to get full entity instance (id-name-etc) having just name.
    public Status findStatusByName(String name) {
        for (Status s : statuses)
            if (s.getName().toLowerCase().equals(name.toLowerCase()))
                return s;
        return null;
    }
    public Employee findEmployeeByName(String name) {
        try (DBConnection dbc = new DBConnection(emf)) {
            List<Employee> employees = dbc.getEntityManager().createNamedQuery("select all employees").getResultList();
            for (Employee e : employees)
                if (e.getFullName().toLowerCase().equals(name.toLowerCase()) || e.getName().toLowerCase().equals(name.toLowerCase()))
                    return e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public Priority findPriorityByName(String name) {
        for (Priority p: priorities)
            if (p.getName().toLowerCase().equals(name.toLowerCase()))
                return p;
        return null;
    }
    public Task findTaskById(int id) {
        Task result = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            result = dbc.getEntityManager().find(Task.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // CREATE Methods
    public Boolean tryCreateTask(Task task){
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().persist(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // READ Methods
    public List<String> readEmployees() {
        List<String> result = null;

        try (DBConnection dbc = new DBConnection(emf)) {
            List<Employee> employees = dbc.getEntityManager().createNamedQuery("select all employees").getResultList();
            result = new ArrayList<String>();
            for (Employee e : employees)
                result.add(e.getFullName().concat(" (").concat(e.getName()).concat(")"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public List<String> readPriorities() {
        List<String> result = new ArrayList<>();

        for (Priority p : priorities)
            result.add(p.getName());

        return result;
    }
    public List<String> readStatuses() {
        List<String> result = new ArrayList<>();

        for (Status s : statuses)
            result.add(s.getName());

        return result;
    }
    public List<String> readGroups() {
        List<String> result = new ArrayList<>();

        result.add("Все сотрудники");

        return result;
    }
    public List<Task> readTasks(String statusFilter, String employeeFilter) {
        List<Task> tasks = null;

        String queryName = createReadTaskQuery(statusFilter, employeeFilter);

        try(DBConnection dbc = new DBConnection(emf)) {
            Query query = dbc.getEntityManager().createNamedQuery(queryName);

            if (queryName.contains("status"))
                query.setParameter("status", findStatusByName(statusFilter));
            if (queryName.contains("employee"))
                query.setParameter("employee", findEmployeeByName(employeeFilter));

            tasks = query.getResultList();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // UPDATE Methods
    public Boolean tryUpdateTask(Task task) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().merge(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // DELETE Methods
    public Boolean tryDeleteTask(Task task) {
        try (DBConnection dbc = new DBConnection(emf)) {
            dbc.getEntityManager().remove(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //Encapsulated single Database connection. Used by DatabaseConnector during transactions
    //Implements Closeable to become able to be used with try-with-resources
    private class DBConnection implements Closeable {
        private EntityManager em;

        public DBConnection(EntityManagerFactory emf) {
            em = emf.createEntityManager();
            em.getTransaction().begin();
        }

        //Used during queries to make calls to database
        public EntityManager getEntityManager() {
            return em;
        }

        @Override
        public void close() throws IOException {
            em.getTransaction().commit();
            em.close();
        }
    }
}

