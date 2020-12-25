package app.repository;

import app.model.BaseModel;

public interface IRestRepository<T extends BaseModel> {
    T[] select();
    T insert(T entity);
}


