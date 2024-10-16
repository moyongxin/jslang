package moyongxin.jslang;

import moyongxin.jslang.system.CString;
import moyongxin.jslang.system.InteropUtils;

public class GlobalSession implements AutoCloseable {
    private final long ptr;

    public long getPtr() {
        return ptr;
    }

    public static native long createGlobalSession();
    public static native int findProfile(long ptr, long name);
    public static native boolean checkPassThroughSupport(long ptr, int passThrough);

    public GlobalSession() {
        ptr = createGlobalSession();
        if (ptr == 0) {
            throw new RuntimeException("Failed to create Slang GlobalSession");
        }
    }

    public int findProfile(String name) {
        return findProfile(ptr, new CString(name).getPtr());
    }
    public boolean checkPassThroughSupport(int passThrough) {
        return checkPassThroughSupport(ptr, passThrough);
    }

    @Override
    public void close() throws Exception {
        if (ptr != 0) {
            InteropUtils.release(ptr);
        }
    }
}
