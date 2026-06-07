package ru.zelmex.salahovcourseach.service;
import ru.zelmex.salahovcourseach.model.ModelLines;
import ru.zelmex.salahovcourseach.repository.ModelLinesDao;
import java.util.List;
public class ModelLinesService {

    private final ModelLinesDao modelLinesDao = new ModelLinesDao();
    private ModelLinesDao clientDao = new ModelLinesDao();
    public List<ModelLines> findAll() {
        return clientDao.findAll();
    }
    public ModelLines findOne(final long id) {
        return clientDao.findOne(id);
    }
    public void save(final ModelLines entity)
    {
        if (entity == null)
            return;
        clientDao.save(entity);
    }
    public void update(final ModelLines entity)
    {
        if (entity == null)
            return;
        clientDao.update(entity);
    }
    public void delete(final ModelLines entity)
    {
        if (entity == null)
            return;
        clientDao.delete(entity);
    }
    public void deleteById(final Long id)
    {
        if (id == null)
            return;
        clientDao.deleteById(id);
    }

}