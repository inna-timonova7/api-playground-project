package playground.models;

import java.sql.Time;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ServiceSchema {
    private int totalCount;
    private int limit;
    private int skip;
    private Class data;

//    public ServiceSchema (int totalCount, int limit, int skip, Class data) {
//        this.totalCount = totalCount;
//        this.limit = limit;
//        this.skip = skip;
//        this.data = Data;
//    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    class Data {
        private int id;
        private String name;
        private Date updatedAt;
        private Date createdAt;

        public Data(int id, String name, Date updatedAt, Date createdAt) {
            this.id = id;
            this.name = name;
            this.updatedAt = updatedAt;
            this.createdAt = createdAt;
        }
    }
}
