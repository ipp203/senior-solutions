package bank.account;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Repository
public class AccountRepository {
    private final EntityManagerFactory factory;

    public AccountRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveAccount(Account account) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
        em.close();
    }

    public List<Account> listActiveAccounts() {
        EntityManager em = factory.createEntityManager();
        List<Account> result = em.createQuery("select a from Account a where a.status = :type order by a.openingDate", Account.class)
                .setParameter("type", AccountStatus.ACTIVE)
                .getResultList();
        em.close();
        return result;
    }

    public List<Account> listAllAccounts() {
        EntityManager em = factory.createEntityManager();
        List<Account> result = em.createQuery("select a from Account a order by a.openingDate", Account.class)
                .getResultList();
        em.close();
        return result;
    }

    public Account updateAccountName(long id, String name) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Account account = em.find(Account.class, id);
        if (account == null || account.getStatus() == AccountStatus.INACTIVE) {
            throw new AccountNotFoundException(id);
        }
        account.setName(name);
        em.getTransaction().commit();
        em.close();
        return account;
    }

    public Account updateAccountBalance(long id, int balance) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Account account = em.find(Account.class, id);
        if (account == null || account.getStatus() == AccountStatus.INACTIVE) {
            throw new AccountNotFoundException(id);
        }
        account.setBalance(balance);
        em.getTransaction().commit();
        em.close();
        return account;
    }

    public void deleteAccount(long id) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        Account account = getActiveAccount(id, em);
        account.setStatus(AccountStatus.INACTIVE);
        em.getTransaction().commit();
        em.close();
    }

    public Account findAccountById(long id) {
        EntityManager em = factory.createEntityManager();
        Account account = getActiveAccount(id, em);
        em.close();
        return account;
    }

    public Account findActiveAccountByNumber(String accountNumber) {
        EntityManager em = factory.createEntityManager();
        try {
            return em.createQuery("select a from Account a where a.accountNumber = :number and a.status = :type", Account.class)
                    .setParameter("number", accountNumber)
                    .setParameter("type", AccountStatus.ACTIVE)
                    .getSingleResult();
        } catch (NoResultException | NonUniqueResultException nre) {
            throw new AccountNotFoundException(accountNumber);
        } finally {
            em.close();
        }
    }


    private Account getActiveAccount(long id, EntityManager em) {
        Account account = em.find(Account.class, id);
        if (account == null || account.getStatus() == AccountStatus.INACTIVE) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }
}
