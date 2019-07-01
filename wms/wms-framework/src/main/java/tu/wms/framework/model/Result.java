package tu.wms.framework.model;

public class Result<T> {

    public final static int CODE_OK = 0;
    public final static int CODE_BAD_REQUEST = 400;
    public final static int CODE_CONFLICT = 409;

    private int code;
    private String errorCode;
    private String errorMsg;
    private T data;

    public Result() {

    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String errorMsg) {
        this(code);
        this.errorMsg = errorMsg;
    }

    public Result(int code, String errorMsg, String errorCode) {
        this(code, errorMsg);
        this.errorCode = errorCode;
    }

    public boolean wasOk() {
        return CODE_OK == code;
    }

    public boolean hasError() {
        return CODE_OK != code;
    }

    public boolean wasBadRequest() {
        return CODE_BAD_REQUEST == code;
    }

    public boolean wasConflict() {
        return CODE_CONFLICT == code;
    }

    public static Result<?> error(int code, String errorMsg) {
        return new Result<>(code, errorMsg);
    }

    public static Result<?> error(int code, String errorMsg, String errorCode) {
        return new Result<>(code, errorMsg, errorCode);
    }

    public static Result<?> ok() {
        return new Result<>();
    }

    public static <R> Result<R> ok(R data) {
        Result<R> result = new Result<>();
        result.data = data;
        return result;
    }

    public static Result<?> badRequest() {
        return badRequest("无效的请求参数");
    }

    public static Result<?> badRequest(String msg) {
        return new Result<>(CODE_BAD_REQUEST, msg);
    }

    public static Result<?> conflict() {
        return conflict("冲突的请求参数");
    }

    public static Result<?> conflict(String msg) {
        return new Result<>(CODE_CONFLICT, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
