public class MemoryManager {
 private final int totalMemory = 1024 ;
 private static int usedMemory ;

    public  boolean checkMemory(int memoryRequired)
    {
        return memoryRequired + usedMemory <= totalMemory;
    }
    public void allocateMemory(int memoryRequired)
    {
        if (checkMemory(memoryRequired))
            usedMemory += memoryRequired;
        else
            System.out.println("Sorry There is No Memory Available !") ;
    }

    public  void releaseMemory(int memoryRequired)
    {
        if ( usedMemory - memoryRequired <= 0 ) // this will handle if the used memory is empty
            usedMemory -= memoryRequired ;      //or if someone try to release more memory than used
        else
            System.out.println("Sorry Wrong Operation !") ;
    }


}
