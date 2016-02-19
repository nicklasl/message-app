package dao;

import dao.models.Message;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    @Override
    public Message store(Message message) {
        final EntityManager em = JPA.em();
        em.persist(message);
        em.flush();
        return message;
    }

    @Override
    public void markAsViewed(List<Long> messageIds) {
        final CriteriaBuilder criteriaBuilder = JPA.em().getCriteriaBuilder();
        final CriteriaUpdate<Message> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Message.class);
        Root<Message> root = criteriaUpdate.from(Message.class);
        final Predicate in = root.get(Message.ID).in(messageIds);
        criteriaUpdate.set(root.get(Message.VIEWED), true);
        final int updated = JPA.em().createQuery(criteriaUpdate.where(in)).executeUpdate();
        Logger.debug("Updated " + updated + " items");
    }

    @Override
    public void remove(List<Long> idsToRemove) {
        final CriteriaBuilder criteriaBuilder = JPA.em().getCriteriaBuilder();
        final CriteriaDelete<Message> criteriaDelete = criteriaBuilder.createCriteriaDelete(Message.class);
        Root<Message> root = criteriaDelete.from(Message.class);
        final Predicate in = root.get(Message.ID).in(idsToRemove);
        final int removed = JPA.em().createQuery(criteriaDelete.where(in)).executeUpdate();
        Logger.debug("Removed " + removed + " items");
    }

    @Override
    public List<Message> listAll(String recipient, int start, int stop) {
        final EntityManager em = JPA.em();
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        final CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = criteriaQuery.from(Message.class);
        final Predicate where = criteriaBuilder.equal(root.get(Message.RECIPIENT), recipient);
        final Order order = criteriaBuilder.desc(root.get(Message.SENT));
        criteriaQuery.select(root).where(where).orderBy(order);
        final int numberOfResults = stop - start;
        return em.createQuery(criteriaQuery)
                .setFirstResult(start)
                .setMaxResults(numberOfResults)
                .getResultList();
    }

    @Override
    public List<Message> listNew(String recipient) {
        final EntityManager em = JPA.em();
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        final CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> root = criteriaQuery.from(Message.class);
        final Predicate whereRecipient = criteriaBuilder.equal(root.get(Message.RECIPIENT), recipient);
        final Predicate whereUnread = criteriaBuilder.equal(root.get(Message.VIEWED), false);
        final Predicate where = criteriaBuilder.and(whereRecipient, whereUnread);
        final Order order = criteriaBuilder.desc(root.get(Message.SENT));
        criteriaQuery.select(root).where(where).orderBy(order);
        return em.createQuery(criteriaQuery).getResultList();
    }
}
