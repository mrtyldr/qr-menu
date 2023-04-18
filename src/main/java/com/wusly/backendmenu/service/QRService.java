package com.wusly.backendmenu.service;

import com.amazonaws.services.s3.AmazonS3;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.wusly.backendmenu.infrastructure.aws.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRService {
    private final PhotoUploadService photoUploadService;
    private final AmazonS3 s3Client;


    public String createQrForRestaurant(UUID restaurantId) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                null;
        try {
            bitMatrix = qrCodeWriter.encode("https://menu.wusly.com/?restaurantId=%s".formatted(restaurantId),
                    BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException("Barcode creation couldn't complete!!", e);
        }
        var bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        var byteOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", byteOutputStream);
        } catch (IOException e) {
            throw new RuntimeException("Barcode creation couldn't complete!!", e);
        }
        InputStream is = new ByteArrayInputStream(byteOutputStream.toByteArray());

        var publicUrlRequest = S3Utils.getPublicUrlRequest(photoUploadService.uploadQrForRestaurant(restaurantId, is));

        return s3Client.generatePresignedUrl(publicUrlRequest).toString();
    }


}
