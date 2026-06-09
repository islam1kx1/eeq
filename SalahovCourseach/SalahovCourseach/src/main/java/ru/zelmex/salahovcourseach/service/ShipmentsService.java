package ru.zelmex.salahovcourseach.service;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.zelmex.salahovcourseach.model.Shipments;
import ru.zelmex.salahovcourseach.repository.ShipmentsDao;
import ru.zelmex.salahovcourseach.util.HibernateSessionFactoryUtil;
import java.util.List;

public class ShipmentsService {

    private ShipmentsDao shipmentsDao = new ShipmentsDao();

    public ShipmentsService() {
    }

    public List<Shipments> findAll() {
        return shipmentsDao.findAll();
    }

    // Новый метод для получения отгрузок с названиями
    public List<Object[]> findAllWithNames() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            String hql = "SELECT s.shipmentId, m.name, d.name, s.quantity, s.date " +
                    "FROM Shipments s " +
                    "JOIN ModelLines m ON s.modelId = m.modelId " +
                    "JOIN Dealers d ON s.dealerId = d.dealerId";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            return query.list();
        }
    }

    public Shipments findOne(final long id) {
        return shipmentsDao.findOne(id);
    }

    public void save(final Shipments entity) {
        if (entity == null) return;
        shipmentsDao.save(entity);
    }

    public void update(final Shipments entity) {
        if (entity == null) return;
        shipmentsDao.update(entity);
    }

    public void delete(final Shipments entity) {
        if (entity == null) return;
        shipmentsDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null) return;
        shipmentsDao.deleteById(id);
    }
}