package com.sho.transaction.Service.Impl;

import com.sho.transaction.Dtos.OrderRequest;
import com.sho.transaction.Dtos.OrderResponse;
import com.sho.transaction.Entity.Order;
import com.sho.transaction.Entity.Payment;
import com.sho.transaction.Repository.OrderRepository;
import com.sho.transaction.Repository.PaymentRepository;
import com.sho.transaction.Service.OrderService;
import com.sho.transaction.exception.PaymentException;
import jakarta.transaction.Transactional;

import java.util.UUID;

public class OderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;

    public OderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = orderRequest.getOrder();
        order.setOrderTicketNumber(UUID.randomUUID().toString());
        order.setStatus("IN_PROGRESS");
        orderRepository.save(order);
        Payment payment = orderRequest.getPayment();
        if (!payment.getType().equals("DEBIT")){
            throw new PaymentException("Payment type is not supported");
        }

        payment.setOrderId(order.getId());
        paymentRepository.save(payment);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderTackingNumber(order.getOrderTicketNumber());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setMessage("SUCCESS");
        return orderResponse;

    }
}
