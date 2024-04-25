/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import com.tuantran.IMPROOK_CARE.configs.vnpay.VNPAYConfig;
import com.tuantran.IMPROOK_CARE.dto.VNPAYDTO;
import com.tuantran.IMPROOK_CARE.dto.VnpayReturnDTO;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiVNPAYController {

    @Autowired
    private VNPAYConfig vnpayConfig;

    @PostMapping("/public/pay/")
    @CrossOrigin
    public String getPay(@Valid @RequestBody VNPAYDTO vnpayDTO) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Long.parseLong(vnpayDTO.getAmount()) * 100;
        String bankCode = "NCB";

        String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
        String vnp_IpAddr = "192.168.28.8";

        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnpayDTO.getOrderInfor() + "-" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPAYConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    @PostMapping("/public/pay-return/")
    @CrossOrigin
    public String getPay_return(@Valid @RequestBody VnpayReturnDTO vnpayReturnDTO) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Long.parseLong(vnpayReturnDTO.getAmount()) * 100;
        String bankCode = "NCB";

        String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
        String vnp_IpAddr = "192.168.28.8";

        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnpayReturnDTO.getOrderInfor() + "-" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayReturnDTO.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    @GetMapping("/public/processReturnVNPAY/")
    @CrossOrigin
    public ResponseEntity<?> processReturnVNPAY(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            try {
                String fieldName = URLEncoder.encode((String) params.nextElement(),
                        StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName),
                        StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ApiVNPAYController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = this.vnpayConfig.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PostMapping("/public/refund/")
    @CrossOrigin
    public String refund() throws IOException {

        // Command: refund
        String vnp_RequestId = VNPAYConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "refund";
        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
        String vnp_TransactionType = "02";
        String vnp_TxnRef = "65245271";
        long amount = Integer.parseInt("10000") * 100;
        String vnp_Amount = String.valueOf(amount);
        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
        String vnp_TransactionNo = "14392274"; // Assuming value of the parameter "vnp_TransactionNo" does not exist on
                                               // your
        // system.
        String vnp_TransactionDate = "20240425141945";
        String vnp_CreateBy = "admin";

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_IpAddr = "192.168.28.8";

        JsonObject vnp_Params = new JsonObject();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);

        if (vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty()) {
            vnp_Params.addProperty("vnp_TransactionNo", vnp_TransactionNo);
        }

        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
        vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data = String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
                vnp_TransactionType, vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_TransactionDate,
                vnp_CreateBy, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);

        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(VNPAYConfig.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());

        return response.toString();
    }
    // @PostMapping("/public/refund/")
    // @CrossOrigin
    // public String refund() throws IOException {

    // // Command: refund
    // String vnp_RequestId = VNPAYConfig.getRandomNumber(8);
    // String vnp_Version = "2.1.0";
    // String vnp_Command = "refund";
    // String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
    // String vnp_TransactionType = "03"; // Hoàn tiền toàn phần
    // String vnp_TxnRef = "45242740";
    // long amount = Integer.parseInt("10000") * 100;
    // String vnp_Amount = String.valueOf(amount);
    // String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
    // String vnp_TransactionNo = ""; // Assuming value of the parameter
    // "vnp_TransactionNo" does not exist on your
    // // system.
    // String vnp_TransactionDate = "20240423093713";
    // String vnp_CreateBy = "tamthy12345@gmail.com";

    // Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    // SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    // String vnp_CreateDate = formatter.format(cld.getTime());

    // String vnp_IpAddr = "192.168.28.8";

    // JsonObject vnp_Params = new JsonObject();

    // vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
    // vnp_Params.addProperty("vnp_Version", vnp_Version);
    // vnp_Params.addProperty("vnp_Command", vnp_Command);
    // vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
    // vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
    // vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
    // vnp_Params.addProperty("vnp_Amount", vnp_Amount);
    // vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);

    // if (vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty()) {
    // vnp_Params.addProperty("vnp_TransactionNo", "{get value of
    // vnp_TransactionNo}");
    // }

    // vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
    // vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
    // vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
    // vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

    // String hash_Data = String.join("|", vnp_RequestId, vnp_Version, vnp_Command,
    // vnp_TmnCode,
    // vnp_TransactionType, vnp_TxnRef, vnp_Amount, vnp_TransactionNo,
    // vnp_TransactionDate,
    // vnp_CreateBy, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);

    // System.out.println(hash_Data);
    // String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey,
    // hash_Data.toString());

    // vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

    // URL url = new URL(VNPAYConfig.vnp_ApiUrl);
    // HttpURLConnection con = (HttpURLConnection) url.openConnection();
    // con.setRequestMethod("POST");
    // con.setRequestProperty("Content-Type", "application/json");
    // con.setDoOutput(true);
    // DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    // wr.writeBytes(vnp_Params.toString());
    // wr.flush();
    // wr.close();
    // int responseCode = con.getResponseCode();
    // System.out.println("nSending 'POST' request to URL : " + url);
    // System.out.println("Post Data : " + vnp_Params);
    // System.out.println("Response Code : " + responseCode);
    // BufferedReader in = new BufferedReader(
    // new InputStreamReader(con.getInputStream()));
    // String output;
    // StringBuffer response = new StringBuffer();
    // while ((output = in.readLine()) != null) {
    // response.append(output);
    // }
    // in.close();
    // System.out.println(response.toString());

    // return response.toString();
    // }

    @PostMapping("/public/querydr/")
    @CrossOrigin
    public String queryVNP(@RequestBody Map<String, String> params) throws IOException {

        String vnp_RequestId = VNPAYConfig.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "querydr";
        String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_OrderInfo = "Kiem tra ket qua GD OrderId:" + vnp_TxnRef;
        // String vnp_TransactionNo = req.getParameter("transactionNo");
        String vnp_TransDate = params.get("vnp_TransactionDate");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_IpAddr = "192.168.28.8";

        JsonObject vnp_Params = new JsonObject();

        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        // vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        String hash_Data = String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef,
                vnp_TransDate, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.secretKey, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        URL url = new URL(VNPAYConfig.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }

}
