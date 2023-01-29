package playground.models;
import java.util.List;

public class ServiceSchema {
    private int total;
    private int limit;
    private int skip;

    private List<Data> data;

    public ServiceSchema() {
    }

    public ServiceSchema(int total, int limit, int skip, List<Data> data) {
        this.total = total;
        this.limit = limit;
        this.skip = skip;
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int totalCount) {
        this.total = totalCount;
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
}
