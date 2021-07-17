package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityDAOIT {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
    EntityManager em;

    Activity activity1 = new Activity(LocalDateTime.of(2021, 5, 17, 14, 30, 0),
            "Hiking in mountains",
            Type.HIKING);
    Activity activity2 = new Activity(LocalDateTime.of(2021, 6, 17, 14, 30, 0),
            "Basketball with friends",
            Type.BASKETBALL);
    Activity activity3 = new Activity(LocalDateTime.of(2021, 7, 17, 14, 30, 0),
            "Running on Margaret-Island",
            Type.RUNNING);

    @BeforeEach
    void init() {
        em = factory.createEntityManager();
    }

    @AfterEach
    void clean() {
        em.close();
    }

    @Test
    void saveActivity() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activityDAO.saveActivity(activity1);

        Activity activity = em.find(Activity.class, activity1.getId());
        assertEquals(activity, activity1);
    }

    @Test
    void findActivityById() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activityDAO.saveActivity(activity1);

        Activity activity = activityDAO.findActivityById(activity1.getId());
        assertEquals(activity, activity1);
    }

    @Test
    void listActivities() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activityDAO.saveActivity(activity1);
        activityDAO.saveActivity(activity2);
        activityDAO.saveActivity(activity3);

        List<Activity> activities = activityDAO.listActivities();

        assertEquals(3, activities.size());
    }
}