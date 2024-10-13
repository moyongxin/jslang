package moyongxin.jslang;

import moyongxin.jslang.system.CString;

import static moyongxin.jslang.system.InteropUtils.calloc;
import static moyongxin.jslang.system.InteropUtils.free;

public class TargetDesc implements AutoCloseable {
    public static final long struct_size = 48;

    private final boolean is_ref;
    private final long ptr;

    public long getPtr() { return ptr; }

    public static native void ctor(long ptr, int format, int profile, int flags, int floatingPointMode, int lineDirectiveMode, boolean forceGLSLScalarforceGLSLScalarBufferLayout, long compilerOptionEntries, int compilerOptionEntryCount);

    public TargetDesc(long ptr) {
        this.ptr = ptr;
        is_ref = true;
    }

    public TargetDesc(int format, int profile, int flags, int floatingPointMode, int lineDirectiveMode, boolean forceGLSLScalarforceGLSLScalarBufferLayout, long compilerOptionEntries, int compilerOptionEntryCount) {
        ptr = calloc(1, struct_size);
        is_ref = false;
        ctor(ptr, format, profile, flags, floatingPointMode, lineDirectiveMode, forceGLSLScalarforceGLSLScalarBufferLayout, compilerOptionEntries, compilerOptionEntryCount);
    }

    @Override
    public void close() throws Exception {
        if (!is_ref) {
            free(ptr);
        }
    }
}
