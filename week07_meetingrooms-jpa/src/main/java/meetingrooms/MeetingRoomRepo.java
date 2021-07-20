package meetingrooms;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MeetingRoomRepo implements MeetingRoomsRepository {

    private EntityManagerFactory factory;

    public MeetingRoomRepo(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public MeetingRoom save(String name, int width, int length) {
        EntityManager em = factory.createEntityManager();
        MeetingRoom room = new MeetingRoom(name, width, length);
        em.getTransaction().begin();
        em.persist(room);
        em.getTransaction().commit();
        em.close();
        return room;
    }

    @Override
    public List<String> getMeetingroomsOrderedByName() {
        EntityManager em = factory.createEntityManager();
        List<String> names = em
                .createQuery("select m.name from MeetingRoom m order by m.name", String.class)
                .getResultList();
        em.close();
        return names;
    }

    @Override
    public List<String> getEverySecondMeetingRoom() {
        EntityManager em = factory.createEntityManager();
        List<String> names = em
                .createQuery("select m.name from MeetingRoom m order by m.name", String.class)
                .getResultList();
        em.close();

        return IntStream.range(0, names.size())
                .filter(i -> i % 2 == 1)
                .mapToObj(names::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<MeetingRoom> getMeetingRooms() {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> rooms = em.createQuery("select mr from MeetingRoom mr", MeetingRoom.class).getResultList();
        em.close();
        return rooms;
    }

    @Override
    public List<MeetingRoom> getExactMeetingRoomByName(String name) {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> rooms = em.createQuery("select mr from MeetingRoom mr where mr.name = :name", MeetingRoom.class)
                .setParameter("name", name)
                .getResultList();
        em.close();
        return rooms;

    }

    @Override
    public List<MeetingRoom> getMeetingRoomsByPrefix(String nameOrPrefix) {
        EntityManager em = factory.createEntityManager();
        List<MeetingRoom> rooms = em.createQuery("select mr from MeetingRoom mr where mr.name like :name order by mr.name", MeetingRoom.class)
                .setParameter("name", nameOrPrefix + "%")
                .getResultList();
        em.close();
        return rooms;
    }

    @Override
    public void deleteAll() {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("delete from MeetingRoom").executeUpdate();
        em.getTransaction().commit();
        em.close();

    }
}
