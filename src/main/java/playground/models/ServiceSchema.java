package playground.models;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ServiceSchema {
    private int total;
    private int limit;
    private int skip;
    @JsonProperty("Data")
    private List<Data> data;

    public ServiceSchema() {
        super();
    }

    public ServiceSchema (int total, int limit, int skip, List<Data> data) {
        this.total = total;
        this.limit = limit;
        this.skip = skip;
        this.data = (List<Data>) data;
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
