package moyongxin.jslang.system;

public class InteropUtils {
    public static native long calloc(long cnt, long size);
    public static native long malloc(long size);
    public static native void free(long ptr);

    public static native void release(long ptr);
    public static native void addRef(long ptr);
}
