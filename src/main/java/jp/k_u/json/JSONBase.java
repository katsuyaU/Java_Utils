package jp.k_u.json;

import java.util.function.Function;

abstract class JSONBase implements JSON{
    protected String json;
    protected String key;



    protected JSONBase(String json , String key){
        this.json = json;
        this.key = key;

    }
    @Override
    public <T> T as(Function<JSON , T> func) throws JSONException{
        try {
            return func.apply(this);
        }catch (Exception e){
            throw new JSONException("This function can't convert.", e);
        }
    }

    @Override
    public String getKey() {
        return this.key;
    }
    @Override
    public String toString(){
    //    return (this.key.isEmpty() ? "": "key : " + this.key + " , ") + "value : " + this.json ;
        return this.json;
    }
    @Override
    public boolean isNull(){
        return false;
    }

}
