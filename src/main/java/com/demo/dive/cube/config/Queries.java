package com.demo.dive.cube.config;

public class Queries {
    public static String orderDetailQuery = "Select a.company,a.name,b.order_date,b.tracking_num,d.item_id,d.item_name,c.amount,c.quantity,c.amount*c.quantity total from supplier a \n" +
            "inner join supplier_order b \n" +
            "on a.id = b.supplier_id \n" +
            "inner join order_detail c \n" +
            "on b.id = c.order_detail \n" +
            "inner join item d \n" +
            "on c.item_id = d.id \n" +
            "where a.is_deleted = 0 \n" +
            "and b.is_deleted = 0 \n" +
            "and c.is_deleted = 0 \n" +
            "and d.is_deleted = 0 \n";

    public static String orderSummary = "Select a.company,count(*) totalOrders,sum(b.amount) totalAmount from supplier a\n" +
            "inner join supplier_order b \n" +
            "on a.id = b.supplier_id \n" +
            "where a.is_deleted = 0 \n" +
            "and b.is_deleted = 0 \n" +
            "criteria \n" +
            "group by a.company ";
}
