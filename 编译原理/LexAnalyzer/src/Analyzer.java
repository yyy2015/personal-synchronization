import java.util.ArrayList;

/**
 * Created by yyy on 2016/10/25.
 */
public class Analyzer {

    private static String[] reservedWords={
            "class","public","protected","private", "void","static","int","char",
            "float","double","string","if","else","do","while","try","catch",
            "switch","case","for"};//保留字数组
    private char input[] = new char[500]; //存储一行输入的字符数组
    private ArrayList<Token> tokenList = new ArrayList<Token>();//存储输出的token序列
    private char word[] = new char[20]; //存储token的str字段
    private State nowState = State.START;


}
