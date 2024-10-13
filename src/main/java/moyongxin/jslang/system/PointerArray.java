package moyongxin.jslang.system;

import static moyongxin.jslang.system.InteropUtils.calloc;
import static moyongxin.jslang.system.InteropUtils.free;

public class PointerArray implements AutoCloseable {
    private final long ptr;
    private final long count;

    public long getPtr() {
        return ptr;
    }

    public long getCount() {
        return count;
    }

    public static native long getElement(long ptr, long idx);
    public static native void setElement(long ptr, long idx, long val);

    public PointerArray(long cnt) {
        ptr = calloc(cnt, 8);
        count = cnt;
    }

    public long getElement(long idx) {
        return getElement(ptr, idx);
    }

    public void setElement(long idx, long val) {
        setElement(ptr, idx, val);
    }

    @Override
    public void close() throws Exception {
        free(ptr);
    }
}
