/*
 * Copyright 2015 Jean-Michel.
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
package com.auxeanne.realm.db.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;

/**
 *
 * @author Jean-Michel
 */
public class Hasher {
    
    public static final String DB_DIGEST_ALGORITHM_PROPERTY = "DIGEST_ALGORITHM";
    public static final String DB_DIGEST_ALGORITHM_SHA256 = "SHA-256";
    public static final String DB_DIGEST_ALGORITHM_SHA512 = "SHA-512";
    
    /**
     * hashing password for verification against database value
     *
     * @param digestAlgorithm SHA256 or SHA512
     * @param password text to hash
     * @return Hashed password
     * @throws LoginException any exception if reseted as LoginException
     */
    static public String hash(String digestAlgorithm, String password) throws LoginException {
        try {
            MessageDigest digest = MessageDigest.getInstance(digestAlgorithm);
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            // critical setup logging
            Logger.getLogger("Delegate").log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             Logger.getLogger("Delegate").log(Level.SEVERE, null, ex);
        }
        throw new LoginException();
    }
}
