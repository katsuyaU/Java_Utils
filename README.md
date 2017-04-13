# Java_Utils

## 概要
- 自作のjava用JSONparserライブラリと、java用Utilクラス
## 例
### JSON
- sample.json
```json
{
    "key1" : "a" , 
    "key2" : 100 , 
    "key3" : true ,
    "key4" : [1 , 2 , 3 , 4] , 
    "key5" : {
        "name" : "hogehoge" , 
        "number" : 1100
    } 
}
```
- Main.java
```java
//import jp.k_u.json.JSON;
public static void main(String... args) throws Exception {
    JSON json = JSON.create(Paths.get("sample.json"));
    System.out.println(json);
    System.out.println(json.get("key1").toString());
    if(json.get("key3").toBoolean()){
        System.out.println("yes!!");
    }
    System.out.println(json.get("key4").get(2).toInt());
    json.get("key4").stream()
    .mapToInt(j -> j.toInt())
    .forEach(System.out::println);
    Sample s = json.get("key5").as(Sample::new);
    System.out.println(s);
}
static class Sample{
    private String name;
    private int number;
    public Sample(JSON json){
        this.name = json.get("name").toString();
        this.number = json.get("number").toInt();
    }
    @Override
    public String toString(){
        return MessageFormat.format("name:{0} , number:{1}" , this.name , this.number);
    }
}
```
- 実行結果
```
> java Main
{"key1":"a","key2":100,"key3":true,"key4":[1,2,3,4],"key5":{"name":"hogehoge","number":1100}}
a
yes!!
3
1
2
3
4
name:hogehoge , number:1,100
```
### Utils
### Try
- 検査例外がthrowされるメソッドをラムダ式を用いて非検査例外にしたり、tryを用いずに簡潔に記述するためのクラス
```java
//String a = Files.lines(Paths.get("sample.json")).collect(Collectors.joining("\n"));
//IOExceptionがthrowされるためコンパイルエラー！
String a = Try.apply( () -> Files.lines(Paths.get("sample.json")).collect(Collectors.joining("\n")));
//このように記述できる
```
### Tuple
- 関数型言語の多くに用いられるタプル型を使用するためのクラス
- ただし本来のタプル型は複数の要素を入れることができるが、このクラスでは2つの要素のみ入れることができる

```java
Tuple<String , Integer> t = Tuple.of("aaa" , 100);
```
```java
IntStream.rangeClosed(100 , 110)
//100~110の3で割った数と余りを求める
.mapToObj(i -> {
    int div3 = i / 3;
    int mod3 = i % 3;
    return Tuple.of(div3 , mod3);
})
.forEach(Tuple.consumer((div3 , mod3) -> System.out.println(div3 + "余り" + mod3)));
/*
33余り1
33余り2
34余り0
34余り1
34余り2
35余り0
35余り1
35余り2
36余り0
36余り1
36余り2
*/
```
