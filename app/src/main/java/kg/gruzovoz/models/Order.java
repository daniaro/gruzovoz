package kg.gruzovoz.models;

import java.util.List;

public class Order {

    private long count;
    private String next;
    private String previous;
    private List<Results> results;

    public Order(){
        super();
    }

    public Order(long count, String next, String previous, List<Results> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public long getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<Results> getResults() {
        return results;
    }
}
