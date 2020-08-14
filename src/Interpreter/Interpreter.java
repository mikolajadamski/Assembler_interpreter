package Interpreter;

import VirtualMemory.*;
import Processes.*;
import java.lang.reflect.Array;
import java.util.Vector;
/*
DONE: AD, SB, ML, DV, IC, DC, MV,
TODO: JN, JZ, JP, NF, OF, CF, DF
 */

public class Interpreter {
    private int AX, BX, CX, DX, PID;
    private int PC;
    private int prsize;
    private int fileref;
    public Interpreter(){
        this.AX =0;
        this.BX =0;
        this.CX =0;
        this.DX =0;
        this.PID =0;
        this.PC = 0;
        this.prsize = 0;
    }

    private  void setRegisters(int AX, int BX, int CX, int DX)
    {
        this.AX = AX;
        this.BX = BX;
        this.CX = CX;
        this.DX = DX;
    }
    private void setCommandCounter(int PC)
    {
        this.PC = PC;
    }
    //sprawdzenie czy zmienna jest liczbą
    private boolean isDigit(String command)
    {
        try
        {
            int number = Integer.parseInt(command);
            return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
    }



    //pobranie pojedyczego polecenia/wartości/adresu z pamięci
    private  String getCommand() {
        int i=0;
        String command = new String();
        String charFromMem = "";

        while (this.PC<this.prsize)
        //&& VirtualMemory.VirtualMemory.Order_Table.size()>this.CommandCounter)
        {
            charFromMem = new String(new byte [] {(VirtualMemory.getByteAt(this.PC))});
            if(charFromMem.equals(" "))
            {
                this.PC++;
                break;
            }
            command += charFromMem;
            this.PC++;
        }
        System.out.println(command);
        return command;

    }



    //sprawdzenie który proces jest w stanie "running" i zwrócenie jego indeksu w liście
    private int findRunningProcess()
    {
        for (int i=0; i <process_list.p_list.size();i++)
        {
            if(process_list.p_list.get(i).getState()== process_list.Running)
            {
                return i;
            }
        }
        return -2;
    }

    //funkcja realizująca operacje arytmetyczne
    private void arithmeticOp(String command, String type, String command2)
    {
        int val = 0;
        val = getVal(command2);
        if (type.equals("+"))
        {
            if (command.equals("AX")) { this.AX += val; }
            else if (command.equals("BX"))  { this.BX+= val; }
            else if (command.equals("CX")) { this.CX+= val; }
            else if (command.equals("DX")) { this.DX+= val; }
        }
        else if (type.equals("-"))
        {
            if (command.equals("AX")) { this.AX -= val; }
            else if (command.equals("BX"))  { this.BX -= val; }
            else if (command.equals("CX")) { this.CX -= val; }
            else if (command.equals("DX")) { this.DX -= val; }
        }
        else if (type.equals("*"))
        {
            if (command.equals("AX")) { this.AX *= val; }
            else if (command.equals("BX"))  { this.BX *= val; }
            else if (command.equals("CX")) { this.CX *= val; }
            else if (command.equals("DX")) { this.DX *= val; }
        }
            else if (type.equals("/")) {
                if (val == 0)
                {
                    System.out.println("Dzielenie przez 0. Zmiana wartości na 1");
                    val = 1;
                }
                if (command.equals("AX")) {
                    this.AX /= val;
                } else if (command.equals("BX")) {
                    this.BX /= val;
                } else if (command.equals("CX")) {
                    this.CX /= val;
                } else if (command.equals("DX")) {
                    this.DX /= val;
                }
            }
    }
    private int getVal(String command)
    {
        if (command.equals("AX")) { return this.AX; }
        else if (command.equals("BX"))  { return this.BX; }
        else if (command.equals("CX")) { return this.CX; }
        else if (command.equals("DX")) { return this.DX; }
        else if (isDigit(command) == true) { return Integer.parseInt(command); }
        else if (command.charAt(0)=='[') { return getValFromAddress(command); }
        else return 0;
    }
    private int getValFromAddress(String address)
    {
        int addressOfNumber = Integer.parseInt(address.substring(1, address.length() - 1));
        return VirtualMemory.getByteAt(addressOfNumber);
    }

    private String getName(String command)
    {
        return command.substring(1,command.length()-1);
    }



    // główna funkcja wywoływana przez Shell
    //wykonanie pojedynczego rozkazu
    public int interpretcommand() {
        int rp = findRunningProcess();
        if (rp == -2)
        {
            return -2;
        }
        setRegisters(process_list.p_list.get(rp).getAX(),process_list.p_list.get(rp).getBX(),
                process_list.p_list.get(rp).getCX(),process_list.p_list.get(rp).getDX());
        setCommandCounter(process_list.p_list.get(rp).getPC());
        this.prsize = process_list.p_list.get(rp).getPrSize();
        String command = getCommand();
        //suma
        if(command.equals("AD"))
        {
            command = getCommand();
            String command2 = getCommand();
            arithmeticOp(command, "+",command2);
        }
        //r�nica
        else if(command.equals("SB"))
        {
            command = getCommand();
            String command2 = getCommand();
            arithmeticOp(command, "-",command2);
        }
        //iloczyn
        else if(command.equals("ML"))
        {
            command = getCommand();
            String command2 = getCommand();
            arithmeticOp(command, "*",command2);
        }
        //iloraz
        else if(command.equals("DV"))
        {
            command = getCommand();
            String command2 = getCommand();
            arithmeticOp(command, "/",command2);
        }
        //inkrementacja
        else if (command.equals("IC")) {
            command = getCommand();
            if (command.equals("AX")) {
                this.AX++;
            } else if (command.equals("BX")) {
                this.BX++;
            } else if (command.equals("CX")) {
                this.CX++;
            } else if (command.equals("DX")) {
                this.DX++;
            }
        }
        //dekrementacja
        else if (command.equals("DC")) {
            command = getCommand();
            if (command.equals("AX")) {
                this.AX--;
            } else if (command.equals("BX")) {
                this.BX--;
            } else if (command.equals("CX")) {
                this.CX--;
            } else if (command.equals("DX")) {
                this.DX--;
            }
        }
        //przenoszenie warto�ci do rejestru
        else if(command.equals("MV"))
        {
            command = getCommand();
            int val = getVal( getCommand());
            if (command.equals("AX")) { this.AX = val; }
            else if (command.equals("BX"))  { this.BX = val; }
            else if (command.equals("CX")) { this.CX = val; }
            else if (command.equals("DX")) { this.DX = val; }
            else if (command.substring(0,1).equals("["))
            {VirtualMemory.setByteAt(Integer.parseInt(command.substring(1,command.length()-1)),(byte)val);}
        }
        //skok bezwarunkowy
        else if(command.equals("JP"))
        {
            String address = getCommand();
            this.PC = Integer.parseInt(address.substring(1, address.length() - 1));
        }
        //skok je�eli CX == 0
        else if(command.equals("JZ"))
        {
            if (this.CX == 0)
            {
                String address = getCommand();
                this.PC = Integer.parseInt(address.substring(1, address.length() - 1));
            }
            else
            {
                command = getCommand();
            }
        }
        //skok je�eli CX != 0
        else if(command.equals("JN"))
        {
            if (this.CX != 0)
            {
                String address = getCommand();
                this.PC = Integer.parseInt(address.substring(1, address.length() - 1));
            }
            else
            {
                command = getCommand();
            }
        }
        else if(command.equals("JL"))
        {
            if (this.CX < 0)
            {
                String address = getCommand();
                this.PC = Integer.parseInt(address.substring(1, address.length() - 1));
            }
            else
            {
                command = getCommand();
            }
        }
        //tworzenie pliku
        else if (command.equals("NF"))
        {
            command = getCommand();
            System.out.println("Wywołano funkcję tworzenia pliku o nazwie "+command);
        }
        else if (command.equals("OF"))
        {
            command = getCommand();
            System.out.println("Wywołano funkcję otwarcia pliku o nazwie "+command);
            Vector<String> of = process_list.p_list.get(rp).getOpenFiles();
            of.add(command);
            process_list.p_list.get(rp).setOpenFiles(of);
        }
        else if (command.equals("WF"))
        {
            command = getCommand();
            System.out.println("Wywołano funkcję zapisania do pliku o nazwie "+command+".");
            command = getCommand();
            System.out.println("Zapisanie wartości: "+getVal(command));
        }
        else if (command.equals("AF"))
        {
            command = getCommand();
            System.out.print("Wywołano funkcję dopisania do pliku o nazwie "+command);
            command = getCommand();
            System.out.println(". Dopisanie wartości: "+getVal(command));
        }
        else if (command.equals("RF"))
        {
            command = getCommand();
            String val = getCommand();
            System.out.println("Wywołano funkcję odczytania pojedynczej wartości z pliku o nazwie "+command+
                    " do "+val);
        }
        //zamknięcie pliku
        else if (command.equals("CF"))
        {
            command = getCommand();
            System.out.println("Zamknięcie pliku o nazwie "+command);
            Vector<String> of = process_list.p_list.get(rp).getOpenFiles();
            of.remove(command);
            process_list.p_list.get(rp).setOpenFiles(of);
        }
        else if (command.equals("DF"))
        {
            command = getCommand();
            System.out.println("Usunięcie pliku o nazwie "+command);
        }
        else if (command.equals("EQ"))
        {
            int val1 = getVal(getCommand());
            int val2 = getVal(getCommand());
            int val3 = (val1==val2)? 1:0;
            command = getCommand();
            if (command.equals("AX"))
            {
                this.AX = val3;
            }
            else if (command.equals("BX"))
            {
                this.BX = val3;
            }
            else if (command.equals("CX"))
            {
                this.CX = val3;
            }
            else if (command.equals("DX"))
            {
                this.DX = val3;
            }

        }

        else if (command.equals("CD"))
        {
            command = getCommand();
            System.out.println("Wywołanie funkcji stworzenia katalogu o nazwie "+command);
        }

        else if (command.equals("DD"))
        {
            command = getCommand();
            System.out.println("Wywołanie funkcji usunięcia katalogu o nazwie "+command);
        }
        else if (command.equals("MD"))
        {
            command = getCommand();
            System.out.println("Wywołanie funkcji przejścia do katalogu o nazwie "+command);
        }
        else if (command.equals("PR"))
        {
            command = getCommand();
            if (command.equals("AX")) {
                System.out.println("Wartość rejestru AX: "+this.AX);
            } else if (command.equals("BX")) {
                System.out.println("Wartość rejestru BX: "+this.BX);
            } else if (command.equals("CX")) {
                System.out.println("Wartość rejestru CX: "+this.CX);
            } else if (command.equals("DX")) {
                System.out.println("Wartość rejestru DX: "+this.DX);
            }
        }
        else if (command.equals("CP"))
        {
            String filename = getCommand();
            String processname = getCommand();
            System.out.println("Wywołano funkcję tworzenia nowego procesu o nazwie " +processname+
                    " i programie z pliku "+ filename);
        }
        else if (command.equals("DP"))
        {
            String filename = getCommand();
            String processname = getCommand();
            System.out.println("Wywołano funkcję usuwania procesu o nazwie " +processname);
        }
        //zako�czenie programu
        else if(command.equals("EX"))
        {
            System.out.println("Proces "+ process_list.p_list.get(rp).getName() +" zakończony");
            process_list.p_list.remove(rp);
            return 4;
        }
        else
        {
            process_list.p_list.get(rp).setPC(this.PC++);
            return -1;
        }
        process_list.p_list.get(rp).setAX(this.AX);
        process_list.p_list.get(rp).setBX(this.BX);
        process_list.p_list.get(rp).setCX(this.CX);
        process_list.p_list.get(rp).setDX(this.DX);
        process_list.p_list.get(rp).setPC(this.PC);

        //pod koniec wykonwywania rozkazu, aktualizuje pcb procesu oraz zwraca 2 = running
        return 2;
    }
}