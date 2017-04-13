package jp.k_u.util;



public final class Console {
    public static void print(Object[] values){
        for (Object value : values) {
            System.out.print(value);
            System.out.println(" ");
        }
        System.out.println();
    }

    public static void print(int[] values) {
        for (int value : values) {
            System.out.print(value);
            System.out.println(" ");
        }
        System.out.println();
    }
    public static void print(Object value1 , Object...values){
        System.out.print(value1);
        for (Object value : values) {
            System.out.print(" ");
            System.out.print(value);
        }
        System.out.println();
    }
    public static void print(Iterable<?> values){
        for (Object value : values) {
            System.out.print(value);
            System.out.print(" ");
        }
        System.out.println();
    }
}
