public class CustomException extends Exception{
    public CustomException() {

    }
    public CustomException(String message) {
        super("自定义异常"+message);
    }
}
