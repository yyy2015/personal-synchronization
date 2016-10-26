import java.io.*;
import java.util.ArrayList;

/**
 * Created by yyy on 2016/10/25.
 */
public class Analyzer {

    private String[] reservedWords={
            "class","public","protected","private", "void","static","int","char",
            "float","double","string","if","else","do","while","try","catch",
            "switch","case","for"};//保留字数组

    private ArrayList<Token> scanner(char[] input){
        ArrayList<Token> tokenList = new ArrayList<Token>();//存储输出的token序列
        int p = 0;//指针，指向被读取的字符
        char ch= 'a';//当前读取字符
        Token toke;
        char[] word = new char[20];//存储token的str字段
        TokenType type = null;//存储token的类型字段
        String error;
        State nowState = State.START; //当前状态
        int sp = 0;//指针，指向被读取字符将要在word中存储的位置

//        System.out.println("begin!");
        while(true){
            if(input.length==0){
                return tokenList;
            }
            ch = input[p];
            switch(nowState){
                case START:
                    if(ch=='+'){
                        type = TokenType.PLUS;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='-'){
                        type = TokenType.MINUS;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='*'){
                        type = TokenType.TIMES;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='>'){
                        type = TokenType.GT;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='<'){
                        type = TokenType.LT;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='='){
                        type = TokenType.ASSIGN;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='('){
                        type = TokenType.LBRACKET;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch==')'){
                        type = TokenType.RBRACKET;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='{'){
                        type = TokenType.LBRACE;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='}'){
                        type = TokenType.RBRACE;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='['){
                        type = TokenType.LSBRACKET;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch==']'){
                        type = TokenType.RSBRACKET;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch==','){
                        type = TokenType.COMMA;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch==';'){
                        type = TokenType.SEMICOLON;
                        nowState = State.DONE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(ch=='/'){
                        nowState = State.IS_DIVIDE;
                        p++;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(isDigit(ch)){
                        nowState = State.ISDIGIT;
                        p++;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(isAlpha(ch)){
                        nowState = State.ISID;
                        p++;
                        word[sp] = ch;
                        word[++sp] = '\0';
                    }else if(isSpace(ch)){
                        p++;
                    }else{
                        p++;
                        error = "unknown str";
                    }
                    break;
                case IS_DIVIDE:
                    if(ch=='/'){
                        type = TokenType.NOTE;
                        nowState = State.ISNOTE;
                        word[sp] = ch;
                        word[++sp] = '\0';
                        sp++;
                    }else{
                        type = TokenType.DIVIDE;
                        nowState = State.DONE;
                        p--;
                    }
                    break;

                case ISDIGIT:
                    if(isDigit(ch)){
                        nowState = State.ISDIGIT;
                        word[sp] = ch;
                        word[++sp] = '\0';
                        p++;
                    }else{
                        type = TokenType.NUM;
                        nowState = State.DONE;
                        p--;
                    }
                    break;

                case ISID:
                    if((!isDigit(ch))&&(!isAlpha(ch))){
                        type = TokenType.ID;
                        p--; //退回多读的字符

                        //检查是否为保留字
                        for(int i=0;i<reservedWords.length;i++){
                            if(char2str(word).substring(0,sp).equals(reservedWords[i])){
                                type = TokenType.RESERVED;
                                break;
                            }
                        }
                        nowState = State.DONE;
                    }else{
                        nowState = State.ISID;
                        word[sp] = ch;
                        word[++sp] = '\0';
                        p++;
                    }
                    break;
                case ISNOTE:
                    toke = new Token(type,char2str(word).substring(0,sp));
                    tokenList.add(toke);
                    return tokenList;
                case DONE:
                    toke = new Token(type,char2str(word).substring(0,sp));
                    tokenList.add(toke);
                    sp = 0; //将word归零
                    p++;//指针前进一个字符
                    nowState = State.START;
                    if(p == input.length){
                        return tokenList;
                    }
            }


        }


    }


    //将char数组转换为字符串
    private String char2str(char[] ch){
        String s = String.valueOf(ch);
        return s;
    }

    private boolean myEqual(char[] ch, String s){
        char[] s_ch = s.toCharArray();
        if(ch.length!=s_ch.length){
            System.out.println(ch.length+" "+s_ch.length);
            return false;
        }else{
            for(int i=0;i<s_ch.length;i++){
                if(ch[i]!=s_ch[i]){
                    System.out.println(ch[i]+" "+s_ch[i]);
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isSpace(char c){
        if((c==' ')||(c=='\t')||(c=='\n')){
            return true;
        }else{
            return false;
        }
    }

    //判断当前字符是否为数字
    private boolean isDigit(char c){
        if(c>='0' && c<='9'){
            return true;
        }else{
            return false;
        }
    }

    //判断当前字符是否为字母
    private boolean isAlpha(char c){
        if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
            return true;
        }else{
            return false;
        }
    }

    public void input2token(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(fileName))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("file not found!");
        }
        String lineStr = null;
        char[] line = null;
        int lineNum = 0;
        ArrayList<Token> tokenList = new ArrayList<Token>();
        try {
            while((lineStr = br.readLine())!=null){
                lineNum++;
                line = lineStr.toCharArray();
                tokenList = scanner(line);
                for(int i=0;i<tokenList.size();i++){
                    System.out.println(lineNum+" "+tokenList.get(i).myToString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("read error!");
        }
    }
    public static void main(String args[]){
        String fileName = "input.txt";
        Analyzer analyzer = new Analyzer();
        analyzer.input2token(fileName);
    }


}
