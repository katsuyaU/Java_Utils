package jp.k_u.json;

import java.util.ArrayList;
import java.util.List;

class Utils {


    static List<String> splitJSON(String str)throws JSONException{
        str = str.trim();
        str = str.substring(1 , str.length() - 1);
        str = str.trim();
        int curly_brackets_count = 0; //中かっこの数 -> {}
        int square_brackets_count = 0;//大かっこの数 -> []

        StringBuilder builder = new StringBuilder();
        List<String> list = new ArrayList<>();

        boolean isMojiLiteral = false;
        boolean isEscape = false;

        for(char c : str.toCharArray()){
            COUNT : switch (c){
                case '{':
                    curly_brackets_count += 1;
                    break COUNT;
                case '}':
                    curly_brackets_count -= 1;
                    break COUNT;
                case '[':
                    square_brackets_count += 1;
                    break COUNT;
                case ']':
                    square_brackets_count -= 1;
                    break COUNT;
                default:
                    break COUNT;
            }
            isMojiLiteral = (c == '\"' && !isEscape) ? !isMojiLiteral : isMojiLiteral;
            //文字リテラルと判断した直後にもifの判定を入れる！
            if(c == ',' && curly_brackets_count == 0 && square_brackets_count == 0 && !isMojiLiteral ){
                list.add(builder.toString().trim());
                builder = new StringBuilder();
            }
            else {
                builder.append(c);
            }
            isEscape = (c == '\\' ? true : false);
        }
        //最後は , がないから、手動で追加
        //ただしbuilderが空のときは追加しない(isEmptyの判定がおかしくなる)
        String builder_toString = builder.toString().trim();
        if(!builder_toString.isEmpty()){
            list.add(builder_toString);
        }
        if( !(curly_brackets_count == 0 && square_brackets_count == 0 ) ){
            throw new JSONException("かっこが変");
        }
        return list;
    }
    static String removeStartEnd(String str){
        return str.substring(1 , str.length() - 1);
    }

}
