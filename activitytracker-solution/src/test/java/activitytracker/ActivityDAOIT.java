package activitytracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.Duration;
import java.time.LocalDate;
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

    @Test
    void updateActivity() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activityDAO.saveActivity(activity1);
        activityDAO.updateActivity(activity1.getId(), "***" + activity1.getDescription());

        Activity updatedActivity = activityDAO.findActivityById(activity1.getId());

        assertEquals("***" + activity1.getDescription(), updatedActivity.getDescription());

        assertTrue(updatedActivity.getCreatedAt().isBefore(updatedActivity.getUpdatedAt()));

        Duration duration = Duration.between(updatedActivity.getCreatedAt(), updatedActivity.getUpdatedAt());
        assertTrue(duration.toMillis() < 120);
        System.out.println(duration.toMillis());
    }

    @Test
    void findActivityByIdWithLabels() {

        ActivityDAO activityDAO = new ActivityDAO(factory);
        activity1.setLabels(List.of("bbbb", "aaaa", "cccc"));

        activityDAO.saveActivity(activity1);

        Activity activity = activityDAO.findActivityByIdWithLabels(activity1.getId());

        assertEquals(3, activity.getLabels().size());
        assertEquals("aaaa", activity.getLabels().get(0));

    }

    @Test
    void findActivityByIdWithTrackPoints() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 11), 43.001, 43.1));
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 10), 43.002, 43.11));
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 12), 43.005, 43.11));

        activityDAO.saveActivity(activity1);

        Activity activity = activityDAO.findActivityByIdWithTrackPoints(activity1.getId());

        assertEquals(3, activity.getTrackPoints().size());
        assertEquals(LocalDate.of(2021, 6, 10), activity.getTrackPoints().get(0).getTime());
    }

    @Test
    void findActivityByIdWithArea() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        AreaDao areaDao = new AreaDao(factory);

        Area area1 = new Area("Badacsony");
        Area area2 = new Area("Gulacs");
        Area area3 = new Area("Somlo");

        areaDao.saveArea(area1);
        areaDao.saveArea(area2);
        areaDao.saveArea(area3);

        activity1.addArea(area2);
        activity1.addArea(area1);

        activity2.addArea(area1);
        activity2.addArea(area3);

        activityDAO.saveActivity(activity1);
        activityDAO.saveActivity(activity2);

        Activity activity1FromDB = activityDAO.findActivityByIdWithAreas(activity1.getId());

        assertEquals(2, activity1FromDB.getAreas().size());
        assertEquals("Badacsony", activity1FromDB.getAreas().get(0).getName());

    }

    @Test
    void findTrackPointCoordinatesByDate() {
        ActivityDAO activityDAO = new ActivityDAO(factory);
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 11), 43.001, 43.1));
        activity1.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 10), 43.002, 43.11));

        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 11), 43.001, 43.1));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 10), 43.002, 43.11));
        activity2.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 12), 43.005, 43.11));

        activity3.addTrackPoint(new TrackPoint(LocalDate.of(2021, 6, 11), 43.001, 43.1));

        activityDAO.saveActivity(activity1);
        activityDAO.saveActivity(activity2);
        activityDAO.saveActivity(activity3);

        List<Coordinate> coordinates =
                activityDAO.findTrackPointCoordinatesByDate(
                        LocalDateTime.of(2021, 6, 11, 12, 0), 0, 10);

        assertEquals(4, coordinates.size());


    }
}