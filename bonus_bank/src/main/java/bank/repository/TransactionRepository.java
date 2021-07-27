package bank.repository;

import bank.model.transaction.Transaction;
import bank.model.transaction.TransactionType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class TransactionRepository {
    private final EntityManagerFactory factory;

    public TransactionRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public List<Transaction> getTransactions() {
        EntityManager em = factory.createEntityManager();
        List<Transaction> result = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
        em.close();
        return result;
    }

    public void save(Transaction transaction) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
        em.close();
    }

    public List<Transaction> getTransfersByAccountNumber(String number) {
        EntityManager em = factory.createEntityManager();
        List<Transaction> result = em.createQuery(
                "select t from Transaction t where t.accountNumber = :number and (t.type = :typeIn or t.type = :typeOut)", Transaction.class)
                .setParameter("number", number)
                .setParameter("typeIn", TransactionType.INPUT_TRANSFER)
                .setParameter("typeOut", TransactionType.OUTPUT_TRANSFER)
                .getResultList();
        em.close();
        return result;
    }
}
