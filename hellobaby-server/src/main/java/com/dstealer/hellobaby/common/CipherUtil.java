/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dstealer.hellobaby.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 加解密工具
 */
public class CipherUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CipherUtil.class);
    private static final String DEFAULT_PUBLIC_KEY_STRING = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKHGwq7q2RmwuRgKxBypQHw0mYu4BQZ3eMsTrdK8E6igRcxsobUC7uT0SoxIjl1WveWniCASejoQtn/BY6hVKWsCAwEAAQ==";
    private static final String DEFAULT_PRIVATE_KEY_STRING = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAocbCrurZGbC5GArEHKlAfDSZi7gFBnd4yxOt0rwTqKBFzGyhtQLu5PRKjEiOXVa95aeIIBJ6OhC2f8FjqFUpawIDAQABAkAPejKaBYHrwUqUEEOe8lpnB6lBAsQIUFnQI/vXU4MV+MhIzW0BLVZCiarIQqUXeOhThVWXKFt8GxCykrrUsQ6BAiEA4vMVxEHBovz1di3aozzFvSMdsjTcYRRo82hS5Ru2/OECIQC2fAPoXixVTVY7bNMeuxCP4954ZkXp7fEPDINCjcQDywIgcc8XLkkPcs3Jxk7uYofaXaPbg39wuJpEmzPIxi3k0OECIGubmdpOnin3HuCP/bbjbJLNNoUdGiEmFL5hDI4UdwAdAiEAtcAwbm08bKN7pwwvyqaCBC//VnEWaq39DCzxr+Z2EIk=";

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            String plainText = args[0].trim();
            System.out.printf("明文是:[%1$s]\n",plainText);
            String cipherText = encrypt(plainText);
            assert plainText.equals(decrypt(cipherText));
            System.out.printf("密文是:[%1$s]\n",cipherText);
            System.out.println("请妥善保管密文防止丢失!");
        }
    }

    /**
     * 使用默认私钥加密明文
     *
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(String cipherText) throws Exception {
        return decrypt((String) null, cipherText);
    }

    /**
     * 使用公钥解密密文
     *
     * @param publicKeyText
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(String publicKeyText, String cipherText)
            throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyText);
        return decrypt(publicKey, cipherText);
    }

    /**
     * 从证书中获取公钥
     *
     * @param x509File
     * @return
     */
    public static PublicKey getPublicKeyByX509(String x509File) {
        if (x509File == null || x509File.length() == 0) {
            return CipherUtil.getPublicKey(null);
        }

        FileInputStream in = null;
        try {
            in = new FileInputStream(x509File);

            CertificateFactory factory = CertificateFactory
                    .getInstance("X.509");
            Certificate cer = factory.generateCertificate(in);
            return cer.getPublicKey();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key", e);
        } finally {
            close(in);
        }
    }

    /**
     * 从字符串中获取公钥
     *
     * @param publicKeyText
     * @return
     */
    public static PublicKey getPublicKey(String publicKeyText) {
        if (publicKeyText == null || publicKeyText.length() == 0) {
            publicKeyText = CipherUtil.DEFAULT_PUBLIC_KEY_STRING;
        }

        try {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyText);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
                    publicKeyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "SunRsaSign");
            return keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key", e);
        }
    }

    /**
     * 从文件中读取公钥
     *
     * @param publicKeyFile
     * @return
     */
    public static PublicKey getPublicKeyByPublicKeyFile(String publicKeyFile) {
        if (publicKeyFile == null || publicKeyFile.length() == 0) {
            return CipherUtil.getPublicKey(null);
        }

        FileInputStream in = null;
        try {
            in = new FileInputStream(publicKeyFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = 0;
            byte[] b = new byte[512 / 8];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }

            byte[] publicKeyBytes = out.toByteArray();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
            return factory.generatePublic(spec);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key", e);
        } finally {
            close(in);
        }
    }

    /**
     * 使用公钥解密字符串
     *
     * @param publicKey
     * @param cipherText
     * @return
     * @throws Exception
     */
    public static String decrypt(PublicKey publicKey, String cipherText)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            // 因为 IBM JDK 不支持私钥加密, 公钥解密, 所以要反转公私钥
            // 也就是说对于解密, 可以通过公钥的参数伪造一个私钥对象欺骗 IBM JDK
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKeySpec spec = new RSAPrivateKeySpec(rsaPublicKey.getModulus(), rsaPublicKey.getPublicExponent());
            Key fakePrivateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);
            cipher = Cipher.getInstance("RSA"); //It is a stateful object. so we need to get new one.
            cipher.init(Cipher.DECRYPT_MODE, fakePrivateKey);
        }

        if (cipherText == null || cipherText.length() == 0) {
            return cipherText;
        }

        byte[] cipherBytes = Base64.getDecoder().decode(cipherText);
        byte[] plainBytes = cipher.doFinal(cipherBytes);

        return new String(plainBytes);
    }

    /**
     * 使用默认私钥加密
     *
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainText) throws Exception {
        return encrypt((String) null, plainText);
    }

    /**
     * 使用私钥加密字符串
     *
     * @param key
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String plainText) throws Exception {
        if (key == null) {
            key = DEFAULT_PRIVATE_KEY_STRING;
        }

        byte[] keyBytes = Base64.getDecoder().decode(key);
        return encrypt(keyBytes, plainText);
    }

    /**
     * 使用私钥加密字符串
     *
     * @param keyBytes
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String encrypt(byte[] keyBytes, String plainText)
            throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA", "SunRsaSign");
        PrivateKey privateKey = factory.generatePrivate(spec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        try {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        } catch (InvalidKeyException e) {
            //For IBM JDK, 原因请看解密方法中的说明
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
            Key fakePublicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, fakePublicKey);
        }

        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

        return encryptedString;
    }

    /**
     * 使用默认算法生成密钥对
     *
     * @param keySize
     * @return 0 私钥字符串 1 公钥字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static byte[][] genKeyPairBytes(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = new byte[2][];

        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
        gen.initialize(keySize, new SecureRandom());
        KeyPair pair = gen.generateKeyPair();

        keyPairBytes[0] = pair.getPrivate().getEncoded();
        keyPairBytes[1] = pair.getPublic().getEncoded();

        return keyPairBytes;
    }

    /**
     * 使用默认算法生成密钥对
     *
     * @param keySize
     * @return 0 私钥字符串 1 公钥字符串
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static String[] genKeyPair(int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        byte[][] keyPairBytes = genKeyPairBytes(keySize);
        String[] keyPairs = new String[2];

        keyPairs[0] = Base64.getEncoder().encodeToString(keyPairBytes[0]);
        keyPairs[1] = Base64.getEncoder().encodeToString(keyPairBytes[1]);

        return keyPairs;
    }

    /**
     * 资源关闭
     *
     * @param closeable
     */
    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.warn("Error", e);
            }
        }
    }
}
