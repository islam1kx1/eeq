package ru.zelmex.salahovcourseach.service;

import ru.zelmex.salahovcourseach.model.Shipments;
import ru.zelmex.salahovcourseach.repository.ShipmentsDao;
import java.util.List;

public class ShipmentsService {

    private ShipmentsDao shipmentsDao = new ShipmentsDao();

    public ShipmentsService() {
    }

    public List<Shipments> findAll() {
        return shipmentsDao.findAll();
    }

    public Shipments findOne(final long id) {
        return shipmentsDao.findOne(id);
    }

    public void save(final Shipments entity) {
        if (entity == null)
            return;
        shipmentsDao.save(entity);
    }

    public void update(final Shipments entity) {
        if (entity == null)
            return;
        shipmentsDao.update(entity);
    }

    public void delete(final Shipments entity) {
        if (entity == null)
            return;
        shipmentsDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null)
            return;
        shipmentsDao.deleteById(id);
    }
}