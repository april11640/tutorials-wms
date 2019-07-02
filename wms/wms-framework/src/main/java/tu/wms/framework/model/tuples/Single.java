package tu.wms.framework.model.tuples;

/**
 * Created by zsp on 2018/7/9.
 */
public class Single<T1> {

    protected T1 item1;

    public Single() {}

    public Single(T1 item1) {
        this.item1 = item1;
    }

    public final T1 getItem1() {
        return item1;
    }

    public void setItem1(T1 item1) {
        this.item1 = item1;
    }
}
