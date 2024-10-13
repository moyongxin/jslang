package moyongxin.jslang;

import static moyongxin.jslang.system.InteropUtils.calloc;
import static moyongxin.jslang.system.InteropUtils.free;

public class CompilerOptionEntry implements AutoCloseable {
    public static final long struct_size = 40;

    private final boolean is_ref;
    private final long ptr;

    public long getPtr() { return ptr; }

    public static native void ctor(long ptr, int name, long val);

    public CompilerOptionEntry(long ptr) {
        this.ptr = ptr;
        is_ref = true;
    }

    public CompilerOptionEntry(int name, long val) {
        ptr = calloc(1, struct_size);
        is_ref = false;
        ctor(ptr, name, val);
    }

    @Override
    public void close() throws Exception {
        if (!is_ref) {
            free(ptr);
        }
    }
}
