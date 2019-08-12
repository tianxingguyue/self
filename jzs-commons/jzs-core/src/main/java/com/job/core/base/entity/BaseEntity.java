package com.job.core.base.entity;

import com.job.core.base.dto.PageDTO;

import java.io.Serializable;

/**
 * @author lizhiliang
 * @createTime 2017-12-08 17:34
 */
public abstract class BaseEntity<I extends Serializable> extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否分页
     */
    private Boolean isPage;

    public Boolean getPage() {
        return isPage;
    }

    public void setPage(Boolean page) {
        isPage = page;
    }

    public abstract I getId();

    public abstract void setId(I id);
}
