package com.sho.transaction.Service;

import com.sho.transaction.Dtos.OrderRequest;
import com.sho.transaction.Dtos.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
