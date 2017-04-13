package jp.k_u.util;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Try {
    public static <T> T orElse(ThrowableSupplier<T> value , Function<Exception , T> or) {
        try{
            return value.get();
        }catch (Exception e){
            return or.apply(e);
        }
    }
    public static <T , S> Function<T , S> catching(ThrowableFunction<T , S> f , BiFunction<Exception , T , S> or){
        return t -> {
            try{
                return f.apply(t);
            }catch (Exception e){
                return or.apply(e , t);
            }
        };
    }
    public static <T> T apply(ThrowableSupplier<T> value){
        try{
            return value.get();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @FunctionalInterface
    public static interface ThrowableSupplier<T>{
        public T get() throws Exception;
    }
    @FunctionalInterface
    public static interface ThrowableFunction<T , S>{
        public S apply(T value)throws Exception;
    }
    /*
    @Deprecated
    public static <T , S> Catching<T , S> Do(ThrowableFunction<T , S> function){
        return new Catching<T, S>(function);
    }
    public static class Catching<T , S>{
        private ThrowableFunction<T , S> throwable_function;
        private Catching(ThrowableFunction<T , S> f){
            this.throwable_function = f;
        }
        public Function<T , S> Catch(BiFunction<Exception , T , S> function){
            return t -> {
                try {
                    return throwable_function.apply(t);
                } catch (Exception e) {
                    return function.apply(e , t);
                }
            };
        }
    }
    */
    /*
    *         Stream.of("10" , "20" , "aaa" , "kkkkkkk")
              .map(Try.<String , Integer>Do(str -> Integer.parseInt(str))
                      .Catch((e , str) -> str.length())
                  )
              .forEach(s -> System.out.println(s));
    * */

}
