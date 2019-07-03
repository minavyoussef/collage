/* ******************************************************************************************************
 *  (c) 2015 Mina Victor (S# 14200381/mina.victor@ucdconnect.ie), all right reserved
 * 
 *  MSc Business Anaytics ~ MIS40980 Natural Computing & Applications 2015
 *    Coordinated by Prof Michael O'Neill
 *    
 *    This code is presented as part of summer course project:
 *    "A Genetic Programming-Based Approach for Building Automated Deception Detection Predictive Model"
 * ******************************************************************************************************/

package ie.ucd.mscba.nca.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Random;

public final class Utilites {
  public static void seed(long seed) {
    random.setSeed(seed);
  }

  public static int randomize(int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }

  public static double randomize(double max, double min) {
    double r = Math.random();
    if (r < 0.5) {
      return ((1 - Math.random()) * (max - min) + min);
    }
    return (Math.random() * (max - min) + min);
  }

  public static boolean randomizeBinary() {
    return randomize(-10, 10) % 2 == 0 ? true : false;
  }

  public static boolean randomizeBinaryWithBiased(double trueRatio) {
    if (trueRatio < 0.0 || trueRatio > 1.0) {
      return randomizeBinary();
    }

    trueRatio *= (int) 10;
    int rand = randomize(1, 10);
    return rand <= trueRatio;
  }

  public static <T extends Enum<T>> T randomizeEnumerator(Class<T> enumType, boolean includeFirst) {
    T[] values = enumType.getEnumConstants();
    return randomizeArray(values, includeFirst);
  }

  public static <T> T randomizeArray(T[] values, boolean zeroBase) {
    return (values.length > 1) ? values[randomize(zeroBase ? 0 : 1, values.length - 1)] : values[0];
  }

  public static <T> T randomizeZeroBasedArray(T[] values) {
    return randomizeArray(values, true);
  }

  public static FilesPathesCollection getFilesPathesByDirectory(String directoryName) {
    DetectorException.throwIfNullOrEmpty(directoryName, "directoryName");
    DetectorException.throwIfPathNotExists(directoryName);
    DetectorException.throwIfPathNotDirectory(directoryName);

    FilesPathesCollection filesPathes = new FilesPathesCollection();
    _getFilesPathesByDirectory(directoryName, filesPathes);

    return filesPathes;
  }

  private static void _getFilesPathesByDirectory(String directoryName, FilesPathesCollection filesPathes) {
    File dir = new File(directoryName);
    File[] directoryListing = dir.listFiles();
    if (directoryListing != null) {
      for (File file : directoryListing) {
        if (file.isDirectory())
          _getFilesPathesByDirectory(file.getAbsolutePath(), filesPathes);
        else
          filesPathes.add(file.getAbsolutePath());
      }
    }
  }

  public static String readFileContent(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  public static String getFileName(String path) {
    DetectorException.throwIfNullOrEmpty(path, "path");
    DetectorException.throwIfPathNotExists(path);
    DetectorException.throwIfPathNotFile(path);

    Path filePath = Paths.get(path);
    return filePath.getFileName().toString();
  }

  public static void deleteIfExists(Path path) throws IOException {
    DetectorException.throwIfNull(path, "path");

    if (Files.exists(path)) {
      Files.delete(path);
    }
  }

  public static String limitString(Object obj, int limit) {
    DetectorException.throwIfNull(obj, "obj");
    DetectorException.throwIfNegative(limit);

    String serializeObj = obj.toString();
    if (serializeObj.length() <= limit) {
      return serializeObj;
    }

    final String ellipsis = "...";
    return serializeObj.substring(0, limit - ellipsis.length()).concat(ellipsis);
  }

  public static String format(double value) {
    return decimalFormat.format(value);
  }

  public static String serializeDouble(Object value) {
    DetectorException.throwIfNull(value, "value");
    return String.format("%.2f", value);
  }

  public static String serializeInteger(Object value) {
    DetectorException.throwIfNull(value, "value");
    return String.format("%d", value);
  }

  public static void createDirectoryIfNotExists(String dirPath) {
    File dirPathFile = new File(dirPath);
    if (!dirPathFile.exists()) {
      dirPathFile.mkdirs();
    }
  }

  private static Random random;
  private static DecimalFormat decimalFormat;
  static {
    random = new Random();
    decimalFormat = new DecimalFormat("#.000");
  }
}
