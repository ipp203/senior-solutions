package activitytracker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AreaDao {
    private EntityManagerFactory factory;

    public AreaDao(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveArea(Area area) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();

        em.persist(area);

        em.getTransaction().commit();
        em.close();
    }
}
