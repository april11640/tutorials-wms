package tu.wms.framework.model;

/**
 *
 * @param <E>   The enum type subclass
 */
public interface CodeEnum<E extends Enum<E>> {

    /**
     *
     * @return
     */
    int getCode();

}
