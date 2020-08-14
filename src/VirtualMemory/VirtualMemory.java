package VirtualMemory;

public class VirtualMemory {
    static byte [] vmem = new byte [128];
    public static byte getByteAt(int index)
    {
        return vmem[index];
    }
    public static void setByteAt(int index, byte data)
    {
        vmem[index] = data;
    }
    public static void loadProgram(String program)
    {
        byte[] pr = program.getBytes();
        for (int i = 0; i<pr.length;i++)
        {
            vmem[i] = pr[i];
        }
        for (int i = pr.length;i<vmem.length; i ++)
        {
            vmem[i] = 0;
        }
    }
    public static void showVirtualMemory()
    {
        for(int i = 1;i<vmem.length+1;i++)
        {
            int temp = i-1;
            if(vmem[temp]==0)
            {
                System.out.print(" ");
            }
            System.out.print(vmem[temp]+" ");
            if (i%30==0)
            {
                System.out.print("\n");
            }
        }
        System.out.print("\n");
    }
    public static void clearVirtualMemory()
    {
        for (int i = 0; i<vmem.length;i++)
        {
            vmem[i] = 0;
        }
    }
}
