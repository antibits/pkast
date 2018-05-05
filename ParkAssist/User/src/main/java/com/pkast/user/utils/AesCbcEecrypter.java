package com.pkast.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AesCbcEecrypter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AesCbcEecrypter.class);

    private static final String CHAR_SET_FORMAT = "utf-8";
    private static final BASE64Decoder base64Decoder = new BASE64Decoder();
    private static final BASE64Encoder base64Encoder = new BASE64Encoder();
    private Cipher cipher;

    private AesCbcEecrypter() {
        try{
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            LOGGER.error("init cipher err.", e);
        }
    }

    public static AesCbcEecrypter initAsEncrypt(String encryptKey, String iv) {
        try {
            AesCbcEecrypter instance = new AesCbcEecrypter();
            if(instance.cipher == null){
                return null;
            }
            SecretKeySpec keySpec = new SecretKeySpec(base64Decoder.decodeBuffer(encryptKey), "AES");
            IvParameterSpec ivParam = new IvParameterSpec(base64Decoder.decodeBuffer(iv));
            instance.cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParam);
            return instance;
        } catch (IOException e) {
            LOGGER.error("init param error", e);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            LOGGER.error("init cipher error", e);
        }
        return null;
    }

    public static AesCbcEecrypter initAsDecrypt(String encryptKey, String iv) {
        try {
            AesCbcEecrypter instance = new AesCbcEecrypter();
            if(instance.cipher == null){
                return null;
            }
            SecretKeySpec keySpec = new SecretKeySpec(base64Decoder.decodeBuffer(encryptKey), "AES");
            IvParameterSpec ivParam = new IvParameterSpec(base64Decoder.decodeBuffer(iv));
            instance.cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParam);
            return instance;
        } catch (IOException e) {
            LOGGER.error("init param error", e);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            LOGGER.error("init cipher error", e);
        }
        return null;
    }

    // 加密
    public String encrypt(String uncryptStr) {
        byte[] encrypted = new byte[0];
        try {
            encrypted = cipher.doFinal(uncryptStr.getBytes(CHAR_SET_FORMAT));
            //此处使用BASE64做转码。
            return base64Encoder.encode(encrypted);
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            LOGGER.error("encrypt error.", e);
        }
        return null;
    }

    // 解密
    public String decrypt(String encryptStr) {
        try {
            //先用base64解密
            byte[] encryptedBytes = base64Decoder.decodeBuffer(encryptStr);
            return new String(cipher.doFinal(encryptedBytes), CHAR_SET_FORMAT);
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
            LOGGER.error("decrypt error.", e);
        }
        return null;
    }
}
