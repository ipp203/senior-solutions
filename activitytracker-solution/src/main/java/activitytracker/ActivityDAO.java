package activitytracker;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;


public class ActivityDAO {
    private final EntityManagerFactory factory;

    public ActivityDAO(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveActivity(Activity activity) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(activity);
        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityById(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em.find(Activity.class, id);
        em.close();
        return activity;
    }

    public List<Activity> listActivities() {
        EntityManager em = factory.createEntityManager();
        List<Activity> activities = em.createQuery("select a from Activity a", Activity.class).getResultList();
        em.close();
        return activities;
    }

    public void updateActivity(long id, String description) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        Activity activity = em.find(Activity.class, id);
        activity.setDescription(description);

        em.getTransaction().commit();
        em.close();
    }

    public Activity findActivityByIdWithLabels(long id) {
        EntityManager em = factory.createEntityManager();

        Activity activity = em
                .createQuery("select a from Activity a join fetch a.labels where a.id = :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithTrackPoints(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em
                .createQuery("select a from Activity a join fetch a.trackPoints where a.id= :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public Activity findActivityByIdWithAreas(long id) {
        EntityManager em = factory.createEntityManager();
        Activity activity = em
                .createQuery("select a from Activity a join fetch a.areas where a.id= :id", Activity.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return activity;
    }

    public List<Coordinate> findTrackPointCoordinatesByDate(LocalDateTime afterThis, int start, int max) {
        EntityManager em = factory.createEntityManager();
        List<Coordinate> result = em
                .createNamedQuery("findTrackPointCoordinatesByDate", Coordinate.class)
                .setParameter("time", afterThis)
                .setFirstResult(start)
                .setMaxResults(max)
                .getResultList();
        em.close();
        return result;
    }

    public List<Object[]> findTrackPointCountByActivity() {
        EntityManager em = factory.createEntityManager();
        List<Object[]> result = em
                .createQuery("select a.description, count(t) from Activity a join a.trackPoints t group by a order by a.description", Object[].class)
                .getResultList();
        em.close();
        return result;

    }
}
