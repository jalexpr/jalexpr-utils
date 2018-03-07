package template.wrapper.conversion;

public class Bytes {

    public static byte[] getBytes(int value) {
        byte[] bytes = new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) (value)
        };
        return bytes;
    }

    public static byte[] getPrimitiveBytes(int value) {
        byte[] bytes = new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) (value)
        };
        return bytes;
    }


    public static byte[] getBytes(long value) {
        byte[] bytes = new byte[]{
                (byte) (value >> 56),
                (byte) (value >> 48),
                (byte) (value >> 40),
                (byte) (value >> 32),
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) (value)
        };
        return bytes;
    }

    public static byte[] plusByte(byte[] arrA, byte elmB) {
        byte[] arrC = new byte[arrA.length + 1];
        System.arraycopy(arrA, 0, arrC, 0, arrA.length);
        arrC[arrA.length] = elmB;
        return arrC;
    }

    public static byte[] plusByte(byte[] arrA, byte[] arrB) {
        byte[] arrC = new byte[arrA.length + arrB.length];
        System.arraycopy(arrA, 0, arrC, 0, arrA.length);
        System.arraycopy(arrB, 0, arrC, arrA.length, arrB.length);
        return arrC;
    }

}
