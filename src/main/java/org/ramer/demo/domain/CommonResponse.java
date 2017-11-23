package org.ramer.demo.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonResponse{
    @ApiModelProperty(notes = "The result returned by the method", required = true)
    private boolean result;
    @ApiModelProperty(notes = "The message returned by the method", required = true)
    private String message;
}
