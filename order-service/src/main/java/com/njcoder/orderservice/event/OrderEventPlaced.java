package com.njcoder.orderservice.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class OrderEventPlaced {
    private String orderNumber;
}
