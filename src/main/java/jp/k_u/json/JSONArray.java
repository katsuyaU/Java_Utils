package jp.k_u.json;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class JSONArray extends JSONBase {
    static final JSONType TYPE = JSONType.ARRAY;

    private List<String> list = new ArrayList<>();
    JSONArray(String json, String key) throws JSONException {
        super(json, key);
        this.list = Utils.splitJSON(json);
    }
    JSONArray(String json) throws JSONException {
        this(json , "");
    }
    @Override
    public JSON get(int index) throws JSONException {
        return JSON.create(this.list.get(index) , String.valueOf(index));//IndexOutOfBoundsException
    }

    @Override
    public int size() {
        return this.list.size();
    }
    @Override
    public JSONType getJSONType(){
        return TYPE;
    };
    @Override
    public boolean isEmpty(){
        return this.list.isEmpty();
    }
    @Override
    public Iterator<JSON> iterator() {
        Iterator<String> iterator = this.list.iterator();
        return new Iterator<JSON>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public JSON next() {
                try {
                    return JSON.create(iterator.next());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

}