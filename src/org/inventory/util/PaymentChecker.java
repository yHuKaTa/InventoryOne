package org.inventory.util;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.paypal.orders.Payer;

import java.util.Collections;

public class PaymentChecker {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";

    public static boolean isSuccessful(String sum) {
        APIContext context = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");
            /*
       try {
            Payment payment = Payment.get(context, createPayment(sum));
            PaymentExecution execution = new PaymentExecution();
            execution.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
            payment.execute(context, execution);
            if (payment.getState().equals("approved")) {
                return true;
            }
        } catch (PayPalRESTException e) {
            System.out.printf("Response code: %d\n", e.getResponsecode());
            System.out.printf("Description: %s", e.getDetails().getMessage());
        }
             */
        return true;
    }

    private static String createPayment(String sum) {
        APIContext context = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");
        Payment payment = new Payment();
        payment.setIntent("sale");
        Amount amount = new Amount("USD", sum);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        payment.setTransactions(Collections.singletonList(transaction));
        /*
        try {
            // We need information of payer from PayPal REST API
            payment.create(context);
        } catch (PayPalRESTException e) {
            System.out.printf("Response code: %d\n", e.getResponsecode());
            System.out.printf("Description: %s", e.getDetails().getMessage());
        }
        */
        return payment.getId();
    }
}
