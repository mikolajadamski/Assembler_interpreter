package Processes;

import java.util.Vector;

public class PCB {
    private int AX,BX,CX,DX,PC;
    private int PID;
    private String name;
    private int state;
    private int prSize;
    private Vector<String> openFiles;
    public PCB(String name,int size)
    {
        this.name = name;
        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;
        this.PC = 0;
        this.state = process_list.Ready;
        this.prSize = size;
        this.openFiles = new Vector<String>();
    }
    public String getName() { return this.name; }
    public int getAX() { return this.AX; }
    public int getBX() { return this.BX; }
    public int getCX() { return this.CX; }
    public int getDX() { return this.DX; }
    public int getPC() { return this.PC; }
    public int getState() { return this.state; }
    public int getPrSize() { return prSize; }
    public Vector<String> getOpenFiles() { return openFiles; }

    public void setName(String name) { this.name = name; }
    public void setAX(int val) { this.AX = val; }
    public void setBX(int val) { this.BX = val; }
    public void setCX(int val) { this.CX = val; }
    public void setDX(int val) { this.DX = val; }
    public void setPC(int val) { this.PC = val; }
    public void setState(int val) { this.state = val; }
    public void setPrSize (int val) {this.prSize = val; }
    public void setOpenFiles (Vector<String> of) {this.openFiles =of; }
}
