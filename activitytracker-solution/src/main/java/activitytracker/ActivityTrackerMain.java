package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ActivityTrackerMain {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");

    private void insertActivity(Activity... activities) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        for (Activity activity : activities) {
            em.persist(activity);
        }

        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args) {

        Activity activity1 = new Activity(LocalDateTime.of(2021, 5, 17, 14, 30, 0),
                "Hiking in mountains",
                Type.HIKING);
        Activity activity2 = new Activity(LocalDateTime.of(2021, 6, 17, 14, 30, 0),
                "Basketball with friends",
                Type.BASKETBALL);
        Activity activity3 = new Activity(LocalDateTime.of(2021, 7, 17, 14, 30, 0),
                "Running on Margaret-Island",
                Type.RUNNING);
        ActivityTrackerMain atm = new ActivityTrackerMain();
        atm.insertActivity(activity1, activity2);
        atm.insertActivity(activity3);

        System.out.println(activity1);
        System.out.println(activity2);
        System.out.println(activity3);
    }

}
