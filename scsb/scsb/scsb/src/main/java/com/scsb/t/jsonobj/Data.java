package com.scsb.t.jsonobj;
@lombok.Data
public class Data<T> {
 public T data;
    public Data(T t) {
        this.data = t;
    }
}
