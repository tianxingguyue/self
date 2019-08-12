package com.job.core.mongodb.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RepositoryData implements Serializable {

    private String key;

    private Object value;

    private Object[] values;

}
