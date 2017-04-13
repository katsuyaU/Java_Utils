package jp.k_u.json;


import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        JSON json = JSON.create(Paths.get("./input/read.json"));
        // int count = json.get("resultCount").toInt();
        // System.out.println(count);
        // System.out.println(json);

        List<CD> list = json.get("results").as(fromJSON());
        list.forEach(CD::print);

        System.out.println(json);

        json.get("resultCount").stream().mapToInt(j -> j.toInt()).forEach(System.out::println);
        /*
        str = str.trim();
        System.out.println(str.startsWith("[") && str.endsWith("]"));


        splitJSON(str).forEach(System.out::println);
        System.out.println("object ->");
        splitJSON(str)
                .stream()
                .flatMap(s -> splitJSON(s).stream())
                .forEach(System.out::println);

        sub(str , "");
        */

    }
    public static Function<JSON , List<CD> > fromJSON(){
        return json -> json.stream()
                           .map(j -> j.as(CD.fromJSON()))
                           .collect(Collectors.toList());


    }
    public static class CD{
        private String artistName;
        private String collectionName;
        private int price;
        public static Function<JSON , CD> fromJSON(){
            return json -> {
                CD cd = new CD();
                cd.artistName = json.get("artistName").toString();
                cd.collectionName = json.get("collectionName").toString();
                cd.price = json.get("collectionPrice").toInt();

                return cd;
            };
        }
        public void print(){
            System.out.println("アーティスト : " + artistName);
            System.out.println("アルバム名 : " + collectionName);
            System.out.println("価格 : " + price + "円");
            System.out.println("---------------------------------");
        }
    }
    public static void sub(String str , String space){
        str = str.trim();
        if(str.startsWith("{") && str.endsWith("}")){
            //JSONObject を生成
            System.out.println(space + "Type:Object -> ");
            System.out.println();
            splitJSON(str)
                    .stream()
                    .map(s -> s.split(":"))
                    .forEach(array -> {
                        System.out.println(space + array[0]);//Key
                        String s = Arrays.stream(array).skip(1).collect(Collectors.joining(":"));//Value
                        sub(s , space + "    ");
                    });

            System.out.println();

        } else if(str.startsWith("[") && str.endsWith("]")){
            //JSONArray を生成
            System.out.println(space + "Type:Array -> ");
            splitJSON(str)
                    .forEach(s -> sub(s , space + "    "));
            System.out.println();

        }else{
            //JSONValue を生成
            System.out.println(space + "Type:Value -> ");
            System.out.println(space + str);
            System.out.println();
        }
    }
    public static List<String> splitJSON(String str){
        str = str.trim();
        str = str.substring(1 , str.length() - 1);
        int curly_brackets_count = 0; //中かっこの数 -> {}
        int square_brackets_count = 0;//大かっこの数 -> []

        StringBuilder builder = new StringBuilder();
        List<String> list = new ArrayList<>();

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
            if(c == ',' && curly_brackets_count == 0 && square_brackets_count == 0 ){
                list.add(builder.toString().trim());
                builder = new StringBuilder();
            }else {
                builder.append(c);
            }
        }
        //最後は , がないから、手動で追加
        list.add(builder.toString().trim());
        if( !(curly_brackets_count == 0 && square_brackets_count == 0 ) ){
            System.out.println("かっこが変");
        }
        return list;
    }
}
