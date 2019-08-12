package com.job.core.base.service.impl;


import com.job.core.base.dao.BaseDao;
import com.job.core.base.entity.BaseEntity;
import com.job.core.base.entity.CommonBaseEntity;
import com.job.core.base.id.IdGenerate;
import com.job.core.base.service.BaseService;
import com.job.core.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author lizhiliang
 * @createTime 2017-12-08 17:33
 */
public abstract class BaseServiceImpl<I extends Serializable, T extends BaseEntity<I>>
        implements BaseService<I, T> {

    /*@Autowired
    private IdGenerate<I> idGenerator;*/

    protected abstract BaseDao<I, T> getDao();

   /* @Override
    public final I getId() {
        return idGenerator.generate();
    }*/


    @Override
    @Transactional
    public T save(T entity) {
        if (entity == null) {
            throw new RuntimeException("不允许保存空对象");
        }
        setSaveTimes(entity);
        //entity.setId(getId());
        getDao().insert(entity);
        return entity;
    }

    @Override
    @Transactional
    public Collection<T> save(Collection<T> list) {
        if (list == null) {
            throw new RuntimeException("list is null");
        }
        for (T t : list) {
            save(t);
        }
        return list;
    }

    @Override
    @Transactional
    public T saveSelective(T entity) {
        if (entity == null) {
            throw new RuntimeException("不允许保存空对象");
        }
        setSaveTimes(entity);
       // entity.setId(getId());
        getDao().insertSelective(entity);
        return entity;
    }

    private void setSaveTimes(T entity) {
        if (entity instanceof CommonBaseEntity) {
            CommonBaseEntity<I> t = (CommonBaseEntity<I>) entity;
            Date nowDate = new Date();
            t.setCreateTime(nowDate);
            t.setUpdateTime(nowDate);
        }
    }

    private void setUpdateTimes(T entity) {
        if (entity instanceof CommonBaseEntity) {
            CommonBaseEntity<I> t = (CommonBaseEntity<I>) entity;
            Date nowDate = new Date();
            t.setUpdateTime(nowDate);
        }
    }

    @Override
    public T getByAppIdAndId(String appId, I id) {
        if (appId == null || appId.isEmpty() || id == null) {
            return null;
        }
        return getDao().selectByAppIdAndId(appId, id);
    }









    @Override
    @Transactional
    public int updateByAppIdAndId(String appId, T entity) {
        if (appId == null || appId.isEmpty() || entity == null) {
            return 0;
        }
        setUpdateTimes(entity);
        if (entity instanceof CommonBaseEntity) {
            CommonBaseEntity<I> baseEntity = (CommonBaseEntity<I>) entity;
            baseEntity.setAppId(appId);
        }
        return getDao().updateByAppIdAndId(entity);
    }

    @Override
    @Transactional
    public int updateByAppIdAndId(String appId, Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int rows = 0;
        for (T t : list) {
            rows += updateByAppIdAndId(appId, t);
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateByAppIdAndIdSelective(String appId, T entity) {
        if (entity == null) {
            return 0;
        }
        setUpdateTimes(entity);
        if (entity instanceof CommonBaseEntity) {
            CommonBaseEntity<I> baseEntity = (CommonBaseEntity<I>) entity;
            baseEntity.setAppId(appId);
        }
        return getDao().updateByAppIdAndIdSelective(entity);
    }

    @Override
    @Transactional
    public int updateByAppIdAndIdSelective(String appId, Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int rows = 0;
        for (T t : list) {
            rows += updateByAppIdAndIdSelective(appId, t);
        }
        return rows;
    }

    @Override
    @Transactional
    public int deleteByAppIdAndId(String appId, I id) {
        return getDao().deleteByAppIdAndId(appId, id);
    }

    @Override
    @Transactional
    public int deleteByAppIdAndIds(String appId, Collection<I> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return getDao().deleteByAppIdAndIds(appId, list);
    }

    @Override
    @Transactional
    public int removeByAppIdAndId(String appId, I id) {
        try {
            return getDao().removeByAppIdAndId(appId, id);
        } catch (Exception e) {
            throw new BizException(10000, "无法软删除!");
        }
    }

    @Override
    @Transactional
    public int removeByAppIdAndIds(String appId, Collection<I> ids) {
        try {
            return getDao().removeByAppIdAndIds(appId, ids);
        } catch (Exception e) {
            throw new BizException(10000, "无法软删除!");
        }
    }


}
