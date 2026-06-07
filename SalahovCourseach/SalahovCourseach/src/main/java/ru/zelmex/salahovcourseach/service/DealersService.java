package ru.zelmex.salahovcourseach.service;
import ru.zelmex.salahovcourseach.model.Dealers;
import ru.zelmex.salahovcourseach.repository.DealersDao;
import java.util.List;
public class DealersService {
    private DealersDao DealersDAO = new DealersDao();
    public DealersService() {
    }
    public List<Dealers> findAll() {
        return DealersDAO.findAll();
    }
    public Dealers findOne(final long id) {
        return DealersDAO.findOne(id);
    }
    public void save(final Dealers entity)
    {
        if (entity == null)
            return;
        DealersDAO.save(entity);
    }
    public void update(final Dealers entity)
    {
        if (entity == null)
            return;
        DealersDAO.update(entity);
    }
    public void delete(final Dealers entity)
    {
        if (entity == null)
            return;
        DealersDAO.delete(entity);
    }
    public void deleteById(final Long id)
    {
        if (id == null)
            return;
        DealersDAO.deleteById(id);
    }
}
