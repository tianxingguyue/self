package com.job.core.mongodb.service.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.job.core.mongodb.entity.RepositoryData;
import com.job.core.mongodb.service.BaseMongoDbRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public class BaseMongodbRepositoryImpl<T> implements BaseMongoDbRepository<T> {


    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<T> entityClass;

    public BaseMongodbRepositoryImpl(){
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType)genType).getActualTypeArguments();
            entityClass = (Class<T>) types[0];
        }else{
            entityClass = (Class<T>) Object.class;
        }
    }
    @Override
    public void add(T t) {
        mongoTemplate.save(t);
    }

    @Override
    public void add(T t, String collectionName) {
        mongoTemplate.save(t,collectionName);
    }

    @Override
    public T get(String key,Object params) {
        Query query = new Query(Criteria.where(key).is(params));
        T one = mongoTemplate.findOne(query, entityClass);
        return one;
    }

    @Override
    public T get(String collectionName, String key, Object params) {
        Query query = new Query(Criteria.where(key).is(params));
        T one = mongoTemplate.findOne(query, entityClass,collectionName);
        return one;
    }

    @Override
    public List<T> getListAll() {
        List<T> tList = mongoTemplate.findAll(entityClass);
        return tList;
    }

    @Override
    public List<T> getListAll(String collectionName) {
        List<T> all = mongoTemplate.findAll(entityClass, collectionName);
        return all;
    }

    @Override
    public T getById(Object id) {
        T t = mongoTemplate.findById(id, entityClass);
        return t;
    }

    @Override
    public T getById(Object id, String collectionName) {
        T t = mongoTemplate.findById(id, entityClass, collectionName);
        return t;
    }

    @Override
    public long delete(String key,Object obj,String collectionName) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        DeleteResult deleteResult = collection.deleteOne(Filters.eq(key, obj));
        long deletedCount = deleteResult.getDeletedCount();
        return deletedCount;
    }

    @Override
    public long deleteAll(String key, Object obj,String collectionName) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        DeleteResult deleteResult = collection.deleteMany(Filters.eq(key, obj));
        long deletedCount = deleteResult.getDeletedCount();
        return deletedCount;
    }

    @Override
    public long remove(String key, Object obj) {
        Query query = new Query(Criteria.where(key).is(obj));
        DeleteResult remove = mongoTemplate.remove(query, entityClass);
        long deletedCount = remove.getDeletedCount();
        return deletedCount;
    }

    @Override
    public long updateMulti(String key,String params, List<RepositoryData> repositories) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        if(repositories != null && !repositories.isEmpty()){
           for(RepositoryData data:repositories){
               update.set(data.getKey(),data.getValue());
           }
        }
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, entityClass);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long updateMulti(String collectionName, String key, String params, List<RepositoryData> repositories) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        if(repositories != null && !repositories.isEmpty()){
            for(RepositoryData data:repositories){
                update.set(data.getKey(),data.getValue());
            }
        }
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, entityClass,collectionName);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long updateMultiPush(String collectionName, String key, String params, List<RepositoryData> repositories) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        if(repositories != null && !repositories.isEmpty()){
            for(RepositoryData data:repositories){
                update.push(data.getKey(),data.getValue());
            }
        }
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, entityClass,collectionName);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long updateOne(String key, String params,RepositoryData data) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        update.set(data.getKey(),data.getValue());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, entityClass);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long updateOnePush(String collectionName, String key, String params,RepositoryData data) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        update.push(data.getKey(),data.getValue());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, entityClass,collectionName);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long updateOnePushAll(String collectionName, String key, String params, RepositoryData data) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        int  count = 0;
        if(data.getValues() != null && data.getValues().length > 0){
           for(int i=0;i < data.getValues().length;i++){
               update.push(data.getKey(),data.getValues()[i]);
               UpdateResult updateResult = mongoTemplate.updateFirst(query, update,collectionName);
               count += updateResult.getModifiedCount();
           }
        }
        return count;
    }

    @Override
    public long updateOne(String collectionName, String key, String params, RepositoryData data) {
        Query query = new Query(Criteria.where(key).is(params));
        Update update = new Update();
        update.set(data.getKey(),data.getValue());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, entityClass,collectionName);
        long modifiedCount = updateResult.getModifiedCount();
        return modifiedCount;
    }

    @Override
    public long getCountByClass(String collectionName,String key, String params) {
        Query query = new Query(Criteria.where(key).is(params));
        long count = mongoTemplate.count(query, entityClass, collectionName);
        return count;
    }

    @Override
    public long getCountByParams(String collectionName,String key,String params) {
        Query query = new Query(Criteria.where(key).is(params));
        long count = mongoTemplate.count(query,collectionName);
        return count;
    }

    @Override
    public long getCount(String key, String params) {
        Query query = new Query(Criteria.where(key).is(params));
        long count = mongoTemplate.count(query, entityClass);
        return count;
    }

    @Override
    public boolean isExists(String key, String params) {
        Query query = new Query(Criteria.where(key).is(params));
        boolean exists = mongoTemplate.exists(query, entityClass);
        return exists;
    }

    @Override
    public boolean isExistsByParams(String collectionName, String key, String params) {
        Query query = new Query(Criteria.where(key).is(params));
        boolean exists = mongoTemplate.exists(query, collectionName);
        return exists;
    }

    @Override
    public boolean isExistsByClass(String collectioName, String key, String params) {
        Query query = new Query(Criteria.where(key).is(params));
        boolean exists = mongoTemplate.exists(query, entityClass, collectioName);
        return exists;
    }
}
