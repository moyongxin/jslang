package moyongxin.jslang;

import static moyongxin.jslang.system.InteropUtils.calloc;
import static moyongxin.jslang.system.InteropUtils.free;

public class Array implements AutoCloseable {
    private final long count;
    private final long size;
    private final long ptr;

    public long getCount() {
        return count;
    }

    public long getSize() {
        return size;
    }

    public long getPtr() {
        return ptr;
    }

    public Array(long count, long size) {
        ptr = calloc(count, size);

        if (ptr == 0) {
            throw new RuntimeException("Failed to allocate array of size " + String.valueOf(count * size));
        }

        this.count = count;
        this.size = size;
    }

    public long getElement(long idx) {
        return ptr + idx * size;
    }

    @Override
    public void close() throws Exception {
        free(ptr);
    }
}
