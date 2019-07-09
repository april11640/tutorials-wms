package tu.wms.framework.model;

public class CommonConstants {

    public final static int DUPLICATE_KEY_AFFECTED_ROWS = -2;

    public static void main(String[] args) {
        long a = 1L << 41;
        long b = 1000L * 60 * 60 * 24 * 365;
        System.out.println(a + "/" + b + "=" + (a / b));

        int count = 0;
        for(int i = 0; i <= 0b1111111111; i++) {
            ++count;
        }
        System.out.println(count);
        System.out.println(1024L * 4096);
    }

}
