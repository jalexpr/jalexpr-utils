/*
 * Copyright (C) 2017  Alexander Porechny alex.porechny@mail.ru
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0) as published by the Creative Commons.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * for more details.
 *
 * You should have received a copy of the Attribution-NonCommercial-ShareAlike
 * 3.0 Unported (CC BY-SA 3.0) along with this program.
 * If not, see <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 *
 * Copyright (C) 2017 Александр Поречный alex.porechny@mail.ru
 *
 * Эта программа свободного ПО: Вы можете распространять и / или изменять ее
 * в соответствии с условиями Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0), опубликованными Creative Commons.
 *
 * Эта программа распространяется в надежде, что она будет полезна,
 * но БЕЗ КАКИХ-ЛИБО ГАРАНТИЙ; без подразумеваемой гарантии
 * КОММЕРЧЕСКАЯ ПРИГОДНОСТЬ ИЛИ ПРИГОДНОСТЬ ДЛЯ ОПРЕДЕЛЕННОЙ ЦЕЛИ.
 * См. Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * для более подробной информации.
 *
 * Вы должны были получить копию Attribution-NonCommercial-ShareAlike 3.0
 * Unported (CC BY-SA 3.0) вместе с этой программой.
 * Если нет, см. <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 */
package template.wrapper.classes;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.LZMAOutputStream;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static template.wrapper.classes.FileHelper.*;

public class Lzma2FileHelper {

    public static final String ARCHIVE_EXPANSION = ".7z";
    private static final int LAVAL_COMPRESS = 9;

    public static int compressionFile(String pathFile) {
        return compressionFile(pathFile, LAVAL_COMPRESS);
    }

    public static int compressionFile(String pathFile, int lavalCompress) {
        LZMA2Options options = new LZMA2Options();
        setPresetInOptions(options, lavalCompress);

        System.err.println("Encoder memory usage: " + options.getEncoderMemoryUsage() + " KiB");
        System.err.println("Decoder memory usage: " + options.getDecoderMemoryUsage() + " KiB");

        BufferedInputStream bufferedInput = openBufferedInputStream(pathFile);
        int inputSize = available(bufferedInput);
        byte[] buf = new byte[inputSize];
        FileHelper.read(bufferedInput, buf);

        LZMAOutputStream encoder = openLzmaOutputStream(pathFile, options, inputSize);
        write(encoder, buf, 0, inputSize);

        closeFile(bufferedInput);
        closeLZMAFile(encoder);
        return inputSize;
    }

    public static void deCompressionFile(String pathFile, File newNameFile) {
        deCompressionFile(pathFile, Integer.MAX_VALUE / 2, newNameFile);
    }

    public static void deCompressionFile(String pathFile, int sizeFileInByte, File newNameFile) {
        FileOutputStream deCompressFile = openFileOutputStream(newNameFile);
        InputStream compressFile = openLzmaInputStream(pathFile);
        byte[] buf = new byte[sizeFileInByte];
        int sizeDecompressFile = read(compressFile, buf, 0, sizeFileInByte);
        FileHelper.write(deCompressFile, buf, sizeDecompressFile);
    }

    public static LZMAOutputStream openLzmaOutputStream(String pathFile, LZMA2Options options, long var3) {
        LZMAOutputStream lzmaOutputStream = null;
        try {
            String fullNameFile = String.format("%s%s", pathFile, ARCHIVE_EXPANSION);
            lzmaOutputStream = new LZMAOutputStream(openFileOutputStream(fullNameFile), options, var3);
        } catch (IOException ex) {
            String messages = String.format("Ошибка при чтении файла.\r\nПроверте наличие %s\r\n", pathFile);
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, messages, ex);
        }
        return lzmaOutputStream;
    }

    public static LZMAInputStream openLzmaInputStream(String pathFile) {
        LZMAInputStream lzmaInputStream = null;
        try {
            lzmaInputStream = new LZMAInputStream(Lzma2FileHelper.class.getClassLoader().getResourceAsStream(pathFile));
        } catch (IOException ex) {
            String messages = String.format("Ошибка при чтении файла.\r\nПроверте наличие %s\r\n", pathFile);
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, messages, ex);
        }
        return lzmaInputStream;
    }

    public static void setPresetInOptions(LZMA2Options options, int lavalCompress) {
        try {
            options.setPreset(lavalCompress);
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void write(LZMAOutputStream encoder, byte[] buf, int var, int inputSize) {
        try {
            encoder.write(buf, var, inputSize);
        } catch (IOException ex) {
            String messages = String.format("Ошибка при чтении файла.\r\n");
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, messages, ex);
        }
    }

    public static int read(InputStream compressFile, byte[] buf, int var, int sizeFileInByte) {
        try {
            return compressFile.read(buf, var, sizeFileInByte);
        } catch (IOException ex) {
            String messages = String.format("Ошибка при чтении файла.\r\n");
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, messages, ex);
        }
        return 0;
    }

    public static void closeLZMAFile(LZMAOutputStream lzmaOutputStream) {
        try {
            lzmaOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, "Не удалось закрыть файл!", ex);
        }
    }

//    public static void main(String[] args) {
//        String nameFile = "dict.opcorpora.txt";
//        int size = 282217094;
//        int size = compressionFile(nameFile, 9);
//        deCompressionFile(nameFile + ARCHIVE_EXPANSION, size, nameFile);
//    }
}
