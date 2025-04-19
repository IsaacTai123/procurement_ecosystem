package common;

import java.util.function.Consumer;

/**
 * @author tisaac
 */
public class Result<T> {
    private boolean success;
    private String message;
    private T data;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public Result<T> onFailure(Consumer<String> errorHandler) {
        if (!success) {
            errorHandler.accept(message);
        }
        return this;
    }

    public Result<T> onSuccess(Consumer<String> successHandler) {
        if (success) {
            successHandler.accept(message);
        }
        return this;
    }
}
