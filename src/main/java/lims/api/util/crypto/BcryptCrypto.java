package lims.api.util.crypto;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptCrypto implements OneWayCrypto {

    @Override
    public String encrypt(String target) {
        return BCrypt.hashpw(target, BCrypt.gensalt());
    }

    @Override
    public boolean equals(String target, String encrypted) {
        return BCrypt.checkpw(target, encrypted);
    }
}