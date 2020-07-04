package com.demo.dive.cube.config;

public class Queries {

    /**
     * Order Queries
     */
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


    /**
     * Student Queries
     */

    public static String studentDetailQuery = "Select a.first_name,a.middle_name,a.last_name,a.email,a.permanent_address,a.phone_number,a.emergency_contact_no,a.country , a.zip_code ,a.date_of_birth , b.course_name ,c.course_date ,c.course_time from course_student a \n" +
            "inner join book_course c \n" +
            "on a.id = c.student_id \n" +
            "inner join course b \n" +
            "on b.id = c.course_id \n" +
            "where a.is_deleted = 0 \n" +
            "and b.is_deleted = 0 \n" +
            "and c.is_deleted = 0 \n" +
            "criteria"+
            "order by a.first_name";

    public static String oldStudentDetailQuery = "Select a.first_name,a.middle_name,a.last_name,a.email,a.permanent_address,a.phone_number,a.emergency_contact_no,a.country , a.zip_code ,a.date_of_birth , b.course_name ,c.course_completion_date,d.date_of_processing from course_student a \n" +
            "inner join book_course c \n" +
            "on a.id = c.student_id \n" +
            "inner join course b \n" +
            "on b.id = c.course_id \n" +
            "inner join process_certificate d \n"+
            "on c.id = d.book_course_id \n"+
            "where a.is_deleted = 0 \n" +
            "and b.is_deleted = 0 \n" +
            "and c.is_deleted = 0 \n" +
            "and c.is_completed = 1 \n"+
            "criteria"+
            "order by a.first_name";

    /*Sales*/
    public static String salesQuery = "Select a.name,b.order_date,d.item_id,\n" +
            "d.item_name,c.amount,c.quantity,c.amount*c.quantity total from supplier a\n" +
            "inner join supplier_order b \n" +
            "on a.id = b.supplier_id \n" +
            "inner join order_detail c \n" +
            "on b.id = c.order_detail \n" +
            "inner join item d \n" +
            "on c.item_id = d.id \n" +
            "where a.is_deleted = 0 \n" +
            "and b.is_deleted = 0 \n" +
            "and c.is_deleted = 0 \n" +
            "and d.is_deleted = 0";
}
