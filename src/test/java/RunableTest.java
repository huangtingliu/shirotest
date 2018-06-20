import org.junit.Test;

public class RunableTest {

    @Test
    public void test(){
        for (int i=0;i<20;i++){
            final int finalI = i;
            System.out.println("xx"+finalI);
            new Runnable() {
                @Override
                public void run() {
                    System.out.println(finalI);
                }
            }.run();

            System.out.println("xx"+finalI+"end");
        }
    }

    @Test
    public void test2(){
        String str = "abcdef";
        System.out.println(str.substring(str.length()-1));
    }
}
