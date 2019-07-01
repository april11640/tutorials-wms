package tu.wms.framework.model.tuples;

/**
 * Created by zsp on 2018/7/9.
 */
public class Pair<T1, T2> extends Single<T1> {

    protected final T2 item2;

    public Pair(T1 item1, T2 item2) {
        super(item1);
        this.item2 = item2;
    }

    public final T2 getItem2() {
        return item2;
    }

}
