package org.ramer.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommonResponse{
    private boolean result;
    private String message;
}
