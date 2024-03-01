package com.twilio.example.controller;

import com.twilio.Twilio;
import com.twilio.example.payload.SMSRequest;
import com.twilio.rest.api.v2010.account.Message;


import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/sms")
public class SMSController {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;


    @PostMapping("/send")
    public ResponseEntity<String> sendSMS(@RequestBody SMSRequest smsRequest) {
        try{
            Twilio.init(twilioAccountSid,twilioAuthToken);
            Message message=Message.creator(
                    new PhoneNumber(smsRequest.getTo()),
                    new PhoneNumber(twilioPhoneNumber),
                    smsRequest.getMessage())
                    .create();
            return ResponseEntity.ok("SMS sent sucessfully "+message.getSid());


        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send sms"+e.getMessage());

        }

    }
}
