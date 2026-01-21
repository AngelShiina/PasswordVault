package de.angelshiina.vault;

import java.io.*;
import java.util.*;

public class VaultStore {

    private static final File FILE = new File("vault.dat");

    public static boolean exists() {
        return FILE.exists();
    }

    public static void init(char[] master) throws Exception {
        save(new HashMap<>(), master);
    }

    public static void save(Map<String,String> map, char[] master) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new ObjectOutputStream(out).writeObject(map);
        byte[] enc = VaultCrypto.encrypt(out.toByteArray(), master);
        try (FileOutputStream f = new FileOutputStream(FILE)) {
            f.write(enc);
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String,String> load(char[] master) throws Exception {
        if (!exists()) return new HashMap<>();
        byte[] enc = java.nio.file.Files.readAllBytes(FILE.toPath());
        byte[] dec = VaultCrypto.decrypt(enc, master);
        return (Map<String,String>) new ObjectInputStream(
                new ByteArrayInputStream(dec)).readObject();
    }
}