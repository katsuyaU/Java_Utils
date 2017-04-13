package jp.k_u.json;

import java.util.Iterator;
import java.util.NoSuchElementException;

class JSONValue extends JSONBase {
    static final JSONType TYPE = JSONType.VALUE;

    private String value;
    private ValueType valueType;
    JSONValue(String json, String key)throws JSONException {
        super(json, key);
        //value の Type が何か確認
        if(json.startsWith("\"") && json.endsWith("\"")){
            this.value = Utils.removeStartEnd(json);
            this.valueType = ValueType.STRING;
        } else {
            switch (json){
                case "true":
                case "false":
                    this.value = json;
                    this.valueType = ValueType.BOOL;
                    break;
                case "null":
                    this.value = json;
                    this.valueType = ValueType.NULL;
                    break;
                default:
                    this.value = json;
                    this.valueType = ValueType.NUMBER;
                    break;
            }
        }
    }
    JSONValue(String json)throws JSONException{
        this(json , "");
    }
    @Override
    public long toLong() throws JSONException {
        switch (this.valueType){
            case NUMBER:
                return (long)Long.parseLong(this.value);
            case NULL:
                return 0;
            default:
                throw new NumberFormatException(valueType + "の" + value + "をlongに変換できません");
        }
    }
    @Override
    public int toInt() throws JSONException {
        switch (this.valueType){
            case NUMBER:
                return (int)Double.parseDouble(this.value);
            case NULL:
                return 0;
            default:
                throw new NumberFormatException(valueType + "の" + value + "をintに変換できません");
        }
    }
    @Override
    public double toDouble() throws JSONException {
        switch (this.valueType){
            case NUMBER:
                return Double.parseDouble(this.value);
            case NULL:
                return 0;
            default:
                throw new NumberFormatException(valueType + "の" + value + "をdoubleに変換できません");
        }

    }
    @Override
    public boolean toBoolean() throws JSONException {
        switch (this.valueType){
            case BOOL:
                return Boolean.parseBoolean(this.value);
            default:
                return false;
        }
    }
    @Override
    public String toString(){
        return this.value;
    }


    @Override
    public int size() {
        return 1;
    }
    @Override
    public JSONType getJSONType(){
        return TYPE;
    };
    @Override
    public boolean isEmpty(){
        return this.valueType == ValueType.NULL || this.value.isEmpty();
    }
    @Override
    public boolean isNull(){
        return this.valueType == ValueType.NULL;
    }
    @Override
    public Iterator<JSON> iterator() {
        return new Iterator<JSON>() {
            private int count = 1;
            @Override
            public boolean hasNext() {
                return count > 0;
            }

            @Override
            public JSON next() {
                if(count > 0){
                    count -= 1;
                    return JSONValue.this;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
    static enum ValueType{
        STRING , NUMBER , BOOL , NULL;
    }
}
