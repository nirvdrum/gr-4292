import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.c.function.CEntryPoint;

public class Bad {
  public static void main(String[] argv) {
  }

  @CEntryPoint
  public static int foo(IsolateThread thread) {
    return 1;
  }
}