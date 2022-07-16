package fr.armees.sagaiem.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;

/**
 _____        _____                  _
 |  __ \      / ____|                | |
 | |  | | ___| |     _ __ _   _ _ __ | |_ ___  _ __
 | |  | |/ _ \ |    | '__| | | | '_ \| __/ _ \| '__|
 | |__| |  __/ |____| |  | |_| | |_) | || (_) | |
 |_____/ \___|\_____|_|   \__, | .__/ \__\___/|_|
 __/ | |
 |___/|_|
 */
public class DeCryptor {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    private KeyStore keyStore;

    public DeCryptor() throws  NoSuchAlgorithmException, KeyStoreException,
            IOException, CertificateException {
        initKeyStore();
    }

    public synchronized void initKeyStore() throws KeyStoreException,
            NoSuchAlgorithmException, IOException, CertificateException {
        keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);
    }

    public synchronized boolean isKeyExiste(final String alias){
        try {
            return Objects.nonNull(getSecretKey(alias));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnrecoverableEntryException e) {
            return false;
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String  decryptData(final String alias, final byte[] encryptedData, final byte[] encryptionIv)
            throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException,
            NoSuchPaddingException, InvalidKeyException, IOException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        final GCMParameterSpec spec = new GCMParameterSpec(128, encryptionIv);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(alias), spec);

        return new String(cipher.doFinal(encryptedData), "UTF-8");
    }

    private synchronized SecretKey getSecretKey(final String alias) throws NoSuchAlgorithmException,
            UnrecoverableEntryException, KeyStoreException {
        return ((KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null)).getSecretKey();
    }
}