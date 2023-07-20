package site.nomoreparties.stellarburgers.api.authregister.models;

public class RegisterResponse {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private RegisterRequest user;

    public RegisterResponse() {
    }

    public RegisterResponse(boolean success, String accessToken, String refreshToken, RegisterRequest user) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RegisterRequest getUser() {
        return user;
    }

    public void setUser(RegisterRequest user) {
        this.user = user;
    }
}
