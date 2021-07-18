package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
}
