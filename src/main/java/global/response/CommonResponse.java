package global.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class CommonResponse<T> {

  private T data;
  private String message;

  public static <T> ResponseEntity<CommonResponse<T>> ok(T data, String message) {
    return ResponseEntity.ok()
        .body(CommonResponse.<T>builder()
            .data(data)
            .message(message)
            .build()
        );
  }

  public static ResponseEntity<CommonResponse<Void>> redirection(String target,
      String message) {
    return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
        .header("Location", target)
        .body(CommonResponse.<Void>builder().data(null).message(message).build());
  }
}
