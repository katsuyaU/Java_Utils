package jp.k_u.util;

import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.BiConsumer;



public class Tuple<A , B> {
    public static <A , B , T> Function<Tuple<A , B> , T> function(BiFunction<A , B , T> f){
        return tuple -> f.apply(tuple.getA() , tuple.getB());
    }
    public static <A , B> Consumer<Tuple<A , B>> consumer(BiConsumer<A , B> f){
        return tuple -> f.accept(tuple.getA() , tuple.getB());
    }


    private A a;
    private B b;

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
    private Tuple(A a , B b){
        this.a = a;
        this.b = b;
    }
    public static <A , B> Tuple<A , B> of(A a , B b){
        return new Tuple<A, B>(a,b);
    }
    @Override
    public String toString(){
        return "Tuple[ " + a.toString() + " , " + b.toString() + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple){
            Tuple<? , ?> tuple = (Tuple)obj;
            return this.getA().equals(tuple.getA()) && this.getB().equals(tuple.getB());
        }else{
            return false;
        }
    }
}
