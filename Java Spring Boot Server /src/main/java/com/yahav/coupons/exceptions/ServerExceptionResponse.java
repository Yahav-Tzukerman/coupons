package com.yahav.coupons.exceptions;

public class ServerExceptionResponse {

    private int status;
    private String message;
    private String timeStamp;

    public ServerExceptionResponse() {
    }

    public ServerExceptionResponse(int status, String message, String timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "ServerExceptionResponse{" +
                "status=" + status +
                ", message='" + message +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
