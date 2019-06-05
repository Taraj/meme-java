package tk.tarajki.meme.dto.in;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ActiveRequest {
    @ApiModelProperty(required = true)
    private int code;
}
