package jp.k_u.json;

public class JSONException extends RuntimeException {
    public JSONException(){super();}
    public JSONException(String message){super(message);}
    public JSONException(String message , Throwable t){super(message , t);}
    public JSONException(Throwable t){super(t);}
}
