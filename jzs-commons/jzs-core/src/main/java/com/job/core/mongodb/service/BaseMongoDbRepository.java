package com.job.core.mongodb.service;


import com.job.core.mongodb.entity.RepositoryData;

import java.util.List;

public interface BaseMongoDbRepository<T> {

    /**
     * 添加数据到mongdb
     */
    void add(T t);
    /**
     * 添加数据到mongdb，需要传collectionName
     */
    void add(T t, String collectionName);
    /**
     * 根据字段查询信息
     */
    T get(String key, Object params);
    /**
     * 查询集合的某个对象
     */
    T get(String collectionName, String key, Object params);
    /**
     * 查询全部列表
     */
    List<T> getListAll();
    /**
     * 根据集合名称查询全部对象
     */
    List<T> getListAll(String collectionName);
    /**
     * 通过id获取信息
     */
    T getById(Object id);
    /**
     * 通过id获取信息，需要传collectionName
     */
    T getById(Object id, String collectionName);
    /**
     * 根据字段删除信息
     */
    long delete(String key, Object obj, String collectionName);
    /**
     * 删除一定数量的信息
     */
    long deleteAll(String key, Object obj, String collectionName);
    /**
     * 移除单条信息
     */
    long remove(String key, Object obj);
    /**
     * 根据字段修改多条信息
     */
    long updateMulti(String key, String params, List<RepositoryData> repositories);
    /**
     * 根据集合名称和字段信息修改多条信息
     */
    long updateMulti(String collectionName, String key, String params, List<RepositoryData> repositories);
    /**
     * 根据集合名称字段信息批量修改， 将有效值追加到对应字段信息上
     * 使用的前提是某个字段保存时必须是数组形式，否则会有数据格式异常
     */
    long updateMultiPush(String collectionName, String key, String params, List<RepositoryData> repositories);
    /**
     * 根据字段修改单条信息
     */
    long updateOne(String key, String params, RepositoryData data);
    /**
     * 修改字段信息，将有效值追加到对应字段信息上
     * 使用的前提是某个字段保存时必须是数组形式，否则会有数据格式异常
     */
    long updateOnePush(String collectionName, String key, String params, RepositoryData data);
    /**
     * 修改字段信息，将多个有效值一起追加到对应字段上
     * 使用的前提是某个字段保存时必须是数组形式，否则会有数据格式异常
     */
    long updateOnePushAll(String collectionName, String key, String params, RepositoryData data);
    /**
     * 根据集合名称和字段信息修改单条记录信息
     */
    long updateOne(String collectionName, String key, String params, RepositoryData data);
    /**
     * 计算数量，根据对象类型和集合名称（数据表）
     */
    long getCountByClass(String collectionName, String key, String params);
    /**
     * 计算数量，根据参数和集合名称（数据表）
     */
    long getCountByParams(String collectionName, String key, String params);
    /**
     * 计算数量，根据参数
     */
    long getCount(String key, String params);
    /**
     * 根据参数查询是否存在当前的对象
     */
    boolean isExists(String key, String params);
    /**
     * 根据集合名称和参数查询是否存在当前对象
     */
    boolean isExistsByParams(String collectionName, String key, String params);
    /**
     * 根据集合名称，对象类型，参数查询是否存在当前对象
     */
    boolean isExistsByClass(String collectioName, String key, String params);



}
