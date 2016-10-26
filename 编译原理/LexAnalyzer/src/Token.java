/**
 * Created by yyy on 2016/10/25.
 */
public class Token {
    private TokenType tokenType;
    private String tokenStr;
    private String error;

    public Token(TokenType tt, String str){
        this.tokenType = tt;
        this.tokenStr = str;
        this.error = null;
    }

    public Token(String err){
        this.error = err;
    }

    public String myToString(){
        if(this.error == null){
            return "<"+this.tokenType.toString()+","+this.tokenStr+">";
        }else{
            return "error:"+error;
        }
    }
}
