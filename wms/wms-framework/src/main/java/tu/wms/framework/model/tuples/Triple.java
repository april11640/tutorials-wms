package tu.wms.framework.model.tuples;

/**
 *
 * Created by zsp on 2018/7/9.
 */
public class Triple<T1, T2, T3> extends Pair<T1, T2> {

    protected  T3 item3;

    public Triple() {}

    public Triple(T1 item1, T2 item2, T3 item3) {
        super(item1, item2);
        this.item3 = item3;
    }

    public final T3 getItem3() {
        return item3;
    }

    public void setItem3(T3 item3) {
        this.item3 = item3;
    }
}
