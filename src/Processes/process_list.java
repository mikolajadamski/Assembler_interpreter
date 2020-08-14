package Processes;

import java.util.Vector;

public class process_list {
    public static int Ready = 1;
    public static int Running = 2;
    public static int Waiting = 3;
    public static int Terminated = 4;
    public static Vector<PCB> p_list = new Vector<PCB>();
}
