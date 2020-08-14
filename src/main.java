import Interpreter.*;
import VirtualMemory.*;
import Processes.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Vector;

public class main {

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int result=0;
        //String program = "MV AX 5 MV BX 10 ML AX BX PR AX EX";
        //VirtualMemory.VirtualMemory.loadProgram(program);
        Interpreter it = new Interpreter();
        while (true)
        {
            String input = sc.nextLine();
            switch (input) {
                case "s":
                    result = it.interpretcommand();
                    System.out.println("Step");
                    if(result ==-1)
                    {
                        System.out.println("Nieznany rozkaz!");
                    }
                    else if(result ==-2)
                    {
                        System.out.println("Brak procesu w stanie \"running\".");
                    }
                    break;
                case "np":
                    input = sc.nextLine() + ".txt";
                    String p_name = sc.nextLine();
                    try {
                        File file = new File(input);
                        Scanner reader = new Scanner(file);
                        String program = reader.nextLine();
                        VirtualMemory.loadProgram(program);
                        System.out.println("Wczytano program:");
                        System.out.println(program);
                        PCB pcb = new PCB(p_name, program.length());
                        pcb.setState(process_list.Running);
                        process_list.p_list.add(pcb);

                        reader.close();
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Plik nie istnieje.");
                    }
                    break;
                case "show virtual":
                    VirtualMemory.showVirtualMemory();
                    break;
                case "show pcb":
                    int rp=0-1;
                    for(int i=0;i<process_list.p_list.size();i++)
                    {
                        if(process_list.p_list.get(i).getState()==process_list.Running)
                        {
                            rp = i;
                        }
                    }
                    if(rp!=-1) {
                        System.out.println(process_list.p_list.get(rp).getName());
                        System.out.println("AX: " + process_list.p_list.get(rp).getAX());
                        System.out.println("BX: " + process_list.p_list.get(rp).getBX());
                        System.out.println("CX: " + process_list.p_list.get(rp).getCX());
                        System.out.println("DX: " + process_list.p_list.get(rp).getDX());
                        System.out.println("PC: " + process_list.p_list.get(rp).getPC());
                        System.out.println("Open Files: " + process_list.p_list.get(rp).getOpenFiles());
                    }
                    break;
                case "dp":
                    input = sc.nextLine();
                    for(int i =0; i <process_list.p_list.size();i++)
                    {
                        if(process_list.p_list.get(i).getName().equals(input));
                        {
                            process_list.p_list.remove(i);
                            VirtualMemory.clearVirtualMemory();
                            System.out.println("Usunięto proces "+input);
                        }
                    }
                    break;
                case "exit":
                    System.exit(0);
                    break;
                case "help":
                    System.out.println("np");
                    System.out.println("show virtual");
                    System.out.println("dp");
                    System.out.println("exit");
                    break;
                default:
                    System.out.println("Nieznana komenda");
            }
        }
    }
}
