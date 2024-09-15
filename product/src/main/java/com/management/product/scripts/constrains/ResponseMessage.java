package com.management.product.scripts.constrains;

import org.springframework.stereotype.Component;

@Component
public class ResponseMessage {
    public  static final String StatusNotFound = "Data not found, Please check again";
    public  static final String StatusFounded = "Data is founded";
    public static final String StoreSuccess = "Store is Accepted";
    public static final String StoreDenied = "Store is Denied, Please check and send again";

    public static final String UpdateSuccess = "Update is Accepted";
    public static final String UpdateDenied = "Update is Denied, Please check and send again";
    public static final String DeleteSuccess = "Delete is Accepted";
    public static final String DeleteDenied = "Delete is Denied, Please check and send again";
}
