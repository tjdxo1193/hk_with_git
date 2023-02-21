package lims.api.util.crypto;

public interface OneWayCrypto extends Encryptable {

    boolean equals(String target, String encrypted);

}