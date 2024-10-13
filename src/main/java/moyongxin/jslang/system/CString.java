package moyongxin.jslang.system;

import static moyongxin.jslang.system.InteropUtils.free;

public class CString implements AutoCloseable {
    private final long ptr;
    private final boolean is_ref;

    public static native long from(String string);
    public static native String toString(long ptr);

    public CString(String string) {
        ptr = from(string);
        is_ref = false;
        if (ptr == 0) {
            throw new RuntimeException("Failed to convert to c-style string: " + string);
        }
    }

    public CString(long ptr) {
        this.ptr = ptr;
        is_ref = true;
    }

    public long getPtr() {
        return ptr;
    }

    public String toString() {
        return toString(ptr);
    }

    @Override
    public void close() throws Exception {
        if (!is_ref) {
            free(ptr);
        }
    }
}
