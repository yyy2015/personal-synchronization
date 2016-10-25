import java.util.ArrayList;

/**
 * Created by yyy on 2016/10/25.
 */
public class Analyzer {

    private String[] reservedWords={
            "class","public","protected","private", "void","static","int","char",
            "float","double","string","if","else","do","while","try","catch",
            "switch","case","for"};//保留字数组
    ArrayList<Token> tokenList = new ArrayList<Token>();//存储输出的token序列

    public ArrayList<Token> scanner(char[] input){
        int p = 0;//指针，指向被读取的字符
        char ch;//当前读取字符
        char[] word = new char[20];//存储token的str字段
        TokenType type;//存储token的类型字段
        String error;
        State nowState = State.START; //当前状态
        int sp = 0;//指针，指向被读取字符将要在word中存储的位置

        while(true){
            ch = input[p];
            word[sp] = ch;
            word[++sp] = '\0';
            switch(nowState){
                case START:
                    if(ch=='+'){
                        type = TokenType.PLUS;
                        nowState = State.DONE;
                    }else if(ch=='-'){
                        type = TokenType.MINUS;
                        nowState = State.DONE;
                    }else if(ch=='*'){
                        type = TokenType.TIMES;
                        nowState = State.DONE;
                    }else if(ch=='>'){
                        type = TokenType.GT;
                        nowState = State.DONE;
                    }else if(ch=='<'){
                        type = TokenType.LT;
                        nowState = State.DONE;
                    }else if(ch=='='){
                        type = TokenType.ASSIGN;
                        nowState = State.DONE;
                    }else if(ch=='('){
                        type = TokenType.LBRACKET;
                        nowState = State.DONE;
                    }else if(ch==')'){
                        type = TokenType.RBRACKET;
                        nowState = State.DONE;
                    }else if(ch=='{'){
                        type = TokenType.LBRACE;
                        nowState = State.DONE;
                    }else if(ch=='}'){
                        type = TokenType.RBRACE;
                        nowState = State.DONE;
                    }else if(ch=='['){
                        type = TokenType.LSBRACKET;
                        nowState = State.DONE;
                    }else if(ch==']'){
                        type = TokenType.RSBRACKET;
                        nowState = State.DONE;
                    }else if(ch==','){
                        type = TokenType.COMMA;
                        nowState = State.DONE;
                    }else if(ch==';'){
                        type = TokenType.SEMICOLON;
                        nowState = State.DONE;
                    }else if(ch=='/'){
                        nowState = State.IS_DIVIDE;
                        p++;
                    }else if(isDigit(ch)){
                        nowState = State.ISDIGIT;
                        p++;
                    }else if(isAlpha(ch)){
                        nowState = State.ISID;
                        p++;
                    }else if(isSpace(ch)){
                        word[--sp]='\0';
                        p++;
                    }else{
                        word[--sp]='\0';
                        error = "unknown str";
                        p++;
                    }
                case IS_DIVIDE:
                    if(ch=='/'){
                        type = TokenType.NOTE;
                        nowState = State.ISNOTE;
                    }else{
                        type = TokenType.DIVIDE;
                        word[--sp]='\0';
                        nowState = State.DONE;
                    }

                case ISDIGIT:
                    if(isDigit(ch)){
                        nowState = State.ISDIGIT;
                        p++;
                    }else{
                        type = TokenType.NUM;
                        word[--sp]='\0';
                        nowState = State.DONE;
                    }

                case ISID:

            }


        }


    }

    //将char数组转换为字符串
    private String char2str(char[] ch){
        String s = String.valueOf(ch);
        return s;
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


}
