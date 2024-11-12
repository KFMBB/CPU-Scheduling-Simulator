public class MemoryManager
{
 private final int totalMemory = 1024 ;
 private static int usedMemory ;

    public  boolean checkMemory(int memoryRequired)
    {
        return memoryRequired + usedMemory <= totalMemory; // this shall return if there is enough memory or not
    }
    public void allocateMemory(int memoryRequired) // this method shall allocate memory
    {                                              // iff there is enough memory it shall print an error message
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
