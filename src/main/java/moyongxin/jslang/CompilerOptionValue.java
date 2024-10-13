package moyongxin.jslang;

import static moyongxin.jslang.system.InteropUtils.calloc;
import static moyongxin.jslang.system.InteropUtils.free;

public class CompilerOptionValue implements AutoCloseable {
    public static final long struct_size = 32;

    private final boolean is_ref;
    private final long ptr;

    public long getPtr() { return ptr; }

    public static native void ctor(long ptr, int kind, int intValue0, int intValue1, long stringValue0, long stringValue1);

    public CompilerOptionValue(long ptr) {
        this.ptr = ptr;
        is_ref = true;
    }

    public CompilerOptionValue(int kind, int intValue0, int intValue1, long stringValue0, long stringValue1) {
        ptr = calloc(1, struct_size);
        is_ref = false;
        ctor(ptr, kind, intValue0, intValue1, stringValue0, stringValue1);
    }

    @Override
    public void close() throws Exception {
        if (!is_ref) {
            free(ptr);
        }
    }
}
