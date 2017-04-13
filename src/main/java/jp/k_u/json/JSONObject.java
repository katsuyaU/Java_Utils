package jp.k_u.json;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class JSONObject extends JSONBase {
    static final JSONType TYPE = JSONType.OBJECT;

    private Map<String , String> map = new HashMap<>();
    JSONObject(String json, String key)throws JSONException {
        super(json, key);
        List<String> list = Utils.splitJSON(json);
        if(list.isEmpty()){
            return; 
        }
        /* 
        //手動でkeyとvalueを分ける
        //パフォーマンス上の問題でボツ
        for(String str : list){
            String[] split = str.split(":" , 2);
            this.map.put(split[0].trim() , split[1].trim());
        }
        */
        this.map = list.stream()
                       .map(str -> str.split(":" , 2))
                       .collect(Collectors.toMap(array -> Utils.removeStartEnd(array[0].trim()) , array -> array[1].trim()));
    }
    JSONObject(String json) throws JSONException {
        this(json , "");
    }
    @Override
    public JSON get(String key) throws JSONException {
        try{
            return JSON.create(this.map.get(key) , key);//NullPointerException
        }catch(NullPointerException e){
            throw new NullPointerException("Key that is \"" + key + "\" does not exist.");
        }
    }
    @Override
    public int size() {
        return this.map.size();
    }
    @Override
    public JSONType getJSONType(){
        return TYPE;
    }
    @Override
    public boolean isEmpty(){
        return this.map.isEmpty();
    }
    @Override
    public Iterator<JSON> iterator() {
        Iterator<Map.Entry<String , String>> entryIterator = this.map.entrySet().iterator();
        Iterator<JSON> iterator = new Iterator<JSON>() {
            @Override
            public boolean hasNext() {
                return entryIterator.hasNext();
            }

            @Override
            public JSON next() {
                Map.Entry<String , String> entry = entryIterator.next();
                return JSON.create(entry.getValue() , entry.getKey());
            }
        };
        return iterator;
    }
}
