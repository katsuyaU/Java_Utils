package jp.k_u.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface JSON extends Iterable<JSON> {
    public int size();
    public default JSON get(int index) throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではget(int)には対応してません");
    }
    public default JSON get(String key) throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではget(String)には対応してません");
    }
    public default long toLong() throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではtoLong()には対応してません");
    }
    public default int toInt() throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではtoInt()には対応してません");
    }
    public default double toDouble() throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではtoDouble()には対応してません");
    }
    public default boolean toBoolean() throws JSONException {
        throw new JSONException("このクラス(" + this.getClass().getName() + ")ではtoBoolean()には対応してません");
    }
    public <T> T as(Function<JSON, T> func)throws JSONException;
    public String getKey();
    public JSONType getJSONType();
    public default boolean isArray(){return this.getJSONType() == JSONType.ARRAY;};
    public default boolean isObject(){return this.getJSONType() == JSONType.OBJECT;};
    public default boolean isValue(){return this.getJSONType() == JSONType.VALUE;};
    public boolean isEmpty();
    public boolean isNull();


    @Override
    public default Spliterator<JSON> spliterator(){
        return Spliterators.spliterator(this.iterator() , this.size() , 0);
    }
    public default Stream<JSON> stream(){
        return StreamSupport.stream(this.spliterator() , false);
    }

    static JSON create(String json, String key) throws JSONException {
        json = json.trim();
        if(json.startsWith("{") && json.endsWith("}")){
            //JSONObject を生成
            return new JSONObject(json , key);

        } else if(json.startsWith("[") && json.endsWith("]")){
            //JSONArray を生成
            return new JSONArray(json , key);
        }else{
            //JSONValue
            return new JSONValue(json , key);
        }
    }
    public static JSON create(String json) throws JSONException {
        return create(json , "");
    }
    public static JSON create(Path path) throws JSONException , IOException{
        String str = String.join("" , Files.readAllLines(path));
        //無駄な空白を削除するアクション
        boolean isMojiLiteral = false;
        boolean isEscape = false;
        StringBuilder builder = new StringBuilder();
        for(char c : str.toCharArray()){
            isMojiLiteral = (c == '\"' && !isEscape) ? !isMojiLiteral : isMojiLiteral;
            if(c != ' ' || isMojiLiteral){
                builder.append(c);
            }
            isEscape = (c == '\\') ? true : false;
        }
        //end
        return create(builder.toString() , "");
    }

}
