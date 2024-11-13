public class MemoryManager
{
 private final int totalMemory = 1024; // Initialize the total memory to 1024 as per the req.
 private static int usedMemory = 0;    // Initialize the used memory to keep track of job memory usage.

    public  boolean checkMemory(int memoryRequired)
    {
        return memoryRequired + usedMemory <= totalMemory; // this shall return if there is enough memory or not
    }

    public void allocateMemory(int memoryRequired) // this method shall allocate memory
    {                                              // iff there is enough memory it shall print an error message
        if (checkMemory(memoryRequired))
            usedMemory += memoryRequired;
        else
            System.out.println("Sorry There is No Memory Available !");
    }

    // Releases the specified memory amount if valid; returns true if successful, false otherwise.
    public boolean releaseMemory(int memoryRequired) {
        if (memoryRequired <= usedMemory) {
            usedMemory -= memoryRequired;
            return true;
        } else {
            System.out.println("Error: Cannot release more memory than is currently allocated!");
            return false;
        }
    }
    // The following method will help us with logging the remaining memory in the main memory.
    public int getRemainingMemory() {
        return totalMemory - usedMemory;
    }

}
