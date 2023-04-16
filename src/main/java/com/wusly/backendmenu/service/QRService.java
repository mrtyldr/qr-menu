package com.wusly.backendmenu.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.UUID;

@Service
public class QRService {

    public BufferedImage createQrForRestaurant(UUID restaurantId){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                null;
        try {
            bitMatrix = qrCodeWriter.encode("https://menu.wusly.com/?restaurantId=%s".formatted(restaurantId),
                    BarcodeFormat.QR_CODE,200,200);
        } catch (WriterException e) {
            throw new RuntimeException("Barcode creation couldn't complete!!",e);
        }
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }



}
